var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/cash/sh03001190",
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
                caller.formView01.setSingleData("reqDate", getFormattedDate(new Date()));
                caller.formView01.setSingleData("fundExpenseStartDate", getFormattedDate(new Date()));
                caller.formView01.setSingleData("fundExpenseEndDate", getFormattedDate(new Date()));
                caller.formView01.setSingleData("fundExpenseTerm", '1');
            },
            options: {
                onError: viewError
            }
        });
        return false;
    },
    MODAL_OPEN: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_BRANCH_MODAL",
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
            parentData.fundExpenseTerm = $('#fundExpenseTerm').val();
            parentData.stextSendGubun = '0';
            parentData.mngOffice = parentData.mngOffice || '';
            axboot
                .call({
                    type: "PUT",
                    url: "/api/v1//mng/cash/sh03001190",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
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
            parentData.fundExpenseTerm = $('#fundExpenseTerm').val();
            parentData.stextSendGubun = '1';
            parentData.mngOffice = parentData.mngOffice || '';
            axboot.ajax({
                type: "GET",
                url: "/api/v1//mng/cash/sh03001190",
                data: $.extend({stextSend: true}, parentData),
                callback: function (res) {
                    fnObj.gridView01.setPageData({pageNumber: 0});
                    if (res.status == 99) {
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                        var message = '\n' + '운영자금청구서 통보 전문응답코드가 99입니다.';
                        formError(message);
                    } else {
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                        axToast.push("운영자금청구서 통보 전문이 전송되었습니다.");
                    }
                },
                options: {
                    onError: viewError
                }
            });
        }
    },
    FORM_MODAL_OPEN: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_BRANCH_MODAL",
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
                caller.formView01.setSingleData("reqDate", getFormattedDate(new Date()));
                caller.formView01.setSingleData("fundExpenseStartDate", getFormattedDate(new Date()));
                caller.formView01.setSingleData("fundExpenseEndDate", getFormattedDate(new Date()));
                caller.formView01.setSingleData("fundExpenseTerm", '1');
            }
        });
    },
    ITEM_CLICK: function (caller, act, data) {
        caller.formView01.setData(data);
        caller.formView01.setSingleData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[data.branchCode]);
        caller.formView01.setSingleData("cornerName", parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[data.terminalNo]);

        var totalStockAmt = Number($('#beforeBdateStockAmt').val().replace(/,/g, "")) + Number($('#beforeBdateRecvAmt').val().replace(/,/g, "")) - Number($('#beforeBdateCashSendingAmt').val().replace(/,/g, "")) - Number($('#nowDayAddCashSendingAmt').val().replace(/,/g, "")) - Number($('#thisDayAddCashSendingAmt').val().replace(/,/g, "")) + Number($('#nowDayRtrvlAmt').val().replace(/,/g, "")) + Number($('#thisDayNoneLoadAmt').val().replace(/,/g, "")) + Number($('#paymentOverAmt').val().replace(/,/g, ""))
        fnObj.formView01.setSingleData("totalStockAmt", totalStockAmt.toLocaleString());

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
            $("#formView01 .cqc-calendar").bind('click', false);
        }
    },
    EXCEL_DOWNLOAD: function (caller, act, data) {
        var params = buildParams($.extend({}, this.searchView.getData()));
        console.log(params);
        window.location = CONTEXT_PATH + "/api/v1//mng/cash/sh03001190/download?" + params;
        return false;
    },

    SEND_AMT_SEARCH: function (caller, act, data) {
        axboot
            .call({
                type: "GET",
                url: "/api/v1//mng/cash/sh03001190",
                data: {
                    findFormAmt: true,
                    reqDate: $('#reqDate').val()
                },
                callback: function (res) {
                    fnObj.formView01.setSingleData("thisDayAddCashSendingAmt", Number(res[0]["data1"]).toLocaleString()); //금일 추가현송금액
                    fnObj.formView01.setSingleData("nowDayAddCashSendingAmt", Number(res[1]["data1"]).toLocaleString()); //휴일 추가현송금액
                    fnObj.formView01.setSingleData("beforeBdateDepositAmt", Number(res[2]["data1"]).toLocaleString()); //금일 입금금액
                    fnObj.formView01.setSingleData("beforeBdateGiveAmt", Number(res[2]["data2"]).toLocaleString()); //금일 지급금액
                    fnObj.formView01.setSingleData("nowDayRtrvlAmt", Number(res[2]["data3"]).toLocaleString()); //금일 회수금액
                    fnObj.formView01.setSingleData("beforeBdateCashSendingAmt", Number(res[3]["data1"]).toLocaleString()); //금일 정규현송금액
                    fnObj.formView01.setSingleData("beforeBdateStockAmt", Number(res[4]["data1"]).toLocaleString()); //금일 재고금액

                    var totalStockAmt = Number($('#beforeBdateStockAmt').val().replace(/,/g, "")) + Number($('#beforeBdateRecvAmt').val().replace(/,/g, "")) - Number($('#beforeBdateCashSendingAmt').val().replace(/,/g, "")) - Number($('#nowDayAddCashSendingAmt').val().replace(/,/g, "")) - Number($('#thisDayAddCashSendingAmt').val().replace(/,/g, "")) + Number($('#nowDayRtrvlAmt').val().replace(/,/g, "")) + Number($('#thisDayNoneLoadAmt').val().replace(/,/g, "")) + Number($('#paymentOverAmt').val().replace(/,/g, ""))
                    fnObj.formView01.setSingleData("totalStockAmt", totalStockAmt.toLocaleString());
                }
            })
            .done(function () {

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
    ACTIONS.dispatch(ACTIONS.SEND_AMT_SEARCH);
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
                fnObj.gridView01.excel("운영자금청구서통보-" + getFormattedDate(new Date()) + ".xls");
                // ACTIONS.dispatch(ACTIONS.EXCEL_DOWNLOAD);
            },
            "search-view-clear": function () {
                if (sessionJson.userCd == "system" || sessionJson.jisaCode == "00") {
                    $("#jisaCode").val("");
                }
                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
                $("#branchCode").val("");
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
        this.jisaCode = $("#jisaCode");
        this.branchCode = $("#branchCode");
        this.terminalNo = $("#terminalNo");
        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        if (sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCode").attr('readonly', true);
            $("#jisaCode").attr('disabled', true);
            $("#jisaCode").val(sessionJson.jisaCode);
        }

        $("#startDate").val(getFormattedDate(new Date(), true));
        $("#endDate").val(getFormattedDate(new Date(), false));

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
            jisaCode: $("#jisaCode").val(),
            branchCode: $("#branchCode").val(),
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
            frozenColumnIndex: 0,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {key: 'txId', label: '처리일시', width: 130, align: 'center'},
                {key: 'reqDate', label: '요청일자', width: 100, align: 'center'},
                {
                    key: 'jisaCode', label: '지사명', width: 70, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["JISA_CODE"].map[this.value];
                    }
                },
                {
                    key: 'branchCode', label: '지점명', width: 150, align: 'left',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["MANLESS_BRANCH_CODE"].map[this.value];
                    }
                },
                {
                    key: 'stextSendGubun', label: '전송여부', width: 100, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["STEXT_SEND_GUBUN"].map[this.value];
                    }
                },
                {
                    key: undefined, label: '자금소요기간', align: 'center',
                    columns: [
                        {key: 'fundExpenseStartDate', label: '시작일', width: 100, align: 'center'},
                        {key: 'fundExpenseEndDate', label: '종료일', width: 100, align: 'center'},
                        {key: 'fundExpenseTerm', label: '소요기간', width: 80, align: 'center'}
                    ]
                },
                {key: 'nextDayCashSendingAmt', label: '익익 영업일 정규현송금액', width: 160, align: 'right', formatter: "money"},
                {key: 'nowDayAddCashSendingAmt', label: '휴일 추가현송금액', width: 130, align: 'right', formatter: "money"},
                {key: 'nowDayRtrvlAmt', label: '금일 회수금액', width: 130, align: 'right', formatter: "money"},
                {key: 'nowDayLackAmt', label: '금일 과여금액', width: 130, align: 'right', formatter: "money"},
                {key: 'thisDayRtrvlExcpectAmt', label: '금일 회수예상금액', width: 130, align: 'right', formatter: "money"},
                {key: 'nextDayRtrvlExcpectAmt', label: '익일 회수예상금액', width: 130, align: 'right', formatter: "money"},

                {key: 'nextDayBillingAmt', label: '익일 청구금액', width: 130, align: 'right', formatter: "money"},
                {key: 'totalStockAmt', label: '금일 재고금액', width: 130, align: 'right', formatter: "money"},

                {key: 'beforeBdateStockAmt', label: '전일 재고금액', width: 130, align: 'right', formatter: "money"},
                {key: 'beforeBdateRecvAmt', label: '금일 현수금액', width: 130, align: 'right', formatter: "money"},

                {key: 'beforeBdateCashSendingAmt', label: '금일 정규현송금액', width: 130, align: 'right', formatter: "money"},
                {key: 'beforeBdateGiveAmt', label: '전영업일 지급금액', width: 130, align: 'right', formatter: "money"},

                {key: 'beforeBdateDepositAmt', label: '전영업일 입금금액', width: 130, align: 'right', formatter: "money"},
                {key: 'thisDayAddCashSendingAmt', label: '금일추가현송금액', width: 130, align: 'right', formatter: "money"},

                {key: 'thisDayNoneLoadAmt', label: '금일미장전금액', width: 130, align: 'right', formatter: "money"},
                {key: 'paymentOverAmt', label: '출납과잉금', width: 130, align: 'right', formatter: "money"},

                {key: 'mngOffice', label: '관리사무소', width: 150, align: 'center'}
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
                return this.branchCode && this.reqDate;
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

        if (sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
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
                    // ACTIONS.dispatch(ACTIONS.SEND_AMT_SEARCH);
                }
            }
        });

        this.setSingleData("reqDate", getFormattedDate(new Date()));
        this.setSingleData("fundExpenseStartDate", getFormattedDate(new Date()));
        this.setSingleData("fundExpenseEndDate", getFormattedDate(new Date()));
        this.setSingleData("fundExpenseTerm", '1');

        axboot.buttonClick(this, "data-form-view-01-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
            "save": function () {
                if (fnObj.formView01.getData().stextSendGubun == 1) {
                    formError("전송완료된 청구서입니다.")
                } else {
                    ACTIONS.dispatch(ACTIONS.FORM_SAVE);
                }
            },
            "add": function () {
                if (fnObj.formView01.getData().stextSendGubun == 1) {
                    formError("전송완료된 청구서입니다.")
                } else {
                    ACTIONS.dispatch(ACTIONS.FORM_STEXT_SAVE);
                }
            },
            "modal": function () {
                ACTIONS.dispatch(ACTIONS.FORM_MODAL_OPEN)
            }
        });

        $('#reqDate').change(
            function () {
                ACTIONS.dispatch(ACTIONS.SEND_AMT_SEARCH);
            });


        // $('#jisaCodeForm').change(
        //     function () {
        //         $("#branchName").val("");
        //         $("#branchCode").val("");
        //         $("#cornerName").val("");
        //         $("#terminalNo").val("");
        //     });

        $('#fundExpenseStartDate').keyup(
            function () {
                var sParts = $('#fundExpenseStartDate').val().split('-');
                var sDate = new Date(sParts[0], sParts[1], sParts[2]);
                var eParts = $('#fundExpenseEndDate').val().split('-');
                var eDate = new Date(eParts[0], eParts[1], eParts[2]);

                if (sDate > eDate) {
                    // $('#fundExpenseStartDate').val($('#fundExpenseEndDate').val());
                    //$('#fundExpenseEndDate').val($('#fundExpenseStartDate').val());
                    fnObj.formView01.setSingleData("fundExpenseEndDate", $('#fundExpenseStartDate').val());
                    // formError('자금소요 시작일은 종료일보다 클수 없습니다.');
                }
                $('#fundExpenseTerm').val(calDateRange($('#fundExpenseStartDate').val(), $('#fundExpenseEndDate').val()) + 1);
            }
        );

        $('#fundExpenseEndDate').keyup(
            function () {
                var sParts = $('#fundExpenseStartDate').val().split('-');
                var sDate = new Date(sParts[0], sParts[1], sParts[2]);
                var eParts = $('#fundExpenseEndDate').val().split('-');
                var eDate = new Date(eParts[0], eParts[1], eParts[2]);

                if (sDate > eDate) {
                    //$('#fundExpenseEndDate').val($('#fundExpenseStartDate').val());
                    fnObj.formView01.setSingleData("fundExpenseEndDate", $('#fundExpenseStartDate').val());

                    formError('자금소요 종료일은 시작일보다 작을수 없습니다.');
                }
                $('#fundExpenseTerm').val(calDateRange($('#fundExpenseStartDate').val(), $('#fundExpenseEndDate').val()) + 1);
            }
        );

      /*  $('#beforeBdateStockAmt').change(
            function () {
                var totalStockAmt = Number($('#beforeBdateStockAmt').val().replace(/,/g, "")) + Number($('#beforeBdateRecvAmt').val().replace(/,/g, "")) - Number($('#beforeBdateCashSendingAmt').val().replace(/,/g, "")) - Number($('#nowDayAddCashSendingAmt').val().replace(/,/g, "")) - Number($('#thisDayAddCashSendingAmt').val().replace(/,/g, "")) + Number($('#nowDayRtrvlAmt').val().replace(/,/g, "")) + Number($('#thisDayNoneLoadAmt').val().replace(/,/g, "")) + Number($('#paymentOverAmt').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("totalStockAmt", totalStockAmt.toLocaleString());
            });
*/

      // 금일재고금액 계산
      $('#beforeBdateStockAmt').keyup(
            function () {
                var totalStockAmt = Number($('#beforeBdateStockAmt').val().replace(/,/g, "")) + Number($('#beforeBdateRecvAmt').val().replace(/,/g, "")) - Number($('#beforeBdateCashSendingAmt').val().replace(/,/g, "")) - Number($('#nowDayAddCashSendingAmt').val().replace(/,/g, "")) - Number($('#thisDayAddCashSendingAmt').val().replace(/,/g, "")) + Number($('#nowDayRtrvlAmt').val().replace(/,/g, "")) + Number($('#thisDayNoneLoadAmt').val().replace(/,/g, "")) + Number($('#paymentOverAmt').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("totalStockAmt", totalStockAmt.toLocaleString());
            });

        $('#beforeBdateRecvAmt').keyup(
            function () {
                var totalStockAmt = Number($('#beforeBdateStockAmt').val().replace(/,/g, "")) + Number($('#beforeBdateRecvAmt').val().replace(/,/g, "")) - Number($('#beforeBdateCashSendingAmt').val().replace(/,/g, "")) - Number($('#nowDayAddCashSendingAmt').val().replace(/,/g, "")) - Number($('#thisDayAddCashSendingAmt').val().replace(/,/g, "")) + Number($('#nowDayRtrvlAmt').val().replace(/,/g, "")) + Number($('#thisDayNoneLoadAmt').val().replace(/,/g, "")) + Number($('#paymentOverAmt').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("totalStockAmt", totalStockAmt.toLocaleString());
            });

        $('#beforeBdateCashSendingAmt').keyup(
            function () {
                var totalStockAmt = Number($('#beforeBdateStockAmt').val().replace(/,/g, "")) + Number($('#beforeBdateRecvAmt').val().replace(/,/g, "")) - Number($('#beforeBdateCashSendingAmt').val().replace(/,/g, "")) - Number($('#nowDayAddCashSendingAmt').val().replace(/,/g, "")) - Number($('#thisDayAddCashSendingAmt').val().replace(/,/g, "")) + Number($('#nowDayRtrvlAmt').val().replace(/,/g, "")) + Number($('#thisDayNoneLoadAmt').val().replace(/,/g, "")) + Number($('#paymentOverAmt').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("totalStockAmt", totalStockAmt.toLocaleString());
            });

        $('#nowDayAddCashSendingAmt').keyup(
            function () {
                var totalStockAmt = Number($('#beforeBdateStockAmt').val().replace(/,/g, "")) + Number($('#beforeBdateRecvAmt').val().replace(/,/g, "")) - Number($('#beforeBdateCashSendingAmt').val().replace(/,/g, "")) - Number($('#nowDayAddCashSendingAmt').val().replace(/,/g, "")) - Number($('#thisDayAddCashSendingAmt').val().replace(/,/g, "")) + Number($('#nowDayRtrvlAmt').val().replace(/,/g, "")) + Number($('#thisDayNoneLoadAmt').val().replace(/,/g, "")) + Number($('#paymentOverAmt').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("totalStockAmt", totalStockAmt.toLocaleString());
            });

        $('#thisDayAddCashSendingAmt').keyup(
            function () {
                var totalStockAmt = Number($('#beforeBdateStockAmt').val().replace(/,/g, "")) + Number($('#beforeBdateRecvAmt').val().replace(/,/g, "")) - Number($('#beforeBdateCashSendingAmt').val().replace(/,/g, "")) - Number($('#nowDayAddCashSendingAmt').val().replace(/,/g, "")) - Number($('#thisDayAddCashSendingAmt').val().replace(/,/g, "")) + Number($('#nowDayRtrvlAmt').val().replace(/,/g, "")) + Number($('#thisDayNoneLoadAmt').val().replace(/,/g, "")) + Number($('#paymentOverAmt').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("totalStockAmt", totalStockAmt.toLocaleString());
            });

        $('#nowDayRtrvlAmt').keyup(
            function () {
                var totalStockAmt = Number($('#beforeBdateStockAmt').val().replace(/,/g, "")) + Number($('#beforeBdateRecvAmt').val().replace(/,/g, "")) - Number($('#beforeBdateCashSendingAmt').val().replace(/,/g, "")) - Number($('#nowDayAddCashSendingAmt').val().replace(/,/g, "")) - Number($('#thisDayAddCashSendingAmt').val().replace(/,/g, "")) + Number($('#nowDayRtrvlAmt').val().replace(/,/g, "")) + Number($('#thisDayNoneLoadAmt').val().replace(/,/g, "")) + Number($('#paymentOverAmt').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("totalStockAmt", totalStockAmt.toLocaleString());
            });

        $('#thisDayNoneLoadAmt').keyup(
            function () {
                var totalStockAmt = Number($('#beforeBdateStockAmt').val().replace(/,/g, "")) + Number($('#beforeBdateRecvAmt').val().replace(/,/g, "")) - Number($('#beforeBdateCashSendingAmt').val().replace(/,/g, "")) - Number($('#nowDayAddCashSendingAmt').val().replace(/,/g, "")) - Number($('#thisDayAddCashSendingAmt').val().replace(/,/g, "")) + Number($('#nowDayRtrvlAmt').val().replace(/,/g, "")) + Number($('#thisDayNoneLoadAmt').val().replace(/,/g, "")) + Number($('#paymentOverAmt').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("totalStockAmt", totalStockAmt.toLocaleString());
            });

        $('#paymentOverAmt').keyup(
            function () {
                var totalStockAmt = Number($('#beforeBdateStockAmt').val().replace(/,/g, "")) + Number($('#beforeBdateRecvAmt').val().replace(/,/g, "")) - Number($('#beforeBdateCashSendingAmt').val().replace(/,/g, "")) - Number($('#nowDayAddCashSendingAmt').val().replace(/,/g, "")) - Number($('#thisDayAddCashSendingAmt').val().replace(/,/g, "")) + Number($('#nowDayRtrvlAmt').val().replace(/,/g, "")) + Number($('#thisDayNoneLoadAmt').val().replace(/,/g, "")) + Number($('#paymentOverAmt').val().replace(/,/g, ""))
                fnObj.formView01.setSingleData("totalStockAmt", totalStockAmt.toLocaleString());
            });
    },
    initEvent: function () {
        var _this = this;
    },
    get: function (dataPath) {
        return this.model.get(dataPath);
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
        }

        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
        if (sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm").attr('readonly', true);
            $("#jisaCodeForm").attr('disabled', true);
            $("#jisaCodeForm").val(sessionJson.jisaCode);
        }
        this.setSingleData("reqDate", getFormattedDate(new Date()));
        this.setSingleData("fundExpenseStartDate", getFormattedDate(new Date()));
        this.setSingleData("fundExpenseEndDate", getFormattedDate(new Date()));
        this.setSingleData("fundExpenseTerm", '1');
        ACTIONS.dispatch(ACTIONS.SEND_AMT_SEARCH);
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

function calDateRange(val1, val2) {
    var FORMAT = "-";
    // FORMAT을 포함한 길이 체크
    if (val1.length != 10 || val2.length != 10)
        return null;

    // FORMAT이 있는지 체크
    if (val1.indexOf(FORMAT) < 0 || val2.indexOf(FORMAT) < 0)
        return null;

    // 년도, 월, 일로 분리
    var start_dt = val1.split(FORMAT);
    var end_dt = val2.split(FORMAT);

    // 월 - 1(자바스크립트는 월이 0부터 시작하기 때문에...)
    // Number()를 이용하여 08, 09월을 10진수로 인식하게 함.
    start_dt[1] = (Number(start_dt[1]) - 1) + "";
    end_dt[1] = (Number(end_dt[1]) - 1) + "";

    var from_dt = new Date(start_dt[0], start_dt[1], start_dt[2]);
    var to_dt = new Date(end_dt[0], end_dt[1], end_dt[2]);

    return (to_dt.getTime() - from_dt.getTime()) / 1000 / 60 / 60 / 24;
}