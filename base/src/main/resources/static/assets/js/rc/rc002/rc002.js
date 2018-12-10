var fnObj = {};

var option = '';
var authorityTypeUuid = '';
var isChanged = false;
var currentData = null;
const ENTITY_TYPE = "RC_AGGREGATION_CON";

$( function() {
    var icons = {
        header: "ui-icon-circle-arrow-e",
        activeHeader: "ui-icon-circle-arrow-s"
    };
    $('.detail_wrap').accordion({
        icons: icons,
        collapsible: true,
        heightStyle: "content",
        //onlyStyle : true,
    });

    //$( ".detail_wrap" ).accordion( "destroy" );

});

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/rc003/01/list",
            data: $.extend({}, {pageSize: 1000}, data

                //{aggregationUuid :'A2EF15E7-BE58-41DD-945C-E99FB5DE60C1'}
            ),
            callback: function (res) {
                if(res.list != "undefined" && res.list != null && res.list.length > 0){
                    rcList = ax5.util.deepCopy(res.list);
                    setFormData(rcList[0]);
                    ACTIONS.dispatch(ACTIONS.GET_META_TEMPLATE, rcList[0].addMetaTemplateSetUuid);
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    GET_TREE : function(caller, act, data)
    {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/rc/rc002/getTreeData",
            data: $.extend({},data),
            callback: function (res) {
                console.log(res);
                fnObj.treeView01.setData({},res.list,data);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    SEARCH_AGGREGATION: function (caller, act, data) {
        var callback = data["callback"];
        var reqData = ax5.util.deepCopy(data);
        delete(reqData["callback"]);
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : reqData["preSearch"],
            sendData: function () {
                return reqData;
            },
            callback: function (data) {
                callback(data);
            }
        });
    },
    SEARCH_ITEM: function (caller, act, data) {
        var callback = data["callback"];
        var reqData = ax5.util.deepCopy(data);
        delete(reqData["callback"]);
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : reqData["preSearch"],
            sendData: function () {
                return reqData;
            },
            callback: function (data) {
                callback(data);
            }
        });
    },
    PAGE_SAVE: function (caller, act, data) {
        var saveData  =
            {
                systemMeta : fnObj.identificationArea.getData(),
                contextualMeta :fnObj.formView.getData(),
                creatorList : fnObj.authorityInfo.getData("creatorArea"),
                relatedAuthorityList : fnObj.authorityInfo.getData("authorityArea"),
                childrenAggregationList : fnObj.childrenAggre.getData(),
                referenceAggregationList : fnObj.referenceAggre.getData(),
                referenceItemList : fnObj.referenceItem.getData()
            }

        axboot.ajax({
            url: "/api/v1/rc/rc002/save",
            dataType : "JSON",
            type : "POST",
            data: JSON.stringify(saveData),
            callback: function (res) {
                axToast.push(axboot.getCommonMessage("AA007"));
            },
            options: {
                onError: axboot.viewError
            }
        });

    },
    GET_META_TEMPLATE: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ad/ad007/listSub",
            data: $.extend({}, {pageSize: 1000}, {addMetaTemplateSetUuid : data, entityType : ENTITY_TYPE}),
            callback: function (res) {
                setAdditionalMeta(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
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

    // TODO 추후에 삭제될 내용으로 /실제 Grid의 컬럼 정보는 DB에서 가져올 예정
    $.ajax({
        url: "/assets/js/column_info/rc00301.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });


    // Data 조회
    var data = axboot.getMenuParams();
    console.log(data);

    var uuid = "";
    if(null != data && data.type == "create")
    {
        if(data["navi"])
        {
            $("#navigatorArea").text(data["navi"]);
            data["parentUuid"] = data["uuid"];
        }
    }
    else if(null != data && data.type != "create") {
        uuid = data.uuid;
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,{aggregationUuid : data.uuid});
        if(data["navi"])
        {
            $("#navigatorArea").text(data["navi"]);
        }
        if(data["title"])
        {
            $("#title").text(data["title"]);
        }
        if(data["uuid"]){
            ACTIONS.dispatch(ACTIONS.GET_TREE,data);
        }
    }

    _this.formView.initView();
    _this.treeView01.initView();

    _this.authorityInfo.initView();


    if(null != data)
    {
        _this.identificationArea.nodeType = undefined == data["nodeType"]? data["type"] : data["nodeType"];
        _this.childrenAggre.nodeType = undefined == data["nodeType"]? data["type"] : data["nodeType"];
        _this.referenceAggre.nodeType = undefined == data["nodeType"]? data["type"] : data["nodeType"];
    }
    _this.identificationArea.initView(uuid);

    if(null != data && data["parentUuid"])
    {
        fnObj.formView.setFormData("parentAggregationUuid", data["parentUuid"]);
        //fnObj.identificationArea.setFrom(data["parentUuid"],true);
    }

    _this.contentsStructureArea.initView(uuid);
    _this.childrenAggre.initView(uuid);
    _this.referenceItem.initView(uuid);
    _this.referenceAggre.initView(uuid);

    if(data != null && data.hasOwnProperty("nodeType") && data.nodeType=="virtual")
    {
        $("#referenceAggreAreaTitle,#referenceItemAreaTitle").show();
        $("#childrenAggreAreaTitle").hide();
    }
    else
    {
        $("#referenceAggreAreaTitle,#referenceItemAreaTitle").hide();
        $("#childrenAggreAreaTitle").show();
    }
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
            config: {
                pattern: 'data'
            },
            content: {
                type: 'date',
                formatter: {
                    pattern: 'number'
                }
            },
        });

        this.initEvent();
    },
    initEvent: function () {
        var _this = this;
        //by the Aggregation type, to control the Reference Area
        /*$("input[data-ax-path='descriptionStartDate']").keyup(function (event) {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });

        $("input[data-ax-path='descriptionStartDate']").keypress(function (event) {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });

        $("input[data-ax-path='descriptionStartDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });

        $("input[data-ax-path='descriptionEndDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });

        $("input[data-ax-path='descriptionEndDate']").keypress(function (event) {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });

        $("input[data-ax-path='descriptionEndDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });

        $("input[data-ax-path='creationStartDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });

        $("input[data-ax-path='creationStartDate']").keypress(function (event) {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });

        $("input[data-ax-path='creationStartDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });

        $("input[data-ax-path='creationEndDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });

        $("input[data-ax-path='creationEndDate']").keypress(function (event) {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });

        $("input[data-ax-path='creationEndDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });*/


        $('.togl01').click(function () {
            $(".togl01_show").toggle();
            if ($('#open_btn1').val() == '▼') {
                $('#open_btn1').val('◀');
            } else {
                $('#open_btn1').val('▼');
            }
        });

        $('.togl02').click(function () {
            $(".togl02_show").toggle();
            if ($('#open_btn2').val() == '▼') {
                $('#open_btn2').val('◀');
            } else {
                $('#open_btn2').val('▼');
            }
        });

        $('.togl03').click(function () {
            $(".togl03_show").toggle();
            if ($('#open_btn3').val() == '▼') {
                $('#open_btn3').val('◀');
            } else {
                $('#open_btn3').val('▼');
            }
        });

        $("[data-ax-path='addMetaTemplateSetUuid']").on("change", function(){
            ACTIONS.dispatch(ACTIONS.GET_META_TEMPLATE, $("[data-ax-path='addMetaTemplateSetUuid']").val());
            isDetailChanged = true;
        });
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.

        data["creationStartDate"] = $("input[data-ax-path='creationStartDate']").val().replace(/-/gi,"");
        data["creationEndDate"] = $("input[data-ax-path='creationEndDate']").val().replace(/-/gi,"");

        data["accumulationStartDate"] = $("input[data-ax-path='accumulationStartDate']").val().replace(/-/gi,"");
        data["accumulationEndDate"] = $("input[data-ax-path='accumulationEndDate']").val().replace(/-/gi,"");

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
//System Meta Object
fnObj.identificationArea = axboot.viewExtend({
    targetTag : $("#identificationArea"),
    aggregationUuid : "",
    nodeType : "",
    popupCode : "",
    initView : function(aggregationUuid){
        var _this = this;
        this.initEvent();
        this.aggregationUuid = aggregationUuid;

        if(this.nodeType.toLowerCase() == "virtual")
            this.popupCode = "PU121"
        else
            this.popupCode = "PU123"

        setTimeout(function(){
            $("select[data-ax-path='typeUuid']>option").each(function(){
                if($(this).text().toLowerCase() == _this.nodeType)
                {
                    $(this).prop("selected","selected");
                    $("select[data-ax-path='typeUuid']").css("background-color","");
                    $("select[data-ax-path='typeUuid']").prop("disabled","disabled")
                }
            });

        },50)


    },
    initEvent : function(){
        var _this = this;
        $("select[data-ax-path='typeUuid']").change(function(){
            fnObj.referenceAggre.nodeType = $(this).find("option:selected").text();
            fnObj.referenceAggre.setPopupCode();
            if($(this).find("option:selected").text().toLowerCase()=="virtual")
            {
                $("#referenceAggreArea,#referenceItemArea").show();
                $("#childrenAggreArea").hide();
            }
            else
            {
                $("#referenceAggreArea,#referenceItemArea").hide();
                $("#childrenAggreArea").show();
            }
        });
        $("input[data-ax-path='parentAggregationUuid']").blur(function(){
            var _thisObj = this;
            _this.setFrom($(this).parents().eq(0).find("input[data-ax-path='parentAggregationUuid']").val());
        });

        $("#fromAggregation").click(function(){
            var _thisObj = this;
            _this.setFrom($(this).parents().eq(0).find("input[data-ax-path='parentAggregationUuid']").val());
        });


    },
    setFrom : function(data,preSearch){
        var data = {
            popupCode : this.popupCode,
            preSearch : undefined == preSearch ? false : true,
            searchData : data,
            callback : function(data){
                var target = $("#fromAggregation").parents().eq(0).find("input[data-ax-path='parentAggregationUuid']");
                target.attr("parentAggregationUuid",data["AGGREGATION_UUID"]);
                target.val(data["TITLE"]);
                console.log(data);
            }
        };
        ACTIONS.dispatch(ACTIONS.SEARCH_AGGREGATION,data);
    },
    getData : function(){
        var data = {};

        data = {};
        data["aggregationUuid"] = this.aggregationUuid;
        $(this.targetTag).find("ul").each(function(){
            $(this).children("li").find("input,select,textarea").each(function(){
                if($(this).attr("data-ax-path"))
                {
                    if($(this).attr($(this).attr("data-ax-path")))
                        data[$(this).attr("data-ax-path")] = $(this).attr($(this).attr("data-ax-path"));
                    else
                        data[$(this).attr("data-ax-path")] = $(this).val();
                }
            });
        });

        data["descriptionStartDate"] = $("input[data-ax-path='descriptionStartDate']").val().replace(/-/gi,"");
        data["descriptionEndDate"] = $("input[data-ax-path='descriptionEndDate']").val().replace(/-/gi,"");

        data["author"] = $("input[data-ax-path='author']").val();
        data["description"] = $("textarea[data-ax-path='description']").val();
        data["notes"] = $("textarea[data-ax-path='notes']").val();

        data["languageCode"] = $("select[data-ax-path='languageCode']").val();
        data["statusDescription"] = $("select[data-ax-path='statusDescription']").val();
        data["levelOfDetailUuid"] = $("select[data-ax-path='levelOfDetailUuid']").val();

        return data;
    }
});
//Contextual Meta Object
fnObj.contentsStructureArea = axboot.viewExtend({
    targetTag : $("#contentsStructureArea"),
    aggregationUuid : "",
    initView : function(aggregationUuid){
        this.initEvent();
        this.aggregationUuid = aggregationUuid;
        console.log(aggregationUuid);
    },
    initEvent : function(){

    },
    getData : function(){
        var data = {};
        data["aggregationUuid"] = this.aggregationUuid;
        $(this.targetTag).find("ul").each(function(){
            $(this).children("li").find("input,select,textarea").each(function(){
                if($(this).attr("data-ax-path"))
                {
                    if($(this).attr($(this).attr("data-ax-path")))
                        data[$(this).attr("data-ax-path")] = $(this).attr($(this).attr("data-ax-path"));
                    else
                        data[$(this).attr("data-ax-path")] = $(this).val();
                }
            });
        });

        return data;
    }
});
/**
 * 전거데이터 영역
 */
fnObj.authorityInfo = axboot.viewExtend({
    parentUuid : "",
    nodeType : "",
    popupCode : "",
    template :
    "                                                            <input type=hidden data-ax-path='__created__'>" +"                                                            " +
    "                                                            <input type=hidden data-ax-path='__deleted__'>" +
    "                                                            <input type=hidden data-ax-path='__modified__'>" +
    "                                                            <input type=hidden data-ax-path='aggAuthorityUuid'>" +
    "                                                            <li style='padding: 0 10px 0 0;'>" +
    "                                                               <div class='src_box2'>" +
    "                                                                   <input type=text data-ax-path='authorityName'  readonly class='form-control' placeholder='관련전거' style='border-radius: 0px'>" +
    "                                                                   <input type=hidden data-ax-path='authorityUuid' class='authorityUuid' >" +
    "                                                                   <a href='#' class='searchAuthority' ><img src='/assets/images/ams/search_normal.png' alt='find'></a>" +
    "                                                               </div>" +
    "                                                            </li>" +
    "                                                            <li style='text-align: center'><a href='#' class='btn_del_left' style=''>X</a></li>",
    initView: function () {
        this.initEvent();
    },
    initEvent: function () {
        var _this = this;
        $("#creatorArea, #authorityArea").on("click", ".addAuthoritiesInfo >li a", function(event){
            _this.addChild($(event.delegateTarget).find(".childDnrInfo"));
        });

        $(".serv_cont").on("change",".authorityUuid",function(){
            if("saved" == $(this).parents().eq(2).attr("saveType"))
            {
                $(this).parents().eq(2).find("input[data-ax-path='__modified__']").val(true);
                isChanged = true;
            }
        });

        $("#creatorArea, #authorityArea, #repositoriesArea, #sourceAcquisitionArea").on("click",".searchAuthority",function(event){
            var parentTarget = $(event.target).parents().eq(2);
            var delegateTarget = $(event.delegateTarget);
            axboot.modal.open({
                modalType: "AUTHORITY_POPUP",
                header: {
                    title: "Authority List"
                },
                sendData: function () {
                    return null;
                },
                callback: function (data) {
                    if(this) this.close();
                    if(data){
                        parentTarget.find("input[data-ax-path='authorityUuid']").val(data["authorityUuid"]);
                        parentTarget.find("input[data-ax-path='authorityName']").val(data["authorityName"]);

                        if(delegateTarget.attr("id") == "repositoriesArea") {
                            fnObj.formView.setFormData("repositoriesUuid", data["authorityUuid"]);
                            fnObj.formView.setFormData("repositoriesName", data["authorityName"]);
                        }

                        if(delegateTarget.attr("id") == "sourceAcquisitionArea") {
                            fnObj.formView.setFormData("sourceAcquisitionUuid", data["authorityUuid"]);
                            fnObj.formView.setFormData("sourceAcquisitionName", data["authorityName"]);
                        }

                        parentTarget.find("input[data-ax-path='authorityUuid']").trigger('change');
                    }
                }
            });
        });


        $("#creatorArea, #authorityArea, #repositoriesArea, #sourceAcquisitionArea").on("click",".btn_del_left",function(event){
            var delegateTarget = $(event.delegateTarget);

            if(delegateTarget.attr("id") == "repositoriesArea") {
                fnObj.formView.setFormData("repositoriesUuid", "");
                fnObj.formView.setFormData("repositoriesName", "");
                return;
            }

            if(delegateTarget.attr("id") == "sourceAcquisitionArea") {
                fnObj.formView.setFormData("sourceAcquisitionUuid", "");
                fnObj.formView.setFormData("sourceAcquisitionName", "");
                return;
            }

            if("create" == $(this).parents().eq(1).attr("saveType"))
            {
                $(this).parents().eq(1).remove();
            }
            else
            {
                $(this).parents().eq(1).hide();
                $(this).parents().eq(1).find("input[data-ax-path='__deleted__']").val(true);
                $(this).parents().eq(1).find("input[data-ax-path='__modified__']").val(false);
            }
        });
    },
    addChild : function(_this,data){
        var cloneTag = "";
        if(data != null && data != undefined) {
            cloneTag = $("<ul style='margin: 0px 5px 10px 0px;'>").addClass("auth_fit").attr("data-ax-path", "saveType").attr("saveType", "saved").html(fnObj.authorityInfo.template).clone();
            cloneTag.find("input[data-ax-path='aggAuthorityUuid']").val(data["aggAuthorityUuid"]);
            cloneTag.find("input[data-ax-path='authorityName']").val(data["authorityName"]);
            cloneTag.find("input[data-ax-path='authorityUuid']").val(data["authorityUuid"]);
            cloneTag.find("input[data-ax-path='__created__']").val(data["__created__"]);
            cloneTag.find("input[data-ax-path='__deleted__']").val(data["__deleted__"]);
            cloneTag.find("input[data-ax-path='__modified__']").val(data["__modified__"]);
        }else{
            cloneTag = $("<ul style='margin: 0px 5px 10px 0px;'>").addClass("auth_fit").attr("data-ax-path","saveType").attr("saveType","created").html(fnObj.authorityInfo.template).clone();
            cloneTag.find("input[data-ax-path='__created__']").val(true);
        }
        $(_this).before(cloneTag);
        cloneTag.show();
    },
    getData : function(target){
        var retData = new Array();
        var data = {};
        var targetTag = $("#"+target);

        if(targetTag.css("display") != "none")
        {
            targetTag.find(">ul:not(.addAuthoritiesInfo)").each(function(){
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

                if(target == "creatorArea"){
                    data["aggregationCreatorUuid"] = data["aggAuthorityUuid"];
                    data["creatorUuid"] = data["authorityUuid"];
                }else if(target == "authorityArea"){
                    data["aggrRelatedAuthorityUuid"] = data["aggAuthorityUuid"];
                }

                // retData.push(data);
                if(data["authorityUuid"] && data["authorityUuid"] != "" ){
                    retData.push(data);
                }
            });
        }
        return retData;
    },
    setData : function(target, data){
        $("#" + target).remove(".auth_fit");

        if(data != null && data != "undefined" && data.length > 0){
            data.forEach(function(item, index){
                if(target == "creatorArea"){
                    item["aggAuthorityUuid"] = item["aggregationCreatorUuid"];
                    item["authorityUuid"] = item["creatorUuid"];
                    item["authorityName"] = item["creatorName"];
                }else if(target == "authorityArea"){
                    item["aggAuthorityUuid"] = item["aggrRelatedAuthorityUuid"];
                }
                fnObj.authorityInfo.addChild($("#" + target).find(".childDnrInfo"), item);
            });
        }
    }
});




fnObj.childrenAggre = axboot.viewExtend({
    targetTag  : $("#childrenAggreArea"),
    parentUuid : "",
    nodeType : "",
    popupCode : "",
    template :
    "                                                            <li style='width: 11%;'>" +
    "                                                                <b>Aggregation Code</b>" +
    "                                                                <div>" +
    "                                                                    <input type='text' data-ax-path='aggregationCode' style='width: 135px;' class='bgf7' readonly>" +
    "                                                                </div>" +
    "                                                            </li>" +
    "                                                            <li style='width: 75%; padding: 0 0.5%;'>" +
    "                                                                <b>Title </b>" +
    "                                                                <div>" +
    "                                                                    <input type='text' data-ax-path='title' style='width: 100%;  background-color: #fffdd6; '>" +
    "                                                                </div>" +
    "                                                            </li>" +
    "                                                            <li style='width: 11%;'>" +
    "                                                                <b>Level </b>" +
    "                                                                <div>" +
    "                                                                    <select data-ax-path='levelUuid' style='width: 135px' >" +
    "                                                                    </select>" +
    "                                                                </div>" +
    "                                                            </li>" +
    "" +
    "                                                            <li style='width: 2%; text-align: center'>" +
    "                                                                <b>&nbsp; </b>" +
    "                                                                <div><a href='#' class='btn_del' style=''>X</a></div>" +
    "                                                            </li>",
    initView: function (parentUuid) {
        this.initEvent();
        this.parentUuid = parentUuid;
        this.setPopupCode();
        this.addChild($("#addAggregation"));

    },
    initEvent: function () {
        var _this = this;
        //add aggregation
        $("#addAggregation").click(function(){
            _this.addChild(this);
        });

        $(".childAggregation").delegate(".btn_del","click",function(){
            if("create" == $(this).parents().eq(2).attr("type"))
            {
                $(this).parents().eq(2).remove();
            }
            else
            {
                $(this).parents().eq(2).hide();
            }
        });
    },
    setPopupCode : function()
    {
        if(this.nodeType.toLowerCase() == "virtual")
            this.popupCode = "PU121"
        else
            this.popupCode = "PU123"

    },
    addChild : function(_this){
        var cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","saveType").attr("saveType","create").html(this.template);

        var optionList = $("#identificationArea").find("select[data-ax-path='levelUuid']>option");

        for(var i = 0; i < optionList.length; i++)
        {
            cloneTag.find("select[data-ax-path='levelUuid']").append($(optionList[i]).clone());
        }

        cloneTag.find("select[data-ax-path='levelUuid']>option").eq(0).attr("selected","selected");
        $(_this).before(cloneTag);
        cloneTag.show();
    },
    getData : function(){
        var retData = new Array();
        var data = {};
        if(this.targetTag.css("display") != "none")
        {
            $(this.targetTag).find(".childAggregation>ul:not(#addAggregation)").each(function(){
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
                data["type"] = $("#identificationArea").find("select[data-ax-path='typeUuid']").val();

                if(data["title"] && data["title"] != "")
                    retData.push(data);
            });
        }
        return retData;
    }
});

fnObj.referenceAggre = axboot.viewExtend({
    targetTag  : $("#referenceAggreArea"),
    parentUuid : "",
    nodeType : "",
    popupCode : "",
    template :
    "                                                        <li style='width: 11%;'>" +
    "                                                            <b>Aggregation Code </b>" +
    "                                                            <div class='src_box2'>" +
    "                                                                <input type=text data-ax-path='aggregationUuid'>" +
    "                                                                <a href='#' style='top: 0' class='searchAggregation' ><img src='/assets/images/ams/search_normal.png' alt='find'></a>" +
    "                                                            </div>" +
    "" +
    "                                                        </li>" +
    "                                                        <li style='width: 75%; padding: 0 0.5%;'>" +
    "                                                            <b>Title</b>" +
    "                                                            <input type='text' data-ax-path='title' style='width: 100%' readonly class='bgf7'>" +
    "                                                        </li>" +
    "                                                        <li style='width: 11%;'>" +
    "                                                            <b>Level </b>" +
    "                                                            <input type='text' data-ax-path='levelUuid' style='width: 135px; margin-right: 3px' readonly class='bgf7'>" +
    "                                                        </li>" +
    "" +
    "                                                        <li style='width: 2%; text-align: center'>" +
    "                                                            <b>&nbsp;</b>" +
    "                                                            <a href='#' class='btn_del' style=''>X</a>" +
    "                                                        </li>"
    ,
   initView: function (parentUuid) {
       this.initEvent();
       this.parentUuid = parentUuid;
       this.setPopupCode();
       this.addChild($("#addReference"));
   },
    initEvent: function () {
        //add aggregation
        var _this = this;
        $("#addReference").click(function(){
            _this.addChild(this);
        });


        //Search Aggregation (Popup)
        $("#referenceAggreArea").delegate(".searchAggregation","click",function(){
            var parentsTag  = $(this).parents().eq(2);
            var data = {
                popupCode : _this.popupCode,
                preSearch : false,
                searchData : data,
                callback : function(data){
                    parentsTag.find("input[data-ax-path='aggregationUuid']").attr("aggregationUuid",data["AGGREGATION_UUID"])
                    parentsTag.find("input[data-ax-path='aggregationUuid']").val(data["AGGREGATION_CODE"])
                    parentsTag.find("input[data-ax-path='title']").val(data["TITLE"])
                    console.log(data);
                }
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_AGGREGATION,data);
        });

        $("#referenceAggreArea").delegate(".btn_del","click",function(){
            if("create" == $(this).parents().eq(2).attr("type"))
            {
                $(this).parents().eq(2).remove();
            }
            else
            {
                $(this).parents().eq(2).hide();
            }
        });

    },
    setPopupCode : function(){
        //if(this.nodeType.toLowerCase() == "virtual")
            this.popupCode = "PU123"
        //else
            //this.popupCode = "PU121"
    },
    addChild : function(_this){
        var cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","saveType").attr("saveType","create").html(this.template);
        //cloneTag.find("select[data-ax-path='levelUuid']").append($("#addReference").find("select[data-ax-path='levelUuid']>option"));
        //cloneTag.find("select[data-ax-path='levelUuid']>option").eq(0).attr("selected","selected");
        $(_this).before(cloneTag);
        cloneTag.show();
    },
    getData : function(){
        var retData = new Array();
        var data = {};
        if(this.targetTag.css("display") != "none")
        {
            $(this.targetTag).find(".childReference>ul:not(#addReference)").each(function(){
                data = {};
                data["saveType"] = $(this).attr("saveType");
                $(this).children("li").find("input,select,textarea").each(function(){
                    if($(this).attr("data-ax-path"))
                    {
                        if($(this).attr($(this).attr("data-ax-path")))
                            data[$(this).attr("data-ax-path")] = $(this).attr($(this).attr("data-ax-path"));
                        else
                            data[$(this).attr("data-ax-path")] = $(this).val();
                    }
                });
                /*
                data["typeUuid"] = $("#identificationArea").find("select[data-ax-path='type']").val();
                data["parentAggregationUuid"] = this.aggregationUuid;
                */
                if(data["aggregationUuid"] && data["aggregationUuid"] != "")
                    retData.push(data);
            });
        }
        return retData;
    }
});
fnObj.referenceItem = axboot.viewExtend({
    targetTag  : $("#referenceItemArea"),
    parentUuid : "",
    nodeType : "",
    popupCode : "",
    template :
    "                                                        <li style='width: 11%;'>" +
    "                                                            <b>Item Code </b>" +
    "                                                            <div>" +
    "                                                                <div class='src_box2'>" +
    "                                                                    <input type=text data-ax-path='itemUuid'>" +
    "                                                                    <a href='#' style='top: 0' class='searchReferenceItem'>" +
    "                                                                        <img src='/assets/images/ams/search_normal.png'" +
    "                                                                             alt='find'></a>" +
    "                                                                </div>" +
    "                                                            </div>" +
    "                                                        </li>" +
    "                                                        <li style='width: 75%; padding: 0 0.5%;'>" +
    "                                                            <b>Title</b>" +
    "                                                            <div>" +
    "                                                                <input type='text' data-ax-path='title' style='width: 100%' readonly class='bgf7'>" +
    "                                                            </div>" +
    "                                                        </li>" +
    "                                                        <li style='width: 11%;'>" +
    "                                                            <b>Type </b>" +
    "                                                            <div>" +
    "                                                                <input type='text' style='width: 135px; margin-right: 3px' readonly class='bgf7'>" +
    "                                                            </div>" +
    "                                                        </li>" +
    "" +
    "                                                        <li style='width: 2%; text-align: center'>" +
    "                                                            <b>&nbsp;</b>" +
    "                                                            <div><a href='#' class='btn_del' style=''>X</a></div>" +
    "                                                        </li>"
    ,
    initView: function (parentUuid) {
        this.initEvent();
        this.parentUuid = parentUuid;
        this.setPopupCode();
        this.addChild($("#addReferenceItem"));
    },
    initEvent: function () {
        //add aggregation
        var _this = this;
        $("#addReferenceItem").click(function(){
            _this.addChild(this);
        });

        $("#referenceItemArea").delegate(".searchReferenceItem","click",function(){
            var parentsTag  = $(this).parents().eq(3);
            var data = {
                popupCode : _this.popupCode,
                preSearch : false,
                searchData : data,
                callback : function(data){
                    parentsTag.find("input[data-ax-path='itemUuid']").attr("itemUuid",data["ITEM_UUID"])
                    parentsTag.find("input[data-ax-path='itemUuid']").val(data["ITEM_CODE"])
                    parentsTag.find("input[data-ax-path='title']").val(data["TITLE"])
                    console.log(data);
                }
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_ITEM,data);
        });

        $("#referenceItemArea").delegate(".btn_del","click",function(){
            if("create" == $(this).parents().eq(2).attr("type"))
            {
                $(this).parents().eq(2).remove();
            }
            else
            {
                $(this).parents().eq(2).hide();
            }
        });

    },
    setPopupCode : function(){
        this.popupCode = "PU122"
    },
    addChild : function(_this){
        var cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","saveType").attr("saveType","create").html(this.template);
        //cloneTag.find("select[data-ax-path='levelUuid']").append($("#addReference").find("select[data-ax-path='levelUuid']>option"));
        //cloneTag.find("select[data-ax-path='levelUuid']>option").eq(0).attr("selected","selected");
        $(_this).before(cloneTag);
        cloneTag.show();
    },
    getData : function(){
        var retData = new Array();
        var data = {};
        if(this.targetTag.css("display") != "none")
        {
            $(this.targetTag).find(".childReferenceItem>ul:not(#addReferenceItem)").each(function(){
                data = {};
                data["saveType"] = $(this).attr("saveType");
                $(this).children("li").find("input,select,textarea").each(function(){
                    if($(this).attr("data-ax-path"))
                    {
                        if($(this).attr($(this).attr("data-ax-path")))
                            data[$(this).attr("data-ax-path")] = $(this).attr($(this).attr("data-ax-path"));
                        else
                            data[$(this).attr("data-ax-path")] = $(this).val();
                    }
                });
                /*
                data["type"] = $("#identificationArea").find("select[data-ax-path='type']").val();
                data["parentAggregationUuid"] = this.aggregationUuid;
                */
                if(data["itemUuid"] && data["itemUuid"] != "")
                    retData.push(data);
            });
        }
        return retData;
    }
});

//=================================================================+
/**
 * treeView
 */
fnObj.treeView01 = axboot.viewExtend(axboot.commonView, {
    param: {},
    deletedList: [],
    newCount: 0,
    otherParam : {},
    addRootNode: function () {
    },
    initView: function () {
        var _this = this;
        this.reloadFlag = false;
        this.checkFlag = false;
        $('[data-tree-view-01-btn]').click(function () {
            var _act = this.getAttribute("data-tree-view-01-btn");
            switch (_act) {
                case "add":
                    ACTIONS.dispatch(ACTIONS.TREE_ROOTNODE_ADD);
                    break;
                case "delete":
                    //ACTIONS.dispatch(ACTIONS.ITEM_DEL);
                    break;
            }
        });
        var _this = this;
        this.target = axboot.treeBuilder($('[data-z-tree="tree-view-01"]'), {
            view: {
                dblClickExpand: false,
                addHoverDom: function (treeId, treeNode) {
                },
                removeHoverDom: function (treeId, treeNode) {
                }
            },
            edit: {
                enable: true,
                editNameSelectAll: false,
                showRemoveBtn : false,
                showRenameBtn : false
            },
            data : {
                simpleData: {
                    enable: true,
                    idKey: "aggregationUuid",
                    pIdKey: "parentAggregationUuid",
                    rootPId: 0
                }
            },
        }, []);
    },
    convertTreeData : function(_tree)
    {
        var iconObj = undefined;
        for(var i = 0; i < _tree.length; i++)
        {
            switch(_tree[i]["nodeType"])
            {
                case "normal":
                    iconObj =
                        {
                            open:false, icon:"/assets/images/ams/icon/fo.png", iconOpen:"/assets/images/ams/icon/fo_op.png", iconClose:"/assets/images/ams/icon/fo.png"
                        };
                    break;
                case "virtual":
                    iconObj =
                        {
                            open:false, icon:"/assets/images/ams/icon/fo_v.png", iconOpen:"/assets/images/ams/icon/fo_op_v.png", iconClose:"/assets/images/ams/icon/fo_v.png"
                        };
                    break;
                case "temporarily":
                    iconObj =
                        {
                            open:false, icon:"/assets/images/ams/icon/fo_t.png",iconOpen:"/assets/images/ams/icon/fo_op_t.png", iconClose:"/assets/images/ams/icon/fo_t.png"
                        };
                    break;
                case "item":
                    iconObj =
                        {
                            open:false, icon:"/assets/images/ams/icon/fi.png"
                        };
                    break;
            }
            _tree[i] = $.extend({},_tree[i],iconObj);
            iconObj = {};
        }
        return _tree;
    },
    setData: function (_searchData, _tree, _data) {
        this.param = $.extend({}, _searchData);


        var treeList = new Array();
        var data = undefined;


        var matchingData = function(key, list)
        {
            var retList = new Array();
            var uuid = undefined;
            var parentUuid = undefined;
            for(var i = 0; i < list.length; i++)
            {
                parentUuid = undefined === list[i]["parentAggregationUuid"] ? list[i]["parentUuid"] : list[i]["parentAggregationUuid"]
                if( key == parentUuid )
                {
                    uuid = undefined === list[i]["aggregationUuid"] ? list[i]["uuid"] : list[i]["aggregationUuid"]
                    list[i].children =  matchingData(uuid, list);
                    retList.push(list[i]);
                }
            }
            return retList;

        }

        var treeData = undefined;
        _tree = this.convertTreeData(_tree);
        var uuid = undefined;
        var parentUuid = undefined;
        for(var i = 0; i < _tree.length; i++)
        {
            treeData = _tree[i];
            uuid = undefined === treeData["aggregationUuid"] ? treeData["uuid"] : treeData["aggregationUuid"]
            parentUuid = undefined === treeData["parentAggregationUuid"] ? treeData["parentUuid"] : treeData["parentAggregationUuid"]
            if(null == parentUuid || undefined == parentUuid)
            {
                treeData.children = matchingData(uuid,_tree);
                treeList.push(treeData);
            }
        }

        this.target.setData(treeList);

    },
    updateNode : function(treeNode)
    {
        this.zTree.updateNode(treeNode);
    },
    getData: function () {
        var _this = this;
        var tree = this.target.getData();

        var convertList = function (_tree) {
            var _newTree = [];
            _tree.forEach(function (n, nidx) {
                var item = {};
                if (n.__created__ || n.__modified__) {
                    item = {
                        __created__: n.__created__,
                        __modified__: n.__modified__,
                        menuId: n.menuId,
                        menuGrpCd: _this.param.menuGrpCd,
                        menuNm: n.name,
                        parentId: n.parentId,
                        sort: nidx,
                        progCd: n.progCd,
                        level: n.level
                    };
                } else {
                    item = {
                        menuId: n.menuId,
                        menuGrpCd: n.menuGrpCd,
                        menuNm: n.name,
                        parentId: n.parentId,
                        sort: nidx,
                        progCd: n.progCd,
                        level: n.level
                    };
                }
                if (n.children && n.children.length) {
                    item.children = convertList(n.children);
                }
                _newTree.push(item);
            });
            return _newTree;
        };
        var newTree = convertList(tree);
        return newTree;
    },
    getDeletedList: function () {
        return this.deletedList;
    },
    clearDeletedList: function () {
        this.deletedList = [];
        return true;
    },
    updateNode: function (data) {
        var treeNodes = this.target.getSelectedNodes();
        if (treeNodes[0]) {
            treeNodes[0].progCd = data.progCd;
        }
    },
    deselectNode: function () {
        ACTIONS.dispatch(ACTIONS.TREEITEM_DESELECTE);
    }
});
setFormData = function(data){
    currentData = data;

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
            case "accumulationStartDate":
            case "accumulationEndDate":
                $("input[data-ax-path='"+columnName+"']").val(getFormattedDate(data[columnName]));
                break;
            case "creatorList" :
                fnObj.authorityInfo.setData("creatorArea", data[columnName]);
                break;
            case "relatedAuthorityList" :
                fnObj.authorityInfo.setData("authorityArea", data[columnName]);
                break;
            default:
                fnObj.formView.setFormData(columnName,data[columnName]);
                break;
        }
    }
}

