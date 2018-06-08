var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "POST",
            url: "/api/v1/ad/ad001/getEnviromentList.do",
            data: JSON.stringify(fnObj.searchView.getData()),
            callback: function (res) {
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SAVE: function (caller, act, data) {

        if (!fnObj.gridView01.validate())
            return false;

        var list = fnObj.gridView01.getData();

        if (list.length < 1)
            return true;

        axboot.ajax({
            type: "POST",
            url: "/api/v1/ad/ad001/save.do",
            async: false,
            data: JSON.stringify(list),
            callback: function (res) {
                fnObj.gridView01.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    CLOSE_TAB: function () {
        return ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
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
        var _this = this;
        $.ajax({
            url: "/assets/js/column_info/ad00101.js",
            dataType: "script",
            async: false,
            success: function () {
            }
        });
        _this.searchView.initView();
        _this.gridView01.initView();
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    }
};

fnObj.searchView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData);
    },
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();
    },
    initEvent: function () {
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        console.log(data);
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

})

fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey : "configurationUuid",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(ad00101.column_info);
        this.setFixedOptions({
            colCount : 2
        })
        this.makeGrid();

    }
});