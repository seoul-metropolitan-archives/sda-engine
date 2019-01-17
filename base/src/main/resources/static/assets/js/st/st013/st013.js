var fnObj = {};
var inoutExceptUuid = "";
var requestorUuid;
var repositoryUuid;
var shelfUuid;
var locationUuid;
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
    },
    PAGE_SEARCH01: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st013/01/list01",
            data: $.extend({}, {pageSize: 1000}, this.formView.getData(),
                {repositoryUuid: repositoryUuid, shelfUuid: shelfUuid, locationUuid: locationUuid, requestorUuid : requestorUuid}
            ),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
                //fnObj.gridView01.disabledColumn();
                fnObj.gridView02.clearData();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH02: function (caller, act, data) {
        if (fnObj.gridView01.getSelectedData() == null) {
            return;
        }
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st013/01/list02",
            data: $.extend({pageSize: 1000}, this.formView.getData(), {inoutExceptUuid: fnObj.gridView01.getSelectedData().inoutExceptUuid,},
                {repositoryUuid: repositoryUuid, shelfUuid: shelfUuid, locationUuid: locationUuid, requestorUuid : requestorUuid}
            ),
            callback: function (res) {
                fnObj.gridView02.setData(res.list);
                // fnObj.gridView02.disabledColumn();
                //fnObj.gridView03.clearData();
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    STATUS_UPDATE01: function (caller, act, data) {

        /*if (fnObj.gridView01.isChangeData() == true) {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA006")
            }, function () {
                if (this.key == "ok") {
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    return;
                }
            });
        }else{
            var rows = fnObj.gridView01.gridObj.getCheckedList();
            if (!rows || rows.length < 1) return;
            var params = rows.filter(function (item) {
                item.changeStatus = data;
                return item.repositoryUuid != "";
            });

           axboot.ajax({
                type: "PUT",
                url: "/api/v1/st/st001/02/confirm01",
                data: JSON.stringify(params),
                callback: function (res) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
                },
                options: {
                    onError: axboot.viewError
                }
            });
        }*/
    },
    STATUS_UPDATE02: function (caller, act, data) {
        /*var rows = fnObj.gridView02.gridObj.getCheckedList();

        if (!rows || rows.length < 1) return;

        var params = rows.filter(function (item) {
            item.changeStatus = data;
            return item.shelfUuid != "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/st/st001/02/confirm02",
            data: JSON.stringify(params),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
            },
            options: {
                onError: axboot.viewError
            }
        });*/
    },
    PAGE_CONFIRM: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE, CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE, CANCEL_STATUS);
    },
    PAGE_SAVE: function (caller, act, data) {

        if (!this.gridView01.gridObj.validate()) {
            return false;
        } else {
            //여긴 신규다
            if (fnObj.gridView01.gridObj.isDataChanged()) {

                var exceptReason = $('textarea[data-ax-path="except_reason"]').val()


                fnObj.gridView01.gridObj.setValue(fnObj.gridView01.getJsonData().length - 1, "exceptReason", exceptReason);

                ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE01);
            } else {
                //데이터가 바뀌지 않았다
                var jsonData = fnObj.gridView01.getJsonData();

                var exceptReason = $('textarea[data-ax-path="except_reason"]').val();
                //null이 아닌경우
                if (exceptReason != "") {
                    //click한애가 있다
                    if (inoutExceptUuid != "") {

                        for (var i = 0; i < jsonData.length; i++) {
                            if (jsonData[i].inoutExceptUuid == inoutExceptUuid) {
                                fnObj.gridView01.gridObj.setValue(i, "exceptReason", exceptReason);
                            } else {
                                fnObj.gridView01.gridObj.setValue(i, "exceptReason", jsonData[i].exceptReason);
                            }
                        }
                    }

                    ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE01);
                }
            }

            if (fnObj.gridView02.gridObj.isDataChanged()) {
                ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE02);
            }
        }
    },
    TOP_GRID_SAVE01: function (caller, act, data) {
        var result = false;
        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st013/01/save01",
            data: JSON.stringify(this.gridView01.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
            }
        })
            .done(function () {
                fnObj.gridView01.commit();
                $('textarea[data-ax-path="except_reason"]').val('');
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    TOP_GRID_SAVE02: function (caller, act, data) {
        var result = false;

        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st013/02/save01",
            data: JSON.stringify(this.gridView02.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
            }
        })
            .done(function () {
                fnObj.gridView02.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    MENU_OPEN: function (caller, act, data) {

    },
    dispatch: function (caller, act, data) {
        var result = ACTIONS.exec(caller, act, data);
        if (result != "error") {
            return result;
        } else {
            return false;
        }
    },
    PAGE_CLASSIFY: function (caller, act, data) {
        if (inoutExceptUuid == "") {
            return
        }

        axboot.modal.open({
            modalType: "ARRANGE_POPUP",
            header: {
                title: "제외 대상 - " + fnObj.gridView01.gridObj.getSelectedData().requestName
            },
            sendData: function () {
                return {
                    inoutExceptUuid: inoutExceptUuid,
                    confirmBtn:"Arrange",
                    flag: "inout",
                    description: fnObj.gridView01.gridObj.getSelectedData().requestName,
                    fromWhere: 'st013',
                };
            },
            callback: function (data) {
                if (this) this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
            }
        });
    },
    SEARCH_CLASS_SCH: function (caller, act, data) {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch: data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='classificationCode']").val(data["CLASSIFICATION_NAME"])
                $("input[data-ax-path='classificationCode']").attr("classificationCode", data["CLASSIFICATION_CODE"])
                classificationSchemeUuid = data['CLASSIFICATION_SCHEME_UUID'];
                selectedTreeItem = {
                    orderKey: "",
                    classTreeName: "",
                    classificationSchemeUuid: "",
                    orderNo: "",
                    parentClassUuid: ""
                };
                if (this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2, data);
            }
        });
    },
    SEARCH_REPOSITORY_SCH: function (caller, act, data) {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch: data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='repositoryName']").val(data["REPOSITORY_NAME"])
                repositoryUuid = data['REPOSITORY_UUID'];
                if (this.close) this.close();
            }
        });
    },
    SEARCH_SHELF_SCH: function (caller, act, data) {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch: data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='shelfName']").val(data["SHELF_NAME"])
                shelfUuid = data['SHELF_UUID'];
                statusUuid = data['STATUS_UUID'];
                if (this.close) this.close();
            }
        });
    },
    SEARCH_LOCATION_SCH : function(caller, act, data)
    {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {

                var text = `${data["ROWNO"]}행 ${data["COLUMNNO"]}열`;

                $("input[data-ax-path='locationName']").val(text)
                locationUuid = data['LOCATIONUUID'];
                console.log('locationUuid', locationUuid);
                if(this.close) this.close();
            }
        });
    },

    SEARCH_USER_SCH: function (caller, act, data) {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch: data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {

                $("input[data-ax-path='requestorName']").val(data['USER_NAME'])
                requestorUuid = data['USER_UUID'];
                console.log('requestorUuid', requestorUuid);
                if (this.close) this.close();
            }
        });
    },

});

