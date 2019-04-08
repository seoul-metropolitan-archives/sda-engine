var fnObj = {};
var containerUuid = "";
var selectedRowForContainer = {};
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
            url: "/api/v1/st/st008/01/list01",
            data: $.extend({}, {pageSize: 1000 }, this.formView.getData(),
                                {repositoryUuid: repositoryUuid, shelfUuid: shelfUuid, locationUuid: locationUuid, requestorUuid : requestorUuid, requestorName: $('input[data-ax-path="requestorName"]').val()}
            ),
            callback: function (res) {

                fnObj.gridView01.setData(res.list);
                //fnObj.gridView01.disabledColumn();
                //fnObj.gridView02.clearData();

                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02, data);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH02: function (caller, act, data) {

        var selectedRow = fnObj.gridView01.getSelectedData();
        if (selectedRow == null) {
            return;
        }
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st008/01/list02",
            data: $.extend({}, {pageSize: 1000 }, this.formView.getData(), {takeoutRequestUuid: selectedRow.takeoutRequestUuid},
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
    STATUS_UPDATE: function (caller, act, data) {

        /*if (fnObj.gridView01.isChangeData() == true) {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA006")
            }, function () {
                if (this.key == "ok") {
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    return;
                }
            });
        }*/
        /*else{

            var rows = fnObj.gridView02.gridObj.getCheckedList();
            if (!rows || rows.length < 1) return;
            var params = rows.filter(function (item) {
                item.changeStatus = data;
                return item.arrangeRecordsResultUuid != "";
            });


            axboot.ajax({
                type: "PUT",
                url: "/api/v1/st/st003/02/confirm",
                data: JSON.stringify(params),
                callback: function (res) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,{containerUuid:params[0].containerUuid});
                },
                options: {
                    onError: axboot.viewError
                }
            });
        }*/
    },
    PAGE_CONFIRM: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE, CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE);
    },
    PAGE_SAVE: function (caller, act, data) {
        if (!this.gridView01.gridObj.validate()) {
            return false;
        }
        if (!this.gridView02.gridObj.validate()) {
            return false;
        }

        if (fnObj.gridView01.gridObj.isDataChanged()) {

            ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE01);
        }
        if (fnObj.gridView02.gridObj.isDataChanged()) {
            ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE02);
        }

    },
    TOP_GRID_SAVE01: function (caller, act, data) {
        var result = false;
        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st008/01/save01",
            data: JSON.stringify(this.gridView01.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
            }
        })
            .done(function () {
                fnObj.gridView01.commit();
                //$('textarea[data-ax-path="except_reason"]').val('');
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    TOP_GRID_SAVE02: function (caller, act, data) {
        var result = false;
        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st008/01/save02",
            data: JSON.stringify(this.gridView02.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
            }
        })
            .done(function () {
                fnObj.gridView02.commit();
                //$('textarea[data-ax-path="except_reason"]').val('');
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    MENU_OPEN: function (caller, act, data) {

    },

    MODAL_OPEN: function (caller, act, title) {
        var modalOption = {title: title};
        if (title == '반출서 작성') {
            modalOption.modalType = 'CREATE_TAKE_OUT_POPUP';
        } else if (title == '반출서 수정') {
            modalOption.modalType = 'MODIFY_TAKE_OUT_POPUP';
        } else if (title == '대상 추가') {
            modalOption.modalType = 'ARRANGE_POPUP';
            modalOption.title = "대상 추가 - " + fnObj.gridView01.gridObj.getSelectedData().requestName
        } else if (title == '반출서 출력') {
            modalOption.modalType = 'PRINT_TAKE_OUT_POPUP';
        } else {
            alert('팝업 타입 없음:' + data);
        }

        if (modalOption.modalType == 'CREATE_TAKE_OUT_POPUP' || modalOption.modalType == 'PRINT_TAKE_OUT_POPUP') {
            // '반출서 작성' 상태 값
            // do nothing
        } else {
            // 반출서수정 / 대상추가
            var selectedRow = fnObj.gridView01.getSelectedData();
            if (isWritingMode( selectedRow ) == true) {
                // 작성 모드
            } else {
                // 그외 모드
                axToast.push(axboot.getCommonMessage("ST008_01"));
                return;
            }

        }

        axboot.modal.open({
            modalType: modalOption.modalType,
            header: {
                title: modalOption.title,
            },
            sendData: function () {
                if (modalOption.modalType == 'CREATE_TAKE_OUT_POPUP') {
                    // 작성의 경우 보낼필요 없음.
                    return {};
                }
                var selectedRow = fnObj.gridView01.getSelectedData();

                if( modalOption.modalType == 'ARRANGE_POPUP'){
                    // 대상 추가
                    return {
                        takeoutRequestUuid: selectedRow.takeoutRequestUuid,
                        confirmBtn:"추가",
                        flag: "inout",
                        description: "반출의뢰서 : "+selectedRow.requestName,
                        fromWhere: 'st008',
                    };
                }else {
                    return selectedRow;
                }

            },
            callback: function (data) {

                if (this) this.close();
                //if(data){
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01, data);

                //}
            }
        });
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
        if (containerUuid == "") {
            return
        }

        axboot.modal.open({
            modalType: "CLASSIFY_POPUP",
            header: {
                title: "CLASSIFY - " + fnObj.gridView01.gridObj.getSelectedData().requestName
            },
            sendData: function () {
                return {
                    classUuid: containerUuid,
                    flag: "inout",
                    description: fnObj.gridView01.gridObj.getSelectedData().requestName
                };
            },
            callback: function (data) {
                if (this) this.close();
                /*if(data){
                    crntClassUuid = data.classUuid
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,data);
                }*/
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
                $("input[data-ax-path='repositoryName']").val(data["REPOSITORY_NAME"]);
                $("input[data-ax-path='shelfName']").val('');
                repositoryUuid = data['REPOSITORY_UUID'];
                if (this.close) this.close();
            }
        });
    },
    SEARCH_SHELF_SCH: function (caller, act, data) {
        axboot.modal.open3({
            modalType: "COMMON_POPUP3",
            preSearch: data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='shelfName']").val(data["SHELF_NAME"]);
                $("input[data-ax-path='locationName']").val('');
                shelfUuid = data['SHELF_UUID'];
                statusUuid = data['STATUS_UUID'];
                if (this.close) this.close();
            }
        });
    }
    ,SEARCH_LOCATION_SCH : function(caller, act, data)
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
    EXCEL_DOWN01 : function(caller, act, data){


        var selectedRow = fnObj.gridView01.getSelectedData();
        if (selectedRow == null) {
            axToast.push("출력할 대상을 선택 해 주세요.");
            return;
        }
        var param = $.param( selectedRow );
        location.href = "/api/v1/st/st008/01/excelDown?"+param;
    }
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
        url: "/assets/js/column_info/st00801.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/st00802.js",
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
        $('.excelDown').on('click',function(){
            ACTIONS.dispatch(ACTIONS.EXCEL_DOWN01);
        });

        $("input[data-ax-path='parentContainerName']").keyup(function(e){
            if($(this).val() == ""){
                parentContainerUuid = "";
            }
        });

        $("input[data-ax-path='containerName']").keyup(function(e){
            if($(this).val() == ""){
                containerUuid = "";
            }
        });

        $("input[data-ax-path='repositoryName']").keyup(function(e){
            if($(this).val() == ""){
                repositoryUuid = "";
            }
        });

        $("input[data-ax-path='shelfName']").keyup(function(e){
            if($(this).val() == ""){
                shelfUuid = "";
            }
        });

        $("input[data-ax-path='locationName']").keyup(function(e){
            if($(this).val() == ""){
                locationUuid = "";
            }
        });


        $('.btn_i').click(function () {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
        });
        $('#btn_createTakeOut').click(function () {
            ACTIONS.dispatch(ACTIONS.MODAL_OPEN, '반출서 작성');
        });
        $('#btn_modifyTakeOut').click(function () {
            ACTIONS.dispatch(ACTIONS.MODAL_OPEN, '반출서 수정');
        });
        $('#btn_addTakeOut').click(function () {
            ACTIONS.dispatch(ACTIONS.MODAL_OPEN, '대상 추가');
        });
        $('#btn_printTakeOut').click(function () {
            ACTIONS.dispatch(ACTIONS.MODAL_OPEN, '반출서 출력');
        });

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


        $("input[data-ax-path='takeoutDateFrom']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='takeoutDateFrom']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='takeoutDateTo']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='takeoutDateTo']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });


        $("input[data-ax-path='returnDueDateFrom']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='returnDueDateFrom']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='returnDueDateTo']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='returnDueDateTo']").keypress(function () {
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
        $(".btn_arrange").click(function () {
            ACTIONS.dispatch(ACTIONS.MODAL_OPEN);
        });
    },
});


fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey: "takeoutRequestUuid",
    entityName: "ST_TAKEOUT_REQUEST",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00801.column_info);
        this.gridObj.setOption({
            checkBar: {visible: false}
        })
        this.makeGrid();

        this.removeRowBeforeEvent(this.cancelDelete);
        this.gridObj.itemClick(this.itemClick);

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

        console.log('clickedItem', data)
        // 반출목적에 적용
        $('#takeoutPropose').val(data.takeoutPropose);
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02, data);
        /*containerUuid = data.containerUuid;
        if (fnObj.gridView01.isChangeData() == true || fnObj.gridView02.isChangeData() == true) {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA008")
            }, function () {
                if (this.key == "ok") {
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                } else {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
                }
            });
        }

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);*/

    },
    cancelDelete: function () {

        if (isWritingMode(fnObj.gridView01.getSelectedData())== true) {
            // 작성 모드
            this.setRunDel(true);
        } else {
            // 그외 모드
            axToast.push(axboot.getCommonMessage("ST008_01"));
            this.setRunDel(false);
        }

    },
    getJsonData: function () {
        return this.gridObj.getJsonRows();
    }

});
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid02",
    primaryKey: "withoutNoticeIoHistUuid",
    uuidFieldName: "withoutNoticeIoHistUuid", //EXCEPT_RECORD_RESULT_UUID
    entityName: "ST_WITHOUT_NOTICE_INOUT_HIST",
    parentsUuidFieldName: "containerUuid",
    parentsGrid: fnObj.gridView01,
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00802.column_info);
        this.gridObj.setOption({
            checkBar: {visible: false}
        })
        this.makeGrid();
        //this.gridObj.itemClick(this.itemClick);
        this.removeRowBeforeEvent(this.cancelDelete);
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
        /*if (fnObj.gridView03.isChangeData() == true) {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA008")
            }, function () {
                if (this.key == "ok") {
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                } else {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
                }
            });
        } else {

            var codes = axboot.commonCodeFilter("CD138").codeArr;
            var names = axboot.commonCodeFilter("CD138").nameArr;
            var state = undefined;
            for (var i = 0; i < codes.length; i++) {
                if (codes[i] == fnObj.gridView02.getSelectedData().statusUuid) {
                    state = names[i];
                    break;
                }
            }

            //confirm confirm
            if(state == CONFIRM_STATUS){
                if(fnObj.gridView02.getSelectedData().useYn == "Y"){
                    fnObj.gridView03.gridObj.setRunAdd(true);
                }else{
                    fnObj.gridView03.gridObj.setRunAdd(false);
                }
            }else{
                fnObj.gridView03.gridObj.setRunAdd(false);
            }


            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
        }*/
    },
    cancelDelete: function () {

        if (isWritingMode(fnObj.gridView01.getSelectedData())== true) {
            // 작성 모드
            this.setRunDel(true);
        } else {
            // 그외 모드
            axToast.push(axboot.getCommonMessage("ST008_01"));
            this.setRunDel(false);
        }

    },
});
function isWritingMode(selectedRow){
    return selectedRow.statusUuid == '6B1C7487-99F3-4F04-B449-891AD4679E00' || selectedRow.statusUuid == null;
}
