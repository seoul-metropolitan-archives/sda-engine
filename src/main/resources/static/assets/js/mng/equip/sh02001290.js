var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            //url: ["samples", "parent"],
            url: "/api/v1//mng/equip/sh02001290",
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
            axboot
                .call({
                    type: "PUT",
                    //url: ["samples", "parent"],
                    url: "/api/v1//mng/equip/sh02001290",
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
    FORM_CLEAR: function (caller, act, data) {
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                axDialog.close();
                caller.formView01.clear();
            }
        });
    },
    ITEM_CLICK: function (caller, act, data) {
        caller.formView01.setData(data);
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
                //console.log('MODAL_OPEN: ', data);
                $("#branchName").val(data.branchName);
                $("#branchCode").val(data.branchCode);
                $("#cornerName").val(data.cornerName);
                $("#jisaCode").val(data.jisaCode);
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
                    "jisa": caller.formView01.get("jisaCode")
                };
            },
            callback: function (data) {
                caller.formView01.setSingleData("jisaCode", data.jisaCode);
                caller.formView01.setSingleData("branchName", data.branchName);
                caller.formView01.setSingleData("branchCode", data.branchCode);
                caller.formView01.setSingleData("cornerName", data.cornerName);
                caller.formView01.setSingleData("cornerCode", data.cornerCode);
                caller.formView01.setSingleData("terminalNo", data.terminalNo);

                this.close();
            }
        });
    },
    FORM_SAVE: function (caller, act, data) {
        axDialog.confirm({
                msg: "일회성비용청구 전문을 전송하시겠습니까?"
            }, function () {
                if (this.key == "ok") {
                    if (caller.formView01.validate()) {

                        var parentData = caller.formView01.getData();
                        axboot.ajax({
                            type: "PUT",
                            url: "/api/v1//mng/equip/sh02001290",
                            data: JSON.stringify(parentData),
                            callback: function (res) {
                                axToast.push("일회성비용청구 전문을 전송하였습니다.");
                                fnObj.gridView01.setPageData({pageNumber: 0});
                                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                            },
                            options: {
                                onError: viewError
                            }
                        });
                    }
                }
        });
    },
    EXCEL_DOWNLOAD: function (caller, act, data) {
        var params = buildParams($.extend({}, this.searchView.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001290/download?" + params;
        return false;
    },

    ROLE_GRID_DATA_INIT: function (caller, act, data) {},
    ROLE_GRID_DATA_GET: function (caller, act, data) {},
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
            "save": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
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
//var picker = new ax5.ui.picker();

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

        /*picker.bind({
            target: $('[data-picker-date="month"]'),
            content: {
                type: 'date',
                config: {
                    mode: "month",
                    selectMode: "month"
                },
                formatter: {
                    pattern: 'date(month)'
                }
            }
        });*/

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
            branchName: $("#branchName").val(),
            branchCode: $("#branchCode").val(),
            cornerName: $("#cornerName").val(),
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
                {key: 'jisaCodeName', label: '지사코드', width: 70, align: 'center', editor: 'text'},
                {key: 'branchCodeName', label: '지점코드', width: 80, align: 'center', editor: 'text'},
                {key: 'cornerCode', label: '코너코드', width: 70, align: 'center', editor: 'text'},
                {key: 'terminalNo', label: '단말번호', width: 70, align: 'center', editor: 'text'},
                {key: 'cornerName', label: '코너명', width: 150, align: 'left', editor: 'text'},
                {
                    key: 'stextSendGubun', label: '전송여부', width: 100, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["STEXT_SEND_GUBUN"].map[this.value];
                    }
                },
                {key: 'billingMonth', label: '청구월', width: 130, align: 'center', editor: 'text'},
                {key: 'costGubunName', label: '비용구분', width: 70, align: 'center', editor: 'text'},
                {key: 'detailItemGubunName', label: '상세항목구분', width: 90, align: 'center', editor: 'text'},
                {key: 'seqNo', label: '일련번호', width: 150, align: 'center', editor: 'text'},
                {key: 'workDate', label: '작업일시', width: 130, align: 'center', editor: 'text'},
                {key: 'inspectionMonth', label: '검수월', width: 130, align: 'center', editor: 'text'},
                {key: 'billingAmt', label: '청구금액', width: 100, align: 'right', editor: 'text' , formatter: "money"},
                {key: 'detailContent', label: '상세내역', width: 100, align: 'center', editor: 'text'}
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
            },
            "excel": function () {
                _this.excel("일회성비용청구-"+getFormattedDate(new Date())+".xls");
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.txId;
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
            }
        });

        this.target.find('[data-ax5picker="month"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date',
                config: {
                    mode: "month", selectMode: "month"
                },
                formatter: {
                    pattern: 'date(month)'
                }
            }
        });
        axboot.buttonClick(this, "data-form-view-01-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
            "form_modal": function () {
                ACTIONS.dispatch(ACTIONS.FORM_MODAL_OPEN);
            },
            "form-save": function () {
                ACTIONS.dispatch(ACTIONS.FORM_SAVE);
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
    get: function(dataPath) {
        return this.model.get(dataPath);
    },
    setData: function (data) {

        if (typeof data === "undefined") data = this.getDefaultData();
        data = $.extend({}, data);

        this.target.find('[data-ax-path="key"]').attr("readonly", "readonly");

        this.model.setModel(data);
        this.modelFormatter.formatting(); // 입력된 값을 포메팅 된 값으로 변경
        if (this.get("branchCode") != null) {
            this.setSingleData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[this.get("branchCode")]);
        }
    },
    setSingleData: function (dataPath, value) {
        this.model.set(dataPath, value);
    },
    validate: function () {
        var rs = this.model.validate();

        if (this.get("branchCode") == "" || typeof this.get("branchCode") === "undefined") {
            //$("#branchCode").focus();
            formError('\n' + '지점은 필수 입력조건입니다.\n' + '지점을 선택하세요.');
            return false;
        }

        if (rs.error) {
            rs.error[0].jquery.focus();
            formError(rs.error[0].jquery.attr("title") + '을(를) 입력해주세요.');
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

var pageSearchAndviewError = function (err) {
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    viewError(err);
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

    params=params.replace(/{/g, "");
    params=params.replace(/}/g, "");
    params=params.replace(/:/g, "=")
    params=params.replace(/,/g, "&");
    params=params.replace(/"/g, "");

    return params;

}