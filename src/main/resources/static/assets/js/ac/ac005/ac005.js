/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    // Role 조회
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ac005/01/list",
            data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);

                if (res.list.length > 0) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, res.list[0]);
                }

            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    // Role Permission 조회
    PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ac005/02/list",
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
        var roleList = [].concat(fnObj.gridView01.getData());
        var rolePermissionList = [].concat(fnObj.gridView02.getData());

        axboot
            .call({
                type: "PUT",
                url: "/api/v1/ac005/01/save",
                data: JSON.stringify(roleList),
                callback: function (res) {
                }
            })
            .call({
                type: "PUT",
                url: "/api/v1/ac005/02/save",
                data: JSON.stringify(rolePermissionList),
                callback: function (res) {
                }
            })
            .done(function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
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
        url: "/assets/js/column_info/ac00501.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/ac00502.js",
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

    // Layout Resize Event
    $('[data-ax5layout]').ax5layout({
        splitter: {
            size: 6
        },
        autoResize: false,
        onResize: function onResize() {
            fnObj.gridView01.gridObj.getGridView().resetSize();
            fnObj.gridView02.gridObj.getGridView().resetSize();
        },
    });
};

/*fnObj.pageResize = function () {
    //
};*/

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
    primaryKey: "roleUuid",
    entityName: "Role",
    initView: function () {
        this.initInstance();
        this.gridObj.setFixedOptions({
            colCount: 1
        });
        this.setColumnInfo(ac00501.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    itemClick: function (data, index) {
        if (data.roleUuid != null && data.roleUuid != "") {
            if (fnObj.gridView02.isChangeData() == true) {
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
            }
        }else{
            fnObj.gridView02.clearData();
        }
    }
});
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 10000
    },
    tagId: "realgrid02",
    entityName: "Permission",
    primaryKey: "rolePermissionUuid",
    parentsUuidFieldName: "roleUuid",
    parentsGrid: fnObj.gridView01,
    initView: function () {
        this.initInstance();
        this.gridObj.setFixedOptions({
            colCount: 1
        });
        this.setColumnInfo(ac00502.column_info);
        this.makeGrid();
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
