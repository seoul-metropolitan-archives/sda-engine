var fnObj = {};
var selectedItem = {};
var aggregationUuid = "";
var parentAggregationUuid = "";
var sParam = [];
var navi = "";

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/rc003/01/list",
            data: $.extend({}, {pageSize: 1000}, data),
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
        url: "/assets/js/column_info/rc00301.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.treeView01.initView();
    // Data 조회

    var data = axboot.getMenuParams();

    if(null == data ){
        return;
    }else {
        if(data["sendData"]){
            sParam = data["sendData"];
            if(sParam[0].parentUuid == undefined){
                sParam[0].parentUuid = "";
            }
        }
        if(data["navi"])
        {
            navi = data["navi"];
        }
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,{aggregationUuid : data.uuid});
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
        $(".togl01").click(function(){
            $(".togl01_show").toggle();
            if ($('#open_btn1').val() == '▼') {
                $('#open_btn1').val('◀');
            } else {
                $('#open_btn1').val('▼');
            }
        });
        $(".togl02").click(function(){
            $(".togl02_show").toggle();
            if ($('#open_btn2').val() == '▼') {
                $('#open_btn2').val('◀');
            } else {
                $('#open_btn2').val('▼');
            }
        });

        $("#doAggregation,#doItem,#edit,#move,#updateStatus,#delete").click(function(e){

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

            switch(e.currentTarget.id)
            {
                case "doAggregation":
                    var item = getMenu("add aggregation");
                    item.menuParams = $.extend({},
                        sParam
                        ,{type: "create"},{navi : navi},{title : ""}
                    );
                    parentsObj.tabView.open(item);
                    break;
                case "doItem":
                    var item = getMenu("add item");
                    item.menuParams = $.extend({},{
                            aggregationUuid : sParam[0].uuid
                        },{type: "create"},{title : ""},{navi : navi}
                    );
                    parentsObj.tabView.open(item);
                    break;
                case "edit":
                    var item = getMenu("add aggregation");
                    item.menuParams = $.extend({},{
                            parentUuid : sParam[0].parentUuid,
                            uuid : sParam[0].uuid,
                            title : sParam[0].name,
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
                                selectType  :  sParam[0].nodeType,
                                "selectedList": sParam
                            };
                        },
                        callback: function (data) {
                            axToast.push(axboot.getCommonMessage("AA007"));
                            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,{aggregationUuid : aggregationUuid, parentAggregationUuid :parentAggregationUuid});
                        }
                    });
                    break;
                case "updateStatus":
                    axboot.modal.open({
                        modalType: "UPDATE_STATE_AGGREGATION_N_ITEM",
                        param: "",
                        sendData: function () {
                            return {
                                "selectedList": sParam
                            };
                        },
                        callback: function (data) {
                            axToast.push(axboot.getCommonMessage("AA007"));
                            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,{aggregationUuid : aggregationUuid, parentAggregationUuid :parentAggregationUuid});
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
                if( key == list[i]["parentAggregationUuid"] )
                {
                    list[i].children =  matchingData(list[i]["aggregationUuid"], list);
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
            if(treeData["parentAggregationUuid"] == null)
            {
                treeData.children = matchingData(treeData["aggregationUuid"],_tree);
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
    aggregationUuid = data.aggregationUuid;
    parentAggregationUuid = data.parentAggregationUuid;

    fnObj.formView.setFormData("rcHeadTitle",data.headTitle);
    fnObj.formView.setFormData("navi",navi);
    fnObj.formView.setFormData("rcTitle",data.name);
    fnObj.formView.setFormData("rcPublishedStatus",data.publishedStatusName);
    fnObj.formView.setFormData("rcLevel",data.levelCode);
    fnObj.formView.setFormData("rcDescription",data.description);
    fnObj.formView.setFormData("rcNotes",data.notes);
    fnObj.formView.setFormData("rcType",data.typeNm);
    fnObj.formView.setFormData("rcAuthor",data.author);
    fnObj.formView.setFormData("rcFrom",data.parentAggregationName);
    fnObj.formView.setFormData("rcAggregationCode",data.aggregationCode);
    if(data.descriptionStartDate != "undefined" || data.descriptionStartDate != null) {
        if (data.descriptionStartDate == data.descriptionEndDate || data.descriptionEndDate == "undefined" || data.descriptionEndDate == null) {
            fnObj.formView.setFormData("rcDateOfDescription", dateFormatter(data.descriptionStartDate));
        } else {
            fnObj.formView.setFormData("rcDateOfDescription", dateFormatter(data.descriptionStartDate) + ' ~ ' + dateFormatter(data.descriptionEndDate));
        }
    }
    fnObj.formView.setFormData("rcProvenance",data.provenance);
    fnObj.formView.setFormData("rcReferenceCode",data.referenceCode);
    fnObj.formView.setFormData("rcCreator",data.creator);
    fnObj.formView.setFormData("rcDateOfCreation",data.creationStartDate);
    fnObj.formView.setFormData("rcAddMetadata01",data.addMetadata01);
    fnObj.formView.setFormData("rcAddMetadata02",data.addMetadata02);
    fnObj.formView.setFormData("rcAddMetadata03",data.addMetadata03);
    fnObj.formView.setFormData("rcAddMetadata04",data.addMetadata04);
    fnObj.formView.setFormData("rcAddMetadata05",data.addMetadata05);
    fnObj.formView.setFormData("rcAddMetadata06",data.addMetadata06);
    fnObj.formView.setFormData("rcAddMetadata07",data.addMetadata07);
    fnObj.formView.setFormData("rcAddMetadata08",data.addMetadata08);
    fnObj.formView.setFormData("rcAddMetadata09",data.addMetadata09);
    fnObj.formView.setFormData("rcAddMetadata10",data.addMetadata10);

}

function dateFormatter(orgDate){
    if(orgDate == undefined || orgDate == null) return ' ';

    var year = orgDate.substring(0, 4);
    var month = orgDate.substring(4, 6);
    var day = orgDate.substring(6, 8);

    return year + '-' + month + '-' + day;
}