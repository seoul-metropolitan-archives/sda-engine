var fnObj = {};
var gridView = undefined;


var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ad/ad006/searchEntityType",
            data: $.extend({}, {pageSize: 1000}, fnObj.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.clearChild();
                if(res.list.length > 0)
                    ACTIONS.dispatch(ACTIONS.GET_ENTITY_DETAIL, res.list[0]);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    GET_ENTITY_DETAIL: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/ad/ad006/getEntityColumnList",
            data: $.extend({}, {pageSize: 1000}, data),
            callback: function (res) {
                fnObj.gridView02.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        /*필수값 체크오류로 주석처리
                if(
                    !fnObj.gridView01.validate()
                    || !fnObj.gridView02.validate()
                )
                    return ;
        */
        var entityTypeList = [].concat(fnObj.gridView01.getData());
        var entityColumnList = [].concat(fnObj.gridView02.getData());

        axboot
            .call({
                url: "/api/v1/ad/ad006/saveEntityType",
                type: "post",
                data: JSON.stringify(entityTypeList),
                callback: function (res) {
                    fnObj.gridView01.commit();
                }
            })
            .call({
                url: "/api/v1/ad/ad006/saveEntityColumn",
                type: "post",
                data: JSON.stringify(entityColumnList),
                callback: function (res) {
                    fnObj.gridView02.commit();
                    ACTIONS.dispatch(ACTIONS.GET_ENTITY_DETAIL, fnObj.gridView01.getRowData());
                }
            })
            .done(function () {
                axToast.push(axboot.getCommonMessage("AA007"));
            });
    },
    /*
    ENTITY_TYPE_PAGE_SAVE: function (caller, act, data) {
        var _this = this;

        var result = false;
        console.log(fnObj.gridView01.getData());
        axboot.ajax({
            url: "/api/v1/ad/ad006/saveEntityType",
            type: "post",
            async: false,
            data: JSON.stringify(fnObj.gridView01.getData()),
            callback: function (res) {
                result = res;
                if (result) {
                    try {
                        fnObj.gridView01.gridObj.commit();
                    } catch (e) {
                        console.log("커밋 에러남");
                    }

                }

            },
            options: {
                onError: axboot.viewError
            }
        });
        if (!result)
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH, fnObj.formView.getData());

        return result;
    },
    ENTITY_COLUMN_PAGE_SAVE: function (data) {
        var result = false;
        axboot.ajax({
            url: "/api/v1/ad/ad006/saveEntityColumn",
            type: "post",
            async: false,
            data: JSON.stringify(fnObj.gridView02.getData()),
            callback: function (res) {
                result = res;
                try {
                    result = true;
                    fnObj.gridView02.gridObj.commit();
                } catch (e) {
                    console.log("커밋 에러남");
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
        if (!result) {
            ACTIONS.dispatch(ACTIONS.GET_ENTITY_DETAIL, fnObj.gridView01.gridObj.getSelectedData());
        }


        return result;
    },
    */
    FORM_CLEAR: function (caller, act, data) {
    },
    CLOSE_TAB: function () {
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
    pageStart: function () {
        var _this = this;
        $.ajax({
            url: "/assets/js/column_info/ad00601.js",
            dataType: "script",
            async: false,
            success: function () {
            }
        });
        $.ajax({
            url: "/assets/js/column_info/ad00602.js",
            dataType: "script",
            async: false,
            success: function () {
            }
        });

        _this.formView.initView();
        _this.gridView01.initView();
        _this.gridView02.initView();

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH, this.formView.getData());

    }
};
/*검색 창*/
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
    }

    , initEvent: function () {
        $("input").keydown(function (event) {
            switch (event.keyCode) {
                case 40:
                    fnObj.gridView01.gridObj.setFocus();
                    break;
            }
        })
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        console.log($.extend({}, data));
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

/*엔티티 헤더*/
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid1",
    entityName: "ENTITY_HEADER",
    primaryKey: "entityTypeUuid",
    initView: function () {
        var _this = this;
        this.initInstance();
        this.setColumnInfo(ad00601.column_info);
        /*
        this.gridObj.setOption({
            checkBar : {visible : true}
        })
        */
        this.makeGrid();
        this.addRowAfterEvent(this.clearChild);
        this.removeRowAfterEvent(this.clearChild);
        this.gridObj.setFixedOptions({
            colCount: 2
        })

        this.gridObj.itemClick(function (data) {
            if (fnObj.gridView02.getData().length < 1) {
                fnObj.formView.setFormData("entityTypeCode", data.entityType);
                ACTIONS.dispatch(ACTIONS.GET_ENTITY_DETAIL, data);
            }
            else {
                axDialog.confirm({
                    msg: "변경사항이 있습니다. 저장하시겠습니까?"
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    }
                    ACTIONS.dispatch(ACTIONS.GET_ENTITY_DETAIL, data);
                });
            }
        });
    },
    clearChild: function () {
        fnObj.gridView02.gridObj.gridView.cancel();
        fnObj.gridView02.clearData();
    },
    getRowData: function (){
        return this.gridObj.getSelectedData();
    },
    getEntityTypeHeaderUUID: function () {
        return this.gridObj.getSelectedData()["entityTypeUuid"];
    }
});
/*엔티티 디테일 ( Column )*/
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid2",
    entityName: "ENTITY_DETAIL",
    primaryKey: "entityColumnUuid",
    parentsUuidFieldName: "entityTypeUuid",
    parentsGrid: fnObj.gridView01,
    initView: function () {
        this.initInstance();
        console.log(ad00602.column_info);
        this.setColumnInfo(ad00602.column_info);
        this.makeGrid();
        this.gridObj.addRowBeforeEvent(this.addRowBeforeEvent);
        this.gridObj.setFixedOptions({
            colCount: 1
        })
    }
});
