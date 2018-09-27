var fnObj = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/common/controller",
            data: $.extend({}, {pageSize: 1000, sort: "", serviceId: "ad001", methodName: "getEnviromentList"}, fnObj.searchView.getData()),
            callback: function (res) {
                fnObj.gridView01.resetCurrent();O
                fnObj.gridView01.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    PAGE_TEST: function (caller, act, data) {
        ACTIONS.dispatch(ACTIONS.PAGE_INGEST,data);
    },
    PAGE_INGEST: function (caller, act, data) {
        axboot.modal.open({
            modalType: "INGEST_POPUP",
            width: 1000,
            height: 800,
            header: {
                title: "INGEST"
            },
            sendData: function () {
               return data;
            },
            callback: function (data) {
                // if(this) this.close();
                // UPLOAD.send();
                // if(data){
                //     ACTIONS.dispatch(ACTIONS.PAGE_SEARCH1,data);
                // }
            }
        });
    },
    PAGE_INGEST_LIST: function (caller, act, data) {
        axboot.modal.open({
            modalType: "INGEST_LIST_POPUP",
            width: 1000,
            height: 800,
            header: {
                title: "INGEST"
            },
            sendData: function () {
                // return {
                //     confirmBtn:"Arrange",
                //     crrntAgg: fnObj.formView.getData().aggInContainerName,
                //     containerUuid :  currentContainerUuid
                // };
            },
            callback: function (data) {
                //if(this) this.close();
                window.axModal.activeModal.remove();
                window.axModal.activeModal = null;

                ACTIONS.dispatch(ACTIONS.PAGE_TEST);
                // setTimeout(function () {
                //
                // }, 500);

            }
        });
    },
    PAGE_DROP : function (caller, act, e) {
        e.stopPropagation();
        e.preventDefault();

        var uploadFile = function(file, path) {
            console.log(path, file);
            // handle file uploading
        };

        var iterateFilesAndDirs = function(filesAndDirs, path) {
            for (var i = 0; i < filesAndDirs.length; i++) {
                if (typeof filesAndDirs[i].getFilesAndDirectories === 'function') {
                    var path = filesAndDirs[i].path;

                    // this recursion enables deep traversal of directories
                    filesAndDirs[i].getFilesAndDirectories().then(function(subFilesAndDirs) {
                        // iterate through files and directories in sub-directory
                        iterateFilesAndDirs(subFilesAndDirs, path);
                    });
                } else {
                    uploadFile(filesAndDirs[i], path);
                }
            }
        };

        // begin by traversing the chosen files and directories
        if ('getFilesAndDirectories' in e.dataTransfer) {
            e.dataTransfer.getFilesAndDirectories().then(function(filesAndDirs) {
                iterateFilesAndDirs(filesAndDirs, '/');
            });
        }
    },
    PAGE_SAVE: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/common/controller",
            data: $.extend({}, {pageSize: 1000, sort: "", serviceId: "ad001", methodName: "getEnviromentList1"}, fnObj.searchView.getData()),
            callback: function (res) {
                fnObj.gridView01.resetCurrent();
                fnObj.gridView01.setData(res.list);
            },
            options: {
                onError: axboot.viewError
            }
        });

        /*if (!fnObj.gridView01.validate())
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
        });*/
    },
    INGEST_ARCHIVE: function (caller, act, data) {

        var uploadFiles = UPLOAD.uploadedFiles;

        axboot
            .call({
                type: "PUT",
                url: "/api/v1/wf999/01/save",
                data: JSON.stringify(uploadFiles),
                callback: function (res) {
                    UPLOAD.uploadedFiles = [];
                    axToast.push(axboot.getCommonMessage("AA007"));
                }
            })
            .done(function () {

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
            manualUpload: true,
            progressBox: false,
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
                            // UPLOAD.send();
                        }else{
                            ACTIONS.dispatch(ACTIONS.PAGE_INGEST_LIST);
                            // UPLOAD.send();
                        }
                    });
                },
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
                    supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">신분증사진을 선택하세요(필수입력!)</div>',
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
            onuploaderror: function () {
                axDialog.alert({
                    title: 'Onsemiro Uploader',
                    theme: "default",
                    msg: this.error.message
                });
            },
            onuploaded: function () {
                //axToast.push("File Upload Completed : onuploaded");
            },
            onuploadComplete: function () {
                axToast.push("File Upload Completed : onuploadComplete");
                ACTIONS.dispatch(ACTIONS.INGEST_ARCHIVE);
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
        $( "#dropDiv" ).on("dragover",dragHover)
        $( "#dropDiv" ).on("dragleave",dragHover)
        $( "#dropDiv" ).on("drop", function (event){
            event.preventDefault();

            var items = event.dataTransfer.items;
            for (var i=0; i<items.length; i++) {
                // webkitGetAsEntry is where the magic happens
                var item = items[i].webkitGetAsEntry();
                if (item) {
                    traverseFileTree(item);
                }
            }
        }, false);
        $( "#dropDiv" ).ondrop  = function(e) {
            var length = e.dataTransfer.items.length;
            for (var i = 0; i < length; i++) {
                var entry = e.dataTransfer.items[i].webkitGetAsEntry();
                if (entry.isFile) {

                } else if (entry.isDirectory) {

                }
            }
        };

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

function dragHover(e) {
    e.stopPropagation();
    e.preventDefault();

    if (e.type === 'dragover') {
        e.target.className = 'over';
    } else {
        e.target.className = '';
    }
}
function traverseFileTree(item, path) {
    path = path || "";
    if (item.isFile) {
        // Get file
        item.file(function(file) {
            console.log("File:", path + file.name);
        });
    } else if (item.isDirectory) {
        // Get folder contents
        var dirReader = item.createReader();
        dirReader.readEntries(function(entries) {
            for (var i=0; i<entries.length; i++) {
                traverseFileTree(entries[i], path + item.name + "/");
            }
        });
    }
}