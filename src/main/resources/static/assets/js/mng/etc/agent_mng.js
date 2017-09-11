var fnObj = {};
var selectedItem = {};

var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/mng/etc/agent_mng",
            data: $.extend({}, this.searchView.getData(), this.gridView01.getPageData()),
            callback: function (res) {
                caller.gridView01.setData(res);
                //caller.formView01.clear();
            },
            options: {
                onError: viewError
            }
        });
        return false;
    },
    EXCEL_DOWNLOAD: function (caller, act, data) {
        var params = buildParams($.extend({}, this.searchView.getData()));
        console.log(params);
        window.location = CONTEXT_PATH + "/api/v1/mng/etc/agent_mng/download/excel?" + params;
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        if (caller.formView01.validate()) {

            var parentData = caller.formView01.getData();
            var childList = [].concat(caller.gridView02.getData("modified"));
            childList = childList.concat(caller.gridView02.getData("deleted"));

            // childList에 parentKey 삽입
            childList.forEach(function (n) {
                n.parentKey = parentData.key;
            });

            axboot
                .call({
                    type: "PUT",
                    //url: ["samples", "parent"],
                    url: "/api/v1//mng/error/sh01001120",
                    data: JSON.stringify([parentData]),
                    callback: function (res) {
                    }
                })
                .call({
                    //childlist에 대하여 직접 코딩 필요!!!
                    /*
                     type: "PUT", url: ["samples", "child"], data: JSON.stringify(childList),
                     callback: function (res) {
                     }
                     */
                })
                .done(function () {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    axToast.push("저장 작업이 완료되었습니다.");
                });
        }
    },
    FORM_CLEAR: function (caller, act, data) {
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                axDialog.close();
                caller.formView01.clear();
                UPLOAD.removeFile(0);
                UPLOAD2.removeFile(0);
            }
        });
    },
    ITEM_CLICK: function (caller, act, data) {
        UPLOAD.removeFile(0);
        UPLOAD2.removeFile(0);
        caller.formView01.setData(data);
    },
    SEND_STEXT: function (caller, act, data) {
        if (fnObj.formView01.validate()) {
            axDialog.confirm({
                msg: "요원정보를 저장하시겠습니까?"
            }, function () {
                if (this.key == "ok") {
                    var parentData = fnObj.formView01.getData(); //화면정보를 가져온다.
                    parentData.digitalSealUrl = UPLOAD.uploadedFiles.length > 0 ? UPLOAD.uploadedFiles[0].id : "";
                    parentData.digitalSignUrl = UPLOAD2.uploadedFiles.length > 0 ? UPLOAD2.uploadedFiles[0].id: "";

                    axboot
                        .call({
                            type: "PUT",
                            url: "/api/v1/mng/etc/agent_mng",
                            data: JSON.stringify(parentData),
                            callback: function (res) {
                            }
                        })
                        .done(function () {
                            caller.formView01.clear();
                            UPLOAD.removeFile(0);
                            UPLOAD2.removeFile(0);
                            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                            axToast.push("요원정보를 저장하였습니다.");
                        });

                }
            });
        }
    },
    dispatch: function (caller, act, data) {
        var result = ACTIONS.exec(caller, act, data);
        if (result != "error") {
            return result;
        } else {
            // 직접코딩
            return false;
        }
    }
});

var CODE = {};

// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    CODE = this; // this는 call을 통해 수집된 데이터들.

    _this.pageButtonView.initView();
    _this.searchView.initView();
    _this.gridView01.initView();
    _this.formView01.initView();

    var API_SERVER = CONTEXT_PATH;

    UPLOAD = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload1"]'),
        form: {
            action: "/api/v1//mng/etc/agent_mng/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
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
                thumbnail: "thumbnail"
            },
            lang: {
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 80px;">이미지를 선택하세요.</div>',
                emptyListMsg: '<div class="text-center" style="padding-top: 80px;">Empty of List.</div>'
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
    UPLOAD2 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload2"]'),
        form: {
            action: "/api/v1//mng/etc/agent_mng/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload2"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload2"]'),
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
                thumbnail: "thumbnail"
            },
            lang: {
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 80px;">이미지를 선택하세요.</div>',
                emptyListMsg: '<div class="text-center" style="padding-top: 80px;">Empty of List.</div>'
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
                                UPLOAD2.removeFile(fileIndex);
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
            console.log("test", this);
        }
    });

    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.pageResize = function () {
};

fnObj.pageButtonView = axboot.viewExtend({
    initView: function () {
        axboot.buttonClick(this, "data-page-btn", {
            "search": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            "excel": function () {
                fnObj.gridView01.excel("요원정보관리-"+getFormattedDate(new Date())+".xls");
            },
            "search-view-clear": function () {
                $("#filter").val("");
                if(sessionJson.userCd == "system" || sessionJson.jisaCode == "00") {
                    $("#jisaCode").val("");
                }
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });
    }
});

//== view 시작
/**
 * searchView
 */
fnObj.searchView = axboot.viewExtend(axboot.searchView, {
    initView: function () {
        this.target = $(document["searchView0"]);
        this.target.attr("onsubmit", "return ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);");
        this.filter = $("#filter");

        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCode").attr('readonly', true);
            $("#jisaCode").attr('disabled', true);
            $("#jisaCode").val(sessionJson.jisaCode);
        }
    },
    getData: function () {
        return {
            filter: this.filter.val(),
            jisaCode: $("#jisaCode").val()
        }
    }
});

/**
 * gridView
 */
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    page: {
        pageNumber: 0,
        pageSize: 20
    },
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({
            showRowSelector: true,
            frozenColumnIndex: 0,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {
                    key: 'jisaCode', label: '지사명', width: 70, align: 'center', formatter: function formatter() {
                    return parent.COMMON_CODE["JISA_CODE"].map[this.value];
                }
                },
                /*{key: 'empRegno', label: '직원주민번호', width: 100, align: 'left', editor: 'text'},*/
                {key: 'empName', label: '직원성명', width: 100, align: 'center', editor: 'text'},
                {key: 'empEnable', label: '직원여부', width: 80, align: 'center', editor: 'text', formatter: function formatter() {
                    return parent.COMMON_CODE["STAFF_ENABLE"].map[this.value];
                }
                },
                {key: 'empGubun', label: '직원구분', width: 80, align: 'center', editor: 'text', formatter: function formatter() {
                    return parent.COMMON_CODE["EMP_GUBUN"].map[this.value];
                }
                },
                {key: 'corpGubun', label: '회사구분', width: 80, align: 'center', editor: 'text', formatter: function formatter() {
                    return parent.COMMON_CODE["CORP_GUBUN"].map[this.value];
                }
                },
                {key: 'digitalSealUrl', label: '전자인감주소', width: 375, align: 'left', editor: 'text'},
                {key: 'digitalSignUrl', label: '전자서명주소', width: 375, align: 'left', editor: 'text'}
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex);
                    selectedItem = this.item;

                    ACTIONS.dispatch(ACTIONS.ITEM_CLICK, this.item);
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({pageNumber: pageNumber});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });

        axboot.buttonClick(this, "data-grid-view-01-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "delete": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_DEL);
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.empRegno;
            });
        } else {
            list = _list;
        }
        return list;
    },
    addRow: function () {
        this.target.addRow({__created__: true}, "last");
    },
    excel: function (file) {
        this.target.exportExcel(file);
    }
});

/**
 * formView01 - 요원정보 상세
 */
