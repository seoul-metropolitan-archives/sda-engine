var fnObj = {};
var selectedItem = {};
var nodeType = "";
var PAGE_MODE = "create";

var currentData = null;
const ENTITY_TYPE = "RC_ITEM_CON";

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
        activate : function(event, ui){
            fnObj.gridView01.gridObj.getGridView().resetSize();
        }
    });

    //$( ".detail_wrap" ).accordion( "destroy" );
});


var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/rc005/01/list",
            data: $.extend({}, {pageSize: 1000}, data),
            callback: function (res) {
                if(res.list != "undefined" && res.list != null && res.list.length > 0){
                    rcList = ax5.util.deepCopy(res.list);
                    var itemIndex = rcList.length - 1;
                    setFormData(rcList[itemIndex]);
                    if(rcList[itemIndex].rc00502VoList!= "undefined" && rcList[itemIndex].rc00502VoList != null && rcList[itemIndex].rc00502VoList.length > 0){
                        fnObj.gridView01.setData(rcList[itemIndex].rc00502VoList);
                    }

                    ACTIONS.dispatch(ACTIONS.GET_META_TEMPLATE, rcList[itemIndex].addMetaTemplateSetUuid);
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SAVE: function (caller, act, data) {
        var sendData = this.formView.getData();
        sendData["creatorList"] = fnObj.authorityInfo.getData("creatorArea");
        sendData["relatedAuthorityList"]  = fnObj.authorityInfo.getData("authorityArea");

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/rc004/01/saveItemDetails",
            data: JSON.stringify(sendData),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,{aggregationUuid : fnObj.formView.getData().raAggregationUuid, itemUuid : res.riItemUuid} );
                axToast.push(axboot.getCommonMessage("AA007"));
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    SEARCH_FROM_SCH : function(caller, act, data)
    {

        axboot.commonModal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                fnObj.formView.setFormData("raAggregationCode",data["AGGREGATION_CODE"]);
                fnObj.formView.setFormData("raAggregationUuid",data["AGGREGATION_UUID"]);
                fnObj.formView.setFormData("from",data["TITLE"]);
                if(this.close)
                    this.close();
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
        url: "/assets/js/column_info/rc00401.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.popupView.initView();
    _this.formView.initView();
    _this.authorityInfo.initView();
    _this.gridView01.initView();
    // Data 조회
    var data = parent.axboot.modal.getData();

    if(null == data ){
        return;
    }else{
        PAGE_MODE = data.type;

        if(data["nodeType"] && data["nodeType"] != undefined){
            if(data["nodeType"].toLowerCase() == "virtual"){
                nodeType = "PU121";
            }else{
                nodeType = "PU123";
            }
        }

        if(PAGE_MODE == "create") {
            fnObj.formView.setFormData("raAggregationUuid",data.aggregationUuid);

            ACTIONS.dispatch(ACTIONS.SEARCH_FROM_SCH,{
                popupCode : nodeType,
                searchData : data.aggregationUuid
            });

        } else {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,{aggregationUuid : data.aggregationUuid, itemUuid : data.itemUuid});
        }
    }
};

fnObj.popupView = axboot.viewExtend({
    initView : function(){
        this.initEvent();
    },
    initEvent : function()
    {
        var _this = this;
        $("#close").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE)
        });
    }
});

fnObj.formView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {useYn: ""});
    },
    initView: function () {
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

        /*$("input[data-ax-path='descriptionStartDate']").keyup(function () {
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
        });*/


        $("input[data-ax-path='from']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU123",
                preSearch : false,
                searchData : $("input[data-ax-path='from']").val().trim(),
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_FROM_SCH,data);
        });
        $("input[data-ax-path='from']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU123",
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_FROM_SCH,data);
            }
        });

        $("[data-ax-path='addMetaTemplateSetUuid']").on("change", function(){
            ACTIONS.dispatch(ACTIONS.GET_META_TEMPLATE, $("[data-ax-path='addMetaTemplateSetUuid']").val());
            isDetailChanged = true;
        });

        /*$("input[data-ax-path='descriptionStartDate'],input[data-ax-path='sdescriptionEndDate']").key (function(){
            var date = _this.value;
            if (date.match(/^\d{4}$/) !== null) {
                _this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                _this.value = date + '-';
            }
        });*/

    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        data["typeUuid"] = $("select[data-ax-path='typeUuid']").val();
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
    "                                                            <input type=hidden data-ax-path='itemAuthorityUuid'>" +
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
            axboot.commonModal.open({
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
            cloneTag.find("input[data-ax-path='itemAuthorityUuid']").val(data["itemAuthorityUuid"]);
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
                    data["itemCreatorUuid"] = data["itemAuthorityUuid"];
                    data["creatorUuid"] = data["authorityUuid"];
                }else if(target == "authorityArea"){
                    data["itemRelatedAuthorityUuid"] = data["itemAuthorityUuid"];
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
        $("#" + target + " .auth_fit").remove();

        if(data != null && data != "undefined" && data.length > 0){
            data.forEach(function(item, index){
                if(target == "creatorArea"){
                    item["itemAuthorityUuid"] = item["itemCreatorUuid"];
                    item["authorityUuid"] = item["creatorUuid"];
                    item["authorityName"] = item["creatorName"];
                }else if(target == "authorityArea"){
                    item["itemAuthorityUuid"] = item["itemRelatedAuthorityUuid"];
                }
                fnObj.authorityInfo.addChild($("#" + target).find(".childDnrInfo"), item);
            });
        }
    }
});


fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    entityName: "item components",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(rc00401.column_info);
        this.gridObj.setOption({
            indicator: {visible: true}
        })
        this.makeGrid();
    }
});

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
                    idKey: "raAggregationUuid",
                    pIdKey: "raParentAggregationUuid",
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
                if( key == list[i]["raParentAggregationUuid"] )
                {
                    list[i].children =  matchingData(list[i]["raAggregationUuid"] == undefined  ? list[i]["riItemUuid"] : list[i]["raAggregationUuid"], list);
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
            if(treeData["raParentAggregationUuid"] == null)
            {
                treeData.children = matchingData(treeData["raAggregationUuid"],_tree);
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

    fnObj.formView.setFormData("headTitle",data.name);

    fnObj.formView.setFormData("name",data.name);
    fnObj.formView.setFormData("riItemUuid",data.riItemUuid);
    fnObj.formView.setFormData("riItemCode",data.riItemCode);
    fnObj.formView.setFormData("riTypeUuid",data.riTypeUuid);
    fnObj.formView.setFormData("riPublishedStatusUuid",data.riPublishedStatusUuid);
    fnObj.formView.setFormData("description",data.description1);
    fnObj.formView.setFormData("riAuthor",data.riAuthor);
    fnObj.formView.setFormData("openStatusUuid",data.openStatusUuid);
    fnObj.formView.setFormData("raAggregationUuid",data.riAggregationUuid);
    fnObj.formView.setFormData("notes",data.notes1);
    fnObj.formView.setFormData("provenance",data.provenance);
    fnObj.formView.setFormData("creator",data.creator);
    fnObj.formView.setFormData("keyword",data.keyword);
    fnObj.formView.setFormData("referenceCode",data.referenceCode);
    fnObj.formView.setFormData("raAggregationCode",data.raAggregationCode);

    fnObj.formView.setFormData("riDescriptionStartDate",dateFormatter(data.riDescriptionStartDate));
    fnObj.formView.setFormData("riDescriptionEndDate",dateFormatter(data.riDescriptionEndDate));

    fnObj.formView.setFormData("creationStartDate",dateFormatter(data.creationStartDate));
    fnObj.formView.setFormData("creationEndDate",dateFormatter(data.creationEndDate));

    fnObj.formView.setFormData("accumulationStartDate",dateFormatter(data.accumulationStartDate));
    fnObj.formView.setFormData("accumulationEndDate",dateFormatter(data.accumulationEndDate));

    /** 설계변경 추가 **/
    fnObj.formView.setFormData("keyword",data.keyword);
    fnObj.formView.setFormData("referenceCode",data.referenceCode);
    fnObj.formView.setFormData("raAggregationCode",data.raAggregationCode);

    fnObj.formView.setFormData("languageCode", data.languageCode);
    fnObj.formView.setFormData("statusDescription", data.statusDescription);
    fnObj.formView.setFormData("levelOfDetailUuid", data.levelOfDetailUuid);
    fnObj.formView.setFormData("sourceSystemUuid", data.sourceSystemUuid);
    fnObj.formView.setFormData("creationSystemUuid", data.creationSystemUuid);
    fnObj.formView.setFormData("addMetaTemplateSetUuid", data.addMetaTemplateSetUuid);
    fnObj.formView.setFormData("legalStatusUuid", data.legalStatusUuid);
    fnObj.formView.setFormData("repositoriesUuid", data.repositoriesUuid);
    fnObj.formView.setFormData("repositoriesName", data.repositoriesName);
    fnObj.formView.setFormData("electronicRecordStatusUuid", data.electronicRecordStatusUuid);
    fnObj.formView.setFormData("accumulationStartDate", data.accumulationStartDate);
    fnObj.formView.setFormData("accumulationEndDate", data.accumulationEndDate);
    fnObj.formView.setFormData("scopeContent", data.scopeContent);
    fnObj.formView.setFormData("custodialHistory", data.custodialHistory);
    fnObj.formView.setFormData("sourceAcquisitionUuid", data.sourceAcquisitionUuid);
    fnObj.formView.setFormData("sourceAcquisitionName", data.sourceAcquisitionName);
    fnObj.formView.setFormData("physicalCondition", data.physicalCondition);
    fnObj.formView.setFormData("useCondition", data.useCondition);
    fnObj.formView.setFormData("findingAids", data.findingAids);
    fnObj.formView.setFormData("rulesConversionUuid", data.rulesConversionUuid);
    fnObj.formView.setFormData("recordScheduleUuid", data.recordScheduleUuid);
    fnObj.formView.setFormData("accessCondition", data.accessCondition);

    fnObj.authorityInfo.setData("creatorArea", data.creatorList);
    fnObj.authorityInfo.setData("authorityArea", data.relatedAuthorityList);
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

function dateFormatter(str){
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

    return date;

    if (chk1.test(strValue) == false && chk2.test(strValue) == false) { // 유효성 검사에 둘다 성공하지 못했다면
        //alert("1999-1-1 형식 또는 \r\n1999-01-01 형식으로 날자를 입력해주세요.");
        axToast.push(axboot.getCommonMessage("AA011"));
        result = false;

    }
    return result;
}