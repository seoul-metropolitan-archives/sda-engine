var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/equip/sh02001110",
            data: $.extend({}, this.searchView.getData(), this.gridView01.getPageData()),
            callback: function (res) {
                caller.gridView01.setData(res);
            },
            options: {
                onError: viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        var saveList = [].concat(caller.gridView01.getData("modified"));
        axboot.ajax({
            type: "PUT",
            url: "/api/v1//mng/equip/sh02001110",
            data: JSON.stringify(saveList),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push("저장 작업이 완료되었습니다.");
            },
            options: {
                onError: viewError
            }
        });
    },
    PAGE_DELETE: function (caller, act, data) {
        caller.gridView01.delRow("selected");
        var saveList = [].concat(caller.gridView01.getData("deleted"));
        axboot.ajax({
            type: "PUT",
            url: "/api/v1//mng/equip/sh02001110",
            data: JSON.stringify(saveList),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push("삭제 작업이 완료되었습니다.");
            },
            options: {
                onError: pageSearchAndViewError
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
            },
            "search-view-clear": function () {
                $("#filter").val("");
                $("#branch").val("");
                $("#corner").val("");
                $("#jisa").val("");
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
            branch: $("#branch").val(),
            corner: $("#corner").val(),
            jisa: $("#jisa").val()
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
            frozenColumnIndex: 0,
            multipleSelect: true,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {key: 'workSeqNo', label: '작업일련번호', width: 100, align: 'center'},
                {key: 'changeChasu', label: '변경차수', width: 70, align: 'center'},
                {key: 'stextGubun', label: '전문구분', width: 70, align: 'center'},
                {key: 'jisaCode', label: '지사코드', width: 70, align: 'center'},
                {key: 'branchCode', label: '지점코드', width: 80, align: 'center'},
                {key: 'branchName', label: '지점명', width: 150, align: 'left'},
                {key: 'cornerCode', label: '코너코드', width: 70, align: 'center'},
                {key: 'cornerName', label: '코너명', width: 150, align: 'left'},
                {key: 'placeGubun', label: '장소구분', width: 70, align: 'center'},
                {key: 'branchGubun', label: '지점구분', width: 70, align: 'center'},
                {key: 'specialStyleGubun', label: '특수형구분', width: 70, align: 'center'},
                {key: 'operTimeGubun', label: '운영시간구분', width: 70, align: 'center'},
                {key: 'operStartTime', label: '운영시작시간', width: 130, align: 'center'},
                {key: 'operEndTime', label: '운영종료시간', width: 130, align: 'center'},
                {key: 'checkOperEnable', label: '수표운영여부', width: 70, align: 'center'},
                {key: 'serviceFee', label: '용역료', width: 150, align: 'right'},
                {key: 'securityCorpCode', label: '경비사코드', width: 80, align: 'center'},
                {key: 'facHireEnable', label: '시설물임차여부', width: 100, align: 'center'},
                {key: 'addr', label: '주소', width: 100, align: 'left'},
                {key: 'unusl', label: '특이사항', width: 100, align: 'left'},
                {key: 'operDay', label: '운영요일', width: 80, align: 'center'}
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, {selectedClear: true});
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
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.workSeqNo;
            });
        } else {
            list = _list;
        }
        return list;
    },
    addRow: function () {
        this.target.addRow({__created__: true}, "last");
    }
});

var viewError = function (err) {
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

var pageSearchAndViewError = function (err) {
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    confirmToast(err);
}