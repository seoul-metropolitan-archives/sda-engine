var fnObj = {};
var classificationSchemeUuid = "";
var jsonClassData;
var dataProvider;
var treeView;
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data){
        axboot.ajax({
            type: "GET",
            url: "/api/v1/cl002/01/getClassificationScheme",
            callback: function (res) {
                fnObj.formView.setFormData("classificationCode",res.map.classificationCode);
                fnObj.formView.setFormData("classificationName",res.map.classificationName);
                classificationSchemeUuid = res.map.classificationSchemeUuid;
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1);
            },
            options: {
                onError: axboot.viewError
            }
        });
        // axboot.ajax({
        //     type: "GET",
        //     url: "/api/v1/cl002/02/getClassList",
        //     async: true,
        //     data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
        //     callback: function (res) {
        //         fnObj.gridView01.setData(res.list);
        //     },
        //     options: {
        //         onError: axboot.viewError
        //     }
        // });
        // axboot.ajax({
        //     type: "GET",
        //     url: "/api/v1/cl002/03/getClassHierarchyList",
        //     async: true,
        //     data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
        //     callback: function (res) {
        //         fnObj.gridView02.setData(res.list);
        //     },
        //     options: {
        //         onError: axboot.viewError
        //     }
        // });
        // return false;
    },
    PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({
             type: "GET",
             url: "/api/v1/cl002/03/getClassHierarchyList",
             async: true,
             data: $.extend({}, {pageSize: 1000}, classificationSchemeUuid),
             callback: function (res) {
                 dataProvider = new RealGridJS.LocalDataProvider();

                 jsonClassData = res.list;
                ACTIONS.dispatch(fnObj.gridView01.setData(res.list));
             },
             options: {
                 onError: axboot.viewError
             }
        });
        axboot.ajax({
            type: "GET",
            url: "/api/v1/cl002/02/getClassList",
            async: true,
            data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
            callback: function (res) {
                if(res.list && res.list.length < 1)
                {
                    fnObj.gridView02.addRow();
                    return ;
                }

                fnObj.gridView02.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    /*PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/cl002/02/detail",
            data: $.extend({}, {pageSize: 1000}, data),
            callback: function (res) {
                /!*fnObj.formView.setFormData("managerOrganization",res.managerOrganization);
                fnObj.formView.setFormData("manager",res.manager);
                fnObj.formView.setFormData("basedOn",res.basedOn);
                fnObj.formView.setFormData("classificationNameTxt", selectedItem.classificationName);
                fnObj.formView.setFormData("useNo",res.useNo);
                fnObj.formView.setFormData("useYes",res.useYes);
                fnObj.formView.setFormData("aggregationCnt",res.aggregationCnt);
                fnObj.formView.setFormData("itemCnt",res.itemCnt);*!/
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },*/

    ERROR_SEARCH: function (caller, act, data) {
    },
    PAGE_CONFIRM: function (caller, act, data) {
    },
    PAGE_CANCEL: function (caller, act, data) {
    },
    PAGE_SAVE: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.TOP_GRID_SAVE);
        // ACTIONS.dispatch(ACTIONS.TOP_GRID_DETAIL_PAGE_SAVE);
    },
    TOP_GRID_SAVE: function (caller, act, data) {
        var result = false;
        axboot.call({
            type: "PUT",
            url: "/api/v1/cl002/03/saveList",
            data: JSON.stringify(this.gridView01.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                result = true;
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
        url: "/assets/js/column_info/cl00201.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/cl00202.js",
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
        return $.extend({}, axboot.formView.defaultData, {useYn: "Y"});
    },
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();
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
    expandAll:function(){
        treeView.expandAll();
    },
    collapseAll:function() {
        treeView.collapseAll();
    }
});


/*
fnObj.gridView01 = axboot.viewExtend({
    initView: function () {
    },
    setData: function (list) {
        dataProvider = new RealGridJS.LocalTreeDataProvider();
        treeView = new RealGridJS.TreeView("realgrid01");
        treeView.setDataSource(dataProvider);

        dataProvider.setFields([{
            fieldName: "orderKey"
        }, {
            fieldName: "className"
        }]);

        treeView.setColumns([{
            name: "className",
            fieldName: "className",
            width: 240,
            editable:false
        }]);

        treeView.setFooter({
            resizable: false,
            visible: false
        });

        treeView.setOptions({
            footer:{visible:false},
            header: { visible: false },
            checkBar: {visible: false},
            indicator: {visible: false},
            stateBar:{visible:false}
        });
        treeView.setDisplayOptions({
            fitStyle:"evenFill"
        });

        dataProvider.setRows(list,"orderKey");
        treeView.expandAll();
    }
});
*/

fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    initView: function () {
        this.gridObj = new GridWrapper("realgrid01", "/assets/js/libs/realgrid", true);
        this.gridObj.setIsTree(true).setGridStyle("100%", "100%")
        this.gridObj.setColumnInfo(cl00201.column_info).setEntityName("ClassName").makeGrid();

        this.gridObj.setOption({
            checkBar: {visible: true},
            indicator: {visible: true}
        });
        this.gridObj.getGridView().setFooter({
            resizable: false,
            visible: false,
        });
                //this.gridObj.itemClick(this.itemClick);
    },
    setData: function (list) {

        this.gridObj.setTreeData(list, "orderKey", "", "icon");
        this.gridObj.expandAll();
    },
    isChangeData: function () {
        return false;
    }
});

fnObj.gridView02 = axboot.viewExtend(axboot.realGridView, {
    tagId: "realgrid02",
    entityName: "Class Grid",
    initView: function () {
        this.setColumnInfo(cl00202.column_info);
        this.gridObj.setFixedOptions({
            colCount: 4
        });
        this.gridObj.setOption({
            checkBar: {visible: true},
            indicator: {visible: true}
        })
        this.makeGrid();
    },
    isChangeData: function () {
    },
    itemClick: function (data) {
    }
});
/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}
 */
/*
isDataChanged = function () {
    if (fnObj.gridView01.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}*/
