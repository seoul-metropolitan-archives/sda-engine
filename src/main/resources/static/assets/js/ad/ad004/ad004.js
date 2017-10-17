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
        var _this = this;

        if(!data)
            data = this.formView.getData();

        axboot.ajax({
            type: "POST",
            url: "/ad/ad004/ad004/searchPopupHeader",
            data : JSON.stringify(data),
            async : false,
            callback: function (list) {
                if (undefined === list || list.length < 1) {
                    fnObj.gridView_h.addRow();
                }
                else {
                    fnObj.gridView_h.setData(list);
                }
            }
        });
    },
    PAGE_SAVE : function(caller, act, data)
    {
        axDialog.confirm({
                msg: "저장하시겠습니까?"
            },function() {
            if (
                this.key == "ok"
                && ACTIONS.dispatch(ACTIONS.POPUP_HEADER_PAGE_SAVE)
                && ACTIONS.dispatch(ACTIONS.POPUP_SQL_PAGE_SAVE)
                && ACTIONS.dispatch(ACTIONS.GET_POPUP_DETAIL)
            )
                alert("저장되었습니다");
        });
        //ACTIONS.dispatch(ACTIONS.POPUP_HEADER_PAGE_SAVE);
    },
    POPUP_HEADER_PAGE_SAVE: function (caller, act, data) {
        var _this = this;

        var result = false;
        console.log(fnObj.gridView_h.getData());
        axboot.ajax({
            url: "/ad/ad004/ad004/insertPopupHeader",
            type: "post",
            async: false,
            data: JSON.stringify(fnObj.gridView_h.getData()),
            callback: function (res) {
                res = result;
            }
        });
        return result;
    },
    POPUP_SQL_PAGE_SAVE : function()
    {
        var result = false;
        axboot.ajax({
            url : "/ad/ad004/ad004/insertPopupSQL",
            type : "post",
            async : false,
            data : JSON.stringify(fnObj.sqlView.getData()),
            callback : function(res)
            {
                result = res;
            }
        });
        return result;
    },
    POPUP_DETAIL_PAGE_SAVE : function(data)
    {
        var result = false;
        axboot.ajax({
            url : "/ad/ad004/ad004/insertPopupDetail",
            type : "post",
            async : false,
            data : JSON.stringify(fnObj.gridView_d.getData()),
            callback : function(res)
            {
                result = res;
            }
        });
        return result;
    },
    GET_POPUP_DETAIL : function(data)
    {
        fnObj.sqlView.setData(fnObj.gridView_h.getSQL());
        axboot.ajax({
            url : "/ad/ad004/ad004/getPopupDetail"
            ,type : "POST"
            ,dataType : "JSON"
            ,data : JSON.stringify({popupHeaderUUID : fnObj.gridView_h.getPopupHeaderUUID()})
            ,callback : function(res)
            {

            }
        });
    },
    FORM_CLEAR: function (caller, act, data) {
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
            url: "/assets/js/ad/ad004/ad004_controller.js",
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

        axboot.ajax({
            type: "POST",
            url: "/ad/ad003/getCode",
            data : JSON.stringify({categoryCode : "CD003"}),
            async : false,
            callback: function (list) {
                var data = undefined;
                console.log(JSON.stringify(list));
                for(var i = 0;i < list.length; i++) {
                    /*column info 정보 갱신*/
                    data = list[i];
                    ad004_d.column_info[5].values.push(data.codeDetailUUID);
                    ad004_d.column_info[5].labels.push(data.codeName);
                }
            }

        });
        axboot.ajax({
            type: "POST",
            url: "/ad/ad003/getCode",
            data : JSON.stringify({categoryCode : "CD004"}),
            async : false,
            callback: function (list) {
                var data = undefined;
                console.log(JSON.stringify(list));
                for(var i = 0;i < list.length; i++) {
                    /*column info 정보 갱신*/
                    data = list[i];
                    ad004_d.column_info[6].values.push(data.codeDetailUUID);
                    ad004_d.column_info[6].labels.push(data.codeName);
                }
            }

        });
        axboot.ajax({
            type: "POST",
            url: "/ad/ad003/getCode",
            data : JSON.stringify({categoryCode : "CD005"}),
            async : false,
            callback: function (list) {
                var data = undefined;
                console.log(JSON.stringify(list));
                for(var i = 0;i < list.length; i++) {
                    /*column info 정보 갱신*/
                    data = list[i];
                    ad004_d.column_info[8].values.push(data.codeDetailUUID);
                    ad004_d.column_info[8].labels.push(data.codeName);
                }
            }

        });

        /**/
        _this.formView.initView();
        _this.gridView_h.initView();
        _this.gridView_d.initView();
        _this.sqlView.initView();

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,this.formView.getData());

    }
};
/*검색 창*/
fnObj.formView = axboot.viewExtend(axboot.baseView,{
    initView : function(){
        /*공통 코드 가져오기*/
        axboot.ajax({
            type: "POST",
            url: "/ad/ad003/getCode",
            data : JSON.stringify({categoryCode : "CD006"}),
            async : false,
            callback: function (list) {
                console.log(JSON.stringify(list));

                if(undefined === list)
                    return ;

                var data = undefined;
                for(var i = 0;i < list.length; i++)
                {
                    data = list[i];
                    /*column info 정보 갱신*/
                    ad004_h.column_info[3].values.push(data.codeDetailUUID);
                    ad004_h.column_info[3].labels.push(data.codeName);

                    /*콤보박스 생성*/
                    $("#serviceList").append($("<option>").val(data.codeDetailUUID).text(data.codeName));

                    serviceList[data.service_uuid] =
                        {
                            "label" : data.codeName,
                            "value": data.codeDetailUUID
                        }
                }
            },
            options: {
                onError: function(a,b,c)
                {
                    console.log(a);
                    console.log(b);
                    console.log(c);

                }
            }
        });
    }
    ,getData : function()
    {
        return {
            popupCode       : $("#popupCode").val()
            , popupName     : $("#popupName").val()
            , serviceUUID   : $("#serviceList option:selected").val()
            , useYN         : $("input[name='useYN']:checked").val()
        }
    }
});

