var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/rc/rc001/getAllNodes",
            data: $.extend({}, {pageSize: 1000}),
            callback: function (res) {
                console.log(res.list);
                fnObj.treeView01.setData({}, res.list, data);
                /*if (res.list.length > 0) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, res.list[0]);
                }*/

            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        axboot.ajax({
            type: "POST",
            url: "/ad/ad001/save.do",
            data: JSON.stringify(fnObj.gridView01.getData()),
            callback: function (res) {
                axToast.push(axboot.getCommonMessage("AA007"));
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

function leftcloseView() {
    $(".left").hide();
    $(".cssBody").css("left", "0%");
    $(".cssBody").css("padding-left", "0");
    $("a.leftmenu_open").show();
    $("a.leftmenu_close").hide();
    $(".left_close_open_btn a").css("left", "0%");
    $(".left_close_open_btn a").css("margin-left", "0");
    $(".header").css("width", "100%");
    setTimeout("gridView.resetSize();", 100);
    setTimeout("M2GridView.resetSize();", 100);
    setTimeout("M3GridView.resetSize();", 100);
    setTimeout("MtreeView.resetSize();", 100);
    setTimeout("treeView.resetSize();", 100);


}

function leftopenView() {
    $(".left").show();
    $("a.leftmenu_open").hide();
    $("a.leftmenu_close").show();
    $(".cssBody").css("left", ""); //오른쪽 부분 원상복귀
    $(".cssBody").css("padding-left", ""); //오른쪽 부분 복귀
    $(".left_close_open_btn a").css("left", "");
    $(".left_close_open_btn a").css("margin-left", "");
    $(".header").css("width", "");
    $(".left").css("top", "80px");
    setTimeout("gridView.resetSize();", 100);
    setTimeout("M2GridView.resetSize();", 100);
    setTimeout("M3GridView.resetSize();", 100);
    setTimeout("MtreeView.resetSize();", 100);
    setTimeout("treeView.resetSize();", 100);


}

function left7openView() {
    $(".left").show();
    $(".left").css("top", "50px");
    $("a.leftmenu7_open").hide();
    $("a.leftmenu_open").hide();
    $("a.leftmenu_close").hide();
    $("a.leftmenu7_close").show();
    $(".cssBody").css("left", ""); //오른쪽 부분 원상복귀
    $(".cssBody").css("padding-left", ""); //오른쪽 부분 복귀
    $(".left_close_open_btn a").css("left", "");
    $(".left_close_open_btn a").css("margin-left", "");
    $(".header").css("width", "");
    setTimeout("gridView.resetSize();", 100);
    setTimeout("M2GridView.resetSize();", 100);
    setTimeout("M3GridView.resetSize();", 100);
    setTimeout("MtreeView.resetSize();", 100);
    setTimeout("treeView.resetSize();", 100);

}

function left7closeView() {
    $(".left").hide();
    $(".cssBody").css("left", "0%");
    $(".cssBody").css("padding-left", "0");
    $("a.leftmenu_open").hide();
    $("a.leftmenu7_open").show();
    $("a.leftmenu_close").hide();
    $("a.leftmenu7_close").hide();
    $(".left_close_open_btn a").css("left", "0%");
    $(".left_close_open_btn a").css("margin-left", "0");
    $(".header").css("width", "100%");
    setTimeout("gridView.resetSize();", 100);
    setTimeout("M2GridView.resetSize();", 100);
    setTimeout("M3GridView.resetSize();", 100);
    setTimeout("MtreeView.resetSize();", 100);
    setTimeout("treeView.resetSize();", 100);


}

function bigView() {
    $(".header").hide();
    $(".left").hide();
    $(".tab1_wrap").hide();
    $(".cssBody").css("left", "0%");
    $(".cssBody").css("top", "50px");
    $(".nav").css("top", "0");
    $(".cssBody").css("padding-left", "0");
    $("a.small_open").show();
    $("a.big_close").hide();
    /*$(".zeta-menu-bar").show();*/
    $("a.leftmenu7_open").show();
    $("a.leftmenu_close").hide();
    $("a.leftmenu_open").hide();
    $(".left_close_open_btn a").css("left", "0%");
    $(".left_close_open_btn a").css("margin-left", "0");
    $("a.leftmenu_open").show();
    $("a.leftmenu_close").hide();
    /*$(".header").css("width","100%");*/
    setTimeout("gridView.resetSize();", 100);
    setTimeout("M2GridView.resetSize();", 100);
    setTimeout("M3GridView.resetSize();", 100);
    setTimeout("MtreeView.resetSize();", 100);
    setTimeout("treeView.resetSize();", 100);


}

function smallView() {
    $(".header").show();
    $(".left").show();
    $(".tab1_wrap").show();
    $(".left").css("top", "80px");
    $(".cssBody").css("left", "");
    $(".cssBody").css("top", "80px");
    $(".nav").css("top", "");
    $(".cssBody").css("padding-left", "");
    $("a.small_open").hide();
    $("a.big_close").show();
    /*$(".zeta-menu-bar").show();*/
    $("a.leftmenu7_open").hide();
    $("a.leftmenu_close").show();
    $("a.leftmenu7_close").hide();
    $("a.leftmenu_open").hide();
    $(".left_close_open_btn a").css("left", "");
    $(".left_close_open_btn a").css("margin-left", "");


    /*$(".header").css("width","");*/
    setTimeout("gridView.resetSize();", 100);
    setTimeout("M2GridView.resetSize();", 100);
    setTimeout("M3GridView.resetSize();", 100);
    setTimeout("MtreeView.resetSize();", 100);
    setTimeout("treeView.resetSize();", 100);
}

function exp_listView() {
    $(".explorer_list").css("display", "");
    $(".explorer_grid").css("display", "none");
    $(".exp_detail").css("display", "");

    setTimeout("gridView.resetSize();", 100);
    setTimeout("M2GridView.resetSize();", 100);
    setTimeout("M3GridView.resetSize();", 100);
    setTimeout("MtreeView.resetSize();", 100);
    setTimeout("treeView.resetSize();", 100);
}

function exp_gridView() {
    $(".explorer_list").css("display", "none");
    $(".explorer_grid").css("display", "block");
    $(".exp_detail").css("display", "none");

    setTimeout("gridView.resetSize();", 100);
    setTimeout("M2GridView.resetSize();", 100);
    setTimeout("M3GridView.resetSize();", 100);
    setTimeout("MtreeView.resetSize();", 100);
    setTimeout("treeView.resetSize();", 100);
}


var fnObj = {
    pageStart : function () {
        /*var _this = this;
        $.ajax({
            url: "/assets/js/column_info/ad00101.js",‰
            dataType: "script",
            async : false,
            success: function(){}
        });
        _this.searchView.initView();
        _this.gridView01.initView();*/



        $(function () {
            $(".zeta-menu li").hover(function () {
                $('ul:first', this).show();
            }, function () {
                $('ul:first', this).hide();
            });
            $(".zeta-menu>li:has(ul)>a").each(function () {
                $(this).html($(this).html() + ' &or;');
            });
            $(".zeta-menu ul li:has(ul)")
                .find("a:first")
                .append("<p style='float:right;margin:-3px'>&#9656;</p>");
        });


        $(function () {
            $(".exp-menu li").hover(function () {
                $('ul:first', this).show();
            }, function () {
                $('ul:first', this).hide();
            });
            $(".exp-menu>li:has(ul)>a").each(function () {
                $(this).html($(this).html() + ' &dtrif;');
            });
            $(".exp-menu ul li:has(ul)")
                .find("a:first")
                .append("<p style='float:left;margin:-3px'>&#9656;</p>");
        });

        $(function () {
            $(".tab1 span").click(function () {
                $(".tab1 span").removeClass();
                $(this).addClass("on");
            });
        });

        /*context menu 테스트*/
        var menu = new ax5.ui.menu({
            position: "absolute", // default position is "fixed"
            icons: {
                'arrow': '<i class="fa fa-caret-right"></i>'
            },
            items: [
                {
                    icon: '<i class="fa fa-comment"></i>',
                    label: "Menu A",
                    items: [
                        {icon: '<i class="fa fa-hand-peace-o"></i>', label: "Menu A-0"},
                        {icon: '<i class="fa fa-hand-rock-o"></i>',label: "Menu A-1"},
                        {icon: '<i class="fa fa-hand-stop-o"></i>',label: "Menu A-2"}
                    ]
                },
                {
                    icon: '<i class="fa fa-comments"></i>',
                    label: "Menu B",
                    items: [
                        {icon: '<i class="fa fa-pencil-square"></i>', label: "Menu B-0"},
                        {icon: '<i class="fa fa-phone-square"></i>', label: "Menu B-1"},
                        {icon: '<i class="fa fa-plus-square"></i>', label: "Menu B-2"}
                    ]
                }
            ]
        });

        $("div.explorer_list>div>div").bind("contextmenu", function (e) {
            menu.popup(e);
            ax5.util.stopEvent(e);
            // e || {left: 'Number', top: 'Number', direction: '', width: 'Number'}
        });
        fnObj.treeView01.initView();
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    }
};
/*
fnObj.searchView = axboot.viewExtend(axboot.formView,{
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData);
    },
    initView : function()
    {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();
    },
    initEvent: function () {
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        console.log(data);
        return $.extend({},data);
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

})

fnObj.gridView01 = axboot.viewExtend(axboot.realGridView, {
    tagId : "realgrid01",
    initView: function ()
    {
        this.gridObj.setColumnInfo(ad00101.column_info);
        this.gridObj.setEntityName($("#realgridName").text());
        this.gridObj.makeGrid();
        this.gridObj.itemClick(function(data){
            console.log(data);
        });
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    }
});

isDataChanged = function()
{
    return (fnObj.gridView01.isChangeData() == true) 
}
*/


/**
 * treeView
 */
fnObj.treeView01 = axboot.viewExtend(axboot.treeView, {
    param: {},
    deletedList: [],
    newCount: 0,
    addRootNode: function () {
        /*
        var _this = this;
        var nodes = _this.target.zTree.getSelectedNodes();
        var treeNode = nodes[0];

        // root
        treeNode = _this.target.zTree.addNodes(null, {
            id: "_isnew_" + (++_this.newCount),
            pId: 0,
            name: "새 메뉴",
            __created__: true,
            menuGrpCd: _this.param.menuGrpCd
        });

        if (treeNode) {
            _this.target.zTree.editName(treeNode[0]);
        }
        fnObj.treeView01.deselectNode();
        */
    },
    initView: function () {
        var _this = this;

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

        this.target = axboot.treeBuilder($('[data-z-tree="tree-view-01"]'), {
            view: {
                dblClickExpand: false,
                addHoverDom: function (treeId, treeNode) {
                    /*
                    var sObj = $("#" + treeNode.tId + "_span");
                    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
                    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                        + "' title='add node' onfocus='this.blur();'></span>";
                    sObj.after(addStr);
                    var btn = $("#addBtn_" + treeNode.tId);
                    if (btn) {
                        btn.bind("click", function () {
                            _this.target.zTree.addNodes(
                                treeNode,
                                {
                                    id: "_isnew_" + (++_this.newCount),
                                    pId: treeNode.id,
                                    name: "새 메뉴",
                                    __created__: true,
                                    menuGrpCd: _this.param.menuGrpCd
                                }
                            );
                            _this.target.zTree.selectNode(treeNode.children[treeNode.children.length - 1]);
                            _this.target.editName();
                            fnObj.treeView01.deselectNode();
                            return false;
                        });
                    }
                    */
                },
                removeHoverDom: function (treeId, treeNode) {
                    //$("#addBtn_" + treeNode.tId).unbind().remove();
                }
            },
            async: {
                enable: true,
                url: "/rc/rc001/getAllNodes",
                autoParam: ["uuid"]
            },
            edit: {
                enable: true,
                editNameSelectAll: false,
                showRemoveBtn : false,
                showRenameBtn : false
            },
            callback: {
                beforeDrag: function () {
                    //return false;
                },
                onClick: function (e, treeId, treeNode, isCancel) {
                    ACTIONS.dispatch(ACTIONS.TREEITEM_CLICK, treeNode);
                },
                onRename: function (e, treeId, treeNode, isCancel) {
                    treeNode.__modified__ = true;
                },
                onRemove: function (e, treeId, treeNode, isCancel) {
                    if (!treeNode.__created__) {
                        treeNode.__deleted__ = true;
                        _this.deletedList.push(treeNode);
                    }
                    fnObj.treeView01.deselectNode();
                }
            }
        }, []);
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
        for(var i = 0; i < _tree.length; i++)
        {
            treeData = _tree[i];
            if(treeData["parentUuid"] == null)
            {
                treeData.children = matchingData(treeData["uuid"],_tree);
                treeList.push(treeData);
            }
        }
        this.target.setData(_tree);

        if (_data && typeof _data.uuid !== "undefined") {
            // selectNode
            (function (_tree, _keyName, _key) {
                var nodes = _tree.getNodes();
                var findNode = function (_arr) {
                    var i = _arr.length;
                    while (i--) {
                        if (_arr[i][_keyName] == _key) {
                            _tree.selectNode(_arr[i]);
                        }
                        if (_arr[i].children && _arr[i].children.length > 0) {
                            findNode(_arr[i].children);
                        }
                    }
                };
                findNode(nodes);
            })(this.target.zTree, "uuid", _data.uuid);
        }
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
