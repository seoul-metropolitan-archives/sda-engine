var fnObj = {};
var takeoutRequestUuid = "";
var TAKEOUT_CONFIRM_STATUS = "반출서 작성";
var TAKEOUT_CONFIRM_STATUS2 = "반출 불가";
var repositoryUuid;
var shelfUuid;
var locationUuid;
var requestorUuid;
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
    },
    PAGE_SEARCH01: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st009/01/list01",
            data: $.extend({}, this.formView.getData(),{requestorUuid : requestorUuid}),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
                //fnObj.gridView01.disabledColumn();
                fnObj.gridView02.clearData();

            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH02: function (caller, act, data) {
        if(fnObj.gridView01.getSelectedData() == null){
            return;
        }
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st009/01/list02",
            data: $.extend({}, this.formView.getData(),{takeoutRequestUuid : fnObj.gridView01.getSelectedData().takeoutRequestUuid}),
            callback: function (res) {
                fnObj.gridView02.setData(res.list);

            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    ERROR_SEARCH: function (caller, act, data) {
    },

    //승인
    STATUS_UPDATE01: function (caller, act, data) {

        if (fnObj.gridView01.isChangeData() == true) {
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

            var codes = axboot.commonCodeFilter("CD208").codeArr;
            var names = axboot.commonCodeFilter("CD208").nameArr;
            var state = undefined;
/*
            for(var j = 0 ; j < rows.length; j++){
                for (var i = 0; i < codes.length; i++) {
                    if (codes[i] == rows[j].statusUuid) {
                        state = names[i];
                        if(state != "반출서 작성"){
                            //axToast.push("반입서 작성만 반출승인 가능합니다");
                            return;
                        }
                        break;
                    }
                }
            }*/




            var params = rows.filter(function (item) {
                item.changeStatus = data;
                return item.takeoutRequestUuid != "";
            });

           axboot.ajax({
                type: "PUT",
                url: "/api/v1/st/st009/01/confirm01",
                data: JSON.stringify(params),
                callback: function (res) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
                },
                options: {
                    onError: axboot.viewError
                }
            });
        }
    },
    //승인블가
    STATUS_UPDATE02: function (caller, act, data) {
        if (fnObj.gridView01.isChangeData() == true) {
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

            var codes = axboot.commonCodeFilter("CD208").codeArr;
            var names = axboot.commonCodeFilter("CD208").nameArr;
            var state = undefined;

           /* for(var j = 0 ; j < rows.length; j++){
                for (var i = 0; i < codes.length; i++) {
                    if (codes[i] == rows[j].statusUuid) {
                        state = names[i];
                        if(state != "반출완료"){
                            //axToast.push("반출서 작성만 반출 불가가 가능합니다");
                            return;
                        }
                        break;
                    }
                }
            }
*/



            var params = rows.filter(function (item) {
                item.changeStatus = data;
                return item.takeoutRequestUuid != "";
            });

            axboot.ajax({
                type: "PUT",
                url: "/api/v1/st/st009/01/confirm02",
                data: JSON.stringify(params),
                callback: function (res) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
                },
                options: {
                    onError: axboot.viewError
                }
            });
        }
    },