function setAdditionalMeta(segmentList){
    var targetTag = $('#addConMetaArea');
    var cloneTag = null;
    var cloneInput = null;
    var dataPath = "";
    var option = "";

    $("#addConMetaArea .meta-ui").remove();

    segmentList.forEach(function(item, idx){
        if(item.displayedYN != "Y") return true;

        dataPath = "addMetadata" + item.additionalColumn.replace("ADD_METADATA", "");

        cloneTag = $('#addConMetaArea #metaTemplate').clone();
        cloneTag.addClass("meta-ui");
        cloneTag.attr("id", item.additionalColumn);
        cloneTag.find(".meta-label").html(item.name);

        targetTag.append(cloneTag);

        if(item.hasOwnProperty("popupUuid")) {
            cloneInput = cloneTag.find(".meta-combo");

            cloneInput.show();
            cloneTag.find(".meta-input").hide();

            axboot.commonCodeVO(item.popupCode).forEach(function(codeVO, idx) {
                option = $("<option value='"+ codeVO["codeDetailUUID"] +"'>"+codeVO["codeName"]+"</option>");
                cloneInput.append(option);
            });
        }else {
            cloneInput = cloneTag.find(".meta-input");

            cloneInput.show();
            cloneTag.find(".meta-combo").hide();
        }

        cloneInput.on("keyup change",function(event){
            isDetailChanged = true;
            currentData[$(event.target).attr("data-ax-path")] = $(event.target).val();
            fnObj.formView.setFormData($(event.target).attr("data-ax-path"), $(event.target).val());
        });

        cloneInput.attr("title", item.name);
        cloneInput.attr("data-ax-path", dataPath);
        if(item.requiredYN == "Y"){
            cloneInput.attr("data-ax-validate", "required");
        }

        cloneInput.css("width", item.displaySize == 0 ? "100%" : item.displaySize);

        fnObj.formView.setFormData(dataPath, currentData[dataPath]);

        cloneTag.show();
    });

    //fnObj.formView.setData(currentData);
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