
var fnObj = {};
var parentsData;

var repositoryUuid;
var shelfUuid;
var locationUuid;

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH_TREE: function (caller, act, data) {
        axboot.ajax({
            url: "/api/v1/st/st006/getAllNodes",
            data: $.extend({},data,{nodeType:"normal"}),
            callback: function (res) {
                fnObj.treeView01.setData({}, res.list, data);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH: function (caller, act, data) {
      axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st006/01/list01",
            async : false,
            data: $.extend({}, {pageSize: 10000},{aggregationUuid: data.uuid}),
            callback: function (res) {
                fnObj.gridView02.resetCurrent();
                fnObj.gridView02.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    PAGE_CONFIRM: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE,CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE,CANCEL_STATUS);
    },
    PAGE_SAVE : function (caller, act, data) {
        console.log('formViewData', data);
        /*if(fnObj.gridView03.getData().length  < 1){
            return;
        }
        var send = fnObj.gridView03.getData();

        for(var i=0;i<fnObj.gridView03.getJsonData().length;i++){
            send[i]['containerUuid'] = parentsData.containerUuid;
        }
*/

        if( repositoryUuid == null){
            axToast.push("Repository 를 선택 해 주세요");
            return;
        }
        if( shelfUuid == null){
            axToast.push("Shelf 를 선택 해 주세요");
            return;
        }
        if( locationUuid == null){
            axToast.push("Location 을 선택 해 주세요");
            return;
        }
        var data = $.extend({repositoryUuid: repositoryUuid, shelfUuid:shelfUuid, locationUuid: locationUuid}, parentsData);

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/st/st006/01/save",
            data: JSON.stringify(data),

            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_CLOSE, data);
            },
            options: {
                onError: axboot.viewError
            }
        });

        return false;
    },
    PAGE_CLOSE: function (caller, act, data) {

        if (parent) {
            if(parent.axboot.modalOpener == "modal")
                parent.axboot.modal.callback(data);
            else if(parent.axboot.modalOpener == "commonModal")
                parent.axboot.commonModal.callback(data);
        }
    },
    PAGE_CLASSIFY: function (caller, act, data) {
        if(fnObj.gridView03.getData().length  < 1){
            return;
        }
        var send = fnObj.gridView03.getData();

        for(var i=0;i<fnObj.gridView03.getJsonData().length;i++){
            send[i]['containerUuid'] = parentsData.containerUuid;
        }

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/st/st006/03/save",
            data: JSON.stringify(send),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_CLOSE,{classUuid:parentsData.classUuid});
            },
            options: {
                onError: axboot.viewError
            }
        });

        return false;
    },
    SEARCH_CONTAINER_SCH: function(caller, act, data){
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='parentContainerName']").val(data["CONTAINER_NAME"])
                $("input[data-ax-path='parentContainerName']").attr("parentContainerName",data["CONTAINER_NAME"])
                parentContainerUuid = data['CONTAINER_UUID'];
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,data);
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
        axboot.modal.open({
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
    dispatch: function (caller, act, data) {
        var result = ACTIONS.exec(caller, act, data);
        if (result != "error") {
            return result;
        } else {
            return false;
        }
    }
});


