var fnObj = {};
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";
var beforeData01 = null;
var beforeData02 = null;
var repositoryUuid;
var shelfUuid;
var locationUuid;
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        /*beforeData01 = null;
        beforeData02 = null;
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);*/
    },
    PAGE_SEARCH01: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st001/01/list01",
            data: $.extend({},  {repositoryCode : $("input[data-ax-path='repositoryCode']").val()},{ repositoryName : $("input[data-ax-path='repositoryName']").val()}),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
                //fnObj.gridView01.disabledColumn();
                fnObj.gridView02.clearData();
                fnObj.gridView03.clearData();
                fnObj.gridView04.clearData();


                if(fnObj.gridView01.gridObj.getJsonRows().length != 0){
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
                }


            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH02: function (caller, act, data) {
        if(fnObj.gridView01.getSelectedData() == null){
            return;
        }
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st001/01/list02",
            data: $.extend({}, {shelfCode : $("input[data-ax-path='shelfCode']").val()},{ shelfName : $("input[data-ax-path='shelfName']").val()},{repositoryUuid : fnObj.gridView01.getSelectedData().repositoryUuid}),
            callback: function (res) {
                fnObj.gridView02.setData(res.list);
                fnObj.gridView03.clearData();
                fnObj.gridView04.clearData();
                if(fnObj.gridView02.gridObj.getJsonRows().length != 0){
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
                }

            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH03: function (caller, act, data) {
        if(fnObj.gridView02.getSelectedData() == null){
            return;
        }
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st001/01/list03",
            data: $.extend({}, this.formView.getData(),{shelfUuid : fnObj.gridView02.getSelectedData().shelfUuid}),
            callback: function (res) {
                fnObj.gridView03.setData(res.list);
                fnObj.gridView04.clearData();
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH04: function (caller, act, data) {
        if(fnObj.gridView03.getSelectedData() == null){
            return;
        }
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st010/01/list04",
            data: $.extend({}, this.formView.getData(),{locationUuid : fnObj.gridView03.getSelectedData().locationUuid}),
            callback: function (res) {
                fnObj.gridView04.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    STATUS_UPDATE01: function (caller, act, data) {

        if (fnObj.gridView01.isChangeData() == true) {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA006")
            }, function () {
                if (this.key == "ok") {
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    return;
                }
            });
        }else{
            var rows = fnObj.gridView01.gridObj.getCheckedList();
            if (!rows || rows.length < 1) return;
            var params = rows.filter(function (item) {
                item.changeStatus = data;
                return item.repositoryUuid != "";
            });

            axboot.ajax({
                type: "PUT",
                url: "/api/v1/st/st001/02/confirm01",
                data: JSON.stringify(params),
                callback: function (res) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
                },
                options: {
                    onError: axboot.viewError
                }
            });
        }
    },
    STATUS_UPDATE02: function (caller, act, data) {
        var rows = fnObj.gridView02.gridObj.getCheckedList();

        if (!rows || rows.length < 1) return;

        var params = rows.filter(function (item) {
            item.changeStatus = data;
            return item.shelfUuid != "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/st/st001/02/confirm02",
            data: JSON.stringify(params),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    STATUS_UPDATE03: function (caller, act, data) {
        var rows = fnObj.gridView03.gridObj.getCheckedList();

        if (!rows || rows.length < 1) return;

        var params = rows.filter(function (item) {
            item.changeStatus = data;
            return item.locationUuid != "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/st/st001/02/confirm03",
            data: JSON.stringify(params),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_CONFIRM: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE, CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE, CANCEL_STATUS);
    },
    PAGE_SAVE: function (caller, act, data) {
        if (!this.gridView01.gridObj.validate() && !this.gridView02.gridObj.validate()
            && !this.gridView03.gridObj.validate()) {
            return false;
        } else {
            if(fnObj.gridView01.gridObj.isDataChanged()){
                ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE01);
            }
            if(fnObj.gridView02.gridObj.isDataChanged()){
                ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE02);
            }
            if(fnObj.gridView03.gridObj.isDataChanged()){
                ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE03);
            }
        }
    },
    TOP_GRID_SAVE01: function (caller, act, data) {
        var result = false;
        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st001/02/save01",
            data: JSON.stringify(this.gridView01.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
            }
        })
            .done(function () {
                fnObj.gridView01.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    TOP_GRID_SAVE02: function (caller, act, data) {
        var result = false;

        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st001/02/save02",
            data: JSON.stringify(this.gridView02.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
            }
        })
            .done(function () {
                fnObj.gridView02.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    TOP_GRID_SAVE03: function (caller, act, data) {
        var result = false;

        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st001/02/save03",
            data: JSON.stringify(this.gridView03.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
            }
        })
            .done(function () {
                fnObj.gridView03.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    MENU_OPEN: function (caller, act, data) {

    },
    POP_OPEN: function(caller, act, data){
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : false,
            sendData: function () {
                return data;
            },
            callback: function (data) {
                if(this.close)
                    this.close();

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
    },
    EXCEL_DOWN01 : function(caller, act, data){

        var repositoryUuid = fnObj.gridView01.getSelectedData().repositoryUuid;
        var repositoryCode = fnObj.gridView01.getSelectedData().repositoryCode;

        location.href = "/api/v1/st/st010/01/excelDown?repositoryUuid="+repositoryUuid+"&repositoryCode="+repositoryCode;
    },
    EXCEL_DOWN02 : function(caller, act, data){
        var repositoryUuid = fnObj.gridView01.getSelectedData().repositoryUuid;
        var repositoryCode = fnObj.gridView01.getSelectedData().repositoryCode;
        var shelfUuid = fnObj.gridView02.getSelectedData().shelfUuid;

        if(shelfUuid == "" || shelfUuid == undefined || shelfUuid == null){
            return;
        }


        location.href = "/api/v1/st/st010/02/excelDown?repositoryUuid="+repositoryUuid+"&repositoryCode="+repositoryCode+"&shelfUuid="+shelfUuid;
    },
    EXCEL_DOWN03 : function(caller, act, data){
        var repositoryUuid = fnObj.gridView01.getSelectedData().repositoryUuid;
        var repositoryCode = fnObj.gridView01.getSelectedData().repositoryCode;
        var shelfUuid = fnObj.gridView02.getSelectedData().shelfUuid;
        var locationUuid = fnObj.gridView03.getSelectedData().locationUuid;

        if(locationUuid == "" || locationUuid == undefined || locationUuid == null){
            return;
        }

        location.href = "/api/v1/st/st010/03/excelDown?repositoryUuid="+repositoryUuid+"&repositoryCode="+repositoryCode+"&shelfUuid="+shelfUuid+"&locationUuid="+locationUuid
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
        url: "/assets/js/column_info/st01001.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/st01002.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/st01003.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/st01004.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();
    _this.gridView03.initView();
    _this.gridView04.initView();
    $("#inquiry").hide();
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01, this.formView.getData());
};

fnObj.formView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData);
    },
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();
        this.bindEvent();
    },
    initEvent: function () {
        var _this = this;
        $('.btn_i').on('click',function(){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
        });
        $('.btn_excelDown01').on('click',function(){
            ACTIONS.dispatch(ACTIONS.EXCEL_DOWN01);
        });
        $('.btn_excelDown02').on('click',function(){
            ACTIONS.dispatch(ACTIONS.EXCEL_DOWN02);
        });
        $('.btn_excelDown03').on('click',function(){
            ACTIONS.dispatch(ACTIONS.EXCEL_DOWN03);
        });

        $("input[data-ax-path='repositoryCode']").change(function () {
            $("input[data-ax-path='shelfCode']").val("");
            $("input[data-ax-path='shelfName']").val("");
            return;
        });

        $("input[data-ax-path='repositoryName']").change(function () {
            $("input[data-ax-path='shelfCode']").val("");
            $("input[data-ax-path='shelfName']").val("");
            return;
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
    bindEvent : function()
    {
        var _this = this;
        $("input[data-ax-path='shelfCode'],input[data-ax-path='shelfName']").keydown(function(e){
            if(e.keyCode == 13){
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
            }
        });

        $("input[data-ax-path='repositoryCode'],input[data-ax-path='repositoryName']").keydown(function(e){
            if(e.keyCode == 13){
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
            }
        });


    },
});

/*팝업 헤더*/
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey: "repositoryUuid",
    entityName: "ST_REPOSITORY",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st01001.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    itemClick: function (data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
    }

});
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid02",
    primaryKey: "shelfUuid",
    uuidFieldName: "shelfUuid",
    entityName: "ST_SHELF",
    parentsUuidFieldName: "repositoryUuid",
    parentsGrid: fnObj.gridView01,
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st01002.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        //this.removeRowBeforeEvent(this.cancelDelete);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    itemClick: function (data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
    }
});

fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid03",
    primaryKey: "locationUuid",
    entityName: "ST_LOCATION",
    parentsUuidFieldName: "shelfUuid",
    parentsGrid: fnObj.gridView02,
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st01003.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        //this.removeRowBeforeEvent(this.cancelDelete);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    itemClick: function (data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH04);
    }
});

fnObj.gridView04 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid04",
    primaryKey: "arrangeContainersResultUuid",
    uuidFieldName: "arrangeContainersResultUuid",
    entityName: "ST_ARRANGE_CONTAINERS_RESULT",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st01004.column_info);
        this.makeGrid();
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    itemClick: function (data) {

    },
    cancelDelete: function(){

    },
});
