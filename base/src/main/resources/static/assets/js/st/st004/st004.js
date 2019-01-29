
var fnObj = {};
var parentContainerUuid = "";
var selectedItem ; //선택된 그리드 아이템
var classList = new Object();
var CONFIRM_STATUS = "Confirm";
var CANCEL_STATUS = "Draft";
var repositoryUuid = "";
var shelfUuid = "";
var statusUuid = "";
var gridData = [];
var deletingList =  [];
var currentLocationUuid = "";
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st004/01/list02",
            async : false,
            data: $.extend({}, {pageSize: 1000}, this.formView.getData(),{repositoryUuid:repositoryUuid,shelfUuid:shelfUuid,statusUuid03:statusUuid}),
            callback: function (res) {
                if (res.list == null || res.list.length <= 0) {
                    fnObj.gridView01.setData([]);
                    return;
                }
                fnObj.gridView01.setData(res.list);
                deletingList = [];
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH01: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st004/01/list01",
            async : false,
            data: $.extend({},data,{locationUuid : currentLocationUuid},{statusUuid : $('select[data-ax-path="statusUuid"]').val()},{containerTypeUuid : $('select[data-ax-path="containerTypeUuid"]').val()},{containerName : $('input[data-ax-path="containerName"]').val()}),
            callback: function (res) {
                /*if(res.list == null || res.list.length <= 0){
                    fnObj.gridView02.setData([]);
                    return;
                }
                fnObj.gridView02.setData(res.list);*/
                fnObj.gridView02.clearData();
                gridData = [];
                gridData = ax5.util.deepCopy(res.list);
                fnObj.gridView02.setData(res.list);
                deletingList = [];
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SEARCH2: function (caller, act, data) {
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
                return item.arrangeContainersResultUuid != "";
            });


            axboot.ajax({
                type: "PUT",
                url: "/api/v1/st/st004/02/confirm",
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
    },
    TOP_GRID_SAVE: function (caller, act, data) {
        var result = false;

        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st004/02/save",
            data: JSON.stringify(deletingList),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01,{locationUuid : currentLocationUuid});
            }
        })
            .done(function () {
                deletingList = [];
                //fnObj.gridView02.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    PAGE_ARRANGE: function (caller, act, data) {
        // if(fnObj.formView.getData().aggInContainerName == "" ||fnObj.formView.getData().aggInContainerName == undefined ){
        //     return
        // }

        //체크 안하면 Arrange 클릭이 되면 안된다.
        // if(fnObj.gridView01.getSelectedData() == undefined){
        //     return;
        // }


        axboot.modal.open({
            modalType: "ARRANGE_CONTAINER_POPUP",
            width: 1600,
            height: 700,
            header: {
                title: "ARRANGE"
            },
            sendData: function () {
                return {
                    confirmBtn:"Arrange",
                    locationUuid : fnObj.gridView01.getSelectedData().locationUuid,
                    repositoryName : $("input[data-ax-path='repositoryName']").val(),
                    shelfName : $("input[data-ax-path='shelfName']").val(),
                    rowNo : fnObj.gridView01.getSelectedData().rowNo,
                    columnNo : fnObj.gridView01.getSelectedData().columnNo
                };
            },
            callback: function (data) {
                if(this) this.close();

                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01,{locationUuid : currentLocationUuid});

            }
        });
    },
    SEARCH_REPOSITORY_SCH : function(caller, act, data)
    {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='repositoryName']").val(data["REPOSITORY_NAME"])
                repositoryUuid = data['REPOSITORY_UUID'];
                if(this.close) this.close();
            }
        });
    },
    SEARCH_SHELF_SCH : function(caller, act, data)
    {
        /*var data = {
            popupCode: "PU138",
            searchData: repositoryUuid,
            preSearch: false
        };*/

        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='shelfName']").val(data["SHELF_NAME"])
                shelfUuid = data['SHELF_UUID'];
                statusUuid = data['STATUS_UUID'];
                if(this.close) this.close();
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
        url: "/assets/js/column_info/st00402.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/st00401.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();
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
        this.initEvent();
        this.bindEvent();
    },
    initEvent: function () {
        var _this = this;

        $("input[data-ax-path='repositoryName']").parents().eq(1).find("a").click(function(){
            var data = {
                popupCode : "PU137",
                searchData : $("input[data-ax-path='repositoryName']").val().trim(),
                preSearch : false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_REPOSITORY_SCH,data);
        });

        $("input[data-ax-path='repositoryName']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU137",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_REPOSITORY_SCH,data);
            }

        });

        $("input[data-ax-path='shelfName']").parents().eq(1).find("a").click(function(){
            if("" != repositoryUuid) {
                var data = {
                    popupCode: "PU138",
                    searchData: repositoryUuid,
                    preSearch: false
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_SHELF_SCH, data);
            }
        });


        $("input[data-ax-path='rowNo'],input[data-ax-path='columnNo']").keyup(function(){
            if(13 == event.keyCode)
                $(".btn_inquiry01").trigger('click');
        });

        $("select[data-ax-path='statusUuid'], select[data-ax-path='containerTypeUuid']").change(function() {
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
        });



    },
    bindEvent : function()
    {
        var _this = this;
        $(".btn_inquiry01").click(function(){
            if("" != repositoryUuid && "" != shelfUuid)
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        });
        $(".btn_inquiry02").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
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

fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid01",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st00401.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        this.removeRowBeforeEvent(this.cancelDelete);
    },
    isChangeData: function () {
        if (this.getData().length > 0) {
            return true;
        } else {
            return false;
        }
    },
    itemClick: function (data, index) {
        $("input[data-ax-path='selectedRowNo']").val(data["rowNo"]);
        $("input[data-ax-path='selectedColumnNo']").val(data["columnNo"]);
        deletingList = [];
        currentLocationUuid = data.locationUuid;
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01,data);
    },
    getData: function () {
        return this.gridObj.getData();
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },

});

fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId : "realgrid02",
    initView: function () {
        /*this.initInstance();
        this.setColumnInfo(st00402.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
        this.removeRowBeforeEvent(this.cancelDelete);*/

        this.gridObj = new TreeGridWrapper("realgrid02", "/assets/js/libs/realgrid", true);
        this.gridObj.setGridStyle("100%", "100%")
            .setOption({
                header: { visible: true },
                checkBar: {visible: true},
                indicator: {visible: true},
                lineVisible: false
            });
        this.gridObj.setColumnInfo(st00402.column_info).makeGrid();
        this.gridObj.onItemChecked(this.onItemChecked);
        this.gridObj.setDisplayOptions({
            fitStyle:"evenFill"
        });
        // this.gridObj.gridView.setSelectOptions({
        //         style: "none"
        //     }
        // )
        this.gridObj.gridView.setCheckBar({
            checkableExpression: "values['choiceYn'] match 'Y'"
        });
        this.gridObj.gridView.applyCheckables();
        //this.gridObj.itemClick(this.itemClick);
        this.removeRowBeforeEvent(this.cancelDelete);

    },
    isChangeData: function () {
        if (this.getData().length > 0) {
            return true;
        } else {
            return false;
        }
    },
    itemClick: function (data, index) {
        $("input[data-ax-path='selectedRowNo']").val(data["rowNo"]);
        $("input[data-ax-path='selectedColumnNo']").val(data["columnNo"]);
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01,data);
    },
    getData: function () {
        return this.gridObj.getData();
    },
    setData: function (list) {
        this.gridObj.setTreeDataForArray(list, "orderKey1");
    },
    checkChildren : function(index,checked){
        this.gridObj.checkChildren(index, checked, true, false);
    },
    onItemChecked: function(grid,itemIndex,checked) {
        fnObj.gridView02.checkChildren(itemIndex,checked);
    },
    cancelDelete: function(){
        var index = fnObj.gridView02.gridObj.getCurrent().dataRow;
        var state = axboot.commonCodeValueByCodeName("CD138", "Confirm");

        //값이 있으면
        if(fnObj.gridView02.gridObj.getSelectedData().parentContainerUuid != undefined){
            this.setRunDel(false);
            return;
        }

        this.setRunDel(false);
        if(fnObj.gridView02.gridObj.getSelectedData().statusUuid == state) {
            // axToast.push(axboot.getCommonMessage("AD011_02"));
            return;
        }else if(fnObj.gridView02.gridObj.getSelectedData().choiceYn == "N") {
            // axToast.push(axboot.getCommonMessage("AD011_02"));
            return;
        }else{
            fnObj.gridView02.gridObj.gridView.commit(true);

            if(fnObj.gridView02.gridObj.getDataProvider().getDescendants(index) != null){ //자식들이 있다면 추가
                var _deletingList = [];
                var convertList = function (items) {
                    items.forEach(function(n){
                        _deletingList.push(fnObj.gridView02.gridObj.getDataProvider().getJsonRow(n));
                        if(fnObj.gridView02.gridObj.getDataProvider().getChildren(n)){
                            convertList(fnObj.gridView02.gridObj.getDataProvider().getChildren(n));
                        }
                    })
                    return _deletingList;
                };

                deletingList = deletingList.concat(convertList(fnObj.gridView02.gridObj.gridView.getSelectedRows()));
            }else{
                deletingList.push(fnObj.gridView02.gridObj.getSelectedData());//최상위 노드 ADD
            }

            fnObj.gridView02.gridObj.getDataProvider().removeRows(fnObj.gridView02.gridObj.gridView.getSelectedRows(), false);
            // if(fnObj.gridView03.gridObj.getDataProvider().getDescendants(index) != null){
            //     fnObj.gridView03.gridObj.getDataProvider().removeRows(fnObj.gridView03.gridObj.getDataProvider().getDescendants(index), false);
            //
            // }
            fnObj.gridView02.gridObj.dispatch("onRemoveRow");
        }
    },
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


