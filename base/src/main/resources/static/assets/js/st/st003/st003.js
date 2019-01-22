
var fnObj = {};
var parentContainerUuid = "";
var selectedItem ; //선택된 그리드 아이템
var classList = new Object();
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";
var currentContainerUuid = "";
var aggregationUuid = "";
var leaf = "";
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        if(currentContainerUuid != "") {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,{containerUuid:currentContainerUuid});
        }
    },
    PAGE_SEARCH_TREE: function (caller, act, data) {
        axboot.ajax({ //트리리스트조회
            type: "GET",
            url: "/api/v1/st/st002/01/list01",
            async : false,
            data: $.extend({}, {pageSize: 1000}),
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
    PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({//그리드리스트조회
            type: "GET",
            url: "/api/v1/st/st003/01/list01",
            async : false,
            data: $.extend({}, {pageSize: 1000},this.formView.getData(),{containerUuid: data.containerUuid},{aggregationCode : $("input[data-ax-path='aggregationCode']").val()}),
            callback: function (res) {
                fnObj.gridView02.resetCurrent();
                fnObj.gridView02.setData(res.list);
                fnObj.formView.setFormData("itInAggregationName", "");

                //item조회인데 주석친다.
                //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2,data)
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH2: function (caller, act, data) {//위에 그리드에서 선택된 항목

        axboot.ajax({
            type: "GET",
            url: "/api/v1/cl/cl003/02/list01",
            async : false,
            data: $.extend({}, {pageSize: 10000},{aggregationUuid: data.aggregationUuid}),
            callback: function (res) {
                fnObj.gridView03.resetCurrent();
                fnObj.gridView03.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    STATUS_UPDATE: function (caller, act, data) {

        if (fnObj.gridView02.isChangeData() == true) {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA006")
            }, function () {
                if (this.key == "ok") {
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    return;
                }
            });
        }else{

            var rows = fnObj.gridView02.gridObj.getCheckedList();
            if (!rows || rows.length < 1) return;
            var params = rows.filter(function (item) {
                item.changeStatus = data;
                return item.arrangeRecordsResultUuid != "";
            });


            axboot.ajax({
                type: "PUT",
                url: "/api/v1/st/st003/02/confirm",
                data: JSON.stringify(params),
                callback: function (res) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,{containerUuid:params[0].containerUuid});
                },
                options: {
                    onError: axboot.viewError
                }
            });
        }
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
        ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE);
        // ACTIONS.dispatch(ACTIONS.TOP_GRID_DETAIL_PAGE_SAVE);
    },
    TOP_GRID_SAVE: function (caller, act, data) {
        //여길 고쳐야 한당.
        var lists = fnObj.gridView02.gridObj.getData();
        var result = false;

        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st003/03/save",
            data: JSON.stringify(lists),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        })
            .done(function () {
                axToast.push("저장 작업이 완료되었습니다.");
            });
        return result;
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    PAGE_ARRANGE: function (caller, act, data) {

        if(leaf == "1"){
            axboot.modal.open({
                modalType: "ARRANGE_POPUP",
                width: 1600,
                height: 800,
                header: {
                    title: "ARRANGE"
                },
                sendData: function () {
                    return {
                        confirmBtn:"Arrange",
                        description: fnObj.formView.getData().aggInContainerName,
                        containerUuid :  currentContainerUuid
                    };
                },
                callback: function (data) {
                    if(this) this.close();
                    if(data){
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH,{currentContainerUuid : currentContainerUuid});
                    }
                }
            });
        }else{
            axToast.push(axboot.getCommonMessage("ST003_01"));
        }

    },
    dispatch: function (caller, act, data) {
        var result = ACTIONS.exec(caller, act, data);
        if (result != "error") {
            return result;
        } else {
            return false;
        }
    },
    SEARCH_AGGREGATION_SCH : function(caller, act, data)
    {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='aggregationCode']").val(data["AGGREGATION_CODE"])
                aggregationUuid = data["AGGREGATION_UUID"];
                if(this.close) this.close();

                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,data);
            }
        });
    },
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
        url: "/assets/js/column_info/st00201.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/st00301.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/st00302.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();
    _this.gridView03.initView();
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH_TREE, this.formView.getData());
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

        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        /*$("input[data-ax-path='arrangedFromDate01']").val(getFormattedDate(new Date(), true));
        $("input[data-ax-path='arrangedToDate01']").val(getFormattedDate(new Date()));*/

        this.initEvent();



    },
    initEvent: function () {
        var _this = this;

        $("input[data-ax-path='containerName']").focusout(function(){

            if("" == $(this).val().trim())
            {
                $("input[data-ax-path='containerName']").val("");
            }
        });
        $("input[data-ax-path='provenance']").focusout(function(){

            if("" == $(this).val().trim())
            {
                $("input[data-ax-path='provenance']").val("");
            }
        });

        $("input[data-ax-path='containerName'],input[data-ax-path='provenance'] ,input[data-ax-path='controlNumber']").keyup(function(){
            if(13 == event.keyCode)
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        });
        $("select[data-ax-path='statusUuid'], select[data-ax-path='containerTypeUuid'],select[data-ax-path='typeUuid']").change(function() {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        });


        $(".bdb").delegate("#rg_tree_allopen", "click", function () {
            _this.expandAll();
        });
        $(".bdb").delegate("#rg_tree_allclose", "click", function () {
            _this.collapseAll();
        });


        $("input[data-ax-path='aggregationCode']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU136",
                searchData : $("input[data-ax-path='aggregationCode']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_AGGREGATION_SCH,data);
        });

        $("input[data-ax-path='arrangedFromDate01']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='arrangedFromDate01']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='arrangedToDate01']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='arrangedToDate01']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
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
    },
    expandAll:function(){
        fnObj.gridView01.gridObj.expandAll();
    },
    collapseAll:function() {
        fnObj.gridView01.gridObj.collapseAll();
    }
});

fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid01",
    initView: function () {
        this.gridObj = new TreeGridWrapper("realgrid01", "/assets/js/libs/realgrid", true);
        this.gridObj.setGridStyle("100%", "100%")
            .setOption({
                footer:{visible:false},
                header: { visible: false },
                checkBar: {visible: false},
                indicator: {visible: false},
                stateBar:{visible:false}
            })
        this.gridObj.setColumnInfo(st00201.column_info).makeGrid();

        this.gridObj.setDisplayOptions({
            fitStyle:"evenFill"
        });
        this.gridObj.itemClick(this.itemClick);
        this.bindEvent();
    },
    bindEvent : function()
    {
        var _this = this;
        $(".open_close.expendAll").click(function(){
            _this.gridObj.expandAll();
        });
        $(".open_close.collapseAll").click(function(){
            _this.gridObj.collapseAll();
        });
        $("#leftMenuTreeParam").keydown(function(event){
            if(13 == event.keyCode)
                $("#searchLeftTree").click();
        })
        $("#searchLeftTree").click(function(){
            if("" != $("#leftMenuTreeParam").val())
            {
                _this.gridObj.search(["containerName"],$("#leftMenuTreeParam").val())
            }
        });
        $(".btn_arrange").click(function(){
            ACTIONS.dispatch(ACTIONS.MODAL_OPEN);
        });
    },
    setData: function (list) {
        this.gridObj.setTreeDataForArray(list, "orderKey1");
    },
    isChangeData: function () {
        if (this.getData().length > 0) {
            return true;
        } else {
            return false;
        }
    },
    itemClick: function (data, index) {

        leaf = data.leaf;

        if(data != null){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
            fnObj.gridView03.clearData();
            fnObj.formView.setFormData("aggInContainerName", data.containerTreeName);
            //fnObj.formView.setFormData("itInAggregationName", "");
            currentContainerUuid = data.containerUuid;
        }

    },
    getData: function () {
        return this.gridObj.getData();
    },

});

fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid02",
    entityName : "arrangeRecordsResultUuid",
    primaryKey : "arrangeRecordsResultUuid",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00301.column_info);
        /*this.gridObj.setFixedOptions({
            colCount: 3
        });*/
        this.gridObj.setOption({
            checkBar: {visible: true},
            /*indicator: {visible: true}*/
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        this.removeRowBeforeEvent(this.cancelDelete);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    isChangeData: function () {
        if (this.getData().length > 0) {
            return true;
        } else {
            return false;
        }
    },
    getRowData: function (){
        return this.gridObj.getSelectedData();
    },
    itemClick: function (data, index) {

        //여기서 클릭하면
        //item list가 나와야 한다.

         if(data != null){
             ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2, data);
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

        //confirm인경우 삭제 못함
        if(state == CONFIRM_STATUS){
            axToast.push(axboot.getCommonMessage("RS001_01"));
            this.setRunDel(false);
            /*this.setRunDel(true);
            this.setConfirmYn(true,"ST004_01");*/
        }else{
            //draft인 경우는 그냥 삭제
            this.setRunDel(true);
        }


    }
});

fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid03",
    entityName : "containerUuid",
    primaryKey : "containerUuid",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00302.column_info);
        this.gridObj.setFixedOptions({
            colCount: 2
        });
        /*this.gridObj.setOption({
            checkBar: {visible: true},
            indicator: {visible: true}
        })*/
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    isChangeData: function () {
        if (this.getData().length > 0) {
            return true;
        } else {
            return false;
        }
    },
    getRowData: function (){
        return this.gridObj.getSelectedData();
    },
    itemClick: function (data, index) {
    }
});
/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}
 */

isDataChanged = function () {
    if (fnObj.gridView02.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}


function getFormattedDate(date, isStart) {
    var day;
    var tempDate;
    if (isStart) {
        date.setDate(date.getDate() - 10);
        tempDate = date.getDate();
    } else {
        tempDate = date.getDate();
    }
    day = tempDate.toString();

    var year = date.getFullYear();
    var month = (1 + date.getMonth()).toString();
    month = month.length > 1 ? month : '0' + month;
    day = day.length > 1 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}

function checkDate(date) {
    var result = true;
    var strValue = date;
    var chk1 = /^(19|20)\d{2}-([1-9]|1[012])-([1-9]|[12][0-9]|3[01])$/;
    //var chk2 = /^(19|20)\d{2}\/([0][1-9]|1[012])\/(0[1-9]|[12][0-9]|3[01])$/;
    var chk2 = /^(19|20)\d{2}-([0][1-9]|1[012])-([012][1-9]|3[01])$/;
    if (strValue == "") { // 공백이면 무시
        return result;
    }
//-------------------------------------------------------------------------------
// 유효성 검사- 입력형식에 맞게 들왔는지 // 예) 2000-1-1, 2000-01-01 2가지 형태 지원
//-------------------------------------------------------------------------------
    if (chk1.test(strValue) == false && chk2.test(strValue) == false) { // 유효성 검사에 둘다 성공하지 못했다면
        //alert("1999-1-1 형식 또는 \r\n1999-01-01 형식으로 날자를 입력해주세요.");
        axToast.push(axboot.getCommonMessage("AA011"));
        result = false;

    }
    return result;
}


