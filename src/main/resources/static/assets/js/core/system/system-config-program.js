var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: ["programs"],
            data: $.extend({}, this.searchView.getData(), this.gridView01.getPageData()),
            callback: function (res) {
                caller.gridView01.setData(res);
            },
            options: {
                onError: function (err) {
                    console.log(err);
                }
            }
        });

        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        var saveList = [].concat(caller.gridView01.getData());
        saveList = saveList.concat(caller.gridView01.getData("deleted"));

        axboot.ajax({
            type: "PUT",
            url: ["programs"],
            data: JSON.stringify(saveList),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push("저장 되었습니다");
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
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            "save": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            },
            "delete": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_DELETE);
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
            //pageNumber: this.pageNumber,
            //pageSize: this.pageSize,
            filter: this.filter.val()
        }
    }
});

/**
 * gridView
 */
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        var _this = this;
        this.target = axboot.gridBuilder({
            showRowSelector: true,
            frozenColumnIndex: 2,
            multipleSelect: true,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {key: "progNm", label: "프로그램명", width: 200, align: "left", editor: "text"},
                {key: "progPh", label: "경로", width: 350, align: "left", editor: {type: "text", disabled: "notCreated"}},
                {key: "authCheck", label: "권한체크여부", width: 80, align: "center", editor: "checkYn"},
                {key: "schAh", label: "조회", width: 50, align: "center", editor: "checkYn"},
                {key: "savAh", label: "저장", width: 50, align: "center", editor: "checkYn"},
                {key: "exlAh", label: "엑셀", width: 50, align: "center", editor: "checkYn"},
                {key: "delAh", label: "삭제", width: 50, align: "center", editor: "checkYn"},
                {key: "fn1Ah", label: "화면갱신", width: 80, align: "center", editor: "checkYn"},
                {key: "fn2Ah", label: "페이지+모델 작성", width: 130, align: "center", editor: "checkYn"},
                {key: "fn3Ah", label: "FN3", width: 50, align: "center", editor: "checkYn"},
                {key: "fn4Ah", label: "FN4", width: 50, align: "center", editor: "checkYn"},
                {key: "fn5Ah", label: "FN5", width: 50, align: "center", editor: "checkYn"},
                {key: "remark", label: "설명", width: 300, editor: "text"}
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex);
                    ACTIONS.dispatch(ACTIONS.ITEM_CLICK, this.item);
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({pageNumber: pageNumber});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.progNm && this.progPh;
            });
        } else {
            list = _list;
        }
        return list;
    },
    addRow: function () {
        this.target.addRow({__created__: true, useYn: "N", authCheck: "N"}, "last");
    }
});