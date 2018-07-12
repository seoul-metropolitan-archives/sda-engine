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
            url: "/api/v1/df/df003/searchTree",
            data: $.extend({}, {pageSize: 1000, sort: "eventCode"}, this.formView.getData()),
            callback: function (res) {
                if(res.list == null || res.list.length <= 0){
                    fnObj.gridView01.setData([]);
                    return;
                }
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.resetCurrent();
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH1: function (caller, act, data) {
        if(data == null) return;

        axboot.ajax({
            type: "GET",
            url: "/api/v1/df/df003/searchList",
            async : false,
            data: $.extend({}, {pageSize: 1000}, this.formView.getData(), data),
            callback: function (res) {
                fnObj.gridView02.resetCurrent();
                fnObj.gridView02.setData(res.list);
                fnObj.formView.setFormData("itInAggregationName", "");
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
        var rows = fnObj.gridView02.gridObj.getCheckedList();

        if(!rows || rows.length < 1) return;

        var params = rows.filter(function (item) {
            item.changeStatus = data;
            return item.disposalFreezeResultUuid !== "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/df/df003/unfreeze",
            data: JSON.stringify(params),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, fnObj.gridView01.getSelectedData());
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_CONFIRM: function (caller, act, data) {
        // Freeze시킬 Item 추가
        if(fnObj.gridView01.getSelectedData() == null
            || fnObj.gridView01.getSelectedData().disposalFreezeDegreeUuid == ""
            || fnObj.gridView01.getSelectedData().disposalFreezeDegreeUuid == undefined){
            return;
        }

        axboot.modal.open({
            modalType: "FREEZE_POPUP",
            width: 1600,
            height: 800,
            header: {
                title: "Freeze"
            },
            sendData: function () {
                return {
                    confirmBtn: "Freeze",
                    crrntAgg: fnObj.gridView01.getSelectedData().eventCode,
                    disposalFreezeResultUuid :  fnObj.gridView01.getSelectedData().disposalFreezeResultUuid,
                    disposalFreezeEventUuid :  fnObj.gridView01.getSelectedData().disposalFreezeEventUuid,
                    disposalFreezeDegreeUuid :  fnObj.gridView01.getSelectedData().disposalFreezeDegreeUuid,
                    primarykey :  fnObj.gridView01.primaryKey,
                    listService : "/api/v1/df/df003/freeze/search",
                    saveService : "/api/v1/df/df003/saveItems"
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
    PAGE_UNFREEZE: function (caller, act, data) {
        var rows = fnObj.gridView01.gridObj.getCheckedList();

        if(!rows || rows.length < 1) return;

        var params = rows.filter(function (item) {
            item.changeStatus = data;
            return item.disposalFreezeEventUuid !== "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/df/df003/unfreeze",
            data: JSON.stringify(params),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, fnObj.gridView01.getSelectedData());
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_GRID_INQUIRY: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, fnObj.gridView01.getSelectedData());
    },
    TOP_GRID_SAVE: function (caller, act, data) {
        var result = false;

        axboot.call({
            type: "PUT",
            url: "/api/v1/df/df003/saveItems",
            data: JSON.stringify(this.gridView02.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, fnObj.gridView01.getSelectedData());
                result = true;
            }
        })
            .done(function () {
                fnObj.gridView02.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    TOP_GRID_DETAIL_PAGE_SAVE :function () {

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
    },
    SEARCH_AGG_CODE : function(caller, act, data)
    {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                fnObj.formView.setFormData("aggregationCode", data['AGGREGATION_CODE']);
                fnObj.formView.setFormData("aggregationTitle", data['TITLE']);
                fnObj.formView.setFormData("aggregationUuid", data['AGGREGATION_UUID']);
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,data);
            }
        });
    },
    SEARCH_ITEM_CODE : function(caller, act, data)
    {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                fnObj.formView.setFormData("itemCode", data['ITEM_CODE']);
                fnObj.formView.setFormData("itemTitle", data['TITLE']);
                fnObj.formView.setFormData("itemUuid", data['ITEM_UUID']);
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,data);
            }
        });
    }
});

fnObj.pageStart = function () {
    var _this = this;
    $.ajax({
        url: "/assets/js/column_info/df00301.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/df00302.js",
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

        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });
    },
    initEvent: function () {
        var _this = this;

        /** 팝업 초기화 시 관련 필드 함께 초기화 **/
        $("input[data-ax-path='aggregationCode']").change(function(){
            fnObj.formView.setFormData("aggregationTitle", "");
            fnObj.formView.setFormData("aggregationUuid", "");
        });

        $("input[data-ax-path='itemCode']").change(function(){
            fnObj.formView.setFormData("itemTitle", "");
            fnObj.formView.setFormData("itemUuid", "");
        });

        $("input[data-ax-path='aggregationCode']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU123",
                searchData : $("input[data-ax-path='aggregationCode']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_AGG_CODE,data);
        });
        $("input[data-ax-path='aggregationCode']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU123",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_AGG_CODE,data);
            }

        });

        $("input[data-ax-path='itemCode']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU139",
                searchData : $("input[data-ax-path='itemCode']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_ITEM_CODE,data);
        });
        $("input[data-ax-path='aggregationCode']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU139",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_ITEM_CODE,data);
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
    tagId : "realgrid01",

    initView: function () {
        this.gridObj = new TreeGridWrapper("realgrid01", "/assets/js/libs/realgrid", true);
        this.gridObj.setGridStyle("100%", "100%")
            .setOption({
                footer:{visible:false},
                header: { visible: true},
                checkBar: {visible:false, exclusive: true},
                indicator: {visible: false},
                stateBar:{visible:false},
                checkBox:{visible:true},
            })
        this.gridObj.setColumnInfo(df00301.column_info).makeGrid();

        this.gridObj.setDisplayOptions({
            fitStyle:"evenFill"
        });
        this.gridObj.itemClick(this.itemClick);
        this.gridObj.itemClick(this.itemClick);
        this.gridObj.onItemChecked(this.onItemChecked);
        this.bindEvent();
    },
    bindEvent : function()
    {
        var _this = this;
        $(".open_close.expendAll").click(function(){
            _this.gridObj.expandAll();
        });
        $(".open_close.collapseAll").click(function(){
            _this.gridObj.collapseAll();
        });
        $("#leftMenuParam").keydown(function(event){
            if(13 == event.keyCode)
                $("#searchLeftMenu").click();
        })
        $("#searchLeftMenu").click(function(){
            if("" != $("#leftMenuParam").val())
            {
                _this.gridObj.search(["title"],$("#leftMenuParam").val())
            }
        });
        $(".btn_arrange").click(function(){
            ACTIONS.dispatch(ACTIONS.MODAL_OPEN);
        });
    },
    setData: function (list) {
        this.gridObj.setTreeDataForArray(list, "orderKey1");
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData();
    },
    isChangeData: function () {
        if (this.getData().length > 0) {
            return true;
        } else {
            return false;
        }
    },
    itemClick: function (data, index) {
        if(data){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,data);
        }
    },
    getData: function () {
        return this.gridObj.getData();
    },

    checkChildren : function(index,checked){
        this.gridObj.checkChildren(index, checked, true, false);
    },
    onItemChecked: function(grid,itemIndex,checked) {
        fnObj.gridView01.checkChildren(itemIndex,checked);
    }
});
/*fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey : "disposalFreezeDegreeUuid",
    entityName: "DF_DISPOSAL_FREEZE_DEGREE",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(df00301.column_info);
        this.makeGrid();
        this.removeRowBeforeEvent(this.cancelDelete);
        this.gridObj.itemClick(this.itemClick);
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
    itemClick: function(data){
        if (data.disposalFreezeDegreeUuid != null && data.disposalFreezeDegreeUuid != "") {
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
        if(fnObj.gridView01.getSelectedData().freezeYN == FREEZE_STATUS){
            axToast.push(axboot.getCommonMessage("DF002_01"));

            this.setRunDel(false);
        }else{
            this.setRunDel(true);
        }
    }

});*/

/*팝업 헤더*/
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid02",
    primaryKey : "disposalFreezeResultUuid",
    entityName: "DF_DISPOSAL_FREEZE_RESULT",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(df00302.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true},
            indicator: {visible: true}
        });
        this.gridObj.setRunAdd(false);
        this.makeGrid();
        this.removeRowBeforeEvent(this.cancelDelete);
        this.addRowAfterEvent(this.ADD_ITEM);
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData();
    },
    disabledColumn : function()
    {
        this.gridObj.setCustomCellStyleRows("disable",function(row){
            if(row["freezeYN"] == FREEZE_STATUS)
                return true;
            else
                return false;
        },["eventName","eventCode","degree"]);
    },
    ADD_ITEM: function (data) {

    },
    cancelDelete: function(){
        var state = axboot.commonCodeValueByCodeName("CD115", CONFIRM_STATUS);

        if(fnObj.gridView01.getSelectedData().statusUuid == state) {
            axToast.push(axboot.getCommonMessage("DF001_01"));

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