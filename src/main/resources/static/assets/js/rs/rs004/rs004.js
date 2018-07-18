var fnObj = {};
var selectedItem ; //선택된 그리드 아이템
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";
var isDetailChanged = false;
var CANCEL_STATUS_CD = "";
var CONFIRM_STATUS_CD = "";
var recordScheduleUuid = ""
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/common/controller",
            data: $.extend({}, {pageSize: 1000, sort:"", serviceId:"rs004",methodName:"getRecordScheduleResultList",recordScheduleUuid:recordScheduleUuid}, this.formView.getData()),
            callback: function (res) {
                if(res.list == null || res.list.length <= 0){
                    fnObj.gridView01.setData([]);
                    fnObj.gridView01.disabledColumn();
                    return;
                }
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.disabledColumn();
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
            url: "/api/v1/rs004/02",
            data: $.extend({}, {pageSize: 1000}, fnObj.gridView01.getSelectedData()),
            callback: function (res) {
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    STATUS_UPDATE: function (caller, act, state) {
        var rows = fnObj.gridView01.gridObj.getCheckedList();
        if(!rows || rows.length < 1) return;

        if(state == CONFIRM_STATUS){//confirm 버튼 클릭 시 체크
            for(var i=0;i<rows.length;i++){
                if(rows[i]['statusUuid'] != CANCEL_STATUS_CD){
                    return;
                }
            }
            for(var i=0;i<rows.length;i++){
                if(!fnObj.gridView01.validate()){
                    return;
                }
            }
        }else{//cancel 버틈 클릭 시
            for(var i=0;i<rows.length;i++){
                if(rows[i]['statusUuid'] != CONFIRM_STATUS_CD){
                    return;
                }
            }
        }

        var params = rows.filter(function (item) {
            item.changeStatus = state;
            return item.recordScheduleResultUuid !== "";
        });
        axboot.ajax({
            type: "PUT",
            url: "/api/v1/rs/rs004/02/confirm",
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
        fnObj.gridView01.gridObj.commit();
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE,CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        fnObj.gridView01.gridObj.commit();
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE,CANCEL_STATUS);
    },
    PAGE_SAVE: function (caller, act, data) {
        if(!this.gridView01.gridObj.validate()){
            return false;
        }else{
            ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE);
        }
        // ACTIONS.dispatch(ACTIONS.TOP_GRID_DETAIL_PAGE_SAVE);
    },
    TOP_GRID_SAVE: function (caller, act, data) {
        var result = false;
        axboot.call({
            type: "PUT",
            url: "/api/v1/rs004/",
            data: JSON.stringify(this.gridView01.getData()),
            callback: function (res) {
                if(isDetailChanged){
                    isDetailChanged = false;
                    ACTIONS.dispatch(ACTIONS.TOP_GRID_DETAIL_PAGE_SAVE);
                    fnObj.gridView01.commit();
                }
                result = true;
            }
        })
            .done(function () {
                fnObj.gridView01.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    TOP_GRID_DETAIL_PAGE_SAVE :function () {

        axboot.ajax({
            type: "GET",
            url: "/api/v1/rs004",
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
    SEARCH_RSCODE_SCH : function(caller, act, data)
    {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='rsCode']").val(data["RS_CODE"])
                $("input[data-ax-path='rsCode']").attr("rsCode",data["RS_CODE"])
                $("input[data-ax-path='rsName']").val(data["RS_NAME"])
                $("input[data-ax-path='rsName']").attr("rsName",data["RS_NAME"])
                recordScheduleUuid = data["RECORD_SCHEDULE_UUID"]
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,data);
            }
        });
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    MENU_OPEN: function (caller,act, data){

    },
    PAGE_SCHEDULING: function(caller,act,data){
        axboot.modal.open({
            modalType: "SCHEDULING_RECORD_POPUP",
            width: 1600,
            height: 800,
            header: {
                title: "SCHEDULING"
            },
            sendData: function () {
                return {

                };
            },
            callback: function (data) {
                if(this) this.close();
                if(data){
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,data);
                }
            }
        });
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
        url: "/assets/js/column_info/rs00401.js",
        dataType: "script",
        async: false,
        success: function () {
        }
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

        CONFIRM_STATUS_CD = axboot.commonCodeValueByCodeName("CD137", CONFIRM_STATUS);
        CANCEL_STATUS_CD = axboot.commonCodeValueByCodeName("CD137", CANCEL_STATUS);

        $("input[data-ax-path='rsCode']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU140",
                searchData : $("input[data-ax-path='rsCode']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_RSCODE_SCH,data);
        });
        $("input[data-ax-path='rsCode']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU140",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_RSCODE_SCH,data);
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

/*팝업 헤더*/
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey : "generalRecordScheduleUuid",
    entityName: "General Record Schedule UUID",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(rs00401.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        this.gridObj.on
    },

    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
    disabledColumn : function()
    {
        var codes = axboot.commonCodeFilter("CD137").codeArr;
        var names = axboot.commonCodeFilter("CD137").nameArr;
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
        },["rsCode","description"]);
    },
    onImageButtonClicked :function (grid, itemIndex, column, buttonIndex, name) {

        alert("onImageButtonClicked: " + itemIndex + ", " + column.name + ", " + buttonIndex + ", " + name);
    },
    itemClick: function (data) {
        /*if (data.classificationSchemeUuid != null && data.classificationSchemeUuid != "") {
            if (isDetailChanged) {
                axDialog.confirm({
                    msg: axboot.getCommonMessage("AA006")
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.TOP_GRID_DETAIL_PAGE_SAVE);
                    } else {
                        isDetailChanged = false;
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
                    }
                });
            } else {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
            }
        }*/
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