var fnObj = {};
var selectedItem = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/error/sh01001230",
            data: $.extend({}, this.searchView.getData(), this.gridView01.getPageData()),
            callback: function (res) {
                caller.gridView01.setData(res);
                //caller.formView01.clear();
            },
            options: {
                onError: viewError
            }
        });
        return false;
    },
    EXCEL_DOWNLOAD: function (caller, act, data) {
        var params = buildParams($.extend({}, this.searchView.getData()));
        console.log(params);
        window.location = CONTEXT_PATH + "/api/v1//mng/error/sh01001230/download?" + params;
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        if (caller.formView01.validate()) {

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
                    url: "/api/v1//mng/error/sh01001120",
                    data: JSON.stringify([parentData]),
                    callback: function (res) {
                    }
                })
                .call({
                    //childlist에 대하여 직접 코딩 필요!!!
                    /*
                     type: "PUT", url: ["samples", "child"], data: JSON.stringify(childList),
                     callback: function (res) {
                     }
                     */
                })
                .done(function () {
                    fnObj.gridView01.setPageData({pageNumber: 0});
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    axToast.push("저장 작업이 완료되었습니다.");
                });
        }
    },
    FORM_CLEAR: function (caller, act, data) {
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                caller.formView01.clear();
            }
        });
    },
    MODAL_OPEN: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_TERMINAL_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisaCode": $("#jisaCode").val()
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
        caller.formView01.setData(data);

        fnObj.formView01.setFormData("jisaName", parent.COMMON_CODE["JISA_CODE"].map[data.jisaCode]);
        fnObj.formView01.setFormData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[data.branchCode]);
        fnObj.formView01.setFormData("cornerName", parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[data.terminalNo]);
        fnObj.formView01.setFormData("smsEnableName", parent.COMMON_CODE["SMS_SUBJECT_ENABLE"].map[data.smsEnable]);
        fnObj.formView01.setFormData("errorTypeName", parent.COMMON_CODE["ERROR_GUBUN"].map[data.errorType]);
        /*axboot.ajax({
         type: "GET",
         url: "/api/v1/mng/error/sh01001120",
         data: {
         details: true,
         branchCode: data.branchCode,
         cornerCode: data.cornerCode,
         terminalNo: data.terminalNo,
         errorDatetime: data.errorDatetime
         },
         callback: function (res) {
         console.log('res:', res);
         caller.formView01.setData(res);
         },
         options: {
         onError: viewError
         }
         });*/
        //data: {groupCd: "USER_ROLE", useYn: "Y"}

        //caller.formView01.setData(data);
        //childlist에 대한 액션이 필요한 경우에는 직접 작성
        /*
         axboot.ajax({
         type: "GET",
         url: "/api/v1/sample/child",
         data: "parentKey=" + data.key,
         callback: function (res) {
         caller.gridView02.setData(res);
         },
         options: {
         onError: viewError
         }
         });
         */
    },
    SEND_STEXT: function (caller, act, data) {
        if (fnObj.formView01.validate()) {
            axDialog.confirm({
                msg: "조치결과를 전송하시겠습니까?"
            }, function () {
                if (this.key == "ok") {

                    var parentData = fnObj.formView01.getData(); //화면정보를 가져온다.
                    parentData.handleDatetime = parentData.handleDate + " " + getDatetimeStr(parentData.handleTime);
                    parentData.handleEmpName = parentData.handleEmpName || '';
                    parentData.handleEmpTelno = parentData.handleEmpTelno || '';
                    parentData.handleDesc = parentData.handleDesc || '';
                    parentData.dealAmount = parentData.dealAmount.replace(/,/g, '') || '';

                    axboot.ajax({
                        type: "PUT",
                        url: "/api/v1/mng/error/sh01001230",
                        data: JSON.stringify(parentData),
                        callback: function (res) {
                            caller.formView01.clear();
                            fnObj.gridView01.setPageData({pageNumber: 0});
                            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                            axToast.push("조치결과전문을 전송하였습니다.");
                        },
                        options: {
                            onError: viewError
                        }
                    });
                }
            });
        }
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
    _this.formView01.initView();
    _this.gridView01.initView();

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
                fnObj.gridView01.excel("후처리내역 통보이력-"+getFormattedDate(new Date())+".xls");
                // ACTIONS.dispatch(ACTIONS.EXCEL_DOWNLOAD);
            },
            "search-view-clear": function () {
                $("#filter").val("");
                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
                $("#terminalNo").val("");
                $("#startDate").val(getFormattedDate(new Date(),true));
                $("#endDate").val(getFormattedDate(new Date()));
                if(sessionJson.userId == "system" || sessionJson.jisaCode == "00") {
                    $("#jisaCode").val("");
                }

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

        $("#startDate").val(getFormattedDate(new Date(), true));
        $("#endDate").val(getFormattedDate(new Date()));

        axboot.buttonClick(this, "data-searchview-btn", {
            "modal": function () {
                ACTIONS.dispatch(ACTIONS.MODAL_OPEN)
            }
        });
        if(sessionJson.userId != "system" && sessionJson.jisaCode != "00") {
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
                {
                    key: 'jisaCode', label: '지사명', width: 70, align: 'center', formatter: function formatter() {
                    return parent.COMMON_CODE["JISA_CODE"].map[this.value];
                }
                },
                {
                    key: 'branchCode', label: '지점명', width: 150, align: 'left', formatter: function formatter() {
                    return parent.COMMON_CODE["BRANCH_CODE"].map[this.value];
                }
                },
                {
                    key: 'cornerCode', label: '코너명', width: 130, align: 'left', formatter: function formatter() {
                    return parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[this.item.terminalNo];
                }
                },
                {key: 'terminalNo', label: '단말번호', width: 80, align: 'center'},
                {
                    key: 'stextSendGubun', label: '전송여부', width: 100, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["STEXT_SEND_GUBUN"].map[this.value];
                    }
                },
                {key: 'stextChasu', label: '전문순번', width: 80, align: 'center', editor: 'text'},
                {key: 'handleOccurDatetime', label: '후처리발생일시', width: 130, align: 'center', editor: 'text'},
                {key: 'handleSeqNo', label: '후처리 일련번호', width: 100, align: 'center', editor: 'text'},
                {key: 'handleNoticeDatetime', label: '통보일시', width: 130, align: 'center', editor: 'text'},
                {key: 'handleNoticeEmpName', label: '통보자명', width: 80, align: 'center', editor: 'text'},
                {key: 'handleNoticeEmpTelno', label: '통보자연락처', width: 110, align: 'center', editor: 'text'},
                {key: 'handleReqDesc', label: '후처리요청의견', width: 150, align: 'left', editor: 'text'},
                {key: 'handleStatus', label: '조치상태', width: 100, align: 'left', formatter: function formatter() {
                    return parent.COMMON_CODE["HANDLE_STATUS"].map[this.value];
                }
                },
                {key: 'smsEnable', label: 'SMS 대상여부', width: 100, align: 'left', formatter: function formatter() {
                    return parent.COMMON_CODE["SMS_SUBJECT_ENABLE"].map[this.value];
                }
                },
                {key: 'customerName', label: '고객명', width: 100, align: 'left'},
                {key: 'customerTelno', label: '고객연락처', width: 100, align: 'left'},
                {key: 'errorType', label: '장애구분', width: 100, align: 'left', formatter: function formatter() {
                    return parent.COMMON_CODE["ERROR_GUBUN"].map[this.value];
                }
                },
                {key: 'accountNo', label: '계좌번호', width: 100, align: 'left'},
                {key: 'dealSeqNo', label: '거래일련번호', width: 100, align: 'left'},
                {key: 'dealAmount', label: '거래금액', width: 100, align: 'right', formatter:'money'},
                {key: 'handleDatetime', label: '조치일시', width: 130, align: 'center', editor: 'text'},
                {key: 'handleEmpName', label: '조치자명', width: 80, align: 'center', editor: 'text'},
                {key: 'handleEmpTelno', label: '조치자연락처', width: 110, align: 'center', editor: 'text'},
                {key: 'handleDesc', label: '조치내용', width: 150, align: 'left', editor: 'text'}
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex);
                    selectedItem = this.item;

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
 * formView01 - 후처리내역 조치 상세정보
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

        axboot.buttonClick(this, "data-form-view-01-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
            "send-stext": function () {
                // 조치결과 전송
                ACTIONS.dispatch(ACTIONS.SEND_STEXT);
            },
            "search-view-clear": function () {
                $("#filter").val("");
                $("#jisaCode").val("");
                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
                $("#terminalNo").val("");
                $("#startDate").val(getFormattedDate(new Date(),true));
                $("#endDate").val(getFormattedDate(new Date()));
                fnObj.gridView01.setPageData({pageNumber: 0});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
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

        if ($("#handleDate").val() == "") {
            message = '\n' + '조치일은 필수 입력조건입니다.\n' + '조치일을 입력하세요';
            // $("#jisaCodeForm").focus();
            formError(message);
            return false;
        }

        if ($("#handleTime").val() == "") {
            message = '\n' + '조치시각은 필수 입력조건입니다.\n' + '조치시각을 입력하세요';
            // $("#jisaCodeForm").focus();
            formError(message);
            return false;
        }

        if ($("#handleEmpName").val() == "") {
            message = '\n' + '조치자명은 필수 입력조건입니다.\n' + '조치자명을 입력하세요';
            // $("#jisaCodeForm").focus();
            formError(message);
            return false;
        }

        /*if ($("#handleEmpTelno").val() == "") {
            message = '\n' + '조치자 연락처는 필수 입력조건입니다.\n' + '조치자연락처를 입력하세요';
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

var buildParams = function (json) {

    var params = JSON.stringify(json);

    params = params.replace(/{/g, "");
    params = params.replace(/}/g, "");
    params = params.replace(/:/g, "=")
    params = params.replace(/,/g, "&");
    params = params.replace(/"/g, "");

    return params;

}

function getDatetimeStr(dateTime) {
    if (dateTime.length > 5) {
        return dateTime;
    } else {
        return dateTime + ":00";
    }
}

function getFormattedDate(date, isStart) {
    var day;
    var tempDate;
    if(isStart){
        date.setDate(date.getDate() - 7);
        tempDate = date.getDate();
    }else{
        tempDate = date.getDate();
    }
    day = tempDate.toString();

    var year = date.getFullYear();
    var month = (1 + date.getMonth()).toString();
    month = month.length > 1 ? month : '0' + month;
    day = day.length > 1 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}