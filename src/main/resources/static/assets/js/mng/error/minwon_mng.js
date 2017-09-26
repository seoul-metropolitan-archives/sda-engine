var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            //url: ["samples", "parent"],
            url: "/api/v1//mng/error/minwon_mng",
            data: $.extend({}, this.searchView.getData(), this.gridView01.getPageData()),
            callback: function (res) {
                caller.gridView01.setData(res);
            },
            options: {
                onError: viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        if (caller.formView01.validate()) {
            var parentData = caller.formView01.getData();
            parentData.lastModifyEmpName = sessionJson.userNm;

            axboot
                .call({
                    type: "PUT",
                    url: "/api/v1//mng/error/minwon_mng",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
                    }
                })
                .done(function () {
                    fnObj.gridView01.setPageData({pageNumber: 0});
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    caller.formView01.clear();
                    axToast.push("저장 작업이 완료되었습니다.");
                });
        }
    },
    PAGE_DELETE: function (caller, act, data) {

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
                    "jisaCode":  $("#jisaCode").val()
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
                    "jisa":  $("#jisaCodeForm").val()
                };
            },
            callback: function (data) {
                caller.formView01.setFormData("jisaCode", data.jisaCode);
                caller.formView01.setFormData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[data.branchCode]);
                caller.formView01.setFormData("branchCode", data.branchCode);
                caller.formView01.setFormData("cornerCode", data.cornerCode);
                caller.formView01.setFormData("cornerName", parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[data.terminalNo]);
                caller.formView01.setFormData("terminalNo", data.terminalNo);
                this.close();
            }
        });
    },
    ITEM_CLICK: function (caller, act, data) {
        caller.formView01.setData(data);

        caller.formView01.setFormData("branchName",parent.COMMON_CODE["BRANCH_CODE"].map[data.branchCode]);
        caller.formView01.setFormData("cornerName",parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[data.terminalNo]);
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
            "save": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            },
            "excel": function () {
                fnObj.gridView01.excel("민원관리 목록-"+getFormattedDate(new Date())+".xls");
                // ACTIONS.dispatch(ACTIONS.EXCEL_DOWNLOAD);
            },
            "delete": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_DELETE);
            },
            "search-view-clear": function () {
                $("#filter").val("");
                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
                $("#terminalNo").val("");
                $("#minwonType").val(""),
                $("#handleDept").val(""),
                $("#minwonStatus").val("")
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
        this.minwonType = $("#minwonType");
        this.handleDept = $("#handleDept");
        this.minwonStatus = $("#minwonStatus");
        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        $("#startDate").val(getFormattedDate(new Date(),true));
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
            filter: this.filter.val(),
            jisaCode: $("#jisaCode").val(),
            branchCode: $("#branchCode").val(),
            terminalNo: $("#terminalNo").val(),
            startDate: $("#startDate").val(),
            endDate: $("#endDate").val(),
            minwonType: $("#minwonType").val(),
            handleDept: $("#handleDept").val(),
            minwonStatus: $("#minwonStatus").val()
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
                {key: 'jisaCode', label: '지사명', width: 80, align: 'center', formatter: function formatter() {
                    return parent.COMMON_CODE["JISA_CODE"].map[this.value];
                }},
                {key: 'branchCode', label: '지점명', width: 100, align: 'left', formatter: function formatter() {
                    return parent.COMMON_CODE["BRANCH_CODE"].map[this.value];
                }},
                {key: 'cornerCode', label: '코너명', width: 150, align: 'left', formatter: function formatter() {
                    return parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[this.item.terminalNo];
                }},
                {key: 'terminalNo', label: '단말번호', width: 80, align: 'center'},
                {key: 'minwonType', label: '민원유형', width: 80, align: 'center', formatter: function formatter() {
                    return parent.COMMON_CODE["MINWON_TYPE"].map[this.value];
                }},
                {key: 'handleDept', label: '조치부서', width: 100, align: 'center', formatter: function formatter() {
                    return parent.COMMON_CODE["HANDLE_DEPT"].map[this.value];
                }},
                {key: 'minwonStatus', label: '조치상태', width: 100, align: 'center', formatter: function formatter() {
                    return parent.COMMON_CODE["MINWON_STATUS"].map[this.value];
                }},
                {key: 'minwonContent', label: '민원내용', width: 200, align: 'left'},
                {key: 'handleContent', label: '조치결과', width: 200, align: 'left'},
                {key: 'regDatetime', label: '등록일시', width: 130, align: 'center'},
                {key: 'updateDatetime', label: '수정일시', width: 130, align: 'center'},
                {key: 'lastModifyEmpName', label: '작성자', width: 100, align: 'center'}
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
                return this.branchCode && this.cornerCode && this.terminalNo && this.regDatetime;
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
        axboot.buttonClick(this, "data-form-view-01-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
            "modal": function () {
                ACTIONS.dispatch(ACTIONS.FORM_MODAL_OPEN)
            },
            "save": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            },
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
        var title;
        var message;

        if($("#jisaCodeForm").val()=="") {
            message = '\n' + '지사는 필수 입력조건입니다.\n' + '지사를 선택하세요.';
            $("#jisaCodeForm").focus();
            formError(message);
            return false;
        } else if($("#branchCodeForm").val()=="") {
            message = '\n' + '지점은 필수 입력조건입니다.\n' + '지점을 선택하세요.';
            $("#branchNameForm").focus();
            formError(message);
            return false;
        } else if($("#terminalNoForm").val()=="") {
            message = '\n' + '단말번호는 필수 입력조건입니다.\n' + '단말번호를 선택하세요.';
            $("#terminalNoForm").focus();
            formError(message);
            return false;
        } else if($("#minwonTypeForm").val()=="") {
            message = '\n' + '민원유형은 필수 입력조건입니다.\n' + '민원유형을 선택하세요.';
            $("#minwonTypeForm").focus();
            formError(message);
            return false;
        } else if($("#minwonStatusForm").val()=="") {
            message = '\n' + '조치상태는 필수 입력조건입니다.\n' + '조치상태를 선택하세요.';
            $("#minwonStatusForm").focus();
            formError(message);
            return false;
        } else if($("#handleDeptForm").val()=="") {
            message = '\n' + '조치부서는 필수 입력조건입니다.\n' + '조치부서를 선택하세요.';
            $("#handleDeptForm").focus();
            formError(message);
            return false;
        }else if($("#minwonContent").val()=="") {
            message = '\n' + '민원내용는 필수 입력조건입니다.\n' + '민원내용를 선택하세요.';
            $("#minwonContent").focus();
            formError(message);
            return false;
        }else if($("#handleContent").val()=="") {
            message = '\n' + '조치결과는 필수 입력조건입니다.\n' + '조치결과를 선택하세요.';
            $("#handleContent").focus();
            formError(message);
            return false;
        }

        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
        if(sessionJson.userId != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm").attr('readonly', true);
            $("#jisaCodeForm").attr('disabled', true);
            $("#jisaCodeForm").val(sessionJson.jisaCode);
        }
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