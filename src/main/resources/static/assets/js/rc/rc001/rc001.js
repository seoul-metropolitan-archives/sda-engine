var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            url: "/rc/rc001/getAllNodes",
            data: $.extend({},data,{isDisplayItem : $("#isDisplayItem").prop("checked")}),
            callback: function (res) {
                console.log(res.list);
                fnObj.treeView01.setData({}, res.list, data);
                /*if (res.list.length > 0) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, res.list[0]);
                }*/

            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    GET_GRID_DATA : function(caller, act, data)
    {
        axboot.ajax({
            //url: "/rc/rc001/getGridData",
            url: "/rc/rc001/getGridDataForPaging",
            data: $.extend({},data,fnObj.pageView.getPageInfo()),
            callback: function (res) {
                console.log(res.list);
                fnObj.gridView01.setData(res.list,true);
                fnObj.pageView.setPage(res);
                fnObj.gridView01.gridObj.getGridView().resetSize();
                ACTIONS.dispatch(ACTIONS.GET_NAVI_DATA,data);
                /*if (res.list.length > 0) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, res.list[0]);
                }*/

            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    GET_SUBDATA : function(caller, act, data)
    {
        console.log(data);
        axboot.ajax({
            url: "/rc/rc001/getAllNodeForPaging",
            data: $.extend({},data,
                {isDisplayItem : true}
                ,
                fnObj.pageView.getPageInfo()
                ),
            async: false,
            callback: function (res) {
                fnObj.iconView.setData(res.list,data.uuid == "");
                fnObj.pageView.setPage(res);
                ACTIONS.dispatch(ACTIONS.GET_NAVI_DATA,data);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    GET_AGGREGATION_INFO : function(caller, act, data)
    {
        axboot.ajax({
            url: "/rc/rc001/getAggregationInfo",
            data: data,
            callback: function (res) {
                //fnObj.detailView.setData(res);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    GET_ITEM_INFO : function(caller, act, data)
    {
        axboot.ajax({
            url: "/rc/rc001/getItemInfo",
            data: data,
            callback: function (res) {
                fnObj.detailView.setData(res);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    GET_NAVI_DATA : function(caller, act, data)
    {
        axboot.ajax({
            url: "/rc/rc001/getNaviData",
            data: data,
            callback: function (res) {
                fnObj.naviView.setList(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    DELETE_AGGREGATION : function(caller, act, list){

        axboot.ajax({
            url: "/rc/rc001/deleteAggregation",
            data: JSON.stringify(list),
            dataType : "JSON",
            type : "POST",
            callback: function (res) {
                if(res.status == -500)
                    axWarningToast.push(axboot.getCommonMessage(res.message));
                else
                {
                    axToast.push(axboot.getCommonMessage(res.message));
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,fnObj.naviView.getRoot());
                    if($(".explorer_grid").css("display")=="none")
                        ACTIONS.dispatch(ACTIONS.GET_SUBDATA,fnObj.naviView.getCurrent());
                    else
                        ACTIONS.dispatch(ACTIONS.GET_GRID_DATA,fnObj.naviView.getCurrent());
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SAVE: function (caller, act, list) {
        console.log(JSON.stringify(list));
        axboot.ajax({
            url: "/rc/rc001/save",
            data: JSON.stringify(list),
            dataType : "JSON",
            type : "POST",
            callback: function (res) {
                axboot.getCommonMessage("AA007");
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

function leftcloseView() {
    $(".left").hide();
    $(".cssBody").css("left", "0%");
    $(".cssBody").css("padding-left", "0");
    $("a.leftmenu_open").show();
    $("a.leftmenu_close").hide();
    $(".left_close_open_btn a").css("left", "0%");
    $(".left_close_open_btn a").css("margin-left", "0");
    $(".header").css("width", "100%");

}

function leftopenView() {
    $(".left").show();
    $("a.leftmenu_open").hide();
    $("a.leftmenu_close").show();
    $(".cssBody").css("left", ""); //오른쪽 부분 원상복귀
    $(".cssBody").css("padding-left", ""); //오른쪽 부분 복귀
    $(".left_close_open_btn a").css("left", "");
    $(".left_close_open_btn a").css("margin-left", "");
    $(".header").css("width", "");
    $(".left").css("top", "80px");

}

function left7openView() {
    $(".left").show();
    $(".left").css("top", "50px");
    $("a.leftmenu7_open").hide();
    $("a.leftmenu_open").hide();
    $("a.leftmenu_close").hide();
    $("a.leftmenu7_close").show();
    $(".cssBody").css("left", ""); //오른쪽 부분 원상복귀
    $(".cssBody").css("padding-left", ""); //오른쪽 부분 복귀
    $(".left_close_open_btn a").css("left", "");
    $(".left_close_open_btn a").css("margin-left", "");
    $(".header").css("width", "");
}

function left7closeView() {
    $(".left").hide();
    $(".cssBody").css("left", "0%");
    $(".cssBody").css("padding-left", "0");
    $("a.leftmenu_open").hide();
    $("a.leftmenu7_open").show();
    $("a.leftmenu_close").hide();
    $("a.leftmenu7_close").hide();
    $(".left_close_open_btn a").css("left", "0%");
    $(".left_close_open_btn a").css("margin-left", "0");
    $(".header").css("width", "100%");
}

function bigView() {
    $(".header").hide();
    $(".left").hide();
    $(".tab1_wrap").hide();
    $(".cssBody").css("left", "0%");
    $(".cssBody").css("top", "50px");
    $(".nav").css("top", "0");
    $(".cssBody").css("padding-left", "0");
    $("a.small_open").show();
    $("a.big_close").hide();
    /*$(".zeta-menu-bar").show();*/
    $("a.leftmenu7_open").show();
    $("a.leftmenu_close").hide();
    $("a.leftmenu_open").hide();
    $(".left_close_open_btn a").css("left", "0%");
    $(".left_close_open_btn a").css("margin-left", "0");
    $("a.leftmenu_open").show();
    $("a.leftmenu_close").hide();
    /*$(".header").css("width","100%");*/
}

function smallView() {
    $(".header").show();
    $(".left").show();
    $(".tab1_wrap").show();
    $(".left").css("top", "80px");
    $(".cssBody").css("left", "");
    $(".cssBody").css("top", "80px");
    $(".nav").css("top", "");
    $(".cssBody").css("padding-left", "");
    $("a.small_open").hide();
    $("a.big_close").show();
    /*$(".zeta-menu-bar").show();*/
    $("a.leftmenu7_open").hide();
    $("a.leftmenu_close").show();
    $("a.leftmenu7_close").hide();
    $("a.leftmenu_open").hide();
    $(".left_close_open_btn a").css("left", "");
    $(".left_close_open_btn a").css("margin-left", "");


    /*$(".header").css("width","");*/
}

function exp_listView() {
    $(".explorer_list").css("display", "");
    $(".explorer_grid").css("display", "none");
    //$(".exp_detail").css("display", "");
    fnObj.pageView.resetPage();
    fnObj.pageView.setPageSize(126);
    ACTIONS.dispatch(ACTIONS.GET_SUBDATA,fnObj.naviView.getCurrent());
}

function exp_gridView() {
    $(".explorer_list").css("display", "none");
    $(".explorer_grid").css("display", "block");
    //$(".exp_detail").css("display", "none");
    fnObj.pageView.resetPage();
    fnObj.pageView.setPageSize(100);
    ACTIONS.dispatch(ACTIONS.GET_GRID_DATA,fnObj.naviView.getCurrent());

}


var fnObj = {
    pageStart : function () {
        /*var _this = this;
        $.ajax({
            url: "/assets/js/column_info/ad00101.js",‰
            dataType: "script",
            async : false,
            success: function(){}
        });
        _this.searchView.initView();
        _this.gridView01.initView();*/

        $.ajax({
            url: "/assets/js/column_info/rc00101.js",
            dataType: "script",
            async : false,
            success: function(){}
        });

        $(function () {
            $(".zeta-menu li").hover(function () {
                $('ul:first', this).show();
            }, function () {
                $('ul:first', this).hide();
            });
            $(".zeta-menu>li:has(ul)>a").each(function () {
                $(this).html($(this).html() + ' &or;');
            });
            $(".zeta-menu ul li:has(ul)")
                .find("a:first")
                .append("<p style='float:right;margin:-3px'>&#9656;</p>");
        });


        $(function () {
            $(".exp-menu li").hover(function () {
                $('ul:first', this).show();
            }, function () {
                $('ul:first', this).hide();
            });
            $(".exp-menu>li:has(ul)>a").each(function () {
                $(this).html($(this).html() + ' &dtrif;');
            });
            $(".exp-menu ul li:has(ul)")
                .find("a:first")
                .append("<p style='float:left;margin:-3px'>&#9656;</p>");
        });

        $(function () {
            $(".tab1 span").click(function () {
                $(".tab1 span").removeClass();
                $(this).addClass("on");
            });
        });

        $(".exp-menu").hover(function(){
            //icon 실행
            if($(".explorer_grid").css("display")=="none")
            {
                selectedData= fnObj.iconView.getSelectedData();
            }
            //grid 실행
            else {
                selectedData = fnObj.gridView01.getSelectedData();
            }

            switch(selectedData.length)
            {
                case 1:
                    $(".exp-menu>li>ul>li>a").removeClass("inactive");
                    break;
                case 0:
                    $(".exp-menu>li>ul>li>a").each(function(){
                        switch($(this).text())
                        {
                            case "Properties":case "Edit":case "Move":case "Delete":
                            $(this).addClass("inactive");
                                break;
                            default :
                                $(this).removeClass("inactive");
                        }
                    });
                    break;
                default :
                    $(".exp-menu>li>ul>li>a").each(function(){
                        switch($(this).text())
                        {
                            case "Aggregation" :case "Item":case "Properties":case "Edit":
                            $(this).addClass("inactive");
                            break;
                            default :
                                $(this).removeClass("inactive");
                        }
                    });
                    break;
            }
        });

        $(".exp-menu a").click(function(event){
            event.stopPropagation();
            event.preventDefault();
            var selectedData = undefined;

            var parentsObj = parent.window.fnObj;
            var menu = "";


            //icon 실행
            if($(".explorer_grid").css("display")=="none")
            {
                selectedData= fnObj.iconView.getSelectedData();
            }
            //grid 실행
            else {
                selectedData = fnObj.gridView01.getSelectedData();
            }

            //var selectedData = rightObj.getSelectedData();

            var getMenu = function(searchData)
            {
                var menuObj = undefined;
                axboot.ajax({
                    url: "/rc/rc001/getMenuInfo",
                    data: JSON.stringify({progNm : searchData}),
                    type : "POST",
                    dataType : "JSON",
                    async : false,
                    callback: function (res) {
                        menuObj = res;
                    },
                    options: {
                        onError: axboot.viewError
                    }
                });
                return menuObj;
            }


            if(selectedData.length > 0 && !selectedData[0]["name"])
                selectedData[0]["name"] = selectedData[0]["title"];

            switch($(this).text())
            {
                //add
                case "Aggregation":
                    if(selectedData.length < 2)
                    {
                        if(selectedData.length == 1)
                            selectedData = selectedData[0];
                        else
                        {
                            selectedData = fnObj.naviView.getCurrent();
                        }

                        var item = getMenu("add aggregation");
                        var naviStr = undefined == selectedData["name"]? "" : " > "+selectedData["name"]
                        item.menuParams = $.extend({},
                            selectedData
                            ,{type: "create"}
                            ,{navi : fnObj.naviView.getPathString()+naviStr},{title : ""}
                            );
                        parentsObj.tabView.open(item);
                    }
                    break;
                case "Item":
                    if(selectedData.length < 2)
                    {
                        if(selectedData.length == 1)
                            selectedData = selectedData[0];
                        else
                        {
                            selectedData = fnObj.naviView.getCurrent();
                        }
                        var item = getMenu("add item");
                        var naviStr = undefined == selectedData["name"]? "" : " > "+selectedData["name"]
                        item.menuParams = $.extend({},{
                            aggregationUuid : selectedData.uuid
                        },{type: "create"},{navi : fnObj.naviView.getPathString()+naviStr},{title : ""}
                        );
                        parentsObj.tabView.open(item);
                    }
                    break;
                //view
                case "Properties":
                    if(selectedData.length == 1)
                    {
                        var item = "";
                        if(selectedData[0].nodeType == "item")
                        {
                            item = getMenu("view item");
                        }
                        else
                        {
                            item = getMenu("view aggregation");
                        }
                        if(item != "")
                        {
                            item.menuParams = $.extend({},selectedData[0],{type: "create"},{navi : fnObj.naviView.getPathString()},{title : selectedData["name"]});
                            parentsObj.tabView.open(item);
                        }
                    }
                    break;
                //do
                case "Edit":
                    if(selectedData.length == 1)
                    {
                        if(selectedData[0].nodeType == "item")
                        {
                            item = getMenu("add item");
                        }
                        else
                        {
                            item = getMenu("add aggregation");
                        }
                        if(item != "")
                        {
                            item.menuParams = $.extend({},{
                                parentUuid : selectedData[0].parentUuid,
                                uuid : selectedData[0].uuid
                            },{type: "update"},{navi : fnObj.naviView.getPathString()},{title : selectedData[0]["name"]});
                            parentsObj.tabView.open(item);
                        }
                        else
                        {
                            item.menuParams = $.extend({},{
                                aggregationUuid : selectedData[0].parentUuid,
                                itemUuid : selectedData[0].uuid
                            },{type: "update"},{navi : fnObj.naviView.getPathString()},{title : selectedData[0]["name"]});
                            parentsObj.tabView.open(item);
                        }
                        console.log(selectedData);
                    }
                    break;
                case "Move":

                    var selectType = "";
                    /*
                    aggregation type을 기준으로 이동할 수 있는 경우의 수가 달라진다.
                    aggregation Type :

                    Aggregation
                    Normal : Normal
                    Tempolary : Temporary
                    Virtual   : Virtual

                    Iteam
                    Normal    : Normal, Tempolary
                    Tempolary : Normal, Tempolary
                    Virtual   : X
                    */
                    var compAggregation = "";
                    var aggregationType = "";
                    var selectType = "";
                    var canMove = true;
                    var errorMsg = "";
                    selectedData.sort(function(a,b){
                        var typeName = undefined == a["nodeType"] ? "nodeType" : "type";

                        return a[typeName]-b[typeName];
                    });

                    for(var i = 0; i < selectedData.length; i++)
                    {
                        aggregationType = undefined === selectedData[i]["nodeType"] ? selectedData[i]["type"] : selectedData[i]["nodeType"];
                        if(i == 0)
                            selectType = compAggregation = aggregationType;

                        if( ( !aggregationType || aggregationType == "") && selectType.toLowerCase().indexOf("virtual") > -1)
                        {
                            canMove = false;
                            errorMsg = "RC001_06";
                            break;
                        }

                        if(aggregationType && "" != aggregationType && compAggregation != aggregationType)
                        {
                            canMove = false;
                            errorMsg = "RC001_02";
                            break;
                        }
                    }

                    if(!canMove)
                    {
                        axWarningToast.push(axboot.getCommonMessage(errorMsg));
                        return ;
                    }
                    axboot.modal.open({
                        modalType: "MOVE_AGGREGATION",
                        param: "",
                        sendData: function () {
                            return {
                                selectType  :  selectType,
                                "selectedList": selectedData
                            };
                        },
                        callback: function (data) {

                            axToast.push(axboot.getCommonMessage("AA007"));
                            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);

                            if($(".explorer_grid").css("display")=="none")
                                ACTIONS.dispatch(ACTIONS.GET_SUBDATA,fnObj.naviView.getCurrent());
                            else
                                ACTIONS.dispatch(ACTIONS.GET_GRID_DATA,fnObj.naviView.getCurrent());


                        }
                    });
                    break;
                case "Update Status":



                    axboot.modal.open({
                        modalType: "UPDATE_STATE_AGGREGATION_N_ITEM",
                        param: "",
                        sendData: function () {
                            return {
                                "selectedList": selectedData
                            };
                        },
                        callback: function (data) {
                            axToast.push(axboot.getCommonMessage("AA007"));
                            if($(".explorer_grid").css("display")=="none")
                                ACTIONS.dispatch(ACTIONS.GET_SUBDATA,fnObj.naviView.getCurrent());
                            else
                                ACTIONS.dispatch(ACTIONS.GET_GRID_DATA,fnObj.naviView.getCurrent());
                        }
                    });


                    break;
                case "Delete":

                    ACTIONS.dispatch(ACTIONS.DELETE_AGGREGATION,selectedData);
                    break;

            }

            console.log($(this).text());

        });

        fnObj.treeView01.initView();
        fnObj.naviView.iintView();
        fnObj.iconView.initView();
        fnObj.gridView01.initView();
        fnObj.pageView.initView();
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        ACTIONS.dispatch(ACTIONS.GET_SUBDATA,{uuid : ""});
    }
};

fnObj.naviView = axboot.viewExtend({
    iintView : function()
    {
        this.initEvent();
    },
    initEvent : function(){
        $("#navigatorArea").delegate(".navigator","click",function(){
            console.log($(this));

            var currentLevel = $(this).attr("level");

            $("#navigatorArea .navigator").each(function(idx){
                if(idx > currentLevel)
                {
                    $(this).remove();
                    $("#navigatorArea .split[splitLevel="+idx+"]").remove();
                }
            });
            if($(".explorer_grid").css("display")=="none")
                ACTIONS.dispatch(ACTIONS.GET_SUBDATA,{uuid:$(this).attr("uuid")});
            else
                ACTIONS.dispatch(ACTIONS.GET_GRID_DATA,{uuid:$(this).attr("uuid")});

        });
    },
    setData : function(data)
    {
        $("#navigatorArea").append($("<span>").attr("splitLevel",$("#navigatorArea span.split").size()+1).addClass("split").text(" > "));
        $("#navigatorArea").append($("<span>").addClass("navigator").attr("level",$("#navigatorArea span.navigator").size()).attr("uuid",data["uuid"]).text(data["name"]));
    },
    setList : function(list)
    {
        fnObj.naviView.clear();
        var data = undefined;
        for(var i = 0; i < list.length; i++)
        {
            data = list[i];
            $("#navigatorArea").append($("<span>").attr("splitLevel",$("#navigatorArea span.split").size()+1).addClass("split").text(" > "));
            $("#navigatorArea").append($("<span>").addClass("navigator").attr("level",$("#navigatorArea span.navigator").size()).attr("nodeType",data["nodeType"]).attr("uuid",data["uuid"]).text(data["name"]));
        }
    },
    getCurrent : function()
    {
        return {
            uuid : $(".navigator:last").attr("uuid"),
            nodeType : $(".navigator:last").attr("nodeType")
        };
    },
    getRoot : function()
    {
        return {uuid : $(".navigator:first").attr("uuid")};
    },
    getPathString : function(){
        var pathStr = "";
        $("#navigatorArea>span").each(function(idx){
            pathStr += $(this).text();
        });
        return pathStr;
    },
    clear : function()
    {
        $("#navigatorArea .navigator").each(function(idx)
        {
            if(idx > 0)
            {
                $(this).remove();
                $("#navigatorArea .split[splitLevel="+idx+"]").remove();
            }
        });
    }

});
fnObj.pageView = axboot.viewExtend({
    page : {
        pageNumber:0,
        pageSize: 126
    },
    initView : function()
    {
        this.initEvent();
    },
    initEvent : function(){
        var _this = this;
        $(".page_no").delegate("a:not(.page_start,.page_prev,.page_next,.page_end)","click",function(){
            _this.page.pageNumber = parseInt($(this).text())-1;
            $(".page_no>a:not(.page_start,.page_prev,.page_next,.page_end)").removeClass("selec");
            $(".page_no>a[pageNo='"+$(this).text()+"']").addClass("selec");


            if($(".explorer_grid").css("display")=="none")
                ACTIONS.dispatch(ACTIONS.GET_SUBDATA,fnObj.naviView.getCurrent());
            else
                ACTIONS.dispatch(ACTIONS.GET_GRID_DATA,fnObj.naviView.getCurrent());

        });
        $(".page_no>a.page_start,.page_no>a.page_prev,.page_no>a.page_next,.page_no>a.page_end").click(function(){
            switch($(this).attr("class"))
            {
                case "page_start":
                    _this.page.pageNumber = 0;
                    break;
                case "page_prev":
                    if(_this.page.pageNumber < 10)
                        _this.page.pageNumber = 0;
                    else
                        _this.page.pageNumber = _this.page.pageNumber - (_this.page.pageNumber % 10)  - 1;
                    break;
                case "page_next":
                    if(_this.page.totalPages < 10)
                        _this.page.pageNumber = _this.page.totalPages - 2;
                    else
                    _this.page.pageNumber = _this.page.pageNumber + 10 - (_this.page.pageNumber % 10);
                    break;
                case "page_end":
                    _this.page.pageNumber = _this.page.totalPages - 1;
                    break;
            }
            ACTIONS.dispatch(ACTIONS.GET_SUBDATA,fnObj.naviView.getCurrent());

        });
    },
    resetPage : function()
    {
        this.page.pageNumber = 0;
    },
    setPage : function(page)
    {

        this.page = page;

        var startPage = parseInt(page.pageNumber/10)*10 + 1;
        var maxPage = startPage+10;
        maxPage = page.totalPages < maxPage ? page.totalPages : maxPage;
        if(maxPage > 10 && maxPage%10 != 1)
            maxPage ++;
        $(".page_no a:not(.page_start,.page_prev,.page_next,.page_end)").remove();
        for(var i = startPage; i < maxPage; i++)
        {
            $(".page_no .page_next").before($("<a>").attr("pageNo",i).attr("href","#").text(i));
        }
        $(".page_no>a").each(function(){
            if((page.pageNumber + 1) == $(this).text())
                $(this).addClass("selec");
            else
                $(this).removeClass("selec");

        });



        /*
        currentPage:0
        pageSize:108
        totalElements:10100
        totalPages:94
        */
    },
    setPageSize : function(_pageSize){
        this.page.pageSize = _pageSize;
    },
    getPageInfo : function(){
        return {
            pageNumber: this.page.pageNumber,
            pageSize: this.page.pageSize
        }
    }
})

/*
fnObj.detailView = axboot.viewExtend({
    initView : function(){

    },
    setData : function(data)
    {
        var descStrDate = "";
        var descEdDate = "";
        var cloneTag = $("#templateAggregationInfoArea").clone();
        for(var key in data)
        {
            if(key == "descStrDate" && key == "descEdDate")
            {
                switch(key){
                    case "descStrDate":
                        descStrDate = data[key];
                        break;
                    case "descEdDate":
                        descEdDate = data[key];
                        break;
                }
                continue;
            }
            cloneTag.find("."+key).after(data[key]);
        }
        if("" != descStrDate || "" != descEdDate) {
            $("#templateAggregationInfoArea").find(".descDate").text(descStrDate + " ~ " + descEdDate);
        }
        $("#aggregationInfoArea").empty().append(cloneTag.children());
    }
});
*/
fnObj.iconView = axboot.viewExtend({
    pressedCtrl : false,
    isdbClk : false,
    initView : function()
    {
        this.initEvent();
    },
    initEvent : function() {
        var _this = this;
        $("#isDisplayItem").change(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,fnObj.naviView.getRoot());
        });

        $("body").keydown(function(event){
            fnObj.iconView.pressedCtrl = event.ctrlKey;
        });
        $("body").keyup(function(event){
            fnObj.iconView.pressedCtrl = event.ctrlKey;
        });
        $("body").click(function(){
            $("#iconListArea .selected").each(function(){
                $(this).removeClass("selected");
            });
        });



        $("#iconListArea").delegate("div","click",function(event){

            event.stopPropagation();
            var index = 0;
            var imgSrc= "";
            var uuid = "";

            if(undefined != $(this).attr("uuid")) {
                uuid = $(this).attr("uuid")
                //imgSrc = $(this).find(".imageTag").find("img").prop("src");
                if(fnObj.iconView.pressedCtrl)
                {
                    if($(this).attr("class").indexOf("selected")>-1)
                        $(this).removeClass("selected")
                    else
                        $(this).addClass("selected")
                }
            }else if(undefined == $(this).parents().eq(index).attr("uuid")){
                index++;
            }
            if("" == uuid)
            {
                uuid = $(this).parents().eq(index).attr("uuid")
                //imgSrc = $(this).parents().eq(index).find(".imageTag").find("img").prop("src")
                //컨트롤 누르고 클릭 시 해당 아이템들이 다중으로 선택되어야된다
                if(fnObj.iconView.pressedCtrl)
                {
                    //$(this).parents().eq(index).addClass("selected")
                    if($(this).parents().eq(index).attr("class").indexOf("selected")>-1)
                    {
                        $(this).parents().eq(index).removeClass("selected")
                    }
                    else
                        $(this).parents().eq(index).addClass("selected")
                }
                else {
                    //컨트롤 누르지 않고 클릭 시 해당 아이템만 선택되어야된다
                    $("#iconListArea .selected").each(function(){
                        $(this).removeClass("selected");
                    });
                    $(this).parents().eq(index).addClass("selected")
                }
            }

            //$("#archiveType").prop("src",imgSrc);

            var reqData = {uuid : uuid};
            //setTimeout(function(){
                //if(fnObj.iconView.isdbClk) {  return ;}
                //if(imgSrc.indexOf("file")>-1)
                //    ACTIONS.dispatch(ACTIONS.GET_ITEM_INFO,reqData);
                //else
                    //ACTIONS.dispatch(ACTIONS.GET_AGGREGATION_INFO,reqData);

            //},250);

        });
        $("#iconListArea").delegate("div","dblclick",function(event){
            event.stopPropagation();
            fnObj.iconView.isdbClk = true;
            var index = 0;
            var uuid ="";
            if(undefined != $(this).attr("uuid")) {
                imgSrc = $(this).find(".imageTag").find("img").prop("src");
                uuid = $(this).attr("uuid");
            }else if(undefined == $(this).parents().eq(index).attr("uuid")){
                index++;
            }
            if(undefined == $(this).parents().eq(index).attr("uuid"))
                index++;

            if(uuid == "")
                imgSrc = $(this).parents().eq(index).find(".imageTag").find("img").prop("src")

            if(imgSrc.indexOf("file")>-1)
                return ;

            fnObj.naviView.setData({uuid : $(this).parents().eq(index).attr("uuid"),name : $(this).parents().eq(index).find(".titleTag").children().eq(0).text()});
            ACTIONS.dispatch(ACTIONS.GET_SUBDATA,{
                    uuid:$(this).parents().eq(index).attr("uuid"),
                    nodeType : $(this).parents().eq(index).attr("nodeType")}
                );
            //fnObj.detailView.setData({});
            //setTimeout(function(){
                fnObj.iconView.isdbClk = false;
            //},300);

        });
    },
    setData : function(list, isFirst)
    {
        var targetTag = $("#iconListArea");
        var template = $("#template>div");
        var cloneTag = undefined;
        var data = undefined;
        var imgTag = undefined;
        var imgPath = "/assets/images/ams/";
        targetTag.empty();
        var fullStr = "";
        this.list = list;
        if(list.length < 1)
            return ;

        var chooseList = new Array();
        if(isFirst)
        {
            for(var i = 0; i < list.length ;i++)
            {
                if(list[i]["parentUuid"] == undefined)
                {
                    chooseList.push(list[i]);
                }
            }
            list = chooseList;
        }

        for(var i = 0; i < list.length; i++)
        {
            data = list[i];
            cloneTag = template.clone();
            imgTag = $("<img>");
            cloneTag.attr("uuid",data["uuid"]);
            cloneTag.attr("nodeType",data["nodeType"]);
            /*
            Aggregation
            class : explorer_folder_empty or explorer_folder_full
            imagePath : /assets/images/ams/
            imageName :  explorer_folder, explorer_folder_t, explorer_folder_v,

            Item
            class : explorer_file, explorer_img
            imagePath : /assets/images/ams/
            imageName : explorer_file,explorer_file_v, explorer_file_img, explorer_file_img_v

            Text
            class : explorer_text, explorer_4line
            */
            if(data["nodeType"] == "item")
            {
                cloneTag.addClass("explorer_file");
            }else
                {
                if(data["childCnt"] > 0)
                {
                    cloneTag.addClass("explorer_folder_full");
                    fullStr = "_f";
                }
                else
                {
                    cloneTag.addClass("explorer_folder_empty");
                    fullStr = "";
                }

            }


            switch(data["nodeType"])
            {
                case "normal":
                    imgTag.prop("src",imgPath+"explorer_folder"+fullStr+".png").prop("alt","folder");
                    break;
                case "temporarily":
                    imgTag.prop("src",imgPath+"explorer_folder"+fullStr+"_t.png").prop("alt","folder");
                    break;
                case "virtual":
                    imgTag.prop("src",imgPath+"explorer_folder"+fullStr+"_v.png").prop("alt","folder");
                    break;
                case "item":
                    imgTag.prop("src",imgPath+"explorer_file.png").prop("alt","item");
                    break;
            }
            cloneTag.find(".imageTag").append(imgTag);

            cloneTag.find(".titleTag").append($("<div>").attr("class", data["name"].length > 15 ? "explorer_4line" : "explorer_text").text(data["name"]));
            cloneTag.attr("level",data["level"]);
            targetTag.append(cloneTag);
            fullStr = "";
            cloneTag = undefined;
            imgTag = undefined;
            data = undefined;


        }

    },
    getSelectedData : function()
    {
        var selectedData = new Array();
        var retData = new Array();
        $(".explorer_list .selected").each(function(item,index){
            selectedData.push($(this).attr("uuid"));
        });
        for(var i = 0; i < this.list.length; i++)
        {
            for(var j = 0; j < selectedData.length; j++)
            {
                if(this.list[i].uuid == selectedData[j])
                {
                    retData.push(this.list[i]);
                }
            }

        }

        return retData;
    }

});

/**
 * treeView
 */
fnObj.treeView01 = axboot.viewExtend(axboot.commonView, {
    param: {},
    deletedList: [],
    newCount: 0,
    otherParam : {},
    addRootNode: function () {
        /*
        var _this = this;
        var nodes = _this.target.zTree.getSelectedNodes();
        var treeNode = nodes[0];

        // root
        treeNode = _this.target.zTree.addNodes(null, {
            id: "_isnew_" + (++_this.newCount),
            pId: 0,
            name: "새 메뉴",
            __created__: true,
            menuGrpCd: _this.param.menuGrpCd
        });

        if (treeNode) {
            _this.target.zTree.editName(treeNode[0]);
        }
        fnObj.treeView01.deselectNode();
        */
    },
    initView: function () {
        var _this = this;
        this.reloadFlag = false;
        this.checkFlag = false;
        $('[data-tree-view-01-btn]').click(function () {
            var _act = this.getAttribute("data-tree-view-01-btn");
            switch (_act) {
                case "add":
                    ACTIONS.dispatch(ACTIONS.TREE_ROOTNODE_ADD);
                    break;
                case "delete":
                    //ACTIONS.dispatch(ACTIONS.ITEM_DEL);
                    break;
            }
        });
        var _this = this;
        this.target = axboot.treeBuilder($('[data-z-tree="tree-view-01"]'), {
            view: {
                dblClickExpand: false,
                addHoverDom: function (treeId, treeNode) {
                },
                removeHoverDom: function (treeId, treeNode) {
                }
            },
            /*
            async: {
                enable: true,
                url: "/rc/rc001/getAllNodes",
                autoParam: ["uuid","parentUuid"] ,
                otherParam : _this.otherParam
            },
            */
            edit: {
                enable: true,
                editNameSelectAll: false,
                showRemoveBtn : false,
                showRenameBtn : false
            },
            data : {
                simpleData: {
                    enable: true,
                    idKey: "uuid",
                    pIdKey: "parentUuid",
                    rootPId: 0
                }
            },
            callback: {
                beforeDrag: function () {
                    //return false;
                },
                onClick: function (e, treeId, treeNode, isCancel) {
                    var reqData = $.extend({},treeNode);
                    var path = treeNode.getPath();
                    for(var key in reqData)
                    {
                        if( !(key == "uuid" || key == "nodeType") )
                            delete(reqData[key]);
                    }

                    //tree는 자기 어머니까지 다해서 한방에 셋해야된다.
                    fnObj.naviView.clear();
                    for(var i = 0; i < path.length; i++)
                    {
                        fnObj.naviView.setData(path[i]);
                    }
                    fnObj.pageView.resetPage();
                    if($(".explorer_grid").css("display")=="none")
                        ACTIONS.dispatch(ACTIONS.GET_SUBDATA,reqData);
                    else
                        ACTIONS.dispatch(ACTIONS.GET_GRID_DATA,reqData);
                    //ACTIONS.dispatch(ACTIONS.GET_SUBDATA, reqData);
                },
                onRename: function (e, treeId, treeNode, isCancel) {
                    treeNode.__modified__ = true;
                },
                onRemove: function (e, treeId, treeNode, isCancel) {
                    if (!treeNode.__created__) {
                        treeNode.__deleted__ = true;
                        _this.deletedList.push(treeNode);
                    }
                    fnObj.treeView01.deselectNode();
                },
                beforeClick: function(treeId, treeNode) {
                    /*
                    zTree = $.fn.zTree.getZTreeObj(treeId);
                    if (treeNode.isParent) {
                        zTree.expandNode(treeNode);//If it is the parent node,Expand the node
                    }else{
                        zTree.checkNode(treeNode, !treeNode.checked, true, true);//Click Check,Click Cancel again
                    }
                    */
                },
                beforeExpand : function(treeId, treeNode) {
                    /*
                    _this.otherParam = treeNode;
                    _this.reloadFlag = true;
                    _this.checkFlag = false;
                    return true;
                    */
                },
                beforeCheck : function (treeId, treeNode) {
                    var zTree = $.fn.zTree.getZTreeObj(treeId);
                    if (!treeNode.children) {
                        _this.reloadFlag = true;
                        _this.checkFlag = true;
                        zTree.reAsyncChildNodes(treeNode, "refresh");
                    }
                    return true;
                },
                onNodeCreated : function(event, treeId, treeNode) {
                    var zTree = $.fn.zTree.getZTreeObj(treeId);
                    if (_this.reloadFlag) {
                        if (_this.checkFlag) {
                            zTree.checkNode(treeNode, true, true);
                        }
                        if (!treeNode.children) {
                            zTree.reAsyncChildNodes(treeNode, "refresh");
                        }
                    }
                },
                beforeDrop : function(treeId, treeNodes, targetNode, moveType, isCopy){


                    console.log(targetNode);
                    console.log(treeNodes);
                    var msgCode = "";
                    var result = true;
                    if(targetNode.nodeType == "item")
                    {
                        result = false;
                        msgCode = "RC001_01";
                    }
                    else
                    {
                        for(var i = 0; i < treeNodes.length; i++)
                        {

                            if(treeNodes[i].nodeType != "item" && targetNode.nodeType != treeNodes[i].nodeType)
                            {
                                result = false;
                                msgCode = "RC001_02";
                                break;
                            }
                            else if(treeNodes[i].nodeType == "item" && targetNode == null )
                            {
                                result = false;
                                msgCode = "RC001_03";
                                break;
                            }
                        }
                    }
                    if(!result)
                        axWarningToast.push(axboot.getCommonMessage(msgCode));


                    return result;

                },
                onDrop : function(event, treeId, treeNodes, targetNode, moveType, isCopy){


                    //

                    console.log(event);
                    console.log(treeId);
                    console.log(targetNode);
                    console.log(treeNodes);


                    var reqList = new Array();
                    var validate = true;
                    //validate 로직 추가 ( item은 최상위 불가, Aggregation의 타입에 따라서 상위 이동 불가 )
                    for(var i = 0; i < treeNodes.length; i++)
                    {
                        if(treeNodes[i].nodeType == "item" && null == targetNode)
                        {
                            validate = false;
                            break;
                        }
                        if(null == targetNode)
                            treeNodes[i].parentUuid = null;
                        else
                            treeNodes[i].parentUuid = targetNode.uuid;
                        reqList.push({
                            uuid :treeNodes[i].uuid,
                            parentUuid :treeNodes[i].parentUuid,
                            nodeType :treeNodes[i].nodeType

                        })
                    }

                    if(!validate)
                        return ;

                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE,reqList);
                    //오른쪽 데이터 refresh 로직 추가

                    console.log(treeNodes);
                    console.log(isCopy);

                    //요청
                    var reqData = $.extend({},targetNode);
                    var path = targetNode.getPath();
                    for(var key in reqData)
                    {
                        if(key != "uuid")
                            delete(reqData[key]);
                    }

                    //tree는 자기 어머니까지 다해서 한방에 셋해야된다.
                    fnObj.naviView.clear();
                    for(var i = 0; i < path.length; i++)
                    {
                        fnObj.naviView.setData(path[i]);
                    }

                    if($(".explorer_grid").css("display")=="none")
                        ACTIONS.dispatch(ACTIONS.GET_SUBDATA,reqData);
                    else
                        ACTIONS.dispatch(ACTIONS.GET_GRID_DATA,reqData);


                },
                //beforeAsync: function(treeId, treeNode){},

                // /onAsyncError: function(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown){alert("보내기고 에러");},
                onAsyncSuccess: function (event, treeId, treeNode, msg) {
                    msg.list;
                    console.log(msg);
                    msg = JSON.parse(msg);
                    var _tree = msg.list;
                    var matchingData = function(key, list)
                    {
                        var retList = new Array();
                        for(var i = 0; i < list.length; i++)
                        {
                            if( key == list[i]["parentUuid"] )
                            {
                                list[i].children =  matchingData(list[i]["uuid"], list);
                                retList.push(list[i]);
                            }
                        }
                        return retList;

                    }

                    var treeData = undefined;
                    var treeList = new Array();
                    for(var i = 0; i < _tree.length; i++)
                    {
                        treeData = _tree[i];
                        if(treeData["parentUuid"] == null)
                        {
                            treeData.children = matchingData(treeData["uuid"],_tree);
                            treeList.push(treeData);
                        }
                    }

                    _this.target.setData(_this.convertTreeData(treeList));
                    /*
                    if(_this.reloadFlag ){
                        if(!_this.checkFlag)
                        {
                            _this.target.zTree.addNodes(treeNode,_this.convertTreeData(msg.list));
                            _this.target.zTree.updateNode(treeNode);
                            _this.checkFlag = true;
                            //_this.target.zTree.reAsyncChildNodes(treeNode,"refresh");
                        }
                        else {
                        }

                    }
                    else
                        _this.target.setData(_this.convertTreeData(msg.list));
                     */
                }
            }
        }, []);
    },
    convertTreeData : function(_tree)
    {
        var iconObj = undefined;
        for(var i = 0; i < _tree.length; i++)
        {
            switch(_tree[i]["nodeType"])
            {
                case "normal":
                    iconObj =
                        {
                            open:false, icon:"/assets/images/ams/icon/fo.png", iconOpen:"/assets/images/ams/icon/fo_op.png", iconClose:"/assets/images/ams/icon/fo.png"
                        };
                    break;
                case "virtual":
                    iconObj =
                        {
                            open:false, icon:"/assets/images/ams/icon/fo_v.png", iconOpen:"/assets/images/ams/icon/fo_op_v.png", iconClose:"/assets/images/ams/icon/fo_v.png"
                        };
                    break;
                case "temporarily":
                    iconObj =
                        {
                            open:false, icon:"/assets/images/ams/icon/fo_t.png",iconOpen:"/assets/images/ams/icon/fo_op_t.png", iconClose:"/assets/images/ams/icon/fo_t.png"
                        };
                    break;
                case "item":
                    iconObj =
                        {
                            open:false, icon:"/assets/images/ams/icon/fi.png"
                        };
                    break;
            }
            _tree[i] = $.extend({},_tree[i],iconObj);
            iconObj = {};
        }
        return _tree;
    },
    setData: function (_searchData, _tree, _data) {
        this.param = $.extend({}, _searchData);


        var treeList = new Array();
        var data = undefined;


        var matchingData = function(key, list)
        {
            var retList = new Array();
            for(var i = 0; i < list.length; i++)
            {
                if( key == list[i]["parentUuid"] )
                {
                    list[i].children =  matchingData(list[i]["uuid"], list);
                    retList.push(list[i]);
                }
            }
            return retList;

        }

        var treeData = undefined;
        _tree = this.convertTreeData(_tree);
        for(var i = 0; i < _tree.length; i++)
        {
            treeData = _tree[i];
            if(treeData["parentUuid"] == null)
            {
                treeData.children = matchingData(treeData["uuid"],_tree);
                treeList.push(treeData);
            }
        }

        this.target.setData(treeList);
        /*if (_data && typeof _data.uuid !== "undefined") {
            // selectNode
            (function (_tree, _keyName, _key) {
                var nodes = _tree.getNodes();
                var findNode = function (_arr) {
                    var i = _arr.length;
                    while (i--) {
                        if (_arr[i][_keyName] == _key) {
                            _tree.selectNode(_arr[i]);
                        }
                        if (_arr[i].children && _arr[i].children.length > 0) {
                            findNode(_arr[i].children);
                        }
                    }
                };
                findNode(nodes);
            })(this.target.zTree, "uuid", _data.uuid);
        }*/
    },
    updateNode : function(treeNode)
    {
        this.zTree.updateNode(treeNode);
    },
    getData: function () {
        var _this = this;
        var tree = this.target.getData();

        var convertList = function (_tree) {
            var _newTree = [];
            _tree.forEach(function (n, nidx) {
                var item = {};
                if (n.__created__ || n.__modified__) {
                    item = {
                        __created__: n.__created__,
                        __modified__: n.__modified__,
                        menuId: n.menuId,
                        menuGrpCd: _this.param.menuGrpCd,
                        menuNm: n.name,
                        parentId: n.parentId,
                        sort: nidx,
                        progCd: n.progCd,
                        level: n.level
                    };
                } else {
                    item = {
                        menuId: n.menuId,
                        menuGrpCd: n.menuGrpCd,
                        menuNm: n.name,
                        parentId: n.parentId,
                        sort: nidx,
                        progCd: n.progCd,
                        level: n.level
                    };
                }
                if (n.children && n.children.length) {
                    item.children = convertList(n.children);
                }
                _newTree.push(item);
            });
            return _newTree;
        };
        var newTree = convertList(tree);
        return newTree;
    },
    getDeletedList: function () {
        return this.deletedList;
    },
    clearDeletedList: function () {
        this.deletedList = [];
        return true;
    },
    updateNode: function (data) {
        var treeNodes = this.target.getSelectedNodes();
        if (treeNodes[0]) {
            treeNodes[0].progCd = data.progCd;
        }
    },
    deselectNode: function () {
        ACTIONS.dispatch(ACTIONS.TREEITEM_DESELECTE);
    },
    getSelectedData : function()
    {
        var treeObj = $.fn.zTree.getZTreeObj("ztree");
        return treeObj.getSelectedNodes();
    }
});

fnObj.gridView01 = axboot.viewExtend(axboot.gridView,{
    tagId : "realgrid01",
    uuidFieldName : "uuid",
    entityName : "Record Explorer",
    initView  : function()
    {
        var _this = this;
        this.initInstance();
        this.gridObj.setFixedOptions({
            colCount: 3
        });
        this.setColumnInfo(rc00101.column_info);

        this.gridObj.onDataCellDblClicked(this.itemDbClick);

        this.makeGrid();
        this.gridObj.addRowAfterEvent(this.addRowAfterEvent);
        this.gridObj.itemClick(this.itemClick);


    },
    itemDbClick : function(grid,index)
    {
        var reqData = fnObj.gridView01.gridObj.getSelectedData();
        fnObj.naviView.setData({uuid : reqData["uuid"],name : reqData["title"],nodeType : reqData["type"]});
        if(reqData["iconType"] == "file")
        {
            //차후에 뷰어 붙여야된다
            return;
        }

        reqData["nodeType"] = reqData["type"].toLowerCase();
        for(var key in reqData)
        {
            if( !("uuid" == key ||"nodeType" == key) )
                delete(reqData[key]);
        }
        ACTIONS.dispatch(ACTIONS.GET_GRID_DATA,reqData);
    },
    itemClick : function(data){

    },
    setData : function(list)
    {
        /*{criteria : "value='file'",styles : "iconIndex=0"}
    ,{criteria : "value='file_v'",styles : "iconIndex=1"}
    ,{criteria : "value='folder'",styles : "iconIndex=2"}
    ,{criteria : "value='folder_t'",styles : "iconIndex=3"}
    ,{criteria : "value='folder_v'",styles : "iconIndex=4"}
    ,{criteria : "value='image'",styles : "iconIndex=5"}
    ,{criteria : "value='image_v'",styles : "iconIndex=6"}
    ,{criteria : "value='folder_open'",styles : "iconIndex=7"}
    ,{criteria : "value='folder_open_t'",styles : "iconIndex=8"}*/
        var data = undefined;
        var iconType = "";
        var isOpen = false;
        for(var i = 0; i < list.length ;i++)
        {
            data = list[i];
            if(data["childCnt"] > 0)
            {
                isOpen = true;
            }
            switch(data["type"])
            {
                case "Normal":
                    iconType = "folder"+(isOpen ? "_open" : "")
                    break;
                case "Temporarily":
                    iconType = "folder"+(isOpen ? "_open" : "")+"_t";
                case "Virtual":
                    iconType = "folder"+(isOpen ? "_open" : "")+"_v";
                    break;
                case "item":
                    iconType = "file";
                    break;
                default:
                    iconType = "file";
                    break;
            }
            isOpen = false;
            data["iconType"] = iconType;

        }

        this.gridObj.setData("set", list);
    },
    getSelectedData : function()
    {
        return this.gridObj.getSelectionData();
    }
});

