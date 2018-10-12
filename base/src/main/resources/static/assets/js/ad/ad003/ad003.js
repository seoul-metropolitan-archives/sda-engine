var fnObj = {};
var gridView = undefined;


var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        var _this = this;

        if(!data)
            data = fnObj.formView.getData();

        axboot.ajax({
            type: "POST",
            url: "/api/v1/ad/ad003/searchCodeHeader",
            data : JSON.stringify(data),
            async : false,
            callback: function (res) {
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.setData(res.list);
                fnObj.gridView02.clear();
                ACTIONS.dispatch(ACTIONS.GET_CODE_DETAIL,res.list[0]);

            }
        });
    },
    GET_CODE_DETAIL : function(caller, act, data)
    {
        if(!data || !data["codeHeaderUuid"])
            return ;
        axboot.ajax({
            url : "/api/v1/ad/ad003/getCodeDetailList"
            ,type : "POST"
            ,dataType : "JSON"
            ,data : JSON.stringify(data)
            ,callback : function(res)
            {
                fnObj.gridView02.setData(res.list);
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

        var codeHeaderList = [].concat(fnObj.gridView01.getData());
        var codeDetailList = [].concat(fnObj.gridView02.getData());

        axboot
            .call({
                url: "/api/v1/ad/ad003/saveCodeHeader",
                type: "post",
                data: JSON.stringify(codeHeaderList),
                callback: function (res) {
                    fnObj.gridView01.commit();
                }
            })
            .call({
                url : "/api/v1/ad/ad003/saveCodeDetail",
                type : "post",
                data: JSON.stringify(codeDetailList),
                callback: function (res) {
                    fnObj.gridView02.commit();
                }
            })
            .done(function () {
                axToast.push(axboot.getCommonMessage("AA007"));
            });


    },
    FORM_CLEAR: function (caller, act, data) {
    },
    CLOSE_TAB : function()
    {
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
            url: "/assets/js/column_info/ad00301.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/column_info/ad00302.js",
            dataType: "script",
            async : false,
            success: function(){}
        });

        /**/
        _this.formView.initView();
        _this.gridView01.initView();
        _this.gridView02.initView();

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
        $("input").keydown(function(event){
            switch(event.keyCode)
            {
                case 40:
                    fnObj.gridView01.gridObj.setFocus();
                    break;
            }
        })
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        console.log($.extend({}, data));
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

/*팝업 헤더*/
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid01",
    primaryKey : "codeHeaderUuid",
    entityName : "AD_CODE_HEADER",
    initView  : function()
    {
        var _this = this;
        this.initInstance();
        this.setColumnInfo(ad00301.column_info);
        this.makeGrid();
        /*this.gridObj.addRowBeforeEvent(this.addRowBeforeEvent);*/
        this.addRowAfterEvent(this.clearChild);
        this.removeRowAfterEvent(this.clearChild);
        this.setFixedOptions({
            colCount : 2
        });
        this.gridObj.itemClick(function(data){
            if(fnObj.gridView02.getData().length > 0)
            {
                axDialog.confirm({
                    msg: axboot.getCommonMessage("AA006")
                },function() {
                    if(this.key == "ok")
                    {
                        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                        ACTIONS.dispatch(ACTIONS.GET_CODE_DETAIL,data);
                    }else {
                        ACTIONS.dispatch(ACTIONS.GET_CODE_DETAIL,data);
                    }
                });
            }
            else
            {
                ACTIONS.dispatch(ACTIONS.GET_CODE_DETAIL,data);
            }
        });
        /**
         *
         * Single Pasted시 데이터에 대한 영향이 간다
         */
        this.gridObj.onCellEdited(function(gridWrapper,grid)
        {
            grid.commit(true);
            var parentData = fnObj.gridView01.gridObj.getSelectedData();

            var index = grid.getCurrent();

            if(index.fieldName.indexOf("attribute") > -1)
            {
                var parentColumnName = index.fieldName;
                var cnt = fnObj.gridView02.gridObj.getRowCnt();
                for(var rowIndex = 0; rowIndex < cnt.length; rowIndex++)
                {
                    fnObj.gridView02.gridObj.gridView.setValue(rowIndex,index.fieldName.replace("Str",""),"");
                }
                fnObj.gridView02.gridObj.commit(true);
                if(!parentData[parentColumnName] || "" == parentColumnName[parentColumnName])
                {
                    var column = fnObj.gridView02.gridObj.gridView.columnByName(index.fieldName.replace("Str",""));
                    column.labels = [];
                    column.values = [];
                    fnObj.gridView02.gridObj.gridView.setColumn(column);
                }
            }


        });
    },
    popupCallback : function(grid,data)
    {
        grid.commit(true);
        var parentData = fnObj.gridView01.gridObj.getSelectedData();

        var index = grid.getCurrent();

        if(index.fieldName.indexOf("attribute") > -1)
        {
            var parentColumnName = index.fieldName;
            var cnt = fnObj.gridView02.gridObj.getRowCnt();
            for(var rowIndex = 0; rowIndex < cnt.length; rowIndex++)
            {
                fnObj.gridView02.gridObj.gridView.setValue(rowIndex,index.fieldName.replace("Str",""),"");
            }
            fnObj.gridView02.gridObj.commit(true);
            if(!parentData[parentColumnName] || "" == parentColumnName[parentColumnName])
            {
                var column = fnObj.gridView02.gridObj.gridView.columnByName(index.fieldName.replace("Str",""));
                column.labels = [];
                column.values = [];
                fnObj.gridView02.gridObj.gridView.setColumn(column);
            }
            fnObj.gridView02.commit();
        }
    },
    clearChild : function()
    {
        fnObj.gridView02.gridObj.gridView.cancel();
        fnObj.gridView02.clearData();
    }
 });
