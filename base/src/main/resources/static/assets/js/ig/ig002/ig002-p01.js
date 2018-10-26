var fnObj = {};
var getMenu = function(searchData)
{
    var menuObj = undefined;
    axboot.ajax({
        url: "/rc/rc001/getMenuInfo",
        data: JSON.stringify({progNm : searchData}),
        type : "POST",
        dataType : "JSON",
        async : false,
        callback: function (res) {
            menuObj = res;
        },
        options: {
            onError: axboot.viewError
        }
    });
    return menuObj;
}

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        if(data != null && data.accessionRecordUuid != null){
            axboot.ajax({
                type: "GET",
                url: "/api/v1/ig/ig002/01/list02",
                data: $.extend({}, {accessionRecordUuid:data.accessionRecordUuid}),
                callback: function (res) {
                    if(res != null && res != "undefined")
                    fnObj.formView.setData(res.accessionRecord);
                    fnObj.childrenDrnInfo.setData(res.childrenDrnInfoList);
                    fnObj.childrenMngInfo.setData(res.childrenMngInfoList);
                },
                options: {
                    onError: axboot.viewError
                }
            });
        }
    },
    PAGE_SEARCH_ACCNO: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ig/ig002/01/list01",
            callback: function (res) {
                if(res.accessionNo != "undefined" && res.accessionNo != "")
                    fnObj.formView.setFormData("accessionNo",res.accessionNo);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SAVE: function (caller, act, data) {
        var saveData  = {
            childrenDrnInfoList : fnObj.childrenDrnInfo.getData(),
            childrenMngInfoList :fnObj.childrenMngInfo.getData(),
            accessionRecord : fnObj.formView.getData()
        }

        axboot.ajax({
            url: "/api/v1/ig/ig002/02/save01",
            dataType : "JSON",
            type : "POST",
            data: JSON.stringify(saveData),
            callback: function (res) {
                if(res.message == "SUCCESS"){
                    axToast.push(axboot.getCommonMessage("AA007"));
                    ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
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

    _this.formView.initView();
    _this.childrenDrnInfo.initView();
    _this.childrenMngInfo.initView();
};
//=================================================================
//작업영역
//=================================================================

fnObj.formView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {useYn: ""});
    },
    initView: function (data) {
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

        var today = new Date();
        $("input[data-ax-path='acquisitionDate']").val(ax5.util.date(today, {"return": "yyyy-MM-dd"}));
        this.setFormData('acquisitionDate',ax5.util.date(today, {"return": "yyyy-MM-dd"}));
        // this.initEvent();
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

fnObj.childrenDrnInfo = axboot.viewExtend({
    targetTag  : $("#childrenDnrInfoArea"),
    parentUuid : "",
    nodeType : "",
    popupCode : "",
    template :
    "                                                            <li style='width: 11%;'>" +
    "                                                                <b>Contact Person</b>" +
    "                                                            <div class='src_box2'>" +
    "                                                                <input type=text data-ax-path='name' readonly class='cntPrsn'>" +
    "                                                                <input type=text data-ax-path='accessionRecordEtcUuid' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='accessionRecordUuid' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='authorityUuid' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='typeUuid' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='__created__' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='__deleted__' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='__modified__' style='width: 0px'>" +
    "                                                                <a href='#' style='top: 0' class='searchAggregation' ><img src='/assets/images/ams/search_normal.png' alt='find'></a>" +
    "                                                            </div>" +
    "                                                            </li>" +
    "                                                            <li style='width: 11%; padding: 0 0.5%;'>" +
    "                                                                <b>Tel </b>" +
    "                                                                <div>" +
    "                                                                    <input type='text' data-ax-path='tel' class='cntPrsn' style='width: 100%;'>" +
    "                                                                </div>" +
    "                                                            </li>" +
    "                                                            <li style='width: 75%;'>" +
    "                                                                <b>Address </b>" +
    "                                                                <div>" +
    "                                                                    <input type='text' data-ax-path='address' class='cntPrsn' style='width: 100%;'>" +
    "                                                                </div>" +
    "                                                            </li>" +
    "" +
    "                                                            <li style='width: 2%; text-align: center'>" +
    "                                                                <b>&nbsp; </b>" +
    "                                                                <div><a href='#' class='btn_del' style=''>X</a></div>" +
    "                                                            </li>",
    initView: function () {
        this.initEvent();
        this.addChild($("#addDnrInfo"));

    },
    initEvent: function () {
        var _this = this;
        $("#addDnrInfo").click(function(){
            _this.addChild(this);
        });

        $("#childrenDnrInfoArea").on("change",".cntPrsn",function(){
            $(this).parents().eq(2).find("input[data-ax-path='__modified__']").val(true);
        });

        $("#childrenDnrInfoArea").delegate(".searchAggregation","click",function(){
            var parentsTag  = $(this).parents().eq(2);
            axboot.modal.open({
                modalType: "AUTHORITY_POPUP",
                header: {
                    title: "Authority List"
                },
                sendData: function () {
                    return {
                    };
                },
                callback: function (data) {
                    if(this) this.close();
                    if(data){
                        parentsTag.find("input[data-ax-path='authorityUuid']").val(data["authorityUuid"]);
                        parentsTag.find("input[data-ax-path='name']").val(data["authorityName"]);
                        if("saved" == parentsTag.attr("saveType")){
                            parentsTag.find("input[data-ax-path='__modified__']").val(true);
                        }
                    }
                }
            });
        });

        $(".childDnrInfo").delegate(".btn_del","click",function(){
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
        if(data != null && data != undefined){
            cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","saveType").attr("saveType","saved").html(this.template);
            cloneTag.find("input[data-ax-path='name']").val(data["name"]);
            cloneTag.find("input[data-ax-path='tel']").val(data["tel"]);
            cloneTag.find("input[data-ax-path='address']").val(data["address"]);
            cloneTag.find("input[data-ax-path='authorityUuid']").val(data["authorityUuid"]);
            cloneTag.find("input[data-ax-path='accessionRecordEtcUuid']").val(data["accessionRecordEtcUuid"]);
            cloneTag.find("input[data-ax-path='accessionRecordUuid']").val(data["accessionRecordUuid"]);
            cloneTag.find("input[data-ax-path='typeUuid']").val(data["typeUuid"]);
            cloneTag.find("input[data-ax-path='__created__']").val(data["__created__"]);
            cloneTag.find("input[data-ax-path='__deleted__']").val(data["__deleted__"]);
            cloneTag.find("input[data-ax-path='__modified__']").val(data["__modified__"]);
            type = "saved";
        }else{
            cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","saveType").attr("saveType","create").html(this.template);
            cloneTag.find("input[data-ax-path='__created__']").val(true);
        }
        $(_this).before(cloneTag);
        cloneTag.show();
    },
    getData : function(){
        var retData = new Array();
        var data = {};
        if(this.targetTag.css("display") != "none")
        {
            $(this.targetTag).find(".childDnrInfo>ul:not(#addDnrInfo)").each(function(){
                data = {};
                data["saveType"] = $(this).attr("saveType");
                $(this).children("li").find("input,select,textarea").each(function(){

                    if($(this).attr("data-ax-path")) {
                        if ($(this).attr($(this).attr("data-ax-path")))
                            data[$(this).attr("data-ax-path")] = $(this).attr($(this).attr("data-ax-path"));
                        else
                            data[$(this).attr("data-ax-path")] = $(this).val();
                    }

                });
                if(data["name"] && data["name"] != "")
                    retData.push(data);
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

fnObj.childrenMngInfo = axboot.viewExtend({
    targetTag  : $("#childrenMngInfoArea"),
    parentUuid : "",
    nodeType : "",
    popupCode : "",
    template :
    "                                                            <li>" +
    "                                                                <b>Creator</b>" +
    "                                                            <div class='src_box2'>" +
    "                                                                <input type=text data-ax-path='name' class='crtr' readonly>" +
    "                                                                <input type=text data-ax-path='accessionRecordEtcUuid' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='accessionRecordUuid' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='authorityUuid' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='typeUuid' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='tel' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='address' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='__created__' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='__deleted__' style='width: 0px'>" +
    "                                                                <input type=text data-ax-path='__modified__' style='width: 0px'>" +
    "                                                                <a href='#' style='top: 0' class='searchAggregation' ><img src='/assets/images/ams/search_normal.png' alt='find'></a>" +
    "                                                            </div>" +
    "                                                            </li>" +
    "" +
    "                                                            <li style='width: 100%;' text-align: left>" +
    "                                                                <b>&nbsp; </b>" +
    "                                                                <div><a href='#' class='btn_del' style=''>X</a></div>" +
    "                                                            </li>",
    initView: function () {
        this.initEvent();
        this.addChild($("#addMngInfo"));

    },
    initEvent: function () {
        var _this = this;
        //add aggregation
        $("#addMngInfo").click(function(){
            _this.addChild(this);
        });

        $("#childrenDnrInfoArea").on("change",".crtr",function(){
            $(this).parents().eq(2).find("input[data-ax-path='__modified__']").val(true);
        });

        $("#childrenMngInfoArea").delegate(".searchAggregation","click",function(){
            var parentsTag  = $(this).parents().eq(2);
            axboot.modal.open({
                modalType: "AUTHORITY_POPUP",
                header: {
                    title: "Authority List"
                },
                sendData: function () {
                    return {
                    };
                },
                callback: function (data) {
                    if(this) this.close();
                    if(data){
                        parentsTag.find("input[data-ax-path='authorityUuid']").val(data["authorityUuid"]);
                        parentsTag.find("input[data-ax-path='name']").val(data["authorityName"]);
                        if("saved" == parentsTag.attr("saveType")){
                            parentsTag.find("input[data-ax-path='__modified__']").val(true);
                        }
                    }
                }
            });
        });

        $(".childMngInfo").delegate(".btn_del","click",function(){
            if("create" == $(this).parents().eq(2).attr("type"))
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
        if(data != null && data != undefined){
            cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","saveType").attr("saveType","saved").html(this.template);
            cloneTag.find("input[data-ax-path='name']").val(data["name"]);
            cloneTag.find("input[data-ax-path='tel']").val(data["tel"]);
            cloneTag.find("input[data-ax-path='address']").val(data["address"]);
            cloneTag.find("input[data-ax-path='authorityUuid']").val(data["authorityUuid"]);
            cloneTag.find("input[data-ax-path='accessionRecordEtcUuid']").val(data["accessionRecordEtcUuid"]);
            cloneTag.find("input[data-ax-path='accessionRecordUuid']").val(data["accessionRecordUuid"]);
            cloneTag.find("input[data-ax-path='typeUuid']").val(data["typeUuid"]);
            cloneTag.find("input[data-ax-path='__created__']").val(data["__created__"]);
            cloneTag.find("input[data-ax-path='__deleted__']").val(data["__deleted__"]);
            cloneTag.find("input[data-ax-path='__modified__']").val(data["__modified__"]);
            type = "saved";
        }else{
            cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","saveType").attr("saveType","create").html(this.template);
            cloneTag.find("input[data-ax-path='__created__']").val(true);
        }
        $(_this).before(cloneTag);
        cloneTag.show();
    },
    getData : function(){
        var retData = new Array();
        var data = {};
        if(this.targetTag.css("display") != "none")
        {
            $(this.targetTag).find(".childMngInfo>ul:not(#addMngInfo)").each(function(){
                data = {};
                data["saveType"] = $(this).attr("saveType");
                $(this).children("li").find("input,select,textarea").each(function(){

                    if($(this).attr("data-ax-path")) {
                        if ($(this).attr($(this).attr("data-ax-path")))
                            data[$(this).attr("data-ax-path")] = $(this).attr($(this).attr("data-ax-path"));
                        else
                            data[$(this).attr("data-ax-path")] = $(this).val();
                    }

                });
                if(data["name"] && data["name"] != "")
                    retData.push(data);
            });
        }
        return retData;
    },
    setData : function (data) {
        if(data != null && data != "undefined"){
            for (var i=0;i<data.length;i++){
                this.addChild($("#addMngInfo"),data[i]);

            }
        }
    }
});
setFormData = function(data){

    console.log(data);
    for(var columnName in data)
    {
        switch(columnName)
        {
            case "name":
                fnObj.formView.setFormData("title",data[columnName]);
                break;
            case "descriptionStartDate":
            case "descriptionEndDate":
            case "creationStartDate":
            case "creationEndDate":
                $("input[data-ax-path='"+columnName+"']").val(getFormattedDate(data[columnName]));
                break;
            default:
                fnObj.formView.setFormData(columnName,data[columnName]);
                break;
        }
    }
}

function getFormattedDate(str) {
    if(str == "undefined" || str == null) return;
    if(str.length == 8) {
        return str.substr(0, 4) + "-" + str.substr(4, 2) + "-" + str.substr(6);
    } else {
        return str;
    }
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

    if (chk1.test(strValue) == false && chk2.test(strValue) == false) { // 유효성 검사에 둘다 성공하지 못했다면
        //alert("1999-1-1 형식 또는 \r\n1999-01-01 형식으로 날자를 입력해주세요.");
        axToast.push(axboot.getCommonMessage("AA011"));
        result = false;

    }
    return result;
}