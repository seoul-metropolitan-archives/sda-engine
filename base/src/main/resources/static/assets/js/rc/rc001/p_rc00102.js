var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.close();
        }
    },
    UPDATE_STATE : function(caller,act,data){
        axboot.ajax({
            url: "/rc/rc001/updateState",
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
        fnObj.popupView.initView(parent.axboot.modal.getData());
    }
};

fnObj.popupView = axboot.viewExtend({
    list : undefined,
    initView : function(data){
        console.log(data);
        this.list = data["selectedList"];
        this.initEvent();
    },
    initEvent : function()
    {
        var _this = this;
        $("#close").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE)
        })
        $("#apply").click(function(){
            var reqList = new Array();
            var publishedStatusUuid = $("select[data-ax-path='publishedStatusUuid']").val()
            for(var i = 0; i < _this.list.length; i++)
            {
                reqList.push({
                    publishedStatusUuid : publishedStatusUuid,
                    aggregationUuid : _this.list[i]["uuid"]
                });
            }
            ACTIONS.dispatch(ACTIONS.UPDATE_STATE,reqList);
        });
    }
});