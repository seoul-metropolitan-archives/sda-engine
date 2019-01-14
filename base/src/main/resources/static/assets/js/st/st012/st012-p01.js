var fnObj = {};
var parentsData;
var disposerUuid;

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH_TREE: function (caller, act, data) {
        axboot.ajax({
            url: "/api/v1/st/st012/getAllNodes",
            data: $.extend({}, data, {nodeType: "normal"}),
            callback: function (res) {
                fnObj.treeView01.setData({}, res.list, data);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH01: function (caller, act, data) {


        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st014/01/list02",
            async: false,
            data: $.extend({}, this.formView.getData(), {withoutNoticeIoRecordUuid: parentsData[0].withoutNoticeIoRecordUuid}),
            callback: function (res) {
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
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE, CONFIRM_STATUS);
    },
    PAGE_CANCEL: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.STATUS_UPDATE, CANCEL_STATUS);
    },
    PAGE_SAVE: function (caller, act, data) {
        console.log('formViewData', data);
        /*if(fnObj.gridView03.getData().length  < 1){
            return;
        }
        var send = fnObj.gridView03.getData();

        for(var i=0;i<fnObj.gridView03.getJsonData().length;i++){
            send[i]['containerUuid'] = parentsData.containerUuid;
        }
 */


        data = fnObj.formView.getData();

        if( disposerUuid == undefined ){
            alert('조치자가 없습니다.');
            return;
        }
        if( data.reason == undefined ){
            alert('사유가 없습니다.')
            return;
        }
        if( data.disposalDate == undefined ){
            alert('조치일이 없습니다.')
            return;
        }

        // data 가 array 로 옴.
        for (var i = 0; i < parentsData.length; i++) {
            parentsData[i].disposerUuid = disposerUuid;
            parentsData[i].reason = data.reason;
            parentsData[i].disposalDate = data.disposalDate;
        }


        axboot.ajax({
            type: "PUT",
            url: "/api/v1/st/st012/01/save01",
            data: JSON.stringify(parentsData),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_CLOSE, parentsData);
            },
            options: {
                onError: axboot.viewError
            }
        });

        return false;
    },
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            if (parent.axboot.modalOpener == "modal")
                parent.axboot.modal.callback(data);
            else if (parent.axboot.modalOpener == "commonModal")
                parent.axboot.commonModal.callback(data);
        }
    },

    SEARCH_DISPOSER_SCH: function (caller, act, data) {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch: data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {

                $("input[data-ax-path='disposerName']").val(data['USER_NAME'])
                disposerUuid = data['USER_UUID'];
                console.log('disposerUuid', disposerUuid);
                if (this.close) this.close();
            }
        });
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
    if (parent.axboot.modalOpener == "modal")
        parentsData = parent.axboot.modal.getData();
    else if (parent.axboot.modalOpener == "commonModal")
        parentsData = parent.axboot.commonModal.getData();

    $.ajax({
        url: "/assets/js/controller/simple_controller.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/st01202.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView02.initView();
    if (parentsData.length == 1) {
        // single select
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01, this.formView.getData());
    } else {
        // multiple select 라면 zone grid 숨기기
        $("#realgrid02").hide();
    }

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

        // if (this.model.model.takeoutRequestUuid) {
        //     // 값이 있다면
        //     // 수정이다.
        //     fnObj.formView.setFormData("takeoutDate", getFormattedDate(new Date(this.model.model.takeoutDate))); //  반출일자
        //     // fnObj.formView.setFormData("returnDate",getFormattedDate(new Date())); //  반입일
        //     fnObj.formView.setFormData("returnDueDate", getFormattedDate(new Date(this.model.model.returnDueDate))); //  반입예정일
        // } else {
        //     fnObj.formView.setFormData("takeoutDate", getFormattedDate(new Date())); //  반출일자
        //     // fnObj.formView.setFormData("returnDate",getFormattedDate(new Date())); //  반입일
        //     fnObj.formView.setFormData("returnDueDate", getFormattedDate(new Date())); //  반입예정일
        // }

        /*  $("input[data-ax-path='startFromDate']").val(getFormattedDate(new Date(), true));
          $("input[data-ax-path='startToDate']").val(getFormattedDate(new Date()));*/

        // this.makeRadio();
        fnObj.formView.setFormData("disposalDate", getFormattedDate(new Date()));
        if (parentsData.length == 1) {
            // 한개만 선택한 데이터라면,

            fnObj.formView.setFormData("code", parentsData[0].code);
            fnObj.formView.setFormData("title", parentsData[0].title);
            fnObj.formView.setFormData("containerInfo", `${parentsData[0].repositoryName}/${parentsData[0].shelfName}/${parentsData[0].locationName}`);
        }


        this.initEvent();
    },
    initEvent: function () {
        var _this = this;

        $("input[data-ax-path='disposerName']").parents().eq(1).find("a").click(function () {

            var data = {
                popupCode: "PU107",
                searchData: null,
                preSearch: false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_DISPOSER_SCH, data);

        });

        $("input[data-ax-path='disposalDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='disposalDate']").keypress(function () {
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

        $("input[data-ax-path='disposalDate']").focusout(function () {
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
        // $(".btn_s").click(function () {
        //     //if (this.textContent == "Save") {
        //     ACTIONS.dispatch(ACTIONS.PAGE_SAVE, fnObj.formView.getData());
        //     //}
        // });
        $(".close_popup").click(function () {
            ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
        });

        var accordion = {
            click: function (target) {
                var $target = $(target);
                $target.on('click', function () {

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
    changeView: function () {
        var requestTypeUuid = getRequestTypeUuidFromRadio();
        if (requestTypeUuid == 'AF04136D-3508-4E1C-A85B-F1A1FEDDB607') {
            // 직원
            $('#innerWorker').show();
            $('#outerWorker').hide();
            console.log('내부직원');
        } else {
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
    makeRadio: function () {
        var codes = axboot.commonCodeFilter("CD209").codeArr;
        var names = axboot.commonCodeFilter("CD209").nameArr;

        var radioTag = "";
        for (var i = 0; i < codes.length; i++) {
            if (codes[i] == parent.axboot.modal.getData().takeoutRequestUuid) {
                // 수정
                radioTag += '<label for="authType' + i + '"><input type="radio" id="authType' + i + '"  name="radio" class="no_border" value="' + codes[i] + '" checked="checked">' + names[i] + '</label>'
            } else {
                // 새로 작성
                var checked = '';
                if (i == 0) {
                    // 첫번째 애를 기본선택
                    checked = 'checked="checked"';
                }
                radioTag += '<label for="authType' + i + '"><input type="radio" id="authType' + i + '" name="radio" class="no_border" value="' + codes[i] + '" ' + checked + '>' + names[i] + '</label>'
            }


        }

        $(".rdo_box").append(radioTag);
        // 최초 한번 호출
        fnObj.formView.changeView();
        // 라디오 이벤트를 걸어주자.
        $('.rdo_box input[type=radio]').change(function () {
            fnObj.formView.changeView();
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
    expandAll: function () {
        fnObj.gridView01.gridObj.expandAll();
    },
    collapseAll: function () {
        fnObj.gridView01.gridObj.collapseAll();
    }
});

fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid02",
    primaryKey: "withoutNoticeIoHistUuid",
    uuidFieldName: "withoutNoticeIoHistUuid", //EXCEPT_RECORD_RESULT_UUID
    entityName: "ST_WITHOUT_NOTICE_INOUT_HIST",
    parentsUuidFieldName: "withoutNoticeIoRecordUuid",
    // parentsGrid: fnObj.gridView01,
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st01202.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true}
        })
        this.makeGrid();
        //this.gridObj.itemClick(this.itemClick);
        // this.removeRowBeforeEvent(this.cancelDelete);
    },
    getSelectedData: function () {
        return this.gridObj.getSelectedData()
    },
    disabledColumn: function () {
        var state = axboot.commonCodeValueByCodeName("CD138", CONFIRM_STATUS);
        this.gridObj.setCustomCellStyleRows("disable", function (row) {
            if (row["statusUuid"] == state)
                return true;
            else if (fnObj.gridView02.getSelectedData().useYn == "Y")
                return true;
            else return false;
        }, function (row) {
            if (row["statusUuid"] == state) {
                return ["statusUuid", "shelfCode", "shelfName", "maxContainer", "description"];
            } else {
                return ["useYn"];
            }
        });
    },
    itemClick: function (data) {
        /*if (fnObj.gridView03.isChangeData() == true) {
            axDialog.confirm({
                msg: axboot.getCommonMessage("AA006")
            }, function () {
                if (this.key == "ok") {
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                } else {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
                }
            });
        } else {

            var codes = axboot.commonCodeFilter("CD138").codeArr;
            var names = axboot.commonCodeFilter("CD138").nameArr;
            var state = undefined;
            for (var i = 0; i < codes.length; i++) {
                if (codes[i] == fnObj.gridView02.getSelectedData().statusUuid) {
                    state = names[i];
                    break;
                }
            }

            //confirm confirm
            if(state == CONFIRM_STATUS){
                if(fnObj.gridView02.getSelectedData().useYn == "Y"){
                    fnObj.gridView03.gridObj.setRunAdd(true);
                }else{
                    fnObj.gridView03.gridObj.setRunAdd(false);
                }
            }else{
                fnObj.gridView03.gridObj.setRunAdd(false);
            }


            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
        }*/
    },
    cancelDelete: function () {
        /*var codes = axboot.commonCodeFilter("CD138").codeArr;
        var names = axboot.commonCodeFilter("CD138").nameArr;
        var state = undefined;
        for (var i = 0; i < codes.length; i++) {
            if (codes[i] == fnObj.gridView02.getSelectedData().statusUuid) {
                state = names[i];
                break;
            }
        }

        if(state == CONFIRM_STATUS){
            axToast.push(axboot.getCommonMessage("DF001_01"));

            this.setRunDel(false);
        }else{
            this.setRunDel(true);
        }*/
    },
});
/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}
 */

isDataChanged = function () {
    // if (fnObj.gridView01.isChangeData() == true) {
    //     return true;
    // } else {
    //     return false;
    // }
    return true;
}
exportItemList = function () {
    if (fnObj.gridView02.gridObj.getCheckedList().length > 0) {
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
    } else {
        alert("Select Arrange Item List")
    }
}
importItemList = function () {
    alert("import");
}

setCheckable = function (rows) {
    // for()
}

/**
 * 직원 구분
 * @returns uuid
 */
function getRequestTypeUuidFromRadio() {
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

