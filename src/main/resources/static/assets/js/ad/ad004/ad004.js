var ad004 = function()
{
    var name = "POPUP";
    var popupGridView = undefined;
    var popupDetailGridView = undefined;
    var serviceList = {};
    var init = function()
    {
        initInstnace();
        initEvent();
        initDisplay();
    }

    var initInstnace = function()
    {
        $.ajax({
            url: "/assets/js/controller/simple_controller.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/ad/ad000/ad000_controller.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/column_info/ad004_h.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/column_info/ad004_d.js",
            dataType: "script",
            async : false,
            success: function(){}
        });

        popupGridView = new GridWrapper("realgrid","/assets/js/libs/realgrid");
        popupGridView.setGridStyle("100%","100%");
        popupDetailGridView = new GridWrapper("realgrid2","/assets/js/libs/realgrid");
        popupDetailGridView.setGridStyle("100%","100%");
    }

    var initEvent = function()
    {

    }

    var initDisplay = function()
    {
        initServiceCombo();

        initGridView();
    }

    var initGridView = function()
    {
        popupGridView.setColumnInfo(popup.column_info).setEntityName(name);
        popupGridView.makeGrid();
        popupGridView.load("/ad/ad004/ad004/searchPopupHeader.do",{});

        popupDetailGridView.setColumnInfo(popup_detail.column_info).setEntityName("PopupDetail").makeGrid();


    }

    var initServiceCombo = function()
    {
        rmsoft.controller.ad.ad000.request("getService",{},false,function(response){
            console.log(JSON.stringify(response));
            var data = undefined;
            for(var i = 0;i < response.list.length; i++)
            {
                data = response.list[i];
                serviceList[data.service_uuid] =
                    {
                        "label" : data.service_name,
                        "value": data.service_uuid
                    }
            }
            setComboBox();
        });
    }

    var setComboBox = function()
    {
        for(var uuid in serviceList)
        {
            $("#serviceList").append($("<option>").val(uuid).text(serviceList[uuid].label));
        }
    }

    init();

}

$(document).ready(function(){
    var page = new ad004();
});