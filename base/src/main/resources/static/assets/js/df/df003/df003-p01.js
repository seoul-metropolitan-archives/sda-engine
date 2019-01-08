
var fnObj = {};
var parentsData;

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH_TREE: function (caller, act, data) {
        axboot.ajax({ //트리리스트조회
            type: "GET",
            url: "/api/v1/df/df003/freeze/aggregationTree",
            async : false,
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
    PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({//그리드리스트조회
            type: "GET",
            url: parentsData.listService,
            async : false,
            data: $.extend({}, {pageSize: 10000},{aggregationUuid: data.aggregationUuid, disposalFreezeEventUuid:parentsData.disposalFreezeEventUuid, disposalFreezeDegreeUuid:parentsData.disposalFreezeDegreeUuid}),
            callback: function (res) {
                fnObj.gridView02.resetCurrent();
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
    PAGE_CONFIRM: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE,CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE,CANCEL_STATUS);
    },
    PAGE_SAVE: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE);
        // ACTIONS.dispatch(ACTIONS.TOP_GRID_DETAIL_PAGE_SAVE);
    },
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.callback(data);
        }
    },
    PAGE_ARRANGE: function (caller, act, data) {
        if(fnObj.gridView03.getData().length  < 1){
            alert("Select Arrange Item List")
            return
        }
        for(var i=0;i<fnObj.gridView03.getData().length;i++){
            fnObj.gridView03.gridObj.setValue(i, "disposalFreezeEventUuid", parentsData.disposalFreezeEventUuid)
            fnObj.gridView03.gridObj.setValue(i, "disposalFreezeDegreeUuid", parentsData.disposalFreezeDegreeUuid)
        }
        axboot.ajax({
            type: "PUT",
            url: parentsData.saveService,
            data: JSON.stringify(fnObj.gridView03.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_CLOSE,{disposalFreezeEventUuid:parentsData.disposalFreezeEventUuid, disposalFreezeDegreeUuid:parentsData.disposalFreezeDegreeUuid});
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
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
    parentsData = parent.axboot.modal.getData();
    $.ajax({
        url: "/assets/js/controller/simple_controller.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/df00301_p01_01.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/df00301_p01_02.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();
    _this.gridView03.initView();
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH_TREE, this.formView.getData());
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

        $(".btn_main_txt01").text(parentsData.confirmBtn);

        $(".sltCont").text(parentsData.crrntAgg);

        $(".open_close.expendAll").click(function(){
            _this.gridObj.expandAll();
        });
        $(".open_close.collapseAll").click(function(){
            _this.gridObj.collapseAll();
        });
        $(".btn_exclude").click(function(){
            exportItemList();
        });
        $(".btn_include").click(function(){
            importItemList();
        });
        $(".btn_arrange").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_ARRANGE);
        });

        $(".close_popup").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
        });

        $(".bdb").delegate("#rg_tree_allopen", "click", function () {
            _this.expandAll();
        });
        $(".bdb").delegate("#rg_tree_allclose", "click", function () {
            _this.collapseAll();
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
    },
    expandAll:function(){
        fnObj.gridView01.gridObj.expandAll();
    },
    collapseAll:function() {
        fnObj.gridView01.gridObj.collapseAll();
    }
});

fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid01",

    initView: function () {
        this.gridObj = new TreeGridWrapper("realgrid01", "/assets/js/libs/realgrid", true);
        this.gridObj.setGridStyle("100%", "100%")
            .setOption({
                footer:{visible:false},
                header: { visible: false },
                checkBar: {visible: false},
                indicator: {visible: false},
                stateBar:{visible:false},
                checkBox:{visible:true},
            })
        this.gridObj.setColumnInfo(df00301_p01_01.column_info).makeGrid();

        this.gridObj.setDisplayOptions({
            fitStyle:"evenFill"
        });
        this.gridObj.itemClick(this.itemClick);
        this.gridObj.onItemChecked(this.onItemChecked);
        this.bindEvent();
    },
    bindEvent : function()
    {
        var _this = this;
    },
    setData: function (list) {
        this.gridObj.setTreeDataForArray(list, "orderKey1");
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

fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid02",
    initView: function () {
        this.initInstance();

        this.entityName = parentsData.primarykey;
        this.primarykey = parentsData.primarykey;

        this.setColumnInfo(df00301_p01_02.column_info);
        this.gridObj.setFixedOptions({
            colCount: 3
        });
        this.gridObj.setOption({
            checkBar: {visible: true},
            indicator: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        this.gridObj.onItemChecked(this.onItemChecked)
    },
    isChangeData: function () {
        if (this.getData().length > 0) {
            return true;
        } else {
            return false;
        }
    },
    getRowData: function (){
        return this.gridObj.getSelectedData();
    },
    itemClick: function (data, index) {

    },
    onItemChecked: function(grid, itemIndex, checked){
        // alert("adfaf");
    }

});

fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid03",
    initView: function () {
        this.initInstance();

        this.entityName = parentsData.primarykey;
        this.primarykey = parentsData.primarykey;

        this.setColumnInfo(df00301_p01_02.column_info);
        this.gridObj.setFixedOptions({
            colCount: 3
        });
        this.gridObj.setOption({
            checkBar: {visible: true},
            indicator: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    isChangeData: function () {
        if (this.getData().length > 0) {
            return true;
        } else {
            return false;
        }
    },
    getRowData: function (){
        return this.gridObj.getSelectedData();
    },
    itemClick: function (data, index) {
    },
    getData: function () {
        return this.gridObj.getJsonRows();
    },
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
exportItemList = function() {
    if(fnObj.gridView02.gridObj.getCheckedList().length > 0){
        //gridView03 목록에 추가
        fnObj.gridView03.setData(fnObj.gridView02.gridObj.getCheckedList());
        // fnObj.gridView02.gridObj.onItemChecked()
        // var getCheckedRows = fnObj.gridView02.gridObj.getCheckedRows();
        // fnObj.gridView02.gridObj.setCheckable(0,false);
        // fnObj.gridView02.gridObj.getcheck
        // fnObj.gridView02.gridObj.setCustomCellStyleRows("disable",function(row){
        //
        //     if(row["statusUuid"] == state)
        //         return true;
        //     else
        //         return false;
        // },["containerName","parentContainerName","containerTypeUuid","controlNumber","provenance","creationStartDate","creationEndDate","description","orderNo"]);
    }else{
        alert("Select Arrange Item List")
    }
}
importItemList = function(){
    alert("import");
}

setCheckable = function(rows){
    // for()
}
