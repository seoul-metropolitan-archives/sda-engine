var fnObj = {};
var aggregationUuid = "";
var nodeType = "";
var levelLabels = null;
var levelValues = null;

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.callback();
            parent.axboot.modal.close();
        }
    },
    PAGE_SAVE: function (caller, act, list) {
        if(!fnObj.gridView01.gridObj.validate()){
            return false;
        }else{
            var data = fnObj.gridView01.getData();
            var saveData  = null;
            $.each(data, function(idx, item){
                item["nodeType"] = nodeType;
                item["publishedStatusUuid"] = axboot.commonCodeValueByCodeName("CD121", "Draft");

                if(nodeType == "aggregation"){
                    //cloneItem["typeUuid"] = axboot.commonCodeValueByCodeName("CD127", "Temporary");
                    item["parentAggregationUuid"] = aggregationUuid;
                }else if(nodeType == "item"){
                    item["aggregationUuid"] = aggregationUuid;
                }

                if(item["creationStartDate"] != null)
                    item["creationStartDate"] = new Date(item["creationStartDate"]).format("yyyyMMdd");
                if(item["creationEndDate"] != null)
                    item["creationEndDate"] = new Date(item["creationEndDate"]).format("yyyyMMdd");

                if(item["accumulationStartDate"] != null)
                    item["accumulationStartDate"] = new Date(item["accumulationStartDate"]).format("yyyyMMdd");
                if(item["accumulationEndDate"] != null)
                    item["accumulationEndDate"] = new Date(item["accumulationEndDate"]).format("yyyyMMdd");

                if(item["descriptionStartDate"] != null)
                    item["descriptionStartDate"] = new Date(item["descriptionStartDate"]).format("yyyyMMdd");
                if(item["descriptionEndDate"] != null)
                    item["descriptionEndDate"] = new Date(item["descriptionEndDate"]).format("yyyyMMdd");
            });

            axboot.ajax({
                url: "/rc/rc001/saveRecords",
                data: JSON.stringify(data),
                dataType : "JSON",
                type : "POST",
                callback: function (res) {
                    if (res.status == -500) {
                        axWarningToast.push(res.message);
                    } else {
                        axboot.getCommonMessage("AA007");
                        ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
                    }
                },
                options: {
                    onError: axboot.viewError
                }
            });
        }
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

        $.ajax({
            url: "/assets/js/column_info/rc00103_p03.js",
            dataType: "script",
            async : false,
            success: function(){}
        });

        levelLabels = $.map($("[data-ax-path='levelUuid'] option") ,function(option) {
            return $(option).text();
        });

        levelValues = $.map($("[data-ax-path='levelUuid'] option") ,function(option) {
            return option.value;
        });

        fnObj.popupView.initView(parent.axboot.modal.getData());
        fnObj.gridView01.initView();

        $("#save").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
        });
    }
};

fnObj.popupView = axboot.viewExtend({
    initView : function(data){
        console.log(data);
        aggregationUuid = data["aggregationUuid"];
        nodeType = data["nodeType"];

        if(nodeType == "item"){
            $(".excelName").text("Item");
        }else{
            $(".excelName").text("Aggregation");
        }
        this.initEvent();
    },
    initEvent : function()
    {
        var _this = this;
        $("#close").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE)
        });
    }
});


fnObj.gridView01 = axboot.viewExtend(axboot.gridView,{
    tagId : "realgrid01",
    uuidFieldName : "uuid",
    entityName : "RC_ITEM",
    noPermission : true,
    initView  : function()
    {
        this.initInstance();

        $.each(rc00103_p03["column_info_" + nodeType], function(idx, item){
            if(item.name == "levelUuid"){
                item.labels = levelLabels;
                item.values = levelValues;
            }

            if(item.name == "typeUuid" ){
                if(nodeType == "aggregation"){
                    item.required = true;
                }else{
                    item.required = false;
                }

            }
        });

        this.setColumnInfo(rc00103_p03["column_info_" + nodeType]);
        this.makeGrid();

        this.gridObj.addRow();
    },
    itemClick : function(data){

    },
    getSelectedData : function()
    {
        return this.gridObj.getSelectionData();
    },
    validate: function () {
        var rs = this.model.validate();
        if (rs.error) {
            alert(rs.error[0].jquery.attr("title") + '을(를) 입력해주세요.');
            rs.error[0].jquery.focus();
            return false;
        }
        return true;
    }
});