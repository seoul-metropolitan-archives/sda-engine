var fnObj = {};
var option = '';
var authorityTypeUuid = '';
var authorityTypeNm = '';
var ORG_TYPE_NM = "Organization";
var relAuthInfoValid = true;
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SAVE: function (caller, act, data) {
        //validate
        var authorityName = fnObj.formView.getData().authorityName;
        var orgTypeUuid = fnObj.formView.getData().orgTypeUuid;

        if(authorityName == undefined || authorityName.trim() == ""){
            return;
        }

        if(authorityTypeNm == ORG_TYPE_NM){
            if(orgTypeUuid == undefined || orgTypeUuid.trim() == ""){
                return;
            }
            if(fnObj.formView.getData().mainJob01 != undefined){
                fnObj.formView.setFormData("mainJob",fnObj.formView.getData().mainJob01);
            }
        }else{
            if(fnObj.formView.getData().mainJob02 != undefined){
                fnObj.formView.setFormData("mainJob",fnObj.formView.getData().mainJob02);
            }
        }

        if(!relAuthInfoValid){
            return;
        }

        var saveData  = {
            relAuthList : fnObj.childrenAuthInfo.getData(),
            authInfo : $.extend({}, fnObj.formView.getData(), {authorityTypeUuid:authorityTypeUuid})
        }
        axboot.ajax({
            type: "PUT",
            url: "/api/v1/at/at001/01/save",
            data: JSON.stringify($.extend({}, saveData)),
            callback: function (res) {
                if(res.message == "SUCCESS"){
                    ACTIONS.dispatch(ACTIONS.PAGE_CLOSE,res.message);
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.callback(data);
        }
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
    _this.childrenAuthInfo.initView();
};
fnObj.formView = axboot.viewExtend(axboot.formView, {
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시

        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        // $("input[data-ax-path='descriptionDate']").val(getFormattedDate(new Date()));
        fnObj.formView.setFormData("descriptionDate",getFormattedDate(new Date()));
        this.makeRadio();
        this.initEvent();
    },
    initEvent: function(){
        $('input:radio[name="radio"]').change(function(){
            authorityTypeUuid = event.currentTarget.value;
            fnObj.formView.changeView();
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
            if(codes[i] == parent.axboot.modal.getData().authorityTypeUuid){
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
    changeView: function () {
        var selected = $('input:radio[name="radio"]:checked').attr("id");
        var selectedLabel = $("label[for='"+selected+"']").text();
        authorityTypeNm = selectedLabel;
        if(selectedLabel == ORG_TYPE_NM){
            $("#organizationArea").show();
            $("#etcArea").hide();
        }else{
            $("#organizationArea").hide();
            $("#etcArea").show();
        }

        fnObj.formView.clear();
        $(".auth_fit").remove();
        fnObj.childrenAuthInfo.addChild($("#addDnrInfo"));
        fnObj.formView.setFormData("descriptionDate",getFormattedDate(new Date()));
    }
});
fnObj.childrenAuthInfo = axboot.viewExtend({
    targetTag  : $("#childrenAuthInfoArea"),
    parentUuid : "",
    nodeType : "",
    popupCode : "",
    template :
    "                                                            <li style='padding: 0 10px 0 0;'>" +
    "                                                               <select data-ax-path='relationTypeUuid' style='width:135px;'>" +
    "                                                                   <option value='' disabled selected>관련기관</option>" +
    "                                                                </select>" +
    "                                                            </li>" +
    "                                                            <li style='padding: 0 10px 0 0;'>" +
    "                                                               <div class='src_box2' style='width: 600px!important;'>" +
    "                                                                <input type=text data-ax-path='relAuthorityName' style='width: 600px!important' disabled class='form-control' placeholder='관련전거'>" +
    "                                                                <input type=text data-ax-path='relAuthorityUuid' style='width: 0; display: none'>" +
        "                                                                <a href='#' class='searchAuthority' ><img src='/assets/images/ams/search_normal.png' alt='find'></a>" +
        "                                                            </div>" +
    "                                                            </li>" +
    "                                                            <li style='text-align: center'>" +
    "                                                                <div><a href='#' class='btn_del_left' style=''>X</a></div>" +
    "                                                          </li>",
    initView: function () {
        var _this = this;
        _this.initEvent();

        var codes = axboot.commonCodeFilter("CD162").codeArr;
        var names = axboot.commonCodeFilter("CD162").nameArr;
        for(var i = 0; i < codes.length; i++)
        {
            option += '<option value="'+ codes[i] + '">' + names[i] + '</option>';

        }

        _this.addChild($("#addAuthInfo"));
    },
    initEvent: function () {
        var _this = this;
        $("#addAuthInfo").click(function(){
            _this.addChild(this);
        });

        $("#childrenAuthInfoArea").on("change",".cntPrsn",function(){
            $(this).parents().eq(2).find("input[data-ax-path='__modified__']").val(true);
        });

        $("#childrenAuthInfoArea").delegate(".searchAuthority","click",function(){
            var parentsTag  = $(this).parents().eq(2);
            var data = {
                popupCode : "PU142",
                preSearch : false,
                searchData : ' ',
                callback : function(data){
                    parentsTag.find("input[data-ax-path='relAuthorityUuid']").val(data["AUTHORITY_UUID"])
                    parentsTag.find("input[data-ax-path='relAuthorityName']").val("[" + data["AUTHORITY_NO"] +"] " + data["AUTHORITY_NAME"])
                }
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_AUTH_INFO,data);
        });

        $(".childDnrInfo").delegate(".btn_del_left","click",function(){
                $(this).parents().eq(2).remove();
        });
    },
    addChild : function(_this,data){
        var cloneTag = $("<ul>").addClass("auth_fit").attr("data-ax-path","__created__").html(this.template).clone();
        cloneTag.find("select").append($(option))
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
                $(this).children("li").find("input,select,textarea").each(function(){

                    if($(this).attr("data-ax-path")) {
                        if ($(this).attr($(this).attr("data-ax-path")))
                            data[$(this).attr("data-ax-path")] = $(this).attr($(this).attr("data-ax-path"));
                        else
                            data[$(this).attr("data-ax-path")] = $(this).val();
                    }

                });
                if(data["relAuthorityUuid"] != "" && data["relationTypeUuid"] != null){
                    retData.push(data);
                }else if(data["relAuthorityUuid"] == "" && data["relationTypeUuid"] == null){

                }else{
                    authInfoValid = false;
                }

            });
        }
        return retData;
    },
    setData : function(data){
        if(data != null && data != "undefined"){
            for (var i=0;i<data.length;i++){
                this.addChild($("#addDnrInfo"),data[i]);
            }
        }
    }
});
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