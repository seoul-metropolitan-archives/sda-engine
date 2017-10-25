var fnObj = {};
var gridView = undefined;


var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        var _this = this;

        if(!data)
            data = fnObj.formView.getData();

        axboot.ajax({
            type: "POST",
            url: "/ad/ad004/ad004/searchPopupHeader",
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
                ACTIONS.dispatch(ACTIONS.POPUP_HEADER_PAGE_SAVE)
                && ACTIONS.dispatch(ACTIONS.POPUP_DETAIL_PAGE_SAVE)
            )
                axToast.push(axboot.getCommonMessage("AA007"));
    },
    POPUP_HEADER_PAGE_SAVE: function (caller, act, data) {
        var _this = this;

        var result = false;
        console.log(fnObj.gridView_h.getData());
        axboot.ajax({
            url: "/ad/ad004/ad004/savePopupHeader",
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
                        fnObj.gridView_h.gridObj.commit();
                    }catch(e)
                    {
                        console.log("커밋 에러남");
                    }

                }

            }
        });
        return result;
    },
    POPUP_DETAIL_PAGE_SAVE : function(data)
    {
        var result = false;
        axboot.ajax({
            url : "/ad/ad004/ad004/savePopupDetail",
            type : "post",
            async : false,
            data : JSON.stringify(fnObj.gridView_d.getData()),
            callback : function(res)
            {
                result = res;
                if(result)
                {
                    try
                    {
                        fnObj.gridView_d.gridObj.commit();
                    }catch(e)
                    {
                        console.log("커밋 에러남");
                    }
                }
            }
        });
        return result;
    },
    GET_POPUP_DETAIL : function(data)
    {
        fnObj.sqlView.setData(fnObj.gridView_h.getSQL());
        console.log(fnObj.gridView_h.getPopupHeaderUUID());
        axboot.ajax({
            url : "/ad/ad004/ad004/getPopupDetail"
            ,type : "POST"
            ,dataType : "JSON"
            ,data : JSON.stringify({popupHeaderUUID : fnObj.gridView_h.getPopupHeaderUUID()})
            ,callback : function(res)
            {
                fnObj.gridView_d.setData(res.list);
            }
        });
    },
    FORM_CLEAR: function (caller, act, data) {
    },
    CLOSE_TAB : function()
    {
        alert("닫힌다! 살려줘!");
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
            url: "/assets/js/column_info/ad00401.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/column_info/ad00402.js",
            dataType: "script",
            async : false,
            success: function(){}
        });
        $.ajax({
            url: "/assets/js/libs/jquery-ui/jquery-ui.js",
            dataType: "script",
            async : false,
            success: function(){}
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
        var _this = this;
        $("#check").click(function() {
            axboot.modal.open({
                modalType: "COMMON_POPUP",
                param: "",
                sendData: function () {
                    return {
                    };
                },
                callback: function (data) {
                    //$("#calleeEmpName").val(data.empName);
                    //$("#calleeEmpTelno").val(data.empPhoneNo);

                    this.close();
                }
            });
            /*
            var modal = new ax5.ui.modal();
            modal.css('<link rel="stylesheet"    type="text/css"     th:href="@{${@environment.getProperty(\'config.extendedCss\')}}"/>');
            modal.open({
                width:800,
                height: 600,
                closeToEsc: true,
                iframeLoadingMsg: '<i class="fa fa-spinner fa-5x fa-spin" aria-hidden="true"></i>',
                iframe: {
                    method: "get",
                    url: "/ad/ad004/ad00401",
                    param: "callback=modalCallback&popupCode=AU001&data='test'"
                },
                header: {
                    title: "MODAL TITLE",
                    btns: {
                        minimize: {
                            label: '<i class="fa fa-minus-circle" aria-hidden="false"></i>', onClick: function () {
                                modal.minimize();
                            }
                        },
                        maximize: {
                            label: '<i class="fa fa-plus-circle" aria-hidden="true"></i>', onClick: function () {
                                modal.maximize();
                            }
                        },
                        close: {
                            label: '<i class="fa fa-times-circle" aria-hidden="true"></i>', onClick: function () {
                                modal.close();
                            }
                        }
                    }
                }
            }, function () {
                console.log(this);
                //this.$["body"].append($("#Popup3").html());
                this.$["body"].find(".btn_popup_ok").click(function(){
                });
                this.$["body"].find(".btn_popup_close").click(function(){
                    modal.close();
                });
            });
            */
            //$("#Popup3").dialog("open");
            //$(".allpop").css("display", "block");

            event.preventDefault();

        });

    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        console.log($.extend({}, data));
        return $.extend({}, data);
    },
    /*
    getData : function()
    {
        return {
            popupCode       : $("#popupCode").val()
            , popupName     : $("#popupName").val()
            , serviceUUID   : $("#serviceList option:selected").val()
            , useYN         : $("input[name='useYN']:checked").val()
        }
    },*/
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

/*검색 창*/
fnObj.sqlView = axboot.viewExtend(axboot.baseView,{
    initView : function()
    {
        var _this = this;
        this.targetTag = $("#sql");
        $("#sql").keydown(function(){
            fnObj.gridView_h.setPopupSQL(_this.targetTag.val());
        })
        $("#apply").click(function(){
            _this.convertSQL();
        });
    }
    ,setData : function(data)
    {
        if(!data)
            data = "";

        this.targetTag.val(data);
    }
    ,getData : function()
    {
        console.log(fnObj.gridView_h.getPopupHeaderUUID());
        return {
            popupHeaderUUID : fnObj.gridView_h.getPopupHeaderUUID()
            ,popupSQL : this.targetTag.val()
        };
    }
    ,clear : function()
    {
        this.targetTag.val("");
    }
    ,convertSQL : function()
    {
        var regExp = /(SELECT)((\s?,?([a-zA-Z0-9]*)_?,?)*)(FROM)?/gi;
        var data = this.targetTag.val().toUpperCase();
        var splitData = data.split("FROM");
        if(regExp.test(splitData[0]))
        {
            var columns = splitData[0].replace(regExp,"$2").split(",");
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
    beforeRowIdx : undefined,
    initView  : function()
    {
        var _this = this;
        this.setColumnInfo(ad00401.column_info);
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
                ACTIONS.dispatch(ACTIONS.GET_POPUP_DETAIL,data);
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
    addDetailList : function(uuid, groupName,data, isReplace)
    {
        if(isReplace || !this.tempList[uuid])
        {
            if(!this.tempList[uuid])
                this.tempList[uuid] = {};

            this.tempList[uuid][groupName] = data;
        }


    },
    addRowBeforeEvent : function()
    {
        var _this = this;
        var uuid = undefined;
        axboot.ajax({
            url : "/ad/ad004/ad004/getUUID",
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
        fnObj.gridView_d.clear();
    },
    addRowAfterEvent : function()
    {
        fnObj.gridView_d.clear();
        fnObj.sqlView.clear();
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
        this.setColumnInfo(ad00402.column_info);
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
        var data = undefined;

        var dataProvider = this.gridObj.getGridView().getDataProvider();
        var compList = dataProvider.getJsonRows();

        var isExistRow = function(list,columnName,columnValue)
        {
            var result = false;
            for(var i = 0; i < list.length; i++)
            {
                if(list[i][columnName] == columnValue && !(dataProvider.getRowState(i) == "deleted"))
                {
                    result = true;
                    list.splice(i,1);
                    break;
                }
            }
            return result;
        }

        for(var i = 0; i < list.length; i++)
        {
            //detailList.push($.extend({},popupDetailTemplate,{sqlColumn : list[i]}))
            data = list[i];
            if(data.includes(" AS "))
            {
                data = data.split(" AS ")[1];
            }
            else if(data.includes("."))
            {
                data = data.split(".")[1];
            }
            data = data.replace(/\s/gi,"");
            if(!isExistRow(compList,"sqlColumn",data))
            {
                this.gridObj.addRow($.extend({},popupDetailTemplate,{sqlColumn : data}));
            }
        }

        for(var i = 0; i < compList.length;i ++)
        {
            var rowIndex = dataProvider.searchDataRow({
                startIndex: -1
                ,fields:["sqlColumn"]
                ,values : [compList[i]["sqlColumn"]]
            });
            dataProvider.removeRow(rowIndex);
        }
        //this.setData(detailList,"append");
    },
    clear : function () {
        this.setData([]);
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