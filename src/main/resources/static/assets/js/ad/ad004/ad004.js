var fnObj = {};
var gridView = undefined;


var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        var _this = this;

        if(!fnObj.gridView02.validate())
            return ;

        if(fnObj.gridView02.getData().length > 0)
        {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA006")
            }, function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            });
        }else {
            fnObj.gridView02.gridObj.resetCurrent();
        }

        if(!data)
            data = fnObj.formView.getData();

        axboot.ajax({
            type: "POST",
            url: "/api/v1/ad/ad004/searchPopupHeader",
            data : JSON.stringify(data),
            async : false,
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.setFocus();
                if(res.list.length > 0)
                {
                    fnObj.sqlView.setData(res.list[0]["popupSQL"]);
                    ACTIONS.dispatch(ACTIONS.GET_POPUP_DETAIL,res.list[0]);
                }
            }
        });
    },
    PAGE_SAVE : function(caller, act, data)
    {
        if(
            !fnObj.gridView01.validate()
            || !fnObj.gridView02.validate()
        )
            return ;

        var popupHeaderList = [].concat(fnObj.gridView01.getData());
        var popupDetailList = [].concat(fnObj.gridView02.getData());

        axboot
            .call({
                url: "/api/v1/ad/ad004/savePopupHeader",
                type: "post",
                data: JSON.stringify(popupHeaderList),
                callback: function (res) {
                    fnObj.gridView01.commit();
                }
            })
            .call({
                url : "/api/v1/ad/ad004/savePopupDetail",
                type : "post",
                data: JSON.stringify(popupDetailList),
                callback: function (res) {
                    fnObj.gridView02.commit();
                }
            })
            .done(function () {
                axToast.push(axboot.getCommonMessage("AA007"));
            });

    },
    /*POPUP_HEADER_PAGE_SAVE: function (caller, act, data) {
        var _this = this;

        var result = false;

        if(!fnObj.gridView01.validate())
            return false;

        var list = fnObj.gridView01.getData();

        if(list.length < 1)
            return true;

        axboot.ajax({
            url: "/ad/ad004/savePopupHeader",
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

        if(!fnObj.gridView02.validate())
            return false;

        var list = fnObj.gridView02.getData()

        if(list.length < 1)
            return true;

        axboot.ajax({
            url : "/ad/ad004/savePopupDetail",
            type : "post",
            async : false,
            data : JSON.stringify(list),
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
    */
    GET_POPUP_DETAIL : function(caller, act, data)
    {
        var reqData = {}
        try
        {
            reqData["popupHeaderUuid"] = fnObj.gridView01.getUUID();
        }
        catch(exception)
        {

        }

        if(!reqData["popupHeaderUuid"])
            reqData = data

        axboot.ajax({
            url : "/api/v1/ad/ad004/getPopupDetail"
            ,type : "POST"
            ,dataType : "JSON"
            ,data : JSON.stringify(reqData)
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
        return ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
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
        $("#popup").click(function() {
            var data = fnObj.gridView01.getCurrentData();
            console.log(data);
            axboot.modal.open({
                modalType: "COMMON_POPUP",
                preSearch : false,
                sendData: function () {
                    return {
                        popupCode : data["popupCode"],
                        searchData : ""
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
fnObj.sqlView = axboot.viewExtend(axboot.commonView,{
    initView : function()
    {
        var _this = this;
        this.targetTag = $("#sql");
        $("#sql").keyup(function(event){

            fnObj.gridView01.setPopupSQL(_this.targetTag.val());
        })
        $("#apply,#sql").keydown(function(event){
            if(9 == event.keyCode)
            {
                console.log($(this).attr("id"));
                switch($(this).attr("id"))
                {
                    case "apply":
                        fnObj.gridView02.setFocus();
                        break;
                    case "sql":
                        $("#apply").parents().eq(2).children().eq(0).focus();
                        break;
                }
            }
        });
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
        console.log(fnObj.gridView01.getUUID());
        return {
            popupHeaderUUID : fnObj.gridView01.getUUID()
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
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid",
    uuidFieldName : "popupHeaderUuid",
    entityName : "POPUP_HEADER",
    initView  : function()
    {
        var _this = this;
        this.initInstance();
        this.setColumnInfo(ad00401.column_info);
        this.makeGrid();
        this.addRowAfterEvent(this.clearChild);
        this.removeRowAfterEvent(this.clearChild);
        this.gridObj.itemClick(this.itemClick);
        this.gridObj.setFixedOptions({
            colCount : 2
        })

    },
    itemClick : function(data){
        if(fnObj.gridView02.gridObj.validate() && fnObj.gridView02.getData().length > 0)
        {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA006")
            },function() {
                if(this.key == "ok")
                {
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    fnObj.sqlView.setData(data["popupSQL"]);
                    ACTIONS.dispatch(ACTIONS.GET_POPUP_DETAIL,data);
                }
                else {
                    ACTIONS.dispatch(ACTIONS.GET_POPUP_DETAIL);
                }
            });
        }
        else
        {
            fnObj.sqlView.setData(data["popupSQL"]);
            ACTIONS.dispatch(ACTIONS.GET_POPUP_DETAIL,data);
        }
    },
    clearChild : function()
    {
        fnObj.gridView02.gridObj.gridView.cancel();
        fnObj.gridView02.clearData();
        fnObj.sqlView.clear();
    },
    setPopupSQL : function(sql)
    {
        this.gridObj.commit(true);
        this.gridObj.setValue(this.gridObj.getCurrent().dataRow,this.gridObj.getFieldIndex("popupSQL"), sql);
        this.gridObj.dataProvider.setRowState(this.gridObj.getCurrent().dataRow, "updated", false)
    },
    getSQL : function()
    {
        return this.gridObj.getSelectedData()["popupSQL"];
    }
});
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid2",
    entityName : "POPUP_DETAIL",
    uuidFieldName : "popupDetailUuid",
    parentsUuidFieldName : "popupHeaderUuid",
    parentsGrid : fnObj.gridView01,
    initView: function ()
    {
        var _this = this;
        this.initInstance();
        this.setColumnInfo(ad00402.column_info);
        this.makeGrid();
        this.gridObj.setFixedOptions({
            colCount : 2
        })
    },
    dynamicSetSqlColumns : function(list)
    {
        console.log(fnObj.gridView01.getUUID());
        fnObj.gridView01.gridObj.setDoAppendValidate(false);

        var popupDetailTemplate = {
            popupDetailUuid : ""
            ,popupHeaderUuid: fnObj.gridView01.getUUID()
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
            var column = undefined;
            if(!isExistRow(compList,"sqlColumn",data))
            {
                column = this.gridObj.columnByName("sqlColumn");
                column.defaultValue = data;
                this.gridObj.setColumn(column);
                this.gridObj.addRow();
                this.gridObj.gridView.commit(true);
                column = this.gridObj.columnByName("sqlColumn");
                column.defaultValue = "";
                this.gridObj.setColumn(column);
                this.gridObj.gridView.commit(true);
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
        fnObj.gridView01.gridObj.setDoAppendValidate(true);
        //this.setData(detailList,"append");
    },
    clear : function () {
        this.setData([]);
    }
});
ㄷㄹ