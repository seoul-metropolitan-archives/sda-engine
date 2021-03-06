var fnObj = {};
var parentInZoneUuid = "";
var parentOutZoneUuid = "";
var ACTIONS = axboot.actionExtend(fnObj, {
    // JOB 조회
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st028/01/list01",
            data: $.extend({}, {pageSize: 1000}, this.formView.getData(),{inZoneUuid: parentInZoneUuid},{outZoneUuid: parentOutZoneUuid}),
            callback: function (res) {
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.setData(res.list);

            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        if( !fnObj.gridView01.validate())
            return ;

        var result = false;
        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st028/01/save01",
            data: JSON.stringify(this.gridView01.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        })
            .done(function () {
                fnObj.gridView01.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;


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
    },
    SEARCH_INZONE_SCH: function(caller, act, data){
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {

                $("input[data-ax-path='InZoneName']").val(data["ZONE_NAME"])
                $("input[data-ax-path='InZoneName']").attr("InZoneName",data["ZONE_NAME"])
                parentInZoneUuid = data['ZONE_UUID'];
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });
    },
    SEARCH_OUTZONE_SCH: function(caller, act, data){
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {

                $("input[data-ax-path='outZoneName']").val(data["ZONE_NAME"])
                $("input[data-ax-path='outZoneName']").attr("outZoneName",data["ZONE_NAME"])
                parentOutZoneUuid = data['ZONE_UUID'];
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });
    },
});


// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    //TODO 추후에 삭제될 내용으로 /실제 Grid의 컬럼 정보는 DB에서 가져올 예정
    $.ajax({
        url: "/assets/js/column_info/st02801.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    //_this.gridView02.initView();

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

        $("select[data-ax-path='modeUuid']").change(function () {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        });

        $("select[data-ax-path='sensorUseYn']").change(function () {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        });

        $("input[data-ax-path='InZoneName']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU146",
                /*searchData : $("input[data-ax-path='parentContainerName']").val().trim(),*/
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_INZONE_SCH,data);
        });

        $("input[data-ax-path='outZoneName']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU146",
                /*searchData : $("input[data-ax-path='parentContainerName']").val().trim(),*/
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_OUTZONE_SCH,data);
        });

        $("input[data-ax-path='InZoneName']").keyup(function(e){
            if($(this).val() == ""){
                parentInZoneUuid = "";
            }
        });

        $("input[data-ax-path='outZoneName']").keyup(function(e){
            if($(this).val() == ""){
                parentOutZoneUuid = "";
            }
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


// AC005 User Group User GridView
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey: "gateUuid",
    entityName: "ST_GATE",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st02801.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    itemClick: function (data, index) {

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
