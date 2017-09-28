var fnObj = {};
var pageSize = 1000;
var errorStatusList = {};
var handleResult = "";
var handleFailReason = "";
var cash10kEmptyStatus = "";
var cash50kEmptyStatus = "";

var selectedIndex = "";
var selectedErrorItem = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/mng/error/error_status",
            data: $.extend({pageSize: 1000}, this.searchView.getData()),
            callback: function (res) {
                errorStatusList = res.list;

                caller.errorStatus.initView();
                caller.errorStatus.setData(errorStatusList);
            },
            options: {
                onError: viewError
            }
        });
        return false;
    },
    ERROR_SEARCH: function (caller, act, data) {

        var parentData = fnObj.formView01.getData();
        parentData.startDate = $("#startDate").val();
        parentData.endDate = $("#endDate").val();

        if (parentData.txId == undefined) {
            formError('\n' + '장애목록에서 조회할 기기를 선택해주세요.');
            return;
        }

        axboot.ajax({
            type: "GET",
            url: "/api/v1/mng/error/error_status/history",
            data: $.extend({}, parentData, this.gridView01.getPageData()),
            callback: function (res) {
                // 장애이력 셋팅
                caller.gridView01.setData(res);
            },
            options: {
                onError: viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        axDialog.confirm({
            msg: "장애조치사항을 저장하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                var parentData = fnObj.formView04.getData(); //화면정보를 가져온다.
                var selectedGridData = fnObj.formView01.getData();
                var inputData = {
                    jisaCode: selectedGridData.jisaCode,
                    branchCode: selectedGridData.branchCode,
                    cornerCode: selectedGridData.cornerCode,
                    terminalNo: selectedGridData.terminalNo,
                    errorDatetime: selectedGridData.errorDatetime,
                    noticeContent: parentData.noticeContent,
                    customerInfo: parentData.customerInfo,
                    handleContent: parentData.handleContent,
                    lastModifyEmpName: sessionJson.userNm
                };
                axboot
                    .call({
                        type: "PUT",
                        url: "/api/v1/mng/error/error_handle_mng",
                        data: JSON.stringify(inputData),
                        callback: function (res) {
                            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                        }
                    })
                    .done(function () {
                        //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                        // ACTIONS.dispatch(ACTIONS.ITEM_CLICK, selectedErrorItem);
                        axToast.push("저장 작업이 완료되었습니다.");
                    });
            }
        });
    },
    FORM_CLEAR: function (caller, act, data) {
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                caller.formView01.clear();
                caller.gridView02.clear();
            }
        });
    },
    ITEM_CLICK: function (caller, act, data) {
        $("#terminalNo").val(data.txId);
        data.startDate = $("#startDate").val();
        data.endDate = $("#endDate").val();

        fnObj.formView04.setData(data);

        axboot
            .call({
                type: "GET",
                url: "/api/v1/mng/error/sh01001110",
                data: {
                    details: true,
                    branchCode: data.branchCode,
                    cornerCode: data.cornerCode,
                    terminalNo: data.terminalNo,
                    calleeReqSeqNo: data.calleeReqSeqNo,
                    errorDatetime: data.errorDate + " " + data.errorTime
                },
                callback: function (res) {
                    // 기기정보
                    res.orgName = parent.COMMON_CODE["ORG_CODE"].map['01'];
                    res.jisaName = parent.COMMON_CODE["JISA_CODE"].map[res.jisaCode];
                    res.terminalCorpName = parent.COMMON_CODE["TERMINAL_CORP_CODE"].map[res.terminalCorpCode];
                    res.modelName = parent.COMMON_CODE["MODEL_CODE"].map[res.terminalCorpCode + res.modelCode];
                    res.placeGubun = parent.COMMON_CODE["PLACE_GUBUN"].map[res.placeGubun];
                    res.boothCorp = parent.COMMON_CODE["BOOTH_CORP"].map[res.boothCorp];
                    res.boothType = parent.COMMON_CODE["BOOTH_TYPE"].map[res.boothType];
                    caller.formView02.setData(res);

                    // 기기상태
                    res.cashPartStatus = parent.COMMON_CODE["CASH_PART_STATUS"].map[res.cashPartStatus];
                    res.enableIcCard = parent.COMMON_CODE["ENABLE_IC_CARD"].map[res.enableIcCard];
                    res.idScannerStatus = parent.COMMON_CODE["ID_SCANNER_STATUS"].map[res.idScannerStatus];
                    res.checkPartStatus = parent.COMMON_CODE["CHECK_PART_STATUS"].map[res.checkPartStatus];
                    res.enableEmv = parent.COMMON_CODE["ENABLE_EMV"].map[res.enableEmv];
                    res.bioScannerStatus = parent.COMMON_CODE["BIO_SCANNER_STATUS"].map[res.bioScannerStatus];
                    res.portfolioPartStatus = parent.COMMON_CODE["PORTFOLIO_PART_STATUS"].map[res.portfolioPartStatus];
                    res.enableIr = parent.COMMON_CODE["ENABLE_IR"].map[res.enableIr];
                    res.scrtyCardHighendAtmOnly = parent.COMMON_CODE["SCRTY_CARD_HIGHEND_ATM_ONLY"].map[res.scrtyCardHighendAtmOnly];
                    res.jrnlPartStatus = parent.COMMON_CODE["JRNL_PART_STATUS"].map[res.jrnlPartStatus];
                    res.enableRf = parent.COMMON_CODE["ENABLE_RF"].map[res.enableRf];
                    res.cardStyleOtp = parent.COMMON_CODE["CARD_STYLE_OTP"].map[res.cardStyleOtp];
                    res.rtrvlBoxStatus = parent.COMMON_CODE["RTRVL_BOX_STATUS"].map[res.rtrvlBoxStatus];
                    res.enableFingerprint = parent.COMMON_CODE["ENABLE_FINGERPRINT"].map[res.enableFingerprint];
                    res.s20General = parent.COMMON_CODE["S20_GENERAL"].map[res.s20General];
                    res.cardPartStatus = parent.COMMON_CODE["CARD_PART_STATUS"].map[res.cardPartStatus];
                    res.encryptionStatus = parent.COMMON_CODE["ENCRYPTION_STATUS"].map[res.encryptionStatus];
                    res.s20Frpy = parent.COMMON_CODE["S20_FRPY"].map[res.s20Frpy];
                    res.bnkbPartStatus = parent.COMMON_CODE["BNKB_PART_STATUS"].map[res.bnkbPartStatus];
                    res.slineGeneral = parent.COMMON_CODE["SLINE_GENERAL"].map[res.slineGeneral];
                    res.giroPartStatus = parent.COMMON_CODE["GIRO_PART_STATUS"].map[res.giroPartStatus];
                    res.slineFrpy = parent.COMMON_CODE["SLINE_FRPY"].map[res.slineFrpy];
                    res.suspendStatus = parent.COMMON_CODE["SUSPEND_STATUS"].map[res.suspendStatus];
                    res.cashPartStatus50kWon = parent.COMMON_CODE["CASH_PART_STATUS_50K_WON"].map[res.cashPartStatus50kWon];
                    res.fourtuneGeneral = parent.COMMON_CODE["FOURTUNE_GENERAL"].map[res.fourtuneGeneral];
                    res.hwErrorStatus = parent.COMMON_CODE["HW_ERROR_STATUS"].map[res.hwErrorStatus];
                    res.atmcExcclcTerminalError = parent.COMMON_CODE["ATMC_EXCCLC_TERMINAL_ERROR"].map[res.atmcExcclcTerminalError];
                    res.fourtuneFrpy = parent.COMMON_CODE["FOURTUNE_FRPY"].map[res.fourtuneFrpy];
                    res.maintenanceStatus = parent.COMMON_CODE["MAINTENANCE_STATUS"].map[res.maintenanceStatus];
                    res.atmcExcclcExecResult = parent.COMMON_CODE["ATMC_EXCCLC_EXEC_RESULT"].map[res.atmcExcclcExecResult];
                    res.rcppayBnkb = parent.COMMON_CODE["RCPPAY_BNKB"].map[res.rcppayBnkb];
                    res.enableDesBoard = parent.COMMON_CODE["ENABLE_DES_BOARD"].map[res.enableDesBoard];
                    res.cardIssuedTerminalStatus = parent.COMMON_CODE["CARD_ISSUED_TERMINAL_STATUS"].map[res.cardIssuedTerminalStatus];

                    caller.formView03.setData(res);

                    // 장애기본정보에 경비사 및 계약번호 전문구분등 셋팅
                    caller.formView01.setFormData("securityCorpName", parent.COMMON_CODE["SECURITY_CORP"].map[res.securityCorp]);
                    caller.formView01.setFormData("securityCorpContractNo", res.securityCorpContractNo);
                    caller.formView01.setFormData("stextGubun", parent.COMMON_CODE["ERROR_STEXT_GUBUN"].map[res.stextGubun]);
                    caller.formView01.setFormData("totalClassifyCodeName", parent.COMMON_CODE["TOTAL_CLASSIFY_CODE"].map[res.totalClassifyCode]);

                    // 자체출동 기본 값 셋팅
                    caller.formView05.setData(fnObj.formView01.getData());

                    // 출동요청일련번호 생성
                    caller.formView05.setFormData("calleeGubun", "자체출동");
                    caller.formView05.setFormData("calleeReqSeqNo", getCalleeSeqNo());
                    caller.formView05.setFormData("terminalCorpCode", res.terminalCorpCode);
                    caller.formView05.setFormData("modelCode", res.modelCode);
                    caller.formView05.setFormData("securityCorp", res.securityCorp);
                    caller.formView05.setFormData("calleeReqReasonCode", '3');
                    caller.formView05.setFormData("totalClassifyCode", res.totalClassifyCode);

                    // 자체출동취소 기본 값 셋팅
                    caller.formView06.setData(fnObj.formView01.getData());
                    caller.formView06.setFormData("calleeGubun", fnObj.formView01.getData().calleeGubun);
                    caller.formView06.setFormData("calleeReqSeqNo", fnObj.formView01.getData().calleeReqSeqNo);
                    caller.formView06.setFormData("calleeReqReasonCode", '3');
                    caller.formView06.setFormData("securityCorp", res.securityCorp);
                    caller.formView06.setFormData("totalClassifyCode", res.totalClassifyCode);
                },
                options: {
                    onError: viewError
                }
            })
            .call({
                    type: "GET",
                    url: "/api/v1//mng/error/sh01001150",
                    data: {
                        details: true,
                        branchCode: data.branchCode,
                        cornerCode: data.cornerCode,
                        terminalNo: data.terminalNo,
                        calleeReqSeqNo: data.calleeReqSeqNo,
                        errorDatetime: data.errorDate + " " + data.errorTime
                    },
                    callback: function (res) {
                        // 장애정보 셋팅 후 값을 추가적으로 셋팅하기 위해 값을 저장 아래 .done 에서 처리
                        handleResult = parent.COMMON_CODE["HANDLE_RESULT"].map[res.handleResult];
                        handleFailReason = parent.COMMON_CODE["HANDLE_FAIL_REASON"].map[res.handleFailReason];
                    },
                    options: {
                        onError: viewError
                    }
                }
            )
            .call({
                    type: "GET",
                    url: "/api/v1//mng/cash/sh03001110/current",
                    data: {
                        jisaCode: data.jisaCode,
                        branchCode: data.branchCode,
                        terminalNo: data.terminalNo,
                        referDate: getFormattedDate(new Date())
                    },
                    callback: function (res) {
                        // 장애정보 셋팅 후 값을 추가적으로 셋팅하기 위해 값을 저장 아래 .done 에서 처리
                        if (checkUndefined(res.cash10kGiveEnableCount) != "") {
                            cash10kEmptyStatus = (res.cash10kGiveEnableCount * 10000).toLocaleString();
                        }

                        if (checkUndefined(res.cash50kGiveEnableCount) != "") {
                            cash50kEmptyStatus = (res.cash50kGiveEnableCount * 50000).toLocaleString();
                        }
                    },
                    options: {
                        onError: viewError
                    }
                }
            )
            .call({
                    type: "GET",
                    url: "/api/v1/mng/error/error_status/history",
                    data: $.extend({}, data, this.gridView01.getPageData()),
                    callback: function (res) {
                        // 장애이력 셋팅
                        caller.gridView01.setData(res);
                    },
                    options: {
                        onError: viewError
                    }
                }
            )
            .done(function () {
                // 장애정보가 셋팅된 후에 조치결과를 셋팅(이렇게 안하면 값이 날아가버림)
                caller.formView01.setFormData("handleResult", handleResult);
                caller.formView01.setFormData("handleFailReason", handleFailReason);
                caller.formView01.setFormData("cash10kEmptyStatus", cash10kEmptyStatus);
                caller.formView01.setFormData("cash50kEmptyStatus", cash50kEmptyStatus);
            });
    },
    SEND_STEXT: function (caller, act, data) {
        if (fnObj.formView05.validate()) {
            axDialog.confirm({
                msg: "자체출동을 요청하시겠습니까?"
            }, function () {
                if (this.key == "ok") {

                    var parentData = fnObj.formView05.getData(); //화면정보를 가져온다.

                    var inputData = {
                        /*calleeReqDatetime: parentData.calleeReqDate + " " + getDatetimeStr(parentData.calleeReqTime),*/
                        errorDatetime: parentData.errorDatetime,
                        calleeReqSeqNo: parentData.calleeReqSeqNo,
                        calleeReqChasu: '01',
                        calleeChasuGubun: '2',
                        jisaCode: parentData.jisaCode,
                        branchCode: parentData.branchCode,
                        branchName: parent.COMMON_CODE["BRANCH_CODE"].map[parentData.branchCode],
                        cornerCode: parentData.cornerCode,
                        cornerName: parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[parentData.terminalNo],
                        terminalNo: parentData.terminalNo,
                        terminalCorpCode: parentData.terminalCorpCode,
                        modelCode: parentData.modelCode,
                        calleeReqGubunCode: '1',
                        calleeReqReasonCode: parentData.calleeReqReasonCode,
                        calleeEmpName: '',
                        calleeEmpTelno: '',
                        terminalErrorCode1: '',
                        terminalErrorCode2: '',
                        totalClassifyCode: parentData.totalClassifyCode,
                        calleeReqUnusl: parentData.calleeReqUnusl || '',
                        crtNo: parentData.crtNo || '',
                        customerWaitEnable: '0',
                        partMngCalleeEnable: '0',
                        securityCorp: parentData.securityCorp,
                        selfCalleeGubun: '1'
                    };
                    axboot
                        .call({
                            type: "PUT",
                            url: "/api/v1/mng/error/sh01001120",
                            data: JSON.stringify(inputData),
                            callback: function (res) {
                            }
                        })
                        .done(function () {
                            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                            //ACTIONS.dispatch(ACTIONS.ITEM_CLICK, selectedErrorItem);
                            axToast.push("자체출동을 요청하였습니다.");
                        });

                }
            });
        }
    },
    SEND_STEXT_CANCEL: function (caller, act, data) {
        if (fnObj.formView06.validate(data)) {
            axDialog.confirm({
                msg: data === '자체출동취소' ? "출동취소전문을 전송하시겠습니까?" : data === '조치완료' ? "해당 장애를 조치완료 하시겠습니까?" : "해당 장애를 더이상 모니터링 하지 않습니다.\n진행하시겠습니까?"
            }, function () {
                if (this.key == "ok") {
                    var parentData = fnObj.formView06.getData(); //화면정보를 가져온다.

                    if (data === '자체출동취소') {
                        if ($("#calleeReqSeqNo").val() == "") {
                            message = '\n' + '출동요청일련번호가 없으면\n' + '출동취소가 불가능합니다.';
                            // $("#jisaCodeForm").focus();
                            formError(message);
                            return;
                        }

                        if ($("#calleeGubun").val() == "" || $("#calleeGubun").val() == "자동출동") {
                            if ($("#calleeGubun").val() == "") {
                                message = '\n' + '출동구분값이 없습니다.\n해당장애건을 선택하시기 \n' + '바랍니다.';
                            } else {
                                message = '\n' + '자동출동 취소는 은행측으로부터\n수신되는 전문을 통해서만\n' + '가능합니다.';
                            }

                            formError(message);
                            return;
                        }
                    }

                    if (parentData.errorProcessStatus == "4") {
                        message = '\n' + '조치완료된건은 취소가 \n불가능합니다.';
                        formError(message);
                        return;
                    }

                    var inputData = {
                        /*calleeReqDatetime: parentData.calleeReqDate + " " + getDatetimeStr(parentData.calleeReqTime),*/
                        errorDatetime: parentData.errorDatetime,
                        calleeReqSeqNo: parentData.calleeReqSeqNo,
                        calleeReqChasu: '01',
                        calleeChasuGubun: '2',
                        jisaCode: parentData.jisaCode,
                        branchCode: parentData.branchCode,
                        cornerCode: parentData.cornerCode,
                        terminalNo: parentData.terminalNo,
                        calleeReqGubunCode: '1',
                        calleeReqReasonCode: parentData.calleeReqReasonCode,
                        calleeCancleReasonCode: parentData.calleeCancleReasonCode,
                        terminalErrorCode1: '',
                        terminalErrorCode2: '',
                        totalClassifyCode: parentData.totalClassifyCode,
                        unusl: parentData.unusl || '',
                        crtNo: parentData.crtNo || '',
                        securityCorp: parentData.securityCorp,
                        selfCalleeGubun: data === '자체출동취소' ? '1' : data === '조치완료' ? '3' : '2' // 1이면 전문전송 , 2면 status만 변경 . 3이면 조치완료
                    };
                    axboot
                        .call({
                            type: "PUT",
                            url: "/api/v1/mng/error/sh01001160",
                            data: JSON.stringify(inputData),
                            callback: function (res) {
                            }
                        })
                        .done(function () {
                            caller.formView01.clear();
                            caller.formView02.clear();
                            caller.formView03.clear();
                            caller.formView04.clear();
                            caller.formView05.clear();
                            caller.formView06.clear();

                            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);

                            if (data === '자체출동취소') {
                                axToast.push("자체출동을 취소하였습니다.");
                            } else if (data === '조치완료') {
                                axToast.push("해당 장애를 조치완료 하였습니다.");
                            } else {
                                axToast.push("해당 장애를 더이상 모니터링 하지 않습니다.");
                            }
                        });
                }
            });
        }
    },
    MODAL_OPEN: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_AGENT_MODAL",
            param: "",
            sendData: function () {
                return {
                    jisaCode: fnObj.formView02.getData().jisaCode
                };
            },
            callback: function (data) {
                $("#calleeEmpName").val(data.empName);
                $("#calleeEmpTelno").val(data.empPhoneNo);

                this.close();
            }
        });
    },
    ROLE_GRID_DATA_INIT: function (caller, act, data) {
    },
    ROLE_GRID_DATA_GET: function (caller, act, data) {
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
var timer;
var refreshRate;

// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    CODE = this; // this는 call을 통해 수집된 데이터들.

    _this.pageButtonView.initView();
    _this.searchView.initView();
    _this.formView01.initView();
    _this.formView02.initView();
    _this.formView03.initView();
    _this.formView04.initView();
    _this.formView05.initView();
    _this.formView06.initView();
    _this.gridView01.initView();

    //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);

    /*if (sessionJson.jisaCode == '00') {
     refreshRate = $('#page-refresh option:selected').val() * 1000;*/

    $('#page-refresh').change(function () {

        if($('#page-refresh option:selected').val() == 0){
            clearInterval(timer);
            return;
        }

        refreshRate = $('#page-refresh option:selected').val() * 1000;
        clearInterval(timer);
        timer = setInterval(function () {
            //axToast.push(refreshRate.toString());
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);

            if (errorStatusList.length > 0) {
                if (checkUndefined(selectedErrorItem.branchCode) != "") {
                    errorStatusList.forEach(function (item) {
                        if (item.txId === selectedErrorItem.txId) {
                            selectedErrorItem = item;
                            ACTIONS.dispatch(ACTIONS.ITEM_CLICK, item);
                        }
                    });
                }
            } else {
                fnObj.formView01.clear();
                fnObj.formView02.clear();
                fnObj.formView03.clear();
                fnObj.formView04.clear();
                fnObj.formView05.clear();
                fnObj.formView06.clear();

                // Grid 초기화
                var res = new Object();
                res.list = [];
                res.page = {currentPage : 0,pageSize :20, totalElements : 0, totalPages : 0};
                fnObj.gridView01.setData(res);
            }
        }, refreshRate);

    });

   /* var timer = setInterval(function () {
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        if (errorStatusList.length > 0) {
            if (checkUndefined(selectedErrorItem.branchCode) != "") {
                ACTIONS.dispatch(ACTIONS.ITEM_CLICK, selectedErrorItem);
            }
        } else {
            fnObj.formView01.clear();
            fnObj.formView02.clear();
            fnObj.formView03.clear();
            fnObj.formView04.clear();
            fnObj.formView05.clear();
        }
    }, refreshRate);*/

    /*}*/
};

