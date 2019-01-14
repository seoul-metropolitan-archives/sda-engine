
var fnObj = {};
var parentsData;
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH_TREE: function (caller, act, data) {
        axboot.ajax({
            url: "/api/v1/st/st008/getAllNodes",
            data: $.extend({},data,{nodeType:"normal"}),
            callback: function (res) {
                fnObj.treeView01.setData({}, res.list, data);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH: function (caller, act, data) {
      axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st008/01/list01",
            async : false,
            data: $.extend({}, {pageSize: 10000},{aggregationUuid: data.uuid}),
            callback: function (res) {
                fnObj.gridView02.resetCurrent();
                fnObj.gridView02.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    ERROR_SEARCH: function (caller, act, data) {
    },
    PAGE_CONFIRM: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE,CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE,CANCEL_STATUS);
    },
    PAGE_SAVE : function (caller, act, data) {
        console.log('formViewData', data);
        /*if(fnObj.gridView03.getData().length  < 1){
            return;
        }
        var send = fnObj.gridView03.getData();

        for(var i=0;i<fnObj.gridView03.getJsonData().length;i++){
            send[i]['containerUuid'] = parentsData.containerUuid;
        }
*/

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/st/st008/01/save",
            data: JSON.stringify(data),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_CLOSE, data);
            },
            options: {
                onError: axboot.viewError
            }
        });

        return false;
    },
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.callback(data);
        }
    },
    PAGE_CLASSIFY: function (caller, act, data) {
        if(fnObj.gridView03.getData().length  < 1){
            return;
        }
        var send = fnObj.gridView03.getData();

        for(var i=0;i<fnObj.gridView03.getJsonData().length;i++){
            send[i]['containerUuid'] = parentsData.containerUuid;
        }

        axboot.ajax({
            type: "PUT",
            url: "/api/v1/st/st008/03/save",
            data: JSON.stringify(send),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_CLOSE,{classUuid:parentsData.classUuid});
            },
            options: {
                onError: axboot.viewError
            }
        });

        return false;
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

   /* $.ajax({
        url: "/assets/js/column_info/st00801_p01_01.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
     */

    _this.formView.initView();



    //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH_TREE, this.formView.getData());
    // ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.formView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, parentsData);
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

        this.initEvent();
    },
    initEvent: function () {
        var _this = this;

        $("input[data-ax-path='startDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='startDate']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='endDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='endDate']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='descriptionDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='descriptionDate']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });

        $("input[data-ax-path='startDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });
        $("input[data-ax-path='endDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });
        $("input[data-ax-path='descriptionDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });
        $(".btn_s").click(function() {
            //if (this.textContent == "Save") {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE, fnObj.formView.getData());
            //}
        });
        $(".close_popup").click(function(){
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
        });

        var accordion = {
            click: function(target) {
                var $target = $(target);
                $target.on('click', function() {

                    if ($(this).hasClass('on')) {
                        slideUp($target);
                    } else {
                        slideUp($target);
                        $(this).addClass('on').next().slideDown();
                    }

                    function slideUp($target) {
                        $target.removeClass('on').next().slideUp();
                    }

                });
            }
        };
        accordion.click('.accordion > ul');

        $("input[data-ax-path='employeeYn']").change(function(){

            if($(this).val() == "Y"){
                //직원

                //직원인경우 이름이 다시 자동셋팅 되어야 한다!?
                $("input[data-ax-path='department']").prop('readonly', true);
                $("input[data-ax-path='position']").prop('readonly', true);
                $("input[data-ax-path='name']").prop('readonly', true);
            }else{
                //기타
                $("input[data-ax-path='department']").prop('readonly', false);
                $("input[data-ax-path='position']").prop('readonly', false);
                $("input[data-ax-path='name']").prop('readonly', false);

            }
        });

        var emp = $("input[data-ax-path='employeeYn']")[0];
        $(emp).prop('checked',true)
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
    },
    expandAll:function(){
        fnObj.gridView01.gridObj.expandAll();
    },
    collapseAll:function() {
        fnObj.gridView01.gridObj.collapseAll();
    }
});

/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}
 */

isDataChanged = function () {
    if (fnObj.gridView01.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}
exportItemList = function() {
    if(fnObj.gridView02.gridObj.getCheckedList().length > 0){
        //gridView03 목록에 추가
        fnObj.gridView03.setData(fnObj.gridView02.gridObj.getCheckedList());
        // fnObj.gridView02.gridObj.onItemChecked()
        // var getCheckedRows = fnObj.gridView02.gridObj.getCheckedRows();
        // fnObj.gridView02.gridObj.setCheckable(0,false);
        // fnObj.gridView02.gridObj.getcheck
        // fnObj.gridView02.gridObj.setCustomCellStyleRows("disable",function(row){
        //
        //     if(row["statusUuid"] == state)
        //         return true;
        //     else
        //         return false;
        // },["containerName","parentContainerName","containerTypeUuid","controlNumber","provenance","creationStartDate","creationEndDate","description","orderNo"]);
    }else{
        alert("Select Arrange Item List")
    }
}
importItemList = function(){
    alert("import");
}

setCheckable = function(rows){
    // for()
}
