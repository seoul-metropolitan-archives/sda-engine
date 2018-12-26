var fnObj = {};
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";
var beforeData01 = null;
var beforeData02 = null;
var repositoryUuid = '';
var shelfUuid ='';
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        beforeData01 = null;
        beforeData02 = null;
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
    },
    PAGE_SEARCH01: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st010/01/list04",
            data: $.extend({}, this.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
                fnObj.gridView02.clearData();
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
            data: $.extend({}, this.formView.getData()),
            //data: $.extend({}, this.formView.getData(),{repositoryUuid : fnObj.gridView01.getSelectedData().repositoryUuid}),
            callback: function (res) {
                fnObj.gridView02.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    ERROR_SEARCH: function (caller, act, data) {
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
    //샘플삭제 해야됨
    $.ajax({
        url: "/assets/js/column_info/st00102.js",
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

    },
});

/*팝업 헤더*/
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey: "arrangeContainersResultUuid",
    uuidFieldName: "arrangeContainersResultUuid",
    entityName: "ST_ARRANGE_CONTAINERS_RESULT",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st01004.column_info);
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
    primaryKey: "takeoutRecordResultUuid",
    uuidFieldName: "takeoutRecordResultUuid",
    entityName: "ST_TAKEOUT_RECORD_RESULT",
    parentsUuidFieldName: "aggregationUuid",
    parentsGrid: fnObj.gridView01,
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00102.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    itemClick: function (data) {

    }
});
