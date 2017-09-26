var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/cash/sh03001130",
            data: $.extend({findPage: true}, this.searchView.getData(), this.gridView01.getPageData()),
            callback: function (res) {
                $("#closeGubun").val("");
                caller.gridView01.setData(res);
            },
            options: {
                onError: viewError
            }
        });
        return false;
    },
    MODAL_OPEN: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_TERMINAL_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisa":  $("#jisaCode").val()
                };
            },
            callback: function (data) {
                $("#jisaCode").val(data.jisaCode);
                $("#branchName").val(data.branchName);
                $("#branchCode").val(data.branchCode);
                $("#cornerName").val(data.cornerName);
                $("#terminalNo").val(data.terminalNo);

                this.close();
            }
        });
    },
    FIND_ONE: function (caller, act, data) {
        var selectedGridData = fnObj.gridView01.getData("selected");
        if(selectedGridData.length > 0) {
            if( fnObj.searchView.validate() ){
                selectedGridData[0].closeGubun = $("#closeGubun").val();
                selectedGridData[0].reqDate = $("#reqDate").val();
                axboot.ajax({
                    type: "GET",
                    url: "/api/v1//mng/cash/sh03001130",
                    data: $.extend({stext: true}, selectedGridData[0]),
                    callback: function () {
                        axToast.push("마감조회가 요청되었습니다.");
                        // $("#closeGubun").val("");
                        fnObj.gridView01.setPageData({pageNumber: 0});
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    },
                    options: {
                        onError: viewError
                    }
                });
            }
        } else {
            formError("단건조회할 대상을 선택해주세요.");
        }
    },
    FIND_ALL: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/cash/sh03001130",
            data: $.extend({findAll: true}, this.searchView.getData()),
            callback: function () {
                axToast.push("전체조회가 완료되었습니다.");
                fnObj.gridView01.setPageData({pageNumber: 0});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            options: {
                onError: viewError
            }
        });
    },
    ITEM_CLICK: function (caller, act, data) {
        // $("#jisaCode").val(data.jisaCode);
        // $("#branchName").val(parent.COMMON_CODE["BRANCH_CODE"].map[data.branchCode]);
        // $("#branchCode").val(data.branchCode);
        // $("#cornerName").val(parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[data.terminalNo]);
        // $("#terminalNo").val(data.terminalNo);
        $("#closeGubun").val(data.closeGubun);
    },
    EXCEL_DOWNLOAD: function (caller, act, data) {
        var params = buildParams($.extend({}, this.searchView.getData()));
        console.log(params);
        window.location = CONTEXT_PATH + "/api/v1//mng/cash/sh03001130/download?" + params;
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
                fnObj.gridView01.setPageData({pageNumber: 0});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            "excel": function () {
                fnObj.gridView01.excel("마감조회-"+getFormattedDate(new Date())+".xls");
                // ACTIONS.dispatch(ACTIONS.EXCEL_DOWNLOAD);
            },
            "search-view-clear": function () {
                if(sessionJson.userId == "system" || sessionJson.jisaCode == "00") {
                    $("#jisaCode").val("");
                }
                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
                $("#terminalNo").val("");
                $("#closeGubun").val("");
                $("#reqDate").val(getFormattedDate(new Date()));
                fnObj.gridView01.setPageData({pageNumber: 0});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });
    }
});

var initPage = { initPage: true };

//== view 시작
/**
 * searchView
 */
