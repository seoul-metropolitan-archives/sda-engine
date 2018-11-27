var fnObj = {};
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/rs/rs003/01/list",
            data: $.extend({},this.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.disabledColumn();
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    STATUS_UPDATE: function (caller, act, data) {
        var rows = fnObj.gridView01.gridObj.getCheckedList();

        if(!rows || rows.length < 1) return;

        var params = rows.filter(function (item) {
            item.changeStatus = data;
            return item.recordScheduleUuid != "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/rs/rs003/02/confirm",
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
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE,CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE,CANCEL_STATUS);
    },
    PAGE_SAVE: function (caller, act, data) {
        if(!this.gridView01.gridObj.validate()){
            return false;
        }else{
            ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE);
        }
    },
    TOP_GRID_SAVE: function (caller, act, data) {
        var result = false;
        axboot.call({
            type: "PUT",
            url: "/api/v1/rs/rs003/03/save",
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
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    MENU_OPEN: function (caller,act, data){

    },
    PAGE_SEARCH1: function (caller,act, data){
        fnObj.gridView01.selectiveDisabledColumn();
    },
    SEARCH_GRS_CODE : function(caller, act, data)
    {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='grsCode']").val(data["GRS_NAME"])
                $("input[data-ax-path='grsCode']").attr("grsCode",data["GRS_CODE"])
                if(this.close)
                    this.close();
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
        url: "/assets/js/column_info/rs00301.js",
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
        return $.extend({}, axboot.formView.defaultData);
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
        $("input[data-ax-path='grsCode']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU130",
                searchData : $("input[data-ax-path='grsCode']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_GRS_CODE,data);
        });
        $("input[data-ax-path='grsCode']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU130",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_GRS_CODE,data);
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
    primaryKey : "recordScheduleUuid",
    entityName: "RS_RECORD_SCHEDULE",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(rs00301.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
        this.setFixedOptions({
            colCount: 6
        });
        //this.gridObj.itemClick(this.itemClick);

        var _this = this;
        this.gridObj.onCellEdited(function(gridWrapper,grid){
            fnObj.gridView01.selectiveDisabledColumn();
        });
        this.removeRowBeforeEvent(this.cancelDelete);
    },
    popupCallback: function(grid,data) {
        grid.commit(true);
        fnObj.gridView01.selectiveDisabledColumn();
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
    selectiveDisabledColumn: function(){
        if(fnObj.gridView01.gridObj.getCurrent().column == "grsCode"){
            this.gridObj.setCustomCellStyleRows("disable",function(row){
                var codes = axboot.commonCodeFilter("CD134").codeArr;
                var names = axboot.commonCodeFilter("CD134").nameArr;
                var state = undefined;
                for(var i = 0; i < names.length; i++)
                {
                    if(names[i] == "Confirm")
                    {
                        state = codes[i];
                        break;
                    }
                }
                if(row["statusUuid"] != state) {//Draft 일때
                    if (row["grsCode"] == "" || row["grsCode"] == undefined){
                        // fnObj.gridView01.gridObj.setValue(fnObj.gridView01.gridObj.getCurrent()["dataRow"], "disposalTypeUuid",'');
                        return false;
                    } else return true;

                } else return true;


            },["retentionPeriodUuid","disposalTypeUuid","basedOn"]);
        }else if(fnObj.gridView01.gridObj.getCurrent().column == "retentionPeriodUuid"){
            var state = axboot.commonCodeValueByCodeName("CD133", "영구");
            this.gridObj.setCustomCellStyleRow(fnObj.gridView01.gridObj, fnObj.gridView01.gridObj.gridView, fnObj.gridView01.gridObj.getCurrent()["dataRow"],"disable", function () {
                var result = false;
                if(!fnObj.gridView01.getCurrentData()) return false;
                if (fnObj.gridView01.getCurrentData()["retentionPeriodUuid"] == state){
                    fnObj.gridView01.gridObj.setValue(fnObj.gridView01.gridObj.getCurrent()["dataRow"], "disposalTypeUuid",axboot.commonCodeValueByCodeName("CD135", "보존"));
                    result = true;
                }else{
                    result = false;
                }
                return result;
            }, ["disposalTypeUuid"]);
        }
        fnObj.gridView01.gridObj.commit();
        //fnObj.gridView01.disabledColumn();
    },
    disabledColumn : function()
    {
        var codes = axboot.commonCodeFilter("CD134").codeArr;
        var names = axboot.commonCodeFilter("CD134").nameArr;
        var cd133State = axboot.commonCodeValueByCodeName("CD133", "영구");

        var state = undefined;
        for(var i = 0; i < names.length; i++)
        {
            if(names[i] == "Confirm")
            {
                state = codes[i];
                break;
            }
        }
        var reverse = null;

        this.gridObj.setCustomCellStyleRows("disable",function(row){
            return row["statusUuid"] == state || (row["grsCode"] != "" && row["grsCode"] != undefined) || row["retentionPeriodUuid"] == cd133State //Confirm 일때
        },function(row){
            if(row["statusUuid"] == state) {
                return ["grsCode", "rsName", "retentionPeriodUuid", "disposalTypeUuid", "basedOn", "triggerName", "description"];
            }else if(row["statusUuid"] != state && row["grsCode"] != "" && row["grsCode"] != undefined){
                return ["retentionPeriodUuid","disposalTypeUuid","basedOn"];
            }else if(row["statusUuid"] != state && row["retentionPeriodUuid"] == cd133State){
                return ["disposalTypeUuid"];
            }else{
                return ["grsCode", "rsName", "retentionPeriodUuid", "disposalTypeUuid", "basedOn", "triggerName", "description"];
            }
        },true,function(row){
            if(row["statusUuid"] != state){
                if(row["grsCode"] != "" && row["grsCode"] != undefined){
                    return ["grsCode", "rsName","triggerName", "description"];
                }else if(row["retentionPeriodUuid"] == cd133State){
                    return ["grsCode", "rsName","triggerName", "description","retentionPeriodUuid","basedOn"];
                }else{
                    return [];
                }
            }else{
                return [];
            }
        });
    },
    itemClick: function (data) {
    },
    cancelDelete: function(){
        var index = fnObj.gridView01.gridObj.getCurrent().dataRow;
        var state = axboot.commonCodeValueByCodeName("CD134", "Confirm");

        if(fnObj.gridView01.gridObj.getSelectedData().statusUuid == state) {
            this.setRunDel(false);
        }else{
            this.setRunDel(true);
        }
    },

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