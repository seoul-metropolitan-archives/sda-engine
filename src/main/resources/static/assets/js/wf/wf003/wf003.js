/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    // JOB 조회
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/wf003/01/list",
            data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);

                if (res.list.length > 0) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, res.list[0]);
                } else {
                    fnObj.gridView02.clearData();
                }

            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    // Parameter 조회
    PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/wf003/02/list",
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
    PAGE_SAVE: function (caller, act, data) {
        var workflowist = [].concat(fnObj.gridView01.getData());
        var workflowJobList = [].concat(fnObj.gridView02.getData());

        axboot
            .call({
                type: "PUT",
                url: "/api/v1/wf003/01/save",
                data: JSON.stringify(workflowist),
                callback: function (res) {
                    fnObj.gridView01.gridObj.commit();
                }
            })
            .call({
                type: "PUT",
                url: "/api/v1/wf003/02/save",
                data: JSON.stringify(workflowJobList),
                callback: function (res) {
                    fnObj.gridView02.gridObj.commit();
                }
            })
            .done(function () {
                //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push(axboot.getCommonMessage("AA007"));
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
        //ACTIONS.dispatch(ACTIONS.PROCESS_RUN);

        axboot.modal.open({
            modalType: "WORKFLOW_POPUP_01",
            param: "",
            sendData: function () {
                return {
                    "workflowUuid": data.workflowUuid
                };
            },
            callback: function (data) {
                //$("#calleeEmpName").val(data.empName);
                //$("#calleeEmpTelno").val(data.empPhoneNo);
                //TODO 개발하고 지워야함
                //axWarningToast.push("팝업이 닫힙니다.");
                //ACTIONS.dispatch(ACTIONS.PROCESS_RUN);

                this.close();
            }
        });
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    PROCESS_RUN: function (caller, act, data) {
        var workflowData = {
            workflowUuid: "5AE01C7A-3393-4651-96C8-D9CCFD0AC566",
            workflowName: "Starndard Ingest",
            serviceUuid: "DF737BDE-42C1-48C4-85A2-07B505EEA911",
            menuUuid: axboot.getMenuId(),
            useYn: "Y",
            workflowJobList: [{
                workflowUuid: "5AE01C7A-3393-4651-96C8-D9CCFD0AC566",
                jobUuid: "5133F666-2AD6-44D7-8CA2-F3C5E59FDBC9",
                skipYn: "Y",
                terminateYn: "Y",
                parameterList: [{
                    parameterUuid: "81E58A14-CF3E-4C38-9B0C-4547391B17EE",
                    defaultValue: "Test123"
                }, {
                    parameterUuid: "81E58A14-CF3E-4C38-9B0C-4547391B17E1",
                    defaultValue: "Test125"
                }]
            }]
        }

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/wf003/01/run",
            dataType: "json",
            data: JSON.stringify(workflowData),
            callback: function (res) {
                axWarningToast.push("호출 완료");
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PROCESS_STOP: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/wf003/01/stop",
            async: true,
            data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
            callback: function (res) {
                axWarningToast.push("호출 완료");
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
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
        url: "/assets/js/column_info/wf00301.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/wf00302.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();

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


// AC005 User Group User GridView
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 10000
    },
    tagId: "realgrid01",
    primaryKey: "workflowUuid",
    entityName: "Workflow",
    initView: function () {
        this.initInstance();
        this.gridObj.setFixedOptions({
            colCount: 1
        });
        this.setColumnInfo(wf00301.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    itemClick: function (data, index) {
        if (data.workflowUuid != null && data.workflowUuid != "") {

            if (index.fieldIndex == 5 && index.fieldName == "Run") {
                axDialog.confirm({
                    msg: axboot.getCommonMessage("WF003_02")
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.PROCESS_RUN);
                    }
                });
            } else {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
            }
        } else {
            fnObj.gridView02.clearData();
            fnObj.gridView03.clearData();
        }
    }
});
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 10000
    },
    tagId: "realgrid02",
    entityName: "Job",
    primaryKey: "workflow_job_Uuid",
    parentsUuidFieldName: "workflowUuid",
    parentsGrid: fnObj.gridView01,
    initView: function () {
        this.initInstance();
        this.gridObj.setFixedOptions({
            colCount: 1
        });
        this.setColumnInfo(wf00302.column_info);
        this.gridObj.itemClick(this.itemClick);
        this.makeGrid();
    },
    itemClick: function (data, index) {
        if (data.jobUuid != null && data.jobUuid != "") {
            fnObj.gridView03.initGrid(data.jobUuid);
        } else {
            fnObj.gridView03.clearData();
        }
    }
});

fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid03",
    entityName: "",
    initView: function () {
    },
    initGrid: function (jobUuid) {
        var _this = this;
        axboot.ajax({
            url: "/api/v1/wf003/p/02/getPopupInfo",
            async: false,
            data: {"jobUuid": jobUuid},
            callback: function (res) {
                res = eval(res.map);

                if (res.columnInfo.length > 0) {

                    fnObj.gridView03.gridObj = new SimpleGridWrapper(fnObj.gridView03.tagId, "/assets/js/libs/realgrid");
                    fnObj.gridView03.gridObj.setGridStyle("100%", "100%");
                    fnObj.gridView03.gridObj.setEntityName(fnObj.gridView03.entityName);
                    fnObj.gridView03.gridObj.setColumnInfo(res.columnInfo);
                    fnObj.gridView03.gridObj.makeGrid();
                    fnObj.gridView03.gridObj.addRow();

                    fnObj.gridView03.gridObj.gridView.resetSize();
                } else {
                    $("#realgrid03").empty();
                }
            }
        });
    },
    initEvent: function () {
        /*fnObj.gridView01.gridObj.onKeydown(function (grid, key, ctrl, shift, alt) {
            switch (key) {
                case 13:
                    ACTIONS.dispatch(ACTIONS.PAGE_CHOICE);
                    ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
                    break;
            }
        });*/
    },
    setData: function (list) {
        this.gridObj.setData("set", list);

        $("#popupGrid01").fadeIn(100);
    },
    getData: function () {
        return this.gridObj.getJsonRows();
    }
});

/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}
 */
isDataChanged = function () {
    if (fnObj.gridView01.isChangeData() == true || fnObj.gridView02.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}
