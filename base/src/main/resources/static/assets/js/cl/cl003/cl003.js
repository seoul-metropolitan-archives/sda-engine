var fnObj = {};
var selectedItem ; //선택된 그리드 아이템
var classificationSchemeUuid = "";
var crntClassUuid = "";
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";
var isLeaf =  "0";
var gridData = [];
var deletingList =  [];
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1);
    },
    PAGE_SEARCH2: function (caller, act, data) {
        if(classificationSchemeUuid =='' || classificationSchemeUuid == null) return;
        axboot.ajax({ //트리 리스트
            type: "GET",
            url: "/api/v1/cl002/03/getClassHierarchyList",
            async : false,
            data: $.extend({}, {pageSize: 1000}, {classificationSchemeUuid:classificationSchemeUuid,className:"cl003"}),
            callback: function (res) {
                classList = ax5.util.deepCopy(res.list);
                fnObj.gridView01.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({ //트리 리스트
            type: "GET",
            url: "/api/v1/cl/cl003/01/list01",
            data: $.extend({}, {pageSize: 10000}, {classUuid:crntClassUuid,statusUuid:fnObj.formView.getFormData('statusUuid')}),
            callback: function (res) {
                fnObj.gridView03.clearData();
                gridData = [];
                gridData = ax5.util.deepCopy(res.list);
                fnObj.gridView03.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },

    STATUS_UPDATE: function (caller, act, data) {

        var rows = fnObj.gridView03.gridObj.getCheckedList();
        if(!rows || rows.length < 1) return;

        //var items = fnObj.gridView03.gridObj.gridView.getCheckedItems();
        var _checkedAllList = [];

        var convertList = function (items) {
            items.forEach(function(n){
                _checkedAllList.push(fnObj.gridView03.gridObj.getDataProvider().getJsonRow(n));
                if(fnObj.gridView03.gridObj.getDataProvider().getChildren(n)){
                    convertList(fnObj.gridView03.gridObj.getDataProvider().getChildren(n));
                }
            })
            return _checkedAllList;
        };

        var checkedAllList = convertList(fnObj.gridView03.gridObj.gridView.getCheckedRows());


        var params = checkedAllList.filter(function (item) {
            item.changeStatus = data;
            return item.classifyRecordsResultUuid !== "";
        });

        /*var params = gridData.filter(function (item) {
            item.changeStatus = data;
            var inCnt = 0;
            rows.forEach(function(n,nidx){
                if(item["orderKey1"].toString().indexOf(n["orderKey1"])!== -1) inCnt++;
            })
            return inCnt > 0 ? true : false;
        });*/

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/cl/cl003/02/confirm",
            data: JSON.stringify(params),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1);
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
        var result = false;
        axboot.call({
            type: "PUT",
            url: "/api/v1/cl/cl003/02/save",
            data: JSON.stringify({cl00301VOList:deletingList}),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1);
            }
        })
            .done(function () {
                deletingList = [];
                axToast.push("저장 작업이 완료되었습니다.");
            });
        return result;
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    PAGE_CLASSIFY: function (caller, act, data) {
        if(crntClassUuid == "" || isLeaf != "1"){
            // alert("Select Class");
            return
        }

        // if(isLeaf != "1") return;

        axboot.modal.open({
            modalType: "CLASSIFY_POPUP",
            header: {
                title: "CLASSIFY - " + fnObj.gridView01.gridObj.getSelectedData().classTreeName
            },
            sendData: function () {
                return {
                    classUuid : crntClassUuid,
                    description : fnObj.gridView01.gridObj.getSelectedData().description
                };
            },
            callback: function (data) {
                if(this) this.close();
                if(data){
                    crntClassUuid = data.classUuid
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,data);
                }
            }
        });
    },
    SEARCH_CLASS_SCH : function(caller, act, data)
    {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='classificationCode']").val(data["CLASSIFICATION_NAME"])
                $("input[data-ax-path='classificationCode']").attr("classificationCode",data["CLASSIFICATION_CODE"])
                classificationSchemeUuid = data['CLASSIFICATION_SCHEME_UUID'];
                selectedTreeItem = {orderKey:"", classTreeName:"",classificationSchemeUuid:"",orderNo:"",parentClassUuid:""};
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2,data);
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
        url: "/assets/js/column_info/cl00201.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/cl00302.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView03.initView();

    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2, this.formView.getData());
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
        $("input[data-ax-path='classificationCode']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU101",
                searchData : $("input[data-ax-path='classificationCode']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_CLASS_SCH,data);
        });
        $("input[data-ax-path='classificationCode']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU101",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_CLASS_SCH,data);
            }
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
    getFormData: function (dataPath) {
        return this.model.get(dataPath);
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
    uuidFieldName : "classificationSchemeUuid",
    entityName : "CL_CLASS",
    initView: function () {
        this.gridObj = new TreeGridWrapper("realgrid01", "/assets/js/libs/realgrid", true);
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
        if(data != null){
            crntClassUuid = data.classUuid;
            isLeaf = data.isLeaf;
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1);

        }
    },
    getData: function () {
        return this.gridObj.getData();
    },

});

fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid03",
    uuidFieldName : "classifyRecordsUuid",
    entityName: "CL_CLASSIFY_RECORDS_RESULT",
    initView: function () {
        this.gridObj = new TreeGridWrapper("realgrid03", "/assets/js/libs/realgrid", true);
        this.gridObj.setGridStyle("100%", "100%")
            .setOption({
                header: { visible: true },
                checkBar: {visible: true},
                indicator: {visible: true},
                lineVisible: false
            });
        this.gridObj.setColumnInfo(cl00302.column_info).makeGrid();
        this.gridObj.onItemChecked(this.onItemChecked);
        this.gridObj.setDisplayOptions({
            fitStyle:"evenFill"
        });
        // this.gridObj.gridView.setSelectOptions({
        //         style: "none"
        //     }
        // )
        this.gridObj.gridView.setCheckBar({
                checkableExpression: "values['choiceYn'] match 'Y'"
        });
        this.gridObj.gridView.applyCheckables();
        this.gridObj.itemClick(this.itemClick);
        this.removeRowBeforeEvent(this.cancelDelete);
    },
    setData: function (list) {
        this.gridObj.setTreeDataForArray(list, "orderKey1");
    },
    cancelDelete: function(){
        var index = fnObj.gridView03.gridObj.getCurrent().dataRow;
        var state = axboot.commonCodeValueByCodeName("CD111", "Confirm");
        this.setRunDel(false);
        if(fnObj.gridView03.gridObj.getSelectedData().statusUuid == state) {
            // axToast.push(axboot.getCommonMessage("AD011_02"));
            return;
        }else if(fnObj.gridView03.gridObj.getSelectedData().choiceYn == "N") {
            // axToast.push(axboot.getCommonMessage("AD011_02"));
            return;
        }else{
            fnObj.gridView03.gridObj.gridView.commit(true);

            if(fnObj.gridView03.gridObj.getDataProvider().getDescendants(index) != null){ //자식들이 있다면 추가
                var _deletingList = [];
                var convertList = function (items) {
                    items.forEach(function(n){
                        _deletingList.push(fnObj.gridView03.gridObj.getDataProvider().getJsonRow(n));
                        if(fnObj.gridView03.gridObj.getDataProvider().getChildren(n)){
                            convertList(fnObj.gridView03.gridObj.getDataProvider().getChildren(n));
                        }
                    })
                    return _deletingList;
                };

                deletingList = deletingList.concat(convertList(fnObj.gridView03.gridObj.gridView.getSelectedRows()));
            }else{
                deletingList.push(fnObj.gridView03.gridObj.getSelectedData());//최상위 노드 ADD
            }

            fnObj.gridView03.gridObj.getDataProvider().removeRows(fnObj.gridView03.gridObj.gridView.getSelectedRows(), false);
            // if(fnObj.gridView03.gridObj.getDataProvider().getDescendants(index) != null){
            //     fnObj.gridView03.gridObj.getDataProvider().removeRows(fnObj.gridView03.gridObj.getDataProvider().getDescendants(index), false);
            //
            // }
            fnObj.gridView03.gridObj.dispatch("onRemoveRow");
        }
    },
    getData : function(){

    },
    itemClick: function (data, index) {
        //시조찾기
        // var sel = {startItem:-1,endItem:-1,style:"rows"}
        // if(fnObj.gridView03.gridObj.getDataProvider().getAncestors(index.dataRow).length == 0) {
        //     var descendants = fnObj.gridView03.gridObj.getDataProvider().getDescendants(index.dataRow)
        //     if(descendants.length > 0){
        //         //시조 아이템
        //         sel.startItem = index.itemIndex;
        //         sel.endItem = descendants[descendants.length-1]-1;
        //     }else{
        //         //독립적 아이템
        //         sel.endItem = sel.startItem = index.itemIndex;
        //     }
        // }else{
        //
        // }
        // fnObj.gridView03.gridObj.gridView.setSelection(sel);
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