var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {

        return false;
    },
    TOGGLE_ASIDE: function (caller, act, data) {
        caller.frameView.toggleAside();
    },

    MENU_OPEN: function (caller, act, data) {
        caller.tabView.open(data);
    },
    TOGGLE_FULLSCREEN: function (caller, act, data) {
        caller.frameView.toggleFullScreen();
    },
    PWD_CHANGE:function(caller, act, data){
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ac003/04/save",
            async : false,
            data: $.extend({}, {pageSize: 1000},{ crntPwd:$("#crntPwd").val(), newPwd:$("#newPwd").val(), userUuid:sessionJson.userUuid }),
            callback: function (res) {
                axToast.push(axboot.getCommonMessage("AC002_03"));
                popupReset();
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
            // 직접코딩
            return false;
        }
    }
});

// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    this.topMenuView.initView();
    this.leftMenuView.initView();
    this.frameView.initView();
    this.tabView.initView();
    this.activityTimerView.initView();
};

fnObj.pageResize = function () {
    this.tabView.resize();
};

//==== 뷰들 시작
/**
 * frameView
 */
fnObj.util = {
    convertList2Tree: function (_list, _childrenKey) {
        _list = JSON.parse(JSON.stringify(_list));

        var childKey = "_id";
        var parentKey = "_pid";
        var childrenKey = _childrenKey || "children";
        var firstItemLabel = ' <i class="cqc-chevron-down"></i>';
        var seq = 0;
        var hashDigit = 3;
        var tree = [];
        var pointer = {};
        for (var i = 0, l = _list.length; i < l; i++) {
            pointer[_list[i][childKey]] = i;
            if (_list[i][parentKey] == "__root__") {
                var item = _list[i];
                item.pHash = ax5.util.setDigit("0", hashDigit);
                item.hash = ax5.util.setDigit("0", hashDigit) + "_" + ax5.util.setDigit(seq, hashDigit);

                var pushItem = {
                    name: item.label,
                    label: item.label + firstItemLabel,
                    pHash: ax5.util.setDigit("0", hashDigit),
                    hash: ax5.util.setDigit("0", hashDigit) + "_" + ax5.util.setDigit(seq, hashDigit),
                    data: {
                        label: item.label,
                        url: item.url,
                        target: item.target,
                        id: item._id
                    },
                    __subTreeLength: 0
                };
                pushItem[childrenKey] = [];

                tree.push(pushItem);
                seq++;
            }
        }
        for (var i = 0, l = _list.length; i < l; i++) {
            if (_list[i][parentKey] != "__root__") {
                var item = _list[i];

                var pItem = _list[pointer[item[parentKey]]];
                var pHash = pItem["hash"];
                var pHashs = pHash.split(/_/g);
                var pTree = tree;
                var pTreeItem;
                var __subTreeLength = (typeof pItem.__subTreeLength !== "undefined") ? pItem.__subTreeLength : 0;

                pHashs.forEach(function (T, idx) {
                    if (idx > 0) {
                        pTreeItem = pTree[Number(T)];
                        pTree = pTree[Number(T)][childrenKey];
                    }
                });

                item[childrenKey] = [];
                item["pHash"] = pHash;
                item["hash"] = pHash + "_" + ax5.util.setDigit(__subTreeLength, hashDigit);

                var pushItem = {
                    name: item.label,
                    label: item.label,
                    pHash: pHash,
                    hash: pHash + "_" + ax5.util.setDigit(__subTreeLength, hashDigit),
                    data: {
                        label: item.label,
                        url: item.url,
                        target: item.target,
                        id: item._id
                    }
                };
                pushItem[childrenKey] = [];
                pTree.push(pushItem);

                if (typeof pItem.__subTreeLength === "undefined") pItem.__subTreeLength = 1;
                else pItem.__subTreeLength++;

                pTreeItem.__subTreeLength = pItem.__subTreeLength;
            }
        }
        return tree;
    }
};

