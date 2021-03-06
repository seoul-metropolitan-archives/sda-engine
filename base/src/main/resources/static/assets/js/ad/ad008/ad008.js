var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        var searchData = this.formView.getData();

        axboot.ajax({
            type: "GET",
            url: "/api/v1/ad/ad008/searchList",
            data: $.extend({}, {pageSize: 1000, sort: "updateDate"}, searchData),
            callback: function (res) {
                if(res.list == null || res.list.length <= 0){
                    fnObj.gridView01.setData([]);
                    fnObj.gridView01.disabledColumn();
                    return;
                }
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.disabledColumn();
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    ERROR_SEARCH: function (caller, act, data) {
    },

    PAGE_SAVE: function (caller, act, data) {
        if(!this.gridView01.gridObj.validate()){
            return false;
        }else{
            ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE);
        }
        // ACTIONS.dispatch(ACTIONS.TOP_GRID_DETAIL_PAGE_SAVE);
    },
    TOP_GRID_SAVE: function (caller, act, data) {
        var result = false;

        axboot.call({
            type: "PUT",
            url: "/api/v1/ad/ad008/saveItems",
            data: JSON.stringify(this.gridView01.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                result = true;
            }
        })
            .done(function () {
                fnObj.gridView01.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
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
    },
    POPUP_SEARCH_01 : function(caller, act, data)
    {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                fnObj.formView.setFormData("entityType", data['ENTITY_TYPE_CODE']);
                fnObj.formView.setFormData("entityName", data['ENTITY_TYPE_NAME']);
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,data);
            }
        });
    },
    POPUP_SEARCH_02 : function(caller, act, data)
    {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                fnObj.formView.setFormData("programName", data['PROGRAM_NAME']);
                if(this.close)
                    this.close();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,data);
            }
        });
    }
});

fnObj.pageStart = function () {
    var _this = this;
    $.ajax({
        url: "/assets/js/column_info/ad00801.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH, this.formView.getData());
};

fnObj.formView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {endYN: ""});
    },
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작

        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        this.initEvent();
    },
    initEvent: function () {
        var _this = this;

        $("input[data-ax-path='primaryKey']").keyup(function(){
            if(13 == event.keyCode)
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        });

        $("input[data-ax-path='entityType']").change(function(){
            $("input[data-ax-path='entityName']").val(" ");
        });

        $("input[data-ax-path='entityType']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU110",
                searchData : $("input[data-ax-path='entityType']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.POPUP_SEARCH_01,data);
        });
        $("input[data-ax-path='entityType']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU110",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.POPUP_SEARCH_01,data);
            }
        });

        $("input[data-ax-path='programName']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU001",
                searchData : $("input[data-ax-path='programName']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.POPUP_SEARCH_02,data);
        });
        $("input[data-ax-path='programName']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU001",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.POPUP_SEARCH_02,data);
            }
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
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
    }
});

/*팝업 헤더*/
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey : "aduitUuid",
    entityName: "AD_ADUIT",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(ad00801.column_info);
        this.setFixedOptions({
            colCount: 3
        });
        this.gridObj.setOption({
            checkBar: {visible: false},
            indicator: {visible: true}
        })
        this.makeGrid();
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
    disabledColumn : function()
    {
        this.gridObj.setCustomCellStyleRows("disable",function(row){

        },[]);
    },
    itemClick: function (data) {

    }

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