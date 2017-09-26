var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/cash/sh03001140",
            data: $.extend({}, this.searchView.getData(), this.gridView01.getPageData()),
            callback: function (res) {
                caller.gridView01.setData(res);
                $("#formView01 input").attr('disabled', false);
                $("#formView01 select").attr('disabled', false);
                $("#formView01 textarea").attr('disabled', false);
                $("#formView01 span").css("pointer-events", "auto");
                $("#formView01 .cqc-calendar").unbind('click', false);
                axDialog.close();
                caller.formView01.clear();
                caller.formView01.setSingleData("closeDate", getFormattedDate(new Date()));
            },
            options: {
                onError: viewError
            }
        });
        return false;
    },
    FORM_SAVE: function (caller, act, data) {
        if (caller.formView01.validate()) {
            var parentData = caller.formView01.getData();
            parentData.stextSendGubun = '0';
            axboot.ajax({
                type: "PUT",
                url: "/api/v1//mng/cash/sh03001140",
                data: JSON.stringify(parentData),
                callback: function (res) {
                    caller.formView01.clear();
                    fnObj.gridView01.setPageData({pageNumber: 0});
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    axToast.push("저장 작업이 완료되었습니다.");
                },
                options: {
                    onError: viewError
                }
            });
        }
    },
    FORM_STEXT_SAVE: function (caller, act, data) {
        if (caller.formView01.validate()) {
            var parentData = caller.formView01.getData();
            parentData.stextSendGubun = '1';
            axboot.ajax({
                type: "GET",
                url: "/api/v1//mng/cash/sh03001140",
                data: $.extend({stextSend: true}, parentData),
                callback: function () {
                    caller.formView01.clear();
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    axToast.push("회수자금결과가 전송되었습니다.");

                },
                options: {
                    onError: viewError
                }
            });
        }
    },
    MODAL_OPEN: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_TERMINAL_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisa": $("#jisaCode").val()
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
    FORM_MODAL_OPEN: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_TERMINAL_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisa": $("#jisaCodeForm").val()
                };
            },
            callback: function (data) {
                $("#jisaCodeForm").val(data.jisaCode);
                $("#branchNameForm").val(data.branchName);
                $("#branchCodeForm").val(data.branchCode);
                $("#cornerNameForm").val(data.cornerName);
                $("#terminalNoForm").val(data.terminalNo);

                ACTIONS.dispatch(ACTIONS.FORM_PAGE_SEARCH);

                this.close();
            }
        });
    },
    FORM_PAGE_SEARCH: function (caller, act, data) {
        var parentData = fnObj.formView01.getData();
        parentData.closeGubun = '1';
        parentData.reqDate = parentData.closeDate;
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/cash/sh03001130",
            data: $.extend({findCloseAmt: true}, parentData, null),
            callback: function (res) {
                if (res.rtrvlAmt == undefined) {
                    fnObj.formView01.setSingleData("prevDayCashSendingAmt", "");
                    fnObj.formView01.setSingleData("depositAmt", "");
                    fnObj.formView01.setSingleData("giveAmt", "");
                    fnObj.formView01.setSingleData("closeAmt", "");
                    formError('\n' + '조회하신 일자의 마감조회 정보가 없습니다.');
                } else {
                    var resData = res;
                    resData.prevDayCashSendingAmt = res.prevDayPutAmt;
                    resData.depositAmt = res.cashDepositAmt;
                    resData.giveAmt = res.cashGiveAmt;
                    resData.closeAmt = res.rtrvlAmt;
                    resData.closeDate = res.reqDate;

                    // $('#prevDayCashSendingAmt').val(resData.prevDayCashSendingAmt);
                    // $('#depositAmt').val(resData.depositAmt);
                    // $('#giveAmt').val(resData.giveAmt);

                    fnObj.formView01.setData(resData);
                    fnObj.formView01.setSingleData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[resData.branchCode]);
                    fnObj.formView01.setSingleData("cornerName", parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[resData.terminalNo]);
                    // fnObj.formView01.setSingleData("prevDayCashSendingAmt", res.prevDayPutAmt);
                    // fnObj.formView01.setSingleData("depositAmt", res.cashDepositAmt);
                    // fnObj.formView01.setSingleData("giveAmt", res.cashGiveAmt);
                    // fnObj.formView01.setSingleData("closeAmt", res.rtrvlAmt);
                }
            },
            options: {
                onError: viewError
            }
        });
    },
    FORM_CLEAR: function (caller, act, data) {
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                $("#formView01 input").attr('disabled', false);
                $("#formView01 select").attr('disabled', false);
                $("#formView01 textarea").attr('disabled', false);
                $("#formView01 span").css("pointer-events", "auto");
                $("#formView01 .cqc-calendar").unbind('click', false);
                axDialog.close();
                caller.formView01.clear();
                caller.formView01.setSingleData("closeDate", getFormattedDate(new Date()));
            }
        });
    },
    ITEM_CLICK: function (caller, act, data) {
        caller.formView01.setData(data);
        caller.formView01.setSingleData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[data.branchCode]);
        caller.formView01.setSingleData("cornerName", parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[data.terminalNo]);

        if (data.stextSendGubun == 1) {
            $("#formView01 input").attr('disabled', true);
            $("#formView01 select").attr('disabled', true);
            $("#formView01 textarea").attr('disabled', true);
            $("#formView01 span").css("pointer-events", "none");
            $("#formView01 .cqc-calendar").bind('click', false);
        } else {
            $("#formView01 input").attr('disabled', false);
            $("#formView01 select").attr('disabled', false);
            $("#formView01 textarea").attr('disabled', false);
            $("#formView01 span").css("pointer-events", "auto");
            $("#formView01 .cqc-calendar").unbind('click', false);
        }
    },
    EXCEL_DOWNLOAD: function (caller, act, data) {
        var params = buildParams($.extend({}, this.searchView.getData()));
        console.log(params);
        window.location = CONTEXT_PATH + "/api/v1//mng/cash/sh03001140/download?" + params;
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
    var _this = this;

    CODE = this; // this는 call을 통해 수집된 데이터들.

    _this.pageButtonView.initView();
    _this.searchView.initView();
    _this.gridView01.initView();
    _this.formView01.initView();

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
                fnObj.gridView01.excel("회수자금결과통보-" + getFormattedDate(new Date()) + ".xls");
                // ACTIONS.dispatch(ACTIONS.EXCEL_DOWNLOAD);
            },
            "search-view-clear": function () {
                $("#filter").val("");
                if(sessionJson.userId == "system" || sessionJson.jisaCode == "00") {
                    $("#jisaCode").val("");
                }
                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
                $("#terminalNo").val("");
                $("#startDate").val(getFormattedDate(new Date(), true));
                $("#endDate").val(getFormattedDate(new Date(), false));
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
        this.jisaCode = $("#jisaCode");
        this.branchCode = $("#branchCode");
        this.terminalNo = $("#terminalNo");
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

        $("#startDate").val(getFormattedDate(new Date(), true));
        $("#endDate").val(getFormattedDate(new Date(), false));

        axboot.buttonClick(this, "data-searchview-btn", {
            "modal": function () {
                ACTIONS.dispatch(ACTIONS.MODAL_OPEN)
            },
            "form_modal": function () {
                ACTIONS.dispatch(ACTIONS.FORM_MODAL_OPEN)
            }
        });

        $('#jisaCode').change(
            function () {
                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
                $("#terminalNo").val("");
            });

        $('#branchCode').change(
            function () {
                $("#branchName").val("");
            });

        $('#terminalNo').change(
            function () {
                $("#cornerName").val("");
            });
    },
    getData: function () {
        return {
            filter: this.filter.val(),
            jisaCode: $("#jisaCode").val(),
            branchName: $("#branchName").val(),
            branchCode: $("#branchCode").val(),
            cornerName: $("#cornerName").val(),
            terminalNo: $("#terminalNo").val(),
            startDate: $("#startDate").val(),
            endDate: $("#endDate").val()
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
            showLineNumber: true,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {
                    key: 'jisaCode', label: '지사명', width: 70, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["JISA_CODE"].map[this.value];
                    }
                },
                {
                    key: 'branchCode', label: '지점명', width: 150, align: 'left',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["BRANCH_CODE"].map[this.value];
                    }
                },
                {
                    key: 'terminalNo', label: '코너명', width: 150, align: 'left',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[this.value];
                    }
                },
                {key: 'terminalNo', label: '단말번호', width: 70, align: 'center'},
                {
                    key: 'stextSendGubun', label: '전송여부', width: 100, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["STEXT_SEND_GUBUN"].map[this.value];
                    }
                },
                {key: 'closeDate', label: '마감일자', width: 100, align: 'center'},
                {key: 'prevDayCashSendingAmt', label: '전일현송액', width: 130, align: 'right', formatter: "money"},
                {key: 'depositAmt', label: '입금금액', width: 130, align: 'right', formatter: "money"},
                {key: 'giveAmt', label: '지급금액', width: 130, align: 'right', formatter: "money"},
                {key: 'closeAmt', label: '마감금액', width: 130, align: 'right', formatter: "money"},
                {key: 'noneProcessAmt', label: '미처리금액', width: 130, align: 'right', formatter: "money"},
                {key: 'rtrvlFund', label: '회수자금', width: 130, align: 'right', formatter: "money"},
                {key: 'adjustLackAmt', label: '과여금조정금액', width: 130, align: 'right', formatter: "money"},
                {key: 'adjustLackAmtCount', label: '과여금조정건수', width: 100, align: 'center', formatter: "money"},
                {
                    key: 'noneProcessAt', label: '미처리건 유무', width: 90, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["NONE_PROCESS_AT"].map[this.value];
                    }
                },
                {key: 'mngOffice', label: '관리사무소', width: 150, align: 'center'}
            ],
            footSum: [
                [
                    {label: "", colspan: 4, align: "center"},
                    {label: "합계", colspan: 2, align: "center"},
                    {key: "prevDayCashSendingAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "depositAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "giveAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "closeAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "noneProcessAmt", collector: "sum", formatter: "money", align: "right"},
                    {key: "rtrvlFund", collector: "sum", formatter: "money", align: "right"},
                    {key: "adjustLackAmt", collector: "sum", formatter: "money", align: "right"},
                    {label: "", colspan: 3, align: "center"}
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
                return this.branchCode && this.terminalNo && this.closeDate;
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
 * formView01
 */
fnObj.formView01 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {});
    },
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();

        if(sessionJson.userId != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm").attr('readonly', true);
            $("#jisaCodeForm").attr('disabled', true);
            $("#jisaCodeForm").val(sessionJson.jisaCode);
        }

        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            },
            onStateChanged: function (type) {
                if (type.state == 'changeValue') {
                    if ($("#branchCodeForm").val() == "" && $("#terminalNoForm").val() == "") {
                        // var message = '\n' + '지점,단발번호는 필수 입력조건입니다.';
                        // formError(message);
                        fnObj.formView01.setSingleData("prevDayCashSendingAmt", "");
                        fnObj.formView01.setSingleData("depositAmt", "");
                        fnObj.formView01.setSingleData("giveAmt", "");
                        fnObj.formView01.setSingleData("closeAmt", "");
                    } else {
                        ACTIONS.dispatch(ACTIONS.FORM_PAGE_SEARCH);
                    }
                }
            }
        });
        this.target.find('[data-ax5picker="date"]').ax5picker("setValue", 0, getFormattedDate(new Date()));

        axboot.buttonClick(this, "data-form-view-01-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
            "save": function () {
                if (fnObj.formView01.getData().stextSendGubun == 1) {
                    formError("전송완료된 보고서입니다.")
                } else {
                    ACTIONS.dispatch(ACTIONS.FORM_SAVE);
                }
            },
            "add": function () {
                if (fnObj.formView01.getData().stextSendGubun == 1) {
                    formError("전송완료된 보고서입니다.")
                } else {
                    ACTIONS.dispatch(ACTIONS.FORM_STEXT_SAVE);
                }
            }
        });

        $('#jisaCodeForm').change(
            function () {
                $("#branchNameForm").val("");
                $("#branchCodeForm").val("");
                $("#cornerNameForm").val("");
                $("#terminalNoForm").val("");
            });

        $('#prevDayCashSendingAmt').keyup(
            function () {
                var closeAmt = Number($('#prevDayCashSendingAmt').val().replace(/,/g, "")) + Number($('#depositAmt').val().replace(/,/g, "")) - Number($('#giveAmt').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("closeAmt", closeAmt.toLocaleString());

                var noneProcessAmt = closeAmt - Number($('#rtrvlFund').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("noneProcessAmt", noneProcessAmt.toLocaleString());
            });

        $('#depositAmt').keyup(
            function () {
                var closeAmt = Number($('#prevDayCashSendingAmt').val().replace(/,/g, "")) + Number($('#depositAmt').val().replace(/,/g, "")) - Number($('#giveAmt').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("closeAmt", closeAmt.toLocaleString());

                var noneProcessAmt = closeAmt - Number($('#rtrvlFund').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("noneProcessAmt", noneProcessAmt.toLocaleString());
            });

        $('#giveAmt').keyup(
            function () {
                var closeAmt = Number($('#prevDayCashSendingAmt').val().replace(/,/g, "")) + Number($('#depositAmt').val().replace(/,/g, "")) - Number($('#giveAmt').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("closeAmt", closeAmt.toLocaleString());

                var noneProcessAmt = closeAmt - Number($('#rtrvlFund').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("noneProcessAmt", noneProcessAmt.toLocaleString());
            });

        $('#rtrvlFund').keyup(
            function () {
                var noneProcessAmt = Number($('#closeAmt').val().replace(/,/g, "")) - Number($('#rtrvlFund').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("noneProcessAmt", noneProcessAmt.toLocaleString());
            });
    },
    initEvent: function () {
        var _this = this;
    },
    getData: function () {
        return {
            jisaCode: $("#jisaCodeForm").val(),
            branchCode: $("#branchCodeForm").val(),
            terminalNo: $("#terminalNoForm").val(),
            closeDate: $("#closeDate").val(),
            prevDayCashSendingAmt: $("#prevDayCashSendingAmt").val(),
            depositAmt: $("#depositAmt").val(),
            giveAmt: $("#giveAmt").val(),
            closeAmt: $("#closeAmt").val(),
            noneProcessAmt: $("#noneProcessAmt").val(),
            noneProcessAt: $("#noneProcessAt").val(),
            mngOffice: $("#mngOffice").val(),
            rtrvlFund: $("#rtrvlFund").val(),
            adjustLackAmtCount: $("#adjustLackAmtCount").val(),
            adjustLackAmt: $("#adjustLackAmt").val(),
            memoContent: $("#memoContent").val()
        };
    },
    setData: function (data) {

        if (typeof data === "undefined") data = this.getDefaultData();
        data = $.extend({}, data);

        this.target.find('[data-ax-path="key"]').attr("readonly", "readonly");

        this.model.setModel(data);
        this.modelFormatter.formatting(); // 입력된 값을 포메팅 된 값으로 변경
    },
    setSingleData: function (dataPath, value) {
        this.model.set(dataPath, value);
    },
    validate: function () {
        var rs = this.model.validate();
        var title;
        var message;

        if ($("#jisaCodeForm").val() == "") {
            message = '\n' + '지사는 필수 입력조건입니다.\n' + '지사를 선택하세요.';
            $("#jisaCodeForm").focus();
            formError(message);
            return false;
        } else if ($("#branchCodeForm").val() == "") {
            message = '\n' + '지점은 필수 입력조건입니다.\n' + '지점을 선택하세요.';
            $("#branchCodeForm").focus();
            formError(message);
            return false;
        } else if ($("#terminalNoForm").val() == "") {
            message = '\n' + '단말번호는 필수 입력조건입니다.\n' + '단말번호를 선택하세요.';
            $("#terminalNoForm").focus();
            formError(message);
            return false;
        } else if (rs.error) {
            title = rs.error[0].jquery.attr("title");
            message = '\n' + title + '는 필수 검색조건입니다.\n' + title + '를 입력하세요.';
            rs.error[0].jquery.focus();
            formError(message);
            return false;
        }
        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
        this.target.find('[data-ax5picker="date"]').ax5picker("setValue", 0, getFormattedDate(new Date()));
        if(sessionJson.userId != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm").attr('readonly', true);
            $("#jisaCodeForm").attr('disabled', true);
            $("#jisaCodeForm").val(sessionJson.jisaCode);
        }
    }
});

var buildParams = function (json) {

    var params = JSON.stringify(json);

    params = params.replace(/{/g, "");
    params = params.replace(/}/g, "");
    params = params.replace(/:/g, "=")
    params = params.replace(/,/g, "&");
    params = params.replace(/"/g, "");

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

var pageSearchAndviewError = function (err) {
    fnObj.gridView01.setPageData({pageNumber: 0});
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    viewError(err);
}

function getFormattedDate(date, isStart) {
    var day;
    var tempDate;
    if (isStart) {
        date.setDate(date.getDate() - 7);
        tempDate = date.getDate();
    } else {
        tempDate = date.getDate();
    }
    day = tempDate.toString();

    var year = date.getFullYear();
    var month = (1 + date.getMonth()).toString();
    month = month.length > 1 ? month : '0' + month;
    day = day.length > 1 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}