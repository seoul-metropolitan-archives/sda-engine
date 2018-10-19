var fnObj = {};
var selectedItem ; //선택된 그리드 아이템
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";
var eventCode = "";
var isDetailChanged = false;

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ad/ad007/list",
            data: $.extend({}, {pageSize: 1000, sort: "eventCode"}, this.formView.getData()),
            callback: function (res) {
                if(res.list == null || res.list.length <= 0){
                    fnObj.gridView01.setData([]);
                    fnObj.gridView01.disabledColumn();
                    return;
                }
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.disabledColumn();

                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH1: function (caller, act, data) {
        var selectedData = fnObj.gridView01.getSelectedData();

        if(!selectedData)
            return;

        fnObj.formView.setFormData("eventNameTxt", selectedData.eventName);

        axboot.ajax({
            type: "GET",
            url: "/api/v1/ad/ad007/listSub",
            data: $.extend({}, {pageSize: 1000}, fnObj.gridView01.getSelectedData()),
            callback: function (res) {
                fnObj.gridView02.setData(res.list);
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
            return item.addContextualMetaUuid !== "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/ad/ad007/updateStatus",
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
            url: "/api/v1/ad/ad007/save",
            data: JSON.stringify(this.gridView01.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.BOTTOM_GRID_SAVE);
                result = true;
            }
        })
        .done(function () {
            fnObj.gridView01.commit();
            axToast.push(axboot.getCommonMessage("AA007"));
        });
        return result;
    },
    BOTTOM_GRID_SAVE: function (caller, act, data) {
        var result = false;

        axboot.call({
            type: "PUT",
            url: "/api/v1/ad/ad007/saveSub",
            data: JSON.stringify(this.gridView02.getData()),
            callback: function (res) {
                fnObj.gridView02.commit();
                result = true;
            }
        })
        .done(function () {
            fnObj.gridView02.commit();
            axToast.push(axboot.getCommonMessage("AA007"));
        });
        return result;
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
        url: "/assets/js/column_info/ad00701.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/ad00702.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH, this.formView.getData());
};

fnObj.formView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {endYN: ""});
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

        $("#entityType").change(function(){
            $("input[data-ax-path='columnCode']").val($('select#entityType option:selected').attr("data-attr01"));
        });
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        return $.extend({}, data);
    },
    setData: function (data) {

        if (typeof data === "undefined") data = this.getDefaultData();
        data = $.extend({}, data);

        this.target.find('[data-ax-path="key"]').attr("readonly", "readonly");

        this.model.setModel(data);
        this.modelFormatter.formatting(); // 입력된 값을 포메팅 된 값으로 변경
    },
    getFormData: function (dataPath) {
        return this.model.get(dataPath);
    },
    setFormData: function (dataPath, value) {
        this.model.set(dataPath, value);
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

/* Additional Meta Segment Grid*/
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey : "addMetaTemplateSetUuid",
    entityName: "AD_CON_META_SETUP",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(ad00701.column_info);
        this.setFixedOptions({
            colCount: 0
        });
        this.gridObj.setOption({
            //checkBar: {visible: true},
            indicator: {visible: true}
        })
        this.makeGrid();
        this.gridObj.setValidations([]);
        this.gridObj.itemClick(this.itemClick);
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData();
    },
    disabledColumn : function()
    {

    },
    itemClick: function (data) {
        if (data.addMetaTemplateSetUuid != null && data.addMetaTemplateSetUuid != "") {
            if(fnObj.gridView02.getData().length > 0){
                axDialog.confirm({
                    msg: axboot.getCommonMessage("AA006")
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.BOTTOM_GRID_SAVE);
                    } else {
                        isDetailChanged = false;
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
                    }
                });
            } else {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
            }
        }
    }
});

/* Additional Meta Setup Grid*/
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid02",
    primaryKey : "addMetaSegmentUuid",
    entityName: "AD_CON_META_SEGMENT",
    parentsGrid : fnObj.gridView01,
    parentsUuidFieldName : "addMetaTemplateSetUuid",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(ad00702.column_info);
        this.setFixedOptions({
            colCount: 0
        });
        this.gridObj.setOption({
            checkBar: {visible: false},
            indicator: {visible: true}
        })
        this.makeGrid();
        this.gridObj.setValidations([]);
        this.gridObj.onCellEdited(this.rowEditHandler);
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData();
    },
    disabledColumn : function()
    {
    },
    itemClick: function (data) {

    },
    rowEditHandler: function(gridWrapper,grid){
        var cellInfo = grid.getCurrent();

        if(cellInfo.column != "entityType" && cellInfo.column != 'inputMethodUuid') return;

        var rowData = this.getSelectedData();
        var columnInfo = null;

        if(cellInfo.column == "entityType") {
            columnInfo = this.getColumnInfo(cellInfo.column, "attributes");

            gridWrapper.commit();
            columnInfo.some(function (item) {
                if (item['code'] == rowData['entityType']) {
                    gridWrapper.setValue(cellInfo.itemIndex, 'columnCode', item['attribute01']);
                    gridWrapper.setValue(cellInfo.itemIndex, 'metadataEntityType', item['attribute02']);

                    return true;
                }
            });
        }else if(cellInfo.column == 'inputMethodUuid'){
            //gridWrapper.commit();
            gridWrapper.setValue(cellInfo.itemIndex, 'inputValue', "");
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