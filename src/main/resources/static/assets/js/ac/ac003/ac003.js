var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        console.log(axboot);

        axboot.ajax({
            type: "GET",
            url: "/api/v1/ac003/01/list",
            data: $.extend({}, {pageSize: 1000}),
            callback: function (res) {
                console.log(res.list);
                fnObj.gridView.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
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

    $.ajax({
        url: "/assets/js/column_info/ac003.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.gridView.initView();

    // Data 조회
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

// AC003 GridView
fnObj.gridView = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        this.gridObj = new GridWrapper("realgrid", "/assets/js/libs/realgrid");
        this.gridObj.setGridStyle("100%", "100%");
        this.gridObj.setColumnInfo(ac003.column_info).setEntityName("CONFIGURATION");
        this.gridObj.makeGrid();
        //this.getData();
        this.gridObj.getGridView().onDataCellClicked = this.itemClick;
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
    itemClick: function (grid, index) {
        console.log(grid.getDataProvider().getJsonRow(index.dataRow));
    }
});

