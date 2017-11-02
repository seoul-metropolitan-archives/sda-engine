var fnObj = {};
var gridView = undefined;


var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        var _this = this;

        if(!data)
            data = fnObj.formView.getData();

        axboot.ajax({
            type: "POST",
            url: "/ad/ad004/ad004/searchPopupHeader",
            data : JSON.stringify(data),
            async : false,
            callback: function (res) {
                if (undefined === res.list || res.list.length < 1)
                {
                    fnObj.gridView01.addRow();
                }
                else {
                    fnObj.gridView01.setData(res.list);
                }
            }
        });
    },
    PAGE_SAVE : function(caller, act, data)
    {

            if (
                ACTIONS.dispatch(ACTIONS.POPUP_HEADER_PAGE_SAVE)
                && ACTIONS.dispatch(ACTIONS.POPUP_DETAIL_PAGE_SAVE)
            )
            {
                axToast.push(axboot.getCommonMessage("AA007"));
            }
    },
    POPUP_HEADER_PAGE_SAVE: function (caller, act, data) {
        var _this = this;

        var result = false;
        console.log(fnObj.gridView01.getData());
        axboot.ajax({
            url: "/ad/ad004/ad004/savePopupHeader",
            type: "post",
            async: false,
            data: JSON.stringify(fnObj.gridView01.getData()),
            callback:  function (res)
            {
                result = res;
                if(result)
                {
                    try
                    {
                        fnObj.gridView01.gridObj.commit();
                    }catch(e)
                    {
                        console.log("커밋 에러남");
                    }

                }

            }
        });
        return result;
    },
    POPUP_DETAIL_PAGE_SAVE : function(data)
    {
        var result = false;
        axboot.ajax({
            url : "/ad/ad004/ad004/savePopupDetail",
            type : "post",
            async : false,
            data : JSON.stringify(fnObj.gridView02.getData()),
            callback : function(res)
            {
                result = res;
                try {
                    result = true;
                    fnObj.gridView02.gridObj.commit();
                } catch (e) {
                    console.log("커밋 에러남");
                }
            }
        });
        return result;
    },
    GET_POPUP_DETAIL : function(data)
    {
        fnObj.sqlView.setData(fnObj.gridView01.getSQL());
        console.log(fnObj.gridView01.getPopupHeaderUUID());
        axboot.ajax({
            url : "/ad/ad004/ad004/getPopupDetail"
            ,type : "POST"
            ,dataType : "JSON"
            ,data : JSON.stringify({popupHeaderUUID : fnObj.gridView01.getPopupHeaderUUID()})
            ,callback : function(res)
            {
                fnObj.gridView02.setData(res.list);
            }
        });
    },
    FORM_CLEAR: function (caller, act, data) {
    },
    CLOSE_TAB : function()
    {
        alert("닫힌다! 살려줘!");
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
fnObj = {
    pageStart : function () {
        var _this = this;
        $.ajax({
            url: "/assets/js/column_info/ad00401.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/column_info/ad00402.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/libs/jquery-ui/jquery-ui.js",
            dataType: "script",
            async : false,
            success: function(){}
        });

        /**/
        _this.formView.initView();
        _this.gridView01.initView();
        _this.gridView02.initView();
        _this.sqlView.initView();

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,this.formView.getData());

    }
};
/*검색 창*/
fnObj.formView = axboot.viewExtend(axboot.formView,{
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData);
    },
    initView : function(){
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();
    }

    ,initEvent: function () {
        var _this = this;
        $("#check").click(function() {
            axboot.modal.open({
                modalType: "COMMON_POPUP",
                sendData: function () {
                    return {
                        popupCode : "PU001",
                        searchData : "사용자01"
                    };
                },
                callback: function (data) {
                    //$("#calleeEmpName").val(data.empName);
                    //$("#calleeEmpTelno").val(data.empPhoneNo);
                    console.log(data);
                    if(this.close)
                        this.close();
                }
            });
            event.preventDefault();

        });

    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        console.log($.extend({}, data));
        return $.extend({}, data);
    },
    /*
    getData : function()
    {
        return {
            popupCode       : $("#popupCode").val()
            , popupName     : $("#popupName").val()
            , serviceUUID   : $("#serviceList option:selected").val()
            , useYN         : $("input[name='useYN']:checked").val()
        }
    },*/
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

/*검색 창*/
fnObj.sqlView = axboot.viewExtend(axboot.baseView,{
    initView : function()
    {
        var _this = this;
        this.targetTag = $("#sql");
        $("#sql").keyup(function(){
            fnObj.gridView01.setPopupSQL(_this.targetTag.val());
        })
        $("#apply").click(function(){
            _this.convertSQL();
        });
    }
    ,setData : function(data)
    {
        if(!data)
            data = "";

        this.targetTag.val(data);
    }
    ,getData : function()
    {
        console.log(fnObj.gridView01.getPopupHeaderUUID());
        return {
            popupHeaderUUID : fnObj.gridView01.getPopupHeaderUUID()
            ,popupSQL : this.targetTag.val()
        };
    }
    ,clear : function()
    {
        this.targetTag.val("");
    }
    ,convertSQL : function()
    {
        var regExp = /(SELECT)((\s?,?([a-zA-Z0-9]*)_?,?)*)(FROM)?/gi;
        var data = this.targetTag.val().toUpperCase();
        var splitData = data.split("FROM");
        if(regExp.test(splitData[0]))
        {
            var columns = splitData[0].replace(regExp,"$2").split(",");
            console.log(this.targetTag.val().toUpperCase().replace(regExp,"$2"));
            console.log(columns);
            fnObj.gridView02.dynamicSetSqlColumns(columns);
            fnObj.gridView01.setPopupSQL(this.targetTag.val());
        }
    }
});