fnObj.searchView = axboot.viewExtend(axboot.searchView, {
    initView: function () {
        this.target = $(document["searchView0"]);
        this.target.attr("onsubmit", "return ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);");
        this.jisaCode = $("#jisaCode");
        this.branchCode = $("#branchCode");
        this.terminalNo = $("#terminalNo");
        this.closeGubun = $("#closeGubun");
        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        if(sessionJson.userId != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCode").attr('readonly', true);
            $("#jisaCode").attr('disabled', true);
            $("#jisaCode").val(sessionJson.jisaCode);
        }

        $("#reqDate").val(getFormattedDate(new Date()));

        axboot.buttonClick(this, "data-searchview-btn", {
            "modal": function () {
                ACTIONS.dispatch(ACTIONS.MODAL_OPEN)
            }
        });

        $('#jisaCode').change(
            function(){
                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
                $("#terminalNo").val("");
            });

        $('#branchCode').change(
            function(){
                $("#branchName").val("");
            });

        $('#terminalNo').change(
            function(){
                $("#cornerName").val("");
            });
    },
    getData: function () {
        return {
            jisaCode: $("#jisaCode").val(),
            branchName: $("#branchName").val(),
            branchCode: $("#branchCode").val(),
            cornerName: $("#cornerName").val(),
            terminalNo: $("#terminalNo").val(),
            closeGubun: $("#closeGubun").val(),
            reqDate: $("#reqDate").val()
        }
    },
    validate: function () {
        var message;
        if($("#closeGubun").val()=="") {
            message = '\n' + '마감구분은 필수 선택조건입니다.\n' + '마감구분을 선택하세요.';
            $("#closeGubun").focus();
            formError(message);
            return false;
        }

        return true;
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
            frozenColumnIndex: 0,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {key: 'jisaCode', label: '지사명', width: 70, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["JISA_CODE"].map[this.value];
                    }
                },
                {key: 'branchCode', label: '지점명', width: 150, align: 'left',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["BRANCH_CODE"].map[this.value];
                    }
                },
                {key: 'terminalNo', label: '코너명', width: 150, align: 'left',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[this.value];
                    }
                },
                {key: 'terminalNo', label: '단말번호', width: 70, align: 'center',
                    formatter: function formatter() {
                        if(this.value) {
                            return this.value;
                        } else {
                            return '-';
                        }
                    }
                },
                {key: 'safeNo', label: '금고번호', width: 80, align: 'center',
                    formatter: function formatter() {
                        if(this.value) {
                            return this.value;
                        } else {
                            return '-';
                        }
                    }
                },
                {key: 'reqDate', label: '요청일자', width: 100, align: 'center',
                    formatter: function formatter() {
                        if(this.value) {
                            return this.value;
                        } else {
                            return '-';
                        }
                    }
                },
                {
                    key: 'stextSendGubun', label: '전송여부', width: 130, align: 'center',
                    formatter: function formatter() {
                        if(this.value == "0") {
                            return "미전송"
                        } else if(this.value == "1") {
                            return "전문전송"
                        } else if(this.value == "2") {
                            return "마감완료"
                        } else if(this.value == "3") {
                            return "전송실패(마감전)"
                        } else {
                            return "-"
                        }
                    }
                },
                {key: 'closeDatetime', label: '마감일시', width: 130, align: 'center',
                    formatter: function formatter() {
                        if(this.value) {
                            return this.value;
                        } else {
                            return '-';
                        }
                    }
                },
                {key: 'closeGubun', label: '마감구분', width: 100, align: 'center',
                    formatter: function formatter() {
                        if(this.value) {
                            return parent.COMMON_CODE["CLOSE_GUBUN"].map[this.value];
                        } else {
                            return '-';
                        }
                    }
                },
                {key: 'cashGiveAmt', label: '현금지급액', width: 130, align: 'right', formatter: "money"},
                {key: 'cashDepositAmt', label: '현금입금액', width: 130, align: 'right', formatter: "money"},
                {key: 'cashSendingAmt', label: '현송금액', width: 130, align: 'right', formatter: "money"},
                {key: 'rtrvlAmt', label: '회수금액', width: 130, align: 'right', formatter: "money"},
                {key: 'checkWithdrawAmt', label: '수표발행액', width: 130, align: 'right', formatter: "money"},
                {key: 'checkDepositAmt', label: '수표입금액', width: 130, align: 'right', formatter: "money"},
                {key: 'nowDayPutAmt', label: '당일투입금액', width: 130, align: 'right', formatter: "money"},
                {key: 'prevDayPutAmt', label: '전일투입금액', width: 130, align: 'right', formatter: "money"},
                {key: 'prevDayAddPutAmt', label: '전일추가투입금액', width: 130, align: 'right', formatter: "money"},
                {key: 'holidayAddPutAmt', label: '휴일추가투입금액', width: 130, align: 'right', formatter: "money"},
                {key: 'thisDayAddPutAmt', label: '금일추가투입금액', width: 130, align: 'right', formatter: "money"},
                {key: 'atmcTerminalNowSijae', label: '자동기현재시재', width: 130, align: 'right', formatter: "money"},
                {key: 'sijaeCash50k', label: '5만원권시재', width: 130, align: 'right', formatter: "money"}
                // {key: 'txId', label: '업데이트일시', width: 130, align: 'center',
                //     formatter: function formatter() {
                //         if(this.value) {
                //             return this.value;
                //         } else {
                //             return '-';
                //         }
                //     }
                // }
            ],
            footSum: [
                [
                    {label: "", colspan: 7, align: "center"},
                    {label: "합계", colspan: 2, align: "center"},
                    {key: "cashGiveAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "cashDepositAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "cashSendingAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "rtrvlAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "checkWithdrawAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "checkDepositAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "nowDayPutAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "prevDayPutAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "prevDayAddPutAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "holidayAddPutAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "thisDayAddPutAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "atmcTerminalNowSijae", collector: "sum", formatter: "money", align: "right"},
                    {key: "sijaeCash50k", collector: "sum", formatter: "money", align: "right"}
                ]
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

        axboot.buttonClick(this, "data-grid-view-01-btn", {
            "findOne": function () {
                ACTIONS.dispatch(ACTIONS.FIND_ONE);
            },
            "findAll": function () {
                ACTIONS.dispatch(ACTIONS.FIND_ALL);
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.branchCode && this.terminalNo && this.closeDatetime;
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
    }
});

var buildParams = function (json) {

    var params = JSON.stringify(json);

    params=params.replace(/{/g, "");
    params=params.replace(/}/g, "");
    params=params.replace(/:/g, "=")
    params=params.replace(/,/g, "&");
    params=params.replace(/"/g, "");

    return params;

}

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

var formError = function (message) {
    axToast.confirm({
        theme: "danger",
        width: 300,
        lang: {
            "ok": "닫기"
        },
        icon: '<i class="cqc-new"></i>',
        msg: '[에러] ' + message
    });
}

var pageSearchAndviewError = function (err) {
    fnObj.gridView01.setPageData({pageNumber: 0});
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    viewError(err);
}

function getFormattedDate(date) {
    var year = date.getFullYear();
    var month = (1 + date.getMonth()).toString();
    month = month.length > 1 ? month : '0' + month;
    var day = date.getDate().toString();
    day = day.length > 1 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}