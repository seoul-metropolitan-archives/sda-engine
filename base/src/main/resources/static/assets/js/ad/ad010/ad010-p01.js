
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
        var sendData = {}
        if(UPLOAD.uploadedFiles.length > 0){
            var filePath = UPLOAD.uploadedFiles[0].filePath +"/" + UPLOAD.uploadedFiles[0].saveName;
            var filePathTemp = UPLOAD.uploadedFiles[0].filePath;
            sendData = $.extend({},fnObj.formView.getData(),{fileName: UPLOAD.uploadedFiles[0].fileName,filePathTemp:filePathTemp,filePath:filePath});
        }else{
            if(UPLOAD.uploadedFiles.length == 0){
                fnObj.formView.setFormData("fileName","");
                fnObj.formView.setFormData("filePath","");
            }
            sendData = fnObj.formView.getData();
        }

        $.ajax({
            url: "/api/v1/ad/ad010/02/save",
            contentType: "application/json",
            type:"PUT",
            data: JSON.stringify(sendData),
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

    $.ajax({
        url: "/assets/js/noticeUploader.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();

    var API_SERVER = "http://localhost:8888/";

    UPLOAD = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload1"]'),
        form: {
            action: "/api/v1/common/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        uploadedBox: {
            target: $('[data-uploaded-box="upload1"]'),
            icon: {
                "delete": '<i class="cqc-cancel" aria-hidden="true"></i>',
                "download": '<i class="cqc-save" aria-hidden="true"></i>'
            },
            columnKeys: {
                name: "fileName",
                type: "ext",
                uploadedName: "saveName",
                uploadedPath: "",
                downloadPath: "",
                previewPath: "",
                thumbnail: ""

            },
            lang: {
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 5px;">파일을 선택하세요</div>',
                emptyListMsg: '<div class="text-center" style="padding-top: 10px;">Empty of List.</div>'
            },
            onchange: function () {
                console.log('onchange: ', this);
            },
            onclick: function () {
                // console.log(this.cellType);
                var fileIndex = this.fileIndex;
                var file = this.uploadedFiles[fileIndex];
                switch (this.cellType) {
                    case "delete":
                        axDialog.confirm({
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD.removeFile(fileIndex);
                                UPLOAD.uploadedFiles = [];
                            }
                        });
                        break;

                    case "download":
                        location.href = API_SERVER + "/api/v1/common/download/notice?attch="+file.filePath;
                        break;
                }
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
        onprogress: function () {
            console.log('progress');
        },
        onuploaderror: function () {
            axDialog.alert({
                title: 'Onsemiro Uploader',
                theme: "default",
                msg: this.error.message
            });
        },
        onuploaded: function () {
        },
        onuploadComplete: function () {
        }
    });

    if(parentsData != null && parentsData != undefined) {
        fnObj.formView.setData(parentsData);
        UPLOAD.clear();
        if(parentsData.filePath != "" && parentsData.filePath != null){
            var obj = {filePath:parentsData.filePath,fileName : parentsData.fileName}
            UPLOAD.uploadedFiles.push(obj);
            UPLOAD.refreshImage();
        }

        if(sessionJson.userUuid != parentsData.registerUuid){
            $("#adminBtn_s").hide();
            $("#adminBtn_d").hide();
            $(".btn_s").attr("disabled",true);
            $("div[data-uploaded-item-cell='delete']").hide();
            $("button[data-ax5uploader-button='selector']").hide();
            $("input[data-ax-path='title']").attr("readonly",true);
            $("textarea[data-ax-path='contents']").attr("readonly",true);
        }else{
            $("#adminBtn_s").show();
            $("#adminBtn_d").show();
            $(".btn_s").attr("disabled",false);
            $("div[data-uploaded-item-cell='delete']").show();
            $("button[data-ax5uploader-button='selector']").show();
            $("input[data-ax-path='title']").attr("readonly",false);
            $("textarea[data-ax-path='contents']").attr("readonly",false);
        }
    }else{
        fnObj.formView.setFormData("registerName",sessionJson.userNm);
        $("button[data-ax5uploader-button='selector']").show();
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

