var fnObj = {};
var parentsData;
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH_TREE: function (caller, act, data) {
        axboot.ajax({
            url: "/api/v1/st/st003/getAllNodes",
            data: $.extend({}, data, {nodeType: "normal"}),
            callback: function (res) {
                fnObj.treeView01.setData({}, res.list, data);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/cl/cl003/02/list01",
            async: false,
            data: $.extend({}, {pageSize: 10000}, {aggregationUuid: data.uuid}),
            callback: function (res) {
                fnObj.gridView02.resetCurrent();
                fnObj.gridView02.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    PAGE_CONFIRM: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE, CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE, CANCEL_STATUS);
    },
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.callback(data);
        }
    },
    PAGE_CLASSIFY: function (caller, act, data) {
        if (fnObj.gridView03.getData().length < 1) {
            return;
        }
        var send = fnObj.gridView03.getData();

        for (var i = 0; i < fnObj.gridView03.getJsonData().length; i++) {
            send[i]['containerUuid'] = parentsData.containerUuid;
            // st013 용
            send[i]['inoutExceptUuid'] = parentsData.inoutExceptUuid;
            // st008 용
            send[i]['takeoutRequestUuid'] = parentsData.takeoutRequestUuid;

        }

        var url = "/api/v1/st/st003/03/save";
        if (parentsData.fromWhere == 'st013') {
            // st013 에서 공통으로 씀
            url = '/api/v1/st/st013/02/save01';
        } else if (parentsData.fromWhere == 'st008') {
            // st008 에서 공통으로 씀
            url = '/api/v1/st/st008/02/save01';
            send = {takeoutRequestUuid: parentsData.takeoutRequestUuid, st00802VOList: send};
        }
        axboot.ajax({
            type: "PUT",
            url: url,
            data: JSON.stringify(send),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_CLOSE, {classUuid: parentsData.classUuid});
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
fnObj.treeView01 = axboot.viewExtend(axboot.commonView, {
    param: {},
    deletedList: [],
    newCount: 0,
    otherParam: {},

    initView: function () {
        var _this = this;
        this.reloadFlag = false;
        this.checkFlag = false;

        var _this = this;
        this.target = axboot.treeBuilder($('[data-z-tree="tree-view-01"]'), {
            view: {
                dblClickExpand: false,
                addHoverDom: function (treeId, treeNode) {
                },
                removeHoverDom: function (treeId, treeNode) {
                }
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "uuid",
                    pIdKey: "parentUuid",
                    rootPId: 0
                }
            },
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkDisabledInherit: true,
                chkboxType: {"Y": "ps", "N": "ps"}
            },

            callback: {
                onClick: function (e, treeId, treeNode, isCancel) {
                    if (treeNode) {
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, treeNode);
                    }
                },
                onCheck: function (e, treeId, treeNode) {
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    if (treeNode.getParentNode()) {
                        if (treeNode.getParentNode().getCheckStatus().checked && !treeNode.getParentNode().getCheckStatus().half) {
                            for (var i = 0, l = treeNode.getParentNode().children.length; i < l; i++) {
                                if (treeNode.getParentNode().children[i]["arrangeRecordsResultUuid"] == undefined || treeNode.getParentNode().children[i]["arrangeRecordsResultUuid"] == null) {
                                    treeObj.setChkDisabled(treeNode.getParentNode().children[i], true, false, true);
                                }
                            }
                            fnObj.gridView03.clearData();
                            fnObj.gridView03.setData(treeObj.getNodesByFilter(function (node) {
                                return !node.chkDisabled && node.getCheckStatus().checked && !node.getCheckStatus().half;
                            }));
                            return;
                        }
                    }
                    if (treeNode.children) {
                        if (treeNode.getCheckStatus().checked) {
                            for (var i = 0, l = treeNode.children.length; i < l; i++) {
                                if (treeNode.children[i]["arrangeRecordsResultUuid"] == undefined || treeNode.children[i]["arrangeRecordsResultUuid"] == null) {
                                    //treeObj.setChkDisabled(treeNode.children[i], true, false, true);
                                    treeObj.setChkDisabled(treeNode.children[i], false, false, true);
                                }
                            }
                        }
                        fnObj.gridView03.clearData();
                        fnObj.gridView03.setData(treeObj.getNodesByFilter(function (node) {
                            //return !node.chkDisabled && node.getCheckStatus().checked && !node.getCheckStatus().half && !node.children.length != 0;
                            return !node.chkDisabled && node.checked && node.children.length == 0;
                        }));
                    }
                },
                beforeCheck: function (treeId, treeNode) {
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    if (treeNode.children) {
                        if (treeNode.getCheckStatus().checked) {
                            for (var i = 0, l = treeNode.children.length; i < l; i++) {
                                if (treeNode.children[i]["arrangeRecordsResultUuid"] == undefined || treeNode.children[i]["arrangeRecordsResultUuid"] == null) {
                                    treeObj.setChkDisabled(treeNode.children[i], false, false, true);
                                }
                            }
                            fnObj.treeView01.initStatus(treeNode.children);
                        }
                    }
                },
                onNodeCreated: function (event, treeId, treeNode) {
                    var zTree = $.fn.zTree.getZTreeObj(treeId);
                    if (_this.reloadFlag) {
                        if (_this.checkFlag) {
                            zTree.checkNode(treeNode, true, true);
                        }
                        if (!treeNode.children) {
                            zTree.reAsyncChildNodes(treeNode, "refresh");
                        }
                    }
                },
                filter: function (node) {
                    return node.check && !node.half
                },
                onAsyncSuccess: function (event, treeId, treeNode, msg) {
                    msg.list;
                    console.log(msg);
                    msg = JSON.parse(msg);
                    var _tree = msg.list;
                    var matchingData = function (key, list) {
                        var retList = new Array();
                        for (var i = 0; i < list.length; i++) {
                            if (key == list[i]["parentUuid"]) {
                                list[i].children = matchingData(list[i]["uuid"], list);
                                retList.push(list[i]);
                            }
                        }
                        return retList;
                    }

                    var treeData = undefined;
                    var treeList = new Array();
                    for (var i = 0; i < _tree.length; i++) {
                        treeData = _tree[i];
                        if (treeData["parentUuid"] == null) {
                            treeData.children = matchingData(treeData["uuid"], _tree);
                            treeList.push(treeData);
                        }
                    }

                    _this.target.setData(_this.convertTreeData(treeList));
                }
            }
        }, []);

        //initialize fuzzysearch function
        // @param0 tree id
        // @param1 하이라이트 할건지
        // @param2 expand 할건지
        fuzzySearch('ztree', true, true);
    },
    convertTreeData: function (_tree) {
        var iconObj = undefined;
        for (var i = 0; i < _tree.length; i++) {
            iconObj = this.getAggregationIcon(_tree[i]["nodeType"])
            _tree[i] = $.extend({}, _tree[i], iconObj);
            iconObj = {};
        }
        return _tree;
    },
    getAggregationIcon: function (nodeType) {
        var iconObj = {open: false, iconSkin: nodeType};

        return iconObj;
    },
    initStatus: function (nodes) {
        var treeObj = $.fn.zTree.getZTreeObj("ztree");
        nodes = nodes == null ? treeObj.getNodes() : nodes;

        for (var i = 0, l = nodes.length; i < l; i++) {
            if (nodes[i]["arrangeRecordsResultUuid"] != undefined && nodes[i]["arrangeRecordsResultUuid"] != null) {
                //treeObj.checkNode(nodes[i], true);
                treeObj.setChkDisabled(nodes[i], true, false, false);
            }
            /*if (parentsData.fromWhere == 'st013' || parentsData.fromWhere == 'st008') {
                if (nodes[i].electronicRecordStatusUuid == 'true') {
                    // 전자인 애들은 disable
                    //treeObj.checkNode(nodes[i], true);
                    treeObj.setChkDisabled(nodes[i], true, false, false);
                }
            } else {
                if (nodes[i].electronicRecordStatusUuid == 'false') {
                    // 전자가 아닌애는 disable
                    //treeObj.checkNode(nodes[i], true);
                    treeObj.setChkDisabled(nodes[i], true, false, false);
                }
            }*/

            if (nodes[i].electronicRecordStatusUuid == 'false') {
                // 전자가 아닌애는 disable
                //treeObj.checkNode(nodes[i], true);
                treeObj.setChkDisabled(nodes[i], true, false, false);
            }

            if (nodes[i].children) {
                fnObj.treeView01.initStatus(nodes[i].children);
            }
        }
    },
    setData: function (_searchData, _tree, _data) {
        this.param = $.extend({}, _searchData);


        var treeList = new Array();
        var data = undefined;


        var matchingData = function (key, list) {
            var retList = new Array();
            for (var i = 0; i < list.length; i++) {
                if (key == list[i]["parentUuid"]) {
                    list[i].children = matchingData(list[i]["uuid"], list);
                    retList.push(list[i]);
                }
            }
            return retList;

        }

        var treeData = undefined;
        _tree = this.convertTreeData(_tree);
        for (var i = 0; i < _tree.length; i++) {
            treeData = _tree[i];
            if (treeData["parentUuid"] == null) {
                treeData.children = matchingData(treeData["uuid"], _tree);
                treeList.push(treeData);
            }
        }

        this.target.setData(treeList);


        fnObj.treeView01.initStatus();
        //체크및 비활성화 처리

        /*if (_data && typeof _data.uuid !== "undefined") {
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
        }*/
    },
    updateNode: function (treeNode) {
        var treeObj = $.fn.zTree.getZTreeObj("ztree");
        treeObj.updateNode(treeNode);
        treeObj.refresh();
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
    deselectNode: function () {
        ACTIONS.dispatch(ACTIONS.TREEITEM_DESELECTE);
    },
    getSelectedData: function () {
        var treeObj = $.fn.zTree.getZTreeObj("ztree");
        return treeObj.getSelectedNodes();
    },
    getNodeByTId: function (tId) {
        var treeObj = $.fn.zTree.getZTreeObj("ztree");
        return treeObj.getNodeByTId(tId);
    },
    moveNode: function (targetNode, treeNode) {
        var treeObj = $.fn.zTree.getZTreeObj("ztree");
        return treeObj.moveNode(targetNode, treeNode, "inner", false);
    },
    getNodeByParam: function (key, value) {
        var treeObj = $.fn.zTree.getZTreeObj("ztree");
        return treeObj.getNodeByParam(key, value, null);
    }
});

fnObj.pageStart = function () {
    var _this = this;
    parentsData = parent.axboot.modal.getData();

    $.ajax({
        url: "/assets/js/controller/simple_controller.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/st00301_p01_01.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/st00301_p01_02.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/cl00301_p01_02.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/libs/zTree_v3-master/css/zTreeStyle/zTreeStyle.css",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();

    fnObj.treeView01.initView();
    _this.gridView02.initView();
    _this.gridView03.initView();

    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH_TREE, this.formView.getData());
    //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    $("span[data-ax-path='popUpContainerName']").html(parentsData.description);
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

        if (parentsData.confirmBtn)
            $(".btn_main_txt01").text(parentsData.confirmBtn);
        //
        // $(".sltCont").text(parentsData.crrntAgg);
        //
        // $(".btn_small").click(function(){
        //    if(this.textContent == "Save"){
        //        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
        //        this.textContent = "Edit";
        //        $("#classDescription").prop("readonly",true);
        //        $("#levelOfDetailUuid").prop("disabled",true);
        //        $("#statusDescription").prop("disabled", true);
        //        $("#classDescription").css("background","#ffffff");
        //    }else if(this.textContent == "Edit"){
        //        this.textContent = "Save";
        //        $("#classDescription").prop("readonly",false);
        //        $("#levelOfDetailUuid").prop("disabled", false);
        //        $("#statusDescription").prop("disabled", false);
        //        $("#classDescription").css("background","#fffdd6");
        //    }
        // });
        $(".open_close.collapseAll").click(function () {
            _this.gridObj.collapseAll();
        });
        $(".btn_exclude").click(function () {
            exportItemList();
        });
        $(".btn_include").click(function () {
            importItemList();
        });
        $(".btn_classify").click(function () {
            ACTIONS.dispatch(ACTIONS.PAGE_CLASSIFY);
        });

        $(".close_popup").click(function () {
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
        });


        var accordion = {
            click: function (target) {
                var $target = $(target);
                $target.on('click', function () {

                    if ($(this).hasClass('on')) {
                        slideUp($target);
                    } else {
                        slideUp($target);
                        $(this).addClass('on').next().slideDown();
                    }

                    function slideUp($target) {
                        $target.removeClass('on').next().slideUp();
                    }

                });
            }
        };
        accordion.click('.accordion > ul');
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        return $.extend({}, data);
    },
    setFormData: function (dataPath, value) {
        this.model.set(dataPath, value);
    },
    getFormData: function (dataPath) {
        return this.model.get(dataPath);
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
    },
    expandAll: function () {
        fnObj.gridView01.gridObj.expandAll();
    },
    collapseAll: function () {
        fnObj.gridView01.gridObj.collapseAll();
    }
});

fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid02",
    entityName: "arrangeRecordsResultUuid",
    primaryKey: "arrangeRecordsResultUuid",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00301_p01_02.column_info);
        // this.gridObj.setFixedOptions({
        //     colCount: 3
        // });
        this.makeGrid();
    },
    isChangeData: function () {
        if (this.getData().length > 0) {
            return true;
        } else {
            return false;
        }
    },
    getRowData: function () {
        return this.gridObj.getSelectedData();
    },
    itemClick: function (data, index) {

    },
    onItemChecked: function (grid, itemIndex, checked) {
        // alert("adfaf");
    }

});

fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid03",
    initView: function () {
        this.gridObj = new TreeGridWrapper("realgrid03", "/assets/js/libs/realgrid", true);
        this.gridObj.style.body = {
            borderRight: "#ccc,1px",
            borderBottom: "#ccc,1px",
            line: "#ffaaaaaa,0px"
        };
        this.gridObj.setGridStyle("100%", "100%")
            .setOption({
                header: {visible: true},
                lineVisible: false
            });
        this.gridObj.setColumnInfo(cl00301_p01_02.column_info).makeGrid();
        this.gridObj.setDisplayOptions({
            fitStyle: "evenFill"
        });

        this.bindEvent();
    },
    isChangeData: function () {
        if (this.getData().length > 0) {
            return true;
        } else {
            return false;
        }
    },
    getRowData: function () {
        return this.gridObj.getSelectedData();
    },
    itemClick: function (data, index) {
    },
    getData: function () {
        if (this.gridObj.getJsonRows().length < 1) return;

        var _tree = this.gridObj.getJsonRows();

        for (var i = 0; i < _tree.length; i++) {
            _tree[i]["choiceYn"] = 'Y';
        }

        var _newTree = [];
        var convertList = function (_tree) {
            _tree.forEach(function (n, nidx) {
                var item = {};
                item = {
                    title: n.name,
                    aggregationUuid: n.uuid,
                    classifyRecordsUuid: n.classifyRecordsUuid == undefined ? '' : n.classifyRecordsUuid,
                    choiceYn: n.choiceYn == undefined ? 'N' : n.choiceYn
                };
                if (n.rows) convertList(n.rows);
                _newTree.push(item);
            });
            return _newTree;
        };
        var newTree = convertList(_tree);
        return newTree;
    },
    setData: function (list) {
        var data = {
            "children": list
        }

        //실제 여기에
        this.gridObj.setTreeDataForJSON(data, "children", "", "icon")
        // this.gridObj.setTreeDataForArray(list, "orderKey1");
    },
    getJsonData: function () {
        return this.gridObj.getJsonRows();
    }
});
/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}
 */