fnObj.frameView = axboot.viewExtend({
    initView: function () {
        this.target = $("#ax-frame-root");
        this.asideHandle = $("#ax-aside-handel");
        this.aside = $("#ax-frame-aside");
        this.asideHandle.on("click", function () {
            ACTIONS.dispatch(ACTIONS.TOGGLE_ASIDE);
        });

        ACTIONS.dispatch(ACTIONS.TOGGLE_ASIDE);

        this.fullScreenHandle = $("#ax-fullscreen-handel");
        this.fullScreenHandle.on("click", function () {
            ACTIONS.dispatch(ACTIONS.TOGGLE_FULLSCREEN);
        });

        if (this.aside.get(0)) {
            this.asideView.initView();
            this.asideView.print();
        }

        $("#userNm").click(function (){
            if($(".user_edit").css("display") == "block"){
                $(".user_edit").css("display", "none");
                $(".header_r a.txt").css("color", "");
            }else if($(".user_edit").css("display") == "none"){
                $(".user_edit").css("display", "block");
                $(".header_r a.txt").css("color", "#ffda29");
                popupReset();
            }
        });

        $("#btnCancel").click(function(){
            if($(".user_edit").css("display") == "block") {
                $(".user_edit").css("display", "none");
                $(".header_r a.txt").css("color", "#ffda29");
                popupReset();
            }

        });

        $("#btnOk").click(function(){
            $(".user_edit").css("display", "none");
            $(".header_r a.txt").css("color", "#ffda29");
            if(isValid() == true){
                ACTIONS.dispatch(ACTIONS.PWD_CHANGE)
            }else{
                axToast.push(axboot.getCommonMessage("AC002_02"));
            }
        });

    },
    toggleAside: function () {
        this.target.toggleClass("show-aside");
    },
    toggleFullScreen: function () {
        if (this.target.hasClass("full-screen")) {
            this.target.removeClass("full-screen");
        } else {
            this.target.addClass("full-screen");
            this.target.removeClass("show-aside");
        }

    },
    asideView: axboot.viewExtend({
        initView: function () {
            this.tmpl = $('[data-tmpl="ax-frame-aside"]').html();
        },
        print: function () {

            var menuItems = ax5.util.deepCopy(TOP_MENU_DATA);
            menuItems.forEach(function (m) {
                m.hasChildren = (m.children && m.children.length);
            });
            menuItems[0].open = true;
            this.openedIndex = 0;

            fnObj.frameView.aside
                .html(ax5.mustache.render(this.tmpl, {items: menuItems}))
                .on("click", '[data-label-index]', function () {
                    var index = this.getAttribute("data-label-index");
                    if (menuItems[index].children && menuItems[index].children.length) {
                        fnObj.frameView.asideView.open(index);
                    } else {
                        if (menuItems[index].program) {
                            ACTIONS.dispatch(ACTIONS.MENU_OPEN, $.extend({}, menuItems[index].program, {
                                menuId: menuItems[index].menuId,
                                menuNm: menuItems[index].menuNm
                            }));
                        }
                    }
                });

            menuItems.forEach(function (n, nidx) {
                var $treeTarget = fnObj.frameView.aside.find('[data-tree-holder-index="' + nidx + '"]');
                if ($treeTarget.get(0)) {
                    var treeTargetId = $treeTarget.get(0).id;
                    $.fn.zTree.init(
                        $treeTarget,
                        {
                            view: {
                                dblClickExpand: false
                            },
                            callback: {
                                onClick: function (event, treeId, treeNode, clickFlag) {
                                    var zTree = $.fn.zTree.getZTreeObj(treeTargetId);
                                    zTree.expandNode(treeNode);

                                    if (treeNode.program) {
                                        ACTIONS.dispatch(ACTIONS.MENU_OPEN, $.extend({}, treeNode.program, {
                                            menuId: treeNode.menuId,
                                            menuNm: treeNode.menuNm
                                        }));
                                    }
                                }
                            }
                        },
                        n.children
                    );
                }
            });
        },
        open: function (_index) {
            if (this.openedIndex != _index) {

                fnObj.frameView.aside.find('[data-label-index="' + this.openedIndex + '"]').removeClass("opend");
                fnObj.frameView.aside.find('[data-tree-body-index="' + this.openedIndex + '"]').removeClass("opend");

                fnObj.frameView.aside.find('[data-label-index="' + _index + '"]').addClass("opend");
                fnObj.frameView.aside.find('[data-tree-body-index="' + _index + '"]').addClass("opend");

                this.openedIndex = _index;
            }
        }
    })
});

