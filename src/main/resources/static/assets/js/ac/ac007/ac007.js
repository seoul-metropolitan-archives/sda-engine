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
        var userList = [].concat(fnObj.gridView01.getData());
        var groupList = [].concat(fnObj.gridView02.getData());
        var roleList = [].concat(fnObj.gridView03.getData());

        axboot
            .call({
                type: "PUT",
                url: "/api/v1/ac007/01/save",
                data: JSON.stringify(userList),
                callback: function (res) {
                }
            })
            .call({
                type: "PUT",
                url: "/api/v1/ac007/02/save",
                data: JSON.stringify(groupList),
                callback: function (res) {
                }
            })
            .call({
                type: "PUT",
                url: "/api/v1/ac007/03/save",
                data: JSON.stringify(roleList),
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


// AC007 User Group User GridView
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        this.gridObj = new GridWrapper("realgrid01", "/assets/js/libs/realgrid");
        this.gridObj.setGridStyle("100%", "100%");
        this.gridObj.setColumnInfo(ac00701.column_info).setEntityName("CONFIGURATION");
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
        pageSize: 20
    },
    initView: function () {
        this.gridObj = new GridWrapper("realgrid02", "/assets/js/libs/realgrid", true);
        this.gridObj.setIsTree(true).setGridStyle("100%", "100%")
        this.gridObj.setColumnInfo(ac00702.column_info).setEntityName("Menu");
        this.gridObj.makeGrid();
        this.gridObj.setOption({
            checkBar: {visible: true},
            indicator: {visible: true}
        });
        this.gridObj.getGridView().setFooter({
            resizable: false,
            visible: false,
        });

        this.gridObj.onDataCellClicked(function (grid, index) {
            if (index.fieldIndex == 8 && index.fieldName == "allYn") {
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

        this.gridObj.getGridView()._dataSource.setRows(list, "menuCode");
        this.gridObj.expandAll();
    },
    isChangeData: function () {
        return false;
    }
});


// AC007 GridView
fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        this.gridObj = new GridWrapper("realgrid03", "/assets/js/libs/realgrid");
        this.gridObj.setGridStyle("100%", "100%");
        this.gridObj.setFixedOptions({
            colCount: 1
        });
        this.gridObj.setColumnInfo(ac00703.column_info).setEntityName("CONFIGURATION");
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
    itemClick: function (data, index) {
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

makeTreeData = function (menuJsonData) {
    var menuItems = convertJsonObj(menuJsonData);

    this.menus = {};
    var leftMenuItems =
        {
            menus: new Array()
        };
    var menu_2_list = new Array();
    var secondList = undefined;
    for (var i = 0; i < menuItems.length; i++) {
        this.menus[menuItems[i]["menuUuid"]] = menuItems[i];
        menu_2_list = menuItems[i]["children"];

        secondList = new Array();
        for (var j = 0; j < menu_2_list.length; j++) {
            this.menus[menu_2_list[j]["menuUuid"]] = menu_2_list[j];
            secondList.push({
                icon: 0
                , menuUuid: menu_2_list[j]["menuUuid"]
                , menuName: menu_2_list[j]["menuName"]
                , program: menu_2_list[j]["program"]
            });
        }
        leftMenuItems.menus.push({
            icon: 0
            , menuUuid: menuItems[i]["menuUuid"]
            , menuName: menuItems[i]["menuName"]
            , menus: secondList
        });
        secondList = undefined;
    }

    return leftMenuItems;
}

function convertJsonObj(jsonStr) {
    return jsonStr == null || jsonStr == '' ? null : JSON.parse(jsonStr.replace(/&quot;/gi, '"'));
}