isDataChanged = function () {
    if (fnObj.gridView01.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}
exportItemList = function () {
    if (fnObj.gridView02.gridObj.getCheckedList().length > 0) {
        //gridView03 목록에 추가
        fnObj.gridView03.setData(fnObj.gridView02.gridObj.getCheckedList());
        // fnObj.gridView02.gridObj.onItemChecked()
        // var getCheckedRows = fnObj.gridView02.gridObj.getCheckedRows();
        // fnObj.gridView02.gridObj.setCheckable(0,false);
        // fnObj.gridView02.gridObj.getcheck
        // fnObj.gridView02.gridObj.setCustomCellStyleRows("disable",function(row){
        //
        //     if(row["statusUuid"] == state)
        //         return true;
        //     else
        //         return false;
        // },["containerName","parentContainerName","containerTypeUuid","controlNumber","provenance","creationStartDate","creationEndDate","description","orderNo"]);
    } else {
        alert("Select Arrange Item List")
    }
}
importItemList = function () {
    alert("import");
}

setCheckable = function (rows) {
    // for()
}


/**
 * http://www.treejs.cn/v3/demo.php#_516 << 여기 참조
 * 해당 기능 사용 하기위해서는, frame.js 에 jquery.ztree.exhide.js 꽂아야됨.
 * @param zTreeId the ztree id used to get the ztree object
 * @param searchField selector of your input for fuzzy search
 * @param isHighLight whether highlight the match words, default true
 * @param isExpand whether to expand the node, default false
 *
 * @returns
 */
function fuzzySearch(zTreeId, isHighLight, isExpand) {
    var zTreeObj = $.fn.zTree.getZTreeObj(zTreeId);//get the ztree object by ztree id
    if (!zTreeObj) {
        alert("fail to get ztree object");
    }
    var nameKey = zTreeObj.setting.data.key.name; //get the key of the node name
    isHighLight = isHighLight === false ? false : true;//default true, only use false to disable highlight
    isExpand = isExpand ? true : false; // not to expand in default
    zTreeObj.setting.view.nameIsHTML = isHighLight; //allow use html in node name for highlight use

    var metaChar = '[\\[\\]\\\\\^\\$\\.\\|\\?\\*\\+\\(\\)]'; //js meta characters
    var rexMeta = new RegExp(metaChar, 'gi');//regular expression to match meta characters

    // keywords filter function
    function ztreeFilter(zTreeObj, _keywords, callBackFunc) {
        if (!_keywords) {
            _keywords = ''; //default blank for _keywords
        }

        // function to find the matching node
        function filterFunc(node) {
            if (node && node.oldname && node.oldname.length > 0) {
                node[nameKey] = node.oldname; //recover oldname of the node if exist
            }
            zTreeObj.updateNode(node); //update node to for modifications take effect
            if (_keywords.length == 0) {
                //return true to show all nodes if the keyword is blank
                zTreeObj.showNode(node);
                zTreeObj.expandNode(node, isExpand);
                return true;
            }
            //transform node name and keywords to lowercase
            if (node[nameKey] && node[nameKey].toLowerCase().indexOf(_keywords.toLowerCase()) != -1) {
                if (isHighLight) { //highlight process
                    //a new variable 'newKeywords' created to store the keywords information
                    //keep the parameter '_keywords' as initial and it will be used in next node
                    //process the meta characters in _keywords thus the RegExp can be correctly used in str.replace
                    var newKeywords = _keywords.replace(rexMeta, function (matchStr) {
                        //add escape character before meta characters
                        return '\\' + matchStr;
                    });
                    node.oldname = node[nameKey]; //store the old name
                    var rexGlobal = new RegExp(newKeywords, 'gi');//'g' for global,'i' for ignore case
                    //use replace(RegExp,replacement) since replace(/substr/g,replacement) cannot be used here
                    node[nameKey] = node.oldname.replace(rexGlobal, function (originalText) {
                        //highlight the matching words in node name
                        var highLightText =
                            '<span style="color: whitesmoke;background-color: darkred;">'
                            + originalText
                            + '</span>';
                        return highLightText;
                    });
                    zTreeObj.updateNode(node); //update node for modifications take effect
                }
                zTreeObj.showNode(node);//show node with matching keywords
                return true; //return true and show this node
            }

            zTreeObj.hideNode(node); // hide node that not matched
            return false; //return false for node not matched
        }

        var nodesShow = zTreeObj.getNodesByFilter(filterFunc); //get all nodes that would be shown
        processShowNodes(nodesShow, _keywords);//nodes should be reprocessed to show correctly
    }

    /**
     * reprocess of nodes before showing
     */
    function processShowNodes(nodesShow, _keywords) {
        if (nodesShow && nodesShow.length > 0) {
            //process the ancient nodes if _keywords is not blank
            if (_keywords.length > 0) {
                $.each(nodesShow, function (n, obj) {
                    var pathOfOne = obj.getPath();//get all the ancient nodes including current node
                    if (pathOfOne && pathOfOne.length > 0) {
                        //i < pathOfOne.length-1 process every node in path except self
                        for (var i = 0; i < pathOfOne.length - 1; i++) {
                            zTreeObj.showNode(pathOfOne[i]); //show node
                            zTreeObj.expandNode(pathOfOne[i], true); //expand node
                        }
                    }
                });
            } else { //show all nodes when _keywords is blank and expand the root nodes
                var rootNodes = zTreeObj.getNodesByParam('level', '0');//get all root nodes
                $.each(rootNodes, function (n, obj) {
                    zTreeObj.expandNode(obj, true); //expand all root nodes
                });
            }
        }
    }

    // //listen to change in input element
    // $(searchField).bind('input propertychange', function() {
    //     var _keywords = $(this).val();
    //     searchNodeLazy(_keywords); //call lazy load
    // });
    //
    // var timeoutId = null;
    // var lastKeyword = '';
    // // excute lazy load once after input change, the last pending task will be cancled
    // function searchNodeLazy(_keywords) {
    //     if (timeoutId) {
    //         //clear pending task
    //         clearTimeout(timeoutId);
    //     }
    //     timeoutId = setTimeout(function() {
    //         if (lastKeyword === _keywords) {
    //             return;
    //         }
    //         ztreeFilter(zTreeObj,_keywords); //lazy load ztreeFilter function
    //         // $(searchField).focus();//focus input field again after filtering
    //         lastKeyword = _keywords;
    //     }, 500);
    // }

    $("#leftMenuTreeParam").keydown(function (event) {
        if (13 == event.keyCode)
            $("#searchLeftTree").click();
    })


    $("#searchLeftTree").click(function () {
        var _keywords = $("#leftMenuTreeParam").val();
        searchNodeLazy(_keywords); //call lazy load
    });

    var lastKeyword = '';

    // excute lazy load once after input change, the last pending task will be cancled
    function searchNodeLazy(_keywords) {

        if (lastKeyword === _keywords) {
            return;
        }
        ztreeFilter(zTreeObj, _keywords); //lazy load ztreeFilter function
        // $(searchField).focus();//focus input field again after filtering
        lastKeyword = _keywords;

    }
}
