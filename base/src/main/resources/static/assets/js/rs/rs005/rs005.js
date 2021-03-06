var fnObj = {};
var selectedItem ; //선택된 그리드 아이템
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";
var COMPLETE_STATUS = "Complete";

var CONFIRM_STATUS_CD = "";
var CANCEL_STATUS_CD = "";
var COMPLETE_STATUS_CD = "";

var isDetailChanged = false;
var names = [];
var codes = [];

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {

        if(fnObj.formView.getFormData("disposalFromDueDate") == "" || fnObj.formView.getFormData("disposalFromDueDate") == null
            || fnObj.formView.getFormData("disposalToDueDate") == "" || fnObj.formView.getFormData("disposalToDueDate") == null) {
            axToast.push("Disposal Due Date 는 필수 입력 값입니다.");
            return false;
        }

        if((fnObj.formView.getFormData("disposalFromConfirmDate") == "" || fnObj.formView.getFormData("disposalFromConfirmDate") == null) !=
            (fnObj.formView.getFormData("disposalToConfirmDate") == "" || fnObj.formView.getFormData("disposalToConfirmDate") == null)) {
            axToast.push("Disposal Confirm Date 는 사이값이(from~to) 있거나 없어야 합니다. ");
            return false;
        }

        if((fnObj.formView.getFormData("disposalFromCompleteDate") == "" || fnObj.formView.getFormData("disposalFromCompleteDate") == null) !=
            (fnObj.formView.getFormData("disposalToCompleteDate") == "" || fnObj.formView.getFormData("disposalToCompleteDate") == null)) {
            axToast.push("Disposal Confirm Date 는 사이값이(from~to) 있거나 없어야 합니다. ");
            return false;
        }

        axboot.call({
            type: "GET",
            url: "/api/v1/common/controller",
            data: $.extend({}, {pageSize: 1000, sort:"", serviceId:"rs005",methodName:"getDisposalRecordList"}, this.formView.getData()),
            callback: function (res) {
                if(res.list == null || res.list.length <= 0){
                    fnObj.gridView01.setData([]);
                    fnObj.gridView01.disabledColumn();
                    return;
                }
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.disabledColumn();
                fnObj.gridView01.disabledCheckBar(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        })
            .done(function () {
                fnObj.gridView01.commit();
            });


        return false;
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    STATUS_UPDATE: function (caller, act, state) {
        fnObj.gridView01.commit();
        var rows = fnObj.gridView01.gridObj.getCheckedList();
        if(!rows || rows.length < 1) return;

        if(state == COMPLETE_STATUS) { //disposal 버튼 클릭 시 체크
            for(var i=0;i<rows.length;i++){
                if(rows[i]['disposalStatus'] != CONFIRM_STATUS_CD){
                    return;
                }
            }
            axDialog.confirm({
                msg: axboot.getCommonMessage("RS005_01")
            }, function () {
                if (this.key == "ok") {
                    ACTIONS.dispatch(ACTIONS.PAGE_UPDATE,state);
                }
            });
        }else if(state == CONFIRM_STATUS){//confirm 버튼 클릭 시 체크
            for(var i=0;i<rows.length;i++){
                if(rows[i]['disposalStatus'] != CANCEL_STATUS_CD){
                    return;
                }
            }
            for(var i=0;i<rows.length;i++){
                if(!fnObj.gridView01.validate()){
                    return;
                }
            }
            ACTIONS.dispatch(ACTIONS.PAGE_UPDATE,state);
        }else{//cancel 버틈 클릭 시
            for(var i=0;i<rows.length;i++){
                if(rows[i]['disposalStatus'] != CONFIRM_STATUS_CD){
                    return;
                }
            }
            ACTIONS.dispatch(ACTIONS.PAGE_UPDATE,state);
        }
    },
    PAGE_UPDATE:function (caller, act, state) {
        var rows = fnObj.gridView01.gridObj.getCheckedList();
        var params = rows.filter(function (item) {
            item.changeStatus = state;
            return item.recordScheduleResultUuid !== "";
        });
        axboot.ajax({
            type: "PUT",
            url: "/api/v1/rs/rs005/02/confirm",
            data: JSON.stringify(params),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_DISPOSAL: function (caller, act, data) {
        fnObj.gridView01.gridObj.commit();
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE,COMPLETE_STATUS);
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
        url: "/assets/js/column_info/rs00501.js",
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

        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });
        fnObj.formView.setFormData("disposalFromDueDate",getFormattedDate(new Date()));
        fnObj.formView.setFormData("disposalToDueDate",getFormattedDate(new Date()));
    },
    initEvent: function () {
        var _this = this;

        codes = axboot.commonCodeFilter("CD137").codeArr;
        names = axboot.commonCodeFilter("CD137").nameArr;
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        return $.extend({}, data);
    },
    setFormData: function (dataPath, value) {
        this.model.set(dataPath, value);
    },
    getFormData: function (dataPath) {
        return this.model.get(dataPath);
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
    entityName : "RS_RECORD_SCHEDULE_RESULT",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(rs00501.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
    setCheckable: function(itemIndex, value){
        this.gridObj.setCheckable(itemIndex, value);
    },
    disabledCheckBar: function(data)
    {

        CONFIRM_STATUS_CD = axboot.commonCodeValueByCodeName("CD137", CONFIRM_STATUS);
        CANCEL_STATUS_CD = axboot.commonCodeValueByCodeName("CD137", CANCEL_STATUS);
        COMPLETE_STATUS_CD = axboot.commonCodeValueByCodeName("CD137", COMPLETE_STATUS);

        for(var j=0; j < data.length;j++){
            if(data[j]["disposalStatus"] == COMPLETE_STATUS_CD){
                this.gridObj.setCheckable(j,false);
            }
        }
    },
    disabledColumn : function()
    {
        var state = undefined;
        for(var i = 0; i < names.length; i++)
        {
            if(names[i] == CANCEL_STATUS)
            {
                state = codes[i];
                break;
            }
        }
        this.gridObj.setCustomCellStyleRows("disable",function(row){

            if(row["disposalStatus"] == state)
                return false;
            else
                return true;
        },["disposalConfirmDate","disposalConfirmReason"]);
    },
    itemClick: function (data) {

    }
});
function getFormattedDate(date, isStart) {
    var day;
    var tempDate;
    if (isStart) {
        date.setDate(date.getDate() - 10);
        tempDate = date.getDate();
    } else {
        tempDate = date.getDate();
    }
    day = tempDate.toString();

    var year = date.getFullYear();
    var month = (1 + date.getMonth()).toString();
    month = month.length > 1 ? month : '0' + month;
    day = day.length > 1 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}
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