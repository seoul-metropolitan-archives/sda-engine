/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.close();
        }
    },
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            url: "/api/v1/common/popup/search",
            dataType : "JSON",
            type : "POST",
            data: JSON.stringify(this.formView01.getData()),
            callback: function (res) {
                caller.gridView01.setData(res.list);
                caller.gridView01.gridObj.getGridView().resetSize();
            }
        });
        return false;
    },
    PAGE_CHOICE: function (caller, act, data) {
        var list = caller.gridView01.getData();
        if (parent && parent.axboot && parent.axboot.modal) {
            parent.axboot.modal.callback(list); // 부모창에 callback 호출
        }
    /*else {
        alert("선택된 목록이 없습니다.");
    }*/
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

var CODE = {};
// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var parentsData = parent.axboot.modal.getData();
    this.popupCode = parentsData["popupCode"];
    $("#searchField").val(parentsData["searchData"]);
    fnObj.formView01.initView();
    fnObj.gridView01.initView();
};

fnObj.pageResize = function () {

};

fnObj.formView01 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData);
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
        $("#inquiry").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        });

        $("#searchField").keydown(function(event){
            console.log(event.keyCode);
            switch(event.keyCode)
            {
                case 13:
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    break;
                case 40:
                    if(fnObj.gridView01.gridObj.getRowCnt().length > 0)
                        fnObj.gridView01.gridObj.setFocus();
                    break;
            }
        });

        $("#searchField").focus();
        
        $("#okPopup").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_CHOICE);
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
        });
        $("#closePopup").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
        });
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        console.log($.extend({}, data));
        data = {popupCode : fnObj.popupCode,searchField : $("#searchField").val(),isTree : fnObj.isTree}
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
    tagId : "popupGrid01",
    entityName : "",
    initView: function () {
        this.initGrid();

    },
    initGrid : function()
    {
        var _this = this;
        axboot.ajax({
            url : "/api/v1/common/popup/getPopupInfo",
            dataType : "JSON",
            type : "POST",
            async : false,
            data: JSON.stringify({popupCode : fnObj.popupCode}),
            callback : function(res)
            {
                res = res.map;
                fnObj.isTree = res["popupInfo"]["treeYN"];
                if(fnObj.isTree == 'Y')
                    fnObj.gridView01.gridObj = new TreeGridWrapper(fnObj.gridView01.tagId, "/assets/js/libs/realgrid");
                else
                    fnObj.gridView01.gridObj = new SimpleGridWrapper(fnObj.gridView01.tagId, "/assets/js/libs/realgrid");

                fnObj.gridView01.gridObj.setGridStyle("100%", "100%");
                fnObj.gridView01.gridObj.setEntityName(fnObj.gridView01.entityName);
                fnObj.isMultiselect = res["popupInfo"]["multiselectYN"] == 'Y';
                fnObj.gridView01.gridObj.setOption({
                    checkBar: {visible: res["popupInfo"]["multiselectYN"] == 'Y',exclusive: res["popupInfo"]["multiselectYN"] != 'Y' },
                    indicator: {visible: true}
                });
                if(res["popupInfo"]["treeYN"] == 'Y')
                {
                    var treeData = undefined;
                    for(var i = 0; i < res.columnInfo.length; i++)
                    {
                        treeData = res.columnInfo[i];
                        if("Y" == treeData.treeColumnYN)
                        {
                            fnObj.treeColumn = treeData["name"];
                        }else if("parent" == treeData.treeRelationType)
                        {
                            fnObj.parent = treeData["name"];
                        }else if("child" == treeData.treeRelationType)
                        {
                            fnObj.child = treeData["name"];
                        }

                    }
                }
                console.log(res.columnInfo);
                fnObj.gridView01.gridObj.setColumnInfo(res.columnInfo);
                fnObj.gridView01.gridObj.makeGrid();
                $("#popupGrid01").fadeIn(100);
                //fnObj.gridView01.gridObj.setData("set",[{"USER_NAME" : "test",""}])
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                fnObj.gridView01.initEvent();
            }
        });
    },
    initEvent : function()
    {
        fnObj.gridView01.gridObj.onKeydown(function(grid, key, ctrl, shift, alt){
            switch (key) {
                case 13:
                    ACTIONS.dispatch(ACTIONS.PAGE_CHOICE);
                    ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
                    break;
            }
        });
    },
    setData : function(list)
    {
        if(fnObj.isTree == "Y")
        {
            var treeList = new Array();
            var data = undefined;
            list.sort(function(a, b) {
                if (undefined == a[fnObj.parent]||null == a[fnObj.parent]) {
                    return -1;
                } else {
                    return a[fnObj.parent]| - b[fnObj.parent];
                }
            });
            console.log(list);


            var matchingData = function(key, list)
            {
                var retList = new Array();
                for(var i = 0; i < list.length; i++)
                {
                    if( key == list[i][fnObj.parent] )
                    {
                        list[i].icon = 0;
                        list[i].rows =  matchingData(list[i][fnObj.child], list);
                        retList.push(list[i]);
                    }
                }
                return retList;

            }

            var treeData = undefined;
            for(var i = 0; i < list.length; i++)
            {
                treeData = list[i];
                if(treeData[fnObj.parent] == null)
                {
                    treeData.icon = 0;
                    treeData.rows = matchingData(list[i][fnObj.child],list);
                    treeList.push(treeData);
                }
            }
            console.log(JSON.stringify({rows:treeList}));
            fnObj.gridView01.gridObj.setTreeData({rows:treeList}, "rows", "", "icon");
            //fnObj.gridView01.gridObj.setTreeDataForArray(treeList,"rows");
        }
        else
            this.gridObj.setData("set", list);

        $("#popupGrid01").fadeIn(100);
    },
    getData : function()
    {
        if(fnObj.isMultiselect)
            return this.gridObj.getCheckedList();
        else
            return this.gridObj.getSelectedData();

    }
});