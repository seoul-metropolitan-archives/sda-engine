var fnObj = {};
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
    },
    PAGE_SEARCH01: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st001/01/list01",
            data: $.extend({}, this.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH02: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st001/01/list02",
            data: $.extend({}, this.formView.getData()),
            callback: function (res) {
                fnObj.gridView02.setData(res.list);

                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH03: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st001/01/list03",
            data: $.extend({}, this.formView.getData()),
            callback: function (res) {
                fnObj.gridView03.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    STATUS_UPDATE: function (caller, act, data) {
        var rows = fnObj.gridView01.gridObj.getCheckedList();

        if (!rows || rows.length < 1) return;

        var params = rows.filter(function (item) {
            item.changeStatus = data;
            return item.generalRecordScheduleUuid != "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/st/rs001/02/confirm",
            data: JSON.stringify(params),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
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
        if (!this.gridView01.gridObj.validate()) {
            return false;
        } else {
            ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE);
        }
    },
    TOP_GRID_SAVE: function (caller, act, data) {
        var result = false;
        axboot.call({
            type: "PUT",
            url: "/api/v1/rs/rs001/03/save",
            data: JSON.stringify(this.gridView01.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        })
            .done(function () {
                fnObj.gridView01.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    MENU_OPEN: function (caller, act, data) {

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
        url: "/assets/js/column_info/st00101.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/st00102.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/st00103.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();
    _this.gridView03.initView();
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
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey: "repositoryUuid",
    entityName: "Repository UUID",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00101.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    disabledColumn: function () {
        var codes = axboot.commonCodeFilter("CD134").codeArr;
        var names = axboot.commonCodeFilter("CD134").nameArr;
        var state = undefined;
        for (var i = 0; i < names.length; i++) {
            if (names[i] == "Confirm") {
                state = codes[i];
                break;
            }
        }
        this.gridObj.setCustomCellStyleRows("disable", function (row) {

            if (row["statusUuid"] == state)
                return true;
            else
                return false;
        }, ["grsName", "retentionPeriodUuid", "disposalTypeUuid", "basedOn", "description"]);
    },
    itemClick: function (data) {
    }
});
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid02",
    primaryKey: "shelfUuid",
    entityName: "Shelf UUID",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00102.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    disabledColumn: function () {
        var codes = axboot.commonCodeFilter("CD134").codeArr;
        var names = axboot.commonCodeFilter("CD134").nameArr;
        var state = undefined;
        for (var i = 0; i < names.length; i++) {
            if (names[i] == "Confirm") {
                state = codes[i];
                break;
            }
        }
        this.gridObj.setCustomCellStyleRows("disable", function (row) {

            if (row["statusUuid"] == state)
                return true;
            else
                return false;
        }, ["grsName", "retentionPeriodUuid", "disposalTypeUuid", "basedOn", "description"]);
   },
    itemClick: function (data) {
    }
});
fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid03",
    primaryKey: "generalRecordScheduleUuid",
    entityName: "General Record Schedule UUID",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00103.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    disabledColumn: function () {
        var codes = axboot.commonCodeFilter("CD134").codeArr;
        var names = axboot.commonCodeFilter("CD134").nameArr;
        var state = undefined;
        for (var i = 0; i < names.length; i++) {
            if (names[i] == "Confirm") {
                state = codes[i];
                break;
            }
        }
        this.gridObj.setCustomCellStyleRows("disable", function (row) {

            if (row["statusUuid"] == state)
                return true;
            else
                return false;
        }, ["grsName", "retentionPeriodUuid", "disposalTypeUuid", "basedOn", "description"]);
    },
    itemClick: function (data) {
    }
});

/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}

 isDataChanged = function () {
    if (fnObj.gridView01.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}
 */