var fnObj = {};
var selectedIndex = "";
var cash10kEmptyStatus = "";
var cash50kEmptyStatus = "";

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/error/sh01001110",
            data: $.extend({}, this.searchView.getData(), this.gridView01.getPageData()),
            callback: function (res) {
                caller.gridView01.setData(res);

                caller.formView01.clear();
                caller.formView02.clear();
                caller.formView03.clear();
                caller.formView04.clear();
                caller.formView05.clear();
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
                var selectedGridData = fnObj.gridView01.getData();
                var inputData = {
                    jisaCode: selectedGridData[selectedIndex].jisaCode,
                    branchCode: selectedGridData[selectedIndex].branchCode,
                    cornerCode: selectedGridData[selectedIndex].cornerCode,
                    terminalNo: selectedGridData[selectedIndex].terminalNo,
                    errorDatetime: selectedGridData[selectedIndex].errorDatetime,
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
                        }
                    })
                    .done(function () {
                        fnObj.gridView01.setPageData({pageNumber: 0});
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                        axToast.push("저장 작업이 완료되었습니다.");
                    });
            }
        });
        /*if (caller.formView01.validate()) {

         var parentData = caller.formView01.getData();
         var childList = [].concat(caller.gridView02.getData("modified"));
         childList = childList.concat(caller.gridView02.getData("deleted"));

         // childList에 parentKey 삽입
         childList.forEach(function (n) {
         n.parentKey = parentData.key;
         });

         axboot
         .call({
         type: "PUT",
         //url: ["samples", "parent"],
         url: "/api/v1//mng/error/sh01001110",
         data: JSON.stringify([parentData]),
         callback: function (res) {
         }
         })
         .call({
         //childlist에 대하여 직접 코딩 필요!!!
         /!*
         type: "PUT", url: ["samples", "child"], data: JSON.stringify(childList),
         callback: function (res) {
         }
         *!/
         })
         .done(function () {
         ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
         axToast.push("저장 작업이 완료되었습니다.");
         });
         }*/
    },
    FORM_CLEAR: function (caller, act, data) {
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                caller.formView05.clear();
                caller.formView05.setFormData("calleeReqSeqNo", getCalleeSeqNo('new'));
                caller.formView05.setFormData("calleeGubun", "자체출동");
            }
        });
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
                caller.formView05.setFormData("jisaCode", data.jisaCode);
                caller.formView05.setFormData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[data.branchCode]);
                caller.formView05.setFormData("branchCode", data.branchCode);
                caller.formView05.setFormData("cornerCode", data.cornerCode);
                caller.formView05.setFormData("cornerName", parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[data.terminalNo]);
                caller.formView05.setFormData("terminalNo", data.terminalNo);

                // 단말기 정보 호출
                axboot.ajax({
                    type: "GET",
                    url: "/api/v1//mng/equip/terminal_status",
                    data: {
                        details: true,
                        branchCode: data.jisaCode,
                        branchCode: data.branchCode,
                        cornerCode: data.cornerCode,
                        terminalNo: data.terminalNo
                    },
                    callback: function (res) {
                        // 장애정보 셋팅 후 값을 추가적으로 셋팅하기 위해 값을 저장 아래 .done 에서 처리
                        caller.formView05.setFormData("securityCorp", res.securityCorp.trim());
                        caller.formView05.setFormData("securityCorpContractNo", res.securityCorpContractNo);
                        caller.formView05.setFormData("modelCode", res.terminalCorpCode + res.modelCode);
                    },
                    options: {
                        onError: viewError
                    }
                });

                this.close();
            }
        });
    },
    SEARCH_AGENT_MODAL_OPEN: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_AGENT_MODAL",
            param: "",
            sendData: function () {
                return {
                    jisaCode: $("#jisaCodeForm").val()
                };
            },
            callback: function (data) {
                $("#calleeEmpName").val(data.empName);
                $("#calleeEmpTelno").val(data.empPhoneNo);

                this.close();
            }
        });
    },
    SEND_STEXT: function (caller, act, data) {
        if (fnObj.formView05.validate(data)) {
            axDialog.confirm({
                msg: data === '장애등록' ? "자체출동을 저장하시겠습니까?\n저장만 하는경우 출동요청은 하지 않습니다.\n장애발생일시는 자동으로 생성됩니다." : "자체출동을 요청하시겠습니까? \n장애모니터링에 출동요청정보가 \n자동 생성됩니다."
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
                        terminalCorpCode: parentData.modelCode.substring(0, 1),
                        modelCode: parentData.modelCode.substring(1, 4),
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
                        selfCalleeGubun: data === '장애등록' ? '2' : '1'
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
                            fnObj.gridView01.setPageData({pageNumber: 0});
                            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                            //ACTIONS.dispatch(ACTIONS.ITEM_CLICK, selectedErrorItem);
                            caller.formView05.clear();
                            caller.formView05.setFormData("calleeReqSeqNo", getCalleeSeqNo('new'));
                            caller.formView05.setFormData("calleeGubun", "자체출동");
                            axToast.push("자체출동을 요청하였습니다.");
                        });

                }
            });
        }
    },
    ITEM_CLICK: function (caller, act, data) {
        axboot
            .call({
                type: "GET",
                url: "/api/v1/mng/error/error_status",
                data: {
                    details: true,
                    branchCode: data.branchCode,
                    cornerCode: data.cornerCode,
                    terminalNo: data.terminalNo,
                    errorDatetime: data.errorDatetime
                },
                callback: function (res) {
                    // console.log('res:', res);
                    // 장애상태정보 셋팅
                    caller.formView01.setData(res);

                    caller.formView01.setFormData("securityCorpName", parent.COMMON_CODE["SECURITY_CORP"].map[data.securityCorp]);
                    caller.formView01.setFormData("calleeGubun", parent.COMMON_CODE["CALLEE_GUBUN"].map[res.calleeGubun]);
                    caller.formView01.setFormData("stextGubun", parent.COMMON_CODE["ERROR_STEXT_GUBUN"].map[data.stextGubun]);
                    caller.formView01.setFormData("totalClassifyCodeName", parent.COMMON_CODE["TOTAL_CLASSIFY_CODE"].map[data.totalClassifyCode]);
                    caller.formView01.setFormData("securityCorpContractNo", data.securityCorpContractNo);

                    if (typeof res.errorType != "undefined") {
                        var errorType = parent.COMMON_CODE["ERROR_TYPE"].map[res.errorType];
                        caller.formView01.setFormData("errorType", errorType);
                    }

                    // 자체출동 기본 값 셋팅
                    caller.formView05.setData(fnObj.formView01.getData());

                    // 출동요청일련번호 생성
                    caller.formView05.setFormData("calleeGubun", "자체출동");
                    caller.formView05.setFormData("calleeReqSeqNo", getCalleeSeqNo());
                    caller.formView05.setFormData("terminalCorpCode", data.terminalCorpCode);
                    caller.formView05.setFormData("modelCode", data.terminalCorpCode + data.modelCode);
                    caller.formView05.setFormData("securityCorp", data.securityCorp);
                    caller.formView05.setFormData("calleeReqDate", getFormattedDate(new Date()));
                    caller.formView05.setFormData("totalClassifyCode", data.totalClassifyCode);
                    caller.formView05.setFormData("calleeGubun", parent.COMMON_CODE["CALLEE_GUBUN"].map[res.calleeGubun]);

                    caller.formView05.setFormData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[data.branchCode]);
                    caller.formView05.setFormData("cornerName", parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[data.terminalNo]);
                    caller.formView05.setFormData("stextGubun", parent.COMMON_CODE["ERROR_STEXT_GUBUN"].map[data.stextGubun]);
                    caller.formView05.setFormData("branchCode", data.branchCode);
                    caller.formView05.setFormData("cornerCode", data.cornerCode);
                    caller.formView05.setFormData("terminalNo", data.terminalNo);

                    if (typeof res.calleeReqSeqNo != "undefined" && res.calleeReqSeqNo != null) {
                        axboot.ajax({
                            type: "GET",
                            url: "/api/v1//mng/error/sh01001150",
                            data: {
                                details: true,
                                branchCode: res.branchCode,
                                cornerCode: res.cornerCode,
                                terminalNo: res.terminalNo,
                                calleeReqSeqNo: res.calleeReqSeqNo
                            },
                            callback: function (res) {
                                // 장애정보 셋팅 후 값을 추가적으로 셋팅하기 위해 값을 저장 아래 .done 에서 처리
                                caller.formView01.setFormData("handleResult", parent.COMMON_CODE["HANDLE_RESULT"].map[res.handleResult]);
                                caller.formView01.setFormData("handleFailReason", parent.COMMON_CODE["HANDLE_FAIL_REASON"].map[res.handleFailReason]);
                            },
                            options: {
                                onError: viewError
                            }
                        });
                    }

                    // 기기정보
                    fnObj.formView02.setFormData("orgName", parent.COMMON_CODE["ORG_CODE"].map['01']);
                    fnObj.formView02.setFormData("placeGubun", parent.COMMON_CODE["PLACE_GUBUN"].map[data.placeGubun]);
                    fnObj.formView02.setFormData("jisaName", parent.COMMON_CODE["JISA_CODE"].map[data.jisaCode]);
                    fnObj.formView02.setFormData("runtime", data.runtime);
                    fnObj.formView02.setFormData("mngChannel", data.mngChannel);
                    fnObj.formView02.setFormData("prodSerialNo", data.prodSerialNo);
                    fnObj.formView02.setFormData("branchName", data.branchName);
                    fnObj.formView02.setFormData("boothCorp", parent.COMMON_CODE["BOOTH_CORP"].map[data.boothCorp]);
                    fnObj.formView02.setFormData("cornerName", data.cornerName);
                    fnObj.formView02.setFormData("boothType", parent.COMMON_CODE["BOOTH_TYPE"].map[data.boothType]);
                    fnObj.formView02.setFormData("terminalNo", data.terminalNo);
                    fnObj.formView02.setFormData("internetClassify", data.internetClassify);
                    fnObj.formView02.setFormData("modelName", parent.COMMON_CODE["MODEL_CODE"].map[data.terminalCorpCode + data.modelCode]);
                    fnObj.formView02.setFormData("commOffice", data.internetClassify);
                    fnObj.formView02.setFormData("terminalCorpName", parent.COMMON_CODE["TERMINAL_CORP_CODE"].map[data.terminalCorpCode]);
                    fnObj.formView02.setFormData("intercomNo", data.internetClassify);
                    fnObj.formView02.setFormData("terminalLocation", data.terminalLocation);
                    fnObj.formView02.setFormData("intercomNo", ' - ');

                    // 장애조치관리 셋팅
                    caller.formView04.setData(data);

                    //fnObj.formView04.setFormData("noticeContent",data.noticeContent);
                    //fnObj.formView04.setFormData("customerInfo",data.customerInfo);
                    //fnObj.formView04.setFormData("handleContent",data.handleContent);
                },
                options: {
                    onError: viewError
                }
            })
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
            .done(function () {
                caller.formView01.setFormData("cash10kEmptyStatus", cash10kEmptyStatus);
                caller.formView01.setFormData("cash50kEmptyStatus", cash50kEmptyStatus);
            });

        // 기기상태
        fnObj.formView03.setFormData("cashPartStatus", parent.COMMON_CODE["CASH_PART_STATUS"].map[data.cashPartStatus]);
        fnObj.formView03.setFormData("enableIcCard", parent.COMMON_CODE["ENABLE_IC_CARD"].map[data.enableIcCard]);
        fnObj.formView03.setFormData("idScannerStatus", parent.COMMON_CODE["ID_SCANNER_STATUS"].map[data.idScannerStatus]);
        fnObj.formView03.setFormData("checkPartStatus", parent.COMMON_CODE["CHECK_PART_STATUS"].map[data.checkPartStatus]);
        fnObj.formView03.setFormData("enableEmv", parent.COMMON_CODE["ENABLE_EMV"].map[data.enableEmv]);
        fnObj.formView03.setFormData("bioScannerStatus", parent.COMMON_CODE["BIO_SCANNER_STATUS"].map[data.bioScannerStatus]);
        fnObj.formView03.setFormData("portfolioPartStatus", parent.COMMON_CODE["PORTFOLIO_PART_STATUS"].map[data.portfolioPartStatus]);
        fnObj.formView03.setFormData("enableIr", parent.COMMON_CODE["ENABLE_IR"].map[data.enableIr]);
        fnObj.formView03.setFormData("scrtyCardHighendAtmOnly", parent.COMMON_CODE["SCRTY_CARD_HIGHEND_ATM_ONLY"].map[data.scrtyCardHighendAtmOnly]);
        fnObj.formView03.setFormData("jrnlPartStatus", parent.COMMON_CODE["JRNL_PART_STATUS"].map[data.jrnlPartStatus]);
        fnObj.formView03.setFormData("enableRf", parent.COMMON_CODE["ENABLE_RF"].map[data.enableRf]);
        fnObj.formView03.setFormData("cardStyleOtp", parent.COMMON_CODE["CARD_STYLE_OTP"].map[data.cardStyleOtp]);
        fnObj.formView03.setFormData("rtrvlBoxStatus", parent.COMMON_CODE["RTRVL_BOX_STATUS"].map[data.rtrvlBoxStatus]);
        fnObj.formView03.setFormData("enableFingerprint", parent.COMMON_CODE["ENABLE_FINGERPRINT"].map[data.enableFingerprint]);
        fnObj.formView03.setFormData("s20General", parent.COMMON_CODE["S20_GENERAL"].map[data.s20General]);
        fnObj.formView03.setFormData("cardPartStatus", parent.COMMON_CODE["CARD_PART_STATUS"].map[data.cardPartStatus]);
        fnObj.formView03.setFormData("encryptionStatus", parent.COMMON_CODE["ENCRYPTION_STATUS"].map[data.encryptionStatus]);
        fnObj.formView03.setFormData("s20Frpy", parent.COMMON_CODE["S20_FRPY"].map[data.s20Frpy]);
        fnObj.formView03.setFormData("bnkbPartStatus", parent.COMMON_CODE["BNKB_PART_STATUS"].map[data.bnkbPartStatus]);
        fnObj.formView03.setFormData("slineGeneral", parent.COMMON_CODE["SLINE_GENERAL"].map[data.slineGeneral]);
        fnObj.formView03.setFormData("giroPartStatus", parent.COMMON_CODE["GIRO_PART_STATUS"].map[data.giroPartStatus]);
        fnObj.formView03.setFormData("slineFrpy", parent.COMMON_CODE["SLINE_FRPY"].map[data.slineFrpy]);
        fnObj.formView03.setFormData("suspendStatus", parent.COMMON_CODE["SUSPEND_STATUS"].map[data.suspendStatus]);
        fnObj.formView03.setFormData("cashPartStatus50kWon", parent.COMMON_CODE["CASH_PART_STATUS_50K_WON"].map[data.cashPartStatus50kWon]);
        fnObj.formView03.setFormData("fourtuneGeneral", parent.COMMON_CODE["FOURTUNE_GENERAL"].map[data.fourtuneGeneral]);
        fnObj.formView03.setFormData("hwErrorStatus", parent.COMMON_CODE["HW_ERROR_STATUS"].map[data.hwErrorStatus]);
        fnObj.formView03.setFormData("atmcExcclcTerminalError", parent.COMMON_CODE["ATMC_EXCCLC_TERMINAL_ERROR"].map[data.atmcExcclcTerminalError]);
        fnObj.formView03.setFormData("fourtuneFrpy", parent.COMMON_CODE["FOURTUNE_FRPY"].map[data.fourtuneFrpy]);
        fnObj.formView03.setFormData("maintenanceStatus", parent.COMMON_CODE["MAINTENANCE_STATUS"].map[data.maintenanceStatus]);
        fnObj.formView03.setFormData("atmcExcclcExecResult", parent.COMMON_CODE["ATMC_EXCCLC_EXEC_RESULT"].map[data.atmcExcclcExecResult]);
        fnObj.formView03.setFormData("rcppayBnkb", parent.COMMON_CODE["RCPPAY_BNKB"].map[data.rcppayBnkb]);
        fnObj.formView03.setFormData("enableDesBoard", parent.COMMON_CODE["ENABLE_DES_BOARD"].map[data.enableDesBoard]);
        fnObj.formView03.setFormData("cardIssuedTerminalStatus", parent.COMMON_CODE["CARD_ISSUED_TERMINAL_STATUS"].map[data.cardIssuedTerminalStatus]);
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

// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    CODE = this; // this는 call을 통해 수집된 데이터들.

    _this.pageButtonView.initView();
    _this.searchView.initView();
    _this.gridView01.initView();
    _this.formView01.initView();
    _this.formView02.initView();
    _this.formView03.initView();
    _this.formView04.initView();
    _this.formView05.initView();

    _this.formView05.setFormData("calleeGubun", "자체출동");
    _this.formView05.setFormData("stextGubun", "장애");
    _this.formView05.setFormData("calleeReqSeqNo", getCalleeSeqNo());
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
            "save": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            },
            "excel": function () {
                fnObj.gridView01.excel("장애통보이력-" + getFormattedDate(new Date()) + ".xls");
            },
            "search-view-clear": function () {
                $("#filter").val("");
                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
                $("#terminalNo").val("");
                $("#errorType").val("");
                $("#startDate").val(getFormattedDate(new Date(), true));
                $("#endDate").val(getFormattedDate(new Date()));
                $("#errorClassifyCode").val("");
                if(sessionJson.userCd == "system" || sessionJson.jisaCode == "00") {
                    $("#jisaCode").val("");
                }
                fnObj.gridView01.setPageData({pageNumber: 0});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });
    }
});

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
        this.errorType = $("#errorType");
        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        $("#startDate").val(getFormattedDate(new Date(), true));
        $("#endDate").val(getFormattedDate(new Date()));

        axboot.buttonClick(this, "data-searchview-btn", {
            "modal": function () {
                ACTIONS.dispatch(ACTIONS.MODAL_OPEN)
            }
        });

        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCode").attr('readonly', true);
            $("#jisaCode").attr('disabled', true);
            $("#jisaCode").val(sessionJson.jisaCode);
        }

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
            branchCode: $("#branchCode").val(),
            terminalNo: $("#terminalNo").val(),
            errorType: $("#errorType").val(),
            startDate: $("#startDate").val(),
            endDate: $("#endDate").val(),
            errorClassifyCode: $("#errorClassifyCode").val()
        }
    },
    excel: function (file) {
        this.target.exportExcel(file);
    }
});