fnObj.pageResize = function () {
    //$("[data-ax5layout=ax1]").resizable("both");
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
            "error-search": function () {
                ACTIONS.dispatch(ACTIONS.ERROR_SEARCH);
            },
            "search-view-clear": function () {
                $("#startDate").val(getFormattedDate(new Date(), true));
                $("#endDate").val(getFormattedDate(new Date(), false));
                fnObj.gridView01.initView();
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

        $("#formViewDate").ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        $("#startDate").val(getFormattedDate(new Date(), true));
        $("#endDate").val(getFormattedDate(new Date(), false));
    },
    getData: function () {
        return {
            filter: this.filter.val()
        }
    }
});

/**
 * errorStatus
 */
fnObj.errorStatus = axboot.viewExtend({
    initView: function () {
        $(".error-status").remove();
    },
    setData: function (data) {
        data.forEach(function (n) {
            switch (n.errorProcessStatus) {
                case 0:
                    $("#errorStatus1").append("<input id='btn_" + n.txId + "' type='button' class='" + n.errorStatusClass + "' value='" + n.txId + "' err_status-btn='item-click'></input>");
                    break;
                case 1:
                    $("#errorStatus2").append("<input id='btn_" + n.txId + "' type='button' class='" + n.errorStatusClass + "' value='" + n.txId + "' err_status-btn='item-click'></input>");
                    break;
                case 2:
                    $("#errorStatus3").append("<input id='btn_" + n.txId + "' type='button' class='" + n.errorStatusClass + "' value='" + n.txId + "' err_status-btn='item-click'></input>");
                    break;
                case 3:
                    $("#errorStatus4").append("<input id='btn_" + n.txId + "' type='button' class='" + n.errorStatusClass + "' value='" + n.txId + "' err_status-btn='item-click'></input>");
                    break;
                case 4:
                    $("#errorStatus5").append("<input id='btn_" + n.txId + "' type='button' class='" + n.errorStatusClass + "' value='" + n.txId + "' err_status-btn='item-click'></input>");
                    break;
                /*case 5:
                 $("#errorStatus1").append("<input id='btn_" + n.txId + "' type='button' class='" + n.errorStatusClass + "' value='" + n.txId + "' err_status-btn='item-click'></input>");
                 break;*/
                default:
                    break;
            }

            if (n.errorProcessStatus != 4) {
                if (checkUndefined(n.pushString) != "") {
                    pushErrorMsg(n.pushString);
                }
            }
        });

        axboot.buttonClick(this, "err_status-btn", {
            "item-click": function () {
                console.log(event.target.value);

                errorStatusList.forEach(function (item) {
                    if (item.txId === event.target.value) {

                        fnObj.formView01.setData(item);
                        fnObj.formView01.setFormData("calleeGubun", parent.COMMON_CODE["CALLEE_GUBUN"].map[item.calleeGubun]);
                        fnObj.formView01.setFormData("errorType", parent.COMMON_CODE["ERROR_TYPE"].map[item.errorType]);

                        selectedErrorItem = item;

                        // fnObj.formView04.setFormData("noticeContent", "");
                        // fnObj.formView04.setFormData("customerInfo", "");
                        // fnObj.formView04.setFormData("handleContent", "");

                        ACTIONS.dispatch(ACTIONS.ITEM_CLICK, item);
                    }
                });
            }
        });
    }
});