fnObj.pageStart = function () {
    var _this = this;
    if(parent.axboot.modalOpener == "modal")
        parentsData = parent.axboot.modal.getData();
    else if(parent.axboot.modalOpener == "commonModal")
        parentsData = parent.axboot.commonModal.getData();

    $.ajax({
        url: "/assets/js/controller/simple_controller.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

   /* $.ajax({
        url: "/assets/js/column_info/st00601_p01_01.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
     */

    _this.formView.initView();



    //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH_TREE, this.formView.getData());
    // ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.formView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, parentsData);
    },
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        console.log('qqqqqqqqq', this.getDefaultData());
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();
    },
    initEvent: function () {
        var _this = this;

        // $(".btn_main_txt01").text(parentsData.confirmBtn);
        //
        // $(".sltCont").text(parentsData.crrntAgg);
        //
        // $(".btn_small").click(function(){
        //    if(this.textContent == "Save"){
        //        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
        //        this.textContent = "Edit";
        //        $("#classDescription").prop("readonly",true);
        //        $("#levelOfDetailUuid").prop("disabled",true);
        //        $("#statusDescription").prop("disabled", true);
        //        $("#classDescription").css("background","#ffffff");
        //    }else if(this.textContent == "Edit"){
        //        this.textContent = "Save";
        //        $("#classDescription").prop("readonly",false);
        //        $("#levelOfDetailUuid").prop("disabled", false);
        //        $("#statusDescription").prop("disabled", false);
        //        $("#classDescription").css("background","#fffdd6");
        //    }
        // });
        // $(".btn_s").click(function() {
        //     //if (this.textContent == "Save") {
        //         ACTIONS.dispatch(ACTIONS.PAGE_SAVE, fnObj.formView.getData());
        //     //}
        // });
        $(".close_popup").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE );
        });

        var accordion = {
            click: function(target) {
                var $target = $(target);
                $target.on('click', function() {

                    if ($(this).hasClass('on')) {
                        slideUp($target);
                    } else {
                        slideUp($target);
                        $(this).addClass('on').next().slideDown();
                    }

                    function slideUp($target) {
                        $target.removeClass('on').next().slideUp();
                    }

                });
            }
        };
        accordion.click('.accordion > ul');

        var _this = this;
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

        $("input[data-ax-path='parentContainerName']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU135",
                searchData : $("input[data-ax-path='parentContainerName']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_CONTAINER_SCH,data);
        });
        $("input[data-ax-path='parentContainerName']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU135",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_CONTAINER_SCH,data);
            }

        });
        $("input[data-ax-path='containerName']").focusout(function(){

            if("" == $(this).val().trim())
            {
                $("input[data-ax-path='containerName']").val("");
            }
        });
        $("input[data-ax-path='provenance']").focusout(function(){

            if("" == $(this).val().trim())
            {
                $("input[data-ax-path='provenance']").val("");
            }
        });

        $("input[data-ax-path='containerName'],input[data-ax-path='provenance'] ,input[data-ax-path='controlNumber']").keyup(function(){
            if(13 == event.keyCode)
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        });
        $("select[data-ax-path='statusUuid'], select[data-ax-path='containerTypeUuid']").change(function() {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        });

        $(".bdb").delegate("#rg_tree_allopen", "click", function () {
            _this.expandAll();
        });
        $(".bdb").delegate("#rg_tree_allclose", "click", function () {
            _this.collapseAll();
        });

        $("input[data-ax-path='creationStartDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='creationStartDate']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='creationEndDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='creationEndDate']").keypress(function () {
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
    getFormData: function (dataPath) {
        return this.model.get(dataPath);
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
    },
    expandAll:function(){
        fnObj.gridView01.gridObj.expandAll();
    },
    collapseAll:function() {
        fnObj.gridView01.gridObj.collapseAll();
    }
});

/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}
 */

isDataChanged = function () {
    if (fnObj.gridView01.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}
exportItemList = function() {
    if(fnObj.gridView02.gridObj.getCheckedList().length > 0){
        //gridView03 목록에 추가
        fnObj.gridView03.setData(fnObj.gridView02.gridObj.getCheckedList());
        // fnObj.gridView02.gridObj.onItemChecked()
        // var getCheckedRows = fnObj.gridView02.gridObj.getCheckedRows();
        // fnObj.gridView02.gridObj.setCheckable(0,false);
        // fnObj.gridView02.gridObj.getcheck
        // fnObj.gridView02.gridObj.setCustomCellStyleRows("disable",function(row){
        //
        //     if(row["statusUuid"] == state)
        //         return true;
        //     else
        //         return false;
        // },["containerName","parentContainerName","containerTypeUuid","controlNumber","provenance","creationStartDate","creationEndDate","description","orderNo"]);
    }else{
        alert("Select Arrange Item List")
    }
}
importItemList = function(){
    alert("import");
}

setCheckable = function(rows){
    // for()
}
