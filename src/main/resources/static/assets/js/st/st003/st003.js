
var fnObj = {};
var parentContainerUuid = "";
var selectedItem ; //선택된 그리드 아이템
var classList = new Object();
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";
var currentContainerUuid = "";

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        if(currentContainerUuid != "") {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,{containerUuid:currentContainerUuid});
        }
    },
    PAGE_SEARCH_TREE: function (caller, act, data) {
        axboot.ajax({ //트리리스트조회
            type: "GET",
            url: "/api/v1/st/st002/01/list01",
            async : false,
            data: $.extend({}, {pageSize: 1000}),
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
            url: "/api/v1/st/st003/01/list01",
            async : false,
            data: $.extend({}, {pageSize: 1000},this.formView.getData(),{containerUuid: data.containerUuid}),
            callback: function (res) {
                fnObj.gridView02.resetCurrent();
                fnObj.gridView02.setData(res.list);
                fnObj.formView.setFormData("itInAggregationName", "");
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2,data)
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH2: function (caller, act, data) {//트리에서 선택된 항목
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st003/01/list02",
            data: $.extend({}, {pageSize: 1000},this.formView.getData(),{containerUuid: data.containerUuid}),
            callback: function (res) {
                fnObj.gridView03.resetCurrent();
                fnObj.gridView03.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    STATUS_UPDATE: function (caller, act, data) {

        var checkedCnt01 = fnObj.gridView02.gridObj.getCheckedList().length;
        var checkedCnt02 = fnObj.gridView03.gridObj.getCheckedList().length;

        var rows = fnObj.gridView02.gridObj.getCheckedList();
        rows = rows.concat(fnObj.gridView03.gridObj.getCheckedList());

        if(!rows || rows.length < 1) return;

        var params = rows.filter(function (item) {
            item.changeStatus = data;
            return item.arrangeRecordsResultUuid !== "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/st/st003/02/confirm",
            data: JSON.stringify(params),
            callback: function (res) {
                if(checkedCnt01 > 0) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,{containerUuid:currentContainerUuid});
                }
                if(checkedCnt02 > 0) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2,{containerUuid:currentContainerUuid});
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
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
    TOP_GRID_SAVE: function (caller, act, data) {
        var lists = fnObj.gridView02.gridObj.getData();
        lists = lists.concat(fnObj.gridView03.gridObj.getData());
        var result = false;

        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st003/03/save",
            data: JSON.stringify(lists),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        })
            .done(function () {
                axToast.push("저장 작업이 완료되었습니다.");
            });
        return result;
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    PAGE_ARRANGE: function (caller, act, data) {
        if(fnObj.formView.getData().aggInContainerName == "" ||fnObj.formView.getData().aggInContainerName == undefined ){
            return
        }
        axboot.modal.open({
            modalType: "ARRANGE_POPUP",
            width: 1600,
            height: 800,
            header: {
                title: "ARRANGE"
            },
            sendData: function () {
                return {
                    confirmBtn:"Arrange",
                    crrntAgg: fnObj.formView.getData().aggInContainerName,
                    containerUuid :  currentContainerUuid
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
        url: "/assets/js/column_info/st00201.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/st00301.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/st00302.js",
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

        $("input[data-ax-path='containerName']").focusout(function(){

            if("" == $(this).val().trim())
            {
                $("input[data-ax-path='containerName']").val("");
            }
        });
        $("input[data-ax-path='provenance']").focusout(function(){

            if("" == $(this).val().trim())
            {
                $("input[data-ax-path='provenance']").val("");
            }
        });

        $("input[data-ax-path='containerName'],input[data-ax-path='provenance'] ,input[data-ax-path='controlNumber']").keyup(function(){
            if(13 == event.keyCode)
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        });
        $("select[data-ax-path='statusUuid'], select[data-ax-path='containerTypeUuid']").change(function() {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
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
                stateBar:{visible:false}
            })
        this.gridObj.setColumnInfo(st00201.column_info).makeGrid();

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
                _this.gridObj.search(["containerName"],$("#leftMenuParam").val())
            }
        });
        $(".btn_arrange").click(function(){
            ACTIONS.dispatch(ACTIONS.MODAL_OPEN);
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
        if(data != null){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
            fnObj.gridView03.clearData();
            fnObj.formView.setFormData("aggInContainerName", data.containerTreeName);
            fnObj.formView.setFormData("itInAggregationName", "");
            currentContainerUuid = data.containerUuid;
        }

    },
    getData: function () {
        return this.gridObj.getData();
    },

});

fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid02",
    entityName : "arrangeRecordsResultUuid",
    primaryKey : "arrangeRecordsResultUuid",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00301.column_info);
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
        // if(data != null){
        //     ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2, data);
        //     fnObj.formView.setFormData("itInAggregationName", data.title);
        //     currentContainerUuid = data.containerUuid;
        // }

    }
});

fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid03",
    entityName : "containerUuid",
    primaryKey : "containerUuid",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00302.column_info);
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


