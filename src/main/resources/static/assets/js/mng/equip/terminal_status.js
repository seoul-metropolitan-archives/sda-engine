var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            //url: ["samples", "parent"],
            url: "/api/v1//mng/equip/terminal_status",
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
                    url: "/api/v1//mng/equip/terminal_status",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
                    }
                })
                .done(function () {
                    caller.formView01.clear();
                    fnObj.gridView01.setPageData({pageNumber: 0});
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    axToast.push("저장 작업이 완료되었습니다.");
                });
        }
    },
    PAGE_SAVE2: function (caller, act, data) {
        if (caller.formView01.validate() && caller.formView03.validate()) {
            var formView01Data = caller.formView01.getData();
            var formView03Data = caller.formView03.getData();
            var parentData = {
                jisaCode: formView01Data.jisaCode,
                branchCode: formView01Data.branchCode,
                branchName: formView01Data.branchName,
                cornerCode: formView01Data.cornerCode,
                cornerName: formView01Data.cornerName,
                terminalNo: formView01Data.terminalNo,
                operEnable: formView01Data.operEnable,
                modelCode: formView01Data.modelCode,
                branchGubun: formView01Data.branchGubun,
                terminalType: formView01Data.terminalType,
                placeGubun: formView01Data.placeGubun,
                operTimeGubun: formView01Data.operTimeGubun,
                weekendOperGubun: formView01Data.weekendOperGubun,
                checkOperEnable: formView01Data.checkOperEnable,
                cash50kOperEnable: formView01Data.cash50kOperEnable,
                operStartTime: formView01Data.operStartTime,
                operEndTime: formView01Data.operEndTime,
                taskApplyDate: formView01Data.taskApplyDate,
                workDate: formView01Data.workDate,
                securityCorp: formView01Data.securityCorp,
                installPlaceGubun: formView01Data.installPlaceGubun,
                photoEnable: formView01Data.photoEnable,
                boothCorp: formView01Data.boothCorp,
                boothType: formView01Data.boothType,
                addr: formView01Data.addr,
                terminalCorpCode: formView01Data.terminalCorpCode,

                intercomEnable: formView03Data.intercomEnable,
                envelopeEnable: formView03Data.envelopeEnable,
                garbagecanEnable: formView03Data.garbagecanEnable,
                shredderEnable: formView03Data.shredderEnable,
                extinguisherEnable: formView03Data.extinguisherEnable,
                posterCount: formView03Data.posterCount,
                coolerHeaterEnable: formView03Data.coolerHeaterEnable,
                slopeEnable: formView03Data.slopeEnable,
                hireTerminalEnable: formView03Data.hireTerminalEnable,
                installTerminalGubun: formView03Data.installTerminalGubun,
                terminalHireFee: formView03Data.terminalHireFee,
                gatewayIpAddr: formView03Data.gatewayIpAddr,
                terminalIpAddr: formView03Data.terminalIpAddr,
                terminalSmaskAddr: formView03Data.terminalSmaskAddr,
                terminalProdNo: formView03Data.terminalProdNo,
                intercomNo: formView03Data.intercomNo,
                terminalLocation: formView03Data.terminalLocation,
                commOffice: formView03Data.commOffice,
                internetClassify: formView03Data.internetClassify,
                securityCorpContractNo: formView03Data.securityCorpContractNo,
                mngChannel: formView03Data.mngChannel
            };
            console.log("form save", parentData);
            axboot
                .call({
                    type: "PUT",
                    url: "/api/v1//mng/equip/terminal_status",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
                    }
                })
                .done(function () {

                    caller.formView01.clear();
                    caller.formView02.clear();
                    caller.formView03.clear();
                    axToast.push("저장 작업이 완료되었습니다.");

                    fnObj.gridView01.setPageData({pageNumber: 0});
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
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
                caller.formView02.clear();
                caller.formView03.clear();
            }
        });
    },
    FORM_CLEAR4: function (caller, act, data) {
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                axDialog.close();
                caller.formView04.clear();
            }
        });
    },
    ITEM_CLICK: function (caller, act, data) {

        data.jisaCodeName = parent.COMMON_CODE["JISA_CODE"].map[data.jisaCode];
        data.branchCodeName = parent.COMMON_CODE["BRANCH_CODE"].map[data.branchCode];

        caller.formView01.setData(data);
        caller.formView03.setData(data);

        axboot
            .call({
                type: "GET",
                url: "/api/v1/mng/error/sh01001110",
                data: {
                    resentlyDetails: true,
                    jisaCode: data.jisaCode,
                    branchCode: data.branchCode,
                    cornerCode: data.cornerCode,
                    terminalNo: data.terminalNo
                },
                callback: function (res) {
                    caller.formView02.setData(data);
                },
                options: {
                    onError: viewError
                }
            })
            .call({
                    type: "GET",
                    url: "/api/v1//mng/equip/sh02001130",
                    data: $.extend({}, {
                        jisaCode: data.jisaCode,
                        branchCode: data.branchCode,
                        cornerCode: data.cornerCode,
                        terminalNo: data.terminalNo
                    }, this.gridView02.getPageData()),
                    callback: function (res) {
                        caller.gridView02.setData(res);
                    },
                    options: {
                        onError: viewError
                    }
                }
            )
            .call({
                    type: "GET",
                    url: "/api/v1//mng/equip/sh02001210",
                    data: $.extend({}, {
                        branchCode: data.branchCode,
                        cornerCode: data.cornerCode,
                        terminalNo: data.terminalNo
                    }, this.gridView03.getPageData()),
                    callback: function (res) {
                        caller.gridView03.setData(res);
                    },
                    options: {
                        onError: viewError
                    }
                }
            )
            .call({
                    type: "GET",
                    url: "/api/v1//mng/equip/sh02001140",
                    data: $.extend({}, {
                        jisaCode: data.jisaCode,
                        branchCode: data.branchCode,
                        cornerCode: data.cornerCode,
                        terminalNo: data.terminalNo
                    }, this.gridView04.getPageData()),
                    callback: function (res) {
                        caller.gridView04.setData(res);
                    },
                    options: {
                        onError: viewError
                    }
                }
            )
            .call({
                    type: "GET",
                    url: "/api/v1//mng/equip/sh02001150",
                    data: $.extend({}, {
                        jisaCode: data.jisaCode,
                        branchCode: data.branchCode,
                        cornerCode: data.cornerCode,
                        terminalNo: data.terminalNo
                    }, this.gridView05.getPageData()),
                    callback: function (res) {
                        caller.gridView05.setData(res);
                    },
                    options: {
                        onError: viewError
                    }
                }
            )
            .done(function () {
            });

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
    ITEM_CLICK2: function (caller, act, data) {
        console.log("formview04", data);
        caller.formView04.setData(data);
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
                //console.log('MODAL_OPEN: ', data);
                $("#branchName").val(data.branchName);
                $("#branchCode").val(data.branchCode);
                $("#cornerName").val(data.cornerName);
                $("#terminalNo").val(data.terminalNo);
                $("#jisaCode").val(data.jisaCode);

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
                    "jisa": caller.formView04.get("jisaCode")
                };
            },
            callback: function (data) {
                caller.formView04.setSingleData("jisaCode", data.jisaCode);
                caller.formView04.setSingleData("branchName", data.branchName);
                caller.formView04.setSingleData("branchCode", data.branchCode);
                caller.formView04.setSingleData("cornerName", data.cornerName);
                caller.formView04.setSingleData("cornerCode", data.cornerCode);

                this.close();
            }
        });
    },
    FORM_MODAL_OPEN4: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_TERMINAL_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisa": caller.formView04.get("jisaCode")
                };
            },
            callback: function (data) {
                caller.formView04.setSingleData("jisaCode", data.jisaCode);
                caller.formView04.setSingleData("branchName", data.branchName);
                caller.formView04.setSingleData("branchCode", data.branchCode);
                caller.formView04.setSingleData("cornerName", data.cornerName);
                caller.formView04.setSingleData("cornerCode", data.cornerCode);
                caller.formView04.setSingleData("terminalNo", data.terminalNo);

                this.close();
            }
        });
    },
    FORM_SAVE4: function (caller, act, data) {
        axDialog.confirm({
            msg: "기기설치/철수결과 전문을 전송하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                if (caller.formView04.validate()) {
                    var parentData = caller.formView04.getData();
                    axboot.ajax({
                        type: "PUT",
                        url: "/api/v1//mng/equip/sh02001150",
                        data: JSON.stringify(parentData),
                        callback: function (res) {
                            caller.formView04.clear();
                            axToast.push("전문전송 작업이 완료되었습니다.");
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
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/terminal_status/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD2: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001130/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD3: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001210/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD4: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001140/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD5: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001150/download?" + params;
        return false;
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
    _this.gridView02.initView();
    _this.gridView03.initView();
    _this.gridView04.initView();
    _this.gridView05.initView();
    _this.formView04.initView();

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
                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
                $("#terminalNo").val("");
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
            branchName: $("#branchName").val(),
            branchCode: $("#branchCode").val(),
            cornerName: $("#cornerName").val(),
            terminalNo: $("#terminalNo").val()
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
            frozenColumnIndex: 4,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {
                    key: 'jisaCode', label: '지사코드', width: 70, align: 'center', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["JISA_CODE"].map[this.value];
                    }
                },
                {key: 'branchName', label: '지점명', width: 150, align: 'left', editor: 'text'},
                {key: 'cornerName', label: '코너명', width: 150, align: 'left', editor: 'text'},
                {key: 'terminalNo', label: '단말번호', width: 70, align: 'center', editor: 'text'},
                {
                    key: 'operEnable', label: '운영여부', width: 70, align: 'center', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["OPER_ENABLE"].map[this.value];
                    }
                },
                {
                    key: 'modelCode', label: '모델코드', width: 70, align: 'center', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["MODEL_CODE"].map[this.value];
                    }
                },
                {
                    key: 'branchGubun', label: '점포구분', width: 80, align: 'center', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["BRANCH_GUBUN"].map[this.value];
                    }
                },
                {
                    key: 'terminalType', label: '단말형태', width: 70, align: 'center', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["TERMINAL_TYPE"].map[this.value];
                    }
                },
                {
                    key: 'placeGubun', label: '장소구분', width: 70, align: 'center', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["PLACE_GUBUN"].map[this.value];
                    }
                },
                {
                    key: 'operTimeGubun', label: '운영시간구분', width: 80, align: 'center', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["OPER_TIME_GUBUN"].map[this.value];
                    }
                },
                {
                    key: 'checkOperEnable', label: '수표운영구분', width: 80, align: 'center', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["CHECK_OPER_ENABLE"].map[this.value];
                    }
                },
                {key: 'operStartTime', label: '운영시작시간', width: 90, align: 'center', editor: 'text'},
                {key: 'operEndTime', label: '운영종료시간', width: 90, align: 'center', editor: 'text'},
                {
                    key: 'securityCorp', label: '경비사코드', width: 80, align: 'center', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["SECURITY_CORP"].map[this.value];
                    }
                },
                {
                    key: 'terminalCorpCode', label: '기기사코드', width: 90, align: 'center', editor: 'text',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["TERMINAL_CORP_CODE"].map[this.value];
                    }
                }
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
                _this.excel("기기현황-" + getFormattedDate(new Date()) + ".xls");
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.jisaCode && this.branchCode && this.cornerCode && this.terminalNo;
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

        if(sessionJson.userId != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm1").attr('readonly', true);
            $("#jisaCodeForm1").attr('disabled', true);
            $("#jisaCodeForm1").val(sessionJson.jisaCode);
        }

        axboot.buttonClick(this, "data-form-view-01-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
            "form-save": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE2);
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

        if(sessionJson.userId != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm1").attr('readonly', true);
            $("#jisaCodeForm1").attr('disabled', true);
            $("#jisaCodeForm1").val(sessionJson.jisaCode);
        }
    }
});


