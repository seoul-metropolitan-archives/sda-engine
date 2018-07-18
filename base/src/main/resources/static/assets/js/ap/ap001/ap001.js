var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/common/controller",
            data: $.extend({}, {pageSize: 1000, sort: "", serviceId: "ad001", methodName: "getEnviromentList"}, fnObj.searchView.getData()),
            callback: function (res) {
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_SAVE: function (caller, act, data) {

        if (!fnObj.gridView01.validate())
            return false;

        var list = fnObj.gridView01.getData();

        if (list.length < 1)
            return true;

        axboot.ajax({
            type: "POST",
            url: "/api/v1/ad/ad001/save.do",
            async: false,
            data: JSON.stringify(list),
            callback: function (res) {
                fnObj.gridView01.commit();
                axToast.push(axboot.getCommonMessage("AA007"));
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    CLOSE_TAB: function () {
        return ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
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
        $.ajax({
            url: "/assets/js/column_info/ad00101.js",
            dataType: "script",
            async: false,
            success: function () {
            }
        });

        _this.searchView.initView();

        var API_SERVER = CONTEXT_PATH;

        UPLOAD = new ax5.ui.uploader({
            debug: false,
            target: $('[data-ax5uploader="upload1"]'),
            form: {
                action: "/api/v1/common/upload",
                fileName: "file"
            },
            multiple: true,
            manualUpload: false,
            progressBox: true,
            progressBoxDirection: "left",
            dropZone: {
                target: $('[data-uploaded-box="upload1"]')
            },
            uploadedBox: {
                target: $('[data-uploaded-box="upload1"]'),
                icon: {
                    "delete": '<i class="cqc-cancel" aria-hidden="true"></i>',
                    "download": '<i class="cqc-save" aria-hidden="true"></i>'
                },
                columnKeys: {
                    apiServerUrl: API_SERVER,
                    name: "fileName",
                    type: "ext",
                    size: "fileSize",
                    uploadedName: "saveName",
                    thumbnail: ""
                },
                lang: {
                    supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">신분증사진을 선택하세요(필수입력)</div>',
                    emptyListMsg: '<div class="text-center" style="padding-top: 30px;">Empty of List.</div>'
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
                                title: "Seoul-AMS",
                                msg: "선택된 이미지를 삭제하시겠습니까?"
                            }, function () {
                                if (this.key == "ok") {
                                    UPLOAD.removeFile(fileIndex);
                                }
                            });
                            break;

                        case "download":
                            if (file.download) {
                                location.href = API_SERVER + file.download;
                            }
                            break;
                    }
                }
            },
            validateSelectedFiles: function () {

                // 1개 이상 업로드 되지 않도록 제한.
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
};

fnObj.searchView = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData);
    },
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();
    },
    initEvent: function () {
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        console.log(data);
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

})

fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid01",
    primaryKey: "configurationUuid",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(ad00101.column_info);
        this.setFixedOptions({
            colCount: 2
        })
        this.makeGrid();

    }
});