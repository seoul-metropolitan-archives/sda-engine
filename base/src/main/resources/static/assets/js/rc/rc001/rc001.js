var fnObj = {};
var isDetailChange = false;
var API_SERVER = CONTEXT_PATH;

var levelLabels = null;
var levelValues = null;

$(function () {
    $("#itemTabs").tabs();
});

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        if (hideMenuRole('inquiryYn')) return;

        axboot.ajax({
            url: "/rc/rc001/getAllNodes",
            data: $.extend({}, data, {isDisplayItem: $("#isDisplayItem").prop("checked")}),
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
    GET_GRID_DATA: function (caller, act, data) {
        if (hideMenuRole('inquiryYn')) return;

        axboot.ajax({
            //url: "/rc/rc001/getGridData",
            url: "/rc/rc001/getGridDataForPaging",
            data: $.extend({}, data, fnObj.pageView.getPageInfo()),
            callback: function (res) {
                console.log(res.list);

                $.each(rc00101.column_info, function(idx, item){
                    if(item.name == "levelUuid"){
                        item.labels = levelLabels;
                        item.values = levelValues;
                        return false;
                    }
                });

                fnObj.gridView01.setColumnInfo(rc00101.column_info);
                fnObj.gridView01.mode = "grid";
                fnObj.gridView01.setData(res.list, true);
                fnObj.pageView.setPage(res);
                fnObj.gridView01.disabledColumn();
                fnObj.gridView01.gridObj.getGridView().resetSize();
                ACTIONS.dispatch(ACTIONS.GET_NAVI_DATA, data);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    GET_SUBDATA: function (caller, act, data) {
        if (hideMenuRole('inquiryYn')) return;

        console.log(data);
        axboot.ajax({
            url: "/rc/rc001/getAllNodeForPaging",
            data: $.extend({}, data,
                {isDisplayItem: true}
                ,
                fnObj.pageView.getPageInfo()
            ),
            async: false,
            callback: function (res) {
                fnObj.iconView.setData(res.list, data.uuid == "");
                fnObj.pageView.setPage(res);
                ACTIONS.dispatch(ACTIONS.GET_NAVI_DATA, data);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    GET_SEARCH_DATA: function (caller, act, data) {
        if (hideMenuRole('inquiryYn')) return;

        var formData = fnObj.formView.getData();
        var searchData = {};
        searchData["level"] = formData["searchLevelUuid"];
        searchData["nodeType"] = formData["searchTypeUuid"];
        searchData["publishedStatus"] = formData["searchPublishedStatusUuid"];
        searchData["author"] = formData["searchAuthor"];
        searchData["title"] = formData["searchTitle"];
        searchData["parentUuid"] = fnObj.naviView.getCurrent()["uuid"];

        var startDate = formData["descriptionDateFrom"] ? formData["descriptionDateFrom"] : "";
        var endDate = formData["descriptionDateTo"] ? formData["descriptionDateTo"] : "";

        startDate = startDate.replace(/-/gi, "");
        endDate = endDate.replace(/-/gi, "");

        if(startDate.length == 4){
            searchData["descriptionStartDate"] = startDate + "0101";
        }else if(startDate.length == 6){
            searchData["descriptionStartDate"] = startDate + "01";
        }else{
            searchData["descriptionStartDate"] = startDate;
        }

        if(endDate.length == 4){
            searchData["descriptionEndDate"] = endDate + "1231";
        }else if(endDate.length == 6){
            searchData["descriptionEndDate"] = endDate + "31";
        }else{
            searchData["descriptionEndDate"] = endDate;
        }
        
        axboot.ajax({
            //url: "/rc/rc001/getGridData",
            url: "/rc/rc001/search",
            data: $.extend({}, searchData, fnObj.pageView.getPageInfo()),
            callback: function (res) {
                console.log(res.list);
                fnObj.gridView01.setColumnInfo(rc00107.column_info);
                fnObj.gridView01.mode = "search";
                fnObj.gridView01.setData(res.list, true);
                fnObj.pageView.setPage(res);
                fnObj.gridView01.gridObj.getGridView().resetSize();

                $("#searchView").hide();
                $("#searchResultView").show();
                $("#searchLabel").html("<span style='color: orangered; font-weight: bold'>\"" + searchData["title"] + "\"</span>의 검색결과(총 " + res["limit"].toLocaleString() +"건)&nbsp;&nbsp;&nbsp;");
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    GET_AGGREGATION_INFO: function (caller, act, data) {
        axboot.ajax({
            url: "/rc/rc001/getAggregationInfo",
            data: data,
            callback: function (res) {
                //fnObj.detailView.setData(res);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    GET_ITEM_INFO: function (caller, act, data) {
        axboot.ajax({
            url: "/rc/rc001/getItemInfo",
            data: data,
            callback: function (res) {
                fnObj.detailView.setData(res);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    GET_NAVI_DATA: function (caller, act, data) {
        axboot.ajax({
            url: "/rc/rc001/getNaviData",
            data: data,
            callback: function (res) {
                fnObj.naviView.setList(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    DELETE_AGGREGATION: function (caller, act, list) {
        if (hideMenuRole('saveYn')) return;

        axboot.ajax({
            url: "/rc/rc001/deleteAggregation",
            data: JSON.stringify(list),
            dataType: "JSON",
            type: "POST",
            callback: function (res) {
                if (res.status == -500)
                    axWarningToast.push(axboot.getCommonMessage(res.message));
                else {
                    axToast.push(axboot.getCommonMessage(res.message));
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH, fnObj.naviView.getRoot());
                    if ($(".explorer_grid").css("display") == "none")
                        ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());
                    else
                        ACTIONS.dispatch(ACTIONS.GET_GRID_DATA, fnObj.naviView.getCurrent());
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SAVE: function (caller, act, list) {
        if (hideMenuRole('saveYn')) return;

        if (!fnObj.gridView01.gridObj.validate()) {
            return false;
        } else {
            var data = fnObj.gridView01.getData();

            $.each(data, function(idx, item){
                if(item["creationStartDate"] != null)
                    item["creationStartDate"] = item["creationStartDate"].replace(/-/gi, "");
                if(item["creationEndDate"] != null)
                    item["creationEndDate"] = item["creationEndDate"].replace(/-/gi, "");

                if(item["accumulationStartDate"] != null)
                    item["accumulationStartDate"] = item["accumulationStartDate"].replace(/-/gi, "");
                if(item["accumulationEndDate"] != null)
                    item["accumulationEndDate"] = item["accumulationEndDate"].replace(/-/gi, "");

                if(item["descriptionStartDate"] != null)
                    item["descriptionStartDate"] = item["descriptionStartDate"].replace(/-/gi, "");
                if(item["descriptionEndDate"] != null)
                    item["descriptionEndDate"] = item["descriptionEndDate"].replace(/-/gi, "");

            });

            axboot.ajax({
                url: "/rc/rc001/saveRecords",
                data: JSON.stringify(data),
                dataType: "JSON",
                type: "POST",
                callback: function (res) {
                    if (res.status == -500) {
                        axWarningToast.push(res.message);
                    } else {
                        axboot.getCommonMessage("AA007");
                        ACTIONS.dispatch(ACTIONS.GET_GRID_DATA, fnObj.naviView.getCurrent());
                    }
                },
                options: {
                    onError: axboot.viewError
                }
            });
        }

    },
    ITEMS_SAVE: function (caller, act, list) {

        if (hideMenuRole('saveYn')) return;
        //if(!isDetailChange) return;

        axboot.ajax({
            url: "/rc/rc001/save",
            data: JSON.stringify(list),
            dataType: "JSON",
            type: "POST",
            callback: function (res) {
                if (res.status == -500) {
                    axWarningToast.push(res.message);
                } else {
                    axboot.getCommonMessage("AA007");
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    ITEM_SAVE: function (caller, act, data) {

        if (hideMenuRole('saveYn')) return;
        //if(!isDetailChange) return;

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/rc004/01/saveItemDetails",
            data: JSON.stringify(data),
            callback: function (res) {
                //axToast.push(axboot.getCommonMessage("AA007"));
                ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    AGG_SAVE: function (caller, act, data) {
        var saveData =
            {
                systemMeta: data,
                contextualMeta: null,
                childrenAggregationList: null,
                referenceAggregationList: null,
                referenceItemList: null
            }


        axboot.ajax({
            url: "/api/v1/rc/rc002/save",
            dataType: "JSON",
            type: "POST",
            data: JSON.stringify(saveData),
            callback: function (res) {
                //axToast.push(axboot.getCommonMessage("AA007"));
                ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());
            },
            options: {
                onError: axboot.viewError
            }
        });

    },
    PAGE_INGEST: function (caller, act, data) {
        axboot.commonModal.open({
            modalType: "INGEST_POPUP",
            header: {
                title: "INGEST"
            },
            sendData: function () {
                return data;
            },
            callback: function (data) {
                if (this) this.close();
                UPLOAD.send();
            }
        });
    },
    PAGE_INGEST_LIST: function (caller, act, data) {
        axboot.modal.open({
            modalType: "INGEST_LIST_POPUP",
            header: {
                title: "INGEST LIST"
            },
            sendData: function () {
            },
            callback: function (data) {
                window.axModal.activeModal.remove();
                window.axModal.activeModal = null;

                ACTIONS.dispatch(ACTIONS.PAGE_INGEST, data);

            }
        });
    },
    INGEST_ARCHIVE: function (caller, act, data) {

        var uploadFiles = UPLOAD.uploadedFiles;

        axboot
            .call({
                type: "PUT",
                url: "/api/v1/wf999/01/save",
                data: JSON.stringify({
                    raAggregationUuid: fnObj.naviView.getCurrent()["uuid"],
                    rc00502VoList: uploadFiles
                }),
                callback: function (res) {
                    UPLOAD.uploadedFiles = [];
                    axToast.push(axboot.getCommonMessage("AA007"));
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    ACTIONS.dispatch(ACTIONS.GET_SUBDATA, {uuid: fnObj.naviView.getCurrent()["uuid"]});
                }
            })
            .done(function () {
                //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                //axToast.push(axboot.getCommonMessage("AA007"));
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
}

function exp_listView() {
    $(".explorer_list").css("display", "");
    $(".explorer_grid").css("display", "none");
    //$(".exp_detail").css("display", "");
    fnObj.pageView.resetPage();
    fnObj.pageView.setPageSize(126);
    ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());

    $("#searchView").show();
    $("#searchResultView").hide();
}

function exp_gridView(isSearch) {
    $(".explorer_list").css("display", "none");
    $(".explorer_grid").css("display", "block");
    //$(".exp_detail").css("display", "none");
    fnObj.pageView.resetPage();
    fnObj.pageView.setPageSize(100);
    if(isSearch) {
        ACTIONS.dispatch(ACTIONS.GET_SEARCH_DATA, null);
    }else{
        $("#searchView").show();
        $("#searchResultView").hide();
        ACTIONS.dispatch(ACTIONS.GET_GRID_DATA, fnObj.naviView.getCurrent());
    }

}

/**
 * Drop 완료 시 Aggregation 및 Record 영역 Update
 * @param targetUi Drag요소
 * @param parentNode 옮겨질 Aggregation
 * @returns {boolean}
 */
function updateRecord(targetData, parentNode, isTree) {
    var targetNode = null;

    if (isTree == null) {
        isTree = false;
    }

    var targetUuid = "";
    var targetNodeType = "";
    var currentUuid = "";   //현재 Record 화면에 로딩된 Aggregation UUID를 가져옴

    if (isTree) {
        targetUuid = targetData["uuid"];
        targetNodeType = targetData["nodeType"];
        currentUuid = targetData["parentUuid"];
    } else {
        targetUuid = targetData.attr("uuid");
        targetNodeType = targetData.attr("nodeType");
        currentUuid = fnObj.naviView.getCurrent()["uuid"];
    }

    if (!targetData || !parentNode) {
        axDialog.alert({
            title: 'Information',
            theme: "default",
            msg: "정보가 갱신되었습니다.\nAggregation 정보를 다시 가져옵니다."
        }, function () {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        });
        return [];
    }

    if (parentNode["nodeType"] == "virtual") {
        axDialog.alert("Virtual Aggregation으로의 이동은 불가능합니다.");
        return false;
    } else if (targetNodeType == "virtual") {
        axDialog.alert("Virtual Aggregation은 이동이 불가능합니다.");
        return false;
    }

    if (targetNodeType == "item") {
        if (fnObj.naviView.getCurrent()["nodeType"] == "virtual") {
            axDialog.alert("Virtual Aggregation의 Item은 \n이동이 불가능합니다.");
        }

        if (parentNode["nodeType"] == "normal" && fnObj.naviView.getCurrent()["nodeType"] == "temp") {
            axDialog.alert("Aggregation Type이 다른 \nItem 이동은 불가능합니다.");
            return false;
        } else if (parentNode["nodeType"] == "temporary" && fnObj.naviView.getCurrent()["nodeType"] == "normal") {
            axDialog.alert("Aggregation Type이 다른 \nItem 이동은 불가능합니다.");
            return false;
        }
    } else if (parentNode["nodeType"] == "normal" && targetNodeType == "temporary") {
        axDialog.alert("Aggregation Type이 다른 \nAggregation 이동은 불가능합니다.");
        return false;
    } else if (parentNode["nodeType"] == "temporary" && targetNodeType == "normal") {
        axDialog.alert("Aggregation Type이 다른 \nAggregation 이동은 불가능합니다.");
        return false;
    }

    if (parentNode["nodeType"] == "normal") {
        if (targetNodeType == "temporary" || targetNodeType == "virtaul") {
            axDialog.alert("Aggregation Type이 동일해야합니다.");
            return false;
        } else if (targetNodeType == "item") {

        }
    }

    var parentUuid = parentNode["uuid"];
    var reqList = null;
    var selectedData = fnObj.iconView.getSelectedData();

    if (targetUuid == parentUuid) {
        axDialog.alert("Aggregation 코드가 같습니다.");
        return false;
    } else if (parentUuid == currentUuid) {
        axDialog.alert("동일한 Aggregation으로 이동할 수 없습니다.");
        return false;
    }

    if (selectedData.length == 0) {
        reqList = [{
            uuid: targetUuid,
            parentUuid: parentUuid,
            nodeType: targetNodeType,
            parentNodeType: parentNode["nodeType"]
        }];
    } else {
        reqList = [];
        $.each(selectedData, function (idx, item) {
            reqList.push({
                uuid: item.uuid,
                parentUuid: parentUuid,
                nodeType: item.nodeType,
                parentNodeType: parentNode["nodeType"]
            });
        });
    }

    axboot.ajax({
        url: "/rc/rc001/save",
        data: JSON.stringify(reqList),
        dataType: "JSON",
        type: "POST",
        callback: function (res) {
            if (res.status == -500) {
                axDialog.alert(res.message);
            } else {
                if (selectedData.length == 0) {
                    if (!isTree)
                        targetData.remove();
                } else {
                    $("#iconListArea >div.selected").remove();
                }

                $.each(selectedData, function (idx, item) {
                    if(item.nodeType == "item") return true;

                    var currentNode = fnObj.treeView01.getNodeByParam("uuid", item.uuid);
                    fnObj.treeView01.moveNode(parentNode, currentNode);
                });

                axboot.getCommonMessage("AA007");
            }
        },
        options: {
            onError: axboot.viewError
        }
    });


    $('#explorerDragWrapper').remove();

    /*if($(".explorer_grid").css("display")=="none")
        ACTIONS.dispatch(ACTIONS.GET_SUBDATA,reqData);
    else
        ACTIONS.dispatch(ACTIONS.GET_GRID_DATA,reqData);*/

    return true;
}


/**
 * ContextMenu Click 시 동작
 * @param ui
 * @param treeData
 */
function contextMenuClick(ui, treeData) {
    var selectedData = fnObj.iconView.getSelectedData();
    var parentsObj = parent.window.fnObj;
    var item = null;
    var naviStr = "";
    var msg = "";
    var typeUuid = "";
    var typeName = "";
    var sendData = null;

    switch (ui.cmd) {
        case "MERGE_ITEM":
            axDialog.confirm({
                msg: "Merge를 진행 하시겠습니까?\nComponent들은 하나의 Item에 귀속됩니다."
            }, function () {
                if (this.key == "ok") {
                    axToast.push("Item이 Merge되었습니다.");
                }
            });
            break;
        case "NODE_DEL":
            selectedData = treeData ? [treeData] : fnObj.iconView.getSelectedData();

            ACTIONS.dispatch(ACTIONS.DELETE_AGGREGATION, selectedData);
            break;
        case "ITEM_ADD":
            selectedData = fnObj.naviView.getCurrent();

            if (selectedData["uuid"] == "") {
                axDialog.alert("Root위치에는 Item생성이 불가능합니다.");
                return;
            }

            var currentNode = fnObj.treeView01.getNodeByParam("uuid", selectedData["uuid"]);
            if (currentNode.nodeType == "normal" && currentNode.isParent) {
                axDialog.alert(axboot.getCommonMessage("RC001_08"));
                return;
            }

            naviStr = undefined == selectedData["name"] ? "" : " > " + selectedData["name"]
            sendData = $.extend({}, {aggregationUuid: selectedData.uuid},
                {type: "create"},
                {navi: fnObj.naviView.getPathString() + naviStr}, {title: ""},
                {nodeType: selectedData.nodeType}
            );

            openRecordServicePopup("ITEM_ADD", "Add Item", sendData);
            break;
        case "ITEM_EDIT":
            selectedData = treeData ? [treeData] : fnObj.iconView.getSelectedData();

            sendData = $.extend({}, {
                aggregationUuid: selectedData[0].parentUuid,
                itemUuid: selectedData[0].uuid
            }, {type: "update"}, {navi: fnObj.naviView.getPathString()}, {title: selectedData[0]["name"]});

            if (this.axModal.activeModal) {
                this.axModal.activeModal.remove();
                this.axModal.activeModal = null;
            }

            openRecordServicePopup("ITEM_ADD", "Edit Item", sendData);
            break;
        case "ITEM_VIEW":
            selectedData = treeData ? [treeData] : selectedData;

            sendData = $.extend({}, selectedData[0], {type: "create"},
                {navi: fnObj.naviView.getPathString()},
                {title: selectedData["name"]}, {sendData: selectedData}
            );
            openRecordServicePopup("ITEM_VIEW", "View Item", sendData);
            break;
        case "AGG_ADD":
            selectedData = fnObj.naviView.getCurrent();
            var currentNode = fnObj.treeView01.getNodeByParam("uuid", selectedData["uuid"]);
            if (currentNode && currentNode.nodeType == "normal" && !currentNode.isParent && currentNode.childCnt > 0) {
                axDialog.alert(axboot.getCommonMessage("RC001_08"));
                return;
            }

            naviStr = undefined == selectedData["name"] ? "" : " > " + selectedData["name"];
            sendData = $.extend({},
                selectedData
                , {type: "create"}
                , {navi: fnObj.naviView.getPathString() + naviStr}, {title: ""}
            );

            openRecordServicePopup("AGGREGATION_ADD", "Add Aggregation", sendData);
            break;
        case "AGG_EDIT":
            selectedData = treeData ? [treeData] : fnObj.iconView.getSelectedData();
            sendData = $.extend({}, {
                parentUuid: selectedData[0].parentUuid,
                uuid: selectedData[0].uuid,
                nodeType: selectedData[0].nodeType
            }, {type: "update"}, {navi: fnObj.naviView.getPathString()}, {title: selectedData[0]["name"]});

            if (this.axModal.activeModal) {
                this.axModal.activeModal.remove();
                this.axModal.activeModal = null;
            }

            openRecordServicePopup("AGGREGATION_ADD", "Edit Aggregation", sendData);
            break;
        case "AGG_VIEW":
            selectedData = treeData ? [treeData] : fnObj.iconView.getSelectedData();
            sendData = $.extend({}, selectedData[0], {type: "create"},
                {navi: fnObj.naviView.getPathString()},
                {title: selectedData["name"]},
                {sendData: selectedData});

            openRecordServicePopup("AGGREGATION_VIEW", "View Aggregation", sendData);
            break;
        case "AGG_TYPE_NORMAL":
        case "AGG_TYPE_TEMP":
        case "AGG_TO_NON_ELECTRONIC":
            var sendData = null;

            if (ui.cmd == "AGG_TYPE_NORMAL") {
                selectedData = treeData ? [treeData] : fnObj.iconView.getSelectedData();
                msg = "Normal Aggregation으로 변경하시겠습니까?";
                typeUuid = axboot.commonCodeValueByCodeName("CD127", "Normal");
                typeName = "normal";
                sendData = {uuid: selectedData[0].uuid, nodeType: typeUuid};
            } else if (ui.cmd == "AGG_TYPE_TEMP") {
                selectedData = treeData ? [treeData] : fnObj.iconView.getSelectedData();
                msg = "Temporay Aggregation으로 변경하시겠습니까?";
                typeUuid = axboot.commonCodeValueByCodeName("CD127", "Temporary");
                typeName = "temporary";
                sendData = {uuid: selectedData[0].uuid, nodeType: typeUuid};
            }  else if (ui.cmd == "AGG_TO_NON_ELECTRONIC") {
                selectedData = treeData ? [treeData] : fnObj.iconView.getSelectedData();
                msg = "Non-electronic Record로 변경하시겠습니까?";
                typeUuid = axboot.commonCodeValueByCodeName("CD204", "Non-electronic Record");
                typeName = "nonElectronic";
                sendData = {uuid: selectedData[0].uuid, electronicRecordStatusUuid : typeUuid};
            }

            axDialog.confirm({
                msg: msg
            }, function () {
                if (this.key == "ok") {
                    axboot.ajax({
                        type: "GET",
                        url: "/rc/rc001/updateAggregationType",
                        data: $.extend({}, sendData, null),
                        callback: function (res) {
                            if (res.map.isSuccess == true) {
                                if (res.map.list) {
                                    $.each(res.map.list, function (idx, item) {
                                        var nodeObj = fnObj.treeView01.getNodeByParam("uuid", item["uuid"]);
                                        if (!nodeObj) {
                                            axDialog.alert({
                                                title: 'Information',
                                                theme: "default",
                                                msg: "정보가 갱신되었습니다.\nAggregation 정보를 다시 가져옵니다."
                                            }, function () {
                                                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                                            });
                                            return;
                                        }

                                        if(typeName == "nonElectronic") return;

                                        nodeObj.iconSkin = typeName;
                                        nodeObj.nodeType = typeName;

                                        fnObj.treeView01.updateNode(nodeObj);
                                    });
                                }
                            } else {
                                axDialog.alert({
                                    title: 'Warning',
                                    theme: "default",
                                    msg: res.map.message
                                });

                                return;
                            }

                            ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());
                        },
                        options: {
                            onError: axboot.viewError
                        }
                    });
                }
            });
            break;
        case "ITEM_ADD_GRID":
        case "AGG_ADD_GRID":
            var nodeType = "";
            if (ui.cmd == "ITEM_ADD_GRID") {
                nodeType = "item";
            } else if (ui.cmd == "AGG_ADD_GRID") {
                nodeType = "aggregation";
            }
            axboot.modal.open({
                modalType: "ADD_RECORD_ITEM_GRID",
                width: 1600,
                height: 800,
                header: {
                    title: nodeType == "item" ? "Add Item" : "Add Aggregation"
                },
                sendData: function () {
                    return {
                        aggregationUuid: fnObj.naviView.getCurrent().uuid,
                        nodeType: nodeType
                    };
                },
                callback: function (data) {
                    ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());
                }
            });
            break;
        case "REFRESH":
            ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());
            break;
        case "AGG_CLASSIFY_RECORDS":
            treeData = treeData ? treeData : fnObj.treeView01.getNodeByParam("uuid", selectedData[0].uuid);
            treeData.choiceYn = "Y";

            var nodeType = "";
            if (ui.cmd == "ITEM_ADD_GRID") {
                nodeType = "item";
            } else if (ui.cmd == "AGG_CLASSIFY_RECORDS") {
                nodeType = "aggregation";
            }

            function getFlatData(listData) {
                listData.forEach(function (item, idx) {
                    if (item.nodeType == "normal") {
                        item.aggregationUuid = item.uuid;
                        selectedData.push(item);
                    } else {
                        return true;
                    }

                    if (item.children.length > 0) {
                        getFlatData(item.children);
                    }
                });
            }

            axboot.modal.open({
                modalType: "RECORD_EXPLORER_CLASSIFY_RECORDS",
                width: 1600,
                height: 800,
                header: {
                    title: nodeType == "item" ? "Add Item" : "Add Aggregation"
                },
                sendData: function () {
                    selectedData = [];
                    getFlatData([treeData]);

                    return {aggregationUuid: treeData.uuid, classifyList: selectedData};
                },
                callback: function (data) {
                    //ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());
                }
            });
            break;
        case "ITEM_EXPORT" :
            location.href = API_SERVER + "/api/v1/common/download/item?itemUuid="+selectedData[0].uuid+"&aggregationUuid="+selectedData[0].parentUuid;
            /*axboot.ajax({
                type: "GET",
                url: "/api/v1/rc005/01/export",
                data: $.extend({}, {pageSize: 1000}, {
                    aggregationUuid: selectedData[0].parentUuid,
                    itemUuid: selectedData[0].uuid
                }),
                callback: function (res) {

                }
            });*/
            break;
    }
}

function openRecordServicePopup(type, title, sendData, callback) {
    if (!callback) {
        callback = function (data) {
            if (data && data.cmd) {
                contextMenuClick({cmd: data.cmd}, sendData);
            } else {
                if(fnObj.gridView01.mode == "grid") {
                    ACTIONS.dispatch(ACTIONS.GET_GRID_DATA, fnObj.naviView.getCurrent());
                } else if(fnObj.gridView01.mode == "search") {
                    ACTIONS.dispatch(ACTIONS.GET_SEARCH_DATA);
                }
            }
        }
    }

    axboot.modal.open({
        modalType: type,
        width: 1600,
        height: 800,
        header: {
            title: title
        },
        sendData: function () {
            return sendData;
        },
        callback: callback
    });
}


/**
 * ContextMenu 생성구분
 * @param ui
 * @param nodeType
 * @returns {*}
 */
function getContextMenu(ui, nodeType) {
    var menu = null;

    $("#iconListArea").tooltip("disable");

    //컨트롤 누르고 클릭 시 해당 아이템들이 다중으로 선택되어야된다
    if ($(ui.target).attr("id") != "iconListArea") {
        if (fnObj.iconView.selectedItems && fnObj.iconView.selectedItems.length > 1) {
            var isNormal = false;

            fnObj.iconView.selectedItems.forEach(function (item, idx) {
                if (item.nodeType == "normal") {
                    isNormal = true;
                    return false;
                }
            });

            if(!isNormal) {
                menu = [
                    {title: "Delete Items/Aggregations", cmd: "NODE_DEL", uiIcon: "ui-icon-trash"},
                ];
            }else{
                menu = [];
            }
        } else {
            if (nodeType == "item") {
                if(fnObj.naviView.getCurrent().nodeType == "normal") {
                    menu = [
                        {title: "View Item", cmd: "ITEM_VIEW", uiIcon: "ui-icon-info"},
                        {title: "----"},
                        {title: "Edit Item", cmd: "ITEM_EDIT", uiIcon: "ui-icon-wrench"},
                        {title: "----"},
                        {title: "Export Item", cmd: "ITEM_EXPORT", uiIcon: "ui-icon-arrowreturnthick-1-e"},
                    ];
                }else if(fnObj.naviView.getCurrent().nodeType == "temp"){
                    menu = [
                        {title: "View Item", cmd: "ITEM_VIEW", uiIcon: "ui-icon-info"},
                        {title: "----"},
                        {title: "Edit Item", cmd: "ITEM_EDIT", uiIcon: "ui-icon-wrench"},
                        {title: "Delete Item", cmd: "NODE_DEL", uiIcon: "ui-icon-trash"},
                        {title: "----"},
                        {title: "Export Item", cmd: "ITEM_EXPORT", uiIcon: "ui-icon-arrowreturnthick-1-e"},
                    ];
                }
            } else if (nodeType == "normal") {
                var treeData = null;
                var selectedData = null;

                if ($(ui.target).offsetParent().attr("id") == "iconListArea") {
                    var selectedData = fnObj.iconView.getSelectedData();
                    treeData = fnObj.treeView01.getNodeByParam("uuid", selectedData[0].uuid);
                } else {
                    treeData = fnObj.treeView01.getNodeByTId($(ui.target).closest("[treenode]").attr("id"));
                }

                if (!treeData) {
                    axDialog.alert({
                        title: 'Information',
                        theme: "default",
                        msg: "정보가 갱신되었습니다.\nAggregation 정보를 다시 가져옵니다."
                    }, function () {
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    });
                    return [];
                }

                var rootInfo = treeData.getPath();
                var enableClassify = true;

                if (rootInfo.length > 0) {
                    rootInfo.forEach(function (item, idx) {
                        if (item.nodeType != "normal") {
                            enableClassify = false;
                            return false;
                        }
                    });
                }

                menu = [
                    {title: "View Aggregation", cmd: "AGG_VIEW", uiIcon: "ui-icon-info"},
                    {title: "----"},
                    {title: "Edit Aggregation", cmd: "AGG_EDIT", uiIcon: "ui-icon-wrench"},
                    {title: "Change to Temporary Aggregation", cmd: "AGG_TYPE_TEMP", uiIcon: "ui-icon-transferthick-e-w"},
                    {title: "Change to Non-Electronic Record", cmd: "AGG_TO_NON_ELECTRONIC", uiIcon: "ui-icon-transferthick-e-w"},
                    //{title: "Publishing Aggregation", cmd: "AGG_PUBLISH", uiIcon: "ui-icon-transferthick-e-w" },
                ];

                // 분류 가능할 조건에만 해당 Context메뉴를 보여줌
                if (enableClassify) {
                    menu.push({title: "----"});
                    menu.push({
                        title: "Classify Records",
                        cmd: "AGG_CLASSIFY_RECORDS",
                        uiIcon: "ui-icon-disk"
                    });
                }
            } else if (nodeType == "temporary") {
                menu = [
                    {title: "View Aggregation", cmd: "AGG_VIEW", uiIcon: "ui-icon-info"},
                    {title: "Edit Aggregation", cmd: "AGG_EDIT", uiIcon: "ui-icon-wrench"},
                    {title: "----"},
                    {title: "Delete Aggregation", cmd: "NODE_DEL", uiIcon: "ui-icon-trash"},
                    {title: "Change to Normal Aggregation", cmd: "AGG_TYPE_NORMAL", uiIcon: "ui-icon-transferthick-e-w"},
                ];
            } else if (nodeType == "virtual") {
                menu = [
                    {title: "View Aggregation", cmd: "AGG_VIEW", uiIcon: "ui-icon-info"},
                    {title: "----"},
                    {title: "Edit Aggregation", cmd: "AGG_EDIT", uiIcon: "ui-icon-wrench"},
                    {title: "Delete Aggregation", cmd: "NODE_DEL", uiIcon: "ui-icon-trash"},
                ];
            }
        }
    } else {
        if (fnObj.naviView.getCurrent().nodeType == "temp") {
            menu = [
                {title: "Add Item", cmd: "ITEM_ADD", uiIcon: "ui-icon-document"},
                {title: "Add Aggregation", cmd: "AGG_ADD", uiIcon: "ui-icon-folder-collapsed"},
                {title: "----"},
                {title: "Add Item Grid", cmd: "ITEM_ADD_GRID", uiIcon: "ui-icon-document"},
                {title: "Add Aggregation Grid", cmd: "AGG_ADD_GRID", uiIcon: "ui-icon-folder-collapsed"},
                {title: "----"},
                {title: "Refresh", cmd: "REFRESH", uiIcon: "ui-icon-arrowrefresh-1-w"},
            ];
        } else {
            menu = [
                {title: "Add Item", cmd: "ITEM_ADD", uiIcon: "ui-icon-document"},
                {title: "Add Aggregation", cmd: "AGG_ADD", uiIcon: "ui-icon-folder-collapsed"},
                {title: "----"},
                {title: "Refresh", cmd: "REFRESH", uiIcon: "ui-icon-arrowrefresh-1-w"},
            ];
        }
    }

    return menu;
}

var getMenu = function (searchData) {
    var menuObj = undefined;
    axboot.ajax({
        url: "/rc/rc001/getMenuInfo",
        data: JSON.stringify({progNm: searchData}),
        type: "POST",
        dataType: "JSON",
        async: false,
        callback: function (res) {
            menuObj = res;
        },
        options: {
            onError: axboot.viewError
        }
    });
    return menuObj;
}


function setFormData(item, isItem) {
    // Properties
    fnObj.formView.setFormData("title", item.name);
    fnObj.formView.setFormData("riItemUuid", item.riItemUuid);
    fnObj.formView.setFormData("aggregationUuid", item.aggregationUuid);
    fnObj.formView.setFormData("riItemCode", isItem ? item.riItemCode : item.aggregationCode);
    fnObj.formView.setFormData("riTypeUuid", item.riTypeUuid);
    fnObj.formView.setFormData("aggregationTypeUuid", item.typeUuid);
    fnObj.formView.setFormData("publishedStatusUuid", isItem ? item.riPublishedStatusUuid : item.publishedStatusUuid);

    fnObj.formView.setFormData("description", isItem ? item.description1 : item.description);
    fnObj.formView.setFormData("notes", isItem ? item.notes1 : item.notes);

    fnObj.formView.setFormData("author", isItem ? item.riAuthor : item.author);
    fnObj.formView.setFormData("rcAggregationCode", item.aggregationCode);
    fnObj.formView.setFormData("openStatusUuid", item.openStatusUuid);
    fnObj.formView.setFormData("raTitle", item.raTitle);
    fnObj.formView.setFormData("raAggregationUuid", item.raAggregationUuid);
    fnObj.formView.setFormData("levelUuid", item.levelUuid);
    fnObj.formView.setFormData("provenance", item.provenance);
    fnObj.formView.setFormData("creator", item.creator);
    fnObj.formView.setFormData("keyword", item.keyword);
    fnObj.formView.setFormData("referenceCode", item.referenceCode);

    fnObj.formView.setFormData("descriptionStartDate", isItem ? item.riDescriptionStartDate : item.descriptionStartDate);
    fnObj.formView.setFormData("descriptionEndDate", isItem ? item.riDescriptionEndDate : item.descriptionEndDate);

    fnObj.formView.setFormData("creationStartDate", item.creationStartDate);
    fnObj.formView.setFormData("creationEndDate", item.creationEndDate);
    fnObj.formView.setFormData("isItem", isItem);

    if (item.extraMetadata) {
        fnObj.gridView02.setData(JSON.parse(item.extraMetadata));
    } else {
        fnObj.gridView02.setData([]);
    }
}

var fnObj = {
    pageStart: function () {
        /*var _this = this;
        $.ajax({
            url: "/assets/js/column_info/ad00101.js",‰
            dataType: "script",
            async : false,
            success: function(){}
        });
        _this.searchView.initView();
        _this.gridView01.initView();*/

        $.ajax({
            url: "/assets/js/column_info/rc00101.js",
            dataType: "script",
            async: false,
            success: function () {
            }
        });

        $.ajax({
            url: "/assets/js/column_info/rc00107.js",
            dataType: "script",
            async: false,
            success: function () {
            }
        });

        $.ajax({
            url: "/assets/js/column_info/rc00102.js",
            dataType: "script",
            async: false,
            success: function () {
            }
        });

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

        $(".exp-menu").hover(function () {
            //icon 실행
            if ($(".explorer_grid").css("display") == "none") {
                selectedData = fnObj.iconView.getSelectedData();
            }
            //grid 실행
            else {
                selectedData = fnObj.gridView01.getSelectedData();
            }

            switch (selectedData.length) {
                case 1:
                    $(".exp-menu>li>ul>li>a").removeClass("inactive");
                    break;
                case 0:
                    $(".exp-menu>li>ul>li>a").each(function () {
                        switch ($(this).text()) {
                            case "Properties":
                            case "Edit":
                            case "Move":
                            case "Delete":
                                $(this).addClass("inactive");
                                break;
                            default :
                                $(this).removeClass("inactive");
                        }
                    });
                    break;
                default :
                    $(".exp-menu>li>ul>li>a").each(function () {
                        switch ($(this).text()) {
                            case "Aggregation" :
                            case "Item":
                            case "Properties":
                            case "Edit":
                                $(this).addClass("inactive");
                                break;
                            default :
                                $(this).removeClass("inactive");
                        }
                    });
                    break;
            }
        });

        $(".exp-menu a").click(function (event) {
            event.stopPropagation();
            event.preventDefault();
            var selectedData = undefined;

            var parentsObj = parent.window.fnObj;
            var menu = "";


            //icon 실행
            if ($(".explorer_grid").css("display") == "none") {
                selectedData = fnObj.iconView.getSelectedData();
            }
            //grid 실행
            else {
                selectedData = fnObj.gridView01.getSelectedData();
            }

            //var selectedData = rightObj.getSelectedData();

            if (selectedData.length > 0 && !selectedData[0]["name"])
                selectedData[0]["name"] = selectedData[0]["title"];

            switch ($(this).text()) {
                //add
                case "Aggregation":
                    contextMenuClick({cmd : "AGG_ADD"});
                    break;
                case "Item":
                    contextMenuClick({cmd : "ITEM_ADD"});
                    break;
                //view
                case "Properties":
                    if (selectedData.length == 1) {
                        if (selectedData[0].nodeType == "item") {
                            contextMenuClick({cmd : "ITEM_VIEW"}, $.extend(selectedData[0], {parentUuid : fnObj.naviView.getCurrent()["uuid"]}));
                        } else {
                            contextMenuClick({cmd : "AGG_VIEW"}, selectedData[0]);
                        }
                    }
                    break;
                //do
                case "Edit":
                    if (selectedData.length == 1) {
                        if (selectedData[0].nodeType == "item") {
                            contextMenuClick({cmd : "ITEM_EDIT"}, $.extend(selectedData[0], {parentUuid : fnObj.naviView.getCurrent()["uuid"]}));
                        } else {
                            contextMenuClick({cmd : "AGG_EDIT"}, selectedData[0]);
                        }
                    }
                    break;
                case "Move":

                    var selectType = "";
                    /*
                    aggregation type을 기준으로 이동할 수 있는 경우의 수가 달라진다.
                    aggregation Type :

                    Aggregation
                    Normal : Normal
                    Tempolary : Temporary
                    Virtual   : Virtual

                    Iteam
                    Normal    : Normal, Tempolary
                    Tempolary : Normal, Tempolary
                    Virtual   : X
                    */
                    var compAggregation = "";
                    var aggregationType = "";
                    var selectType = "";
                    var canMove = true;
                    var errorMsg = "";
                    selectedData.sort(function (a, b) {
                        var typeName = undefined == a["nodeType"] ? "nodeType" : "type";

                        return a[typeName] - b[typeName];
                    });

                    for (var i = 0; i < selectedData.length; i++) {
                        aggregationType = undefined === selectedData[i]["nodeType"] ? selectedData[i]["type"] : selectedData[i]["nodeType"];
                        if (i == 0)
                            selectType = compAggregation = aggregationType;

                        if ((!aggregationType || aggregationType == "") && selectType.toLowerCase().indexOf("virtual") > -1) {
                            canMove = false;
                            errorMsg = "RC001_06";
                            break;
                        }

                        if (aggregationType && "" != aggregationType && compAggregation != aggregationType) {
                            canMove = false;
                            errorMsg = "RC001_02";
                            break;
                        }
                    }

                    if (!canMove) {
                        axDialog.alert(axboot.getCommonMessage(errorMsg));
                        return;
                    }
                    axboot.modal.open({
                        modalType: "MOVE_AGGREGATION",
                        param: "",
                        sendData: function () {
                            return {
                                selectType: selectType,
                                "selectedList": selectedData
                            };
                        },
                        callback: function (data) {

                            axToast.push(axboot.getCommonMessage("AA007"));
                            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);

                            if ($(".explorer_grid").css("display") == "none")
                                ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());
                            else
                                ACTIONS.dispatch(ACTIONS.GET_GRID_DATA, fnObj.naviView.getCurrent());


                        }
                    });
                    break;
                case "Update Status":


                    axboot.modal.open({
                        modalType: "UPDATE_STATE_AGGREGATION_N_ITEM",
                        param: "",
                        sendData: function () {
                            return {
                                "selectedList": selectedData
                            };
                        },
                        callback: function (data) {
                            axToast.push(axboot.getCommonMessage("AA007"));
                            if ($(".explorer_grid").css("display") == "none")
                                ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());
                            else
                                ACTIONS.dispatch(ACTIONS.GET_GRID_DATA, fnObj.naviView.getCurrent());
                        }
                    });


                    break;
                case "Delete":

                    ACTIONS.dispatch(ACTIONS.DELETE_AGGREGATION, selectedData);
                    break;

            }

            console.log($(this).text());

        });

        fnObj.treeView01.initView();
        fnObj.naviView.initView();
        fnObj.iconView.initView();
        fnObj.gridView01.initView();
        fnObj.gridView02.initView();
        fnObj.pageView.initView();
        fnObj.formView.initView();

        $("#search").on("click", function(){
            if($.trim($("[data-ax-path='searchTitle']").val()).length < 2){
                axWarningToast.push("검색어를 2글자 이상 입력하세요.");
                return;
            }

            exp_gridView(true);
        });

        $("#reSearch").on("click", function(){
            $("#searchView").show();
            $("#searchResultView").hide();
        });

        $("#iconListArea").on({
            "dragenter": function (event) {
                if (fnObj.naviView.getCurrent().nodeType == "temp") {
                    $('[data-ax5uploader="upload1"]').show();
                }
            }
        });

        var API_SERVER = CONTEXT_PATH;

        UPLOAD = new ax5.ui.uploader({
            debug: false,
            target: $('[data-ax5uploader="upload1"]'),
            form: {
                action: "/api/v1/common/upload",
                fileName: "file"
            },
            multiple: true,
            manualUpload: true,
            progressBox: true,
            progressBoxDirection: "left",
            dropZone: {
                target: $('[data-uploaded-box="upload1"]'),
                ondrop: function () {
                    axDialog.confirm({
                        title: "Seoul-AMS",
                        msg: "입수정보를 작성해야합니다. 작성하셨습니까?"
                    }, function () {
                        if (this.key == "ok") {
                            ACTIONS.dispatch(ACTIONS.PAGE_INGEST);
                        } else {
                            ACTIONS.dispatch(ACTIONS.PAGE_INGEST_LIST);
                        }
                        $(["data-pregressbox-btn='abort'"]).click(function () {
                            $('[data-ax5uploader="upload1"]').hide();
                        });
                    });
                },
                ondragover: function () {
                    $('[data-ax5uploader="upload1"]').show();
                },
                ondragout: function () {
                    $('[data-ax5uploader="upload1"]').hide();
                },
                onclick: function () {
                    $('[data-ax5uploader="upload1"]').hide();
                }
            },
            uploadedBox: {
                target: $('[data-ax5uploader="upload1"]'),
                icon: {
                    "delete": '<i class="cqc-cancel" aria-hidden="true"></i>',
                    "download": '<i class="cqc-save" aria-hidden="true"></i>'
                },
                columnKeys: {
                    apiServerUrl: API_SERVER,
                    name: "fileName",
                    type: "ext",
                    size: "fileSize",
                    uploadedName: "saveName",
                    thumbnail: ""
                },
                lang: {
                    supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">신분증사진을 선택하세요(필수입력!)</div>',
                    emptyListMsg: '<div class="text-center" style="padding-top: 30px;">Empty of List.</div>'
                },
                onchange: function () {
                    console.log('onchange: ', this);
                },
                onclick: function () {
                    // console.log(this.cellType);
                    var fileIndex = this.fileIndex;
                    var file = this.uploadedFiles[fileIndex];
                    switch (this.cellType) {
                        case "delete":
                            axDialog.confirm({
                                title: "Seoul-AMS",
                                msg: "선택된 이미지를 삭제하시겠습니까?"
                            }, function () {
                                if (this.key == "ok") {
                                    UPLOAD.removeFile(fileIndex);
                                }
                            });
                            break;

                        case "download":
                            if (file.download) {
                                location.href = API_SERVER + file.download;
                            }
                            break;
                    }
                }
            },
            validateSelectedFiles: function () {

                // 1개 이상 업로드 되지 않도록 제한.
                return true;
            },
            onuploaderror: function () {
                $('[data-ax5uploader="upload1"]').hide();
                axDialog.alert({
                    title: 'Onsemiro Uploader',
                    theme: "default",
                    msg: this.error.message
                });
            },
            onuploaded: function () {
                //axToast.push("File Upload Completed : onuploaded");
            },
            onuploadComplete: function () {
                $('[data-ax5uploader="upload1"]').hide();
                axToast.push("File Upload Completed : onuploadComplete");
                ACTIONS.dispatch(ACTIONS.INGEST_ARCHIVE);
            },
            abortCallback: function () {
                $('[data-ax5uploader="upload1"]').hide();
                axToast.push("업로드를 취소하였습니다.");
            }
        });

        $("[href='#itemTabs-3']").on("click", function () {
            fnObj.gridView02.gridObj.getGridView().resetSize();
        });

        $("#extraMetaAll").on("change", function () {
            fnObj.gridView02.setFilter(!$(this).is(":checked"));
        });


        levelLabels = $.map($("[data-ax-path='levelUuid'] option") ,function(option) {
            return $(option).text();
        });

        levelValues = $.map($("[data-ax-path='levelUuid'] option") ,function(option) {
            return option.value;
        });

        // Data 조회
        var data = axboot.getMenuParams();

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);

        if(data && data["type"] == "search"){
            fnObj.formView.setFormData("searchTitle", data["searchWord"]);
            exp_gridView(true);
        }else{
            ACTIONS.dispatch(ACTIONS.GET_SUBDATA, {uuid: ""});
        }
    }
};

fnObj.naviView = axboot.viewExtend({
    initView: function () {
        this.initEvent();
    },
    initEvent: function () {
        $("#navigatorArea").delegate(".navigator", "click", function () {
            console.log($(this));

            var currentLevel = $(this).attr("level");

            $("#navigatorArea .navigator").each(function (idx) {
                if (idx > currentLevel) {
                    $(this).remove();
                    $("#navigatorArea .split[splitLevel=" + idx + "]").remove();
                }
            });
            if ($(".explorer_grid").css("display") == "none")
                ACTIONS.dispatch(ACTIONS.GET_SUBDATA, {uuid: $(this).attr("uuid")});
            else
                ACTIONS.dispatch(ACTIONS.GET_GRID_DATA, {uuid: $(this).attr("uuid")});

        });
    },
    setData: function (data) {
        $("#navigatorArea").append($("<span>").attr("splitLevel", $("#navigatorArea span.split").size() + 1).addClass("split").text(" > "));
        $("#navigatorArea").append($("<span>").addClass("navigator").attr("level", $("#navigatorArea span.navigator").size()).attr("uuid", data["uuid"]).text(data["name"]));
    },
    setList: function (list) {
        fnObj.naviView.clear();
        var data = undefined;
        for (var i = 0; i < list.length; i++) {
            data = list[i];
            $("#navigatorArea").append($("<span>").attr("splitLevel", $("#navigatorArea span.split").size() + 1).addClass("split").text(" > "));
            $("#navigatorArea").append($("<span>").addClass("navigator").attr("level", $("#navigatorArea span.navigator").size()).attr("nodeType", data["nodeType"]).attr("uuid", data["uuid"]).text(data["name"]));
        }
    },
    getCurrent: function () {
        return {
            uuid: $(".navigator:last").attr("uuid"),
            nodeType: $(".navigator:last").attr("nodeType")
        };
    },
    getRoot: function () {
        return {uuid: $(".navigator:first").attr("uuid")};
    },
    getPathString: function () {
        var pathStr = "";
        $("#navigatorArea>span").each(function (idx) {
            pathStr += $(this).text();
        });
        return pathStr;
    },
    clear: function () {
        $("#navigatorArea .navigator").each(function (idx) {
            if (idx > 0) {
                $(this).remove();
                $("#navigatorArea .split[splitLevel=" + idx + "]").remove();
            }
        });
    }
});
fnObj.pageView = axboot.viewExtend({
    page: {
        pageNumber: 0,
        pageSize: 100
        //pageSize: 126
    },
    initView: function () {
        this.initEvent();
    },
    initEvent: function () {
        var _this = this;

        $(".page_no").delegate("a:not(.page_start,.page_prev,.page_next,.page_end)", "click", function () {
            _this.page.pageNumber = parseInt($(this).text()) - 1;
            $(".page_no>a:not(.page_start,.page_prev,.page_next,.page_end)").removeClass("selec");
            $(".page_no>a[pageNo='" + $(this).text() + "']").addClass("selec");


            if ($(".explorer_grid").css("display") == "none") {
                ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());
            } else{
                if(fnObj.gridView01.mode == "grid") {
                    ACTIONS.dispatch(ACTIONS.GET_GRID_DATA, fnObj.naviView.getCurrent());
                } else if(fnObj.gridView01.mode == "search") {
                    ACTIONS.dispatch(ACTIONS.GET_SEARCH_DATA);
                }
            }
        });

        $(".page_no>a.page_start,.page_no>a.page_prev,.page_no>a.page_next,.page_no>a.page_end").click(function () {
            switch ($(this).attr("class")) {
                case "page_start":
                    _this.page.pageNumber = 0;
                    break;
                case "page_prev":
                    if (_this.page.pageNumber < 10)
                        _this.page.pageNumber = 0;
                    else
                        _this.page.pageNumber = _this.page.pageNumber - (_this.page.pageNumber % 10) - 1;
                    break;
                case "page_next":
                    if (_this.page.totalPages < 10)
                        _this.page.pageNumber = _this.page.totalPages - 2;
                    else
                        _this.page.pageNumber = _this.page.pageNumber + 10 - (_this.page.pageNumber % 10);
                    break;
                case "page_end":
                    _this.page.pageNumber = _this.page.totalPages - 1;
                    break;
            }
            ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());

        });
    },
    resetPage: function () {
        this.page.pageNumber = 0;
    },
    setPage: function (page) {

        this.page = page;
        //page.limit
        var startPage = parseInt(page.pageNumber / 10) * 10 + 1;
        var maxPage = startPage + 10;
        maxPage = page.totalPages < maxPage ? page.totalPages : maxPage;
        if (maxPage > 10 && maxPage % 10 != 1)
            maxPage++;

        maxPage = maxPage == 0 ? 1 : maxPage;

        $(".page_no a:not(.page_start,.page_prev,.page_next,.page_end)").remove();
        for (var i = startPage; i <= maxPage; i++) {
            $(".page_no .page_next").before($("<a>").attr("pageNo", i).attr("href", "#").text(i));
        }
        $(".page_no>a").each(function () {
            if ((page.pageNumber + 1) == $(this).text())
                $(this).addClass("selec");
            else
                $(this).removeClass("selec");

        });


        /*
        currentPage:0
        pageSize:108
        totalElements:10100
        totalPages:94
        */
    },
    setPageSize: function (_pageSize) {
        this.page.pageSize = _pageSize;
    },
    getPageInfo: function () {
        return {
            pageNumber: this.page.pageNumber,
            pageSize: this.page.pageSize
        }
    }
})

/*
fnObj.detailView = axboot.viewExtend({
    initView : function(){

    },
    setData : function(data)
    {
        var descStrDate = "";
        var descEdDate = "";
        var cloneTag = $("#templateAggregationInfoArea").clone();
        for(var key in data)
        {
            if(key == "descStrDate" && key == "descEdDate")
            {
                switch(key){
                    case "descStrDate":
                        descStrDate = data[key];
                        break;
                    case "descEdDate":
                        descEdDate = data[key];
                        break;
                }
                continue;
            }
            cloneTag.find("."+key).after(data[key]);
        }
        if("" != descStrDate || "" != descEdDate) {
            $("#templateAggregationInfoArea").find(".descDate").text(descStrDate + " ~ " + descEdDate);
        }
        $("#aggregationInfoArea").empty().append(cloneTag.children());
    }
});
*/
fnObj.iconView = axboot.viewExtend({
    pressedCtrl: false,
    pressedShift: false,
    isdbClk: false,
    selectedItem: null,
    timer: 0,
    delay: 400,
    prevent: false,
    isItemDrop: false,
    isOver: false,
    initView: function () {
        this.initEvent();
    },
    initEvent: function () {
        var _this = this;
        $("#isDisplayItem").change(function () {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH, fnObj.naviView.getRoot());
        });

        $(document).keydown(function (event) {
            fnObj.iconView.pressedCtrl = event.ctrlKey;
            fnObj.iconView.pressedShift = event.shiftKey;
        });
        $(document).keyup(function (event) {
            fnObj.iconView.pressedCtrl = event.ctrlKey;
            fnObj.iconView.pressedShift = event.shiftKey;
        });

        $('#componentView').delegate(">div", "dblclick", function (event) {
            event.preventDefault();
            if (event.currentTarget.hasAttribute("componentuuid") != null && event.currentTarget.getAttribute("componentuuid") != "" && event.currentTarget.getAttribute("componentuuid") != null) {
                $.ajax({
                    url :"/api/v1/common/getStreamingUrl",
                    data : JSON.stringify({componentUuid:event.currentTarget.getAttribute("componentuuid")}),
                    dataType : "json",
                    type : "post",
                    contentType : "application/json",
                    success : function(res){
                        if(res.url != undefined && res.url != null){
                            window.open(res.url, "", "");
                        }else if(res.componentUuid != undefined && res.componentUuid != null){
                            window.open("/api/v1/common/video/" + res.componentUuid, "", "");
                        }
                    },
                    error : function (a,b,c)
                    {
                        console.log(a);
                    }
                })
            }
        });

        $(document).click(function (event) {
            $("#iconListArea .selected").removeClass("selected");

            if (!fnObj.iconView.isOver && $(event.target).closest("#itemTabs").length == 0) {
                $("#iconListArea").width("100%");
                $("#componentView").empty();
                //fnObj.formView.clear();
            }

            fnObj.iconView.isOver = false;
        });

        /**
         * 화면 Resize 시 iconListArea 크기를 조정
         */
        $(window).resize(function (event) {

            if ($("#iconListArea").width() != "100%")
                $("#iconListArea").width($("div.explorer_list").width() - $("#itemTabs").width() - 7);
        });


        /**
         * Record Explorer Item/Aggregation Tooltip
         */
        $("#iconListArea").tooltip({
            track: true
        });

        /*$(document).click(function(event){

        });*/

        $("#iconListArea").delegate(">div[nodeType]", "click", function (event) {

            event.stopPropagation();

            $("#componentView").empty();
            if (fnObj.iconView.pressedCtrl) {
                $(this).toggleClass("selected");
            } else if (fnObj.iconView.pressedShift) {
                var selectedIdx = $("#iconListArea .selected").index();

                if (selectedIdx == -1) {
                    $(this).toggleClass("selected");
                } else if (selectedIdx < $(this).index()) {
                    for (var i = selectedIdx; i <= $(this).index(); i++) {
                        $($("#iconListArea >div").get(i)).addClass("selected");
                    }
                } else if (selectedIdx > $(this).index()) {
                    for (var i = $(this).index(); i <= selectedIdx; i++) {
                        $($("#iconListArea >div").get(i)).addClass("selected");
                    }
                } else {
                    $(this).toggleClass("selected");
                }

            } else {
                //컨트롤 누르지 않고 클릭 시 해당 아이템만 선택되어야된다
                $("#iconListArea >div").removeClass("selected");
                $(this).toggleClass("selected");
                //fnObj.formView.clear();

                var selectedData = fnObj.iconView.getSelectedData();

                fnObj.iconView.timer = setTimeout(function () {
                    if (fnObj.iconView.prevent) {
                        fnObj.iconView.prevent = false;
                        return;
                    }

                    $("#componentView").empty();
                    $("#iconListArea").width($("div.explorer_list").width() - $("#itemTabs").width() - 7);

                    if (selectedData[0]["nodeType"] == "item") {
                        $("#itemTabs").tabs("enable", 1);
                        //$( "#itemTabs" ).tabs( "option", "active", 1 );
                        $("[data-ax-path='riTypeUuid']").show();
                        $("[data-ax-path='aggregationTypeUuid']").hide();
                        $("#aggLevel").hide();

                        axboot.ajax({
                            type: "GET",
                            url: "/api/v1/rc005/01/list",
                            data: $.extend({}, {pageSize: 1000}, {
                                aggregationUuid: selectedData[0].parentUuid,
                                itemUuid: selectedData[0].uuid
                            }),
                            callback: function (res) {
                                if (res.list != "undefined" && res.list != null && res.list.length > 0) {
                                    // Component List

                                    var rcList = ax5.util.deepCopy(res.list);

                                    var item = rcList[rcList.length - 1];

                                    if (item.rc00502VoList != "undefined" && item.rc00502VoList != null && item.rc00502VoList.length > 0) {
                                        var targetTag = $("#componentView");
                                        var template = $("#template>div");
                                        var cloneTag = undefined;
                                        var imgTag = undefined;
                                        var imgPath = "/assets/images/ams/";

                                        $.each(item.rc00502VoList, function (idx, data) {
                                            cloneTag = template.clone();
                                            imgTag = $("<img>");
                                            cloneTag.attr("uuid", data["itemComponentUuid"]);
                                            cloneTag.attr("componentUuid", data["componentUuid"]);
                                            cloneTag.attr("status",data["status"]);
                                            cloneTag.attr("areaUuid", data["areaUuid"]);
                                            cloneTag.attr("label", data["title"]);
                                            cloneTag.attr("type", "comp");

                                            cloneTag.addClass("explorer_file");
                                            if(data["thumbnailContent"] == "" || data["thumbnailContent"] == null ){
                                                imgTag.prop("src", imgPath + "explorer_file_img.png").prop("alt", "folder");
                                            }else{
                                                imgTag.prop("src", "data:image/jpg;base64," + data["thumbnailContent"]).prop("alt", "folder");
                                            }
                                            // imgTag.setStyle("width",320);
                                            // imgTag.setStyle("height",320);
                                            cloneTag.find(".imageTag").append(imgTag);

                                            cloneTag.find(".titleTag").append($("<div>").attr("class", data["title"].length > 15 ? "explorer_4line" : "explorer_text").text(data["title"]));
                                            targetTag.append(cloneTag);
                                            delete cloneTag;
                                            delete imgTag;
                                        });

                                        $('#componentView >div').draggable({
                                            helper: function (event, ui) {
                                                var wrapper = "<div id='explorerDragWrapper'></div>";
                                                $('#ax-base-root').append(wrapper);

                                                return $('#explorerDragWrapper').append($(event.delegateTarget).clone());
                                            },
                                            opacity: 0.7
                                        });
                                    }

                                    setFormData(item, true);
                                }
                            }
                        });
                    } else {
                        $("#itemTabs").tabs("option", "disabled", [1]);
                        if ($("#itemTabs").tabs("option", "active") == 1) {
                            $("#itemTabs").tabs("option", "active", 0);
                        }
                        $("[data-ax-path='riTypeUuid']").hide();
                        $("[data-ax-path='aggregationTypeUuid']").show();
                        $("#aggLevel").show();

                        axboot.ajax({
                            type: "GET",
                            url: "/api/v1/rc003/01/list",
                            data: $.extend({}, {pageSize: 1000}, {aggregationUuid: selectedData[0].uuid}),
                            callback: function (res) {
                                if (res.list != "undefined" && res.list != null && res.list.length > 0) {
                                    rcList = ax5.util.deepCopy(res.list);
                                    setFormData(rcList[0], false);
                                }
                            },
                            options: {
                                onError: axboot.viewError
                            }
                        });
                    }
                }, fnObj.iconView.delay);
            }

            fnObj.pageView.resetPage();

        });

        $("#iconListArea").delegate(">div[nodeType]", "dblclick", function (event) {
            event.stopPropagation();

            $("#componentView").empty();

            clearTimeout(fnObj.iconView.timer);
            fnObj.iconView.prevent = true;

            fnObj.iconView.isdbClk = true;

            var index = 0;
            var uuid = "";
            if (undefined != $(this).attr("uuid")) {
                imgSrc = $(this).find(".imageTag").find("img").prop("src");
                uuid = $(this).attr("uuid");
            } else if (undefined == $(this).attr("uuid")) {
                index++;
            }
            if (undefined == $(this).attr("uuid"))
                index++;

            if (uuid == "")
                imgSrc = $(this).find(".imageTag").find("img").prop("src")

            if (imgSrc.indexOf("file") > -1) {
                var selectedData = undefined;

                var parentsObj = parent.window.fnObj;

                //icon 실행
                if ($(".explorer_grid").css("display") == "none") {
                    selectedData = fnObj.iconView.getSelectedData();
                }

                if (selectedData.length == 1) {
                    if (selectedData[0].nodeType == "item") {
                        contextMenuClick({cmd : "ITEM_VIEW"}, selectedData[0]);
                    } else {
                        contextMenuClick({cmd : "AGG_VIEW"}, selectedData[0]);
                    }
                }

                return;
            }


            fnObj.naviView.setData({
                uuid: $(this).attr("uuid"),
                name: $(this).find(".titleTag").children().eq(0).text()
            });
            ACTIONS.dispatch(ACTIONS.GET_SUBDATA, {
                    uuid: $(this).attr("uuid"),
                    nodeType: $(this).attr("nodeType")
                }
            );
            //fnObj.detailView.setData({});
            //setTimeout(function(){
            fnObj.iconView.isdbClk = false;
            //},300);

        });

        /**
         * Contextmenu Record Frame/Agg/Item
         */
        $('#iconListArea').contextmenu({
            autoFocus: true,
            delegate: "",
            preventContextMenuForPopup: true,
            preventSelect: true,
            taphold: true,
            menu: [],
            // Handle menu selection to implement a fake-clipboard
            select: function (event, ui) {
                contextMenuClick(ui);
                // Optionally return false, to prevent closing the menu now
            },
            // Implement the beforeOpen callback to dynamically change the entries
            beforeOpen: function (event, ui) {
                var contextTarget = $(ui.target).closest("#iconListArea >div");

                if (fnObj.iconView.pressedCtrl) {
                    contextTarget.addClass("selected");
                } else if (fnObj.iconView.pressedShift) {
                    var selectedIdx = $("#iconListArea .selected").index();

                    if (selectedIdx == -1) {
                        contextTarget.toggleClass("selected");
                    } else if (selectedIdx < contextTarget.index()) {
                        for (var i = selectedIdx; i <= contextTarget.index(); i++) {
                            $($("#iconListArea >div").get(i)).addClass("selected");
                        }
                    } else if (selectedIdx > contextTarget.index()) {
                        for (var i = contextTarget.index(); i <= selectedIdx; i++) {
                            $($("#iconListArea >div").get(i)).addClass("selected");
                        }
                    } else {
                        contextTarget.toggleClass("selected");
                    }

                } else {
                    //컨트롤 누르지 않고 클릭 시 해당 아이템만 선택되어야된다
                    $("#iconListArea >div").removeClass("selected");
                    contextTarget.toggleClass("selected");
                }

                fnObj.pageView.resetPage();
                fnObj.iconView.selectedItems = fnObj.iconView.getSelectedData();

                $(this).contextmenu({
                    menu: getContextMenu(ui, contextTarget.attr("nodetype"))
                });
            },
            close: function (event, ui) {
                $("#iconListArea").tooltip("enable");
            }
        });

        $("#componentView").on("click", ">div" ,function (event) {
            var status = $(event.currentTarget).attr("status");
            if(status == "Fail") {
                axDialog.alert("변환에 실패하였습니다. 관리자에게 문의하세요");
                return;
            }else if(status == "Nothing"){
                axDialog.alert("미리보기를 지원하지 않는 파일 양식입니다.");
            }else if(status == "Success"){
                var componentUuid = $(event.currentTarget).attr("componentUuid");

                if (componentUuid != null && componentUuid != "") {
                    $.ajax({
                        url: "/api/v1/common/getStreamingUrl",
                        data: JSON.stringify({componentUuid: componentUuid}),
                        dataType: "json",
                        type: "post",
                        contentType: "application/json",
                        success: function (res) {
                            if (res.url != undefined && res.url != null) {
                                window.open(res.url, "", "");
                            } else if (res.componentUuid != undefined && res.componentUuid != null) {
                                window.open("/api/v1/common/video/" + res.componentUuid, "", "");
                            }
                        },
                        error: function (a, b, c) {
                            console.log(a);
                        }
                    })
                }
            }else {
                axDialog.alert("변환 작업중입니다. 잠시후에 시도하세요");
            }
        });
    },
    setData: function (list, isFirst) {
        var targetTag = $("#iconListArea");
        var template = $("#template>div");
        var cloneTag = undefined;
        var data = undefined;
        var imgTag = undefined;
        var imgPath = "/assets/images/ams/";
        $("#iconListArea >div").remove("[nodeType]");
        var fullStr = "";
        this.list = list;
        if (list.length < 1)
            return;

        var chooseList = new Array();
        if (isFirst) {
            for (var i = 0; i < list.length; i++) {
                if (list[i]["parentUuid"] == undefined) {
                    chooseList.push(list[i]);
                }
            }
            list = chooseList;
        }

        for (var i = 0; i < list.length; i++) {
            data = list[i];
            cloneTag = template.clone();
            imgTag = $("<img>");
            cloneTag.attr("uuid", data["uuid"]);
            cloneTag.attr("nodeType", data["nodeType"]);
            /*
            Aggregation
            class : explorer_folder_empty or explorer_folder_full
            imagePath : /assets/images/ams/
            imageName :  explorer_folder, explorer_folder_t, explorer_folder_v,

            Item
            class : explorer_file, explorer_img
            imagePath : /assets/images/ams/
            imageName : explorer_file,explorer_file_v, explorer_file_img, explorer_file_img_v

            Text
            class : explorer_text, explorer_4line
            */
            if (data["nodeType"] == "item") {
                cloneTag.addClass("explorer_file");
            } else {
                if (data["childCnt"] > 0) {
                    cloneTag.addClass("explorer_folder_full");
                    fullStr = "_f";
                }
                else {
                    cloneTag.addClass("explorer_folder_empty");
                    fullStr = "";
                }
            }


            switch (data["nodeType"]) {
                case "normal":
                    imgTag.prop("src", imgPath + "explorer_folder" + fullStr + ".png").prop("alt", "folder");
                    break;
                case "temporary":
                    imgTag.prop("src", imgPath + "explorer_folder" + fullStr + "_t.png").prop("alt", "folder");
                    break;
                case "virtual":
                    imgTag.prop("src", imgPath + "explorer_folder" + fullStr + "_v.png").prop("alt", "folder");
                    break;
                case "item":
                    imgTag.prop("src", imgPath + "explorer_file.png").prop("alt", "item");
                    break;
            }
            cloneTag.find(".imageTag").append(imgTag);

            cloneTag.find(".titleTag").append($("<div>").attr("class", data["name"].length > 15 ? "explorer_4line" : "explorer_text").text(data["name"]));
            cloneTag.attr("level", data["level"]);
            cloneTag.attr("title", data["name"].length > 35 ? data["name"] : "");
            //cloneTag.css("position", "absolute");
            targetTag.append(cloneTag);
            fullStr = "";
            delete cloneTag;
            delete imgTag;
            delete data;

        }

        /*$('#iconListArea >div').ondrop(function(event){
            console.log(event.type());
        });*/

        /**
         * Draggable Record Aggregation/Item
         */
        $('#iconListArea >div').draggable({
            containment: "window",
            opacity: 0.5,
            delay: 50,
            stack: "#iconListArea >div",
            scroll: false,
            revert: false,
            revertDuration: 300,
            helper: function (event) {
                var wrapper = "<div id='explorerDragWrapper'></div>";
                $('#ax-base-root').append(wrapper);

                if (fnObj.iconView.getSelectedData().length < 2) {
                    $('#iconListArea >div').removeClass("selected");
                    $(this).addClass("selected");
                }

                return $('#explorerDragWrapper').append($('#iconListArea >div.selected').clone());
            },
            start: function (event, ui) {
                try {
                    $('[data-z-tree="tree-view-01"] [treenode_a]').droppable("option", "accept", "#iconListArea >div");
                }catch(e){
                    console.log(e);
                }
            }
        });

        /**
         * Droppable Record Tree 2 Aggregation
         */
        $('#iconListArea [nodetype="temporary"], #iconListArea [nodetype="normal"]').droppable({
            tolerance: "pointer",
            hoverClass: "selected",
            accept: "#iconListArea [nodetype='temporary'], #iconListArea [nodetype='normal'], #iconListArea [nodetype='item']",
            drop: function (event, ui) {
                var parentNode = fnObj.treeView01.getNodeByParam("uuid", $(this).attr("uuid"));

                if (!updateRecord(ui.draggable, parentNode)) {
                    return;
                }
            }
        });

        /**
         * Droppable  Item/Component 2 Item
         */
        $('#iconListArea .explorer_file').droppable({
            tolerance: "pointer",
            hoverClass: "selected",
            accept: "#iconListArea .explorer_file, #componentView .explorer_file",
            over: function (event, ui) {
                fnObj.iconView.isItemDrop = true;
            },
            out: function (event, ui) {
                fnObj.iconView.isItemDrop = false;
            },
            drop: function (event, ui) {
                // Item to Item
                fnObj.iconView.isItemDrop = false;

                var selectedData = fnObj.iconView.getSelectedData();

                // Normal/Virtual Aggregation으로는 Component 2 Item 생성 불가
                if (fnObj.naviView.getCurrent()["nodeType"] == "normal" || fnObj.naviView.getCurrent()["nodeType"] == "virtual") {
                    return;
                }

                // 멀티 선택시 Aggregation 포함되었을 때
                for (var i = 0; selectedData.length > i; i++) {
                    if (selectedData[i]["nodeType"] != "item") {
                        axDialog.alert("Aggregation은 포함될 수 없습니다.");
                        return;
                    }
                }

                var targetItemUuid = $(event.target).attr("uuid");

                var msg = "";
                var url = "";

                if (ui.draggable.closest("[type='comp']").length > 0) {
                    msg = "Component를 이동하시겠습니까?"
                    url = "/rc/rc001/moveComponent";
                } else if (ui.draggable.closest('.explorer_file').length > 0) {
                    msg = "Component를 이동하시겠습니까?\n해당 Item은 삭제됩니다."
                    url = "/rc/rc001/delItemAndMoveComponent";
                }

                axDialog.confirm({
                    msg: msg
                }, function () {
                    if (this.key == "ok") {
                        var data = [];

                        if (ui.draggable.closest("[type='comp']").length > 0) {
                            data.push({
                                itemUuid: targetItemUuid,
                                itemComponentUuid: ui.draggable.attr("uuid")
                            });
                        } else if (ui.draggable.closest('#iconListArea .explorer_file').length > 0) {
                            $.each(selectedData, function (idx, item) {
                                data.push({
                                    itemUuid: item.uuid,
                                    targetItemUuid: targetItemUuid
                                });
                            });
                        }

                        axboot.ajax({
                            type: "PUT",
                            url: url,
                            data: JSON.stringify(data),
                            callback: function (res) {
                                ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());
                            },
                            options: {
                                onError: axboot.viewError
                            }
                        });
                    }
                });
            }
        });

        /**
         * Droppable Record Frame
         */
        $('#iconListArea').droppable({
            tolerance: "pointer",
            drop: function (event, ui) {
                // Record 영역에 드랍 될 때 트리영역 드랍되는 것을 방지
                try{
                    $('[data-z-tree="tree-view-01"] [treenode_a]').droppable("option", "accept", "#iconListArea .noAccept");
                }catch(e){
                    console.log(e);
                }

                // Normal/Virtual Aggregation으로는 Component 2 Item 생성 불가
                if (fnObj.naviView.getCurrent()["nodeType"] == "normal" || fnObj.naviView.getCurrent()["nodeType"] == "virtual") {
                    return;
                }

                // Item 2 Item 시 드랍방지
                if (fnObj.iconView.isItemDrop) return;

                // Record Frame은 부모가 componentView 일때만 받아들인다.
                if (ui.draggable.parent().attr("id") != "componentView") return;

                $('#explorerDragWrapper').remove();
                var compUuid = ui.draggable.attr("uuid");
                axDialog.confirm({
                    msg: "Component를 Item으로 변경하시겠습니까?"
                }, function () {
                    if (this.key == "ok") {
                        var data = {
                            raTitle: ui.draggable.attr("label"),
                            riPublishedStatusUuid: axboot.commonCodeValueByCodeName("CD121", "Draft"),
                            raAggregationUuid: fnObj.naviView.getCurrent()["uuid"],
                            rc00502VoList: [{itemComponentUuid: compUuid}]
                        };

                        axboot.ajax({
                            type: "PUT",
                            url: "/rc/rc001/creItemAndMoveComponent",
                            data: JSON.stringify(data),
                            callback: function (res) {
                                ACTIONS.dispatch(ACTIONS.GET_SUBDATA, fnObj.naviView.getCurrent());
                            },
                            options: {
                                onError: axboot.viewError
                            }
                        });

                        ui.draggable.remove();
                    }
                });
            }
        });
    },
    getSelectedData: function () {
        var selectedData = new Array();
        var retData = new Array();
        $(".explorer_list .selected").each(function (item, index) {
            selectedData.push($(this).attr("uuid"));
        });
        for (var i = 0; i < this.list.length; i++) {
            for (var j = 0; j < selectedData.length; j++) {
                if (this.list[i].uuid == selectedData[j]) {
                    retData.push(this.list[i]);
                }
            }

        }

        return retData;
    }

});

/**
 * treeView
 */
fnObj.treeView01 = axboot.viewExtend(axboot.commonView, {
    param: {},
    deletedList: [],
    newCount: 0,
    otherParam: {},
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
        this.reloadFlag = false;
        this.checkFlag = false;

        /**
         * Contextmenu Tree
         */
        $("[data-z-tree='tree-view-01']").contextmenu({
            autoFocus: true,
            delegate: "[treenode_a]",
            preventContextMenuForPopup: true,
            preventSelect: true,
            taphold: true,
            menu: [],
            // Handle menu selection to implement a fake-clipboard
            select: function (event, ui) {
                contextMenuClick(ui, fnObj.treeView01.getNodeByTId($(ui.target).closest("[treenode]").attr("id")));
                // Optionally return false, to prevent closing the menu now
            },
            // Implement the beforeOpen callback to dynamically change the entries
            beforeOpen: function (event, ui) {
                var objNode = fnObj.treeView01.getNodeByTId($(ui.target).closest("[treenode]").attr("id"));

                $(this).contextmenu({
                    menu: getContextMenu(ui, objNode["nodeType"])
                });
            }
        });

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
            /*
            async: {
                enable: true,
                url: "/rc/rc001/getAllNodes",
                autoParam: ["uuid","parentUuid"] ,
                otherParam : _this.otherParam
            },
            */
            edit: {
                enable: true,
                editNameSelectAll: false,
                showRemoveBtn: false,
                showRenameBtn: false,
                drag: {
                    inner: true,
                    prev: false,
                    next: false
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
            callback: {
                beforeDrag: function () {
                    //return false;
                },
                onClick: function (e, treeId, treeNode, isCancel) {
                    var reqData = $.extend({}, treeNode);
                    var path = treeNode.getPath();
                    for (var key in reqData) {
                        if (!(key == "uuid" || key == "nodeType"))
                            delete(reqData[key]);
                    }

                    //tree는 자기 어머니까지 다해서 한방에 셋해야된다.
                    fnObj.naviView.clear();
                    for (var i = 0; i < path.length; i++) {
                        fnObj.naviView.setData(path[i]);
                    }
                    fnObj.pageView.resetPage();
                    if ($(".explorer_grid").css("display") == "none")
                        ACTIONS.dispatch(ACTIONS.GET_SUBDATA, reqData);
                    else
                        ACTIONS.dispatch(ACTIONS.GET_GRID_DATA, reqData);
                    //ACTIONS.dispatch(ACTIONS.GET_SUBDATA, reqData);
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
                },
                beforeClick: function (treeId, treeNode) {
                    /*
                    zTree = $.fn.zTree.getZTreeObj(treeId);
                    if (treeNode.isParent) {
                        zTree.expandNode(treeNode);//If it is the parent node,Expand the node
                    }else{
                        zTree.checkNode(treeNode, !treeNode.checked, true, true);//Click Check,Click Cancel again
                    }
                    */
                },
                beforeExpand: function (treeId, treeNode) {
                    /*
                    _this.otherParam = treeNode;
                    _this.reloadFlag = true;
                    _this.checkFlag = false;
                    return true;up
                    */
                },
                beforeCheck: function (treeId, treeNode) {
                    var zTree = $.fn.zTree.getZTreeObj(treeId);
                    if (!treeNode.children) {
                        _this.reloadFlag = true;
                        _this.checkFlag = true;
                        zTree.reAsyncChildNodes(treeNode, "refresh");
                    }
                    return true;
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

                    $('[data-z-tree="tree-view-01"] [treenode_a]').droppable({
                        tolerance: "pointer",
                        greedy: true,
                        hoverClass: "curSelectedNode",
                        drop: function (event, ui) {
                            $('#explorerDragWrapper').remove();

                            var parentNode = fnObj.treeView01.getNodeByTId($(this).parent().attr("id"));

                            if (!updateRecord(ui.draggable, parentNode)) {
                                return;
                            }
                        }
                    });
                },
                beforeDrop: function (treeId, treeNodes, targetNode, moveType, isCopy) {


                    console.log(targetNode);
                    console.log(treeNodes);
                    var msgCode = "";
                    var result = true;
                    if (targetNode.nodeType == "item") {
                        result = false;
                        msgCode = "RC001_01";
                    }
                    else {
                        var itemCnt = targetNode.childCnt - targetNode.children.length;

                        for (var i = 0; i < treeNodes.length; i++) {
                            if (treeNodes[i].nodeType == "normal" && itemCnt > 0) {
                                result = false;
                                msgCode = "RC001_08";
                                break;
                            }

                            if (treeNodes[i].nodeType != "item" && targetNode.nodeType != treeNodes[i].nodeType) {
                                result = false;
                                msgCode = "RC001_02";
                                break;
                            }
                            else if (treeNodes[i].nodeType == "item" && targetNode == null) {
                                result = false;
                                msgCode = "RC001_03";
                                break;
                            }
                        }
                    }

                    /*if(targetNode["nodeType"] == "normal" && targetNode["isParent"] == true){
                        result = false;
                        msgCode = "RC001_03";
                    }*/

                    if (!result)
                        axDialog.alert(axboot.getCommonMessage(msgCode));


                    return result;

                },
                onDrop: function (event, treeId, treeNodes, targetNode, moveType, isCopy) {

                    console.log(event);
                    console.log(treeId);
                    console.log(targetNode);
                    console.log(treeNodes);

                    /**
                     * Droppable Tree 2 Record
                     * Tree간의 이동이 아닐 경우 동작함
                     */
                    if (targetNode == null) {
                        var parentNode = null;
                        var currentNode = treeNodes[0];
                        var childNodes = currentNode.children;

                        // 이동될 Aggregation의 자신의 하위 Aggregation 여부를 체크하는 함수
                        var checkChildNodes = function (nodes, parentUuid) {
                            var rtn = false;

                            $.each(nodes, function (idx, item) {
                                if (item.children.length > 0) {
                                    if (checkChildNodes(item.children, parentUuid)) {
                                        rtn = true;
                                        return false;
                                    }
                                    ;
                                }

                                if (parentUuid == item["uuid"]) {
                                    rtn = true;
                                    return false;
                                }
                            });

                            return rtn;
                        };

                        if ($(event.target).attr("id") == $('#iconListArea').attr("id") ||
                            $(event.target).closest('.explorer_file').length > 0) {
                            parentNode = fnObj.treeView01.getNodeByParam("uuid", fnObj.naviView.getCurrent()["uuid"]);
                        } else {
                            if ($(event.target).closest('.explorer_folder_full').length > 0) {
                                parentNode = fnObj.treeView01.getNodeByParam("uuid", $(event.target).closest('.explorer_folder_full').attr("uuid"));
                            } else if ($(event.target).closest('.explorer_folder_empty').length > 0) {
                                parentNode = fnObj.treeView01.getNodeByParam("uuid", $(event.target).closest('.explorer_folder_empty').attr("uuid"));
                            }
                        }

                        if (parentNode == null) return;

                        if (checkChildNodes(currentNode.children, parentNode["uuid"])) {
                            axDialog.alert("하위 Aggregation으로 이동이 불가능합니다.");
                            return;
                        }

                        if (parentNode) {
                            if (!updateRecord(currentNode, parentNode, true)) {
                                return;
                            }
                        }

                        return;
                    }

                    var reqList = new Array();
                    var validate = true;
                    //validate 로직 추가 ( item은 최상위 불가, Aggregation의 타입에 따라서 상위 이동 불가 )
                    for (var i = 0; i < treeNodes.length; i++) {
                        if (treeNodes[i].nodeType == "item" && null == targetNode) {
                            validate = false;
                            break;
                        }
                        if (null == targetNode)
                            treeNodes[i].parentUuid = null;
                        else
                            treeNodes[i].parentUuid = targetNode.uuid;
                        reqList.push({
                            uuid: treeNodes[i].uuid,
                            parentUuid: treeNodes[i].parentUuid,
                            nodeType: treeNodes[i].nodeType,
                            parentNodeType: targetNode.nodeType,
                        })
                    }

                    if (!validate)
                        return;

                    ACTIONS.dispatch(ACTIONS.ITEMS_SAVE, reqList);
                    //오른쪽 데이터 refresh 로직 추가

                    console.log(treeNodes);
                    console.log(isCopy);

                    //요청
                    var reqData = $.extend({}, targetNode);
                    var path = targetNode.getPath();
                    for (var key in reqData) {
                        if (key != "uuid")
                            delete(reqData[key]);
                    }

                    //tree는 자기 어머니까지 다해서 한방에 셋해야된다.
                    fnObj.naviView.clear();
                    for (var i = 0; i < path.length; i++) {
                        fnObj.naviView.setData(path[i]);
                    }

                    if ($(".explorer_grid").css("display") == "none")
                        ACTIONS.dispatch(ACTIONS.GET_SUBDATA, reqData);
                    else
                        ACTIONS.dispatch(ACTIONS.GET_GRID_DATA, reqData);


                },
                //beforeAsync: function(treeId, treeNode){},

                // /onAsyncError: function(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown){alert("보내기고 에러");},
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
                    /*
                    if(_this.reloadFlag ){
                        if(!_this.checkFlag)
                        {
                            _this.target.zTree.addNodes(treeNode,_this.convertTreeData(msg.list));
                            _this.target.zTree.updateNode(treeNode);
                            _this.checkFlag = true;
                            //_this.target.zTree.reAsyncChildNodes(treeNode,"refresh");
                        }
                        else {
                        }

                    }
                    else
                        _this.target.setData(_this.convertTreeData(msg.list));
                     */
                }
            }
        }, []);
    },
    convertTreeData: function (_tree) {
        var iconObj = undefined;
        for (var i = 0; i < _tree.length; i++) {
            iconObj = this.getAggregationIcon(_tree[i]["nodeType"], _tree[i]["publishedStatus"])
            _tree[i] = $.extend({}, _tree[i], iconObj);
            iconObj = {};
        }
        return _tree;
    },
    getAggregationIcon: function (nodeType, publishedStatus) {
        if (nodeType == "normal" && publishedStatus == "published") {
            nodeType = publishedStatus;
        }

        var iconObj = {open: false, iconSkin: nodeType};
        /*switch(nodeType)
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
            case "temporary":
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
        }*/

        return iconObj;
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
    /*updateNode: function (data) {
        var treeNodes = this.target.getSelectedNodes();
        if (treeNodes[0]) {
            treeNodes[0].progCd = data.progCd;
        }
    },*/
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

fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    uuidFieldName: "uuid",
    entityName: "Record Explorer",
    mode: "grid",
    initView: function () {
        var _this = this;
        this.initInstance();
        this.gridObj.setFixedOptions({
            colCount: 3,
        });

        this.gridObj.onDataCellDblClicked(this.itemDbClick);

        this.makeGrid();
        this.gridObj.addRowAfterEvent(this.addRowAfterEvent);
        this.gridObj.itemClick(this.itemClick);

        this.gridObj.gridView.setHeader({
            height: 40,                 // 헤더 높이 지정
        });
    },
    itemDbClick: function (grid, index) {
        if(index["column"] != "code") return;

        fnObj.pageView.resetPage();
        var reqData = fnObj.gridView01.gridObj.getSelectedData();
        if (reqData["iconType"] == "file") {
            var item = getMenu("view item");
            var parentsObj = parent.window.fnObj;

            if(fnObj.gridView01.mode == "grid")
                reqData["parentUuid"] = fnObj.naviView.getCurrent()["uuid"];

            contextMenuClick({cmd : "ITEM_VIEW"}, reqData);
            return;
        }

        fnObj.naviView.setData({uuid: reqData["uuid"], name: reqData["title"], nodeType: reqData["type"]});

        reqData["nodeType"] = reqData["type"].toLowerCase();
        for (var key in reqData) {
            if (!("uuid" == key || "nodeType" == key))
                delete(reqData[key]);
        }
        ACTIONS.dispatch(ACTIONS.GET_GRID_DATA, reqData);
    },
    itemClick: function (data) {

    },
    setData: function (list) {
        /*{criteria : "value='file'",styles : "iconIndex=0"}
    ,{criteria : "value='file_v'",styles : "iconIndex=1"}
    ,{criteria : "value='folder'",styles : "iconIndex=2"}
    ,{criteria : "value='folder_t'",styles : "iconIndex=3"}
    ,{criteria : "value='folder_v'",styles : "iconIndex=4"}
    ,{criteria : "value='image'",styles : "iconIndex=5"}
    ,{criteria : "value='image_v'",styles : "iconIndex=6"}
    ,{criteria : "value='folder_open'",styles : "iconIndex=7"}
    ,{criteria : "value='folder_open_t'",styles : "iconIndex=8"}*/
        var data = undefined;
        var iconType = "";
        var nodeType = "";
        var isOpen = false;
        for (var i = 0; i < list.length; i++) {
            data = list[i];

            if(this.mode == "search"){
                data["type"] = data["nodeType"];
            }

            if (data["childCnt"] > 0) {
                isOpen = true;
            }
            switch (data["type"]) {
                case "Normal":
                    iconType = "folder" + (isOpen ? "_open" : "");
                    nodeType = data["type"].toLowerCase();
                    break;
                case "Temporary":
                    iconType = "folder" + (isOpen ? "_open" : "") + "_t";
                    nodeType = data["type"].toLowerCase();
                    break;
                case "Virtual":
                    iconType = "folder" + (isOpen ? "_open" : "") + "_v";
                    nodeType = data["type"].toLowerCase();
                    break;
                case "item":
                    iconType = "file";
                    nodeType = "item";
                    break;
                default:
                    iconType = "file";
                    nodeType = "item";
                    break;
            }
            isOpen = false;
            data["iconType"] = iconType;
            data["nodeType"] = nodeType;

            data["creationStartDate"] = getFormattedDate(data["creationStartDate"]);
            data["creationEndDate"] = getFormattedDate(data["creationEndDate"]);
            data["accumulationStartDate"] = getFormattedDate(data["accumulationStartDate"]);
            data["accumulationEndDate"] = getFormattedDate(data["accumulationEndDate"]);
            data["descriptionStartDate"] = getFormattedDate(data["descriptionStartDate"]);
            data["descriptionEndDate"] = getFormattedDate(data["descriptionEndDate"]);
        }

        this.gridObj.setData("set", list);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectionData();
    },
    disabledColumn : function()
    {
        this.gridObj.setCustomCellStyleRows("disable",function(row){
            if(row["type"] == "Normal" || row["type"] == "Temporary")
                return false;
            else
                return true;
        },["levelUuid"]);
    },
    validate: function () {
        var rs = this.model.validate();
        if (rs.error) {
            alert(rs.error[0].jquery.attr("title") + '을(를) 입력해주세요.');
            rs.error[0].jquery.focus();
            return false;
        }
        return true;
    }
});

fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid02",
    uuidFieldName: "fieldName",
    entityName: "RC_ITEM",
    initView: function () {
        var _this = this;
        this.initInstance();
        this.setColumnInfo(rc00102.column_info);

        this.setFixedOptions({
            colCount: 0
        });
        this.gridObj.setIndicator({
            visible: false
        });

        this.setEntityName("ExtraMetadata");
        this.makeGrid();
        this.setFilter(true);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectionData();
    },
    setFilter: function (value) {
        var filters = [{
            name: "NULL_VALUE",
            criteria: "value != ''",
            active: value
        }];

        this.gridObj.getGridView().addColumnFilters('value', filters, true);
    },
    validate: function () {
        var rs = this.model.validate();
        if (rs.error) {
            alert(rs.error[0].jquery.attr("title") + '을(를) 입력해주세요.');
            rs.error[0].jquery.focus();
            return false;
        }
        return true;
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

        $("#formView01 #propertiesView input, #formView01 #propertiesView textarea").on("focusout", function () {
            if (fnObj.formView.getData()["itemUuid"] == "" || fnObj.formView.getData()["aggregationUuid"] == "") return;

            var data = fnObj.formView.getData();
            if (fnObj.formView.getData()["isItem"]) {
                data["raAggregationUuid"] = fnObj.naviView.getCurrent()["uuid"];
                data["name"] = data["title"];
                data["riTypeUuid"] = data["itemTypeUuid"];
                data["riPublishedStatusUuid"] = data["publishedStatusUuid"];
                data["riDescriptionStartDate"] = data["descriptionStartDate"];
                data["riDescriptionEndDate"] = data["descriptionEndDate"];
                data["riAuthor"] = data["author"];
                ACTIONS.dispatch(ACTIONS.ITEM_SAVE, data);
            } else {
                data["parentAggregationUuid"] = fnObj.naviView.getCurrent()["uuid"];
                data["typeUuid"] = data["aggregationTypeUuid"];
                ACTIONS.dispatch(ACTIONS.AGG_SAVE, data);
            }
        });

        $("#formView01 #propertiesView input, #formView01 #propertiesView textarea").on("focus", function (event) {
            $(event.target).click(function (event) {
                event.stopPropagation();
            });
            fnObj.iconView.isOver = true;
        });

        $("#formView01 #propertiesView select").on("change", function () {
            if (fnObj.formView.getData()["itemUuid"] == "" || fnObj.formView.getData()["aggregationUuid"] == "") return;

            fnObj.formView.setFormData($(this).attr("data-ax-path"), $(this).val());

            var data = fnObj.formView.getData();
            if (fnObj.formView.getData()["isItem"]) {
                data["raAggregationUuid"] = fnObj.naviView.getCurrent()["uuid"];
                data["name"] = data["title"];
                data["riPublishedStatusUuid"] = data["publishedStatusUuid"];
                data["riDescriptionStartDate"] = data["descriptionStartDate"];
                data["riDescriptionEndDate"] = data["descriptionEndDate"];
                data["riAuthor"] = data["author"];
                ACTIONS.dispatch(ACTIONS.ITEM_SAVE, data);
            } else {
                data["parentAggregationUuid"] = fnObj.naviView.getCurrent()["uuid"];
                data["typeUuid"] = fnObj.formView.getData()["aggregationTypeUuid"];
                ACTIONS.dispatch(ACTIONS.AGG_SAVE, data);
            }
        });

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


        $("input[data-ax-path='from']").parents().eq(1).find("a").click(function () {
            var data = {
                popupCode: "PU123",
                preSearch: false,
                searchData: $("input[data-ax-path='from']").val().trim(),
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_FROM_SCH, data);
        });
        $("input[data-ax-path='from']").focusout(function () {

            if ("" != $(this).val().trim()) {
                var data = {
                    popupCode: "PU123",
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_FROM_SCH, data);
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


function getFormattedDate(str) {
    if(str == "undefined" || str == null) return;
    if(str.length == 8) {
        return str.substr(0, 4) + "-" + str.substr(4, 2) + "-" + str.substr(6);
    }else if(str.length == 6) {
        return str.substr(0, 4) + "-" + str.substr(4, 2);
    }else{
        return str;
    }
}