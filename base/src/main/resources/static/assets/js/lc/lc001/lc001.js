var fnObj = {};
var gridView = undefined;
var gridIndex01 = 0;
var gridIndex02 = 0;

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        var _this = this;
        axboot.ajax({
            type: "GET",
            url: "/api/v1/lc/lc001/01/list",
            data: $.extend({},fnObj.formView.getData()),
            async : false,
            callback: function (res) {
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.setData(res.list);
                ACTIONS.dispatch(ACTIONS.GET_LC_DETAIL);

            }
        });
    },
    GET_LC_DETAIL : function(caller, act, data)
    {
        if(fnObj.gridView01.getCurrentData() == undefined) return false
        axboot.ajax({
            type: "GET",
            url: "/api/v1/lc/lc001/02/list",
            data: $.extend({},{leadCaseUuid : fnObj.gridView01.getCurrentData().leadCaseUuid}),
            async : false,
            callback: function (res) {
                gridIndex02 = 0;
                fnObj.gridView02.resetCurrent();
                fnObj.gridView02.setData(res.list);
                if(res.list != undefined && res.list.length > 0){
                    fnObj.formView.setFormData("collectionContents",res.list[gridIndex02].collectionContents);
                }
            }
        });
    },
    PAGE_SAVE_DETAIL : function(caller, act, data)
    {
        if(fnObj.gridView01.getCurrentData() == undefined) return false
        axboot.ajax({
            type: "PUT",
            url: "/api/v1/lc/lc001/02/save",
            data: JSON.stringify(fnObj.gridView02.getData()),
            async : false,
            callback: function (res) {
                fnObj.gridView02.resetCurrent();
                fnObj.gridView02.setData(res.list);
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });
    },
    PAGE_SAVE : function(caller, act, data)
    {
        if(!fnObj.gridView01.gridObj.validate()){
            return false;
        }

        var _this = this;
        axboot.ajax({
            type: "PUT",
            url: "/api/v1/lc/lc001/01/save",
            data: JSON.stringify(fnObj.gridView01.getData()),
            async : false,
            callback: function (res) {
                fnObj.gridView01.resetCurrent();
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE_DETAIL);
            }
        });
    },
    FORM_CLEAR: function (caller, act, data) {
    },
    CLOSE_TAB : function()
    {
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
fnObj = {
    pageStart : function () {
        var _this = this;
        $.ajax({
            url: "/assets/js/column_info/lc00101.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/column_info/lc00102.js",
            dataType: "script",
            async : false,
            success: function(){}
        });

        /**/
        _this.formView.initView();
        _this.gridView01.initView();
        _this.gridView02.initView();

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,this.formView.getData());


    }
};
/*검색 창*/
fnObj.formView = axboot.viewExtend(axboot.formView,{
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData);
    },
    initView : function(){
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();
    },

    initEvent: function () {

        $("textarea[data-ax-path='ownMaterial']").change(function(data){
            fnObj.gridView01.gridObj.setValue(gridIndex01,"ownMaterial",fnObj.formView.getData().ownMaterial);
        });
        $("textarea[data-ax-path='ownMaterial']").focusin(function(data){
            fnObj.gridView01.gridObj.commit();
        });

        $("textarea[data-ax-path='collectionContents']").change(function(data){
            fnObj.gridView02.gridObj.setValue(gridIndex02,"collectionContents",fnObj.formView.getData().collectionContents);
        });
        $("textarea[data-ax-path='collectionContents']").focusin(function(data){
            fnObj.gridView02.gridObj.commit();
        });
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        console.log($.extend({}, data));
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
    primaryKey : "leadCaseUuid",
    entityName : "LC_LEAD_CASE",
    initView  : function()
    {
        var _this = this;
        this.initInstance();
        this.setColumnInfo(lc00101.column_info);
        this.makeGrid();
        this.gridObj.itemClick(function(data){
            fnObj.gridView02.gridObj.commit();
            gridIndex01 = fnObj.gridView01.gridObj.getCurrent().itemIndex;
            fnObj.formView.setFormData("ownMaterial", data.ownMaterial);
            ACTIONS.dispatch(ACTIONS.GET_LC_DETAIL);
        });
        this.setFixedOptions({
            colCount : 2
        });
        this.gridObj.addRowBeforeEvent(function () {
           fnObj.gridView02.resetCurrent();
        });
        this.removeRowBeforeEvent(this.cancelDelete);
        /**
         *
         * Single Pasted시 데이터에 대한 영향이 간다
         */

    },
    cancelDelete : function() {
        for(var i=0; i < fnObj.gridView01.gridObj.getSelectionData().length; i++){
            if (fnObj.gridView01.gridObj.getSelectionData()[i].childCnt > 0) {
                this.setRunDel(false);
                return;
            }
        }
        this.setRunDel(true);
    },
    clearChild : function()
    {
        fnObj.gridView02.gridObj.gridView.cancel();
        fnObj.gridView02.clearData();
    }
 });
/*팝업 디테일 ( Column )*/
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid02",
    entityName : "LC_LEAD_CASE_SCHEDULE",
    primaryKey : "leadCaseScheduleUuid",
    parentsUuidFieldName : "leadCaseUuid",
    parentsGrid : fnObj.gridView01,
    initView: function ()
    {
        this.initInstance();
        this.setColumnInfo(lc00102.column_info);
        this.makeGrid();
        this.setFixedOptions({
            colCount : 2
        });

        this.gridObj.itemClick(function(data){
            gridIndex02 = fnObj.gridView02.gridObj.getCurrent().itemIndex;
            fnObj.formView.setFormData("collectionContents", data.collectionContents);
        });
    },

    clear : function () {
        this.gridObj.setData("set",[]);
    },

    /*
    addRowBeforeEvent : function()
    {
        var data = fnObj.gridView02.gridObj.getDefaultData();
        data[1] = fnObj.gridView01.getCodeHeaderUUID();
        fnObj.gridView02.gridObj.setDefaultData(data);
    },
    onRowsPasted : function(grid, items)
    {
        var data = undefined;
        for(var i = 0; i < items.length; i++)
        {
            fnObj.gridView02.gridObj.setValue(items[i],1,fnObj.gridView01.getCodeHeaderUUID());
        }
    }
    */
});