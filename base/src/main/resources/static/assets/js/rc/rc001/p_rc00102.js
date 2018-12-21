var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_CLOSE: function (caller, act, data) {
        if(parent.axboot.modalOpener == "modal") {
            parent.axboot.modal.close();
            parent.axboot.modal.callback(data);
        }else if(parent.axboot.modalOpener == "commonModal") {
            parent.axboot.commonModal.close();
            parent.axboot.commonModal.callback(data);
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
        var param = null;

        if(parent.axboot.modalOpener == "modal")
            param = parent.axboot.modal.getData();
        else if(parent.axboot.modalOpener == "commonModal")
            param = parent.axboot.commonModal.getData();

        fnObj.popupView.initView(param);
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
            if(parent.axboot.modalOpener == "modal") {
                parent.axboot.modal.close();
            }else if(parent.axboot.modalOpener == "commonModal") {
                parent.axboot.commonModal.close();
            }
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