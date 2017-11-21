/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    // ROLE 정보
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ac007/01/list",
            data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);

                if (res.list.length > 0) {
                    fnObj.formView.setFormData("roleNameHeader", res.list[0].roleName);

                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, res.list[0]);
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2, res.list[0]);
                } else {
                    fnObj.gridView02.clearData();
                    fnObj.gridView03.clearData();
                }

            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    // 롤 메뉴 조회
    PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ac007/02/list",
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
    // 롤 퍼미션 조회
    PAGE_SEARCH2: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ac007/03/list",
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
        var roleMenuList = [].concat(fnObj.gridView02.getData());
        var permissionList = [].concat(fnObj.gridView03.getData());

        if(!fnObj.gridView02.validate()
        || !fnObj.gridView03.validate()
        )
        return ;
        axboot
            .call({
                type: "PUT",
                url: "/api/v1/ac007/02/save",
                data: JSON.stringify(roleMenuList),
                callback: function (res) {
                    fnObj.gridView01.commit();
                }
            })
            .call({
                type: "PUT",
                url: "/api/v1/ac007/03/save",
                data: JSON.stringify(permissionList),
                callback: function (res) {
                    fnObj.gridView02.commit();
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
    SEARCH_ROLE_POPUP: function (caller, act, data) {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            sendData: function () {
                return data;
            },
            callback: function (data) {
                fnObj.formView.setFormData("roleUuid", data["ROLE_UUID"]);
                fnObj.formView.setFormData("roleName", data["ROLE_NAME"]);

                if (this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH, data);
            }
        });
    },
    PASSWORD_CHANGE_POPUP: function (caller, act, data) {
        var promptDialog = new ax5.ui.dialog();

        promptDialog.prompt({
            title: "Password Change",
            msg: 'Please fill new password'
        }, function (data) {
            console.log(this);
            if (this.key == "ok") {
                this.value;
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
        url: "/assets/js/column_info/ac00701.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/ac00702.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/ac00703.js",
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

        axboot.buttonClick(this, "data-form-view-01-btn", {
            "select-all": function () {
                var dataLists = fnObj.gridView02.gridObj.getJsonRows();

                dataLists.forEach(function (menuInfo) {
                    if (menuInfo.allYn == "Y") {
                        menuInfo["useYn"] = "Y";
                        menuInfo["saveYn"] = "Y";
                        menuInfo["inquiryYn"] = "Y";
                    }
                });

                fnObj.gridView02.gridObj.getDataProvider().updateRows(0, dataLists, 0, -1);
                fnObj.gridView02.gridObj.commit(true);
            },
            "deselect-all": function () {
                var dataLists = fnObj.gridView02.gridObj.getJsonRows();

                dataLists.forEach(function (menuInfo) {
                    if (menuInfo.allYn == "N") {
                        menuInfo["useYn"] = "N";
                        menuInfo["saveYn"] = "N";
                        menuInfo["inquiryYn"] = "N";
                    }
                });

                fnObj.gridView02.gridObj.getDataProvider().updateRows(0, dataLists, 0, -1);
                fnObj.gridView02.gridObj.commit(true);
            }
        });
    },
    initEvent: function () {
        var _this = this;

        $("input[data-ax-path='roleName']").parents().eq(1).find("a").click(function () {
            if ("" != $("input[data-ax-path='roleName']").val().trim()) {
                var data = {
                    popupCode: "PU109",
                    searchData: $("input[data-ax-path='roleName']").val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_ROLE_POPUP, data);
            }
        });
        $("input[data-ax-path='roleName']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU109",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_ROLE_POPUP,data);
            }
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


// AC007 User Group User GridView
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
        this.setColumnInfo(ac00701.column_info);
        this.gridObj.setRunAdd(false);
        this.gridObj.setRunDel(false);
        this.makeGrid();

        this.gridObj.itemClick(this.itemClick);
    },
    itemClick: function (data, index) {
        /* if (index.fieldIndex == 3) {
             // 비밀번호 Cell 선택시
             ACTIONS.dispatch(ACTIONS.PASSWORD_CHANGE_POPUP, data);
         }*/

        if (data.roleUuid != null && data.roleUuid != "") {
            if (fnObj.gridView02.isChangeData() == true || fnObj.gridView03.isChangeData() == true) {
                axDialog.confirm({
                    msg: axboot.getCommonMessage("AA006")
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    } else {
                        fnObj.formView.setFormData("roleNameHeader", data.roleName);

                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2, data);
                    }
                });
            } else {
                fnObj.formView.setFormData("roleNameHeader", data.roleName);

                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2, data);
            }
        }
    }
});

fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 10000
    },
    initView: function () {
        this.gridObj = new TreeGridWrapper("realgrid02", "/assets/js/libs/realgrid", true);
        this.gridObj.setGridStyle("100%", "100%").setOption({
            footer: {visible: false},
            header: {visible: true},
            checkBar: {visible: false},
            indicator: {visible: true},
            stateBar: {visible: false}
        });
        this.gridObj.setRunAdd(false);
        this.gridObj.setRunDel(false);
        this.gridObj.setStyle("header",{background: "linear,#f2f2f2",textAlignment: "near",fontFamily: "nanum",fontSize : 12,borderRight: "#cccccc, 1",});
        this.gridObj.setStyle("body",{background : "#ffffffff",fontSize : 12,fontFamily : "nanum"});
        this.gridObj.setStyle("grid",{border: "#ffffffff,0"});
        this.gridObj.setStyle("indicator",{background: "linear,#f2f2f2",fontFamily: "nanum"});
        this.gridObj.setColumnInfo(ac00702.column_info).setEntityName("Menu").makeGrid();
        this.gridObj.setFixedOptions({
            colCount: 1
        });
        this.gridObj.setDisplayOptions({
            fitStyle: "none"
        });
        this.gridObj.onDataCellClicked(function (grid, index) {
            grid.commit(true);

            if (index.fieldName == "allYn") {
                var menuInfo = grid.getDataProvider().getJsonRow(index.dataRow);
                // Y 에서 N 으로 바꾸면 다른 값도 모두 N으로 세팅
                if (menuInfo.allYn == "Y") {
                    menuInfo["useYn"] = "N";
                    menuInfo["saveYn"] = "N";
                    menuInfo["inquiryYn"] = "N";
                } else {
                    menuInfo["useYn"] = "Y";
                    menuInfo["saveYn"] = "Y";
                    menuInfo["inquiryYn"] = "Y";
                }
                grid.getDataProvider().updateRow(index.dataRow, menuInfo);
                grid.commit(true);
            }
            //var menu = _this.menus[grid.getDataProvider().getJsonRow(index.dataRow)["menuUuid"]];
            //if (menu["program"]) {
            /* ACTIONS.dispatch(ACTIONS.MENU_OPEN, $.extend({}, menu["program"], {
                 menuId: menu["menuId"],
                 menuNm: menu["menuNm"]
             }));*/
            //}
        });

        //this.gridObj.itemClick(this.itemClick);
    },
    setData: function (list) {
        //this.gridObj.gridView.getDataProvider().setRows(list, "menuCode");
        this.gridObj.setTreeDataForArray(list, "menuCode");
        this.gridObj.expandAll();
    },
    getData: function () {
        return this.gridObj.getData();
    },
    isChangeData: function () {
        return false;
    }
});


// AC007 GridView
fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 10000
    },
    tagId: "realgrid03",
    entityName: "Permission",
    primaryKey: "rolePermissionUuid",
    parentsUuidFieldName: "roleUuid",
    parentsGrid: fnObj.gridView01,
    initView: function () {
        this.initInstance();
        this.gridObj.setFixedOptions({
            colCount: 1
        });
        this.gridObj.setRunAdd(false);
        this.gridObj.setRunDel(false);
        this.setColumnInfo(ac00703.column_info);
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
    if (fnObj.gridView01.isChangeData() == true || fnObj.gridView02.isChangeData() == true || fnObj.gridView03.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}
