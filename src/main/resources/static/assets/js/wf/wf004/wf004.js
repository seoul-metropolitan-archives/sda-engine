var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    // WorkflowResult 정보 조회
    PAGE_SEARCH: function (caller, act, data) {
        //TODO 삭제바람
        alert("startFromDate :" + $("#startFromDate").val() + " ,\n" + "startToDate :" + $("#startToDate").val() + " ,\n" + "endFromDate :" + $("#endFromDate").val() + " ,\n" + "endToDate :" + $("#endToDate").val());


        this.formView.checkPopup();

        axboot.ajax({
            type: "GET",
            url: "/api/v1/wf004/01/list",
            data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);

                if (res.list.length > 0) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, res.list[0]);
                } else {
                    fnObj.gridView02.clearData();
                    fnObj.gridView03.clearData();
                }

            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    // JobResult 정보 조회
    PAGE_SEARCH1: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/wf004/02/list",
            data: $.extend({}, {pageSize: 1000}, data),
            callback: function (res) {
                fnObj.gridView02.setData(res.list);

                if (res.list.length > 0) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2, res.list[0]);
                } else {
                    fnObj.gridView03.clearData();
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    // ParameterResult 정보 조회
    PAGE_SEARCH2: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/wf004/03/list",
            data: $.extend({}, {pageSize: 1000}, data),
            callback: function (res) {
                fnObj.gridView03.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        var workflowList = [].concat(fnObj.gridView01.getData());
        var jobList = [].concat(fnObj.gridView02.getData());
        var parameterList = [].concat(fnObj.gridView03.getData());

        axboot
            .call({
                type: "PUT",
                url: "/api/v1/wf004/01/save",
                data: JSON.stringify(workflowList),
                callback: function (res) {
                    fnObj.gridView01.gridObj.commit();
                }
            })
            .call({
                type: "PUT",
                url: "/api/v1/wf004/02/save",
                data: JSON.stringify(jobList),
                callback: function (res) {
                    fnObj.gridView02.gridObj.commit();
                }
            })
            .call({
                type: "PUT",
                url: "/api/v1/wf004/03/save",
                data: JSON.stringify(parameterList),
                callback: function (res) {
                    fnObj.gridView03.gridObj.commit();
                }
            })
            .done(function () {
                //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push(axboot.getCommonMessage("AA007"));
            });
    },
    FORM_CLEAR: function (caller, act, data) {
        /*
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                caller.formView01.clear();
                caller.gridView02.clear();
            }
        });
        */
    },
    ITEM_CLICK: function (caller, act, data) {
    },
    SEARCH_POPUP_EXECUTER: function (caller, act, data) {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch: data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='executer']").val(data["USER_NAME"])
                $("input[data-ax-path='executer']").attr("executer", data["USER_UUID"])

                if (this.close)
                    this.close();
            }
        });
    },
    SEARCH_POPUP_MENU: function (caller, act, data) {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch: data["preSearch"],
            async: false,
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='menu']").val(data["MENU_NAME"])
                $("input[data-ax-path='menu']").attr("menu", data["MENU_UUID"])

                if (this.close)
                    this.close();
            }
        });
    },
    PASSWORD_CHANGE_POPUP: function (caller, act, data) {
        var promptDialog = new ax5.ui.dialog();

        promptDialog.prompt({
            title: "Password Change",
            msg: 'Please fill new password'
        }, function (data) {
            console.log(this);
            if (this.key == "ok") {
                this.value;
            }
        });

    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
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


// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    //TODO 추후에 삭제될 내용으로 /실제 Grid의 컬럼 정보는 DB에서 가져올 예정
    $.ajax({
        url: "/assets/js/column_info/wf00401.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/wf00402.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/wf00403.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();
    _this.gridView03.initView();

    // Data 조회
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH, this.formView.getData());

    //TODO 개발하고 지워야함
    axWarningToast.push("개발이 진행중인 화면입니다.");

};

fnObj.formView = axboot.viewExtend(axboot.formView, {
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel({}, this.target);
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
        $("input[data-ax-path='executer']").parents().eq(1).find("a").click(function () {
            var data = {
                popupCode: "PU107",
                searchData: $("input[data-ax-path='executer']").val().trim(),
                preSearch: false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_POPUP_EXECUTER, data);
        });
        /*$("input[data-ax-path='executer']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU107",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_POPUP_EXECUTER,data);
            }
            else
                $("input[data-ax-path='executer']").attr("executer","")
        });*/
        $("input[data-ax-path='menu']").parents().eq(1).find("a").click(function () {
            var data = {
                popupCode: "PU126",
                searchData: $("input[data-ax-path='menu']").val().trim(),
                preSearch: false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_POPUP_MENU, data);
        });
        /*$("input[data-ax-path='menu']").focusout(function(){

            if("" != $(this).val().trim())
            {
                var data = {
                    popupCode : "PU126",
                    searchData : $(this).val().trim()
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_POPUP_MENU,data);
            }
            else
                $("input[data-ax-path='menu']").attr("menu","")
        });*/
    },
    checkPopup: function () {
        var checkData = $("input[data-ax-path='executer']").val();
        var data = {};
        if (checkData.trim() != "") {
            data = {
                popupCode: "PU107",
                searchData: checkData.trim()
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_POPUP_EXECUTER, data);
        }
        else {
            $("input[data-ax-path='executer']").attr("executer", "")
        }

        var checkData = $("input[data-ax-path='menu']").val();
        if (checkData.trim() != "") {
            data = {
                popupCode: "PU126",
                searchData: checkData.trim()
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_POPUP_MENU, data);
        }
        else {
            $("input[data-ax-path='menu']").attr("menu", "")
        }
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.

        $("#formView01 input,#formView01 select").each(function () {
            if ($(this).attr("data-ax-path")) {
                if ($(this).attr($(this).attr("data-ax-path"))) {
                    data[$(this).attr("data-ax-path")] = $(this).attr($(this).attr("data-ax-path"));
                }
                else
                    data[$(this).attr("data-ax-path")] = $(this).val();
            }
        });

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


fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 10000
    },
    tagId: "realgrid01",
    initView: function () {
        this.initInstance();
        this.gridObj.setFixedOptions({
            colCount: 1
        });
        this.setColumnInfo(wf00401.column_info);
        this.gridObj.setOption({
            checkBar: {visible: true},
            indicator: {visible: true}
        })
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    itemClick: function (data, index) {
        if (data.workflowResultUuid != null && data.workflowResultUuid != "") {
            if (fnObj.gridView02.isChangeData() == true) {
                axDialog.confirm({
                    msg: axboot.getCommonMessage("AA006")
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    } else {
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
                    }
                });
            } else {
                fnObj.gridView03.clearData();
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
            }
        } else {
            fnObj.gridView02.clearData();
            fnObj.gridView03.clearData();
        }
    }
})
;
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 10000
    },
    tagId: "realgrid02",
    parentsGrid: fnObj.gridView01,
    initView: function () {
        this.initInstance();
        this.gridObj.setFixedOptions({
            colCount: 1
        });
        this.setColumnInfo(wf00402.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    itemClick: function (data, index) {
        if (data.jobResultUuid != null && data.jobResultUuid != "") {
            if (fnObj.gridView03.isChangeData() == true) {
                axDialog.confirm({
                    msg: axboot.getCommonMessage("AA006")
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
                    } else {
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2, data);
                    }
                });
            } else {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH2, data);
            }
        } else {
            fnObj.gridView03.clearData();
        }
    }
});


// AC003 GridView
fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 10000
    },
    tagId: "realgrid03",
    parentsGrid: fnObj.gridView02,
    initView: function () {
        this.initInstance();
        this.gridObj.setFixedOptions({
            colCount: 1
        });
        this.setColumnInfo(wf00403.column_info);
        this.makeGrid();
    }
});

/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}
 */
isDataChanged = function () {
    if (fnObj.gridView01.isChangeData() == true || fnObj.gridView02.isChangeData() == true || fnObj.gridView03.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}

function getFormattedDate(date, isStart) {
    var day;
    var tempDate;
    if (isStart) {
        date.setDate(date.getDate() - 2);
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