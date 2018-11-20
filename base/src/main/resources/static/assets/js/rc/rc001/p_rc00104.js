var fnObj = {};
var sendData = null;
var aggregationUuid = "";
var nodeType = "";

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.callback();
            parent.axboot.modal.close();
        }
    },
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/cl001/01/list",
            data: $.extend({}, {pageSize: 1000, sort: "classificationCode"}, fnObj.formView.getData()),
            callback: function (res) {
                if(res.list == null || res.list.length <= 0){
                    fnObj.gridView01.setData([]);
                    return;
                }
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.resetCurrent();
                //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH1: function (caller, act, data) {
        //if(classificationSchemeUuid =='' || classificationSchemeUuid == null) return;
        axboot.ajax({ //트리 리스트
            type: "GET",
            url: "/api/v1/cl002/03/getClassHierarchyList",
            async : false,
            data: $.extend({}, {pageSize: 1000},
                {
                    classificationSchemeUuid:fnObj.gridView01.getSelectedData()[0]["classificationSchemeUuid"],
                    aggregationUuid : aggregationUuid,
                    className:"cl003"
                }),
            callback: function (res) {
                classList = ax5.util.deepCopy(res.list);
                fnObj.gridView02.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_CLASSIFY: function (caller, act, data) {
        var selectedData = fnObj.gridView02.getSelectedData();

        if(!selectedData || selectedData.isLeaf != "1"){
            axDialog.alert({
                title: 'Classify',
                theme: "default",
                msg: "최하위 Class를 선택하세요."
            });
            return;
        }

        if(selectedData.classifyCount > 0){
            axDialog.alert({
                title: 'Classify',
                theme: "default",
                msg: "해당 Class에 등록되어있는 Aggregation입니다.\n다른 Class를 선택하세요."
            });
            return;
        }

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/cl/cl003/02/save",
            data: JSON.stringify({classUuid:selectedData.classUuid,cl00301VOList:sendData}),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
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

var fnObj = {
    pageStart: function () {

        $.ajax({
            url: "/assets/js/column_info/rc00104_p04.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/column_info/cl00201.js",
            dataType: "script",
            async: false,
            success: function () {
            }
        });

        fnObj.popupView.initView(parent.axboot.modal.getData());
        fnObj.formView.initView();
        fnObj.gridView01.initView();
        fnObj.gridView02.initView();

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    }
};

fnObj.popupView = axboot.viewExtend({
    initView : function(data){
        console.log(data);

        aggregationUuid = data.aggregationUuid;
        sendData = data.classifyList;

        this.initEvent();
    },
    initEvent : function()
    {
        var _this = this;
        $("#close").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE)
        });
    }
});

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
});


fnObj.gridView01 = axboot.viewExtend(axboot.gridView,{
    tagId : "realgrid01",
    uuidFieldName : "classificationSchemeUuid",
    entityName : "CL_CLASSIFICATION_SCHEME",
    noPermission : true,
    initView  : function()
    {
        this.initInstance();
        this.setColumnInfo(rc00104_p04["column_info"]);
        this.makeGrid();

        this.gridObj.itemClick(this.itemClick);
    },
    itemClick : function(data){
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1);
    },
    getSelectedData : function()
    {
        return this.gridObj.getSelectionData();
    },
    validate: function () {
        var rs = this.model.validate();
        if (rs.error) {
            alert(rs.error[0].jquery.attr("title") + '을(를) 입력해주세요.');
            rs.error[0].jquery.focus();
            return false;
        }
        return true;
    }
});

fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid02",
    uuidFieldName : "classificationSchemeUuid",
    entityName : "ClassName",
    initView: function () {
        this.gridObj = new TreeGridWrapper(this.tagId, "/assets/js/libs/realgrid", true);
        this.gridObj.setGridStyle("100%", "100%")
            .setOption({
                footer:{visible:false},
                header: { visible: false },
                checkBar: {visible: false},
                indicator: {visible: false},
                stateBar:{visible:false},
                showAll: false,
            })
        this.gridObj.style.body = {
            borderRight: "#ccc,1px",
            borderBottom: "#ccc,1px",
            line: "#ffaaaaaa,0px"
        };
        this.gridObj.setColumnInfo(cl00201.column_info).makeGrid();

        this.gridObj.setDisplayOptions({
            fitStyle:"evenFill"
        });
        this.gridObj.itemClick(this.itemClick);
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
                _this.gridObj.search(["classTreeName"],$("#leftMenuParam").val())
            }
        });
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
        if(data && data.classifyCount > 0){
            axDialog.alert({
                title: 'Classify',
                theme: "default",
                msg: "해당 Class에 등록되어있는 Aggregation입니다.\n다른 Class를 선택하세요."
            });
        }
    },
    getData: function () {
        return this.gridObj.getData();
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
});