var fnObj = {};
var selectedItem = {};
var from = "";
var navi = "";
var sParam = [];
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
                    try
                    {
                        from = rcList[rcList.length - 2]["raTitle"];
                    }catch(exception)
                    {
                        console.log(exception);
                    }


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
        return false;
    },
    DELETE_AGGREGATION : function(caller, act, list){

        axboot.ajax({
            url: "/rc/rc001/deleteAggregation",
            data: JSON.stringify(list),
            dataType : "JSON",
            type : "POST",
            callback: function (res) {
                if(res.status == -500)
                    axWarningToast.push(axboot.getCommonMessage(res.message));
                else
                {
                    axToast.push(axboot.getCommonMessage(res.message));
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH1: function (caller, act, data) {},
    PAGE_SAVE: function (caller, act, data) {},
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
        url: "/assets/js/column_info/rc00501.js",
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
    } else {
        if(data["navi"]){
            fnObj.formView.setFormData("navi",data["navi"]);
            navi = data["navi"];
        }
        //sParam = data["sendData"];
        sParam = data;
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,{aggregationUuid : data.parentUuid, itemUuid : data.uuid});
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
        this.initEvent();
    },
    initEvent: function () {
        var _this = this;

        $("#edit,#move,#updateStatus,#delete").click(function(e){

            var parentsObj = parent.window.fnObj;

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

            var parentUuid = sParam[0] === undefined ? sParam.parentUuid : sParam[0].parentUuid;
            var itemUuid = sParam[0] === undefined ? sParam.uuid : sParam[0].uuid;
            var title = sParam[0] === undefined ? sParam.name : sParam[0].name;
            switch(e.currentTarget.id)
            {

                case "edit":
                    var item = getMenu("add item");
                    item.menuParams = $.extend({},{
                        aggregationUuid : parentUuid,
                        itemUuid : itemUuid,
                        title : title,
                        navi:navi
                        },{type: "update"}
                    );
                    parentsObj.tabView.open(item);
                    break;
                case "move":
                        axboot.modal.open({
                        modalType: "MOVE_AGGREGATION",
                        param: "",
                        sendData: function () {
                            return {
                                selectType  :  "item",
                                "selectedList":sParam // [{uuid: itemUuid, parentUuid: aggregationUuid,nodeType:typeNm}]
                            };
                        },
                        callback: function (data) {
                            axToast.push(axboot.getCommonMessage("AA007"));
                            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,{aggregationUuid : itemUuid, itemUuid :sParam.parentUuid});
                        }
                    });
                    break;
                case "updateStatus":
                    axboot.modal.open({
                        modalType: "UPDATE_STATE_AGGREGATION_N_ITEM",
                        param: "",
                        sendData: function () {
                            return {
                                "selectedList": sParam//[{uuid: itemUuid, parentUuid: aggregationUuid}]
                            };
                        },
                        callback: function (data) {
                            axToast.push(axboot.getCommonMessage("AA007"));
                            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,{aggregationUuid : sParam.uuid, itemUuid :parentUuid});
                        }
                    });
                    break;
                case "delete":
                    ACTIONS.dispatch(ACTIONS.DELETE_AGGREGATION,sParam);
                    break;
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

fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    entityName: "item components",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(rc00501.column_info);
        this.gridObj.setOption({
            indicator: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    getRowData: function (){
        return this.gridObj.getSelectedData();
    },
    itemClick: function (data, index) {
        if (data.componentUuid != null && data.componentUuid != "") {
            $.ajax({
                url :"/api/v1/common/getStreamingUrl",
                data : JSON.stringify({componentUuid:data.componentUuid}),
                dataType : "json",
                type : "post",
                contentType : "application/json",
                success : function(res){
                    if(res.url != undefined && res.url != null){
                        window.open(res.url, "", "");
                    }
                },
                error : function (a,b,c)
                {
                    console.log(a);
                }
            })
        }
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
    fnObj.formView.setFormData("itemTitle",'Item - ' + data.name);
    fnObj.formView.setFormData("title",data.name);
    fnObj.formView.setFormData("code",data.riItemCode);
    fnObj.formView.setFormData("type",data.riTypeNm);
    fnObj.formView.setFormData("publishedStatus",data.riPublishedStatusNm);
    fnObj.formView.setFormData("level",data.raLevelNm);
    fnObj.formView.setFormData("description",data.description1);
    fnObj.formView.setFormData("author",data.riAuthor);
    fnObj.formView.setFormData("rcAggregationCode",data.aggregationCode);
    fnObj.formView.setFormData("notes",data.notes1);
    fnObj.formView.setFormData("from",from);
    fnObj.formView.setFormData("referenceCode",data.referenceCode);

    if(data.riDescriptionStartDate != "undefined" || data.riDescriptionStartDate != null) {
        if (data.riDescriptionStartDate == data.riDescriptionEndDate) {
            fnObj.formView.setFormData("rcDateOfDescription", dateFormatter(data.riDescriptionStartDate));
        } else {
            fnObj.formView.setFormData("rcDateOfDescription", dateFormatter(data.riDescriptionStartDate) + ' ~ ' + dateFormatter(data.riDescriptionEndDate));
        }
    }

    if(data.creationStartDate != "undefined" || data.creationStartDate != null) {
        if (data.creationEndDate == data.creationStartDate) {
            fnObj.formView.setFormData("rcDateOfCreation", dateFormatter(data.creationEndDate));
        } else {
            fnObj.formView.setFormData("rcDateOfCreation", dateFormatter(data.creationStartDate) + ' ~ ' + dateFormatter(data.creationEndDate));
        }
    }


    fnObj.formView.setFormData("provenance",data.provenance);
    fnObj.formView.setFormData("creator",data.creator);
    fnObj.formView.setFormData("keyword",data.keyword);
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
    if(orgDate == undefined) return ' ';

    var year = orgDate.substring(0, 4);
    var month = orgDate.substring(4, 6);
    var day = orgDate.substring(6, 8);

    return year + '-' + month + '-' + day;
}