/**
 * topMenuView
 */
fnObj.topMenuView = axboot.viewExtend({
    initView: function () {
        this.target = $("#ax-top-menu");

        var menuItems = ax5.util.deepCopy(TOP_MENU_DATA);
        // menuItems.forEach(function (n) {
        //     n.name += ' <i class="cqc-chevron-down"></i>';
        // });

        this.menu = new ax5.ui.menu({
            theme: 'axboot',
            direction: "top",
            offset: {left: 0, top: -1},
            position: "absolute",
            icons: {
                'arrow': '<i class="cqc-chevron-right"></i>'
            },
            columnKeys: {
                label: 'name',
                items: 'children'
            },
            items: menuItems
        });

        this.menu.attach(this.target);
        this.menu.onClick = function () {
            if (this.program) {
                ACTIONS.dispatch(ACTIONS.MENU_OPEN, $.extend({}, this.program, {
                    menuId: this.menuId,
                    menuNm: this.menuNm
                }));
            }
        };
        this.menu.onStateChanged = function () {
            if (this.state == 'close') {
                //console.log(this.self.getCheckValue());
            }
        };
    }
});


fnObj.leftMenuView = axboot.viewExtend({
    initView: function () {
        var _this = this;
        var menuItems = ax5.util.deepCopy(TOP_MENU_DATA);
        //console.log(menuItems);
        var column_info = [
            {
                "name": "menuUuid",
                "fieldName": "",
                "width": "150",
                "visible": false,
                "styles": {
                    "textAlignment": "near"
                },
                "header": {
                    "text": ""
                }
            },
            {
                "name": "menuName",
                "fieldName": "",
                "width": "150",
                "styles": {
                    "textAlignment": "near"
                },
                editable: false,
                "header": {
                    "text": " "
                }
            }
        ]
        this.menus = {};
        var leftMenuItems =
            {
                menus: new Array()
            };
        var menu_2_list = new Array();
        var secondList = undefined;
        for (var i = 0; i < menuItems.length; i++) {
            this.menus[menuItems[i]["menuUuid"]] = menuItems[i];
            menu_2_list = menuItems[i]["children"];

            secondList = new Array();
            for (var j = 0; j < menu_2_list.length; j++) {
                this.menus[menu_2_list[j]["menuUuid"]] = menu_2_list[j];
                secondList.push({
                    icon: 0
                    , menuUuid: menu_2_list[j]["menuUuid"]
                    , menuName: menu_2_list[j]["menuName"]
                    , program: menu_2_list[j]["program"]
                });
            }
            leftMenuItems.menus.push({
                icon: 0
                , menuUuid: menuItems[i]["menuUuid"]
                , menuName: menuItems[i]["menuName"]
                , menus: secondList
            });
            secondList = undefined;
        }
        this.gridObj = new TreeGridWrapper("realgridM1", "/assets/js/libs/realgrid");
        this.gridObj.setGridStyle("100%", "100%")
            .setColumnInfo(column_info)
            .makeGrid();
        console.log(leftMenuItems);
        this.gridObj.setTreeData(leftMenuItems, "menus", "", "icon");
        this.gridObj.expandAll();
        this.gridObj.onDataCellClicked(function (grid, index) {
            var menu = _this.menus[grid.getDataProvider().getJsonRow(index.dataRow)["menuUuid"]];
            if (menu["program"]) {
                ACTIONS.dispatch(ACTIONS.MENU_OPEN, $.extend({}, menu["program"], {
                    menuId: menu["menuId"],
                    menuNm: menu["menuNm"]
                }));
            }
        });
        this.gridObj.onKeydown(function(grid, key, ctrl, shift, alt){
            var index = grid.getCurrent();
            if(key == 13)
            {
                var menu = _this.menus[grid.getDataProvider().getJsonRow(index.dataRow)["menuUuid"]];
                if (menu["program"]) {
                    ACTIONS.dispatch(ACTIONS.MENU_OPEN, $.extend({}, menu["program"], {
                        menuId: menu["menuId"],
                        menuNm: menu["menuNm"]
                    }));
                }
            }
        });
        this.initEvent();
        //console.log(leftMenuItems);
    },
    initEvent : function()
    {
        var _this = this;
        $(".open_close.expendAll").click(function(){
            _this.gridObj.expandAll();
        });
        $(".open_close.collapseAll").click(function(){
            _this.gridObj.collapseAll();
        });
        $("#leftMenuParam").keydown(function(event){
            if(13 == event.keyCode)
                $("#searchLeftMenu").click();        
        })
        $("#searchLeftMenu").click(function(){
            if("" != $("#leftMenuParam").val())
            {
                _this.gridObj.search(["menuName"],$("#leftMenuParam").val())
            }
        });
    }
});

