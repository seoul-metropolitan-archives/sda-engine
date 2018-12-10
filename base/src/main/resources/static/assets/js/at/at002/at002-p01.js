var fnObj = {};
var parentData = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({ //트리 리스트
            type: "GET",
            url: "/api/v1/at/at001/02/list",
            data: $.extend({}, {pageSize: 1000},parentData, {authorityTypeUuid:authorityTypeUuid,authorityName:$("#AuthNm").val()}),
            callback: function (res) {
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    STATUS_UPATE: function (caller, act, data) {
    },
    PAGE_CONFIRM: function (caller, act, data) {
    },
    PAGE_CANCEL: function (caller, act, data) {
    },
    PAGE_SAVE: function (caller, act, data) {
    },
    TOP_GRID_SAVE: function (caller, act, data) {
    },
    TOP_GRID_DETAIL_PAGE_SAVE :function () {
    },
    CLOSE_TAB: function (caller, act, data) {
    },
    MENU_OPEN: function (caller,act, data){

    },
    PAGE_CLOSE: function (caller,act, data){
        if (parent) {
            data.AUTHORITY_NAME = data.authorityName;
            data.AUTHORITY_NO = data.authorityNo;
            data.AUTHORITY_TYPE_UUID = data.authorityTypeUuid;
            data.AUTHORITY_UUID = data.authorityUuid;
            data.ORG_TYPE_UUID = data.orgTypeUuid;
            parent.axboot.modal.callback(data);
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

fnObj.pageStart = function () {
    var _this = this;
    parentData = parent.axboot.modal.getData();

    $.ajax({
        url: "/assets/js/controller/simple_controller.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/at00202.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    authorityTypeUuid = '';
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH, this.formView.getData());
};

fnObj.formView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {useYn: ""});
    },
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.makeRadio();
        this.initEvent();
    },
    initEvent: function () {
        var _this = this;
        parentsData = parent.axboot.modal.getData();
        $('input:radio[name="radio"]').change(function(){
            authorityTypeUuid = event.currentTarget.value;
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        });

        $(".ok_popup").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE,fnObj.gridView01.getSelectedData());
        });

        $(".close_popup").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
        });

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
    makeRadio: function() {
        var codes = axboot.commonCodeFilter("CD161").codeArr;
        var names = axboot.commonCodeFilter("CD161").nameArr;
        var defaultYNs = axboot.commonCodeFilter("CD161").defaultYNArr;
        var radioTag = '<label for="authType99"><input type="radio" id="authType99"  name="radio" class="no_border"  value="" checked="checked">' +'All'+ '</label>';
        for(var i = 0; i < codes.length; i++)
        {
            if(defaultYNs[i] == 'Y'){
                radioTag += '<label for="authType' + i + '"><input type="radio" id="authType' + i + '"  name="radio" class="no_border" value="' + codes[i] + '" >' + names[i] + '</label>'
                authorityTypeUuid = codes[i];
                authorityTypeNm = names[i];
            }else{
                radioTag += '<label for="authType' + i + '"><input type="radio" id="authType' + i + '" name="radio" class="no_border" value="' + codes[i] + '">' + names[i] + '</label>'
            }


        }
        $(".rdo_box").append(radioTag);
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
    }
});

fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId:"realgrid01",
    primaryKey : "authorityUuid",
    entityName : "AT_AUTHORITY",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(at00101.column_info);
        this.makeGrid();
        this.gridObj.onDataCellDblClicked(this.itemDbClick);
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
    itemDbClick : function(grid,index)
    {
        ACTIONS.dispatch(ACTIONS.PAGE_CLOSE,fnObj.gridView01.getSelectedData());
    }
});
