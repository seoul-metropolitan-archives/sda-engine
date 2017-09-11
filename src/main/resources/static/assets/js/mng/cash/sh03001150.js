var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/cash/sh03001150",
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
                caller.formView01.setSingleData("dealDate", getFormattedDate(new Date()));
                caller.formView01.setSingleData("processDate", getFormattedDate(new Date()));
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
    FORM_SAVE: function (caller, act, data) {
        if (caller.formView01.validate()) {
            var parentData = caller.formView01.getData();
            parentData.stextSendGubun = '0';
            parentData.unusl = parentData.unusl || '';
            axboot
                .call({
                    type: "PUT",
                    url: "/api/v1//mng/cash/sh03001150",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
                        caller.formView01.clear();
                        fnObj.gridView01.setPageData({pageNumber: 0});
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                        axToast.push("저장 작업이 완료되었습니다.");
                    }
                })
                .done(function () {
                });
        }
    },
    FORM_STEXT_SAVE: function (caller, act, data) {
        if (caller.formView01.validate()) {
            var parentData = caller.formView01.getData();
            parentData.stextSendGubun = '1';
            parentData.unusl = parentData.unusl || '';
            axboot.ajax({
                type: "GET",
                url: "/api/v1//mng/cash/sh03001150",
                data: $.extend({stextSend: true}, parentData),
                callback: function (res) {
                    axToast.push("미처리금액보고가 전송되었습니다.");
                    caller.formView01.clear();
                    fnObj.gridView01.setPageData({pageNumber: 0});
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                },
                options: {
                    onError: viewError
                }
            });
        }
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
                caller.formView01.setSingleData("jisaCode", data.jisaCode);
                caller.formView01.setSingleData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[data.branchCode]);
                caller.formView01.setSingleData("branchCode", data.branchCode);
                caller.formView01.setSingleData("cornerName", parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[data.terminalNo]);
                caller.formView01.setSingleData("terminalNo", data.terminalNo);

                axboot.ajax({
                    type: "GET",
                    url: "/api/v1//mng/cash/sh03001150",
                    data: {
                        nextSeqNo: true,
                        jisaCode: data.jisaCode,
                        branchCode: data.branchCode,
                        terminalNo: data.terminalNo,
                        dealDate: $("#dealDateForm").val()
                    },
                    callback: function (res) {
                        if (res.noneProcessSeqNo == undefined) {
                            caller.formView01.setSingleData("noneProcessSeqNo", "001");
                        } else {
                            caller.formView01.setSingleData("noneProcessSeqNo", res.noneProcessSeqNo);
                        }
                    },
                    options: {
                        onError: viewError
                    }
                });

                this.close();
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
                caller.formView01.setSingleData("dealDate", getFormattedDate(new Date()));
                caller.formView01.setSingleData("processDate", getFormattedDate(new Date()));
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
        window.location = CONTEXT_PATH + "/api/v1//mng/cash/sh03001150/download?" + params;
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
                fnObj.gridView01.excel("미처리금액보고서-" + getFormattedDate(new Date()) + ".xls");
                // ACTIONS.dispatch(ACTIONS.EXCEL_DOWNLOAD);
            },
            "search-view-clear": function () {
                if(sessionJson.userCd == "system" || sessionJson.jisaCode == "00") {
                    $("#jisaCode").val("");
                }
                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
                $("#branchCode").val("");
                $("#terminalNo").val("");
                $("#dealType").val(""),
                $("#dealDate").val(getFormattedDate(new Date()));
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
        this.jisaCode = $("#jisaCode");
        this.branchCode = $("#branchCode");
        this.terminalNo = $("#terminalNo");
        this.dealType = $("#dealType");
        this.dealDate = $("#dealDate");
        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCode").attr('readonly', true);
            $("#jisaCode").attr('disabled', true);
            $("#jisaCode").val(sessionJson.jisaCode);
        }

        $("#dealDate").val(getFormattedDate(new Date()));

        axboot.buttonClick(this, "data-searchview-btn", {
            "modal": function () {
                ACTIONS.dispatch(ACTIONS.MODAL_OPEN)
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
            jisaCode: this.jisaCode.val(),
            branchCode: this.branchCode.val(),
            terminalNo: this.terminalNo.val(),
            dealType: this.dealType.val(),
            dealDate: this.dealDate.val()
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
            frozenColumnIndex: 0,
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
                {key: 'noneProcessSeqNo', label: '일련번호', width: 70, align: 'center'},
                {
                    key: 'dealType', label: '거래종류', width: 70, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["DEAL_TYPE"].map[this.value];
                    }
                },
                {key: 'dealDate', label: '거래일자', width: 100, align: 'center'},

                {key: 'noneProcessAmt', label: '미처리금액', width: 130, align: 'right', formatter: "money"},
                {key: 'statementNo', label: '전표번호', width: 70, align: 'center'},
                {key: 'dealBank', label: '거래은행', width: 70, align: 'center'},
                {key: 'cardAccountNo', label: '카드/계좌번호', width: 120, align: 'center'},
                {key: 'dealAmt', label: '거래금액', width: 130, align: 'right', formatter: "money"},
                {key: 'sendCommission', label: '송금수수료', width: 130, align: 'right', formatter: "money"},
                {key: 'rtnCommission', label: '반환수수료', width: 130, align: 'right', formatter: "money"},
                {key: 'managerName', label: '담당자명', width: 70, align: 'center'},
                {key: 'mngOffice', label: '관리사무소', width: 150, align: 'center'},
                {key: 'customerName', label: '고객명', width: 70, align: 'center'},
                {key: 'customerTelno', label: '고객연락처', width: 100, align: 'center'},
                {key: 'processDate', label: '처리일자', width: 100, align: 'center'},
                {key: 'unusl', label: '특이사항', width: 400, align: 'left'}
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
                return this.branchCode && this.terminalNo && this.noneProcessSeqNo && this.dealDate;
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
        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm").attr('readonly', true);
            $("#jisaCodeForm").attr('disabled', true);
            $("#jisaCodeForm").val(sessionJson.jisaCode);
        }

        $('#jisaCodeForm').change(
            function () {
                $("#branchNameForm").val("");
                $("#branchCodeForm").val("");
                $("#cornerNameForm").val("");
                $("#terminalNoForm").val("");
            });

        this.setSingleData("dealDate", getFormattedDate(new Date()));
        this.setSingleData("processDate", getFormattedDate(new Date()));

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
            },
            "modal": function () {
                ACTIONS.dispatch(ACTIONS.FORM_MODAL_OPEN)
            }
        });
    },
    initEvent: function () {
        var _this = this;
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        return $.extend({}, data);
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
            $("#branchNameForm").focus();
            formError(message);
            return false;
        } else if ($("#terminalNoForm").val() == "") {
            message = '\n' + '단말번호는 필수 입력조건입니다.\n' + '단말번호를 선택하세요.';
            $("#cornerNameForm").focus();
            formError(message);
            return false;
        } else if ($("#dealTypeForm").val() == "") {
            message = '\n' + '거래종류는 필수 입력조건입니다.\n' + '거래종류을 선택하세요.';
            $("#dealTypeForm").focus();
            formError(message);
            return false;
        } else if ($("#dealBank").val() == "") {
            message = '\n' + '거래은행은 필수 입력조건입니다.\n' + '거래은행을 선택하세요.';
            $("#dealBank").focus();
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
        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
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