var fnObj = {};
var clickListData = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            //url: ["samples", "parent"],
            url: "/api/v1//mng/equip/facility_status",
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
                    url: "/api/v1//mng/equip/facility_status",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
                    }
                })
                .done(function () {
                    caller.formView01.clear();
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    axToast.push("저장 작업이 완료되었습니다.");
                });
        }
    },
    PAGE_SAVE2: function (caller, act, data) {
        if (caller.formView01.validate()) {
            var parentData = caller.formView01.getData();
            console.log("form1save", parentData);
            axboot
                .call({
                    type: "PUT",
                    url: "/api/v1//mng/equip/facility_status",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
                    }
                })
                .done(function () {

                    caller.formView01.clear();
                    axToast.push("저장 작업이 완료되었습니다.");

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
            }
        });
    },
    FORM_CLEAR2: function (caller, act, data) {
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                axDialog.close();
                caller.gridView02.clear();
                caller.formView02.clear();
            }
        });
    },
    FORM_CLEAR3: function (caller, act, data) {
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                axDialog.close();
                caller.formView03.clear();
            }
        });
    },
    ITEM_CLICK: function (caller, act, data) {
        clickListData = data;
        caller.formView01.setData(data);

        fnObj.gridView02.setPageData({pageNumber: 0});
        fnObj.gridView03.setPageData({pageNumber: 0});
        fnObj.gridView04.setPageData({pageNumber: 0});
        fnObj.gridView05.setPageData({pageNumber: 0});
        fnObj.gridView06.setPageData({pageNumber: 0});
        axboot
            .call({
                type: "GET",
                url: "/api/v1//mng/equip/sh02001160",
                data: $.extend({}, {
                    jisaCode: data.jisaCode,
                    branchCode: data.branchCode,
                    cornerCode: data.cornerCode,
                    facGubunCode: data.facGubunCode,
                    facCode: data.facCode
                }, this.gridView02.getPageData()),
                callback: function (res) {
                    caller.gridView02.setData(res);
                },
                options: {
                    onError: viewError
                }
            })
            .call({
                type: "GET",
                url: "/api/v1//mng/equip/sh02001190",
                data: $.extend({}, {
                    facGubunCode: data.facGubunCode,
                    facCode: data.facCode
                }, this.gridView03.getPageData()),
                callback: function (res) {
                    caller.gridView03.setData(res);
                },
                options: {
                    onError: viewError
                }
            })
            .call({
                type: "GET",
                url: "/api/v1//mng/equip/sh02001170",
                data: $.extend({}, {
                    jisaCode: data.jisaCode,
                    branchCode: data.branchCode,
                    cornerCode: data.cornerCode,
                    facGubunCode: data.facGubunCode,
                    facCode: data.facCode
                }, this.gridView04.getPageData()),
                callback: function (res) {
                    console.log("grid04", res);
                    caller.gridView04.setData(res);
                },
                options: {
                    onError: viewError
                }
            })
            .call({
                type: "GET",
                url: "/api/v1//mng/equip/sh02001180",
                data: $.extend({}, {
                    jisaCode: data.jisaCode,
                    branchCode: data.branchCode,
                    cornerCode: data.cornerCode,
                    facCode: data.facCode
                }, this.gridView05.getPageData()),
                callback: function (res) {
                    caller.formView02.setData(data);
                    caller.gridView05.setData(res);
                },
                options: {
                    onError: viewError
                }
            })
            .call({
                type: "GET",
                url: "/api/v1//mng/equip/sh02001230",
                data: $.extend({}, {
                    jisaCode: data.jisaCode,
                    branchCode: data.branchCode,
                    cornerCode: data.cornerCode,
                }, this.gridView06.getPageData()),
                callback: function (res) {
                    caller.formView03.setData(data);
                    caller.gridView06.setData(res);
                },
                options: {
                    onError: viewError
                }
            })
            .done(function () {
            });
    },
    ITEM_CLICK2: function (caller, act, data) {
        console.log("formView02", data);
        /*caller.formView02.setData(data);*/
    },
    ITEM_CLICK3: function (caller, act, data) {
        fnObj.formView03.setSingleData("changeBeforeAssetSeqNo", data.changeBeforeAssetSeqNo);
        fnObj.formView03.setSingleData("changeAfterAssetSeqNo", data.changeAfterAssetSeqNo);
        fnObj.formView03.setSingleData("changeDate", data.changeDate);
        fnObj.formView03.setSingleData("changeTime", data.changeTime);
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
                    "jisa": caller.formView01.get("jisaCode")
                };
            },
            callback: function (data) {
                caller.formView01.setSingleData("jisaCode", data.jisaCode);
                caller.formView01.setSingleData("branchName", data.branchName);
                caller.formView01.setSingleData("branchCode", data.branchCode);
                caller.formView01.setSingleData("cornerName", data.cornerName);
                caller.formView01.setSingleData("cornerCode", data.cornerCode);

                this.close();
            }
        });
    },
    FORM_MODAL_OPEN2: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_TERMINAL_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisa": caller.formView02.get("jisaCode")
                };
            },
            callback: function (data) {
                caller.formView02.setSingleData("jisaCode", data.jisaCode);
                caller.formView02.setSingleData("branchName", data.branchName);
                caller.formView02.setSingleData("branchCode", data.branchCode);
                caller.formView02.setSingleData("cornerName", data.cornerName);
                caller.formView02.setSingleData("cornerCode", data.cornerCode);

                this.close();
            }
        });
    },
    FORM_MODAL_OPEN3: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_TERMINAL_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisa": caller.formView03.get("jisaCode")
                };
            },
            callback: function (data) {
                caller.formView03.setSingleData("jisaCode", data.jisaCode);
                caller.formView03.setSingleData("branchName", data.branchName);
                caller.formView03.setSingleData("branchCode", data.branchCode);
                caller.formView03.setSingleData("cornerName", data.cornerName);
                caller.formView03.setSingleData("cornerCode", data.cornerCode);

                this.close();
            }
        });
    },
    FORM_SAVE2: function (caller, act, data) {
        axDialog.confirm({
            msg: "시설물설치결과 전문을 전송하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                if (caller.formView02.validate()) {

                    var parentData = caller.formView02.getData();
                    axboot.ajax({
                        type: "PUT",
                        url: "/api/v1//mng/equip/sh02001180",
                        data: JSON.stringify(parentData),
                        callback: function (res) {
                            caller.formView02.clear();

                            axboot
                                .call({
                                    type: "GET",
                                    url: "/api/v1//mng/equip/sh02001180",
                                    data: $.extend({}, {
                                        jisaCode: clickListData.jisaCode,
                                        branchCode: clickListData.branchCode,
                                        cornerCode: clickListData.cornerCode,
                                        facCode: clickListData.facCode
                                    }),
                                    callback: function (res) {
                                        caller.gridView05.setData(res);
                                    },
                                    options: {
                                        onError: viewError
                                    }
                                })
                                .done(function () {
                                });

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
    FORM_SAVE3: function (caller, act, data) {
        axDialog.confirm({
            msg: "일련번호변경 전문을 전송하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                if (caller.formView03.validate()) {

                    var parentData = caller.formView03.getData();
                    parentData.changeDatetime = parentData.changeDate + " " + getDatetimeStr(parentData.changeTime);

                    axboot.ajax({
                        type: "PUT",
                        url: "/api/v1//mng/equip/sh02001230",
                        data: JSON.stringify(parentData),
                        callback: function (res) {
                            caller.formView03.clear();

                            axboot
                                .call({
                                    type: "GET",
                                    url: "/api/v1//mng/equip/sh02001230",
                                    data: $.extend({}, {
                                        jisaCode: clickListData.jisaCode,
                                        branchCode: clickListData.branchCode,
                                        cornerCode: clickListData.cornerCode,
                                    }),
                                    callback: function (res) {
                                        caller.gridView06.setData(res);
                                    },
                                    options: {
                                        onError: viewError
                                    }
                                })
                                .done(function () {
                                });

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
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/facility_status/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD2: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001160/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD3: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001220/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD4: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001170/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD5: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001180/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD6: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001230/download?" + params;
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
    _this.gridView02.initView();
    _this.gridView03.initView();
    _this.gridView04.initView();
    _this.gridView05.initView();
    _this.gridView06.initView();
    _this.formView02.initView();
    _this.formView03.initView();

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
                if(sessionJson.userCd == "system" || sessionJson.jisaCode == "00") {
                    $("#jisaCode").val("");
                }
                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
                $("#terminalNo").val("");
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
            branchName: $("#branchName").val(),
            branchCode: $("#branchCode").val(),
            cornerName: $("#cornerName").val()
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
                {key: 'branchCode', label: '지점코드', width: 80, align: 'center', editor: 'text'},
                {key: 'branchName', label: '지점명', width: 150, align: 'left', editor: 'text'},
                {key: 'cornerCode', label: '코너코드', width: 70, align: 'center', editor: 'text'},
                {key: 'cornerName', label: '코너명', width: 150, align: 'left', editor: 'text'},
                {key: 'facGubunCodeName', label: '시설물구분코드', width: 100, align: 'center', editor: 'text'},
                {key: 'facCodeName', label: '시설물코드', width: 150, align: 'center', editor: 'text'},
                {key: 'operEnableName', label: '운영여부', width: 70, align: 'center', editor: 'text'},
                {key: 'hireFacEnableName', label: '임차시설물여부', width: 100, align: 'center', editor: 'text'},
                {key: 'installArticleGubunName', label: '설치형태구분', width: 100, align: 'center', editor: 'text'},
                {key: 'assetSeqNo', label: '자산일련번호', width: 120, align: 'center', editor: 'text'},
                {key: 'facIpAddr', label: '시설물IP주소', width: 150, align: 'center', editor: 'text'},
                {key: 'facGwAddr', label: '시설물Gateway주소', width: 150, align: 'center', editor: 'text'},
                {key: 'facSmaskAddr', label: '시설물Subnetmask주소', width: 150, align: 'center', editor: 'text'},
                {key: 'hireFee', label: '임차료', width: 150, align: 'right', editor: 'text', formatter: 'money'},
                {key: 'detailFacInfo', label: '상세시설물정보', width: 100, align: 'left', editor: 'text'},
                {key: 'adoptDate', label: '적용일자', width: 130, align: 'center', editor: 'text'},
                {key: 'installDate', label: '설치일자', width: 130, align: 'center', editor: 'text'}
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
                _this.excel("시설물관리-" + getFormattedDate(new Date()) + ".xls");
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

        axboot.buttonClick(this, "data-form-view-01-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
            "form-save": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE2);
            },
            "form_modal": function () {
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
        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm").attr('readonly', true);
            $("#jisaCodeForm").attr('disabled', true);
            $("#jisaCodeForm").val(sessionJson.jisaCode);
        }
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
                {key: 'facSeqNo', label: '시설물일련번호', width: 100, align: 'center'},
                {key: 'changeChasu', label: '변경차수', width: 100, align: 'center'},
                {key: 'stextGubunName', label: '전문구분', width: 100, align: 'center'},
                {key: 'hireFacEnableName', label: '임차시설물여부', width: 100, align: 'center'},
                {key: 'installArticleGubunName', label: '설치물품구분', width: 100, align: 'center'},
                {key: 'assetSeqNo', label: '자산일련변호', width: 100, align: 'center'},
                {key: 'facIpAddr', label: '시설물 IP주소', width: 100, align: 'center'},
                {key: 'facGwAddr', label: '시설물 GW주소', width: 100, align: 'center'},
                {key: 'facSmaskAddr', label: '시설물 SMASK주소', width: 100, align: 'center'},
                {key: 'hireFee', label: '임차료', width: 100, align: 'left', formatter: 'money'},
                {key: 'detailFacInfo', label: '세부시설물정보', width: 100, align: 'left'}
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

        axboot.buttonClick(this, "data-grid-view-02-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "excel": function () {
                _this.excel("시설물신규등록통보이력-" + getFormattedDate(new Date()) + ".xls");
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
                {key: 'stextGubun', label: '전문구분', width: 100, align: 'center'},
                {key: 'changeBeforeFacGubunCodeName', label: '변경전시설물구분코드', width: 150, align: 'center'},
                {key: 'changeBeforeFacCodeName', label: '변경전시설물구분', width: 150, align: 'center'},
                {key: 'changeAfterFacGubunCodeName', label: '변경후시설물구분코드', width: 150, align: 'center'},
                {key: 'changeAfterFacCode', label: '변경후시설물구분코드', width: 150, align: 'center'},
                {key: 'changeAfterFacName', label: '변경후시설물명', width: 150, align: 'left'},
                {key: 'changeAfterCorpName', label: '변경후업체명', width: 150, align: 'center'},
                {key: 'changeAfterHireFee', label: '변경후임차료', width: 100, align: 'left', formatter: 'money'}
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
                _this.excel("시설물변경통보이력-" + getFormattedDate(new Date()) + ".xls");
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
                {key: 'facSeqNo', label: '시설물일련번호', width: 100, align: 'center'},
                {key: 'changeChasu', label: '변경차수', width: 100, align: 'center'},
                {key: 'stextGubunName', label: '전문구분', width: 100, align: 'center'},
                {key: 'hireFacEnableName', label: '임차시설물여부', width: 100, align: 'center'},
                {key: 'nouseGubunName', label: '철수구분', width: 100, align: 'center'},
                {key: 'assetSeqNo', label: '자산일련번호', width: 100, align: 'center'}
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
                _this.excel("시설물철수통보이력-" + getFormattedDate(new Date()) + ".xls");
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
                {key: 'facSeqNo', label: '시설물일련번호', width: 100, align: 'center', editor: 'text'},
                {key: 'resultStextGubunName', label: '결과전문구분', width: 100, align: 'center', editor: 'text'},
                {key: 'assetSeqNo', label: '자산일련번호', width: 100, align: 'left', editor: 'text'},
                {key: 'adoptDate', label: '도입일', width: 140, align: 'center', editor: 'text'},
                {key: 'workCompleteEnableName', label: '작업완료여부', width: 100, align: 'center', editor: 'text'},
                {key: 'workCompleteDate', label: '작업완료일', width: 140, align: 'center', editor: 'text'},
                {key: 'unusl', label: '특이사항', width: 100, align: 'left', editor: 'text'},
                {
                    key: 'stextSendGubun', label: '전송여부', width: 100, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["STEXT_SEND_GUBUN"].map[this.value];
                    }
                },
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
                _this.excel("시설물설치/철수결과전송이력-" + getFormattedDate(new Date()) + ".xls");
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
fnObj.gridView06 = axboot.viewExtend(axboot.gridView, {
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
            target: $('[data-ax5grid="grid-view-06"]'),
            columns: [
                {key: 'txId', label: '처리일시', width: 130, align: 'center'},
                {key: 'changeBeforeAssetSeqNo', label: '변경전자산일련번호', width: 150, align: 'center', editor: 'text'},
                {key: 'changeAfterAssetSeqNo', label: '변경후자산일련번호', width: 150, align: 'center', editor: 'text'},
                {key: 'changeDate', label: '변경일자', width: 80, align: 'center', editor: 'text'},
                {key: 'changeTime', label: '변경시간', width: 80, align: 'center', editor: 'text'},
                {
                    key: 'stextSendGubun', label: '전송여부', width: 100, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["STEXT_SEND_GUBUN"].map[this.value];
                    }
                },
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, {selectedClear: true});
                    ACTIONS.dispatch(ACTIONS.ITEM_CLICK3, this.item);
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({
                    pageNumber: pageNumber
                });
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });

        axboot.buttonClick(this, "data-grid-view-06-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "excel": function () {
                _this.excel("시설물일련번호변경 전송이력-" + getFormattedDate(new Date()) + ".xls");
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

        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm2").attr('readonly', true);
            $("#jisaCodeForm2").attr('disabled', true);
            $("#jisaCodeForm2").val(sessionJson.jisaCode);
        }
        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        axboot.buttonClick(this, "data-form-view-02-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR2);
            },
            "form-save": function () {
                ACTIONS.dispatch(ACTIONS.FORM_SAVE2);
            },
            "form_modal": function () {
                ACTIONS.dispatch(ACTIONS.FORM_MODAL_OPEN2)
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

        if (this.get("facSeqNo").length != 2) {
            formError('\n' + '시설물 일련번호는 2자리입니다.');
            return false;
        }

        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm2").attr('readonly', true);
            $("#jisaCodeForm2").attr('disabled', true);
            $("#jisaCodeForm2").val(sessionJson.jisaCode);
        }
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

        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm3").attr('readonly', true);
            $("#jisaCodeForm3").attr('disabled', true);
            $("#jisaCodeForm3").val(sessionJson.jisaCode);
        }
        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        axboot.buttonClick(this, "data-form-view-03-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR3);
            },
            "form-save": function () {
                ACTIONS.dispatch(ACTIONS.FORM_SAVE3);
            },
            "form_modal": function () {
                ACTIONS.dispatch(ACTIONS.FORM_MODAL_OPEN3)
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

        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm3").attr('readonly', true);
            $("#jisaCodeForm3").attr('disabled', true);
            $("#jisaCodeForm3").val(sessionJson.jisaCode);
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