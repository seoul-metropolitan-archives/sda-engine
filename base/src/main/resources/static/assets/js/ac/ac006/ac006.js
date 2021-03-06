/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    // Role 조회
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ac006/01/list",
            data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
                if (res.list.length > 0) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, res.list[0]);
                }

            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        var permissionList = [].concat(fnObj.gridView01.getData());

        if(!fnObj.gridView01.validate())
            return ;

        axboot
            .call({
                type: "PUT",
                url: "/api/v1/ac006/01/save",
                data: JSON.stringify(permissionList),
                callback: function (res) {
                    fnObj.gridView01.commit();
                }
            })
            .done(function () {
                //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push(axboot.getCommonMessage("AA007"));
            });
    },
    FORM_CLEAR: function (caller, act, data) {
        /*
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                caller.formView01.clear();
                caller.gridView02.clear();
            }
        });
        */
    },
    ITEM_CLICK: function (caller, act, data) {
    },
    MODAL_OPEN: function (caller, act, data) {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            param: "",
            sendData: function () {
                return {
                    //jisaCode: fnObj.formView02.getData().jisaCode
                };
            },
            callback: function (data) {
                //$("#calleeEmpName").val(data.empName);
                //$("#calleeEmpTelno").val(data.empPhoneNo);

                this.close();
            }
        });
    },
    SHOW_PROGRAM_POP : function(caller, act, data){
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                console.log(data);
                $("input[data-ax-path='pmsProgramUuid']").val(data["PROGRAM_NAME"])
                $("input[data-ax-path='pmsProgramUuid']").attr("programUuid",data["PROGRAM_UUID"])
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,data);
            }
        });
    },
    SHOW_ENTITY_TYPE_POP : function(caller, act, data){
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                console.log(data);
                $("input[data-ax-path='pmsEntityTypeUuid']").val(data["ENTITY_TYPE_NAME"])
                $("input[data-ax-path='pmsEntityTypeUuid']").attr("entityTypeUuid",data["ENTITY_TYPE_UUID"])
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,data);
            }
        });
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    dispatch: function (caller, act, data) {
        var result = ACTIONS.exec(caller, act, data);
        if (result != "error") {
            return result;
        } else {
            return false;
        }
    }
});


// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    //TODO 추후에 삭제될 내용으로 /실제 Grid의 컬럼 정보는 DB에서 가져올 예정
    $.ajax({
        url: "/assets/js/column_info/ac00601.js",
        dataType: "script",
        async: false,
        success: function (test) {
            console.log(test)
        },error : function(a,b,c){
            console.log(a);
            console.log(b);
            console.log(c);
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();

    // Data 조회
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.formView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {useYn: ""});
    },
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();
    },
    initEvent: function () {
        var _this = this;
        $("input[data-ax-path='pmsProgramUuid']").parents().eq(0).find("a").click(function(){
            var data = {
                popupCode : "PU001",
                searchData : $("input[data-ax-path='pmsProgramUuid']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SHOW_PROGRAM_POP,data);
        });
        $("input[data-ax-path='classificationCode']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU001",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SHOW_PROGRAM_POP,data);
            }

        });
        $("input[data-ax-path='pmsEntityTypeUuid']").parents().eq(0).find("a").click(function(){
            var data = {
                popupCode : "PU110",
                searchData : $("input[data-ax-path='pmsEntityTypeUuid']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SHOW_ENTITY_TYPE_POP,data);
        });
        $("input[data-ax-path='pmsEntityTypeUuid']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU110",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SHOW_ENTITY_TYPE_POP,data);
            }

        });
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        return $.extend({}, data,
            {
                pmsProgramUuid : $("input[data-ax-path='pmsProgramUuid']").attr("programUuid")
                , pmsEntityTypeUuid : $("input[data-ax-path='pmsEntityTypeUuid']").attr("entityTypeUuid")
            });
    },
    setFormData: function (dataPath, value) {
        this.model.set(dataPath, value);
    },
    setData: function (data) {

        if (typeof data === "undefined") data = this.getDefaultData();
        data = $.extend({}, data);

        this.target.find('[data-ax-path="key"]').attr("readonly", "readonly");

        this.model.setModel(data);
        this.modelFormatter.formatting(); // 입력된 값을 포메팅 된 값으로 변경
    },
    validate: function () {
        var rs = this.model.validate();
        if (rs.error) {
            alert(rs.error[0].jquery.attr("title") + '을(를) 입력해주세요.');
            rs.error[0].jquery.focus();
            return false;
        }
        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
    }
});


// AC006 User Group User GridView
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid01",
    uuidFieldName : "permissionUuid",
    entityName : "AC_PERMISSION",
    initView: function () {
        this.initInstance();
        this.setFixedOptions({
            colCount: 1
        });
        this.setColumnInfo(ac00601.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    itemClick: function (data, index) {
        if (data.permissionUuid != null && data.permissionUuid != "") {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
        }
    }
});

/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}
 */
isDataChanged = function () {
    if (fnObj.gridView01.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}
