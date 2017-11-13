var fnObj = {};
var gridView = undefined;


var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        var _this = this;

        if(!data)
            data = fnObj.formView.getData();

        axboot.ajax({
            type: "POST",
            url: "/ad/ad006/ad006/searchEntityType",
            data : JSON.stringify(data),
            async : false,
            callback: function (res) {
                if (undefined === res.list || res.list.length < 1)
                {
                    fnObj.gridView_h.addRow();
                }
                else {
                    fnObj.gridView_h.setData(res.list);

                }
            }
        });
    },
    GET_ENTITY_DETAIL : function(caller, act, data)
    {

        if(!data["entityTypeUuid"])
            return ;

        axboot.ajax({
            url : "/ad/ad006/ad006/getEntityColumnList"
            ,type : "POST"
            ,dataType : "JSON"
            ,data : JSON.stringify(data)
            ,callback : function(res)
            {
                fnObj.gridView_d.setData(res.list);
            }
        });
    },
    PAGE_SAVE : function(caller, act, data)
    {

        if (
            ACTIONS.dispatch(ACTIONS.ENTITY_TYPE_PAGE_SAVE)
            && ACTIONS.dispatch(ACTIONS.ENTITY_COLUMN_PAGE_SAVE)
        )
        {
            axToast.push(axboot.getCommonMessage("AA007"));
        }
    },
    ENTITY_TYPE_PAGE_SAVE: function (caller, act, data) {
        var _this = this;

        var result = false;
        console.log(fnObj.gridView_h.getData());
        axboot.ajax({
            url: "/ad/ad006/ad006/saveEntityType",
            type: "post",
            async: false,
            data: JSON.stringify(fnObj.gridView_h.getData()),
            callback:  function (res)
            {
                result = res;
                if(result) {
                    try {
                        fnObj.gridView_h.gridObj.commit();
                    } catch (e) {
                        console.log("커밋 에러남");
                    }

                }

            },
            options: {
                onError: axboot.viewError
            }
        });
        if(!result)
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,fnObj.formView.getData());

        return result;
    },
    ENTITY_COLUMN_PAGE_SAVE : function(data)
    {
        var result = false;
        axboot.ajax({
            url : "/ad/ad006/ad006/saveEntityColumn",
            type : "post",
            async : false,
            data : JSON.stringify(fnObj.gridView_d.getData()),
            callback : function(res)
            {
                result = res;
                try {
                    result = true;
                    fnObj.gridView_d.gridObj.commit();
                } catch (e) {
                    console.log("커밋 에러남");
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
        if(!result){
            ACTIONS.dispatch(ACTIONS.GET_ENTITY_DETAIL,fnObj.gridView_h.gridObj.getSelectedData());
}


        return result;
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
            url: "/assets/js/column_info/ad00601.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/column_info/ad00602.js",
            dataType: "script",
            async : false,
            success: function(){}
        });

        _this.formView.initView();
        _this.gridView_h.initView();
        _this.gridView_d.initView();

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,this.formView.getData());
        ACTIONS.dispatch(ACTIONS.GET_ENTITY_DETAIL, this.gridView_h.gridObj.getSelectedData());
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
                    fnObj.gridView_h.gridObj.setFocus();
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

/*엔티티 헤더*/
fnObj.gridView_h = axboot.viewExtend(axboot.realGridView, {
    tagId : "realgrid1",
    entityName : "ENTITY_HEADER",
    beforeRowIdx : undefined,
    initView  : function()
    {
        var _this = this;
        this.setColumnInfo(ad00601.column_info);
        /*
        this.gridObj.setOption({
            checkBar : {visible : true}
        })
        */
        this.makeGrid();
        this.gridObj.addRowBeforeEvent(this.addRowBeforeEvent);
        this.gridObj.addRowAfterEvent(this.addRowAfterEvent);

        this.gridObj.itemClick(function(data){
            if(fnObj.gridView_d.getData().length < 1)
            {
                fnObj.formView.setFormData("entityTypeCode", data.entityType);
                ACTIONS.dispatch(ACTIONS.GET_ENTITY_DETAIL,data);
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
                    else
                    {
                        ACTIONS.dispatch(ACTIONS.GET_ENTITY_DETAIL, data);
                    }
                });
            }
        });
    },

    addRowBeforeEvent : function()
    {
        var _this = this;
        var uuid = undefined;
        axboot.ajax({
            url : "/ad/ad006/ad006/getUUID",
            type : "POST",
            async : false,
            callback:function(res)
            {
                uuid = res.map.uuid;
            }
        });
        var data = fnObj.gridView_h.gridObj.getDefaultData();
        data[0] = uuid;
        fnObj.gridView_h.gridObj.setDefaultData(data);
    },
    addRowAfterEvent : function()
    {
        fnObj.gridView_d.clear();
    },
    getEntityTypeHeaderUUID : function()
    {
        return this.gridObj.getSelectedData()["entityTypeUuid"];
    }
});
/*엔티티 디테일 ( Column )*/
fnObj.gridView_d = axboot.viewExtend(axboot.realGridView, {
    tagId : "realgrid2",
    entityName : "ENTITY_DETAIL",
    initView: function ()
    {
        this.setColumnInfo(ad00602.column_info);
        this.makeGrid();
        this.gridObj.addRowBeforeEvent(this.addRowBeforeEvent);
        this.gridObj.onRowsPasted(this.onRowsPasted);
    },
    clear : function () {
        this.setData([]);
    },
    addRowBeforeEvent : function()
    {
        var data = fnObj.gridView_d.gridObj.getDefaultData();
        data[1] = fnObj.gridView_h.getEntityTypeHeaderUUID();
        fnObj.gridView_d.gridObj.setDefaultData(data);
    },
    onRowsPasted : function(grid, items)
    {
        var data = undefined;
        for(var i = 0; i < items.length; i++)
        {
            fnObj.gridView_d.gridObj.setValue(items[i],1,fnObj.gridView_h.getEntityTypeHeaderUUID());
        }
    }
});
isDataChanged = function()
{
    if (fnObj.gridView_h.isChangeData() == true || fnObj.gridView_d.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}