var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {

    ITEM_CLICK: function (caller, act, data) {
        //console.log("this.gridView01.getList", this.gridView01.getList());
    },
    GENERATE: function (caller, act, data) {
        var filteredData = data.filter(function(codeObj) {
            if (codeObj.__selected__) return codeObj.code;
        })
        var templateTypes = filteredData.map(function(data) {
            return data.code;
        });

        templateTypes = templateTypes.join(", ");

        var tableName = $("#tableName").text();
        var className = $("[data-grid-view-01-btn]").val();

        axDialog.prompt({
            theme: 'primary',
            width: 300,
            title: '[Model Extractor] Code Generator',
            input: {
                data1: {label:"Package Name", value: 'simple'},
                data2: {label:"Class Name", value: className}
            }
        }, function () {
            if(this.key == 'ok') {
                axboot.ajax({
                    type: "GET",
                    url: ["modelExtractor", "gen_code"],
                    data: {packageName: this.data1, className: this.data2, templateTypes: templateTypes, tableName: tableName},
                    callback: function (res) {
                        console.log(res);
                        axToast.push("코드 작성 작업이 완료되었습니다.");
                    },
                    options: {
                        onError: function (err) {
                            console.log(err);
                            axToast.confirm({
                                theme: "danger",
                                width: 300,
                                lang: {
                                    "ok": "닫기"
                                },
                                icon: '<i class="cqc-new"></i>',
                                msg: '[Error] ' + err.message
                            });
                        }
                    }
                });
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
    _this.gridView01.initView();
    //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);

    $(document.body).find("pre").addClass("prettyprint linenums lang-jsp");
    if (window["prettyPrint"]) window["prettyPrint"]();

};

fnObj.pageResize = function () {

};

//== view 시작
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
            multipleSelect: true,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {key: "code", label: "Code", width: 205, align: "center"}
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex);
                    ACTIONS.dispatch(ACTIONS.ITEM_CLICK, this.item);
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({pageNumber: pageNumber});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });

        var list = [];

        list.push({code: "Controller"});
        list.push({code: "Entity"});
        list.push({code: "VO"});
        list.push({code: "Service"});
        list.push({code: "Repository"});
        list.push({code: "MyBatisInterface"});
        list.push({code: "MyBatisXML"});

        this.target.setData(list);
        this.target.selectAll({selected: true});

        axboot.buttonClick(this, "data-grid-view-01-btn", {
            "generate": function () {
                ACTIONS.dispatch(ACTIONS.GENERATE, this.target.getList());
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
