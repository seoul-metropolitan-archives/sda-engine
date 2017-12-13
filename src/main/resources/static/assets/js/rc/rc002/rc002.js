
var fnObj = {};
var selectedItem = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/rc003/01/list",
            data: $.extend({}, {pageSize: 1000, sort: "classificationCode"}, data

        //{aggregationUuid :'A2EF15E7-BE58-41DD-945C-E99FB5DE60C1'}
        ),
            callback: function (res) {
                if(res.list != "undefined" && res.list != null && res.list.length > 0){
                    rcList = ax5.util.deepCopy(res.list);
                    fnObj.treeView01.setData({}, res.list, data);
                    setFormData(rcList[0]);
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
                systemMeta : fnObj.systemMetaArea.getData(),
                contextualMeta :fnObj.contextualMetaArea.getData(),
                childrenAggregationList : fnObj.childrenAggre.getData(),
                referenceAggregationList : fnObj.referenceAggre.getData(),
                referenceItemList : fnObj.referenceItem.getData()
            }
        console.log();
        console.log(saveData);
        axboot.ajax({
            url: "/api/v1/rc/rc002/save",
            dataType : "JSON",
            type : "POST",
            data: JSON.stringify(saveData),
            callback: function (res) {
                axboot.getCommonMessage("AA007");
            },
            options: {
                onError: axboot.viewError
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
    if(null == data || data.type == "create")
    {

    }
    else
    {
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


    if(null != data)
    {
        _this.systemMetaArea.nodeType = undefined == data["nodeType"]? data["type"] : data["nodeType"];
        _this.childrenAggre.nodeType = undefined == data["nodeType"]? data["type"] : data["nodeType"];
        _this.referenceAggre.nodeType = undefined == data["nodeType"]? data["type"] : data["nodeType"];
    }
    _this.systemMetaArea.initView(uuid);
    if(null != data && data["parentUuid"])
    {
        fnObj.systemMetaArea.setFrom(data["parentUuid"],true);
    }

    _this.contextualMetaArea.initView(uuid);
    _this.childrenAggre.initView(uuid);
    _this.referenceItem.initView(uuid);
    _this.referenceAggre.initView(uuid);
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

        this.initEvent();
    },
    initEvent: function () {
        var _this = this;
        //by the Aggregation type, to control the Reference Area
        $("input[data-ax-path='descriptionStartDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });

        $("input[data-ax-path='descriptionStartDate']").keypress(function () {
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

        $("input[data-ax-path='descriptionEndDate']").keypress(function () {
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

        $("input[data-ax-path='creationStartDate']").keypress(function () {
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

        $("input[data-ax-path='creationEndDate']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });

        $("input[data-ax-path='creationEndDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });


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
//System Meta Object
fnObj.systemMetaArea = axboot.viewExtend({
    targetTag : $("#systemMetaArea"),
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
                    $("select[data-ax-path='typeUuid']").prop("disabled","disabled")
                }
            });

        },500)


    },
    initEvent : function(){
        var _this = this;
        $("select[data-ax-path='typeUuid']").change(function(){
            fnObj.referenceAggre.nodeType = $(this).find("option:selected").text();
            fnObj.referenceAggre.setPopupCode();
            if($(this).find("option:selected").text()=="Virtual")
            {
                $("#referenceAggreArea,#referenceItemArea").show();
            }
            else
            {
                $("#referenceAggreArea,#referenceItemArea").hide();
            }
        });
        $("input[data-ax-path='parentsAggregationUuid']").blur(function(){
            var _thisObj = this;
            _this.setFrom($(this).parents().eq(0).find("input[data-ax-path='parentsAggregationUuid']").val());
        });

        $("#fromAggregation").click(function(){
            var _thisObj = this;
            _this.setFrom($(this).parents().eq(0).find("input[data-ax-path='parentsAggregationUuid']").val());
        });


    },
    setFrom : function(data,preSearch){
        var data = {
            popupCode : this.popupCode,
            preSearch : undefined == preSearch ? false : true,
            searchData : data,
            callback : function(data){
                var target = $("#fromAggregation").parents().eq(0).find("input[data-ax-path='parentsAggregationUuid']");
                target.attr("parentsAggregationUuid",data["AGGREGATION_UUID"]);
                target.val(data["TITLE"]);
                console.log(data);
            }
        };
        ACTIONS.dispatch(ACTIONS.SEARCH_AGGREGATION,data);
    },
    getData : function(){
        var data = {};
        if(this.targetTag.css("display") != "none")
        {
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
        }
        return data;
    }
});
//Contextual Meta Object
fnObj.contextualMetaArea = axboot.viewExtend({
    targetTag : $("#contextualMetaArea"),
    aggregationUuid : "",
    initView : function(aggregationUuid){
        this.initEvent();
        this.aggregationUuid = aggregationUuid;
    },
    initEvent : function(){

    },
    getData : function(){
        var retData = new Array();
        var data = {};
        if(this.targetTag.css("display") != "none")
        {
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
        }
        return data;
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
        cloneTag.find("select[data-ax-path='levelUuid']").append($("#systemMetaArea").find("select[data-ax-path='levelUuid']>option"));
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
                data["type"] = $("#systemMetaArea").find("select[data-ax-path='type']").val();

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
    template : "<ul class='pdb_10' data-ax-path='saveType' saveType='create'>" +
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
    "                                                        </li>" +
    "                                                    </ul>",
   initView: function (parentUuid) {
       this.initEvent();
       this.parentUuid = parentUuid;

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
                    parentsTag.find("input[data-ax-path='aggregationUuid']").attr("aggregationUuid",data["AGGREGATION_CODE"])
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
        if(this.nodeType.toLowerCase() == "virtual")
            this.popupCode = "PU121"
        else
            this.popupCode = "PU123"
    },
    addChild : function(_this){
        var cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","referenceAggreAreaSaveType").attr("saveType","create").html(this.template);
        //cloneTag.find("select[data-ax-path='levelUuid']").append($("#addReference").find("select[data-ax-path='levelUuid']>option"));
        // /cloneTag.find("select[data-ax-path='levelUuid']>option").eq(0).attr("selected","selected");
        $(_this).before(cloneTag);
        cloneTag.show();
    },
    getData : function(){
        var retData = new Array();
        var data = {};
        if(this.targetTag.css("display") != "none")
        {
            $(this.targetTag).find(".childAggregation>ul:not(#addReference)").each(function(){
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
                data["typeUuid"] = $("#systemMetaArea").find("select[data-ax-path='type']").val();
                data["parentAggregationUuid"] = this.aggregationUuid;
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
    template : "<ul class='pdb_10' data-ax-path='saveType' saveType='create'>" +
    "                                                        <li style='width: 11%;'>" +
    "                                                            <b>Item Code </b>" +
    "                                                            <div>" +
    "                                                                <div class='src_box2'>" +
    "                                                                    <input type=text>" +
    "                                                                    <a href='#' style='top: 0' class='searchReferenceItem' data-ax-path='itemUuid'>" +
    "                                                                        <img src='/assets/images/ams/search_normal.png'" +
    "                                                                             alt='find'></a>" +
    "                                                                </div>" +
    "                                                            </div>" +
    "                                                        </li>" +
    "                                                        <li style='width: 75%; padding: 0 0.5%;'>" +
    "                                                            <b>Title</b>" +
    "                                                            <div>" +
    "                                                                <input type='text' style='width: 100%' readonly" +
    "                                                                       class='bgf7'>" +
    "                                                            </div>" +
    "                                                        </li>" +
    "                                                        <li style='width: 11%;'>" +
    "                                                            <b>Type </b>" +
    "                                                            <div>" +
    "                                                                <input type='text' style='width: 135px; margin-right: 3px'" +
    "                                                                       readonly class='bgf7'>" +
    "                                                            </div>" +
    "                                                        </li>" +
    "" +
    "                                                        <li style='width: 2%; text-align: center'>" +
    "                                                            <b>&nbsp;</b>" +
    "                                                            <div><a href='#' class='btn_del' style=''>X</a></div>" +
    "                                                        </li>" +
    "                                                    </ul>",
    initView: function (parentUuid) {
        this.initEvent();
        this.parentUuid = parentUuid;
        this.addChild($("#addReferenceItem"));
    },
    initEvent: function () {
        //add aggregation
        var _this = this;
        $("#addReferenceItem").click(function(){
            _this.addChild(this);
        });

        $("#referenceItemArea").delegate(".searchReferenceItem","click",function(){

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
    addChild : function(_this){
        var cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","referenceItemAreaSaveType").attr("saveType","create").html(this.template);
        //cloneTag.find("select[data-ax-path='levelUuid']").append($("#addReference").find("select[data-ax-path='levelUuid']>option"));
        // /cloneTag.find("select[data-ax-path='levelUuid']>option").eq(0).attr("selected","selected");
        $(_this).before(cloneTag);
        cloneTag.show();
    },
    getData : function(){
        var retData = new Array();
        var data = {};
        if(this.targetTag.css("display") != "none")
        {
            $(this.targetTag).find(".childAggregation>ul:not(#addReferenceItem)").each(function(){
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
                data["type"] = $("#systemMetaArea").find("select[data-ax-path='type']").val();
                data["parentAggregationUuid"] = this.aggregationUuid;
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
            for(var i = 0; i < list.length; i++)
            {
                if( key == list[i]["parentUuid"] )
                {
                    list[i].children =  matchingData(list[i]["uuid"], list);
                    retList.push(list[i]);
                }
            }
            return retList;

        }

        var treeData = undefined;
        _tree = this.convertTreeData(_tree);
        for(var i = 0; i < _tree.length; i++)
        {
            treeData = _tree[i];
            if(treeData["parentUuid"] == null)
            {
                treeData.children = matchingData(treeData["uuid"],_tree);
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