var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {

        console.log('data: ', PARAMS)

        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/cash/sh03001110/m01",
            data: PARAMS,
            callback: function (res) {
                console.log(res);
                caller.formView01.setData(res);
                parent.axboot.modal.callback();
            },
            options: {
                onError: viewError
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
var PARAMS = {};
// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    _this.formView01.initView();

    PARAMS = {
        referDate:  $('[data-refer-date]').data('refer-date'),
        branchCode: $('[data-branch]').data('branch'),
        terminalNo: $('[data-terminal-no]').data('terminal-no')
    }

    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.pageResize = function () {
};

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
    },
    setData: function (data) {

        if (typeof data === "undefined") data = this.getDefaultData();
        data = $.extend({}, data);

        this.target.find('[data-ax-path="key"]').attr("readonly", "readonly");

        this.model.setModel(data);
        this.modelFormatter.formatting(); // 입력된 값을 포메팅 된 값으로 변경
    }
});

var viewError = function (err) {
    parent.axToast.confirm({
        theme: "danger",
        width: 300,
        lang: {
            "ok": "닫기"
        },
        icon: '<i class="cqc-new"></i>',
        msg: '[에러] ' + err.message
    });
}