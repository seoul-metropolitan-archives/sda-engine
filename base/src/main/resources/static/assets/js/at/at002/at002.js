var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({ //트리 리스트
            type: "GET",
            url: "/api/v1/at/at001/02/list",
            data: $.extend({}, {pageSize: 1000}, {authorityTypeUuid:authorityTypeUuid,authorityName:$("#AuthNm").val()}),
            callback: function (res) {
                fnObj.gridView01.resetCurrent();
                fnObj.gridView02.resetCurrent();
                fnObj.gridView01.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({ //트리 리스트
            type: "GET",
            url: "/api/v1/at/at001/03/list",
            data: $.extend({}, {pageSize: 1000}, {authorityUuid:data.authorityUuid}),
            callback: function (res) {
                fnObj.gridView02.resetCurrent();
                fnObj.gridView02.setData(res.list);
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
        url: "/assets/js/column_info/at00101.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/at00201.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();
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
        $('input:radio[name="radio"]').change(function(){
            authorityTypeUuid = event.currentTarget.value;
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
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
        var radioTag = "";
        for(var i = 0; i < codes.length; i++)
        {
            if(defaultYNs[i] == 'Y'){
                radioTag += '<label for="authType' + i + '"><input type="radio" id="authType' + i + '"  name="radio" class="no_border" value="' + codes[i] + '" checked="checked">' + names[i] + '</label>'
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
        this.gridObj.itemClick(this.itemClick);
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
    itemClick: function (data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
    },
});

/*팝업 헤더*/
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId:"realgrid02",
    entityName : "AT_AUTHORITY_RESULT",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(at00201.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
    itemClick: function (data) {

    },
});
/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}

 isDataChanged = function () {
    if (fnObj.gridView01.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}
 */