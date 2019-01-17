
var fnObj = {};
var parentsData;
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function(caller,act,data) {
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    PAGE_CONFIRM: function (caller, act, data) {fnObj.formView.getData()
    },
    PAGE_CANCEL: function (caller, act, data) {
    },
    PAGE_SAVE: function (caller, act, data) {
        $.ajax({
            url: "/api/v1/ad/ad010/02/save",
            contentType: "application/json",
            type:"PUT",
            data: JSON.stringify(fnObj.formView.getData()),
            success : function (res) {
                axToast.push(axboot.getCommonMessage("AA007"));
                parent.axboot.modal.callback();
            },
            error: function (res) {

            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_DELETE: function (caller, act, data) {
        $.ajax({
            url: "/api/v1/ad/ad010/02/delete",
            contentType: "application/json",
            type:"PUT",
            data: JSON.stringify(fnObj.formView.getData()),
            success : function (res) {
                axToast.push(axboot.getCommonMessage("AA007"));
                parent.axboot.modal.callback();
                ACTIONS.dispatch(ACTIONS.CLOSE);
            },
            error: function (res) {

            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    CLOSE : function(caller, act, data){
        if (parent && parent.axboot && parent.axboot.modal) {
            parent.axboot.modal.close();
        }
    },
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.callback(data);
        }
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
    parentsData = parent.axboot.modal.getData();

    $.ajax({
        url: "/assets/js/controller/simple_controller.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();

    if(parentsData != null && parentsData != undefined) {
        fnObj.formView.setData(parentsData);
        if(sessionJson.userUuid != parentsData.registerUuid){
            $("input[data-ax-path='title']").attr("readonly",true);
            $("textarea[data-ax-path='contents']").attr("readonly",true);
            $("input[data-ax-path='fileName']").attr("readonly",true);
            $(".btn_s").attr("disabled",true);
            $("#adminBtn_s").hide();
            $("#adminBtn_d").hide();
        }else{
            $("#adminBtn_s").show();
            $("#adminBtn_d").show();
        }
    }else{
        fnObj.formView.setFormData("registerName",sessionJson.userNm);
        $("#adminBtn_s").show();
        $("#adminBtn_d").hide();
    }
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
    },
    initEvent: function () {
        $(".btn_s").click(function(){
           ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
        });
        $(".btn_d").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_DELETE);
        });
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        return $.extend({}, data);
    },
    setFormData: function (dataPath, value) {
        this.model.set(dataPath, value);
    },
    getFormData: function (dataPath) {
        return this.model.get(dataPath);
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