fnObj.pageStart = function () {
    var _this = this;
    $.ajax({
        url: "/assets/js/controller/simple_controller.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/st01301.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/st01302.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01, this.formView.getData());
};

fnObj.formView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData);
    },
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });
        this.initEvent();
        this.bindEvent();
    },
    initEvent: function () {
        var _this = this;

        $("input[data-ax-path='requestorName']").parents().eq(1).find("a").click(function () {

            var data = {
                popupCode: "PU107",
                searchData: null,
                preSearch: false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_USER_SCH, data);

        });

        $("input[data-ax-path='repositoryName']").parents().eq(1).find("a").click(function () {
            var data = {
                popupCode: "PU137",
                searchData: $("input[data-ax-path='repositoryName']").val().trim(),
                preSearch: false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_REPOSITORY_SCH, data);
        });

        $("input[data-ax-path='shelfName']").parents().eq(1).find("a").click(function () {
            if ("" != repositoryUuid) {
                var data = {
                    popupCode: "PU138",
                    searchData: repositoryUuid,
                    preSearch: false
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_SHELF_SCH, data);
            }
        });

        $("input[data-ax-path='locationName']").parents().eq(1).find("a").click(function () {
            if ("" != shelfUuid) {
                var data = {
                    popupCode: "PU147",
                    searchData: shelfUuid,
                    preSearch: false
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_LOCATION_SCH, data);
            }
        });


        $("input[data-ax-path='exceptStartDateFrom']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='exceptStartDateFrom']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='exceptStartDateTo']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='exceptStartDateTo']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });

        $("select[data-ax-path='statusUuid'], select[data-ax-path='containerTypeUuid']").change(function () {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
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

        //this.target.find('[data-ax-path="key"]').attr("readonly", "readonly");

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
    },
    bindEvent: function () {
        /* var _this = this;
         $(".btn_confirm01").click(function(){
             ACTIONS.dispatch(ACTIONS.STATUS_UPDATE01,CONFIRM_STATUS);
         });
         $(".btn_cancel01").click(function(){
             ACTIONS.dispatch(ACTIONS.STATUS_UPDATE01,CANCEL_STATUS);
         });
         $(".btn_confirm02").click(function(){
             ACTIONS.dispatch(ACTIONS.STATUS_UPDATE02,CONFIRM_STATUS);
         });
         $(".btn_cancel02").click(function(){
             ACTIONS.dispatch(ACTIONS.STATUS_UPDATE02,CANCEL_STATUS);
         });*/

        $('#btn_deleteWithOutNoticeApproval').click(function () {
            fnObj.gridView02.gridObj.getDataProvider().removeRows(fnObj.gridView02.gridObj.gridView.getCheckedRows(), false);
            fnObj.gridView02.gridObj.dispatch("onRemoveRow");
        });

    },
});

/*팝업 헤더*/
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey: "inoutExceptUuid",
    entityName: "ST_INOUT_EXCEPT",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st01301.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        //this.removeRowBeforeEvent(this.cancelDelete);

    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    disabledColumn: function () {
        var state = axboot.commonCodeValueByCodeName("CD138", CONFIRM_STATUS);
        this.gridObj.setCustomCellStyleRows("disable", function (row) {

            if (row["statusUuid"] == state)
                return true;
            else
                return false;
        }, ["statusUuid", "repositoryCode", "repositoryName", "description"]);
    },
    itemClick: function (data) {

        inoutExceptUuid = data.inoutExceptUuid;

        if (data.exceptReason != undefined) {
            $('textarea[data-ax-path="except_reason"]').val(data.exceptReason);
        } else {
            $('textarea[data-ax-path="except_reason"]').val('');
        }


        if (fnObj.gridView01.isChangeData() == true || fnObj.gridView02.isChangeData() == true) {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA006")
            }, function () {
                if (this.key == "ok") {
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                } else {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
                }
            });
        }

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);

    },
    cancelDelete: function () {
        var codes = axboot.commonCodeFilter("CD138").codeArr;
        var names = axboot.commonCodeFilter("CD138").nameArr;
        var state = undefined;
        for (var i = 0; i < codes.length; i++) {
            if (codes[i] == fnObj.gridView01.getSelectedData().statusUuid) {
                state = names[i];
                break;
            }
        }

        if (state == CONFIRM_STATUS) {
            axToast.push(axboot.getCommonMessage("DF001_01"));

            this.setRunDel(false);
        } else {
            this.setRunDel(true);
        }
    },
    getJsonData: function () {
        return this.gridObj.getJsonRows();
    }

});
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid02",
    primaryKey: "exceptRecordResultUuid",
    uuidFieldName: "exceptRecordResultUuid", //EXCEPT_RECORD_RESULT_UUID
    entityName: "ST_EXCEPT_RECORD_RESULT",
    parentsUuidFieldName: "inoutExceptUuid",
    parentsGrid: fnObj.gridView01,
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st01302.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    disabledColumn: function () {
        var state = axboot.commonCodeValueByCodeName("CD138", CONFIRM_STATUS);
        this.gridObj.setCustomCellStyleRows("disable", function (row) {
            if (row["statusUuid"] == state)
                return true;
            else if (fnObj.gridView02.getSelectedData().useYn == "Y")
                return true;
            else return false;
        }, function (row) {
            if (row["statusUuid"] == state) {
                return ["statusUuid", "shelfCode", "shelfName", "maxContainer", "description"];
            } else {
                return ["useYn"];
            }
        });
    },
    itemClick: function (data) {

    },
    cancelDelete: function () {

    },
});
