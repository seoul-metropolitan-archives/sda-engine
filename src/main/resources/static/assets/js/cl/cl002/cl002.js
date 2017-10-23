var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data){
        axboot.ajax({
            type: "GET",
            url: "/api/v1/cl002/01/list",
            data: $.extend({}, {pageSize: 1000}),
            callback: function (res) {
                if(res.list && res.list.length < 1)
                {
                    fnObj.gridView01.addRow();
                    return ;
                }

                fnObj.gridView01.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    ERROR_SEARCH: function (caller, act, data) {
        return false;
    },
    POPUP_HEADER_PAGE_SAVE: function (caller, act, data) {
        axDialog.confirm({
            msg: "저장하시겠습니까?"
        },function() {
            if (this.key == "ok") {
                alert("저장하자");
            }
        });
    },
    FORM_CLEAR: function (caller, act, data) {
        return false;
    },
    ITEM_CLICK: function (caller, act, data) {
        return false;
    },
    SEND_STEXT: function (caller, act, data) {
        return false;
    },
    SEND_STEXT_CANCEL: function (caller, act, data) {
        return false;
    },
    MODAL_OPEN: function (caller, act, data) {
        return false;
    },
    ROLE_GRID_DATA_INIT: function (caller, act, data) {
        return false;
    },
    ROLE_GRID_DATA_GET: function (caller, act, data) {
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

fnObj = {
    pageStart : function () {
        var _this = this;
        $.ajax({
            url: "/assets/js/controller/simple_controller.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/column_info/cl00201.js",
            dataType: "script",
            async : false,
            success: function(){}
        });

        _this.formView.initView();
        _this.gridView01.initView();
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,this.formView.getData());

    }
};

fnObj.formView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {useYn: "Y"});
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
fnObj.gridView01 = axboot.viewExtend(axboot.realGridView, {
    tagId : "realgrid01",
    entityName : "class",
    initView  : function()
    {
        this.setColumnInfo(cl00201.column_info);
        this.gridObj.setFixedOptions(4);
        this.gridObj.setOption({
            checkBar : {visible : true}
        })
        this.makeGrid();
    },
    setData: function (list) {
        this.gridObj.setData("set", list);

    },
    getData: function () {
        return this.gridObj.getData();
    },
    addRow: function () {
        this.gridObj.addRow();
    },
    itemClick: function (data) {
    }
});