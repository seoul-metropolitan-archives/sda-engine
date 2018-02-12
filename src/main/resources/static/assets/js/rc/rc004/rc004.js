var fnObj = {};
var selectedItem = {};
var nodeType = "";
var PAGE_MODE = "create";
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/rc005/01/list",
            data: $.extend({}, {pageSize: 1000}, data),
            callback: function (res) {
                if(res.list != "undefined" && res.list != null && res.list.length > 0){
                    rcList = ax5.util.deepCopy(res.list);
                    fnObj.treeView01.setData({}, res.list, data);
                    var itemIndex = rcList.length - 1;
                    setFormData(rcList[itemIndex]);
                    if(rcList[itemIndex].rc00502VoList!= "undefined" && rcList[itemIndex].rc00502VoList != null && rcList[itemIndex].rc00502VoList.length > 0){
                        fnObj.gridView01.setData(rcList[itemIndex].rc00502VoList);
                    }
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SAVE: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/rc004/01/saveItemDetails",
            data: $.extend({},  {pageSize: 1000} ,this.formView.getData()),
            callback: function (res) {
                if(PAGE_MODE != 'create'){
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,{aggregationUuid : fnObj.formView.getData().raAggregationUuid, itemUuid : fnObj.formView.getData().itemUuid} );
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    SEARCH_FROM_SCH : function(caller, act, data)
    {

        axboot.modal.open({
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

    _this.formView.initView();
    _this.treeView01.initView();
    _this.gridView01.initView();
    // Data 조회
    var data = axboot.getMenuParams();

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

        if(data["title"]){
            fnObj.formView.setFormData("headTitle",data["title"]);
        }

        if(data["navi"]){
            fnObj.formView.setFormData("navi",data["navi"]);
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
    fnObj.formView.setFormData("title",data.name);
    fnObj.formView.setFormData("itemUuid",data.riItemUuid);
    fnObj.formView.setFormData("itemCode",data.riItemCode);
    fnObj.formView.setFormData("typeUuid",data.riTypeUuid);
    fnObj.formView.setFormData("publishedStatusUuid",data.riPublishedStatusUuid);
    fnObj.formView.setFormData("level",data.raLevelNm);
    fnObj.formView.setFormData("description",data.description1);
    fnObj.formView.setFormData("author",data.riAuthor);
    fnObj.formView.setFormData("rcAggregationCode",data.aggregationCode);
    fnObj.formView.setFormData("openStatusUuid",data.openStatusUuid);
    fnObj.formView.setFormData("raTitle",data.raTitle);
    fnObj.formView.setFormData("raAggregationUuid",data.raAggregationUuid);
    fnObj.formView.setFormData("notes",data.notes1);
    fnObj.formView.setFormData("provenance",data.provenance);
    fnObj.formView.setFormData("creator",data.creator);
    fnObj.formView.setFormData("keyword",data.keyword);
    fnObj.formView.setFormData("from",data.riTitle);
    fnObj.formView.setFormData("referenceCode",data.referenceCode);
    fnObj.formView.setFormData("raAggregationCode",data.raAggregationCode);

    fnObj.formView.setFormData("descriptionStartDate",dateFormatter(data.riDescriptionStartDate));
    fnObj.formView.setFormData("descriptionEndDate",dateFormatter(data.riDescriptionEndDate));

    fnObj.formView.setFormData("creationStartDate",dateFormatter(data.creationStartDate));
    fnObj.formView.setFormData("creationEndDate",dateFormatter(data.creationEndDate));


    ACTIONS.dispatch(ACTIONS.SEARCH_FROM_SCH,{
        popupCode : "PU123",
        searchData : data.riAggregationUuid
    });
  /*  $("input[data-ax-path='descriptionStartDate']").val();
    $("input[data-ax-path='descriptionEndDate']").val();
*/
    // fnObj.formView.setFormData("creationStartDate",data.creationStartDate);
    // fnObj.formView.setFormData("creationEndDate",data.creationEndDate);

/*    fnObj.formView.setFormData("addMetadata01",data.addMetadata01);
    fnObj.formView.setFormData("addMetadata02",data.addMetadata02);
    fnObj.formView.setFormData("addMetadata03",data.addMetadata03);
    fnObj.formView.setFormData("addMetadata04",data.addMetadata04);
    fnObj.formView.setFormData("addMetadata05",data.addMetadata05);
    fnObj.formView.setFormData("addMetadata06",data.addMetadata06);
    fnObj.formView.setFormData("addMetadata07",data.addMetadata07);
    fnObj.formView.setFormData("addMetadata08",data.addMetadata08);
    fnObj.formView.setFormData("addMetadata09",data.addMetadata09);
    fnObj.formView.setFormData("AddMetadata10",data.addMetadata10);*/
}

function dateFormatter(orgDate){
    if(orgDate == undefined || orgDate == null) return ' ';
    var year = orgDate.substring(0, 4);
    var month = orgDate.substring(4, 6);
    var day = orgDate.substring(6, 8);

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

    if (chk1.test(strValue) == false && chk2.test(strValue) == false) { // 유효성 검사에 둘다 성공하지 못했다면
        //alert("1999-1-1 형식 또는 \r\n1999-01-01 형식으로 날자를 입력해주세요.");
        axToast.push(axboot.getCommonMessage("AA011"));
        result = false;

    }
    return result;
}