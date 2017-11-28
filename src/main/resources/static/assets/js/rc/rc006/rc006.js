var fnObj = {};
var gridView = undefined;


var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        var _this = this;

        if(!data)
            data = fnObj.formView.getData();

        axboot.ajax({
            type: "POST",
            url: "/rc/rc006/rc006/searchLevelOfDescription",
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
    PAGE_SAVE : function(caller, act, data)
    {

        if (
            ACTIONS.dispatch(ACTIONS.LEVEL_OF_DESCRIPTION_PAGE_SAVE)
        )
        {
            axToast.push(axboot.getCommonMessage("AA007"));
        }
    },
    LEVEL_OF_DESCRIPTION_PAGE_SAVE: function (caller, act, data) {
        var _this = this;

        var result = false;
        console.log(fnObj.gridView_h.getData());
        axboot.ajax({
            url: "/rc/rc006/rc006/saveLevelOfDescription",
            type: "post",
            async: false,
            data: JSON.stringify(fnObj.gridView_h.getData()),
            callback:  function (res)
            {
                result = res;
                if(result)
                {
                    try
                    {
                        fnObj.gridView_h.commit();
                    }catch(e)
                    {
                        console.log("커밋 에러남");
                    }

                }

            }
        });
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
            url: "/assets/js/column_info/rc00601.js",
            dataType: "script",
            async : false,
            success: function(){}
        });

        _this.formView.initView();
        _this.gridView_h.initView();

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

/*Level Of Description*/
fnObj.gridView_h = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid",
    beforeRowIdx : undefined,
    primaryKey : "levelUuid",
    initView  : function()
    {
        var _this = this;
        this.initInstance();
        this.setColumnInfo(rc00601.column_info);

        this.setFixedOptions({
            colCount: 2
        });
        this.makeGrid();
    }
});
/*
isDataChanged = function()
{
    if (fnObj.gridView_h.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}*/