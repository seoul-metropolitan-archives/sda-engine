var fnObj = {};
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";
var beforeData01 = null;
var beforeData02 = null;
var repositoryUuid = '';
var shelfUuid ='';
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        beforeData01 = null;
        beforeData02 = null;
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
    },
    PAGE_SEARCH01: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st001/01/list01",
            //data: $.extend({}, this.formView.getData()),
            data: $.extend({},{useYn : $('input[data-ax-path="useYn01"]:checked').val()},{statusUuid : $("select[data-ax-path='statusUuid01']").val()} , {repositoryCode : $("input[data-ax-path='repositoryCode']").val()},{ repositoryName : $("input[data-ax-path='repositoryName']").val()}),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.disabledColumn();
                fnObj.gridView02.clearData();
                fnObj.gridView03.clearData();
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH02: function (caller, act, data) {
        if(fnObj.gridView01.getSelectedData() == null){
            return;
        }
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st001/01/list02",
            //data: $.extend({}, this.formView.getData(),{repositoryUuid : fnObj.gridView01.getSelectedData().repositoryUuid}),
            data: $.extend({},{useYn : $('input[data-ax-path="useYn02"]:checked').val()},{statusUuid : $("select[data-ax-path='statusUuid02']").val()}, {shelfCode : $("input[data-ax-path='shelfCode']").val()},{ shelfName : $("input[data-ax-path='shelfName']").val()},{repositoryUuid : fnObj.gridView01.getSelectedData().repositoryUuid}),
            callback: function (res) {
                fnObj.gridView02.setData(res.list);
                fnObj.gridView02.disabledColumn();
                fnObj.gridView03.clearData();
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH03: function (caller, act, data) {
        if(fnObj.gridView02.getSelectedData() == null){
            return;
        }
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st001/01/list03",
            data: $.extend({},{useYn : $('input[data-ax-path="useYn03"]:checked').val()} ,{statusUuid : $("select[data-ax-path='statusUuid03']").val()},{shelfUuid : fnObj.gridView02.getSelectedData().shelfUuid},{ rowNo : $("input[data-ax-path='row']").val() } , { columnNo : $("input[data-ax-path='columnNo']").val()}),
            callback: function (res) {
                fnObj.gridView03.setData(res.list);
                fnObj.gridView03.disabledColumn();
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    STATUS_UPDATE01: function (caller, act, data) {

        if (fnObj.gridView01.isChangeData() == true) {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA006")
            }, function () {
                if (this.key == "ok") {
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    return;
                }
            });
        }else{
            var rows = fnObj.gridView01.gridObj.getCheckedList();
            if (!rows || rows.length < 1) return;
            var params = rows.filter(function (item) {
                item.changeStatus = data;
                return item.repositoryUuid != "";
            });

            axboot.ajax({
                type: "PUT",
                url: "/api/v1/st/st001/02/confirm01",
                data: JSON.stringify(params),
                callback: function (res) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
                },
                options: {
                    onError: axboot.viewError
                }
            });
        }
    },
    STATUS_UPDATE02: function (caller, act, data) {
        var rows = fnObj.gridView02.gridObj.getCheckedList();

        if (!rows || rows.length < 1) return;

        var params = rows.filter(function (item) {
            item.changeStatus = data;
            return item.shelfUuid != "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/st/st001/02/confirm02",
            data: JSON.stringify(params),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    STATUS_UPDATE03: function (caller, act, data) {
        var rows = fnObj.gridView03.gridObj.getCheckedList();

        if (!rows || rows.length < 1) return;

        var params = rows.filter(function (item) {
            item.changeStatus = data;
            return item.locationUuid != "";
        });

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/st/st001/02/confirm03",
            data: JSON.stringify(params),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_CONFIRM: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE, CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE, CANCEL_STATUS);
    },
    PAGE_SAVE: function (caller, act, data) {
        if (!this.gridView01.gridObj.validate() && !this.gridView02.gridObj.validate()
            && !this.gridView03.gridObj.validate()) {
            return false;
        } else {
            if(fnObj.gridView01.gridObj.isDataChanged()){
                ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE01);
            }
            if(fnObj.gridView02.gridObj.isDataChanged()){
                ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE02);
            }
            if(fnObj.gridView03.gridObj.isDataChanged()){
                ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE03);
            }
        }
    },
    TOP_GRID_SAVE01: function (caller, act, data) {
        debugger
        var result = false;

       /* var flag = false;
        if(fnObj.gridView01.gridObj.getCheckedList().length == 1){
            if(fnObj.gridView01.gridObj.getCheckedList()[0].repositoryUuid == this.gridView01.getData()[0].repositoryUuid){
                flag = true;
            }
        }*/

        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st001/02/save01",
            data: JSON.stringify(this.gridView01.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
            }
        })
            .done(function () {
                fnObj.gridView01.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    TOP_GRID_SAVE02: function (caller, act, data) {
        var result = false;

        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st001/02/save02",
            data: JSON.stringify(this.gridView02.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
            }
        })
            .done(function () {
                fnObj.gridView02.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    TOP_GRID_SAVE03: function (caller, act, data) {
        var result = false;

        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st001/02/save03",
            data: JSON.stringify(this.gridView03.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
            }
        })
            .done(function () {
                fnObj.gridView03.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    MENU_OPEN: function (caller, act, data) {

    },
    POP_OPEN: function(caller, act, data){
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : false,
            sendData: function () {
                return data;
            },
            callback: function (data) {
                if(this.close)
                    this.close();

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
        url: "/assets/js/column_info/st00101.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/st00102.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/st00103.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();
    _this.gridView03.initView();
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01, this.formView.getData());
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
        this.bindEvent();
    },
    initEvent: function () {
        var _this = this;
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
    },
    bindEvent : function()
    {
        var _this = this;
        $(".btn_confirm01").click(function(){
            ACTIONS.dispatch(ACTIONS.STATUS_UPDATE01,CONFIRM_STATUS);
        });
        $(".btn_cancel01").click(function(){
            ACTIONS.dispatch(ACTIONS.STATUS_UPDATE01,CANCEL_STATUS);
        });
        $(".btn_confirm02").click(function(){
            ACTIONS.dispatch(ACTIONS.STATUS_UPDATE02,CONFIRM_STATUS);
        });
        $(".btn_cancel02").click(function(){
            ACTIONS.dispatch(ACTIONS.STATUS_UPDATE02,CANCEL_STATUS);
        });
        $(".btn_confirm03").click(function(){
            ACTIONS.dispatch(ACTIONS.STATUS_UPDATE03,CONFIRM_STATUS);
        });
        $(".btn_cancel03").click(function(){
            ACTIONS.dispatch(ACTIONS.STATUS_UPDATE03,CANCEL_STATUS);
        });

        $('.pop_repository').click(function(){
            var data = {
                popupCode : "PU145"
            };
            ACTIONS.dispatch(ACTIONS.POP_OPEN,data);
        });


        $("input[data-ax-path='shelfCode'],input[data-ax-path='shelfName']").keydown(function(e){
            if(e.keyCode == 13){
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
            }
        });

        $("input[data-ax-path='repositoryCode'],input[data-ax-path='repositoryName']").keydown(function(e){
            if(e.keyCode == 13){
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
            }
        });

        $('select[data-ax-path="statusUuid01"]').on('change',function(){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
        });

        $('select[data-ax-path="statusUuid02"]').on('change',function(){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
        });

        $('select[data-ax-path="statusUuid03"]').on('change',function(){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
        });

        $('input[data-ax-path="useYn01"]').on('change',function(){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
        });
        $('input[data-ax-path="useYn02"]').on('change',function(){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
        });
        $('input[data-ax-path="useYn03"]').on('change',function(){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
        });


        $("input[data-ax-path='row'],input[data-ax-path='columnNo']").keydown(function(e){
            if(e.keyCode == 13){
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
            }
        });


    },
});

/*팝업 헤더*/
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey: "repositoryUuid",
    entityName: "ST_REPOSITORY",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00101.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        this.removeRowBeforeEvent(this.cancelDelete);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    disabledColumn: function () {
        var state = axboot.commonCodeValueByCodeName("CD138", CONFIRM_STATUS);
        this.gridObj.setCustomCellStyleRows("disable", function (row) {

            if (row["statusUuid"] == state)
                return true;
            else
                return false;
        }, ["statusUuid", "repositoryCode", "repositoryName", "description"]);
    },
    itemClick: function (data) {
        if (fnObj.gridView02.isChangeData() == true || fnObj.gridView03.isChangeData() == true) {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA006")
            }, function () {
                if (this.key == "ok") {
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                } else {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
                }
            });
        } else {

            var codes = axboot.commonCodeFilter("CD138").codeArr;
            var names = axboot.commonCodeFilter("CD138").nameArr;
            var state = undefined;
            for (var i = 0; i < codes.length; i++) {
                if (codes[i] == fnObj.gridView01.getSelectedData().statusUuid) {
                    state = names[i];
                    break;
                }
            }


            //confirm confirm
            if(state == CONFIRM_STATUS){
                if(fnObj.gridView01.getSelectedData().useYn == "Y"){
                    fnObj.gridView02.gridObj.setRunAdd(true);
                }else{
                    fnObj.gridView02.gridObj.setRunAdd(false);
                }
            }else{
                fnObj.gridView02.gridObj.setRunAdd(false);
            }

            $('input[data-ax-path="displayRepositoryName"]').val(fnObj.gridView01.getSelectedData().repositoryName);

            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
        }

    },
    cancelDelete: function(){
        var codes = axboot.commonCodeFilter("CD138").codeArr;
        var names = axboot.commonCodeFilter("CD138").nameArr;
        var state = undefined;
        for (var i = 0; i < codes.length; i++) {
            if (codes[i] == fnObj.gridView01.getSelectedData().statusUuid) {
                state = names[i];
                break;
            }
        }

        if(state == CONFIRM_STATUS){
            axToast.push(axboot.getCommonMessage("DF001_01"));

            this.setRunDel(false);
        }else{
            this.setRunDel(true);
            this.setConfirmYn(true,"ST001_01");
        }
    }

});
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid02",
    primaryKey: "shelfUuid",
    uuidFieldName: "shelfUuid",
    entityName: "ST_SHELF",
    parentsUuidFieldName: "repositoryUuid",
    parentsGrid: fnObj.gridView01,
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00102.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        this.removeRowBeforeEvent(this.cancelDelete);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    /*disabledColumn: function () {
        var state = axboot.commonCodeValueByCodeName("CD138", CONFIRM_STATUS);
        this.gridObj.setCustomCellStyleRows("disable", function (row) {

            if (row["statusUuid"] == state)
                return true;
            else
                return false;
        }, ["statusUuid", "shelfCode", "shelfName", "maxContainer", "description"]);
    },*/
    disabledColumn: function () {
        var state = axboot.commonCodeValueByCodeName("CD138", CONFIRM_STATUS);
        this.gridObj.setCustomCellStyleRows("disable", function(row){
            if (row["statusUuid"] == state)
                return true;
            else if(fnObj.gridView02.getSelectedData().useYn == "Y")
                return true;
            else return false;
        },function(row){
            if(row["statusUuid"] == state) {
                return ["statusUuid", "shelfCode", "shelfName", "maxContainer", "description"];
            }else {
                return ["useYn"];
            }
        });
   },
    itemClick: function (data) {
        if (fnObj.gridView03.isChangeData() == true) {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA006")
            }, function () {
                if (this.key == "ok") {
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                } else {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
                }
            });
        } else {

            var codes = axboot.commonCodeFilter("CD138").codeArr;
            var names = axboot.commonCodeFilter("CD138").nameArr;
            var state = undefined;
            for (var i = 0; i < codes.length; i++) {
                if (codes[i] == fnObj.gridView02.getSelectedData().statusUuid) {
                    state = names[i];
                    break;
                }
            }

            //confirm confirm
            if(state == CONFIRM_STATUS){
                if(fnObj.gridView02.getSelectedData().useYn == "Y"){
                    fnObj.gridView03.gridObj.setRunAdd(true);
                }else{
                    fnObj.gridView03.gridObj.setRunAdd(false);
                }
            }else{
                fnObj.gridView03.gridObj.setRunAdd(false);
            }

            $('input[data-ax-path="displayShelfName"]').val(fnObj.gridView02.getSelectedData().shelfName);


            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
        }
    },
    cancelDelete: function(){
        var codes = axboot.commonCodeFilter("CD138").codeArr;
        var names = axboot.commonCodeFilter("CD138").nameArr;
        var state = undefined;
        for (var i = 0; i < codes.length; i++) {
            if (codes[i] == fnObj.gridView02.getSelectedData().statusUuid) {
                state = names[i];
                break;
            }
        }

        if(state == CONFIRM_STATUS){
            axToast.push(axboot.getCommonMessage("DF001_01"));

            this.setRunDel(false);
        }else{
            this.setRunDel(true);
            this.setConfirmYn(true,"ST001_01");
        }
    },
});

fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid03",
    primaryKey: "locationUuid",
    entityName: "ST_LOCATION",
    parentsUuidFieldName: "shelfUuid",
    parentsGrid: fnObj.gridView02,
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00103.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        this.removeRowBeforeEvent(this.cancelDelete);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    disabledColumn: function () {

        var state = axboot.commonCodeValueByCodeName("CD138", CONFIRM_STATUS);

        this.gridObj.setCustomCellStyleRows("disable", function (row) {

            if (row["statusUuid"] == state)
                return true;
            else
                return false;
        }, ["statusUuid", "rowNo", "columnNo", "description","useYn"]);
    },
    itemClick: function (data) {

    },
    cancelDelete: function(){
        var codes = axboot.commonCodeFilter("CD138").codeArr;
        var names = axboot.commonCodeFilter("CD138").nameArr;
        var state = undefined;
        for (var i = 0; i < codes.length; i++) {
            if (codes[i] == fnObj.gridView03.getSelectedData().statusUuid) {
                state = names[i];
                break;
            }
        }

        if(parseInt(fnObj.gridView03.getSelectedData().totalContainer) >= 1){

            axToast.push(axboot.getCommonMessage("ST001_04"));
            this.setRunDel(false);
            return;

        }

        if(state == CONFIRM_STATUS){
            axToast.push(axboot.getCommonMessage("DF001_01"));

            this.setRunDel(false);
        }else{
            if(this.getSelectedData().rowNo == ""){
                this.setRunDel(true);
            }else{
                this.setRunDel(true);
                this.setConfirmYn(true,"ST001_01");
            }


        }
    }
});