/*검색 창*/
fnObj.sqlView = axboot.viewExtend(axboot.baseView,{
    initView : function()
    {
        var _this = this;
        this.targetTag = $("#sql");
        this.targetTag.blur(function(){
            _this.convertSQL();
        });
    }
    ,setData : function(data)
    {
        this.targetTag.text(data);
    }
    ,getData : function()
    {
        console.log(fnObj.gridView_h.getPopupHeaderUUID());
        return {
            popupHeaderUUID : fnObj.gridView_h.getPopupHeaderUUID()
            ,popupSQL : this.targetTag.val()
        };
    }
    ,convertSQL : function()
    {
        var regExp = /(SELECT?)((\s?,?([a-zA-Z0-9]*)_?,?)*)(FROM?)((\s?,?([a-zA-Z0-9]*)_?,?)*)/gi;
        if(regExp.test(this.targetTag.val().toUpperCase()))
        {
            var columns = this.targetTag.val().toUpperCase().replace(regExp,"$2").replace(/\s/gi,"").split(",");
            console.log(this.targetTag.val().toUpperCase().replace(regExp,"$2"));
            console.log(columns);
            fnObj.gridView_d.dynamicSetSqlColumns(columns);
            fnObj.gridView_h.setPopupSQL(this.targetTag.val());
        }
    }
});

/*팝업 헤더*/
fnObj.gridView_h = axboot.viewExtend(axboot.realGridView, {
    tagId : "realgrid",
    entityName : "POPUP_HEADER",
    itemClick : ACTIONS.GET_POPUP_DETAIL,
    initView  : function()
    {
        this.setColumnInfo(ad004_h.column_info);
        this.gridObj.setOption({
            checkBar : {visible : true}
        })
        this.makeGrid();
    },
    getPopupHeaderUUID : function()
    {
        return this.gridObj.getSelectedData()["popupHeaderUUID"];
    },
    setPopupSQL : function(sql)
    {
        this.gridObj.setValues(this.gridObj.getCurrent().dataRow,this.gridObj.getFieldIndex("popupSQL"), sql);
    },
    getSQL : function()
    {
        return this.gridObj.getSelectedData()["popupSQL"];
    }
 });
/*팝업 디테일 ( Column )*/
fnObj.gridView_d = axboot.viewExtend(axboot.realGridView, {
    tagId : "realgrid2",
    entityName : "POPUP_DETAIL",
    initView: function ()
    {
        this.setColumnInfo(ad004_d.column_info);
        this.makeGrid();
    },
    dynamicSetSqlColumns : function(list)
    {
        console.log(fnObj.gridView_h.getPopupHeaderUUID());
        var popupDetailTemplate = {
            popupDetailUUID : ""
            ,popupHeaderUUID: fnObj.gridView_h.getPopupHeaderUUID()
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
        var detailList = new Array();
        for(var i = 0; i < list.length; i++)
        {
            detailList.push($.extend({},popupDetailTemplate,{sqlColumn : list[i]}))
            //this.gridObj.addRow($.extend({},popupDetailTemplate,{sqlColumn : list[i]}));
        }
        this.setData(detailList);

    }

});