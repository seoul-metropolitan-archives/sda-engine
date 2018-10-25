/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var fnObj = {};
var defaultParameter = {};

var jobList;
var selectedJobUuid = "";

var ACTIONS = axboot.actionExtend(fnObj, {
    // 워크플로우 조회
    PAGE_SEARCH: function (caller, act, data) {
        clearSavedParameter();
        axboot.ajax({
            type: "GET",
            url: "/api/v1/wf003/01/list",
            data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.resetCurrent();
                fnObj.gridView02.clearData();
                fnObj.gridView03.clearData();
                if (res.list.length > 0)
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, res.list[0]);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    // Job 조회
    PAGE_SEARCH1: function (caller, act, data) {
        clearSavedParameter();

        axboot.ajax({
            type: "GET",
            url: "/api/v1/wf003/02/list",
            data: $.extend({}, {pageSize: 1000}, data),
            callback: function (res) {
                jobList = res.list;
                fnObj.gridView02.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        clearSavedParameter();

        var workflowist = [].concat(fnObj.gridView01.getData());
        var workflowJobList = [].concat(fnObj.gridView02.getData());

        axboot
            .call({
                type: "PUT",
                url: "/api/v1/wf003/01/save",
                data: JSON.stringify(workflowist),
                callback: function (res) {
                    fnObj.gridView01.gridObj.commit();
                }
            })
            .call({
                type: "PUT",
                url: "/api/v1/wf003/02/save",
                data: JSON.stringify(workflowJobList),
                callback: function (res) {
                    fnObj.gridView02.gridObj.commit();
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
    MODAL_OPEN: function (caller, act, data) {
        //ACTIONS.dispatch(ACTIONS.PROCESS_RUN);

        axboot.modal.open({
            modalType: "WORKFLOW_POPUP_01",
            param: "",
            sendData: function () {
                return {
                    "workflowUuid": data.workflowUuid
                };
            },
            callback: function (data) {
                //$("#calleeEmpName").val(data.empName);
                //$("#calleeEmpTelno").val(data.empPhoneNo);
                //TODO 개발하고 지워야함
                //axWarningToast.push("팝업이 닫힙니다.");
                //ACTIONS.dispatch(ACTIONS.PROCESS_RUN);

                this.close();
            }
        });
    },
    CLOSE_TAB: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
    },
    PROCESS_RUN: function (caller, act, data) {
        axboot.ajax({
            type: "PUT",
            url: "/api/v1/wf003/01/run",
            dataType: "json",
            data: JSON.stringify(data),
            callback: function (res) {
                //clearSavedParameter();
                axWarningToast.push("워크플로우를 실행하였습니다.\n진행상황은 Workflow Result 화면에서 확인가능합니다.");
            },
            options: {
                onError: axboot.viewError
            }
        });

        return false;
    },
    PROCESS_STOP: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/wf003/01/stop",
            async: true,
            data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
            callback: function (res) {
                clearSavedParameter();
                axWarningToast.push("호출 완료");
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


// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    //TODO 추후에 삭제될 내용으로 /실제 Grid의 컬럼 정보는 DB에서 가져올 예정
    $.ajax({
        url: "/assets/js/column_info/wf00301.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/wf00302.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    _this.gridView02.initView();

    var data = axboot.getMenuParams();

    if (null == data) {
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    } else {
        fnObj.formView.setFormData("serviceUuid", data);
        $("#formView01 select").attr('disabled', true);
        $("#formView01 select").css('background-color' , '#f2f2f2');

        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    }

    var API_SERVER = CONTEXT_PATH;

    UPLOAD = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload1"]'),
        form: {
            action: "/api/v1/common/upload",
            fileName: "file"
        },
        multiple: false,
        manualUpload: false,
        progressBox: true,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload1"]'),
            ondrop: function () {
                axDialog.confirm({
                    title: "Seoul-AMS",
                    msg: "입수정보를 작성해야합니다. 작성하셨습니까?"
                }, function () {
                    if (this.key == "ok") {
                        ACTIONS.dispatch(ACTIONS.PAGE_INGEST);
                    }else{
                        ACTIONS.dispatch(ACTIONS.PAGE_INGEST_LIST);
                    }
                    $(["data-pregressbox-btn='abort'"]).click(function(){
                        $('[data-ax5uploader="upload1"]').hide();
                    });
                });
            },
            ondragover: function () {
                $('[data-ax5uploader="upload1"]').show();
            },
            ondragout: function () {
                $('[data-ax5uploader="upload1"]').hide();
            },
            onclick: function () {
                $('[data-ax5uploader="upload1"]').hide();
            }
        },
        validateSelectedFiles: function () {

            if (this.uploadedFiles.length + this.selectedFiles.length > 1) {
                axDialog.alert({
                    theme: "primary",
                    msg: "파일은 1개이상 업로드할 수 없습니다."
                });
                return false;
            }
            return true;
        },
        onuploaderror: function () {
            $('[data-ax5uploader="upload1"]').hide();
            axDialog.alert({
                title: 'Onsemiro Uploader',
                theme: "default",
                msg: this.error.message
            });
        },
        onuploaded: function () {
        },
        onuploadComplete: function () {
            $('[data-ax5uploader="upload1"]').hide();
            fnObj.gridView03.gridObj.setValue(0,'tempFile',UPLOAD.uploadedFiles[0].fileName)
            axToast.push("File Upload Completed : onuploadComplete");
            UPLOAD.removeFileAll();
        },
        abortCallback: function(){
            $('[data-ax5uploader="upload1"]').hide();
            axToast.push("업로드를 취소하였습니다.");
        },
        onprogress: function () {
            if( $('[data-ax5uploader="upload1"]').is($('[data-ax5uploader="upload1"]').show()) ) {
                return
            }else{
                $('[data-ax5uploader="upload1"]').show();
            }
        },
        onDrop: function(){
            $('[data-ax5uploader="upload1"]').show();
        }
    });

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
        var _this = this;
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
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
        if ($("select[data-ax-path='serviceUuid']").val() == "") {
            axToast.push(axboot.getCommonMessage("AA011"));
            return false;
        }
        else {
            return true;
        }

    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
    }
});


// AC005 User Group User GridView
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 10000
    },
    tagId: "realgrid01",
    primaryKey: "workflowUuid",
    entityName: "WF_WORKFLOW",
    initView: function () {
        this.initInstance();
        this.gridObj.setFixedOptions({
            colCount: 1
        });
        this.setColumnInfo(wf00301.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    itemClick: function (data, index) {
        if (data.workflowUuid != null && data.workflowUuid != "") {

            if (index.fieldIndex == 5 && index.fieldName == "Run") {
                axDialog.confirm({
                    msg: axboot.getCommonMessage("WF003_02")
                }, function () {
                    if (this.key == "ok") {
                        if (checkParameter()) {
                            saveCurrentParameter();

                            if (data) {
                                data.menuUuid = axboot.getMenuId();

                                var jobList = fnObj.gridView02.gridObj.dataProvider.getJsonRows(-1, -1);

                                jobList.forEach(function (job) {
                                    job.parameterList = getSavedParameter(job.jobUuid);
                                });

                                data.workflowJobList = jobList;
                            }

                            ACTIONS.dispatch(ACTIONS.PROCESS_RUN, data);
                        }
                    }
                });
            } else {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1, data);
                fnObj.gridView03.clearData();
            }
        } else {
            fnObj.gridView02.clearData();
            fnObj.gridView03.clearData();
        }
    }
});
fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 10000
    },
    tagId: "realgrid02",
    entityName: "WF_JOB",
    primaryKey: "workflow_job_Uuid",
    parentsUuidFieldName: "workflowUuid",
    parentsGrid: fnObj.gridView01,
    initView: function () {
        this.initInstance();
        this.gridObj.setFixedOptions({
            colCount: 2
        });
        this.setColumnInfo(wf00302.column_info);
        this.gridObj.itemClick(this.itemClick);
        this.makeGrid();
    },
    itemClick: function (data, index) {
        if (jobList && data) {
            for (var i = 0; i < jobList.length; i++) {
                if (jobList[i].jobUuid == data.jobUuid) {
                    jobList[i].checkParameter = true;
                }
            }
        }

        if (data.jobUuid != null && data.jobUuid != "") {
            saveCurrentParameter();

            selectedJobUuid = data.jobUuid;
            fnObj.gridView03.initGrid(data.jobUuid);
        } else {
            fnObj.gridView03.clearData();
        }
    }
});

fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid03",
    entityName: "",
    initView: function () {
    },
    initGrid: function (jobUuid) {
        var _this = this;

        this.clearData();
        if (defaultParameter[jobUuid]) {
            // 이미 저장되어 있는 값이 있는 경우 해당 값으로 grid를 만들어준다.
            drawParameterGrid(defaultParameter[fnObj.gridView02.getCurrentData().jobUuid]);
        } else {
            axboot.ajax({
                url: "/api/v1/wf003/p/02/getPopupInfo",
                async: false,
                data: {"jobUuid": jobUuid},
                callback: function (res) {
                    res = eval(res.map);

                    defaultParameter[fnObj.gridView02.getCurrentData().jobUuid] = res;
                    drawParameterGrid(res);
                }
            });
        }
    },
    initEvent: function () {
        /*fnObj.gridView01.gridObj.onKeydown(function (grid, key, ctrl, shift, alt) {
            switch (key) {
                case 13:
                    ACTIONS.dispatch(ACTIONS.PAGE_CHOICE);
                    ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
                    break;
            }
        });*/
    },
    setData: function (list) {
        this.gridObj.setData("set", list);

        $("#popupGrid01").fadeIn(100);
    },
    getData: function () {
        return this.gridObj.getJsonRows();
    },
    itemClick: function (data,index){
        if(index.fieldName == "tempFile"){
            // event.preventDefault();
            var aa = $('.btn-primary').trigger('click');
            // if(UPLOAD.selectedFiles)
            // $('[data-ax5uploader="upload1"]').show();
            //
            // setInterval(function () {
            //     fnObj.activityTimerView.print();
            // }, 1000);

        }
    },
    clearData: function () {
        $("#realgrid03").empty();
    }
});

