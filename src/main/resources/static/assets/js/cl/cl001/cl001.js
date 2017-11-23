var fnObj = {};
var selectedItem ; //선택된 그리드 아이템
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";
var isDetailChanged = false;

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        if (isDetailChanged) {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA006")
            }, function () {
                if (this.key == "ok") {
                    isDetailChanged = false;
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    return;
                }
            });
        }
        axboot.ajax({
            type: "GET",
            url: "/api/v1/cl001/01/list",
            data: $.extend({}, {pageSize: 1000, sort: "classificationCode"}, this.formView.getData()),
            callback: function (res) {
                if(res.list == null || res.list.length <= 0){
                    return;
                }
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.disabledColumn();
                isDetailChanged = false;
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/cl001/02/detail",
            data: $.extend({}, {pageSize: 1000}, fnObj.gridView01.getSelectedData()),
            callback: function (res) {
                var selectedData = fnObj.gridView01.getSelectedData();

                if(!selectedData)
                    return ;

                fnObj.formView.setFormData("managerOrganization",res.managerOrganization);
                fnObj.formView.setFormData("manager",res.manager);
                fnObj.formView.setFormData("basedOn",res.basedOn);
                if(fnObj.gridView01.getSelectedData().classificationName != "undefined"){
                    fnObj.formView.setFormData("classificationNameTxt", fnObj.gridView01.getSelectedData().classificationName);
                }
                fnObj.formView.setFormData("useNo",res.useNo);
                fnObj.formView.setFormData("useYes",res.useYes);
                fnObj.formView.setFormData("aggregationCnt",res.aggregationCnt);
                fnObj.formView.setFormData("itemCnt",res.itemCnt);

            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    STATUS_UPATE: function (caller, act, data) {
        var rows = fnObj.gridView01.gridObj.getCheckedList();

        if(!rows || rows.length < 1) return;

        var params = rows.filter(function (item) {
            item.changeStatus = data;
            return item.classificationSchemeUuid !== "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/cl001/04/updateStatus",
            data: JSON.stringify(params),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },

    PAGE_CONFIRM: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPATE,CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPATE,CANCEL_STATUS);
    },
    PAGE_SAVE: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE);
        // ACTIONS.dispatch(ACTIONS.TOP_GRID_DETAIL_PAGE_SAVE);
    },
    TOP_GRID_SAVE: function (caller, act, data) {
        var result = false;

        if(!this.gridView01.validate())
            return false;

        axboot.call({
                type: "PUT",
                url: "/api/v1/cl001/03/updateClassificationSchemeList",
                data: JSON.stringify(this.gridView01.getData()),
                callback: function (res) {
                    if(isDetailChanged){
                        isDetailChanged = false;
                        ACTIONS.dispatch(ACTIONS.TOP_GRID_DETAIL_PAGE_SAVE);
                    }
                    result = true;
                }
            })
            .done(function () {
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    TOP_GRID_DETAIL_PAGE_SAVE :function () {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/cl001/05/updateClassificationSchemeConDetail",
            data: $.extend({},  {pageSize: 1000},fnObj.gridView01.getSelectedData() ,this.formView.getData()),
            callback: function (res) {
                isDetailChanged = false;
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    MENU_OPEN: function (caller,act, data){

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

fnObj.pageStart = function () {
    var _this = this;
    $.ajax({
        url: "/assets/js/controller/simple_controller.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/cl00101.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $(document).delegate(".under.classCondition", "click", function () {
        ACTIONS.dispatch(ACTIONS.MENU_OPEN);
    });

    $(document).delegate(".under.classRecordCondition", "click", function () {
        ACTIONS.dispatch(ACTIONS.MENU_OPEN);
    });

    _this.formView.initView();
    _this.gridView01.initView();
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH, this.formView.getData());
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
        $("textarea[data-ax-path='basedOn'],input[data-ax-path='manager'],input[data-ax-path='managerOrganization']").keyup(function(){
            isDetailChanged = true;
        });

        $("input[data-ax-path='classificationCode'],input[data-ax-path='classificationName']").keyup(function(){
            if(13 == event.keyCode)
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        });
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        return $.extend({}, data);
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

/*팝업 헤더*/
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey : "classificationSchemeUuid",
    entityName: "Classification Scheme",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(cl00101.column_info);
        this.setFixedOptions({
            colCount: 4
        });
        this.gridObj.setOption({
            checkBar: {visible: true},
            indicator: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
    disabledColumn : function()
    {
        var codes = axboot.commonCodeFilter("CD111").codeArr;
        var names = axboot.commonCodeFilter("CD111").nameArr;
        var state = undefined;
        for(var i = 0; i < names.length; i++)
        {
            if(names[i] == "Confirm")
            {
                state = codes[i];
                break;
            }
        }
        this.gridObj.setCustomCellStyleRows("disable",function(row){

            if(row["statusUuid"] == state)
                return true;
            else
                return false;
        },["classificationName","classificationTypeUuid","orderNo","useYn"]);
    },
    itemClick: function (data) {
        if (data.classificationSchemeUuid != null && data.classificationSchemeUuid != "") {
            if (isDetailChanged) {
                axDialog.confirm({
                    msg: axboot.getCommonMessage("AA006")
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.TOP_GRID_DETAIL_PAGE_SAVE);
                    } else {
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
                    }
                });
            } else {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
            }
        }
    }
});

/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}

isDataChanged = function () {
    if (fnObj.gridView01.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}
 */