/**
 * formView02
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

        axboot.buttonClick(this, "data-form-view-02-btn", {
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
 * formView03
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

        axboot.buttonClick(this, "data-form-view-03-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
            "form-save": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE2);
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
 * gridView
 */
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({
            showRowSelector: false,
            frozenColumnIndex: 0,
            multipleSelect: false,
            target: $('[data-ax5grid="grid-view-02"]'),
            columns: [
                {key: 'workSeqNo', label: '작업일련번호', width: 100, align: 'center'},
                {key: 'terminalSeqNo', label: '기기일련번호', width: 100, align: 'center'},
                {key: 'changeChasu', label: '변경차수', width: 100, align: 'center'},
                {key: 'stextGubunName', label: '전문구분', width: 100, align: 'center'},
                {key: 'terminalCorpCodeName', label: '기기업체코드', width: 100, align: 'center'},
                {key: 'modelCodeName', label: '기종코드', width: 150, align: 'center'},
                {key: 'hireTerminalEnableName', label: '임차기기여부', width: 100, align: 'center'},
                {key: 'installTerminalGubunName', label: '설치기기구분', width: 100, align: 'center'},
                {key: 'terminalHireFee', label: '기기임차료', width: 100, align: 'right', formatter: 'money'},
                {key: 'gatewayIpAddr', label: 'GATEWAY IP주소', width: 150, align: 'center'},
                {key: 'terminalIpAddr', label: '기기 IP주소', width: 100, align: 'center'},
                {key: 'terminalSmaskAddr', label: '기기 SMASK주소', width: 100, align: 'center'},
                {key: 'workPlanDatetime', label: '작업예정일', width: 150, align: 'center'},
                {key: 'terminalProdNo', label: '기기제조번호', width: 100, align: 'center'}
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, {selectedClear: true});
                    ACTIONS.dispatch(ACTIONS.ITEM_CLICK2, this.item);
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({
                    pageNumber: pageNumber
                });
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });

        axboot.buttonClick(this, "data-grid-view-02-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "excel": function () {
                _this.excel("기기신규등록통보이력-" + getFormattedDate(new Date()) + ".xls");
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.workSeqNo;
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
 * gridView
 */
fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({
            showRowSelector: false,
            frozenColumnIndex: 0,
            multipleSelect: false,
            target: $('[data-ax5grid="grid-view-03"]'),
            columns: [
                {key: 'workSeqNo', label: '작업일련번호', width: 100, align: 'center'},
                {key: 'terminalSeqNo', label: '기기일련번호', width: 100, align: 'center'},
                {key: 'changeChasu', label: '변경차수', width: 100, align: 'center'},
                {key: 'stextGubunName', label: '전문구분', width: 100, align: 'center'},
                {key: 'terminalCorpCodeName', label: '기기업체코드', width: 100, align: 'center'},
                {key: 'modelCodeName', label: '기종코드', width: 100, align: 'center'},
                {key: 'hireTerminalEnableName', label: '임차기기여부', width: 100, align: 'center'},
                {key: 'nouseGubunName', label: '철수구분', width: 100, align: 'center'},
                {key: 'workPlanDatetime', label: '작업예정일', width: 150, align: 'center'}
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, {selectedClear: true});
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({
                    pageNumber: pageNumber
                });
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });

        axboot.buttonClick(this, "data-grid-view-03-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "excel": function () {
                _this.excel("기기변경통보이력-" + getFormattedDate(new Date()) + ".xls");
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.workSeqNo;
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
 * gridView
 */
fnObj.gridView04 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({
            showRowSelector: false,
            frozenColumnIndex: 0,
            multipleSelect: false,
            target: $('[data-ax5grid="grid-view-04"]'),
            columns: [
                {key: 'workSeqNo', label: '작업일련번호', width: 100, align: 'center'},
                {key: 'terminalSeqNo', label: '기기일련번호', width: 100, align: 'center'},
                {key: 'changeChasu', label: '변경차수', width: 100, align: 'center'},
                {key: 'stextGubunName', label: '전문구분', width: 100, align: 'center'},
                {key: 'terminalCorpCodeName', label: '기기업체코드', width: 100, align: 'center'},
                {key: 'modelCodeName', label: '기종코드', width: 150, align: 'center'},
                {key: 'hireTerminalEnableName', label: '임차기기여부', width: 100, align: 'center'},
                {key: 'nouseGubunName', label: '철수구분', width: 100, align: 'center'},
                {key: 'workPlanDatetime', label: '작업예정일', width: 150, align: 'center'}
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, {selectedClear: true});
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({
                    pageNumber: pageNumber
                });
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });

        axboot.buttonClick(this, "data-grid-view-04-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "excel": function () {
                _this.excel("기기철수통보이력-" + getFormattedDate(new Date()) + ".xls");
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.workSeqNo;
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
 * gridView
 */
fnObj.gridView05 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({
            showRowSelector: false,
            frozenColumnIndex: 0,
            multipleSelect: false,
            target: $('[data-ax5grid="grid-view-05"]'),
            columns: [
                {key: 'workSeqNo', label: '작업일련번호', width: 100, align: 'center', editor: 'text'},
                {key: 'terminalSeqNo', label: '기기일련번호', width: 100, align: 'center', editor: 'text'},
                {key: 'resultStextGubunName', label: '결과전문구분', width: 100, align: 'center', editor: 'text'},
                {key: 'terminalCorpCodeName', label: '기기업체코드', width: 100, align: 'center', editor: 'text'},
                {key: 'modelCodeName', label: '기종코드', width: 150, align: 'center', editor: 'text'},
                {key: 'terminalProdNo', label: '기기제조번호', width: 100, align: 'center', editor: 'text'},
                {key: 'workCompleteEnableName', label: '작업완료여부', width: 100, align: 'center', editor: 'text'},
                {key: 'workCompleteDate', label: '작업완료일', width: 100, align: 'center', editor: 'text'},
                {key: 'unusl', label: '특이사항', width: 100, align: 'left', editor: 'text'},
                {
                    key: 'stextSendGubun', label: '전송여부', width: 100, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["STEXT_SEND_GUBUN"].map[this.value];
                    }
                }
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, {selectedClear: true});
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({
                    pageNumber: pageNumber
                });
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });

        axboot.buttonClick(this, "data-grid-view-05-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "excel": function () {
                _this.excel("기기설치/철수결과전송이력-" + getFormattedDate(new Date()) + ".xls");
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.workSeqNo;
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
 * formView04
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


        if(sessionJson.userId != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm4").attr('readonly', true);
            $("#jisaCodeForm4").attr('disabled', true);
            $("#jisaCodeForm4").val(sessionJson.jisaCode);
        }

        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        axboot.buttonClick(this, "data-form-view-04-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR4);
            },
            "form-save": function () {
                ACTIONS.dispatch(ACTIONS.FORM_SAVE4);
            },
            "form_modal": function () {
                ACTIONS.dispatch(ACTIONS.FORM_MODAL_OPEN4);
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
    get: function (dataPath) {
        return this.model.get(dataPath);
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

        if (this.get("workSeqNo").length != 8) {
            formError('\n' + '작업일련번호는 8자리입니다.');
            return false;
        }

        if (this.get("terminalSeqNo").length != 2) {
            formError('\n' + '기기 일련번호는 2자리입니다.');
            return false;
        }

        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
        if(sessionJson.userId != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm4").attr('readonly', true);
            $("#jisaCodeForm4").attr('disabled', true);
            $("#jisaCodeForm4").val(sessionJson.jisaCode);
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