drawParameterGrid = function (res) {
    var obj =  {renderer: {type: "imageButton", text: "Run",imageUrl: "/assets/images/ams/btn_run_normal.png",hoverUrl: "/assets/images/ams/btn_run_hover.png", activeUrl: "/assets/images/ams/btn_run_hover.png"}}
    var data = {schemeName :"D",tempFile :"",xlsFileName : "",xlsFilePath:""}
    if (res.columnInfo.length > 0) {
        for (var i = 0; i < res.columnInfo.length; i++) {
            if (res.columnInfo[i]["dataType"] == "combo") {
                res.columnInfo[i]["dataType"]["labels"] = eval(res.columnInfo[i]["dataType"]["labels"]);
                res.columnInfo[i]["dataType"]["values"] = eval(res.columnInfo[i]["dataType"]["values"]);

            }
        }

        fnObj.gridView03.gridObj = new SimpleGridWrapper(fnObj.gridView03.tagId, "/assets/js/libs/realgrid");
        fnObj.gridView03.gridObj.setGridStyle("100%", "100%");
        fnObj.gridView03.gridObj.setColumnInfo(res.columnInfo);
        fnObj.gridView03.gridObj.makeGrid();
        fnObj.gridView03.gridObj.addRow();
        fnObj.gridView03.gridObj.itemClick(fnObj.gridView03.itemClick);
        fnObj.gridView03.gridObj.gridView.resetSize();
        fnObj.gridView03.gridObj.gridView.commit(true);
        fnObj.gridView03.commit();

        /*if (fnObj.gridView03.gridObj) {
            saveCurrentParameter();
        }*/

    } else {
        $("#realgrid03").empty();
    }
}