/**
 * formView01 - 장애정보
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

/**
 * formView02 - 기기정보
 */
fnObj.formView02 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {});
    },
    initView: function () {
        this.target = $("#formView02");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();

        axboot.buttonClick(this, "data-form-view-01-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
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

/**
 * formView03 - 기기상태
 */
fnObj.formView03 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {});
    },
    initView: function () {
        this.target = $("#formView03");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();

        axboot.buttonClick(this, "data-form-view-01-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
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

/**
 * formView04 - 장애이력
 */
fnObj.formView04 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {});
    },
    initView: function () {
        this.target = $("#formView04");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();

        axboot.buttonClick(this, "data-form-view-01-btn", {
            "form-save": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
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

/**
 * formView05 - 자체출동
 */
fnObj.formView05 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {});
    },
    initView: function () {
        this.target = $("#formView05");
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
        axboot.buttonClick(this, "data-form-view-01-btn", {
            "send-stext": function () {
                // 자체출동 전송
                ACTIONS.dispatch(ACTIONS.SEND_STEXT);
            }
        });
        axboot.buttonClick(this, "data-searchview-btn", {
            "search_agent_modal": function () {
                ACTIONS.dispatch(ACTIONS.MODAL_OPEN)
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
        var title;
        var message;

        /*if ($("#calleeEmpName").val() == "") {
         message = '\n' + '출동요원은 필수 입력조건입니다.\n' + '출동요원을 선택하세요.';
         // $("#jisaCodeForm").focus();
         formError(message);
         return false;
         }*/
        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
    }
});


/**
 * formView05 - 자체출동취소(모니터링 강제취소)
 */
fnObj.formView06 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {});
    },
    initView: function () {
        this.target = $("#formView06");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();

        axboot.buttonClick(this, "data-form-view-02-btn", {
            "send-stext": function () {
                // 자체출동취소 전송
                ACTIONS.dispatch(ACTIONS.SEND_STEXT_CANCEL, '자체출동취소');
            },
            "send-cancel": function () {
                // 모니터링취소
                ACTIONS.dispatch(ACTIONS.SEND_STEXT_CANCEL, '모니터링취소');
            },
            "send-ok": function () {
                // 모니터링취소
                ACTIONS.dispatch(ACTIONS.SEND_STEXT_CANCEL, '조치완료');
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
        var title;
        var message;

        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
    }
});

/**
 * gridView - 장애이력
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
            sortable: true,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {
                    key: 'stextGubun', label: '전문구분코드', width: 100, align: 'left', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["ERROR_STEXT_GUBUN"].map[this.value];
                    }
                },
                {
                    key: 'totalClassifyCode', label: '집계분류코드', width: 100, align: 'left', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["TOTAL_CLASSIFY_CODE"].map[this.value];
                    }
                },
                {
                    key: 'errorType', label: '장애구분', width: 100, align: 'left', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["ERROR_TYPE"].map[this.value];
                    }
                },
                /*{key: 'errorType', label: '처리상태', width: 100, align: 'left', editor: 'text'},*/
                {
                    key: 'errorDatetime', label: '장애발생일시', width: 130, align: 'center', editor: 'text',
                    formatter: function formatter() {
                        return this.item.errorDate + " " + this.item.errorTime;
                    }
                },
                {
                    key: 'calleeReqDatetime', label: '출동요청일시', width: 130, align: 'left', editor: 'text',
                    formatter: function formatter() {
                        return checkUndefined(this.item.calleeReqDate) + " " + checkUndefined(this.item.calleeReqTime);
                    }
                },
                {
                    key: 'calleePlanDatetime', label: '출동예정일시', width: 130, align: 'left',
                    formatter: function formatter() {
                        return checkUndefined(this.item.calleePlanDate) + " " + checkUndefined(this.item.calleePlanTime);
                    }
                },
                {
                    key: 'arrivalPlanDatetime', label: '도착예정일시', width: 130, align: 'left', editor: 'text',
                    formatter: function formatter() {
                        return checkUndefined(this.item.arrivalPlanDate) + " " + checkUndefined(this.item.arrivalPlanTime);
                    }
                },
                {
                    key: 'cornerArrivalDatetime', label: '도착일시', width: 130, align: 'left', editor: 'text',
                    formatter: function formatter() {
                        return checkUndefined(this.item.cornerArrivalDate) + " " + checkUndefined(this.item.cornerArrivalTime);
                    }
                },
                {
                    key: 'handleDatetime', label: '조치일시', width: 130, align: 'left', editor: 'text',
                    formatter: function formatter() {
                        return checkUndefined(this.item.handleDate) + " " + checkUndefined(this.item.handleTime);
                    }
                }
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex);
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
                return this.jisaCode && this.branchCode && this.cornerCode;
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

var pushErrorMsg = function (msgStr) {
    axToast.push({
        theme: "danger",
        width: 450,
        icon: '<i class="cqc-new"></i>',
        msg: '[알림] ' + msgStr
    });
}

function getCalleeSeqNo() {
    var time = new Date();

    var hour = ("0" + time.getHours()).slice(-2);
    var min = ("0" + time.getMinutes()).slice(-2);
    var second = ("0" + time.getSeconds()).slice(-2);
    var milSecond = ("0" + time.getMilliseconds()).slice(-2);
    var seqNo;

    if (fnObj.formView01.getData().calleeReqSeqNo == undefined) {
        seqNo = hour + min + second + milSecond + "00";
    } else {
        seqNo = fnObj.formView01.getData().calleeReqSeqNo;
    }
    return seqNo;
}

function getFormattedDate(date, isStart) {
    var day;
    var tempDate;
    if (isStart) {
        date.setDate(date.getDate() - 2);
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

function getDatetimeStr(dateTime) {
    if (dateTime.length > 5) {
        return dateTime;
    } else {
        return dateTime + ":00";
    }
}

function checkUndefined(value) {
    if (typeof value === "undefined") {
        return "";
    } else {
        return value;
    }
}