/**
 * tabView
 */
fnObj.tabView = axboot.viewExtend({
    target: null,
    frameTarget: null,
    limitCount: 10,
    list: axboot.def["DEFAULT_TAB_LIST"],
    initView: function () {
        this.target = $("#ax-frame-header-tab-container");
        this.frameTarget = $("#content-frame-container");
        this.print();

        this.limitCount = axboot.getConfigValue("SYS_TAB_MENU_MAX");

        var menu = new ax5.ui.menu({
            position: "absolute", // default position is "fixed"
            theme: "primary",
            icons: {
                'arrow': '▸'
            },
            items: [
                {icon: '<i class="cqc-ccw"></i>', label: 'Reload Current Tab', action: "reload"},
                /*{icon: '<i class="cqc-cancel3"></i>', label: '현재탭 닫기', action: "close"},*/
                {icon: '<i class="cqc-cancel3"></i>', label: 'Close All Tabs Except Current Tab', action: "closeAnother"},
                {icon: '<i class="cqc-cancel"></i>', label: 'Close All Tabs', action: "closeAll"}
            ]
        });

        menu.onClick = function () {
            switch (this.action) {
                case "reload":
                    fnObj.tabView.list.forEach(function (_item, idx) {
                        if (_item.status == "on") {
                            window["frame-item-" + _item.menuId].location.reload();
                            return false;
                        }
                    });
                    break;
                case "close":
                    fnObj.tabView.list.forEach(function (_item, idx) {
                        if (_item.status == "on") {
                            if (idx == 0) {
                                alert("홈 탭은 닫을 수 없습니다.");
                                return false;
                            }
                            fnObj.tabView.close(_item.menuId);
                            return false;
                        }
                    });
                    break;
                case "closeAnother":
                    fnObj.tabView.list.forEach(function (_item, idx) {
                        if (idx > 0 && _item.status != "on") {
                            fnObj.tabView.close(_item.menuId);
                        }
                    });
                    //fnObj.tabView.open(fnObj.tabView.list[0]);
                    break;
                case "closeAll":
                    fnObj.tabView.list.forEach(function (_item, idx) {
                        /*
                        2017.11.21 요구사항으로 인한 모든 화면 닫기
                          if (idx > 0) {
                         */

                        if (idx > -1) {
                            fnObj.tabView.close(_item.menuId);
                        }
                    });
                    /*
                    2017.11.21 요구사항으로 인한 모든 화면 닫기
                    fnObj.tabView.open(fnObj.tabView.list[0]);
                     */
                    break;
                default:
                    return false;
            }
        };

        this.target.on("contextmenu", '.tab-item', function (e) {
            menu.popup(e);
            ax5.util.stopEvent(e);
        });
    },
    _getItem: function (item) {
        var po = [];
        po.push('<div class="tab-item ' + item.status + '" data-tab-id="' + item.menuId + '">');
        po.push('<span data-toggle="tooltip" data-placement="bottom" title=\'' + item.menuNm + '\'>', item.menuNm, '</span>');
        /*
        2017.11.
        요구사항으로 인한 프로그램명을 메뉴명으로 변경
         */
        //po.push('<span data-toggle="tooltip" data-placement="bottom" title=\'' + item.progNm + '\'>', item.progNm, '</span>');
        po.push('<i class="cqc-cancel3" data-tab-close="true" data-tab-id="' + item.menuId + '"></i>');
        po.push('</div>');
        return po.join('');
    },
    _getFrame: function (item) {
        var po = [];
        po.push('<iframe class="frame-item ' + item.status + '" data-tab-id="' + item.menuId + '" name="frame-item-' + item.menuId + '" src="' + item.url + '" frameborder="0" framespacing="0"></iframe>');
        return po.join('');
    },
    print: function () {
        var _this = this;

        var po = [], fo = [], active_item;

        po.push('<div class="tab-item-holder">');
        po.push('<div class="tab-item-menu" data-tab-id=""></div>');
        this.list.forEach(function (_item, idx) {
            po.push(_this._getItem(_item));
            fo.push(_this._getFrame(_item));
            if (_item.status == "on") {
                active_item = _item;
            }
        });
        po.push('<div class="tab-item-addon" data-tab-id=""></div>');
        po.push('</div>');

        this.target.html(po.join(''));
        this.frameTarget.html(fo.join(''));
        this.targetHolder = this.target.find(".tab-item-holder");
        // event bind
        this.bindEvent();

        if (active_item) {
            //topMenu.setHighLightOriginID(active_item.menuId || "");
        }
    },
    open: function (item) {
        var _item;




        var findedIndex = ax5.util.search(this.list, function () {
            this.status = '';
            return this.menuId == item.menuId;
        });
        this.target.find('.tab-item').removeClass("on");
        this.frameTarget.find('.frame-item').removeClass("on");

        if (findedIndex < 0) {

            if (this.list.length >= this.limitCount) {
                axErrorToast.push(axboot.getCommonMessage("AA009"));
                return ;
            }

            this.list.push({
                menuId: item.menuId,
                id: item.id,
                progNm: item.progNm,
                menuNm: item.menuNm,
                progPh: item.progPh,
                menuParams: item.menuParams,
                url: CONTEXT_PATH + item.progPh + "?menuId=" + item.menuId + "&menuParams=" + item.menuParams,
                status: "on"
            });
            _item = this.list[this.list.length - 1];
            this.targetHolder.find(".tab-item-addon").before(this._getItem(_item));
            this.frameTarget.append(this._getFrame(_item));
        }
        else {
            _item = this.list[findedIndex];
            this.target.find('[data-tab-id="' + _item.menuId + '"]').addClass("on");
            this.frameTarget.find('[data-tab-id="' + _item.menuId + '"]').addClass("on");
            //window["frame-item-" + _item.menuId].location.reload();
        }

        if (_item) {
            //topMenu.setHighLightOriginID(_item.menuId || "");
        }

        /*if (this.list.length > this.limitCount) {
            this.close(this.list[1].menuId);
        }*/

        this.bindEvent();
        this.resize();
    },
    click: function (id, e) {
        this.list.forEach(function (_item) {
            if (_item.menuId == id) {
                _item.status = 'on';
                if (event.shiftKey) {

                    window.open(_item.url);
                }

                if (_item) {
                    //topMenu.setHighLightOriginID(_item.menuId || "");
                }
            }
            else _item.status = '';
        });
        this.target.find('.tab-item').removeClass("on");
        this.frameTarget.find('.frame-item').removeClass("on");

        this.target.find('[data-tab-id="' + id + '"]').addClass("on");
        this.frameTarget.find('[data-tab-id="' + id + '"]').addClass("on");
    },
    close: function (menuId) {
        var newList = [], removeItem;
        this.list.forEach(function (_item) {
            if (_item.menuId != menuId) newList.push(_item);
            else removeItem = _item;
        });
        /*if (newList.length == 0) {
            alert("마지막 탭을 닫을 수 없습니다");
            return false;
        }*/
        this.list = newList;
        var thisTarget = this.target.find('[data-tab-id="' + menuId + '"]');
        var _this = this;

        // 프레임 제거
        (function () {
            var $iframe = this.frameTarget.find('[data-tab-id="' + menuId + '"]'), // iframe jQuery Object
                iframeObject = $iframe.get(0),
                idoc = (iframeObject.contentDocument) ? iframeObject.contentDocument : iframeObject.contentWindow.document;

            //if (iframeObject.contentWindow.isDataChanged && iframeObject.contentWindow.isDataChanged()) {
            if (iframeObject.contentWindow.axboot.isDataChanged && iframeObject.contentWindow.axboot.isDataChanged(menuId)) {
                axDialog.confirm({
                    msg: axboot.getCommonMessage("AA006")
                }, function () {
                    if (this.key == "ok") {
                        if (iframeObject.contentWindow.ACTIONS
                            && iframeObject.contentWindow.ACTIONS.dispatch
                            && iframeObject.contentWindow.ACTIONS.CLOSE_TAB) {
                            //닫히기 전에 닫을지 설정값을 받는다 만약에 닫지 말라고 하면 닫지 않는다
                            var result = iframeObject.contentWindow.ACTIONS.dispatch(iframeObject.contentWindow.ACTIONS.CLOSE_TAB);

                            if(result)
                            {
                                thisTarget.remove();

                                $(idoc.body).children().each(function () {
                                    $(this).remove();
                                });
                                idoc.innerHTML = "";
                                $iframe
                                    .attr('src', 'about:blank')
                                    .remove();

                                // force garbarge collection for IE only
                                window.CollectGarbage && window.CollectGarbage();

                                if (removeItem && removeItem.status == 'on') {
                                    var lastIndex = _this.list.length - 1;
                                    if (lastIndex > -1) {
                                        _this.list[lastIndex].status = 'on';
                                        _this.target.find('[data-tab-id="' + _this.list[lastIndex].menuId + '"]').addClass("on");
                                        _this.frameTarget.find('[data-tab-id="' + _this.list[lastIndex].menuId + '"]').addClass("on");
                                    }
                                }

                                // check status = "on"
                                var hasStatusOn = false;
                                _this.list.forEach(function (_item) {
                                    if (_item.status == "on") hasStatusOn = true;
                                });
                                if (!hasStatusOn) {
                                    var lastIndex = _this.list.length - 1;

                                    if (lastIndex > -1) {
                                        _this.list[lastIndex].status = 'on';
                                        _this.target.find('[data-tab-id="' + _this.list[lastIndex].menuId + '"]').addClass("on");
                                        _this.frameTarget.find('[data-tab-id="' + _this.list[lastIndex].menuId + '"]').addClass("on");
                                    }
                                }
                                _this.target.find('.tooltip').remove();
                                _this.resize();
                            }


                        }
                    } else {
                        thisTarget.remove();

                        $(idoc.body).children().each(function () {
                            $(this).remove();
                        });
                        idoc.innerHTML = "";
                        $iframe
                            .attr('src', 'about:blank')
                            .remove();

                        // force garbarge collection for IE only
                        window.CollectGarbage && window.CollectGarbage();

                        if (removeItem && removeItem.status == 'on') {
                            var lastIndex = _this.list.length - 1;
                            if (lastIndex > -1) {
                                _this.list[lastIndex].status = 'on';
                                _this.target.find('[data-tab-id="' + _this.list[lastIndex].menuId + '"]').addClass("on");
                                _this.frameTarget.find('[data-tab-id="' + _this.list[lastIndex].menuId + '"]').addClass("on");
                            }
                        }

                        // check status = "on"
                        var hasStatusOn = false;
                        _this.list.forEach(function (_item) {
                            if (_item.status == "on") hasStatusOn = true;
                        });
                        if (!hasStatusOn) {
                            var lastIndex = _this.list.length - 1;

                            if (lastIndex > -1) {
                                _this.list[lastIndex].status = 'on';
                                _this.target.find('[data-tab-id="' + _this.list[lastIndex].menuId + '"]').addClass("on");
                                _this.frameTarget.find('[data-tab-id="' + _this.list[lastIndex].menuId + '"]').addClass("on");
                            }
                        }
                        _this.target.find('.tooltip').remove();
                        _this.resize();
                    }
                });
            } else {
                thisTarget.remove();

                $(idoc.body).children().each(function () {
                    $(this).remove();
                });
                idoc.innerHTML = "";
                $iframe
                    .attr('src', 'about:blank')
                    .remove();

                // force garbarge collection for IE only
                window.CollectGarbage && window.CollectGarbage();

                if (removeItem.status == 'on') {
                    var lastIndex = this.list.length - 1;
                    if (lastIndex > -1) {
                        this.list[lastIndex].status = 'on';
                        this.target.find('[data-tab-id="' + this.list[lastIndex].menuId + '"]').addClass("on");
                        this.frameTarget.find('[data-tab-id="' + this.list[lastIndex].menuId + '"]').addClass("on");
                    }
                }

                // check status = "on"
                var hasStatusOn = false;
                this.list.forEach(function (_item) {
                    if (_item.status == "on") hasStatusOn = true;
                });
                if (!hasStatusOn) {
                    var lastIndex = this.list.length - 1;

                    if (lastIndex > -1) {
                        this.list[lastIndex].status = 'on';
                        this.target.find('[data-tab-id="' + this.list[lastIndex].menuId + '"]').addClass("on");
                        this.frameTarget.find('[data-tab-id="' + this.list[lastIndex].menuId + '"]').addClass("on");
                    }
                }
                this.target.find('.tooltip').remove();
                this.resize();
            }
        }).call(this);
    },
    bindEvent: function () {
        var _this = this;
        this.target.find('.tab-item').unbind("click").bind("click", function (e) {
            if (e.target.tagName == "I") {
                _this.close(this.getAttribute("data-tab-id"));
            }
            else {
                _this.click(this.getAttribute("data-tab-id"), e);
            }
        });

        this.target.find('[data-toggle="tooltip"]').tooltip();
    },
    resize: function () {
        if (this.resizeTimer) clearTimeout(this.resizeTimer);
        this.resizeTimer = setTimeout((function () {
            var ctWidth = this.target.width();
            var tabsWidth = this.targetHolder.outerWidth();

            if (ctWidth < tabsWidth) {
                this.targetHolder.css({width: "100%"});
                this.target.find('.tab-item').css({'min-width': 'auto', width: (ctWidth / this.list.length) - 4});
            }
            else {
                this.targetHolder.css({width: "auto"});
                this.target.find('.tab-item').css({'min-width': '120px', width: "auto"});
                tabsWidth = this.targetHolder.outerWidth();
                if (ctWidth < tabsWidth) {
                    this.targetHolder.css({width: "100%"});
                    this.target.find('.tab-item').css({'min-width': 'auto', width: (ctWidth / this.list.length) - 4});
                }
            }
        }).bind(this), 100);

    }
});