/**
 * gridView
 */

var rowIndex = -1;
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {

        var test = new Test();

        var _this = this;

        this.target = axboot.gridBuilder({
            showRowSelector: false,
            frozenColumnIndex: 0,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {
                    key: 'jisaCode', label: '지사명', width: 70, align: 'center', formatter: function formatter() {
                    return parent.COMMON_CODE["JISA_CODE"].map[this.value];
                }
                },
                {key: 'branchName', label: '지점명', width: 100, align: 'left'},
                {key: 'cornerName', label: '코너명', width: 100, align: 'left'},
                {key: 'terminalNo', label: '단말번호', width: 80, align: 'center'},
                {
                    key: 'placeGubun', label: '장소구분', width: 100, align: 'center', formatter: function formatter() {
                    return parent.COMMON_CODE["PLACE_GUBUN"].map[this.value];
                }
                },
                {
                    key: 'errorType', label: '장애구분', width: 100, align: 'center', formatter: function formatter() {
                    return parent.COMMON_CODE["ERROR_TYPE"].map[this.value];
                }
                },
                {key: 'errorDatetime', label: '발생일자', width: 130, align: 'center'},
                {key: 'txId', label: '장애종류', width: 100, align: 'center'},
                {
                    key: 'modelCode', label: '기종코드', width: 100, align: 'left', formatter: function formatter() {
                    return parent.COMMON_CODE["MODEL_CODE"].map[this.item.terminalCorpCode + this.item.modelCode];
                }
                },
                {
                    key: 'totalClassifyCode',
                    label: '장애메세지',
                    width: 100,
                    align: 'left',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["TOTAL_CLASSIFY_CODE"].map[this.value];
                    }
                },
                {key: 'noticeContent', label: '통보내용', width: 200, align: 'left'},
                {key: 'customerInfo', label: '고객정보', width: 200, align: 'left'},
                {key: 'handleContent', label: '조치내용', width: 200, align: 'left'}


                /*{key: 'noticeContent', label: '통보내용', editor: 'text', width: 200, align: 'left',
                 formatter: function () {
                 return "<div class='editable-cell'>" + checkUndefined(this.value) + "</div>";
                 }},
                 {key: 'customerInfo', label: '고객정보', editor: 'text', width: 200, align: 'left',
                 formatter: function () {
                 return "<div class='editable-cell'>" + checkUndefined(this.value) + "</div>";
                 }},
                 {key: 'handleContent', label: '조치내용', editor: 'text', width: 200, align: 'left',
                 formatter: function () {
                 return "<div class='editable-cell'>" + checkUndefined(this.value) + "</div>";
                 }}*/
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex);
                    selectedIndex = this.dindex;

                    ACTIONS.dispatch(ACTIONS.ITEM_CLICK, this.item);

                    // 아이템클릭시 동일 row인 경우에는 editing 가능하게 처리
                    /*if (rowIndex != this.dindex) {
                     ACTIONS.dispatch(ACTIONS.ITEM_CLICK, this.item);
                     rowIndex = this.dindex;
                     }*/
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
                return this.errorDate && this.errorTime && this.branchCode && this.cornerCode && this.terminalNo;
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
    setFormData: function (dataPath, value) {
        this.model.set(dataPath, value);
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
 * formView04 - 장애조치관리
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
        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm").attr('readonly', true);
            $("#jisaCodeForm").attr('disabled', true);
            $("#jisaCodeForm").val(sessionJson.jisaCode);
        }

        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });
        axboot.buttonClick(this, "data-form-view-05-btn", {
            "save-stext": function () {
                // 자체출동을 위한 장애등록
                ACTIONS.dispatch(ACTIONS.SEND_STEXT, '장애등록');
            },
            "send-stext": function () {
                // 자체출동 전송
                ACTIONS.dispatch(ACTIONS.SEND_STEXT, '자체출동');
            },
            "modal": function () {
                ACTIONS.dispatch(ACTIONS.FORM_MODAL_OPEN)
            },
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR)
            }
        });
        axboot.buttonClick(this, "data-searchview-btn", {
            "search_agent_modal": function () {
                /*if($("#jisaCodeForm").val() === ""){
                 formError("출동요원은 지사선택시 가능합니다.");
                 return;
                 }*/

                ACTIONS.dispatch(ACTIONS.SEARCH_AGENT_MODAL_OPEN)
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
    validate: function (data) {
        var rs = this.model.validate();
        var title;
        var message;
        /*if (typeof this.model.get("calleeReqTime") === "undefined") {
         message = '\n' + '출동요청시간 필수 입력조건입니다.\n' + '출동요청시간 입력하세요.';
         // $("#jisaCodeForm").focus();
         formError(message);
         return false;
         }
         if ($("#calleeEmpName").val() == "" && data != 1) {
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
        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm").attr('readonly', true);
            $("#jisaCodeForm").attr('disabled', true);
            $("#jisaCodeForm").val(sessionJson.jisaCode);
        }
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

function checkUndefined(value) {
    if (typeof value === "undefined") {
        return "";
    } else {
        return value;
    }
}

function getCalleeSeqNo(newGen) {
    var time = new Date();

    var hour = ("0" + time.getHours()).slice(-2);
    var min = ("0" + time.getMinutes()).slice(-2);
    var second = ("0" + time.getSeconds()).slice(-2);
    var milSecond = ("0" + time.getMilliseconds()).slice(-2);
    var seqNo;

    if (newGen === 'new' || fnObj.formView01.getData().calleeReqSeqNo == undefined) {
        seqNo = hour + min + second + milSecond + "00";
    } else {
        seqNo = fnObj.formView01.getData().calleeReqSeqNo;
    }
    return seqNo;
}

function getDatetimeStr(dateTimeStr) {
    if (dateTimeStr.length > 5) {
        return dateTimeStr;
    } else {
        return dateTimeStr + ":00";
    }
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
