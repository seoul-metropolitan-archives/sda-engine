
var fnObj = {};
var classificationSchemeUuid = "";
var selectedItem ; //선택된 그리드 아이템
var classList = new Object();
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";
var selectedTreeItem = {orderKey:"", classTreeName:"",classificationSchemeUuid:"",orderNo:"",parentClassUuid:""}; //선택된 트리 아이템
var isDetailChanged = false;

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH0: function (caller, act, data){ //초기조회, Inquiry
        axboot.ajax({
            type: "GET",
            url: "/api/v1/cl002/01/getClassificationScheme",
            async : false,
            data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
            callback: function (res) {
                if(Object.keys(res).length < 1) return;

                $("input[data-ax-path='classificationCode']").attr("classificationCode",res.map.classificationCode);
                fnObj.formView.setFormData("classificationCode",res.map.classificationName);
                fnObj.formView.setFormData("classificationName",res.map.classificationName);
                fnObj.gridView02.defaultParentsUuid = classificationSchemeUuid = res.map.classificationSchemeUuid;
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,res.map);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1);
    },
    PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({ //트리 리스트
             type: "GET",
             url: "/api/v1/cl002/03/getClassHierarchyList",
             async : false,
             data: $.extend({}, {pageSize: 1000}, {classificationSchemeUuid:classificationSchemeUuid,className:"cl002"}),
             callback: function (res) {
                classList = ax5.util.deepCopy(res.list);
                ACTIONS.dispatch(fnObj.gridView01.setData(res.list));
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2);
             },
             options: {
                 onError: axboot.viewError
             }
        });
        return false;
    },
    PAGE_SEARCH2: function (caller, act, data) {//트리에서 선택된 항목
        if(selectedTreeItem["classificationSchemeUuid"] == ""){
            selectedTreeItem["classificationSchemeUuid"] = classificationSchemeUuid;
        }
        axboot.ajax({
            type: "GET",
            url: "/api/v1/cl002/04/getSelectedClassList",
            async: true,
            data: $.extend({},  {pageSize: 1000},selectedTreeItem ,this.formView.getData()),
            callback: function (res) {
                setDefaultClassDetails();

                if(res.list && res.list.length < 1)
                {
                    fnObj.gridView02.setData([]);
                    fnObj.gridView02.disabledColumn();
                    return ;
                }

                fnObj.gridView02.setData(res.list);
                fnObj.gridView02.disabledColumn();
                selectedItem = fnObj.gridView02.getRowData();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH3, fnObj.gridView02.getRowData());
                if(isDetailChanged == true){
                    isDetailChanged = false;
                }

            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH3: function (caller, act, data) { //상세
        if(data["classUuid"] == "undefined" || data["classUuid"] == "" ){
            setDefaultClassDetails();
            return;
        }

        axboot.ajax({
            type: "GET",
            url: "/api/v1/cl002/05/getSelectedClassDetail",
            async: true,
            data: $.extend({}, {pageSize: 1000}, data),
            callback: function (res) {
                fnObj.formView.setFormData("detailClassName",selectedItem.className);
                fnObj.formView.setFormData("detailPath",selectedItem.path);
                if(selectedItem.classLevelUuid != "undefined"){
                    fnObj.formView.setFormData("detailClassLevel",axboot.commonCodeFilter("CD114").nameArr[axboot.commonCodeFilter("CD114").codeArr.indexOf(selectedItem.classLevelUuid)]);
                }
                fnObj.formView.setFormData("rulesConversionUuid",res.rulesConversionUuid);
                fnObj.formView.setFormData("description",res.description);
                fnObj.formView.setFormData("statusDescription",res.statusDescription);
                fnObj.formView.setFormData("levelOfDetailUuid",res.levelOfDetailUuid);
                fnObj.formView.setFormData("creationStartDate",res.creationStartDate);
                fnObj.formView.setFormData("creationEndDate",res.creationEndDate);
                fnObj.formView.setFormData("accumulationStartDate",res.accumulationStartDate);
                fnObj.formView.setFormData("accumulationEndDate",res.accumulationEndDate);
                fnObj.formView.setFormData("scopeContent",res.scopeContent);
                isDetailChanged = false;
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    STATUS_UPATE: function (caller, act, data) {

    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    PAGE_CONFIRM: function (caller, act, data) {
        var rows = fnObj.gridView02.gridObj.getCheckedList();

        if(!rows || rows.length < 1) return;

        var params = rows.filter(function (item) {
            item.changeStatus = CONFIRM_STATUS;
            return item.classUuid !== "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/cl002/06/updateStatusConfirm",
            data: JSON.stringify(params),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_CANCEL: function (caller, act, data) {
        var rows = fnObj.gridView02.gridObj.getCheckedList();

        if(!rows || rows.length < 1) return;

        var params = rows.filter(function (item) {
            item.changeStatus = CANCEL_STATUS;
            return item.classUuid !== "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/cl002/07/updateStatusCancel",
            data: JSON.stringify(params),
            callback: function (res) {
                if(res.message == "CLASSIFIED"){
                    alert("상태변경 실패!  연결된 Aggregation 이 존재합니다.")
                }
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SAVE: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE);
        // ACTIONS.dispatch(ACTIONS.TOP_GRID_DETAIL_PAGE_SAVE);
    },
    TOP_GRID_SAVE: function (caller, act, data) {
        var result = false;
        axboot.call({
            type: "PUT",
            url: "/api/v1/cl002/08/updateClassList",
            data: JSON.stringify(this.gridView02.getData()),
            callback: function (res) {
                if(isDetailChanged){
                    fnObj.gridView02.commit();
                    ACTIONS.dispatch(ACTIONS.TOP_GRID_DETAIL_PAGE_SAVE);
                }else{
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                }
                result = true;
            }
        })
            .done(function () {
                axToast.push("저장 작업이 완료되었습니다.");
            });
        return result;
    },
    TOP_GRID_DETAIL_PAGE_SAVE: function () {
        axboot.ajax({
            type: "PUT",
            url: "/api/v1/cl002/09/updateClassCon",
            data: JSON.stringify($.extend({}, selectedItem, this.formView.getData())),
            callback: function (res) {
                isDetailChanged = false;
                // ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            options: {
                onError: axboot.viewError
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
                fnObj.gridView02.defaultParentsUuid = classificationSchemeUuid = data['CLASSIFICATION_SCHEME_UUID'];
                selectedTreeItem = {orderKey:"", classTreeName:"",classificationSchemeUuid:"",orderNo:"",parentClassUuid:""};
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,data);
            }
        });
    },
    SEARCH_CLASS_CCD : function(caller, act, data)
    {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                fnObj.formView.setFormData("parentClassCode",data["CLASS_CODE"]);

               /* $("input[data-ax-path='parentClassCode']").val(data["CLASS_CODE"])
                $("input[data-ax-path='parentClassCode']").attr("parentClassCode",data["CLASS_CODE"])*/
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2);
            }
        });
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
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
        url: "/assets/js/column_info/cl00202.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();
    // ACTIONS.dispatch(ACTIONS.PAGE_SEARCH0, this.formView.getData());
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

        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            config: {
                pattern: 'data'
            },
            content: {
                type: 'date',
                formatter: {
                    pattern: 'number'
                }
            },



        });
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
        /*
        $("input[data-ax-path='parentClassCode']").parents().eq(1).find("a").click(function(){
                var data = {
                    popupCode : "PU115",
                    searchData : $("input[data-ax-path='parentClassCode']").val().trim(),
                    preSearch : false
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_CLASS_CCD,data);
        });

        */
        $("input[data-ax-path='parentClassCode']").focusout(function(){

            if("" == $(this).val().trim())
            {
                $("input[data-ax-path='parentClassName']").val("");
            }
        });
        $("input[data-ax-path='classCodeForm']").focusout(function(){

            if("" == $(this).val().trim())
            {
                $("input[data-ax-path='className']").val("");
            }
        });

        $("textarea[data-ax-path='scopeContent'],textarea[data-ax-path='description'],input[data-ax-path='creationStartDate'],input[data-ax-path='creationEndDate'],input[data-ax-path='accumulationStartDate'],input[data-ax-path='accumulationEndDate']").keyup(function(){
            isDetailChanged = true;
        });
        $("textarea[data-ax-path='scopeContent'],textarea[data-ax-path='description'],input[data-ax-path='creationStartDate'],input[data-ax-path='creationEndDate'],input[data-ax-path='accumulationStartDate'],input[data-ax-path='accumulationEndDate']").change(function(){
            isDetailChanged = true;
        });

        $("select[data-ax-path='rulesConversionUuid'],select[data-ax-path='statusDescription'],select[data-ax-path='levelOfDetailUuid']").change(function(){
            isDetailChanged = true;
        });

        $("input[data-ax-path='parentClassName'],input[data-ax-path='classCodeForm'],input[data-ax-path='className'] ,input[data-ax-path='parentClassCode']").keyup(function(){
            if(13 == event.keyCode)
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
                stateBar:{visible:false}
            })
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
        if(selectedItem != null){
            if (fnObj.gridView02.isChangeData() == true ||isDetailChanged == true) {
                axDialog.confirm({
                    msg: axboot.getCommonMessage("AA006")
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    } else {
                        selectedTreeItem = data
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2);
                    }
                });
            } else {
                selectedTreeItem = data
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2);
            }
        }else{
            selectedTreeItem = data
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2);
        }

    },
    getData: function () {
        return this.gridObj.getData();
    },

});

fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid02",
    entityName : "CL_CLASS",
    uuidFieldName : "classUuid",
    parentsUuidFieldName : "classificationSchemeUuid",
    parentsGrid : fnObj.gridView01,
    defaultParentsUuid : classificationSchemeUuid,
    initView: function () {
        this.initInstance();
        this.setColumnInfo(cl00202.column_info);
        this.gridObj.setFixedOptions({
            colCount: 3
        });
        this.gridObj.setOption({
            checkBar: {visible: true},
            indicator: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        this.removeRowBeforeEvent(this.cancelDelete);
    },
    disabledColumn : function()
    {
        var codes = axboot.commonCodeFilter("CD113").codeArr;
        var names = axboot.commonCodeFilter("CD113").nameArr;
        var state = undefined;
        for(var i = 0; i < names.length; i++)
        {
            if(names[i] == "Confirm")
            {
                state = codes[i];
                break;
            }
        }
        this.gridObj.setCustomCellStyleRows("disable",function(row){

            if(row["statusUuid"] == state)
                return true;
            else
                return false;
        },["parentClassCode","classCode","className","classLevelUuid","orderNo","creationStartDate","creationEndDate","accumulationStartDate","accumulationEndDate"]);
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
    cancelDelete: function(){
        var state = axboot.commonCodeValueByCodeName("CD113", "Confirm");
        if(fnObj.gridView02.gridObj.getSelectedData().statusUuid == state){
            axToast.push(axboot.getCommonMessage("RS001_01"));
            this.setRunDel(false);
        }else{
            this.setRunDel(true);
        }
    },
    itemClick: function (data) {
        if(selectedItem.classUuid == data.classUuid) return;
        selectedItem = data;
        if (fnObj.formView.getData().detailClassName != null && fnObj.formView.getData().detailClassName != "") {

            if (isDetailChanged == true ) {
                axDialog.confirm({
                    msg: axboot.getCommonMessage("AA006")
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    } else {
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH3, data);
                    }
                });
            } else {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH3, data);
            }
        }else {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH3, data);
        }
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
setDefaultClassDetails = function(){
    fnObj.formView.setFormData("detailClassName",'');
    fnObj.formView.setFormData("detailPath",'');
    fnObj.formView.setFormData("detailClassLevel",'');
    fnObj.formView.setFormData("detailAddMetadata01",'');
    fnObj.formView.setFormData("detailAddMetadata02",'');
    fnObj.formView.setFormData("detailAddMetadata03",'');
    fnObj.formView.setFormData("detailAddMetadata04",'');
    fnObj.formView.setFormData("creationStartDate",'');
    fnObj.formView.setFormData("creationEndDate",'');
    fnObj.formView.setFormData("accumulationStartDate",'');
    fnObj.formView.setFormData("accumulationEndDate",'');
    isDetailChanged = false;
}

/*
getPreClassLevels = function(key) {
    var path = classList.filter(function (item) {
       return item.orderKey === key.path;
    });
    return path;
}*/
