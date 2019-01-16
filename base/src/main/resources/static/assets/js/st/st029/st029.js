var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    // JOB 조회
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st029/01/list01",
            data: $.extend({}, {pageSize: 1000}, this.formView.getData()),
            callback: function (res) {
                fnObj.gridView01.setData(res.list);
                fnObj.gridView01.resetCurrent();
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        if( !fnObj.gridView01.validate())
            return ;

        var result = false;
        axboot.call({
            type: "PUT",
            url: "/api/v1/st/st029/01/save01",
            data: JSON.stringify(this.gridView01.getData()),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        })
            .done(function () {
                fnObj.gridView01.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            });
        return result;


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
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            param: "",
            sendData: function () {
                return {
                    //jisaCode: fnObj.formView02.getData().jisaCode
                };
            },
            callback: function (data) {
                //$("#calleeEmpName").val(data.empName);
                //$("#calleeEmpTelno").val(data.empPhoneNo);

                this.close();
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
        url: "/assets/js/column_info/st02901.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    //_this.gridView02.initView();

    // Data 조회
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);


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
            axToast.push("File Upload Complete");
            /*fnObj.gridView02.gridObj.setValue(fnObj.gridView02.gridObj.gridView.getSelectedItems()[0],'refUploadFilePath',UPLOAD.uploadedFiles[0].fileName);
            fnObj.gridView03.gridObj.setValue(0,'uploadFilePath',UPLOAD.uploadedFiles[0].fileName);
            // 마지막으로 업로드 된 파일의 실제 경로를 저장한다.
            var lastUploadFilePath = "/" + UPLOAD.uploadedFiles[0].filePath +"/" + UPLOAD.uploadedFiles[0].saveName;
            fnObj.gridView02.gridObj.setValue(fnObj.gridView02.gridObj.gridView.getSelectedItems()[0],'lastUploadFilePath',lastUploadFilePath);
            axToast.push("File Upload Complete");
            UPLOAD.removeFileAll();
            saveCurrentParameter(fnObj.gridView02.gridObj.gridView.getSelectedItems()[0]);*/
        },
        abortCallback: function(){
            $('[data-ax5uploader="upload1"]').hide();
            axToast.push("업로드를 취소하였습니다.");
        },
        onprogress: function () {

            if(!($('[data-ax5uploader="upload1"]').is($('[data-ax5uploader="upload1"]').show()))) {
                $('[data-ax5uploader="upload1"]').show();
            }else{
                //$('[data-ax5uploader="upload1"]').show();
            }
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

        $("input[data-ax-path='arrangedFromDate01']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='arrangedFromDate01']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });


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


// AC005 User Group User GridView
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey: "programUuid",
    entityName: "ST_PROGRAM",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st02901.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    itemClick: function (data, index) {
        if(index.fieldName == "uploadFilePath"){
            $('.btn-primary').trigger('click');
        }
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
