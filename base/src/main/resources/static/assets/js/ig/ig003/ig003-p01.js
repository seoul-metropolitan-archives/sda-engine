var fnObj = {};
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";

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

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ig/ig003/01/list01",
            data: $.extend({},this.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
         });
    },
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.callback(data);
        }
    },
    PAGE_DETAIL:function (caller, act, data) {
        fnObj.formView.clear();
        fnObj.childrenMngInfo.removeChild();
        fnObj.childrenDrnInfo.removeChild();
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ig/ig002/01/list02",
            data: $.extend({}, {accessionRecordUuid:data.accessionRecordUuid}),
            callback: function (res) {
                if(res != null && res != "undefined"){
                    fnObj.formView.setFormData("description",res.accessionRecord["description"]);
                    fnObj.formView.setFormData("notes",res.accessionRecord["notes"]);
                    fnObj.childrenDrnInfo.setData(res.childrenDrnInfoList);
                    fnObj.childrenMngInfo.setData(res.childrenMngInfoList);
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    PAGE_CONFIRM: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE,CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE,CANCEL_STATUS);
    },
    PAGE_SAVE: function (caller, act, data) {
        if(!this.gridView01.gridObj.validate()){
            return false;
        }else{
            ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE);
        }
    },
    TOP_GRID_SAVE: function (caller, act, data) {

    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    MENU_OPEN: function (caller,act, data){

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

fnObj.pageStart = function () {
    var _this = this;
    $.ajax({
        url: "/assets/js/controller/simple_controller.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/ig00301_p01_01.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    _this.childrenDrnInfo.initView();
    _this.childrenMngInfo.initView();
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH, this.formView.getData());
};

fnObj.formView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData);
    },
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();
    },
    initEvent: function () {
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
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
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    delay: 300,
    clicks:0,
    timer:null,
    tagId: "realgrid01",
    primaryKey : "recordScheduleUuid",
    entityName: "Record Schedule UUID",
    initView: function () {
        var _this = this;
        this.initInstance();
        this.setColumnInfo(ig00301_p01_01.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        this.gridObj.onDataCellDblClicked(this.onDataCellDblClicked)
    },
    popupCallback: function(grid,data) {
        grid.commit(true);
        fnObj.gridView01.selectiveDisabledColumn();
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
    itemClick: function (data) {
        fnObj.gridView01.clicks++;
        if(fnObj.gridView01.clicks === 1){
            fnObj.gridView01.timer = setTimeout(function () {
                fnObj.gridView01.clicks = 0;
                if (data.accessionRecordUuid != null && data.accessionRecordUuid != "") {
                    ACTIONS.dispatch(ACTIONS.PAGE_DETAIL, data);
                }
            },fnObj.gridView01.delay)
        }else{
            clearTimeout(fnObj.gridView01.timer);
            fnObj.gridView01.clicks = 0;
            fnObj.gridView01.onDataCellDblClicked(data);
        }


    },
    onDataCellDblClicked: function (data) {
        event.stopPropagation();
        data = $.extend({},{type: "edit",accessionRecordUuid:fnObj.gridView01.getSelectedData().accessionRecordUuid});
        ACTIONS.dispatch(ACTIONS.PAGE_CLOSE,data);
        // parentsObj.tabView.open(item);
    }
});

fnObj.childrenMngInfo = axboot.viewExtend({
    targetTag  : $("#childrenMngInfoArea"),
    parentUuid : "",
    nodeType : "",
    popupCode : "",
    template :
    "                                                            <li>" +
    "                                                                <b>Creator</b>" +
    "                                                            <div class='src_box2'>" +
    "                                                                <input type=text data-ax-path='name' class='crtr' readonly>" +
    "                                                                <a href='#' style='top: 0' class='searchAggregation' ><img src='/assets/images/ams/search_normal.png' alt='find'></a>" +
    "                                                            </div>" +
    "                                                            </li>",
    initView: function () {
        this.initEvent();
        this.addChild($("#addMngInfo"));
    },
    initEvent: function () {
        var _this = this;
        $(".childMngInfo").delegate(".btn_del","click",function(){
            if("create" == $(this).parents().eq(2).attr("type"))
            {
                $(this).parents().eq(2).remove();
            }
            else
            {
                $(this).parents().eq(2).hide();
            }
        });
    },
    removeChild: function(){
        $(".childMngInfo").empty();
    },
    addChild : function(_this,data){
        var cloneTag = "";
        if(data != null && data != undefined){
            cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","saveType").attr("saveType","saved").html(this.template);
            cloneTag.find("input[data-ax-path='name']").val(data["name"]);
        }else{
            cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","saveType").attr("saveType","create").html(this.template);
        }
        $(".childMngInfo").append(cloneTag);
        // $(_this).before(cloneTag);
        cloneTag.show();
    },
    setData : function(data){
        if(data != null && data != "undefined"){
            if(data.length > 0){
                for (var i=0;i<data.length;i++){
                    this.addChild($("#addMngInfo"),data[i]);
                }
            }else{
                this.addChild($("#addMngInfo"));
            }
        }else{
            this.addChild($("#addMngInfo"));
        }
    }

});
fnObj.childrenDrnInfo = axboot.viewExtend({
    targetTag  : $("#childrenDnrInfoArea"),
    parentUuid : "",
    nodeType : "",
    popupCode : "",
    template :
    "                                                            <li style='width: 11%;'>" +
    "                                                                <b>Contact Person</b>" +
    "                                                            <div class='src_box2'>" +
    "                                                                <input type=text data-ax-path='name' class='cntPrsn' readonly>" +
    "                                                                <a href='#' style='top: 0' class='searchAggregation' ><img src='/assets/images/ams/search_normal.png' alt='find'></a>" +
    "                                                            </div>" +
    "                                                            </li>" +
    "                                                            <li style='width: 11%; padding: 0 0.5%;'>" +
    "                                                                <b>Tel </b>" +
    "                                                                <div>" +
    "                                                                    <input type='text' data-ax-path='tel' class='cntPrsn' style='width: 100%;' readonly>" +
    "                                                                </div>" +
    "                                                            </li>" +
    "                                                            <li style='width: 75%;'>" +
    "                                                                <b>Address </b>" +
    "                                                                <div>" +
    "                                                                    <input type='text' data-ax-path='address' class='cntPrsn' style='width: 100%;' readonly>" +
    "                                                                </div>" +
    "                                                            </li>",
    initView: function () {
        this.initEvent();
        this.addChild($("#addDnrInfo"));

    },
    initEvent: function () {
        var _this = this;
        $(".childDnrInfo").delegate(".btn_del","click",function(){
            if("create" == $(this).parents().eq(2).attr("saveType"))
            {
                $(this).parents().eq(2).remove();
            }
            else
            {
                $(this).parents().eq(2).hide();
            }
        });
    },
    removeChild: function(){
        $(".childDnrInfo").empty();
    },
    addChild : function(_this,data){
        var cloneTag = "";
        if(data != null && data != undefined){
            cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","saveType").attr("saveType","saved").html(this.template);
            cloneTag.find("input[data-ax-path='name']").val(data["name"]);
            cloneTag.find("input[data-ax-path='tel']").val(data["tel"]);
            cloneTag.find("input[data-ax-path='address']").val(data["address"]);
        }else{
            cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","saveType").attr("saveType","create").html(this.template);
        }
        // $(_this).before(cloneTag);
        $(".childDnrInfo").append(cloneTag);
        cloneTag.show();
    },
    setData : function(data){
        if(data != null && data != "undefined"){
            if(data.length > 0){
                for (var i=0;i<data.length;i++){
                    this.addChild($("#addDnrInfo"),data[i]);
                }
            }else{
                this.addChild($("#addDnrInfo"));
            }
        }else{
            this.addChild($("#addDnrInfo"));
        }
    }
});