/**
 * activityTimerView
 */
fnObj.activityTimerView = axboot.viewExtend({
    initView: function () {
        this.$target = $("#account-activity-timer");
        /*
         $(document.body).on("click", function () {
         fnObj.activityTimerView.update();
         });
         */
        this.update();
        setInterval(function () {
            fnObj.activityTimerView.print();
        }, 1000);
    },
    update: function () {
        this.initTime = (new Date()).getTime();
    },
    print: function () {
        var now = (new Date()).getTime(),
            D_Milli = (1000 * 60) * 60,
            M_Milli = (1000 * 60),
            S_Milli = 1000;

        var diffNum = (now - this.initTime);
        var displayTime = [];
        var hh, mi, ss;

        if (diffNum > D_Milli) {
            hh = Math.floor(diffNum / D_Milli);
            displayTime.push(ax5.util.setDigit(hh, 2) + ":");
            diffNum -= hh * D_Milli;
        }
        if (diffNum > M_Milli) {
            mi = Math.floor(diffNum / M_Milli);
            displayTime.push(ax5.util.setDigit(mi, 2) + ":");
            diffNum -= mi * M_Milli;
        } else {
            displayTime.push("00:");
        }
        if (diffNum > S_Milli) {
            ss = Math.floor(diffNum / S_Milli);
            displayTime.push(ax5.util.setDigit(ss, 2));
        } else {
            displayTime.push("00");
        }

        this.$target.html(displayTime.join(""));
    }
});

