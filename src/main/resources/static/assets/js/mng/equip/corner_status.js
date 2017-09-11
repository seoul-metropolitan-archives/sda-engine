var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            //url: ["samples", "parent"],
            url: "/api/v1//mng/equip/corner_status",
            data: $.extend({}, this.searchView.getData(), this.gridView01.getPageData()),
            callback: function (res) {
                console.log("test:", res);
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
                    url: "/api/v1//mng/equip/corner_status",
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
        if (caller.formView01.validate()) {
            var parentData = caller.formView01.getData();
            console.log("form2save", parentData);
            axboot
                .call({
                    type: "PUT",
                    url: "/api/v1//mng/equip/corner_status",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
                    }
                })
                .done(function () {

                    caller.formView01.clear();
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
    ITEM_CLICK: function (caller, act, data) {
        caller.formView01.setData(data);
        fnObj.gridView02.setPageData({pageNumber: 0});
        fnObj.gridView03.setPageData({pageNumber: 0});
        fnObj.gridView04.setPageData({pageNumber: 0});
        fnObj.gridView05.setPageData({pageNumber: 0});
        fnObj.gridView06.setPageData({pageNumber: 0});
        axboot
            .call({
                type: "GET",
                url: "/api/v1//mng/equip/sh02001110",
                data: $.extend({}, {
                    jisaCode: data.jisaCode,
                    branchCode: data.branchCode,
                    cornerCode: data.cornerCode,
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
                    url: "/api/v1//mng/equip/sh02001200",
                    data: $.extend({}, {
                        jisaCode: data.jisaCode,
                        branchCode: data.branchCode,
                        cornerCode: data.cornerCode,
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
                    url: "/api/v1//mng/equip/sh02001120",
                    data: $.extend({}, {
                        closingBranchNo: data.branchCode,
                        closingCornerCode: data.cornerCode,
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
                    url: "/api/v1//mng/equip/sh02001270",
                    data: $.extend({}, {
                        jisaCode: data.jisaCode,
                        branchCode: data.branchCode,
                        cornerCode: data.cornerCode,
                    }, this.gridView05.getPageData()),
                    callback: function (res) {
                        caller.gridView05.setData(res);
                    },
                    options: {
                        onError: viewError
                    }
                }
            )
            .call({
                type: "GET",
                url: "/api/v1//mng/equip/facility_status",
                data: $.extend({}, {
                    jisaCode: data.jisaCode,
                    branchCode: data.branchCode,
                    cornerCode: data.cornerCode,
                }, this.gridView06.getPageData()),
                callback: function (res) {
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
        caller.formView02.setData(data);
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
    EXCEL_DOWNLOAD: function (caller, act, data) {
        var params = buildParams($.extend({}, this.searchView.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/corner_status/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD2: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001110/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD3: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001200/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD4: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001120/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD5: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/sh02001270/download?" + params;
        return false;
    },
    EXCEL_DOWNLOAD6: function (caller, act, data) {
        var params = buildParams($.extend({}, this.formView01.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/facility_status/download?" + params;
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
    _this.gridView02.initView();
    _this.gridView03.initView();
    _this.gridView04.initView();
    _this.gridView05.initView();
    _this.gridView06.initView();

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
                if(sessionJson.userCd == "system" || sessionJson.jisaCode == "00") {
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

        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
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
                {key: 'jisaCodeName', label: '지사코드', width: 70, align: 'center'},
                {key: 'branchCode', label: '지점코드', width: 80, align: 'center'},
                {key: 'branchName', label: '지점명', width: 100, align: 'left'},
                {key: 'cornerCode', label: '코너코드', width: 70, align: 'center'},
                {key: 'cornerName', label: '코너명', width: 100, align: 'left'},
                {key: 'placeGubunName', label: '장소구분', width: 70, align: 'center'},
                {key: 'branchGubunName', label: '점포구분', width: 80, align: 'center'},
                // {key: 'specialStyleGubunName', label: '특수형구분', width: 80, align: 'center'},
                // {key: 'operTimeGubunName', label: '운영시간구분', width: 100, align: 'center'},
                {key: 'operStartTime', label: '운영시작시간', width: 80, align: 'center'},
                {key: 'operEndTime', label: '운영종료시간', width: 80, align: 'center'},
                // {key: 'checkOperEnableName', label: '수표운영여부', width: 100, align: 'center'},
                // {key: 'seviceFee', label: '용역료', width: 130, align: 'right', formatter: 'money'},
                {key: 'securityCorpCodeName', label: '경비사코드', width: 80, align: 'center'},
                {key: 'facHireEnableName', label: '시설물임차여부', width: 100, align: 'center'},
                // {key: 'addr', label: '주소', width: 200, align: 'left'},
                // {key: 'unusl', label: '특이사항', width: 200, align: 'left'},
                // {key: 'operDay', label: '운영요일', width: 90, align: 'center'}
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
                _this.excel("코너현황-"+getFormattedDate(new Date())+".xls");

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
            $('#jisaCodeForm').attr('readonly', true);
            $('#jisaCodeForm').attr('disabled', true);
            $('#jisaCodeForm').val(sessionJson.jisaCode);
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
    get: function(dataPath) {
        return this.model.get(dataPath);
    },
    setData: function (data) {

        console.log("form2", data);
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
            $('#jisaCodeForm').attr('readonly', true);
            $('#jisaCodeForm').attr('disabled', true);
            $('#jisaCodeForm').val(sessionJson.jisaCode);
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
                {key: 'changeChasu', label: '변경차수', width: 70, align: 'center'},
                {key: 'stextGubunName', label: '전문구분', width: 70, align: 'center'},
                {key: 'placeGubunName', label: '장소구분', width: 70, align: 'center'},
                {key: 'branchGubunName', label: '지점구분', width: 70, align: 'center'},
                {key: 'specialStyleGubunName', label: '특수형구분', width: 70, align: 'center'},
                {key: 'operTimeGubunName', label: '운영시간구분', width: 100, align: 'center'},
                {key: 'operStartTime', label: '운영시작시간', width: 130, align: 'center'},
                {key: 'operEndTime', label: '운영종료시간', width: 130, align: 'center'},
                {key: 'checkOperEnableName', label: '수표운영여부', width: 100, align: 'center'},
                {key: 'serviceFee', label: '용역료', width: 150, align: 'right', formatter: 'money'},
                {key: 'securityCorpCodeName', label: '경비사코드', width: 80, align: 'center'},
                {key: 'facHireEnableName', label: '시설물임차여부', width: 100, align: 'center'},
                {key: 'addr', label: '주소', width: 100, align: 'left'},
                {key: 'unusl', label: '특이사항', width: 100, align: 'left'},
                {key: 'operDay', label: '운영요일', width: 80, align: 'center'}
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex);
                    ACTIONS.dispatch(ACTIONS.ITEM_CLICK2, this.item);
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({
                    pageNumber: pageNumber
                });
            }
        });

        axboot.buttonClick(this, "data-grid-view-02-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "excel": function () {
                _this.excel("코너신규등록통보이력-"+getFormattedDate(new Date())+".xls");
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
    setData: function (_data) {
        this.target.setData(_data);
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
                {key: 'stextNoticeDatetime', label: '전문통보일시', width: 150, align: 'center'},
                {key: 'stextGubun', label: '전문구분', width: 100, align: 'center'},
                {key: 'placeGubun', label: '장소구분', width: 100, align: 'center'},
                {key: 'branchGubun', label: '점포구분', width: 100, align: 'center'},
                {key: 'operStartGubun', label: '운영시간구분', width: 100, align: 'center'},
                {key: 'operStartTime', label: '운영시작시간', width: 100, align: 'center'},
                {key: 'operEndTime', label: '운영종료시간', width: 100, align: 'center'},
                {key: 'securityCorpCode', label: '경비사코드', width: 100, align: 'center'},
                {key: 'checkOperEnable', label: '수표운영여부', width: 100, align: 'center'},
                {key: 'operDay', label: '운영요일', width: 100, align: 'center'},
                {key: 'addr', label: '주소', width: 100, align: 'left'},
                {key: 'installPlace', label: '설치장소', width: 100, align: 'left'},
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
            }
        });

        axboot.buttonClick(this, "data-grid-view-03-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "excel": function () {
                _this.excel("코너정보변경통보이력-"+getFormattedDate(new Date())+".xls");
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.stextNoticeDatetime;
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
                {key: 'workSeqNo', label: '작업일련번호', width: 100, align: 'left'},
                {key: 'changeChasu', label: '변경차수', width: 100, align: 'left'},
                {key: 'stextGubun', label: '전문구분', width: 100, align: 'left'},
                {key: 'closingBranchNo', label: '폐쇄지점', width: 100, align: 'left'},
                {key: 'closingCornerCode', label: '폐쇄코너코드', width: 100, align: 'left'},
                {key: 'addr', label: '주소', width: 100, align: 'left'},
                {key: 'unusl', label: '특이사항', width: 100, align: 'left'}
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
            }
        });

        axboot.buttonClick(this, "data-grid-view-04-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "excel": function () {
                _this.excel("코너폐쇄통보이력-"+getFormattedDate(new Date())+".xls");
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
                {key: 'workSeqNo', label: '작업일련번호', width: 100, align: 'left'},
                {key: 'chasu', label: '차수', width: 100, align: 'left'},
                {key: 'stextGubun', label: '전문구분', width: 100, align: 'left'},
                {key: 'closingDate', label: '폐쇄(철수)일자', width: 100, align: 'left'},
                {key: 'restartGubun', label: '재가동구분', width: 100, align: 'left'},
                {key: 'restartDate', label: '재가동일자', width: 100, align: 'left'},
                {key: 'changeItemGubun', label: '변경항목구분', width: 100, align: 'left'},
                {key: 'operStartTime', label: '운영시작시간', width: 100, align: 'left'},
                {key: 'operEndTime', label: '운영종료시간', width: 100, align: 'left'},
                {key: 'checkOper', label: '수표운영', width: 100, align: 'left'},
                {key: 'applyDate', label: '적용일', width: 100, align: 'left'}
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
                _this.excel("코너상태변경통보이력-"+getFormattedDate(new Date())+".xls");
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
            target: $('[data-ax5grid="grid-view-06"]'),
            columns: [
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
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({pageNumber: pageNumber});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });

        axboot.buttonClick(this, "data-grid-view-06-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "delete": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_DEL);
            },
            "excel": function () {
                _this.excel("시설물목록-"+getFormattedDate(new Date())+".xls");
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

var buildParams = function (json) {

    var params = JSON.stringify(json);

    params=params.replace(/{/g, "");
    params=params.replace(/}/g, "");
    params=params.replace(/:/g, "=")
    params=params.replace(/,/g, "&");
    params=params.replace(/"/g, "");

    return params;

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