/*    PAGE_CONFIRM: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE, CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE, CANCEL_STATUS);
    },*/
    PAGE_SAVE: function (caller, act, data) {

       /* if (!this.gridView01.gridObj.validate()) {
            return false;
        } else {
            //여긴 신규다
            if(fnObj.gridView01.gridObj.isDataChanged()){

                var exceptReason = $('textarea[data-ax-path="except_reason"]').val()



                fnObj.gridView01.gridObj.setValue(fnObj.gridView01.getJsonData().length - 1, "exceptReason", exceptReason);

                ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE01);
            }else{
                //데이터가 바뀌지 않았다
                var jsonData = fnObj.gridView01.getJsonData();

                var exceptReason = $('textarea[data-ax-path="except_reason"]').val();
                //null이 아닌경우
                if(exceptReason != ""){
                    //click한애가 있다
                    if(inoutExceptUuid != ""){

                        for(var i=0;i<jsonData.length;i++){
                            if(jsonData[i].inoutExceptUuid == inoutExceptUuid){
                                fnObj.gridView01.gridObj.setValue(i, "exceptReason", exceptReason);
                            }else{
                                fnObj.gridView01.gridObj.setValue(i, "exceptReason", jsonData[i].exceptReason);
                            }
                        }
                    }

                    ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE01);
                }
            }

            if(fnObj.gridView02.gridObj.isDataChanged()){
                ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE02);
            }
        }*/
    },
    TOP_GRID_SAVE01: function (caller, act, data) {
       /* var result = false;
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
        return result;*/
    },
    TOP_GRID_SAVE02: function (caller, act, data) {
       /* var result = false;

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
        return result;*/
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
  /*  PAGE_CLASSIFY: function (caller, act, data) {
        if(inoutExceptUuid == ""){
            return
        }

        axboot.modal.open({
            modalType: "CLASSIFY_POPUP2",
            header: {
                title: "CLASSIFY - " + fnObj.gridView01.gridObj.getSelectedData().requestName
            },
            sendData: function () {
                return {
                    classUuid : inoutExceptUuid,
                    flag : "inout",
                    description : fnObj.gridView01.gridObj.getSelectedData().requestName
                };
            },
            callback: function (data) {
                if(this) this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
            }
        });
    },*/
   /* SEARCH_CLASS_SCH : function(caller, act, data)
    {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='classificationCode']").val(data["CLASSIFICATION_NAME"])
                $("input[data-ax-path='classificationCode']").attr("classificationCode",data["CLASSIFICATION_CODE"])
                classificationSchemeUuid = data['CLASSIFICATION_SCHEME_UUID'];
                selectedTreeItem = {orderKey:"", classTreeName:"",classificationSchemeUuid:"",orderNo:"",parentClassUuid:""};
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2,data);
            }
        });
    },*/
    MODAL_OPEN: function (caller, act, title) {


        var codes = axboot.commonCodeFilter("CD208").codeArr;
        var names = axboot.commonCodeFilter("CD208").nameArr;
        var state = undefined;
        for (var i = 0; i < codes.length; i++) {
            if (codes[i] == fnObj.gridView01.getSelectedData().statusUuid) {
                state = names[i];
                break;
            }
        }

        if(state == "반출완료"){
            var modalOption = { title : title };

            axboot.modal.open({
                modalType: 'CREATE_TAKE_IN_POPUP',
                header: {
                    title: modalOption.title
                },
                sendData: function () {
                    var rows = fnObj.gridView01.getSelectedData();

                    return rows;
                },
                callback: function (data) {
                    if(this) this.close();

                    $('input[data-ax-path="outsourcingDepartment"]').val(data.outsourcingDepartment ? data.outsourcingDepartment : "");
                    $('input[data-ax-path="outsourcingPersonName"]').val(data.outsourcingPersonName ? data.outsourcingPersonName : "");
                    $('input[data-ax-path="outsourcingPosition"]').val(data.outsourcingPosition ? data.outsourcingPosition : "");
                    $('input[data-ax-path="returnDate"]').val(data.startDate ? data.startDate : "");


                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,data);
                }
            });
        }else{
            axToast.push("반출 완료만 반입의뢰서를 작성할수 있습니다.");
        }


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
        url: "/assets/js/column_info/st00901.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/st00902.js",
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

        $('#btn_createTakeOut').click(function(){
            ACTIONS.dispatch(ACTIONS.MODAL_OPEN, '반입의뢰서 작성');
        });

        $("input[data-ax-path='requestorName']").parents().eq(1).find("a").click(function () {

            var data = {
                popupCode: "PU107",
                searchData: null,
                preSearch: false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_USER_SCH, data);

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




        $("input[data-ax-path='requestorName']").keyup(function(e){
            if($(this).val() == ""){
                requestorUuid = "";
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
    bindEvent : function()
    {

        //승인 버튼시 상태 update
        $('.btn_takeoutApproval').click(function(){
            ACTIONS.dispatch(ACTIONS.STATUS_UPDATE01,TAKEOUT_CONFIRM_STATUS);
        });
        //승인 불가 버튼시 상태 update
        $('.btn_takeoutNotApproval').click(function(){
            ACTIONS.dispatch(ACTIONS.STATUS_UPDATE02,TAKEOUT_CONFIRM_STATUS2);
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
        this.setColumnInfo(st00901.column_info);
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
    itemClick: function (data) {

        $('input[data-ax-path="outsourcingDepartment"]').val(data.outsourcingDepartment ? data.outsourcingDepartment : "");
        $('input[data-ax-path="outsourcingPersonName"]').val(data.outsourcingPersonName ? data.outsourcingPersonName : "");
        /*$('input[data-ax-path="outsourcingPhone"]').val(data.outsourcingPhone ? data.outsourcingPhone : "");*/
        $('input[data-ax-path="outsourcingPosition"]').val(data.outsourcingPosition ? data.outsourcingPosition : "");
        $('input[data-ax-path="returnDate"]').val(data.returnDate ? formatDate(data.returnDate) : "");

        //TODO : 아이템 클릭하면 반입 정보, 반입자, 소속, 직위, 반입일자가 들어 와야 하는데 모르겟다.

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);

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
        this.setColumnInfo(st00902.column_info);
        /*this.gridObj.setOption({
            checkBar: {visible: true}
        })*/
        this.makeGrid();
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    itemClick: function (data) {

    },
    cancelDelete: function(){

    },
});

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}