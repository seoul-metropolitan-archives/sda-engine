var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    TERMINAL_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/equip/terminal_status",
            data: $.extend({}, {jisaCode:$("#jisaCode").val()}, this.gridView01.getPageData()),
            callback: function (res) {
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
    ITEM_CLICK: function (caller, act, data) {

        var parentData = this.searchView.getData();
        parentData.jisaCode = data.jisaCode;
        parentData.branchCode = data.branchCode;
        parentData.terminalNo = data.terminalNo;

        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/cash/sh03001120/allList",
            data: $.extend({}, parentData),
            callback: function (res) {
                caller.gridView02.setData(res);
            },
            options: {
                onError: viewError
            }
        });

    },
    PAGE_SAVE: function (caller, act, data) {
        if( fnObj.searchView.validate() ){
            var parentData = this.searchView.getData();
            parentData.referStartTime = "000000";
            parentData.referEndTime = "235959";
            axboot.ajax({
                type: "GET",
                url: "/api/v1//mng/cash/sh03001120",
                data: $.extend({stextSend: true}, parentData),
                callback: function () {
                    axToast.push("거래내역 조회가 요청되었습니다.");
                    fnObj.gridView02.setPageData({pageNumber: 0});
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                },
                options: {
                    onError: viewError
                }
            });
        }
        return false;
    },
    PAGE_SEARCH: function (caller, act, data) {
        if( fnObj.searchView.validate() ){
            var parentData = this.searchView.getData();
            parentData.referStartTime = "00:00";
            parentData.referEndTime = "23:59";
            axboot.ajax({
                type: "GET",
                url: "/api/v1//mng/cash/sh03001120",
                data: $.extend({}, parentData, this.gridView02.getPageData(), initPage),
                callback: function (res) {
                    caller.gridView02.setData(res);
                },
                options: {
                    onError: viewError
                }
            });
        }
        return false;
    },
    EXCEL_DOWNLOAD: function (caller, act, data) {
        var params = buildParams($.extend({}, this.searchView.getData()));
        console.log(params);
        window.location = CONTEXT_PATH + "/api/v1//mng/cash/sh03001120/download?" + params;
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
var PARAMS = {};
// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    CODE = this; // this는 call을 통해 수집된 데이터들.

    this.pageButtonView.initView();
    this.searchView.initView();
    this.gridView01.initView();
    this.gridView02.initView();

    ACTIONS.dispatch(ACTIONS.TERMINAL_SEARCH);
};

fnObj.pageResize = function () {
};

fnObj.pageButtonView = axboot.viewExtend({
    initView: function () {
        axboot.buttonClick(this, "data-page-btn", {
            "search": function () {
                fnObj.gridView02.setPageData({pageNumber: 0});
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            },
            "excel": function () {
                fnObj.gridView02.excel("거래내역조회-"+getFormattedDate(new Date())+".xls");
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
                $("#referDate").val(getFormattedDate(new Date()));
                $("#filter").val("");
                $("#referStatementNo").val("");
                // $("#referStartTime").val("00:00");
                // $("#referEndTime").val("23:59");
                fnObj.gridView01.setPageData({pageNumber: 0});
                ACTIONS.dispatch(ACTIONS.TERMINAL_SEARCH);
                fnObj.gridView01.initView();
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
        this.jisaCode = $("#jisaCode");
        this.branchCode = $("#branchCode");
        this.terminalNo = $("#terminalNo");
        this.referDate = $("#referDate");
        this.referStatementNo = $("#referStatementNo");
        this.referStartTime = $("#referStartTime");
        this.referEndTime = $("#referEndTime");
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

        $("#referDate").val(getFormattedDate(new Date()));
        $("#referStartTime").val("00:00");
        $("#referEndTime").val("23:59");

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
                ACTIONS.dispatch(ACTIONS.TERMINAL_SEARCH);
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
            branchCode: $("#branchCode").val(),
            terminalNo: $("#terminalNo").val(),
            referDate: $("#referDate").val(),
            referStatementNo: $("#referStatementNo").val(),
            referStartTime: $("#referStartTime").val(),
            referEndTime: $("#referEndTime").val()
        }
    },
    validate: function () {
        var message;
        if($("#jisaCode").val()=="") {
            message = '\n' + '지사는 필수 입력조건입니다.\n' + '지사를 선택하세요.';
            $("#jisaCode").focus();
            formError(message);
            return false;
        } else if($("#branchCode").val()=="") {
            message = '\n' + '지점은 필수 입력조건입니다.\n' + '지점을 선택하세요.';
            $("#branchCode").focus();
            formError(message);
            return false;
        } else if($("#terminalNo").val()=="") {
            message = '\n' + '단말번호는 필수 입력조건입니다.\n' + '단말번호를 선택하세요.';
            $("#terminalNo").focus();
            formError(message);
            return false;
        } else if($("#referStatementNo").val()=="") {
            message = '\n' + '전표번호는 필수 입력조건입니다.\n' + '전표번호를 입력하세요.';
            $("#referStatementNo").focus();
            formError(message);
            return false;
        }

        return true;
    }
});

/**
 * gridView 01
 */
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({
            frozenColumnIndex: 0,
            showRowSelector: false,
            sortable: true,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {key: 'jisaCode', label: '지사코드', width: 70, align: 'center', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["JISA_CODE"].map[this.value];
                    }
                },
                {key: 'branchName', label: '지점명', width: 150, align: 'left'},
                {key: 'cornerName', label: '코너명', width: 150, align: 'left'},
                {key: 'terminalNo', label: '단말번호', width: 70, align: 'center'},
                {key: 'operEnable', label: '운영여부', width: 70, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["OPER_ENABLE"].map[this.value];
                    }
                },
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex);
                    ACTIONS.dispatch(ACTIONS.ITEM_CLICK, this.item);
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({pageNumber: pageNumber});
                ACTIONS.dispatch(ACTIONS.TERMINAL_SEARCH);
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.jisaCode && this.branchCode && this.cornerCode && this.terminalNo;
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

/**
 * gridView 02
 */
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({
            frozenColumnIndex: 0,
            showLineNumber: true,
            showRowSelector: false,
            target: $('[data-ax5grid="grid-view-02"]'),
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
                {key: 'terminalNo', label: '단말번호', width: 70, align: 'center'},
                {key: 'referStatementNo', label: '전표번호', width: 70, align: 'center'},
                {key: 'dealSeqNo', label: '거래일련번호', width: 100, align: 'center'},
                {key: 'dealTime', label: '거래시간', width: 140, align: 'center'},
                {key: 'processStatus', label: '처리상태', width: 100, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["PROCESS_STATUS"].map[this.value];
                    }
                },
                {key: 'locateGubun', label: '입지구분', width: 100, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["LOCATE_GUBUN"].map[this.value];
                    }
                },
                {key: 'dealMethod', label: '거래매체', width: 100, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["DEAL_METHOD"].map[this.value];
                    }
                },
                {key: 'dealAmt', label: '거래금액', width: 130, align: 'right', formatter: "money", sortable:true},
                {key: 'depositBankCode', label: '입금은행', width: 100, align: 'center'},
                {key: 'depositAccountNo', label: '입금계좌', width: 150, align: 'center'},
                {key: 'withdrawBankCode', label: '출금은행', width: 100, align: 'center'},
                {key: 'withdrawAccountNo', label: '출금계좌', width: 150, align: 'center'}
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
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.branchCode && this.terminalNo && this.referDate && this.referStatementNo;
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

var viewError = function (err) {

    console.log(err);

    axToast.push({
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
    fnObj.gridView02.setPageData({pageNumber: 0});
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