/*팝업 헤더*/
fnObj.gridView01 = axboot.viewExtend(axboot.realGridView, {
    tagId : "realgrid",
    entityName : "POPUP_HEADER",
    beforeRowIdx : undefined,
    initView  : function()
    {
        var _this = this;
        this.setColumnInfo(ad00401.column_info);
        /*
        this.gridObj.setOption({
            checkBar : {visible : true}
        })
        */
        this.makeGrid();
        this.gridObj.addRowBeforeEvent(this.addRowBeforeEvent);
        this.gridObj.addRowAfterEvent(this.addRowAfterEvent);

        this.gridObj.itemClick(function(data){
            if(fnObj.gridView02.getData().length < 1)
            {
                ACTIONS.dispatch(ACTIONS.GET_POPUP_DETAIL,data);
            }
            else
            {
                axDialog.confirm({
                    msg: "변경사항이 있습니다. 저장하시겠습니까?"
                },function() {
                    if(this.key == "ok")
                    {
                        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    }
                });
            }
        });
    },
    addDetailList : function(uuid, groupName,data, isReplace)
    {
        if(isReplace || !this.tempList[uuid])
        {
            if(!this.tempList[uuid])
                this.tempList[uuid] = {};

            this.tempList[uuid][groupName] = data;
        }


    },
    addRowBeforeEvent : function()
    {
        var _this = this;
        var uuid = undefined;
        var data = _this.gridObj.getDefaultData();
        axboot.ajax({
            url : "/api/v1/common/getUUID",
            type : "POST",
            async : false,
            callback:function(res)
            {
                uuid = res.map.uuid;
            }
        });

        data[0] = uuid;
        this.gridObj.setDefaultData(data);
        this.gridView02.clear();
    },
    addRowAfterEvent : function()
    {
        fnObj.gridView02.clear();
        fnObj.sqlView.clear();
    },
    getPopupHeaderUUID : function()
    {
        return this.gridObj.getSelectedData()["popupHeaderUUID"];
    },
    setPopupSQL : function(sql)
    {
        this.gridObj.setValue(this.gridObj.getCurrent().dataRow,this.gridObj.getFieldIndex("popupSQL"), sql);
    },
    getSQL : function()
    {
        return this.gridObj.getSelectedData()["popupSQL"];
    }
 });
/*팝업 디테일 ( Column )*/
fnObj.gridView02 = axboot.viewExtend(axboot.realGridView, {
    tagId : "realgrid2",
    entityName : "POPUP_DETAIL",
    initView: function ()
    {
        this.setColumnInfo(ad00402.column_info);
        this.makeGrid();
        this.gridObj.onRowsPasted(this.onRowsPasted);
    },
    dynamicSetSqlColumns : function(list)
    {
        console.log(fnObj.gridView01.getPopupHeaderUUID());
        var popupDetailTemplate = {
            popupDetailUUID : ""
            ,popupHeaderUUID: fnObj.gridView01.getPopupHeaderUUID()
            ,sqlColumn      : ""
            ,title          : ""
            ,width          : ""
            ,inputMethod    : ""
            ,align          : ""
            ,tree           : ""
            ,treeRelation   : ""
            ,orderNo        : ""
            ,insertUUID : ""
            ,insertUserName : ""
            ,insertDate : ""
            ,updateUUID : ""
            ,updateUserName : ""
            ,updateDate : ""
        }
        var data = undefined;

        var dataProvider = this.gridObj.getGridView().getDataProvider();
        var compList = dataProvider.getJsonRows();

        var isExistRow = function(list,columnName,columnValue)
        {
            var result = false;
            for(var i = 0; i < list.length; i++)
            {
                if(list[i][columnName] == columnValue && !(dataProvider.getRowState(i) == "deleted"))
                {
                    result = true;
                    list.splice(i,1);
                    break;
                }
            }
            return result;
        }

        for(var i = 0; i < list.length; i++)
        {
            //detailList.push($.extend({},popupDetailTemplate,{sqlColumn : list[i]}))
            data = list[i];
            if(data.includes(" AS "))
            {
                data = data.split(" AS ")[1];
            }
            else if(data.includes("."))
            {
                data = data.split(".")[1];
            }
            data = data.replace(/\s/gi,"");
            if(!isExistRow(compList,"sqlColumn",data))
            {
                this.gridObj.addRow($.extend({},popupDetailTemplate,{sqlColumn : data}));
            }
        }

        for(var i = 0; i < compList.length;i ++)
        {
            var rowIndex = dataProvider.searchDataRow({
                startIndex: -1
                ,fields:["sqlColumn"]
                ,values : [compList[i]["sqlColumn"]]
            });
            dataProvider.removeRow(rowIndex);
        }
        //this.setData(detailList,"append");
    },
    clear : function () {
        this.setData([]);
    },
    onRowsPasted : function(grid, items)
    {
        var data = undefined;
        for(var i = 0; i < items.length; i++)
        {
            fnObj.gridView02.gridObj.setValue(items[i],1,fnObj.gridView01.getPopupHeaderUUID());
        }
    }

});
isDataChanged = function()
{
    if (fnObj.gridView01.isChangeData() == true || fnObj.gridView02.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}