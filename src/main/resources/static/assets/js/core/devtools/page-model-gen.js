var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_GEN: function (caller, act, data) {

        var options = $('[data-ax5select="select1"]').ax5select("getValue");
        var table = options[0].optionValue;
        var name = $("#name").val();
        var path = $("#path").val();
        var templateCode = $("#tableName").attr("template-code");

        console.log('templateCode', templateCode);

        axboot.ajax({
            type: "GET",
            url: "/api/v1/page-gen/all-set",
            data: {name: name, path: path, table: table, templateCode: templateCode},
            callback: function (res) {
                parent.axToast.push("페이지+모델 작성 작업이 완료되었습니다.");
                parent.axboot.modal.close();
            },
            options: {
                onError: function (err) {
                    parent.axToast.confirm({
                        theme: "danger",
                        width: 300,
                        lang: {
                            "ok": "닫기"
                        },
                        icon: '<i class="cqc-new"></i>',
                        msg: '[ERROR MESSAGE]\n' + err.message
                    });
                }
            }
        });

        return false;
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
    _this.pageButtonView.initView();
    _this.formView01.initView();
};

fnObj.pageResize = function () {
};

fnObj.pageButtonView = axboot.viewExtend({
    initView: function () {
        axboot.buttonClick(this, "data-page-btn", {
            "exec": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_GEN);
            }
        });
    }
});

/**
 * formView01
 */
fnObj.formView01 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {});
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
        $('[data-ax5select]').ax5select({
            columnKeys: {
                optionValue: "optionValue", optionText: "optionText"
            }
        });
        axboot.layoutResize(1);
    },
    getData: function () {
        console.log('getData', this.model.get());
        console.log('this.model', this.model);
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        return $.extend({}, data);
    }
});