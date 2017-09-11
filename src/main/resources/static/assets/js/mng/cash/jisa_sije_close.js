var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/cash/jisa_sije_close",
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
            url: "/api/v1//mng/cash/jisa_sije_close",
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
    EXCEL_DOWNLOAD: function (caller, act, data) {
        var params = buildParams($.extend({}, this.searchView.getData()));
        console.log(params);
        window.location = CONTEXT_PATH + "/api/v1//mng/cash/jisa_sije_close/download?" + params;
        return false;
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
            "excel": function () {
                fnObj.gridView01.excel("지사별마감일지-"+getFormattedDate(new Date())+".xls");
                // ACTIONS.dispatch(ACTIONS.EXCEL_DOWNLOAD);
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
        this.closeDate = $("#closeDate");
        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        $("#closeDate").val(getFormattedDate(new Date()));

    },
    getData: function () {
        return {
            closeDate: this.closeDate.val()
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
            showRowSelector: false,
            multipleSelect: true,
            frozenColumnIndex: 0,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {key: 'closeDate', label: '마감일자', width: 100, align: 'center'},
                {key: 'jisaCode', label: '지사명', width: 70, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["JISA_CODE"].map[this.value];
                    }
                },
                {key: 'prevDayReserveSije', label: '전일예비시재', width: 130, align: 'right',  formatter: "money"},
                {key: 'thisDayCashDepositAmt', label: '금일입금액(*)', width: 130, align: 'right',  formatter: "money", editor: 'number'},
                {key: 'jisaToShinhanSendAmt', label: '지사>신한(*)', width: 130, align: 'right',  formatter: "money", editor: 'number'},
                {key: 'closeAmt', label: '금일마감금액', width: 130, align: 'right',  formatter: "money"},
                {key: 'unCheckAmt', label: '미확인금액(*)', width: 130, align: 'right',  formatter: "money", editor: 'number'},
                {key: 'sijeMistakeAmt', label: '시재착오금(*)', width: 130, align: 'right',  formatter: "money", editor: 'number'},
                {key: 'cashSendingAmt', label: '현송금액', width: 130, align: 'right',  formatter: "money"},
                {key: 'addCashSendingAmt', label: '추가현송금액', width: 130, align: 'right',  formatter: "money"},
                {key: 'jisaSafeAmt', label: '금고보관액', width: 130, align: 'right',  formatter: "money"},
                {key: 'memoContent', label: '메모(*)', width: 520, align: 'left', editor: 'text'},
                {key: 'userNm', label: '변경사용자', width: 100, align: 'center',
                    formatter: function formatter() {
                        if(this.value) {
                            return this.value;
                        } else {
                            return '-';
                        }
                    }
                },
                {key: 'txId', label: '변경일시', width: 130, align: 'center',
                    formatter: function formatter() {
                        if(this.value) {
                            return this.value;
                        } else {
                            return '-';
                        }
                    }
                }
            ],
            footSum: [
                [
                    {label: "합계", colspan: 2, align: "center"},
                    {key: "prevDayReserveSije", collector: "sum", formatter: "money", align: "right"},
                    {key: "thisDayCashDepositAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "jisaToShinhanSendAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "closeAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "unCheckAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "sijeMistakeAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "cashSendingAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "addCashSendingAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "jisaSafeAmt", collector: "sum", formatter: "money", align: "right"}
                ]
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, {selectedClear: true});
                },
                onDataChanged: function () {
                    this.item.jisaSafeAmt = Number(this.item.prevDayReserveSije)
                        + Number(this.item.thisDayCashDepositAmt)
                        + Number(this.item.jisaToShinhanSendAmt)
                        + Number(this.item.closeAmt)
                        + Number(this.item.unCheckAmt)
                        + Number(this.item.sijeMistakeAmt)
                        - Number(this.item.cashSendingAmt)
                        - Number(this.item.addCashSendingAmt);

                    fnObj.gridView01.setValue(this.dindex, 'jisaSafeAmt' ,this.item.jisaSafeAmt);
                    // fnObj.gridView01.setData(this.list);
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
                this.txId = getFormattedDate(new Date());
                this.userNm = sessionJson.userNm;
                return this.jisaCode && this.closeDate && this.txId && this.userNm;
            });
        } else {
            list = _list;
        }

        return list;
    },
    addRow: function () {
        this.target.addRow({__created__: true}, "last");
    },
    excel: function (file) {
        this.target.exportExcel(file);
    },
    setValue: function (dindex, key, value) {
        this.target.setValue(dindex,key,value);
    }
});

var viewError = function (err) {
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

var pageSearchAndViewError = function (err) {
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    confirmToast(err);
}

function getFormattedDate(date) {
    var year = date.getFullYear();
    var month = (1 + date.getMonth()).toString();
    month = month.length > 1 ? month : '0' + month;
    var day = date.getDate().toString();
    day = day.length > 1 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}