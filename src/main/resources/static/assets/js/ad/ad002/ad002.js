var fnObj = {};
var pageSize = 1000;
var errorStatusList = {};
var gridView = undefined;
var serviceList = [];
var handleResult = "";
var handleFailReason = "";
var cash10kEmptyStatus = "";
var cash50kEmptyStatus = "";

var selectedIndex = "";
var selectedErrorItem = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "POST",
            url: "/ad/ad002/getMessageList.do",
            data: JSON.stringify(fnObj.searchView.getData()),
            callback: function (res) {
                console.log(res);
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.setFocus();
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SAVE: function (caller, act, data) {
        if(fnObj.gridView01.getData().length < 1)
            return ;

        axboot.ajax({
            type: "POST",
            url: "/ad/ad002/save.do",
            data: JSON.stringify(fnObj.gridView01.getData()),
            callback: function (res) {
                axToast.push(axboot.getCommonMessage("AA007"));
                fnObj.gridView01.gridObj.commit();fnObj.gridView01
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
    pageStart : function () {
        var _this = this;
        $.ajax({
            url: "/assets/js/column_info/ad00201.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        _this.searchView.initView();
        _this.gridView01.initView();
    }
};

fnObj.searchView = axboot.viewExtend(axboot.formView,{
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData);
    },
    initView : function()
    {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();
    },
    initEvent: function () {
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
        console.log(data);
        return $.extend({},data);
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

})

fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid01",
    initView: function ()
    {
        this.initInstance();
        this.setColumnInfo(ad00201.column_info);
        this.setEntityName($("#realgridName").text());
        this.makeGrid();
        this.setFixedOptions({colCount : 2});
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    }
});

isDataChanged = function()
{
    return (fnObj.gridView01.isChangeData() == true) 
}