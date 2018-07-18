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
                }else{
                    isDetailChanged = false;
                }
            });
        }
        axboot.ajax({
            type: "GET",
            url: "/api/v1/df/df001/searchList",
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
        fnObj.formView.setFormData("reason", selectedData.reason);

        axboot.ajax({
            type: "GET",
            url: "/api/v1/common/controller",
            data: $.extend({}, {pageSize: 1000, sort: "", serviceId:"df001", methodName:"detail"}, fnObj.gridView01.getSelectedData()),
            //url: "/api/v1/df/df001/detail",
            //data: $.extend({}, {pageSize: 1000}, fnObj.gridView01.getSelectedData()),
            callback: function (res) {
                if(!selectedData)
                    return ;

                fnObj.formView.setFormData("freezeCnt",res.freezeCnt);
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
            return item.disposalFreezeEventUuid !== "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/df/df001/updateStatus",
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
            url: "/api/v1/df/df001/saveItems",
            data: JSON.stringify(this.gridView01.getData()),
            callback: function (res) {
                if(isDetailChanged){
                    isDetailChanged = false;
                    fnObj.gridView01.commit();
                }

                ACTIONS.dispatch(ACTIONS.TOP_GRID_DETAIL_PAGE_SAVE);
                isDetailChanged = false;
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
            url: "/api/v1/df/df001/saveDetailItem",
            data: $.extend({},  {pageSize: 1000},this.gridView01.getSelectedData() ,this.formView.getData()),
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
        url: "/assets/js/column_info/df00101.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $(document).delegate(".under.dfCondition", "click", function () {
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
        return $.extend({}, axboot.formView.defaultData, {endYN: ""});
    },
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작

        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        //$("input[data-ax-path='startFromDate']").val(getFormattedDate(new Date(), true));
        //$("input[data-ax-path='startToDate']").val(getFormattedDate(new Date()));

        this.initEvent();
    },
    initEvent: function () {
        var _this = this;
        $("textarea[data-ax-path='reason']").keyup(function(){
            isDetailChanged = true;
        });

        $("input[data-ax-path='eventCode'],input[data-ax-path='eventName']").keyup(function(){
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
    primaryKey : "disposalFreezeEventUuid",
    entityName: "DF_DISPOSAL_FREEZE_EVENT",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(df00101.column_info);
        this.setFixedOptions({
            colCount: 4
        });
        this.gridObj.setOption({
            checkBar: {visible:true, showAll:false},
            indicator: {visible: true}
        })
        this.makeGrid();
        this.removeRowBeforeEvent(this.cancelDelete);
        this.gridObj.itemClick(this.itemClick);
        this.gridObj.onItemChecked(this.onItemChecked);
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
    disabledColumn : function()
    {
        var state = axboot.commonCodeValueByCodeName("CD115", CONFIRM_STATUS);
        this.gridObj.setCustomCellStyleRows("disable",
            function(row){
                if(row["statusUuid"] == state) {
                    return true;
                }else
                    return false;
            },["eventName", "eventTypeUuid", "reviewDate", "description"]);
    },
    onItemChecked: function (grid, itemIndex) {
        var checkData = this.gridView.getDataProvider().getJsonRow(itemIndex);
        if(checkData["freezeCnt"] > 0){
            axWarningToast.push(axboot.getCommonMessage("DF001_02"));

            this.gridView.checkItem(itemIndex, false);
        }
    },
    itemClick: function (data) {
        if (data.disposalFreezeEventUuid != null && data.disposalFreezeEventUuid != "") {
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
        }
    },
    cancelDelete: function(){
        var state = axboot.commonCodeValueByCodeName("CD115", CONFIRM_STATUS);

        if(fnObj.gridView01.getSelectedData().statusUuid == state) {
            axWarningToast.push(axboot.getCommonMessage("DF001_01"));

            this.setRunDel(false);
        }else if(fnObj.formView.getFormData("freezeCnt") > 0){
            axWarningToast.push(axboot.getCommonMessage("DF001_02"));

            this.setRunDel(false);
        }else{
            this.setRunDel(true);
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