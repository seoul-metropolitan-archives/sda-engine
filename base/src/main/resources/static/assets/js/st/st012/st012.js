var fnObj = {};
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
            url: "/api/v1/st/st012/01/list01",
            data: $.extend({}, {pageSize: 1000}, this.formView.getData(),
                {repositoryUuid: repositoryUuid, shelfUuid: shelfUuid, locationUuid: locationUuid}
                ),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
                //fnObj.gridView01.disabledColumn();
                // fnObj.gridView02.clearData();
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
            url: "/api/v1/st/st012/01/list02",
            data: $.extend({pageSize: 1000}, this.formView.getData(),{inoutExceptUuid : fnObj.gridView01.getSelectedData().inoutExceptUuid},
                {repositoryUuid: repositoryUuid, shelfUuid: shelfUuid, locationUuid: locationUuid}
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
            if(fnObj.gridView01.gridObj.isDataChanged()){


                var exceptReason = $('textarea[data-ax-path="except_reason"]').val()

                for(var i=0;i<fnObj.gridView01.getJsonData().length;i++){
                    fnObj.gridView01.gridObj.setValue(i, "exceptReason", exceptReason);
                }

                ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE01);
            }
            /*if(fnObj.gridView02.gridObj.isDataChanged()){
                ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE02);
            }*/
        }
    },
    TOP_GRID_SAVE01: function (caller, act, data) {
        var result = false;
        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st012/01/save01",
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
        /*var result = false;

        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st001/02/save02",
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
    MODAL_OPEN: function (caller, act, title) {
        var modalOption ={ title : title };
        if( title == '사유 등록'){
            modalOption.modalType = 'SET_REASON_POPUP';
        } else{
            alert('팝업 타입 없음:' + data);
        }
        var aSelected = fnObj.gridView01.getSelectedData();
        console.log("aSelected.length",aSelected.length);
        if( aSelected.length == 0){
            axToast.push(axboot.getCommonMessage("ST012_01"));
            return;
        }

        //aSelected = $.extend({},aSelected);
        console.log("aSelected:"+aSelected);
        axboot.commonModal.open({
            modalType: modalOption.modalType,
            header: {
                title: modalOption.title,
            },
            sendData: function () {


                console.log('aSelected', aSelected);
                // selectedRow.confirmBtn = "Arrange";
                return aSelected;

            },
            callback: function (data) {

                if(this) this.close();
                //if(data){
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01, data);
                //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02, data);
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
        if(inoutExceptUuid == ""){
            return
        }

        axboot.modal.open({
            modalType: "CLASSIFY_POPUP",
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
                /*if(data){
                    crntClassUuid = data.classUuid
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,data);
                }*/
            }
        });
    },
    SEARCH_CLASS_SCH : function(caller, act, data)
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
    },
    SEARCH_REPOSITORY_SCH : function(caller, act, data)
    {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='repositoryName']").val(data["REPOSITORY_NAME"])
                repositoryUuid = data['REPOSITORY_UUID'];
                if(this.close) this.close();
            }
        });
    },
    SEARCH_SHELF_SCH : function(caller, act, data)
    {
        axboot.modal.open3({
            modalType: "COMMON_POPUP3",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='shelfName']").val(data["SHELF_NAME"])
                shelfUuid = data['SHELF_UUID'];
                statusUuid = data['STATUS_UUID'];
                if(this.close) this.close();
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
        url: "/assets/js/column_info/st01201.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/st01202.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    _this.formView.initView();
    _this.gridView01.initView();
   // _this.gridView02.initView();
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

        $('#btn_setReason').click(function(){
            ACTIONS.dispatch(ACTIONS.MODAL_OPEN, '사유 등록');
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

        $("select[data-ax-path='statusUuid'], select[data-ax-path='containerTypeUuid']").change(function () {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
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


        $("input[data-ax-path='inoutDateTimeFrom']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='inoutDateTimeFrom']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='inoutDateTimeTo']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='inoutDateTimeTo']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
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


    },
});

/*팝업 헤더*/
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey: "inoutExceptUuid",
    entityName: "ST_INOUT_EXCEPT",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st01201.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        //this.removeRowBeforeEvent(this.cancelDelete);

    },
    getSelectedData: function () {
        var checkedList = this.gridObj.getCheckedList();
        console.log(checkedList);
        if( checkedList.length == 0){
            // checked된 애가 없음
            // array 형태로 return
            if( this.gridObj.getSelectedData() ){
                return [this.gridObj.getSelectedData()];
            }else{
                return [];
            }

        }else{
            // check 된 애가 있다.
            return checkedList;
        }


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

        console.log( fnObj.gridView01.getSelectedData())

        // if (fnObj.gridView01.isChangeData() == true || fnObj.gridView02.isChangeData() == true) {
        //     axDialog.confirm({
        //         msg: axboot.getCommonMessage("AA006")
        //     }, function () {
        //         if (this.key == "ok") {
        //             ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
        //         } else {
        //             ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
        //         }
        //     });
        // }
        //
        // ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);

    },
    cancelDelete: function(){
        var codes = axboot.commonCodeFilter("CD138").codeArr;
        var names = axboot.commonCodeFilter("CD138").nameArr;
        var state = undefined;
        for (var i = 0; i < codes.length; i++) {
            if (codes[i] == fnObj.gridView01.getSelectedData().statusUuid) {
                state = names[i];
                break;
            }
        }

        if(state == CONFIRM_STATUS){
            axToast.push(axboot.getCommonMessage("DF001_01"));

            this.setRunDel(false);
        }else{
            this.setRunDel(true);
        }
    },
    getJsonData: function () {
        return this.gridObj.getJsonRows();
    }

});


function getFormattedDate(date, isStart) {
    var day;
    var tempDate;
    if (isStart) {
        date.setDate(date.getDate() - 10);
        tempDate = date.getDate();
    } else {
        tempDate = date.getDate();
    }
    day = tempDate.toString();

    var year = date.getFullYear();
    var month = (1 + date.getMonth()).toString();
    month = month.length > 1 ? month : '0' + month;
    day = day.length > 1 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}

function checkDate(date) {
    var result = true;
    var strValue = date;
    var chk1 = /^(19|20)\d{2}-([1-9]|1[012])-([1-9]|[12][0-9]|3[01])$/;
    //var chk2 = /^(19|20)\d{2}\/([0][1-9]|1[012])\/(0[1-9]|[12][0-9]|3[01])$/;
    var chk2 = /^(19|20)\d{2}-([0][1-9]|1[012])-([012][1-9]|3[01])$/;
    if (strValue == "") { // 공백이면 무시
        return result;
    }
//-------------------------------------------------------------------------------
// 유효성 검사- 입력형식에 맞게 들왔는지 // 예) 2000-1-1, 2000-01-01 2가지 형태 지원
//-------------------------------------------------------------------------------
    if (chk1.test(strValue) == false && chk2.test(strValue) == false) { // 유효성 검사에 둘다 성공하지 못했다면
        //alert("1999-1-1 형식 또는 \r\n1999-01-01 형식으로 날자를 입력해주세요.");
        axToast.push(axboot.getCommonMessage("AA011"));
        result = false;

    }
    return result;
}