saveCurrentParameter = function () {
    // job 클릭시 parameter 정보가 바뀌기 전에 값을 저장한다.

    if (axboot.isUndefined(fnObj.gridView03.gridObj) === false) {
        fnObj.gridView03.gridObj.gridView.commit(true);

        if (defaultParameter[selectedJobUuid]) {
            var columnInfo = defaultParameter[selectedJobUuid].columnInfo;

            if (columnInfo && columnInfo.length > 0) {
                for (var i = 0; i < columnInfo.length; i++) {
                    if (fnObj.gridView03.getData()[0].hasOwnProperty(columnInfo[i].name)) {
                        columnInfo[i].defaultValue = fnObj.gridView03.getData()[0][columnInfo[i].name];
                    }
                }

                defaultParameter[selectedJobUuid].columnInfo = columnInfo;
            }
        }
    }
}

clearSavedParameter = function () {
    defaultParameter = {};
    selectedJobUuid = "";
}

getSavedParameter = function (jobUuid) {

    var saveParamArray;

    if (defaultParameter[jobUuid] && defaultParameter[jobUuid].columnInfo) {
        saveParamArray = defaultParameter[jobUuid].columnInfo;
    }

    if (saveParamArray != null && saveParamArray.length > 0) {
        var parameterList = [];

        for (var i = 0; i < jobList.length; i++) {
            if (jobList[i].jobUuid == jobUuid) {
                var tempParameterList = jobList[i].parameterList;

                for (var j = 0; j < tempParameterList.length; j++) {
                    for (var inx = 0; inx < saveParamArray.length; inx++) {
                        if (tempParameterList[j].parameterName == saveParamArray[inx].name) {

                            tempParameterList[j].defaultValue = saveParamArray[inx].defaultValue;
                            parameterList.push(tempParameterList[j]);
                        }
                    }
                }
            }
        }

        return parameterList;
    } else {
        return null;
    }
}

checkParameter = function () {
    jobList.forEach(function (job) {
        if (job.checkParameter === false) {
            axboot.viewError({message: "체크되지 않은 파라미터 값이 있습니다.\nJob의 파라미터를 확인하시기 바랍니다."});
            return false;
        }
    });

    return true;
}


/**
 * [필수]
 * Grid 데이터 변경 여부를 체크하기 위한 함수
 * 모든 페이지에 넣기를 권고하며, 안넣은 경우 데이터 변경여부를 확인하지 않음
 * @returns {boolean}
 */
isDataChanged = function () {
    if (fnObj.gridView01.isChangeData() == true || fnObj.gridView02.isChangeData() == true) {
        return true;
    } else {
        return false;
    }
}


