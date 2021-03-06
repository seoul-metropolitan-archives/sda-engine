/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    // JOB 조회
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/wf003/02/list",
            data: $.extend({}, {pageSize: 1000}, parent.axboot.modal.getData(), this.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);

                /*if (res.list.length > 0) {
                    //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, res.list[0]);
                    fnObj.gridView02.initGrid(res.list[0]["jobUuid"]);
                }*/
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        var jobList = [].concat(fnObj.gridView01.getData());
        var parameterList = [].concat(fnObj.gridView02.getData());

        axboot
            .call({
                type: "PUT",
                url: "/api/v1/wf003/p/01/save",
                data: JSON.stringify(jobList),
                callback: function (res) {
                    fnObj.gridView01.gridObj.commit();
                }
            })
            .call({
                type: "PUT",
                url: "/api/v1/wf003/p/02/save",
                data: JSON.stringify(parameterList),
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
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            param: "",
            sendData: function () {
                return {
                    //jisaCode: fnObj.formView02.getData().jisaCode
                };
            },
            callback: function (data) {
                //$("#calleeEmpName").val(data.empName);
                //$("#calleeEmpTelno").val(data.empPhoneNo);

                this.close();
            }
        });
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.close();
        }
    },
    PAGE_CHOICE: function (caller, act, data) {
        var list = caller.gridView02.getData();

        if (parent && parent.axboot && parent.axboot.modal) {
            parent.axboot.modal.callback(list); // 부모창에 callback 호출
        }
        /*else {
            alert("선택된 목록이 없습니다.");
        }*/
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
    this.parentsData = parent.axboot.modal.getData();
    //$("#searchField").val(parentsData["searchData"]);

    //TODO 추후에 삭제될 내용으로 /실제 Grid의 컬럼 정보는 DB에서 가져올 예정
    $.ajax({
        url: "/assets/js/column_info/wf00301_p01_01.js",
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
        return $.extend({}, axboot.formView.defaultData, {useYn: ""});
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

        $("#okPopup").click(function () {
            ACTIONS.dispatch(ACTIONS.PAGE_CHOICE);
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
        });
        $("#closePopup").click(function () {
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
        });
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
    primaryKey: "jobUuid",
    entityName: "Job",
    initView: function () {
        this.initInstance();
        this.gridObj.setFixedOptions({
            colCount: 1
        });
        this.setColumnInfo(wf00301_p01_01.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    itemClick: function (data, index) {
        if (data.jobUuid != null && data.jobUuid != "") {
            fnObj.gridView02.initGrid(data["jobUuid"]);

            /*if (fnObj.gridView02.isChangeData() == true) {
                axDialog.confirm({
                    msg: axboot.getCommonMessage("AA006")
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    } else {
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
                    }
                });
            } else {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
            }*/
        }
    }
});


fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid02",
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

                if(res.columnInfo.length <1)
                    return ;

                fnObj.gridView02.gridObj = new SimpleGridWrapper(fnObj.gridView02.tagId, "/assets/js/libs/realgrid");
                fnObj.gridView02.gridObj.setGridStyle("100%", "100%");
                fnObj.gridView02.gridObj.setColumnInfo(res.columnInfo);
                fnObj.gridView02.gridObj.makeGrid();
                fnObj.gridView02.gridObj.addRow();
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
