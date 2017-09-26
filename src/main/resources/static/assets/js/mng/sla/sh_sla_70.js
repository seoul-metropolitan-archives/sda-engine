var fnObj = {};
var gridDataList;

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/sla/sh_sla_70",
            data: $.extend({}, this.searchView.getData(), this.gridView01.getPageData()),
            callback: function (res) {
                caller.gridView01.setData(res);

                gridDataList = res.list;
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
        window.location = CONTEXT_PATH + "/api/v1//mng/sla/sh_sla_70/download?" + params;
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        // 불인정유무처리된데이터는 불인정사유가 있는지 체크
        if (caller.gridView01.validate()) {
            var saveList = [].concat(gridDataList);

            var saveUrl = "";

            if (data === "save") {
                saveUrl = "/api/v1//mng/sla/sh_sla_70";
            } else {
                saveUrl = "/api/v1//mng/sla/sh_sla_70/writeSla";
            }

            axboot
                .call({
                    type: "PUT",
                    //url: ["samples", "parent"],
                    url: saveUrl,
                    data: JSON.stringify(saveList),
                    callback: function (res) {
                    }
                })
                .done(function () {
                    fnObj.gridView01.setPageData({pageNumber: 0});
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    axToast.push("저장 작업이 완료되었습니다.");
                });
        } else {
            var message = '\n' + '불인정사유항목에 \n' + '사유를 반드시 입력하시기 바랍니다.';
            formError(message);
        }
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
    ITEM_CLICK: function (caller, act, data) {
        axboot.ajax({
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

// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    CODE = this; // this는 call을 통해 수집된 데이터들.

    _this.pageButtonView.initView();
    _this.searchView.initView();
    //_this.formView01.initView();
    _this.gridView01.initView();

    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.pageResize = function () {
};

fnObj.pageButtonView = axboot.viewExtend({
    initView: function () {
        axboot.buttonClick(this, "data-page-btn", {
            "save": function () {
                axDialog.confirm({
                    dialogTitle: "확인",
                    msg: "저장하시겠습니까?"
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.PAGE_SAVE, "save");
                    }
                });
            },
            "write": function () {
                axDialog.confirm({
                    dialogTitle: "확인",
                    msg: "소명내역을 파일로 저장하시겠습니까?"
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.PAGE_SAVE, "write");
                    }
                });
            },
            "search": function () {
                fnObj.gridView01.setPageData({pageNumber: 0});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            "excel": function () {
                fnObj.gridView01.excel("패널티내역-" + getFormattedDate(new Date()) + ".xls");
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
                $("#endDate").val(getFormattedDate(new Date()));
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
        $("#endDate").val(getFormattedDate(new Date()));

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
        pageSize: 150
    },
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({
            showRowSelector: true,
            multipleSelect: true,
            frozenColumnIndex: 0,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {key: 'txId', label: '기기번호', width: 100, align: 'center', editor: 'center'},
                {key: 'errorDatetime', label: '장애발생일시', width: 130, align: 'center'},
                {key: 'calleeChasu', label: '출동차수', width: 70, align: 'center'},
                {
                    key: 'customerWait',
                    label: '고객대기',
                    width: 70,
                    align: 'center',
                    formatter: function formatter() {
                        var customerWait = "";

                        switch (this.value) {
                            case "1"   :
                                customerWait = "유";
                                break;
                            case "2"   :
                                customerWait = "무";
                                break;
                            default    :
                                customerWait = "";
                                break;
                        }

                        return customerWait;
                    }
                },
                {key: 'evalType', label: '평가구분', width: 70, align: 'center'},
                {key: 'corpCode', label: '업체코드', width: 70, align: 'center'},
                {key: 'calleeType', label: '출동형태', width: 70, align: 'center'},
                {
                    key: 'jisaCode', label: '지사명', width: 70, align: 'center', formatter: function formatter() {
                    return parent.COMMON_CODE["JISA_CODE"].map[this.value];
                }
                },
                {key: 'branchCode', label: '지점코드', width: 70, align: 'center'},
                {key: 'branchName', label: '지점명', width: 100, align: 'left'},
                {key: 'cornerCode', label: '코너코드', width: 70, align: 'center'},
                {key: 'cornerName', label: '코너명', width: 130, align: 'left'},
                {key: 'location', label: '위치구분', width: 90, align: 'center'},
                {key: 'area', label: '지역구분', width: 90, align: 'center'},
                {key: 'terminalNo', label: '단말번호', width: 70, align: 'center'},
                {key: 'calleeGubun', label: '출동구분', width: 80, align: 'center'},
                {key: 'calleeReqDatetime', label: '출동요청일시', width: 130, align: 'center'},
                {key: 'arrivalPlanDatetime', label: '도착예정일시', width: 130, align: 'center'},
                {key: 'arrivalDatetime', label: '도착일시', width: 130, align: 'center'},
                {key: 'arrivalType', label: '도착처리방법', width: 120, align: 'center'},
                {key: 'calleeReqElapsedTime', label: '출동경과시간', width: 90, align: 'center'},
                {key: 'calleeReqElapsedSeconds', label: '출동경과시간(초)', width: 110, align: 'center'},
                {key: 'arrivalElapsedTime', label: '도착준수시간', width: 90, align: 'center'},
                {key: 'arrivalElapsedSeconds', label: '도착준수시간(초)', width: 110, align: 'center'},
                {key: 'reportDatetime', label: '도착후업체보고예정일시', width: 150, align: 'center'},
                {key: 'errorContent', label: '장애내용', width: 200, align: 'left'},
                {key: 'modelName', label: '기종', width: 130, align: 'left'},
                {
                    key: 'accept', label: '인정유뮤', width: 70, align: 'center',
                    formatter: function formatter() {
                        if (checkUndefined(this.value) != "") {
                            if (this.value === "0") {
                                return "인정";
                            } else {
                                return "불인정";
                            }

                        } else {
                            return "";
                        }
                    }
                },
                {key: 'refuseReason', label: '불인정사유', width: 100, align: 'left', editor: "text"}
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex);
                    //ACTIONS.dispatch(ACTIONS.ITEM_CLICK, this.item);
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
            },
            "accept": function () {
                var orgDataList = gridDataList;
                var gridSelectedDataList = fnObj.gridView01.getData("selected");

                orgDataList.forEach(function (n) {
                    gridSelectedDataList.forEach(function (m) {
                        if (n.txId === m.txId && n.errorDatetime === m.errorDatetime && n.calleeChasu === m.calleeChasu) {
                            n.accept = "0";
                        }
                    });
                });

                fnObj.gridView01.setData(orgDataList);
            },
            "deny": function () {
                var orgDataList = gridDataList;
                var gridSelectedDataList = fnObj.gridView01.getData("selected");

                orgDataList.forEach(function (n) {
                    gridSelectedDataList.forEach(function (m) {
                        if (n.txId === m.txId && n.errorDatetime === m.errorDatetime && n.calleeChasu === m.calleeChasu) {
                            n.accept = "1";
                        }
                    });
                });

                fnObj.gridView01.setData(orgDataList);
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);
        list = _list;

        return list;
    },
    addRow: function () {
        this.target.addRow({__created__: true}, "last");
    },
    excel: function (file) {
        this.target.exportExcel(file);
    },
    validate: function () {
        if (gridDataList == null || gridDataList.length == 0) {
            var message = '\n' + '저장하기 위한 데이터가 없습니다.';
            formError(message);
            return false;
        }

        var falseCnt = 0;
        gridDataList.forEach(function (n) {
            if (n.accept == "1") {
                if (checkUndefined(n.refuseReason) == "" || n.refuseReason.length == 0) {
                    falseCnt++;
                }
            }
        });

        if (falseCnt > 0) {
            return false;
        } else {
            return true;
        }
    }
});

/**
 * formView01 - 출동요청 상세정보
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

var buildParams = function (json) {

    var params = JSON.stringify(json);

    params = params.replace(/{/g, "");
    params = params.replace(/}/g, "");
    params = params.replace(/:/g, "=")
    params = params.replace(/,/g, "&");
    params = params.replace(/"/g, "");

    return params;

}


function checkUndefined(value) {
    if (typeof value === "undefined") {
        return "";
    } else {
        return value;
    }
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

    if (isStart) {
        return year + '-' + month + '-' + '01';
    } else {
        return year + '-' + month + '-' + day;
    }
}