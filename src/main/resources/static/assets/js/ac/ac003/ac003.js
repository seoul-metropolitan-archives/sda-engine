var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    // User 정보 조회
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ac003/01/list",
            data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    // User 그룹정보 조회
    PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ac003/02/list",
            data: $.extend({}, {pageSize: 1000}, data),
            callback: function (res) {
                fnObj.gridView02.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    // 퍼미션 조회
    PAGE_SEARCH2: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ac003/03/list",
            data: $.extend({}, {pageSize: 1000}, data),
            callback: function (res) {
                fnObj.gridView03.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        axDialog.confirm({
            msg: "Do you want to save all items?"
        }, function () {
            if (this.key == "ok") {
                var updateList;

                var saveList = [].concat(fnObj.gridView01.getData()); // 추가되어서 수정된건
                /*saveList.forEach(function (n) {
                    n.isDeleted = false;
                });

                var deleteList = [].concat(fnObj.gridView01.getData("deleted")); // 삭제된건

                deleteList.forEach(function (n) {
                    n.isDeleted = true;
                });*/

               /* if (saveList.length > 0) {
                    updateList = saveList;
                } else if (deleteList.length > 0) {
                    updateList = deleteList;
                }*/

                axboot
                    .call({
                        type: "PUT",
                        url: "/api/v1/ac003/01/save",
                        data: JSON.stringify(saveList),
                        callback: function (res) {
                            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                        }
                    })
                    .done(function () {
                        axToast.push("저장 작업이 완료되었습니다.");
                    });
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
    dispatch: function (caller, act, data) {
        var result = ACTIONS.exec(caller, act, data);
        if (result != "error") {
            return result;
        } else {
            return false;
        }
    }
});


// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    //TODO 추후에 삭제될 내용으로 /실제 Grid의 컬럼 정보는 DB에서 가져올 예정
    $.ajax({
        url: "/assets/js/column_info/ac00301.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/ac00302.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/ac00303.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();
    _this.gridView03.initView();

    // Data 조회
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.formView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {useYn: "Y"});
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


// AC003 User Group User GridView
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        this.gridObj = new GridWrapper("realgrid01", "/assets/js/libs/realgrid");
        this.gridObj.setGridStyle("100%", "100%");
        this.gridObj.setColumnInfo(ac00301.column_info).setEntityName("CONFIGURATION");
        this.gridObj.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    setData: function (list) {
        this.gridObj.setData("set", list);

    },
    getData: function () {
        return this.gridObj.getData();
    },
    addRow: function () {
        this.gridObj.addRow();
    },
    itemClick: function (data) {
        if (data.userUuid != null && data.userUuid != "") {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2, data);
        }
    }
});
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        this.gridObj = new GridWrapper("realgrid02", "/assets/js/libs/realgrid");
        this.gridObj.setGridStyle("100%", "100%");
        this.gridObj.setColumnInfo(ac00302.column_info).setEntityName("CONFIGURATION");
        this.gridObj.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    setData: function (list) {
        this.gridObj.setData("set", list);

    },
    getData: function (_type) {
        //this.gridObj.load("/ad/ad001/getEnviromentList.do", {});
    },
    addRow: function () {
        this.gridObj.addRow();
    },
    itemClick: function (data) {
        //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
    }
});


// AC003 GridView
fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        this.gridObj = new GridWrapper("realgrid03", "/assets/js/libs/realgrid");
        this.gridObj.setGridStyle("100%", "100%");
        this.gridObj.setColumnInfo(ac00303.column_info).setEntityName("CONFIGURATION");
        this.gridObj.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    setData: function (list) {
        this.gridObj.setData("set", list);

    },
    getData: function (_type) {
        //this.gridObj.load("/ad/ad001/getEnviromentList.do", {});
    },
    addRow: function () {
        this.gridObj.addRow();
    },
    itemClick: function (data) {
        //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
    }
});

