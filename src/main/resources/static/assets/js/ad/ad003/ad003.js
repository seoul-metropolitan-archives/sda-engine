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
    GET_CODE_DETAIL : function(caller, act, data)
    {
        if(!data["codeHeaderUuid"])
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

            if (
                ACTIONS.dispatch(ACTIONS.CODE_HEADER_PAGE_SAVE)
                && ACTIONS.dispatch(ACTIONS.CODE_DETAIL_PAGE_SAVE)
            )
                axToast.push(axboot.getCommonMessage("AA007"));
    },
    CODE_HEADER_PAGE_SAVE: function (caller, act, data) {
        var _this = this;

        var result = false;
        console.log(fnObj.gridView01.getData());
        axboot.ajax({
            url: "/api/v1/ad/ad003/saveCodeHeader",
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
    CODE_DETAIL_PAGE_SAVE : function(data)
    {
        var result = false;
        axboot.ajax({
            url : "/api/v1/ad/ad003/saveCodeDetail",
            type : "post",
            async : false,
            data : JSON.stringify(fnObj.gridView02.getData()),
            callback : function(res)
            {
                result = res;
                if(result)
                {
                    try
                    {
                        fnObj.gridView02.gridObj.commit();
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
fnObj.gridView01 = axboot.viewExtend(axboot.realGridView, {
    tagId : "realgrid01",
    entityName : "CODE_HEADER",
    beforeRowIdx : undefined,
    initView  : function()
    {
        var _this = this;
        this.setColumnInfo(ad00301.column_info);
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
                ACTIONS.dispatch(ACTIONS.GET_CODE_DETAIL,data);
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
    addRowBeforeEvent : function()
    {
        var _this = this;
        var uuid = undefined;
        axboot.ajax({
            url : "/api/v1/common/getUUID",
            type : "POST",
            async : false,
            callback:function(res)
            {
                uuid = res.map.uuid;
            }
        });
        var data = fnObj.gridView01.gridObj.getDefaultData();
        data[0] = uuid;
        fnObj.gridView01.gridObj.setDefaultData(data);
    },
    addRowAfterEvent : function()
    {
        fnObj.gridView02.clear();
    },
    getCodeHeaderUUID : function()
    {
        return this.gridObj.getSelectedData()["codeHeaderUuid"];
    }
 });
/*팝업 디테일 ( Column )*/
fnObj.gridView02 = axboot.viewExtend(axboot.realGridView, {
    tagId : "realgrid02",
    entityName : "CODE_DETAIL",
    initView: function ()
    {
        this.setColumnInfo(ad00302.column_info);
        this.makeGrid();
        this.gridObj.addRowBeforeEvent(this.addRowBeforeEvent);
    },
    clear : function () {
        this.setData([]);
    },
    addRowBeforeEvent : function()
    {
        var data = fnObj.gridView02.gridObj.getDefaultData();
        data[1] = fnObj.gridView01.getCodeHeaderUUID();
        fnObj.gridView02.gridObj.setDefaultData(data);
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