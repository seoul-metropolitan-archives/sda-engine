var fnObj = {};
var inoutExceptUuid = "";
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
            url: "/api/v1/st/st007/01/list01",
            data: $.extend({}, {pageSize: 1000}, this.formView.getData(),
            {repositoryUuid : repositoryUuid, shelfUuid : shelfUuid, locationUuid : locationUuid}
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

        if(fnObj.gridView01.getSelectedData() == null){
            return;
        }

        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st007/01/list02",
            data: $.extend({}, {pageSize: 1000}, fnObj.gridView01.getSelectedData(), this.formView.getData(), {repositoryUuid: repositoryUuid, shelfUuid: shelfUuid, locationUuid: locationUuid}),
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
            url: "/api/v1/st/st007/01/save01",
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
                $("input[data-ax-path='repositoryName']").val(data["REPOSITORY_NAME"]);
                $("input[data-ax-path='shelfName']").val('');
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
                $("input[data-ax-path='shelfName']").val(data["SHELF_NAME"]);
                $("input[data-ax-path='locationName']").val('');
                shelfUuid = data['SHELF_UUID'];
                statusUuid = data['STATUS_UUID'];
                if(this.close) this.close();
            }
        });
    },
    SEARCH_LOCATION_SCH : function(caller, act, data)
    {
        axboot.modal.open3({
            modalType: "COMMON_POPUP3",
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
        url: "/assets/js/column_info/st00701.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/st00702.js",
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

        $("input[data-ax-path='disposalDueDateStart']").val(getFormattedDate(new Date(), true));
        $("input[data-ax-path='disposalDueDateEnd']").val(getFormattedDate(new Date()));
        // 초기값을 이렇게 넣어줘야 최초 호출시 값이 들어감.
        fnObj.formView.setFormData("disposalDueDateStart", $("input[data-ax-path='disposalDueDateStart']").val());
        fnObj.formView.setFormData("disposalDueDateEnd", $("input[data-ax-path='disposalDueDateEnd']").val());

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

        $("input[data-ax-path='repositoryName']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU137",
                searchData : $("input[data-ax-path='repositoryName']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_REPOSITORY_SCH,data);
        });

        $("input[data-ax-path='shelfName']").parents().eq(1).find("a").click(function(){
            if("" != repositoryUuid) {
                var data = {
                    popupCode: "PU138",
                    searchData: repositoryUuid,
                    preSearch: false
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_SHELF_SCH, data);
            }
        });

        $("input[data-ax-path='locationName']").parents().eq(1).find("a").click(function(){
            if("" != shelfUuid) {
                var data = {
                    popupCode: "PU147",
                    searchData: shelfUuid,
                    preSearch: false
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_LOCATION_SCH, data);
            }
        });


        $("input[data-ax-path='disposalDueDateStart']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='disposalDueDateStart']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='disposalDueDateEnd']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='disposalDueDateEnd']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });

        $("select[data-ax-path='statusUuid'], select[data-ax-path='containerTypeUuid']").change(function() {
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
    primaryKey: "withoutNoticeIoRecordUuid",
    entityName: "ST_WITHOUT_NOTICE_INOUT_RECORD",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00701.column_info);
        this.gridObj.setOption({
           // checkBar: {visible: true}
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
        withoutNoticeIoRecordUuid = data.withoutNoticeIoRecordUuid;
        console.log(withoutNoticeIoRecordUuid)

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
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid02",
    primaryKey: "withoutNoticeIoHistUuid",
    uuidFieldName: "withoutNoticeIoHistUuid", //EXCEPT_RECORD_RESULT_UUID
    entityName: "ST_WITHOUT_NOTICE_INOUT_HIST",
    parentsUuidFieldName: "withoutNoticeIoRecordUuid",
    parentsGrid: fnObj.gridView01,
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00702.column_info);
        this.gridObj.setOption({
            // checkBar: {visible: true}
        })
        this.makeGrid();
        //this.gridObj.itemClick(this.itemClick);
        // this.removeRowBeforeEvent(this.cancelDelete);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    disabledColumn: function () {
        var state = axboot.commonCodeValueByCodeName("CD138", CONFIRM_STATUS);
        this.gridObj.setCustomCellStyleRows("disable", function(row){
            if (row["statusUuid"] == state)
                return true;
            else if(fnObj.gridView02.getSelectedData().useYn == "Y")
                return true;
            else return false;
        },function(row){
            if(row["statusUuid"] == state) {
                return ["statusUuid", "shelfCode", "shelfName", "maxContainer", "description"];
            }else {
                return ["useYn"];
            }
        });
    },
    itemClick: function (data) {
        /*if (fnObj.gridView03.isChangeData() == true) {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA006")
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
    cancelDelete: function(){
        /*var codes = axboot.commonCodeFilter("CD138").codeArr;
        var names = axboot.commonCodeFilter("CD138").nameArr;
        var state = undefined;
        for (var i = 0; i < codes.length; i++) {
            if (codes[i] == fnObj.gridView02.getSelectedData().statusUuid) {
                state = names[i];
                break;
            }
        }

        if(state == CONFIRM_STATUS){
            axToast.push(axboot.getCommonMessage("DF001_01"));

            this.setRunDel(false);
        }else{
            this.setRunDel(true);
        }*/
    },
});

function getFormattedDate(date, isStart) {
    var day;
    var tempDate;
    if (isStart) {
        date.setDate(date.getDate() - 30);
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

