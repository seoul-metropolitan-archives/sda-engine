var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: ["commonCodes"],
            data: $.extend({}, this.searchView.getData(), this.gridView01.getPageData(), this.gridView01.getSortData()),
            callback: function (res) {
                caller.gridView01.setData(res);
            },
            options: {
                onError: function (err) {
                    console.log('err: ', err);
                    axToast.confirm({
                        theme: "danger",
                        width: 300,
                        lang: {
                            "ok": "닫기"
                        },
                        icon: '<i class="cqc-new"></i>',
                        msg: '[에러] ' + err.message
                    });
                }
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        var saveList = [].concat(caller.gridView01.getData("modified"));
        saveList = saveList.concat(caller.gridView01.getData("deleted"));

        axboot.ajax({
            type: "PUT",
            url: ["commonCodes"],
            data: JSON.stringify(saveList),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push("저장 되었습니다");
            },
            options: {
                onError: function (err) {
                    console.log('err: ', err);
                    axToast.confirm({
                        theme: "danger",
                        width: 300,
                        lang: {
                            "ok": "닫기"
                        },
                        icon: '<i class="cqc-new"></i>',
                        msg: '[에러] ' + err.message
                    });
                }
            }
        });
    },
    PAGE_DELETE: function (caller, act, data) {
        caller.gridView01.delRow("selected");
        var saveList = [].concat(caller.gridView01.getData("deleted"));
        axboot.ajax({
            type: "PUT",
            url: ["commonCodes"],
            data: JSON.stringify(saveList),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push("삭제 작업이 완료되었습니다.");
            },
            options: {
                onError: function (err) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    axToast.confirm({
                        theme: "danger",
                        width: 300,
                        lang: {
                            "ok": "닫기"
                        },
                        icon: '<i class="cqc-new"></i>',
                        msg: '[에러] ' + err.message
                    });
                }
            }
        });
    },
    ITEM_ADD: function (caller, act, data) {
        caller.gridView01.addRow();
    },
    dispatch: function (caller, act, data) {
        var result = ACTIONS.exec(caller, act, data);
        if (result != "error") {
            return result;
        } else {
            // 직접코딩
            return false;
        }
    }
});

var CODE = {};

// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    this.pageButtonView.initView();
    this.searchView.initView();
    this.gridView01.initView();

    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.pageResize = function () {

};

fnObj.pageButtonView = axboot.viewExtend({
    initView: function () {
        axboot.buttonClick(this, "data-page-btn", {
            "search": function () {
                sortData = {};
                fnObj.gridView01.initView();
                fnObj.gridView01.setPageData({pageNumber: 0});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            "save": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            },
            "delete": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_DELETE);
            },
            "search-view-clear": function () {
                $("#filter").val("");
                $("#commonCode").val("");
                sortData = {};
                fnObj.gridView01.setPageData({pageNumber: 0, pageSize: 10000});
                fnObj.gridView01.initView();
                fnObj.gridView01.setPageData({pageNumber: 0});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });
    }
});

//== view 시작
/**
 * searchView
 */
fnObj.searchView = axboot.viewExtend(axboot.searchView, {
    initView: function () {
        this.target = $(document["searchView0"]);
        this.target.attr("onsubmit", "return ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);");
        this.filter = $("#filter");
    },
    getData: function () {
        return {
            filter: this.filter.val(),
            groupCd: $("#commonCode").val()
        }
    }
});

var sortData = {};

/**
 * gridView
 */
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {

    page: {
        pageNumber: 0,
        pageSize: 10000
    },
    initView: function () {
        var _this = this;
        this.target = axboot.gridBuilder({
            showLineNumber: true,
            showRowSelector: true,
            frozenColumnIndex: 0,
            remoteSort: function() {
                if (this != null) {
                    sortData = {
                        sort: this.sortInfo[0].key + "," + this.sortInfo[0].orderBy,
                        sortTarget: this.sortInfo[0].key,
                        order: this.sortInfo[0].orderBy
                    };
                }
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            multipleSelect: false,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {key: "groupCd", label: "분류코드", width: 250, align: "center", editor: {type: "text", disabled: "notCreated"}},
                {key: "groupNm", label: "분류명", width: 250, align: "center", sortable: true, editor: "text",
                    formatter: function () {
                        return "<div class='editable-cell'>" + this.value + "</div>";
                    }
                },
                {key: "code", label: "코드", width: 250, align: "center", editor: {type: "text", disabled: "notCreated"}},
                {key: "name", label: "코드값", width: 250, align: "center", sortable: true, editor: "text",
                    formatter: function () {
                        return "<div class='editable-cell'>" + this.value + "</div>";
                    }
                },
                {key: "sort", editor: "number"},
                {key: "useYn", editor: "checkYn"},
                {key: "remark", label: "비고", width: 200, align: "center", editor: {
                    type: "select",
                    config: {
                        columnKeys: {
                            optionValue: "name", optionText: "name"
                        },
                        options: parent.COMMON_CODE["USER_STATUS"]
                        /*options: [
                         {code: "M", name: "M: Man"},
                         {code: "D", name: "D: Daughter"},
                         {code: "S", name: "S: Son"},
                         {code: "W", name: "W: Wife"}
                         ]*/
                    }
                }}
                /*{key: "remark", label: "비고", width: 200, align: "left", editor: "text"}*/
            ],
            body: {
                onClick: function () {
                    //this.self.select(this.dindex, {selectedClear: true});
                    this.self.select(this.dindex);
                    ACTIONS.dispatch(ACTIONS.ITEM_CLICK, this.item);
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({
                    pageNumber: pageNumber
                });
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });

        axboot.buttonClick(this, "data-grid-view-01-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "delete": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_DEL);
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.groupCd && this.code;
            });
        } else {
            list = _list;
        }
        return list;
    },
    getSortData: function () {
        return sortData;
    },
    addRow: function () {
        this.target.addRow({__created__: true, posUseYn: "N", useYn: "Y"}, "last");
    }
});