/*팝업 디테일 ( Column )*/
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid02",
    entityName : "AD_CODE_DETAIL",
    primaryKey : "codeDetailUuid",
    parentsUuidFieldName : "codeHeaderUuid",
    parentsGrid : fnObj.gridView01,
    initView: function ()
    {
        this.initInstance();
        this.setColumnInfo(ad00302.column_info);
        this.makeGrid();
        /*this.gridObj.addRowBeforeEvent(this.addRowBeforeEvent);
        this.gridObj.onRowsPasted(this.onRowsPasted);
        */
        this.setFixedOptions({
            colCount : 2
        });

        this.gridObj.onDataCellClicked(this.setAttribute);
    },
    setAttribute : function(grid)
    {
        var parentData = fnObj.gridView01.gridObj.getSelectedData();


        var index = grid.getCurrent();

        if(index.fieldName.indexOf("attribute") > -1)
        {
            var parentColumnName = index.fieldName + "Code";

            var column = grid.columnByName(index.fieldName);
            if(parentData[parentColumnName] && "" != parentData[parentColumnName])
            {
                column.labels = axboot.commonCodeFilter(parentData[parentColumnName]).nameArr;
                column.values = axboot.commonCodeFilter(parentData[parentColumnName]).codeArr;
            }
            else {
                column.labels = [];
                column.values = [];
            }

            grid.setColumn(column);
        }
    },
    clear : function () {
        this.gridObj.setData("set",[]);
    },
    setData : function(list)
    {
        var columnList = ["attribute01Code","attribute02Code","attribute03Code","attribute04Code","attribute05Code","attribute06Code","attribute07Code","attribute08Code","attribute09Code","attribute10Code"]
        var data = fnObj.gridView01.getCurrentData();

        if(!data)
            return ;

        var getLabelNValue = function(code)
        {
            return {label : axboot.commonCodeFilter(code).nameArr,values :axboot.commonCodeFilter(code).codeArr}
        }

        var column = undefined;
        var attrData = undefined;
        for(var i = 0; i < columnList.length ;i++)
        {
            if(data[columnList[i]] && "" != data[columnList[i]])
            {
                try
                {
                    attrData = getLabelNValue(data[columnList[i]]);
                    column = this.gridObj.gridView.columnByName(columnList[i].replace("Code",""));
                    column.labels = attrData.label;
                    column.values = attrData.values;
                    this.gridObj.gridView.setColumn(column);
                }catch(exception)
                {

                }
            }
        }
        this.gridObj.setData("set",list);
        /*
        var column = this.gridObj.columnByName(key);

        */
    }
    /*
    addRowBeforeEvent : function()
    {
        var data = fnObj.gridView02.gridObj.getDefaultData();
        data[1] = fnObj.gridView01.getCodeHeaderUUID();
        fnObj.gridView02.gridObj.setDefaultData(data);
    },
    onRowsPasted : function(grid, items)
    {
        var data = undefined;
        for(var i = 0; i < items.length; i++)
        {
            fnObj.gridView02.gridObj.setValue(items[i],1,fnObj.gridView01.getCodeHeaderUUID());
        }
    }
    */
});