/**
 * 우측 상단 버튼 전체화면
 */

function leftcloseView() {
    $(".left").hide();
    $(".ax-frame-header-tab").css("left", "0%");
    $(".ax-frame-header-tab").css("padding-left", "0");
    $(".ax-frame-contents").css("left", "0%");
    $(".ax-frame-contents").css("padding-left", "0");
    $("a.leftmenu_open").show();
    $("a.leftmenu_close").hide();
    $(".left_close_open_btn a").css("left", "0%");
    $(".left_close_open_btn a").css("margin-left", "0");
    $(".ax-frame-header-tool").css("width", "100%");
}

function leftopenView() {
    $(".left").show();
    $(".left").css("top", "50px");
    $("a.leftmenu_open").hide();
    $("a.leftmenu_close").show();
    $(".ax-frame-header-tab").css("left", "");
    $(".ax-frame-header-tab").css("padding-left", "");
    $(".ax-frame-contents").css("left", ""); //오른쪽 부분 원상복귀
    $(".ax-frame-contents").css("padding-left", ""); //오른쪽 부분 복귀
    $(".left_close_open_btn a").css("left", "");
    $(".left_close_open_btn a").css("margin-left", "");
    $(".ax-frame-header-tool").css("width", "");

}

function left7openView() {
    $(".left").show();
    $(".left").css("top", "50px");
    $("a.leftmenu7_open").hide();
    $("a.leftmenu_open").hide();
    $("a.leftmenu_close").hide();
    $("a.leftmenu7_close").show();
    $(".ax-frame-contents").css("left", ""); //오른쪽 부분 원상복귀
    $(".ax-frame-contents").css("padding-left", ""); //오른쪽 부분 복귀
    $(".left_close_open_btn a").css("left", "");
    $(".left_close_open_btn a").css("margin-left", "");
    $(".ax-frame-header-tool").css("width", "");
}

