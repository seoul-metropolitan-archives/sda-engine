var fnObj = {};
var pageSize = 1000;
var errorStatusList = {};
var gridView = undefined;
var serviceList = [];
var handleResult = "";
var handleFailReason = "";
var cash10kEmptyStatus = "";
var cash50kEmptyStatus = "";

var selectedIndex = "";
var selectedErrorItem = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        var _this = this;

        if(!data)
            data = this.formView.getData();

        axboot.ajax({
            type: "POST",
            url: "/ad/ad004/ad004/searchPopupHeader",
            data : JSON.stringify(data),
            async : false,
            callback: function (list) {
                if(undefined === list || list.length < 1)
                    fnObj.gridView_h.addRow();
                else
                    fnObj.gridView_h.setData(list);
            }
        })
    },
    ERROR_SEARCH: function (caller, act, data) {
        /*
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
        */
        return false;
    },
    PAGE_SAVE : function(caller, act, data)
    {
        //alert("저장하자");
        ACTIONS.dispatch(ACTIONS.POPUP_HEADER_PAGE_SAVE);
    },
    POPUP_HEADER_PAGE_SAVE: function (caller, act, data) {
        var _this = this;
        axDialog.confirm({
            msg: "저장하시겠습니까?"
        },function() {
            if (this.key == "ok") {
                console.log(fnObj.gridView_h.getData());
                /*
                axboot.ajax({
                    url : "ad/ad004/insertPopupHeader",
                    data : this.gridView_h.getData()
                })
                */
            }
        });
        /*
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
        */
    },
    GET_POPUP_DETAIL : function(data)
    {

        return ;

        axboot.ajax({
            url : ""
            ,type : "POST"
            ,dataType : "JSON"
            ,data : JSON.stringify(data)
            ,callback : function(res)
            {

            }
        });
    },
    FORM_CLEAR: function (caller, act, data) {
        /*
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                caller.formView01.clear();
                caller.gridView02.clear();
            }
        });
        */
    },
    ITEM_CLICK: function (caller, act, data) {
        /*
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
            */
    },
    SEND_STEXT: function (caller, act, data) {
        /*
        if (fnObj.formView05.validate()) {
            axDialog.confirm({
                msg: "자체출동을 요청하시겠습니까?"
            }, function () {
                if (this.key == "ok") {

                    var parentData = fnObj.formView05.getData(); //화면정보를 가져온다.

                    var inputData = {
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
        */
    },
    SEND_STEXT_CANCEL: function (caller, act, data) {
        /*
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
        */
    },
    MODAL_OPEN: function (caller, act, data) {
        /*
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
        */
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
            return false;
        }
    }
});
fnObj = {
    pageStart : function () {
        var _this = this;
        $.ajax({
            url: "/assets/js/controller/simple_controller.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/ad/ad000/ad000_controller.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/ad/ad004/ad004_controller.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/column_info/ad004_h.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/column_info/ad004_d.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        /**/
        _this.formView.initView();
        _this.gridView_h.initView();
        _this.gridView_d.initView();

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,this.formView.getData());

    }
};
/*검색 창*/
fnObj.formView = axboot.viewExtend(axboot.baseView,{
    initView : function(){
        /*공통 코드 가져오기*/
        axboot.ajax({
            type: "POST",
            url: "/ad/ad003/getCode",
            data : JSON.stringify({categoryCode : "CD006"}),
            async : false,
            callback: function (list) {
                console.log(JSON.stringify(list));

                if(undefined === list)
                    return ;

                var data = undefined;
                for(var i = 0;i < list.length; i++)
                {
                    data = list[i];
                    /*column info 정보 갱신*/
                    ad004_h.column_info[3].values.push(data.codeDetailUUID);
                    ad004_h.column_info[3].labels.push(data.codeName);

                    /*콤보박스 생성*/
                    $("#serviceList").append($("<option>").val(data.codeDetailUUID).text(data.codeName));

                    serviceList[data.service_uuid] =
                        {
                            "label" : data.codeName,
                            "value": data.codeDetailUUID
                        }
                }
            },
            options: {
                onError: function(a,b,c)
                {
                    console.log(a);
                    console.log(b);
                    console.log(c);

                }
            }
        });
    }
    ,getData : function()
    {
        return {
            popupCode       : $("#popupCode").val()
            , popupName     : $("#popupName").val()
            , serviceUUID   : $("#serviceList option:selected").val()
            , useYN         : $("input[name='useYN']:checked").val()
        }
    }
});

/*팝업 헤더*/
fnObj.gridView_h = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid",
    entityName : "POPUP_HEADER",
    itemClick : ACTIONS.GET_POPUP_DETAIL,
    initView  : function()
    {
        this.setColumnInfo(ad004_h.column_info);
        this.makeGrid();
    }
 });
/*팝업 디테일 ( Column )*/
fnObj.gridView_d = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    tagId : "realgrid2",
    entityName : "POPUP_DETAIL",
    initView: function ()
    {
        this.setColumnInfo(ad004_d.column_info);
        this.makeGrid();
    }
});