
var fnObj = {};
var parentsData;
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {

        var codes = axboot.commonCodeFilter("CD138").codeArr;
        var names = axboot.commonCodeFilter("CD138").nameArr;
        var state = undefined;
        for(var i = 0; i < names.length; i++)
        {
            if(names[i] == "Confirm")
            {
                state = codes[i];
                break;
            }
        }

        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st004/01/list03",
            async : false,
            data: $.extend({}, {pageSize: 1000},this.formView.getData(),{statusUuid: state},{useYn: 'Y'},{containerName : data.containerName, containerTypeUuid : data.containerTypeUuid}),
            callback: function (res) {
                fnObj.gridView03.resetCurrent();
                fnObj.gridView03.setData(res.list);
                //fnObj.gridView01.disabledColumn();
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },

    ERROR_SEARCH: function (caller, act, data) {
    },
    PAGE_FIND: function (caller, act, data) {

    },
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.callback(data);
        }
    },
    PAGE_ARRANGE: function (caller, act, data) {
       if(fnObj.gridView04.getData().length  < 1){
            alert("Select Arrange Item List")
            return
        }
        for(var i=0;i<fnObj.gridView04.getData().length;i++){
            fnObj.gridView04.gridObj.setValue(i, "locationUuid", parentsData.locationUuid);
        }
        axboot.ajax({
            type: "PUT",
            url: "/api/v1/st/st004/02/save",
            data: JSON.stringify(fnObj.gridView04.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    SEARCH_CONTAINER_SCH: function(caller, act, data){
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='parentContainerName']").val(data["CONTAINER_NAME"])
                $("input[data-ax-path='parentContainerName']").attr("parentContainerName",data["CONTAINER_NAME"])
                parentContainerUuid = data['CONTAINER_UUID'];
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,data);
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
    parentsData = parent.axboot.modal.getData();
    $.ajax({
        url: "/assets/js/controller/simple_controller.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/st00402_p01_01.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView03.initView();
    _this.gridView04.initView();
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
    },
    initEvent: function () {
        var _this = this;

        $("input[data-ax-path='repositoryName']").val(parentsData.repositoryName);
        $("input[data-ax-path='shelfName']").val(parentsData.shelfName);
        $("input[data-ax-path='rowNo']").val(parentsData.rowNo);
        $("input[data-ax-path='columnNo']").val(parentsData.columnNo);

        $("input[data-ax-path='parentContainerName']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU135",
                searchData : $("input[data-ax-path='parentContainerName']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_CONTAINER_SCH,data);
        });

        $("input[data-ax-path='parentContainerName']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU135",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_CONTAINER_SCH,data);
            }

        });


        $(".close_popup").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
        });

        $(".btn_exclude").click(function(){
            exportItemList();
        });
        $(".btn_include").click(function(){
            importItemList();
        });


        $('#findBtn').click(function(){

            var containerName = $('#containerName').val();
            var containerTypeUuid = $("select[data-ax-path='containerTypeUuid'] option:checked").val();

            var data = {
                "containerName" : containerName,
                "containerTypeUuid" : containerTypeUuid
            }

            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,data);
        });

        $(".btn_arrange").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_ARRANGE);
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

fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid3",
    entityName : "ST_CONTAINER",
    primaryKey : "containerUuid",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00402_p01_01.column_info);
        this.gridObj.setFixedOptions({
            colCount: 3
        });
        this.gridObj.setOption({
            checkBar: {visible: true},
            indicator: {visible: true}
        })
        this.makeGrid();
        //this.removeRowBeforeEvent(this.cancelDelete);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
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
    }
});

fnObj.gridView04 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid4",
    entityName : "ST_CONTAINER",
    primaryKey : "containerUuid",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00402_p01_01.column_info);
        this.gridObj.setFixedOptions({
            colCount: 3
        });
        this.gridObj.setOption({
            checkBar: {visible: true},
            indicator: {visible: true}
        })
        this.makeGrid();
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
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
    getData: function () {
        return this.gridObj.getJsonRows();
    }
});

/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}
 */
exportItemList = function() {
    if(fnObj.gridView03.gridObj.getCheckedList().length > 0){
        fnObj.gridView04.setData(fnObj.gridView03.gridObj.getCheckedList());

    }else{
        alert("Select Arrange Item List")
    }
}
importItemList = function(){
    fnObj.gridView04.gridObj.dataProvider.removeRow(0);
}