fnObj.formView01 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {});
    },
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();

        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm").attr('readonly', true);
            $("#jisaCodeForm").attr('disabled', true);
            $("#jisaCodeForm").val(sessionJson.jisaCode);
        }

        axboot.buttonClick(this, "data-form-view-01-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
            "send-stext": function () {
                // 요원정보 저장
                ACTIONS.dispatch(ACTIONS.SEND_STEXT);
            },
            "search-view-clear": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });

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

        if (typeof data.digitalSealUrlFile != "undefined") {
            UPLOAD.uploadedFiles.push(data.digitalSealUrlFile);
            UPLOAD.refreshImage();
        }
        if (typeof data.digitalSignUrlFile != "undefined") {
            UPLOAD2.uploadedFiles.push(data.digitalSignUrlFile);
            UPLOAD2.refreshImage();
        }

        this.target.find('[data-ax-path="key"]').attr("readonly", "readonly");

        this.model.setModel(data);
        this.modelFormatter.formatting(); // 입력된 값을 포메팅 된 값으로 변경
    },
    validate: function () {
        var rs = this.model.validate();
        var title;
        var message;

        if ($("#empName").val() == "") {
            message = '\n' + '직원명은 필수 입력조건입니다.\n' + '직원명을 입력하세요';
            // $("#jisaCodeForm").focus();
            formError(message);
            return false;
        }

        if ($("#empEnable").val() == "") {
            message = '\n' + '직원여부는 필수 입력조건입니다.\n' + '직원여부를 선택하세요';
            // $("#jisaCodeForm").focus();
            formError(message);
            return false;
        }

        if ($("#empGubun").val() == "") {
            message = '\n' + '직원구분은 필수 입력조건입니다.\n' + '직원구분을 선택하세요';
            // $("#jisaCodeForm").focus();
            formError(message);
            return false;
        }

        if ($("#corpGubun").val() == "") {
            message = '\n' + '회사구분은 필수 입력조건입니다.\n' + '회사구분을 선택하세요';
            // $("#jisaCodeForm").focus();
            formError(message);
            return false;
        }

        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm").attr('readonly', true);
            $("#jisaCodeForm").attr('disabled', true);
            $("#jisaCodeForm").val(sessionJson.jisaCode);
        }
    }
});

var formError = function (message) {
    axToast.confirm({
        theme: "danger",
        width: 300,
        lang: {
            "ok": "닫기"
        },
        icon: '<i class="cqc-new"></i>',
        msg: '[에러] ' + message
    });
}

var viewError = function (err) {
    axToast.confirm({
        theme: "danger",
        width: 300,
        lang: {
            "ok": "닫기"
        },
        icon: '<i class="cqc-new"></i>',
        msg: '[에러] ' + err.message
    });
}

var buildParams = function (json) {

    var params = JSON.stringify(json);

    params = params.replace(/{/g, "");
    params = params.replace(/}/g, "");
    params = params.replace(/:/g, "=")
    params = params.replace(/,/g, "&");
    params = params.replace(/"/g, "");

    return params;
}

var removeAllUploadFiles = function (upload) {
    console.log("files:", upload.uploadedFiles.length);
    if (upload.uploadedFiles.length <= 0) {
        return;
    }
    $.ajax({
        contentType: "application/json",
        method: "post",
        url: "/api/v1//mng/cash/sh03001220/delete",
        data: JSON.stringify([{
            id: upload.uploadedFiles[0].id
        }]),
        success: function (res) {
            if (res.error) {
                alert(res.error.message);
                return;
            }
            upload.removeFile(0);
            return removeAllUploadFiles(upload);
        }
    });
}


function getFormattedDate(date, isStart) {
    var day;
    var tempDate;
    if(isStart){
        date.setDate(date.getDate() - 7);
        tempDate = date.getDate();
    }else{
        tempDate = date.getDate();
    }
    day = tempDate.toString();

    var year = date.getFullYear();
    var month = (1 + date.getMonth()).toString();
    month = month.length > 1 ? month : '0' + month;
    day = day.length > 1 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}


var UPLOAD;
var UPLOAD2;