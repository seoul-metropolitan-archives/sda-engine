var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {

    },
    PLAN_SAVE: function (caller, act, data) {
        if (caller.formView01.validate()) {
            var formData = caller.formView01.getData();
            var parentData = {
                jisaCode : PARAMS.jisaCode,
                jisaName : PARAMS.jisaName,
                branchCode : PARAMS.branchCode,
                branchName : PARAMS.branchName,
                cornerCode : PARAMS.cornerCode,
                cornerName : PARAMS.cornerName,
                overhaulDateFrom : formData.startDate,
                overhaulDateTo : formData.endDate,
                overhaulGubun : formData.overhaulGubun
            }
            axboot
                .call({
                    type: "PUT",
                    url: "/api/v1//mng/equip/overhaul_plan",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
                    }
                })
                .done(function () {
                    caller.formView01.clear();
                    axToast.push("저장 작업이 완료되었습니다.");
                    parent.axboot.modal.callback(parentData);
                    ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
                });
        }
    },
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.close();
        }
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
    _this.formView01.setSingleData("jisaName", parent.PARAMS.jisaName);
    _this.formView01.setSingleData("branchName", parent.PARAMS.branchName);
    _this.formView01.setSingleData("cornerName", parent.PARAMS.cornerName);
    _this.formView01.setSingleData("startDate", parent.PARAMS.startDate);
    _this.formView01.setSingleData("endDate", parent.PARAMS.endDate);

    PARAMS = {
        jisaCode: parent.PARAMS.jisaCode,
        jisaName :  parent.PARAMS.jisaName,
        branchCode: parent.PARAMS.branchCode,
        branchName :  parent.PARAMS.branchName,
        cornerCode: parent.PARAMS.cornerCode,
        cornerName :  parent.PARAMS.cornerName
    }
    //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
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
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        return $.extend({}, data);
    },
    setData: function (data) {

        if (typeof data === "undefined") data = this.getDefaultData();
        data = $.extend({}, data);

        this.target.find('[data-ax-path="key"]').attr("readonly", "readonly");

        this.model.setModel(data);
        this.modelFormatter.formatting(); // 입력된 값을 포메팅 된 값으로 변경
    },
    setSingleData: function (dataPath, value) {
        this.model.set(dataPath, value);
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


$('#form-cancel').on('click', function () {
    ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
});


$('#form-save').on('click', function () {
    ACTIONS.dispatch(ACTIONS.PLAN_SAVE);
});
