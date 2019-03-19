
var fnObj = {};
var parentsData;
var repositoryUuid;
var shelfUuid;
var locationUuid;
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
        data = fnObj.formView.getData();
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
            if(parent.axboot.modalOpener == "modal")
                parent.axboot.modal.callback(data);
            else if(parent.axboot.modalOpener == "commonModal")
                parent.axboot.commonModal.callback(data);
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
    if(parent.axboot.modalOpener == "modal")
        parentsData = parent.axboot.modal.getData();
    else if(parent.axboot.modalOpener == "commonModal")
        parentsData = parent.axboot.commonModal.getData();

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

function getFormattedDate(date, isStart) {
    var day;
    var tempDate;
    if (isStart) {
        date.setDate(date.getDate() - 10);
        tempDate = date.getDate();
    } else {
        tempDate = date.getDate();
    }
    day = tempDate.toString();

    var year = date.getFullYear();
    var month = (1 + date.getMonth()).toString();
    month = month.length > 1 ? month : '0' + month;
    day = day.length > 1 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}
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

        if(this.model.model.takeoutRequestUuid){
            // 값이 있다면
            // 수정이다.
            fnObj.formView.setFormData("takeoutDate",getFormattedDate(new Date(this.model.model.takeoutDate))); //  반출일자
            // fnObj.formView.setFormData("returnDate",getFormattedDate(new Date())); //  반입일
            fnObj.formView.setFormData("returnDueDate",getFormattedDate(new Date(this.model.model.returnDueDate))); //  반입예정일
        }else{
            fnObj.formView.setFormData("takeoutDate",getFormattedDate(new Date())); //  반출일자
            // fnObj.formView.setFormData("returnDate",getFormattedDate(new Date())); //  반입일
            fnObj.formView.setFormData("returnDueDate",getFormattedDate(new Date())); //  반입예정일
        }

      /*  $("input[data-ax-path='startFromDate']").val(getFormattedDate(new Date(), true));
        $("input[data-ax-path='startToDate']").val(getFormattedDate(new Date()));*/

        this.makeRadio();
        this.initEvent();
    },
    initEvent: function () {
        var _this = this;

        $("input[data-ax-path='takeoutDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='takeoutDate']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='returnDueDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='returnDueDate']").keypress(function () {
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

        $("input[data-ax-path='takeoutDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });
        $("input[data-ax-path='returnDueDate']").focusout(function () {
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
             if (this.textContent == "Save") {
                 ACTIONS.dispatch(ACTIONS.PAGE_SAVE, fnObj.formView.getData());
             }
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
    },
    changeView: function (requestTypeUuid) {

        if( requestTypeUuid == 'AF04136D-3508-4E1C-A85B-F1A1FEDDB607' || requestTypeUuid == null || requestTypeUuid == undefined){
            // 직원
            $('#innerWorker').show();
            $('#outerWorker').hide();
            console.log('내부직원');
        }else{
            // 외부
            $('#innerWorker').hide();
            $('#outerWorker').show();
            console.log('외부직원');
        }
      /*  fnObj.formView.clear();
        // $(".auth_fit").remove();
        fnObj.childrenAuthInfo.addChild($("#addDnrInfo"));

        selectedRadio = $('input:radio[name="radio"]:checked').attr("id");
        selectedLabel = $("label[for='"+selectedRadio+"']").text();
        authorityTypeNm = selectedLabel;
        if(selectedLabel == "Organization"){
            $("#organizationArea").show();
            $("#etcArea").hide();
        }else{
            $("#organizationArea").hide();
            $("#etcArea").show();
        }

         ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);*/
    },
    makeRadio: function() {
        var codes = axboot.commonCodeFilter("CD209").codeArr;
        var names = axboot.commonCodeFilter("CD209").nameArr;

        var radioTag = "";
        for(var i = 0; i < codes.length; i++)
        {
            if(parent.axboot.modal.getData().takeoutRequestUuid){
                // 수정
                var checked ='';
                if( codes[i] == parentsData.requestTypeUuid){
                    // 외부, 내부 직원인지에따라 checked
                    checked = 'checked="checked"';
                }
                radioTag += '<label for="authType' + i + '"><input type="radio" id="authType' + i + '"  name="radio" class="no_border" value="' + codes[i] + '" '+ checked +'>' + names[i] + '</label>'
            }else{
                // 새로 작성
                var checked ='';
                if( i == 0){
                    // 첫번째 애를 기본선택
                    checked = 'checked="checked"';
                }
                radioTag += '<label for="authType' + i + '"><input type="radio" id="authType' + i + '" name="radio" class="no_border" value="' + codes[i] + '" '+ checked +'>' + names[i] + '</label>'
            }


        }

        $(".rdo_box").append(radioTag);
        // 최초 한번 호출
        fnObj.formView.changeView( parentsData.requestTypeUuid );
        // 라디오 이벤트를 걸어주자.
        $('.rdo_box input[type=radio]').change(function(){
            var requestTypeUuid = getRequestTypeUuidFromRadio();
            fnObj.formView.changeView(requestTypeUuid);
        })

    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.

        // 직원구분 넣어주자.
        data.requestTypeUuid = getRequestTypeUuidFromRadio();
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

/**
 * 직원 구분
 * @returns uuid
 */
function getRequestTypeUuidFromRadio(){
    var selected = $(".rdo_box input[type='radio']:checked");
    if (selected.length > 0) {
        var selectedVal = selected.val();
        return selectedVal;
    }
    return null;
}
function checkDate(date) {
    var result = true;
    var strValue = date;
    var chk1 = /^(19|20)\d{2}-([1-9]|1[012])-([1-9]|[12][0-9]|3[01])$/;
    //var chk2 = /^(19|20)\d{2}\/([0][1-9]|1[012])\/(0[1-9]|[12][0-9]|3[01])$/;
    var chk2 = /^(19|20)\d{2}-([0][1-9]|1[012])-([012][1-9]|3[01])$/;
    if (strValue == "") { // 공백이면 무시
        return result;
    }
//-------------------------------------------------------------------------------
// 유효성 검사- 입력형식에 맞게 들왔는지 // 예) 2000-1-1, 2000-01-01 2가지 형태 지원
//-------------------------------------------------------------------------------
    if (chk1.test(strValue) == false && chk2.test(strValue) == false) { // 유효성 검사에 둘다 성공하지 못했다면
        //alert("1999-1-1 형식 또는 \r\n1999-01-01 형식으로 날자를 입력해주세요.");
        axToast.push(axboot.getCommonMessage("AA011"));
        result = false;

    }
    return result;
}
function getFormattedDate(date, isStart) {
    var day;
    var tempDate;
    if (isStart) {
        date.setDate(date.getDate() - 10);
        tempDate = date.getDate();
    } else {
        tempDate = date.getDate();
    }
    day = tempDate.toString();

    var year = date.getFullYear();
    var month = (1 + date.getMonth()).toString();
    month = month.length > 1 ? month : '0' + month;
    day = day.length > 1 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}