function left7closeView() {
    $(".left").hide();
    $(".ax-frame-contents").css("left", "0%");
    $(".ax-frame-contents").css("padding-left", "0");
    $("a.leftmenu_open").hide();
    $("a.leftmenu7_open").show();
    $("a.leftmenu_close").hide();
    $("a.leftmenu7_close").hide();
    $(".left_close_open_btn a").css("left", "0%");
    $(".left_close_open_btn a").css("margin-left", "0");
    $(".ax-frame-header-tool").css("width", "100%");
}

function bigView() {
    $(".ax-frame-header-tool").hide();
    $(".left").hide();
    $(".ax-frame-header-tab").hide();
    $(".ax-frame-contents").css("left", "0%");
    $(".ax-frame-contents").css("top", "50px");
    $(".ax-frame-contents").css("padding-bottom", "50px");
    $("#cssBody").css("top", "0");
    $(".ax-frame-contents").css("padding-left", "0");
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
}

function smallView() {
    $("#cssBody").css("top", "30px");
    $(".ax-frame-header-tool").show();
    $(".left").show();
    $(".ax-frame-header-tab").show();
    $(".left").css("top", "50px");

    $(".ax-frame-contents").css("left", "");
    $(".ax-frame-contents").css("top", "80px");
    $(".ax-frame-contents").css("padding-bottom", "110px");
    $(".ax-frame-contents").css("padding-left", "");
    $("a.small_open").hide();
    $("a.big_close").show();
    /*$(".zeta-menu-bar").show();*/
    $("a.leftmenu7_open").hide();
    $("a.leftmenu_close").show();
    $("a.leftmenu7_close").hide();
    $("a.leftmenu_open").hide();
    $(".left_close_open_btn a").css("left", "");
    $(".left_close_open_btn a").css("margin-left", "");
}
function popupReset(){
    $("#crntPwd").val("");
    $("#newPwd").val("");
    $("#reNewPwd").val("");

}
function popCtrl(){
    if($(".user_edit").css("display") == "block"){
        $(".user_edit").css("display", "none");
        $(".header_r a.txt").css("color", "");
    }else if($(".user_edit").css("display") == "none"){
        $(".user_edit").css("display", "block");
        $(".header_r a.txt").css("color", "#ffda29");
    }
}

function isValid() {
    var newPwd = $("#newPwd").val();
    var reNewPwd = $("#reNewPwd").val();

    if(newPwd ==  reNewPwd){
        return true;
    }else{
        return false;
    }
}