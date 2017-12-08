var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH : function(caller, act, data){
        axboot.ajax({
            url: "/rc/rc001/getAllNodes",
            data: JSON.stringify({uuid : ""}),
            dataType:"JSON",
            type : "post",
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.close();
        }
    },
    MOVE : function(caller,act,data){
        axboot.ajax({
            url: "/rc/rc001/move",
            data: JSON.stringify(data),
            dataType:"JSON",
            type : "post",
            callback: function (res) {
                if(res.status == 0 || res.status == 200)
                {
                    parent.axboot.modal.callback();
                    ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
                }
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

var fnObj = {
    pageStart: function () {
        fnObj.gridView01.initView(parent.axboot.modal.getData());
    }
};

fnObj.gridView01 = axboot.viewExtend(axboot.gridView,{
    tagId : "realgrid01",
    list : undefined,
    initView : function(data){
        console.log(data);
        this.list = data["selectedList"];
        this.initEvent();
        this.initGrid();
    },
    initGrid : function(){
        fnObj.gridView01.gridObj = new TreeGridWrapper(fnObj.gridView01.tagId, "/assets/js/libs/realgrid");
        fnObj.gridView01.gridObj.setGridStyle("100%", "445px");
        fnObj.gridView01.gridObj.setColumnInfo([
            {
                "name": "uuid",
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
                "name": "parentUuid",
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
                "name": "name",
                "fieldName": "",
                "width": "150",
                "visible": true,
                "styles": {
                    "textAlignment": "near"
                },
                "header": {
                    "text": ""
                }
            },
        ]);
        fnObj.gridView01.gridObj.makeGrid();
        fnObj.gridView01.gridObj.setRunAdd(false);
        fnObj.gridView01.gridObj.setRunDel(false);
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    },
    initEvent : function()
    {
        $("#apply").click(function(){

        });
    },
    setData : function(list){
        var treeList = new Array();
        var data = undefined;
        list.sort(function(a, b) {
            if (undefined == a["parentUuid"]||null == a["parentUuid"]) {
                return -1;
            } else {
                return a["parentUuid"]| - b["parentUuid"];
            }
        });
        //console.log(list);


        var matchingData = function(key, list)
        {
            var retList = new Array();
            for(var i = 0; i < list.length; i++)
            {
                if( key == list[i]["parentUuid"] )
                {
                    list[i].icon = 0;
                    list[i].rows =  matchingData(list[i]["uuid"], list);
                    retList.push(list[i]);
                }
            }
            return retList;

        }

        var treeData = undefined;
        for(var i = 0; i < list.length; i++)
        {
            treeData = list[i];
            if(treeData["parentUuid"] == null)
            {
                treeData.icon = 0;
                treeData.rows = matchingData(list[i]["uuid"],list);
                treeList.push(treeData);
            }
        }
        console.log(JSON.stringify({rows:treeList}));
        fnObj.gridView01.gridObj.setTreeData({rows:treeList}, "rows", "", "icon");
    }
});