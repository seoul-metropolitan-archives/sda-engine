var fnObj = {};
var option = '';
var authorityTypeUuid = '';
var isChanged = false;
var selectedRadio = "";
var selectedLabel = "";
var authInfoCnt = 0;
var relAutData = [];
var ORG_TYPE_NM = "Organization";
var firstSearch = 1;
var childrenAuthInfo = false;
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({ //트리 리스트
            type: "GET",
            url: "/api/v1/at/at001/01/list",
            data: $.extend({}, {pageSize: 1000}, {authorityTypeUuid:authorityTypeUuid,authorityName:$("#AuthNm").val()}),
            callback: function (res) {
                fnObj.formView.clear();
                var gridData = [];
                relAutData = [];
                for (var i = 0; i < res.list.length; i++){
                    gridData.push(res.list[i].authInfo);
                    relAutData.push(res.list[i].relAuthList);
                }
                fnObj.gridView01.setData(gridData);
                if(res.list.length == 0){
                    $("#contentsArea").hide();
                }else{
                    $("#contentsArea").show();
                    fnObj.formView.setData(gridData[fnObj.gridView01.gridObj.gridView.getSelectedItems()]);
                    fnObj.childrenAuthInfo.setData(relAutData[fnObj.gridView01.gridObj.gridView.getSelectedItems()])
                    isChanged = false;
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    SEARCH_AUTH_INFO: function (caller, act, data) {
        var callback = data["callback"];
        var reqData = ax5.util.deepCopy(data);
        delete(reqData["callback"]);
        axboot.commonModal.open({
            modalType: "COMMON_POPUP",
            // preSearch : reqData["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                callback(data);
            }
        });
    },
    PAGE_SAVE: function (caller, act, data) {
        var saveData  = {
            relAuthList : fnObj.childrenAuthInfo.getData(),
            authInfo : $.extend({}, fnObj.formView.getData(), {authorityTypeUuid:authorityTypeUuid})
        }

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/at/at001/01/save",
            data: JSON.stringify($.extend({}, saveData)),
            callback: function (res) {
                if(data!= undefined){
                    authorityTypeUuid = data.tempAuthorityTypeUuid;
                    fnObj.formView.changeView();
                }else{
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH)
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    ADD_AUTHORITY: function(caller, act, data){
        fnObj.gridView01.registerAuth();
    },
    DEL_AUTHORITY: function(caller, act, data){
        axDialog.confirm({
            msg: fnObj.formView.getData().authorityName + "을(를) 삭제하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                fnObj.formView.setFormData('__deleted__',true);
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
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
    $.ajax({
        url: "/assets/js/controller/simple_controller.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/at00101.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    _this.formView.initView();
    _this.gridView01.initView();
    _this.childrenAuthInfo.initView();
};
fnObj.formView = axboot.viewExtend(axboot.formView, {
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시
        this.model.onChange("*", function (n) {
            isChanged = true;
         });
        this.makeRadio();
        this.initEvent();
        this.bindEvent();
        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });
    },
    initEvent: function(){
        $('input:radio[name="radio"]').change(function(){
            var tempAuthorityTypeUuid = event.currentTarget.value;
            if(isChanged){
                axDialog.confirm({
                    msg: "데이터가 변경되었습니다. 저장하시겠습니까?"
                }, function () {
                    if (this.key == "ok") {
                        // $('#' + selectedRadio).prop('checked',true);
                        ACTIONS.dispatch(ACTIONS.PAGE_SAVE,{tempAuthorityTypeUuid : tempAuthorityTypeUuid})
                    }else{
                        authorityTypeUuid = tempAuthorityTypeUuid;
                        fnObj.formView.changeView();
                    }
                    isChanged = false;
                });
            }else{
                authorityTypeUuid = tempAuthorityTypeUuid;
                fnObj.formView.changeView();
            }
        });
        $("input[data-ax-path='startDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='startDate']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='endDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='endDate']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='descriptionDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='descriptionDate']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });

        $("input[data-ax-path='startDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });
        $("input[data-ax-path='endDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });
        $("input[data-ax-path='descriptionDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });
    },
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData);
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        return $.extend({}, data);
    },
    setFormData: function (dataPath, value) {
        this.model.set(dataPath, value);
    },
    setData: function (data) {
        if(firstSearch != 1) {
            $(".auth_fit").remove();
        }
        firstSearch++;
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
    makeRadio: function() {
        var codes = axboot.commonCodeFilter("CD161").codeArr;
        var names = axboot.commonCodeFilter("CD161").nameArr;
        var defaultYNs = axboot.commonCodeFilter("CD161").defaultYNArr;
        var radioTag = "";
        for(var i = 0; i < codes.length; i++)
        {
            if(defaultYNs[i] == 'Y'){
                radioTag += '<label for="authType' + i + '"><input type="radio" id="authType' + i + '"  name="radio" class="no_border" value="' + codes[i] + '" checked="checked">' + names[i] + '</label>'
                authorityTypeUuid = codes[i];
                authorityTypeNm = names[i];
            }else{
                radioTag += '<label for="authType' + i + '"><input type="radio" id="authType' + i + '" name="radio" class="no_border" value="' + codes[i] + '">' + names[i] + '</label>'
            }


        }
        $(".rdo_box").append(radioTag);
        fnObj.formView.changeView();
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
    },
    bindEvent : function()
    {
        var _this = this;
        $(".btn_adf").click(function() {
            ACTIONS.dispatch(ACTIONS.ADD_AUTHORITY);
        }),
        $(".btn_def").click(function() {
            ACTIONS.dispatch(ACTIONS.DEL_AUTHORITY);
        })
    },
    changeView: function () {
        fnObj.formView.clear();
        // $(".auth_fit").remove();
        fnObj.childrenAuthInfo.addChild($("#addDnrInfo"));

        selectedRadio = $('input:radio[name="radio"]:checked').attr("id");
        selectedLabel = $("label[for='"+selectedRadio+"']").text();
        authorityTypeNm = selectedLabel;
        if(selectedLabel == "Organization"){
            $("#organizationArea").show();
            $("#etcArea").hide();
        }else{
            $("#organizationArea").hide();
            $("#etcArea").show();
        }

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    }
});
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId:"realgrid01",
    primaryKey : "authorityUuid",
    entityName : "AT_AUTHORITY",
    initView : function() {
        var _this = this;
        _this.initInstance();
        _this.setColumnInfo(at00101.column_info);
        _this.makeGrid();
        _this.gridObj.itemClick(_this.itemClick);
    },
    itemClick: function (data) {
        if(isChanged){
            axDialog.confirm({
                msg: "데이터가 변경되었습니다. 저장하시겠습니까?"
            }, function () {
                if (this.key == "ok") {
                    //유효성 검사
                    var authorityName = fnObj.formView.getData().authorityName;
                    var orgTypeUuid = fnObj.formView.getData().orgTypeUuid;

                    if(authorityName == undefined || authorityName.trim() == ""){
                        return;
                    }

                    if(authorityTypeNm == ORG_TYPE_NM){
                        if(orgTypeUuid == undefined || orgTypeUuid.trim() == ""){
                            return;
                        }
                    }
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE)
                }else{
                    fnObj.formView.setData(data);
                }
                isChanged = false;
            });
        }else{
            fnObj.formView.clear();
            fnObj.formView.setData(data);
            fnObj.childrenAuthInfo.setData(relAutData[fnObj.gridView01.gridObj.gridView.getSelectedItems()])
        }
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
    registerAuth : function(){
        axboot.modal.open({
            modalType: "AUTHORITY_REGISTER_POPUP",
            header: {
                title: "AUTHORITY REGISTER POPUP"
            },
            sendData: function () {
                return {
                    authorityTypeUuid : authorityTypeUuid,
                    // description : fnObj.gridView01.gridObj.getSelectedData().description
                };
            },
            callback: function (data) {
                if(this) this.close();
                if(data){
                   ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                }else{
                    // _this.gridView.commit(true);
                    // _this.gridView.getDataProvider().removeRows(_this.gridView.getSelectedRows(), false);
                    fnObj.gridView01.gridObj._gridView.dispatch("onRemoveRow");
                }
            }
        });
    }
});

fnObj.childrenAuthInfo = axboot.viewExtend({
    targetTag  : $("#childrenAuthInfoArea"),
    parentUuid : "",
    nodeType : "",
    popupCode : "",
    template :
    "                                                            <input type=text data-ax-path='__created__' style='width: 0px;display: none'>" +
    "                                                            <input type=text data-ax-path='__deleted__' style='width: 0px;display: none'>" +
    "                                                            <input type=text data-ax-path='__modified__' style='width: 0px;display: none'>" +
    "                                                            <input type=text data-ax-path='authorityRelationUuid' style='width: 0px;display: none'>" +
    "                                                            <li style='padding: 0 10px 0 0;'>" +
    "                                                               <select data-ax-path='relationTypeUuid' class='relationTypeUuid' style='width:135px;'>" +
    "                                                                   <option value='' disabled>관련기관</option>" +
    "                                                                </select>" +
    "                                                            </li>" +
    "                                                            <li style='padding: 0 10px 0 0;'>" +
    "                                                               <div class='src_box2'>" +
    "                                                                <input type=text data-ax-path='relAuthorityName'  disabled class='form-control' placeholder='관련전거'>" +
    "                                                                <input type=text data-ax-path='relAuthorityUuid' class='relAuthorityUuid' style='width: 0; display: none'>" +
    "                                                                <a href='#' class='searchAuthority' ><img src='/assets/images/ams/search_normal.png' alt='find'></a>" +
    "                                                            </div>" +
    "                                                            </li>" +
    "                                                            <li style='text-align: center'>" +
    "                                                                <div><a href='#' class='btn_del_left' style=''>X</a></div>" +
    "                                                          </li>",
    initView: function () {
        this.initEvent();

        var codes = axboot.commonCodeFilter("CD162").codeArr;
        var names = axboot.commonCodeFilter("CD162").nameArr;
        for(var i = 0; i < codes.length; i++)
        {
            option += '<option value="'+ codes[i] + '">' + names[i] + '</option>';

        }

        //this.addChild($("#addAuthInfo"));
    },
    initEvent: function () {
        var _this = this;
        $("#addAuthInfo").click(function(){
            _this.addChild(this);
        });

        $(".childDnrInfo").on("change",".relationTypeUuid",function(){
            if("saved" == $(this).parents().eq(1).attr("saveType"))
            {
                $(this).parents().eq(2).find("input[data-ax-path='__modified__']").val(true);
                isChanged = true;
            }
        });

        $(".childDnrInfo").on("change",".relAuthorityUuid",function(){
            if("saved" == $(this).parents().eq(1).attr("saveType"))
            {
                $(this).parents().eq(2).find("input[data-ax-path='__modified__']").val(true);
                isChanged = true;
            }
        });

        $("#childrenAuthInfoArea").delegate(".searchAuthority","click",function(){
            var parentsTag  = $(this).parents().eq(2);
            axboot.modal.open({
                modalType: "AUTHORITY_POPUP",
                header: {
                    title: "Authority List"
                },
                sendData: function () {
                    return {
                        antiAuthorityUuid : fnObj.gridView01.getSelectedData().authorityUuid
                    };
                },
                callback: function (data) {
                    if(this) this.close();
                    if(data){
                        parentsTag.find("input[data-ax-path='relAuthorityUuid']").val(data["authorityUuid"])
                        parentsTag.find("input[data-ax-path='relAuthorityName']").val(data["authorityName"])
                    }
                }
            });
        });


        $(".childDnrInfo").delegate(".btn_del_left","click",function(){
            if("create" == $(this).parents().eq(2).attr("saveType"))
            {
                $(this).parents().eq(2).remove();
            }
            else
            {
                $(this).parents().eq(2).hide();
                $(this).parents().eq(2).find("input[data-ax-path='__deleted__']").val(true);
                $(this).parents().eq(2).find("input[data-ax-path='__modified__']").val(false);
            }
        });
    },
    addChild : function(_this,data){
        var cloneTag = "";
        if(data != null && data != undefined) {
            cloneTag = $("<ul>").addClass("auth_fit").attr("data-ax-path", "saveType").attr("saveType", "saved").html(this.template).clone();
            cloneTag.find("select").append($(option));
            cloneTag.find("input[data-ax-path='relAuthorityName']").val(data["relAuthorityName"]);
            cloneTag.find("input[data-ax-path='relAuthorityUuid']").val(data["relAuthorityUuid"]);
            cloneTag.find("input[data-ax-path='relationTypeUuid']").val(data["relationTypeUuid"]);
            cloneTag.find("input[data-ax-path='authorityRelationUuid']").val(data["authorityRelationUuid"]);
            cloneTag.find("input[data-ax-path='__created__']").val(data["__created__"]);
            cloneTag.find("input[data-ax-path='__deleted__']").val(data["__deleted__"]);
            cloneTag.find("input[data-ax-path='__modified__']").val(data["__modified__"]);
            cloneTag.find("select").val(data["relationTypeUuid"]).attr("selected","selected");

        }else{
            cloneTag = $("<ul>").addClass("auth_fit").attr("data-ax-path","saveType").attr("saveType","created").html(this.template).clone();
            cloneTag.find("select").append($(option));
            cloneTag.find("input[data-ax-path='__created__']").val(true);
            cloneTag.find("select").val("").attr("selected","selected");
        }
            $(_this).before(cloneTag);
            cloneTag.show();
    },
    getData : function(){
        var retData = new Array();
        var data = {};
        if(this.targetTag.css("display") != "none")
        {
            $(this.targetTag).find(".childDnrInfo>ul:not(#addAuthInfo)").each(function(){
                data = {};
                data["saveType"] = $(this).attr("saveType");
                $(this).find("input,select,textarea").each(function(){

                    if($(this).attr("data-ax-path")) {
                        if ($(this).attr($(this).attr("data-ax-path")))
                            data[$(this).attr("data-ax-path")] = $(this).attr($(this).attr("data-ax-path"));
                        else
                            data[$(this).attr("data-ax-path")] = $(this).val();
                    }
                });
                // retData.push(data);
                if(data["relAuthorityUuid"] && data["relAuthorityUuid"] != ""
                    && data["relationTypeUuid"] && data["relationTypeUuid"] != "" ){
                    retData.push(data);
                }
            });
        }
        return retData;
    },
    setData : function(data){
        if(data != null && data != "undefined" && data.length > 0){
            for (var i=0;i<data.length;i++){
                this.addChild($("#addAuthInfo"),data[i]);
            }
        }
    }
});
