var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: ["modelExtractor", "tables"],
            data: $.extend({}, this.searchView.getData(), this.gridView01.getPageData()),
            callback: function (res) {

                res.forEach(function(table) {
                    table.tableName = '&nbsp;&nbsp;&nbsp;&nbsp;' + table.tableName.toUpperCase();
                    table.remarks = '&nbsp;&nbsp;&nbsp;&nbsp;' + table.remarks;
                })
                //console.log(res);
                caller.gridView01.setData(res);
            },
            options: {
                onError: function (err) {
                    console.log(err);
                }
            }
        });

        return false;
    },
    ITEM_CLICK: function (caller, act, data) {

        data.tableName = data.tableName.replace(/&nbsp;/gi, '');

        $('#table_detail').html("<a href='/devtools/model-extractor-detail?tableName=" + data.tableName + "&className=" + data.className +
            "' ><h3><i class='cqc-list'></i>" + data.tableName + " &nbsp;&nbsp;&nbsp;<i class='cqc-download'></i></h3></a>");

        axboot.ajax({
            type: "GET",
            url: ["modelExtractor", "table"],
            data: "tableName=" + data.tableName,
            callback: function (res) {
                res.columns.forEach(function(column) {
                    column.key = column.key == true ? 'true' : 'false';
                });
                caller.gridView02.setData(res.columns);
            },
            options: {
                onError: function (err) {
                    console.log(err);
                }
            }
        });
    },

    dispatch: function (caller, act, data) {
        var result = ACTIONS.exec(caller, act, data);
        if (result != "error") {
            return result;
        } else {
            // 직접코딩
            return false;
        }
    }
});

var CODE = {};

// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    _this.searchView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();

    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.pageResize = function () {

};

//== view 시작
/**
 * searchView
 */
fnObj.searchView = axboot.viewExtend(axboot.searchView, {
    initView: function () {
        this.target = $(document["searchView0"]);
        this.target.attr("onsubmit", "return ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);");
        this.filter = $("#filter");
    },
    getData: function () {
        return {
            filter: this.filter.val()
        }
    }
});

/**
 * gridView01
 */
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({
            showRowSelector: true,
            frozenColumnIndex: 0,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {key: "tableName", label: "테이블", width: 230, align: "left"},
                {key: "remarks", label: "코멘트", width: 230, align: "left"}
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex);

                    console.log('item :: ' + this.item);

                    ACTIONS.dispatch(ACTIONS.ITEM_CLICK, this.item);
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({pageNumber: pageNumber});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });

        axboot.buttonClick(this, "data-grid-view-01-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "delete": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_DEL);
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.key;
            });
        } else {
            list = _list;
        }
        return list;
    },
    addRow: function () {
        this.target.addRow({__created__: true}, "last");
    }
});

/**
 * gridView02
 */
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {

    initView: function () {

        var _this = this;

        this.target = axboot.gridBuilder({
            showLineNumber: true,
            showRowSelector: false,
            multipleSelect: false,
            target: $('[data-ax5grid="grid-view-02"]'),
            columns: [
                {key: "attributeName", label: "필드명", width: 200, align: "left"},
                {key: "columnName", label: "컬럼명", width: 200, align: "left", editor: "text"},
                {key: "humanReadableDataType", label: "데이터 타입", width: 190, align: "center", editor: "text"},
                {key: "remarks", label: "설명", width: 370, align: "center", editor: "text"},
                {key: "key", label: "주 키", width: 70, align: "center", editor: "text"}
            ],
            body: {
                onClick: function () {
                    //this.self.select(this.dindex);
                    //ACTIONS.dispatch(ACTIONS.ITEM_CLICK, this.list[this.dindex]);
                }
            }
        });

        axboot.buttonClick(this, "data-grid-view-02-btn", {
            "item-add": function () {
                this.addRow();
            },
            "item-remove": function () {
                this.delRow();
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.key;
            });
        } else {
            list = _list;
        }
        return list;
    },
    align: function () {
        this.target.align();
    }
});