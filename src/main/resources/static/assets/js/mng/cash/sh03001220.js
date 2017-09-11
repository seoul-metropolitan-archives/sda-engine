var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {

    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/cash/sh03001220",
            data: $.extend({}, this.searchView.getData(), this.gridView01.getPageData()),
            callback: function (res) {
                caller.gridView01.setData(res);
                caller.formView01.clear();
                caller.formView01.setSingleData("reqDate", getFormattedDate(new Date()));
            },
            options: {
                onError: viewError
            }
        });
        return false;
    },
    MODAL_OPEN: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_TERMINAL_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisa": $("#jisaCode").val()
                };
            },
            callback: function (data) {
                $("#jisaCode").val(data.jisaCode);
                $("#branchName").val(data.branchName);
                $("#branchCode").val(data.branchCode);
                $("#cornerName").val(data.cornerName);
                $("#cornerCode").val(data.cornerCode);
                $("#terminalNo").val(data.terminalNo);

                this.close();
            }
        });
    },
    FORM_CLEAR: function (caller, act, data) {
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {

                $("#formView01 input").attr('disabled', false);
                $("#formView01 select").attr('disabled', false);
                $("#formView01 textarea").attr('disabled', false);
                $("#formView01 span").css("pointer-events", "auto");
                $("#formView01 .cqc-calendar").unbind('click', false);
                axDialog.close();
                caller.formView01.clear();
                caller.formView01.setSingleData("reqDate", getFormattedDate(new Date()));

                UPLOAD.removeFile(0);
                UPLOAD2.removeFile(0);

                $('#cashType').val(2);
                cashTypeChange("2", "27", "21", "22", "23", "28");
            }
        });
    },
    FORM_STEXT_SAVE: function (caller, act, data) {
        if (caller.formView01.validate()) {
            var parentData = caller.formView01.getData();
            parentData.cornerCode = '99';
            parentData.terminalNo = '9999';
            parentData.chargeEmpPhotoUrl = UPLOAD.uploadedFiles.length > 0 ? UPLOAD.uploadedFiles[0].filePath.substring(0, 4) + '/' + UPLOAD.uploadedFiles[0].filePath.substring(5, 7) + '/' + UPLOAD.uploadedFiles[0].filePath.substring(8, 10) + '/' + UPLOAD.uploadedFiles[0].saveName : "";
            parentData.digitalSignUrl = UPLOAD2.uploadedFiles.length > 0 ? UPLOAD2.uploadedFiles[0].filePath.substring(0, 4) + '/' + UPLOAD2.uploadedFiles[0].filePath.substring(5, 7) + '/' + UPLOAD2.uploadedFiles[0].filePath.substring(8, 10) + '/' + UPLOAD2.uploadedFiles[0].saveName : "";

            parentData.cashTypeCode1 = parentData.cashTypeCode1 || "";
            parentData.cashTypeCode2 = parentData.cashTypeCode2 || "";
            parentData.cashTypeCode3 = parentData.cashTypeCode3 || "";
            parentData.cashTypeCode4 = parentData.cashTypeCode4 || "";
            parentData.cashTypeCode5 = parentData.cashTypeCode5 || "";

            parentData.cashTypeCode1RecvAmt = parentData.cashTypeCode1RecvAmt || "0";
            parentData.cashTypeCode2RecvAmt = parentData.cashTypeCode2RecvAmt || "0";
            parentData.cashTypeCode3RecvAmt = parentData.cashTypeCode3RecvAmt || "0";
            parentData.cashTypeCode4RecvAmt = parentData.cashTypeCode4RecvAmt || "0";
            parentData.cashTypeCode5RecvAmt = parentData.cashTypeCode5RecvAmt || "0";

            parentData.s20GCardRecvCount = parentData.s20GCardRecvCount || "0";
            parentData.s20TCardRecvCount = parentData.s20TCardRecvCount || "0";
            parentData.s20pinkGCardRecvCount = parentData.s20pinkGCardRecvCount || "0";
            parentData.s20pinkTCardRecvCount = parentData.s20pinkTCardRecvCount || "0";
            parentData.loveCardRecvCount = parentData.loveCardRecvCount || "0";
            parentData.hipointCardRecvCount = parentData.hipointCardRecvCount || "0";
            parentData.scrtyCardRecvCount = parentData.scrtyCardRecvCount || "0";
            parentData.chargeEmpRegno = parentData.chargeEmpRegno + '0000000';
            parentData.stextSendGubun = '1';
            axboot.ajax({
                type: "PUT",
                url: "/api/v1//mng/cash/sh03001220",
                data: JSON.stringify(parentData),
                callback: function () {
                    caller.formView01.clear();
                    caller.formView01.setSingleData("reqDate", getFormattedDate(new Date()));
                    UPLOAD.removeFile(0);
                    UPLOAD2.removeFile(0);
                    fnObj.gridView01.setPageData({pageNumber: 0});
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    axToast.push("운영자금인수정보통보전문이 전송되었습니다.");

                }
            });
        }
    },
    FORM_SAVE: function (caller, act, data) {
        if (caller.formView01.validate()) {
            var parentData = caller.formView01.getData();
            parentData.cornerCode = '99';
            parentData.terminalNo = '9999';
            parentData.chargeEmpPhotoUrl = UPLOAD.uploadedFiles.length > 0 ? UPLOAD.uploadedFiles[0].filePath.substring(0, 4) + '/' + UPLOAD.uploadedFiles[0].filePath.substring(5, 7) + '/' + UPLOAD.uploadedFiles[0].filePath.substring(8, 10) + '/' + UPLOAD.uploadedFiles[0].saveName : "";
            parentData.digitalSignUrl = UPLOAD2.uploadedFiles.length > 0 ? UPLOAD2.uploadedFiles[0].filePath.substring(0, 4) + '/' + UPLOAD2.uploadedFiles[0].filePath.substring(5, 7) + '/' + UPLOAD2.uploadedFiles[0].filePath.substring(8, 10) + '/' + UPLOAD2.uploadedFiles[0].saveName : "";

            parentData.cashTypeCode1 = parentData.cashTypeCode1 || "";
            parentData.cashTypeCode2 = parentData.cashTypeCode2 || "";
            parentData.cashTypeCode3 = parentData.cashTypeCode3 || "";
            parentData.cashTypeCode4 = parentData.cashTypeCode4 || "";
            parentData.cashTypeCode5 = parentData.cashTypeCode5 || "";

            parentData.cashTypeCode1RecvAmt = parentData.cashTypeCode1RecvAmt || "0";
            parentData.cashTypeCode2RecvAmt = parentData.cashTypeCode2RecvAmt || "0";
            parentData.cashTypeCode3RecvAmt = parentData.cashTypeCode3RecvAmt || "0";
            parentData.cashTypeCode4RecvAmt = parentData.cashTypeCode4RecvAmt || "0";
            parentData.cashTypeCode5RecvAmt = parentData.cashTypeCode5RecvAmt || "0";

            parentData.s20GCardRecvCount = parentData.s20GCardRecvCount || "0";
            parentData.s20TCardRecvCount = parentData.s20TCardRecvCount || "0";
            parentData.s20pinkGCardRecvCount = parentData.s20pinkGCardRecvCount || "0";
            parentData.s20pinkTCardRecvCount = parentData.s20pinkTCardRecvCount || "0";
            parentData.loveCardRecvCount = parentData.loveCardRecvCount || "0";
            parentData.hipointCardRecvCount = parentData.hipointCardRecvCount || "0";
            parentData.scrtyCardRecvCount = parentData.scrtyCardRecvCount || "0";
            parentData.chargeEmpRegno = parentData.chargeEmpRegno + '0000000';
            parentData.stextSendGubun = '0';
            axboot.ajax({
                type: "PUT",
                url: "/api/v1//mng/cash/sh03001220",
                data: JSON.stringify(parentData),
                callback: function (res) {
                    caller.formView01.clear();
                    caller.formView01.setSingleData("reqDate", getFormattedDate(new Date()));
                    UPLOAD.removeFile(0);
                    UPLOAD2.removeFile(0);
                    fnObj.gridView01.setPageData({pageNumber: 0});
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    axToast.push("저장 작업이 완료되었습니다.");
                },
                options: {
                    onError: viewError
                }
            });
        }
    },
    FORM_MODAL_OPEN: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_TERMINAL_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisa": $("#jisaCodeForm").val()
                };
            },
            callback: function (data) {
                caller.formView01.setSingleData("jisaCode", data.jisaCode);
                caller.formView01.setSingleData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[data.branchCode]);
                caller.formView01.setSingleData("branchCode", data.branchCode);
                caller.formView01.setSingleData("cornerName", parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[data.terminalNo]);
                caller.formView01.setSingleData("cornerCode", data.cornerCode);
                caller.formView01.setSingleData("terminalNo", data.terminalNo);
                this.close();
            }
        });
    },
    ITEM_CLICK: function (caller, act, data) {

        UPLOAD.removeFile(0);
        UPLOAD2.removeFile(0);

        caller.formView01.setData(data);
        caller.formView01.setSingleData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[data.branchCode]);
        caller.formView01.setSingleData("cornerName", parent.COMMON_CODE["CORNER_TERMINAL_CODE"].map[data.terminalNo]);

        if (data.stextSendGubun == 1) {
            $("#formView01 input").attr('disabled', true);
            $("#formView01 select").attr('disabled', true);
            $("#formView01 textarea").attr('disabled', true);
            $("#formView01 span").css("pointer-events", "none");
            $("#formView01 .cqc-calendar").bind('click', false);
        } else {
            $("#formView01 input").attr('disabled', false);
            $("#formView01 select").attr('disabled', false);
            $("#formView01 textarea").attr('disabled', false);
            $("#formView01 span").css("pointer-events", "auto");
            $("#formView01 .cqc-calendar").bind('click', false);
        }

        if (data.cashTypeCode1 != undefined) {
            $('#cashType').val(data.cashTypeCode1.substring(0, 1));
            cashTypeChange($('#cashType').val(), data.cashTypeCode1, data.cashTypeCode2, data.cashTypeCode3, data.cashTypeCode4, data.cashTypeCode5);
        }
    },
    EXCEL_DOWNLOAD: function (caller, act, data) {
        var params = buildParams($.extend({}, this.searchView.getData()));
        console.log(params);
        window.location = CONTEXT_PATH + "/api/v1//mng/cash/sh03001220/download?" + params;
        return false;
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

    this.pageButtonView.initView();
    this.searchView.initView();
    this.gridView01.initView();
    this.formView01.initView();

    var API_SERVER = CONTEXT_PATH;

    UPLOAD = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload1"]'),
        form: {
            action: "/api/v1//mng/cash/sh03001220/upload",
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
                            msg: "인수책임자 사진파일을 삭제하시겠습니까?"
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
            action: "/api/v1//mng/cash/sh03001220/upload",
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
                            msg: "인수책임자 전자인감을 삭제하시겠습니까?"
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
                fnObj.gridView01.setPageData({pageNumber: 0});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            "excel": function () {
                fnObj.gridView01.excel("운영자금인수정보통보-" + getFormattedDate(new Date()) + ".xls");
                // ACTIONS.dispatch(ACTIONS.EXCEL_DOWNLOAD);
            },
            "search-view-clear": function () {
                if(sessionJson.userCd == "system" || sessionJson.jisaCode == "00") {
                    $("#jisaCode").val("");
                }
                $("#reqGubun").val("");
                $("#reqDate").val(getFormattedDate(new Date()));
                fnObj.gridView01.setPageData({pageNumber: 0});
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
        this.jisaCode = $("#jisaCode");
        this.reqGubun = $("#reqGubun");
        this.reqDate = $("#reqDate");
        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCode").attr('readonly', true);
            $("#jisaCode").attr('disabled', true);
            $("#jisaCode").val(sessionJson.jisaCode);
        }

        $("#reqDate").val(getFormattedDate(new Date()));

        axboot.buttonClick(this, "data-searchview-btn", {
            "modal": function () {
                ACTIONS.dispatch(ACTIONS.MODAL_OPEN)
            }
        });
    },
    getData: function () {
        return {
            jisaCode: this.jisaCode.val(),
            reqGubun: this.reqGubun.val(),
            reqDate: this.reqDate.val()
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
            showRowSelector: false,
            frozenColumnIndex: 0,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {key: 'reqDate', label: '인수일자', width: 100, align: 'center'},
                {
                    key: 'reqGubun', label: '신청구분', width: 130, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["REQ_GUBUN"].map[this.value];
                    }
                },
                {
                    key: 'jisaCode', label: '지사명', width: 70, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["JISA_CODE"].map[this.value];
                    }
                },
                {
                    key: 'branchCode', label: '상대점명', width: 150, align: 'left',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["RECEIVE_BRANCH_CODE"].map[this.value];
                    }
                },
                {
                    key: 'manlessBranchCode', label: '무인점명', width: 150, align: 'left',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["MANLESS_BRANCH_CODE"].map[this.value];
                    }
                },
                {
                    key: 'stextSendGubun', label: '전송여부', width: 100, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["STEXT_SEND_GUBUN"].map[this.value];
                    }
                },
                {
                    label: '권종별 수령금액', align: 'center',
                    columns: [
                        {
                            key: 'cashTypeCode1', label: '권종코드1', width: 90, align: 'center',
                            formatter: function formatter() {
                                return parent.COMMON_CODE["AMT_CODE"].map[this.value];
                            }
                        },
                        {
                            key: 'cashTypeCode1RecvAmt',
                            label: '권종코드1 수령금액',
                            width: 130,
                            align: 'right',
                            formatter: "money"
                        },
                        {
                            key: 'cashTypeCode2', label: '권종코드2', width: 90, align: 'center',
                            formatter: function formatter() {
                                return parent.COMMON_CODE["AMT_CODE"].map[this.value];
                            }
                        },
                        {
                            key: 'cashTypeCode2RecvAmt',
                            label: '권종코드2 수령금액',
                            width: 130,
                            align: 'right',
                            formatter: "money"
                        },
                        {
                            key: 'cashTypeCode3', label: '권종코드3', width: 90, align: 'center',
                            formatter: function formatter() {
                                return parent.COMMON_CODE["AMT_CODE"].map[this.value];
                            }
                        },
                        {
                            key: 'cashTypeCode3RecvAmt',
                            label: '권종코드3 수령금액',
                            width: 130,
                            align: 'right',
                            formatter: "money"
                        },
                        {
                            key: 'cashTypeCode4', label: '권종코드4', width: 90, align: 'center',
                            formatter: function formatter() {
                                return parent.COMMON_CODE["AMT_CODE"].map[this.value];
                            }
                        },
                        {
                            key: 'cashTypeCode4RecvAmt',
                            label: '권종코드4 수령금액',
                            width: 130,
                            align: 'right',
                            formatter: "money"
                        },
                        {
                            key: 'cashTypeCode5', label: '권종코드5', width: 90, align: 'center',
                            formatter: function formatter() {
                                return parent.COMMON_CODE["AMT_CODE"].map[this.value];
                            }
                        },
                        {
                            key: 'cashTypeCode5RecvAmt',
                            label: '권종코드5 수령금액',
                            width: 130,
                            align: 'right',
                            formatter: "money"
                        }
                    ]
                },
                {
                    label: '카드수령매수', align: 'center',
                    columns: [
                        {key: 's20GCardRecvCount', label: 'S20(일반)', width: 90, align: 'center', formatter: "money"},
                        {key: 's20TCardRecvCount', label: 'S20(후불교통)', width: 90, align: 'center', formatter: "money"},
                        {
                            key: 's20pinkGCardRecvCount',
                            label: 'S20핑크(일반)',
                            width: 90,
                            align: 'center',
                            formatter: "money"
                        },
                        {
                            key: 's20pinkTCardRecvCount',
                            label: 'S20핑크(후불교통)',
                            width: 90,
                            align: 'center',
                            formatter: "money"
                        },
                        {key: 'loveCardRecvCount', label: 'LOVE카드', width: 90, align: 'center', formatter: "money"},
                        {
                            key: 'hipointCardRecvCount',
                            label: 'HIPOINT카드',
                            width: 90,
                            align: 'center',
                            formatter: "money"
                        },
                        {key: 'scrtyCardRecvCount', label: '보안카드', width: 90, align: 'center', formatter: "money"}
                    ]
                },
                {key: 'cashSendingCarNo', label: '현금수송 차량번호', width: 120, align: 'center'},
                {
                    label: '인수책임자', align: 'center',
                    columns: [
                        {key: 'chargeEmpName', label: '성명', width: 100, align: 'center'},
                        {key: 'chargeEmpRegno', label: '주민등록번호', width: 150, align: 'center'},
                        {key: 'chargeEmpPhotoUrl', label: '사진파일', width: 150, align: 'left'},
                        {key: 'digitalSignUrl', label: '전자인감/서명파일', width: 150, align: 'left'}
                    ]
                },
                {key: 'txId', label: '업데이트일시', width: 130, align: 'center'}
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex);
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
                return this.txId && this.branchCode && this.terminalNo && this.reqDate;
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
 * formView01
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

        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });

        if(sessionJson.userCd != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCodeForm").attr('readonly', true);
            $("#jisaCodeForm").attr('disabled', true);
            // $("#jisaCodeForm").val(sessionJson.jisaCode);
            this.setSingleData("jisaCode", sessionJson.jisaCode);
        }

        this.setSingleData("reqDate", getFormattedDate(new Date()));

        axboot.buttonClick(this, "data-form-view-01-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
            "form-add": function () {
                if (fnObj.formView01.getData().stextSendGubun == 1) {
                    formError("전송완료된 보고서입니다.")
                } else {
                    ACTIONS.dispatch(ACTIONS.FORM_STEXT_SAVE);
                }
            },
            "form-save": function () {
                if (fnObj.formView01.getData().stextSendGubun == 1) {
                    formError("전송완료된 보고서입니다.")
                } else {
                    ACTIONS.dispatch(ACTIONS.FORM_SAVE);
                }
            },
            "form_modal": function () {
                ACTIONS.dispatch(ACTIONS.FORM_MODAL_OPEN);
            }
        });

        $('#cashType').change(
            function () {
                if ($('#cashType').val() == 1) {
                    cashTypeChange($('#cashType').val(), "13", "14", "15", "16", "");
                } else if ($('#cashType').val() == 2) {
                    cashTypeChange($('#cashType').val(), "27", "21", "22", "23", "28");
                } else if ($('#cashType').val() == 3) {
                    cashTypeChange($('#cashType').val(), "34", "35", "36", "37", "");
                } else if ($('#cashType').val() == 4) {
                    cashTypeChange($('#cashType').val(), "41", "42", "43", "44", "");
                } else {
                    cashTypeChange($('#cashType').val(), null, null, null, null, null);
                }

            });

        $('#cashTypeCode1RecvAmt').change(
            function () {
                sumCashRecvAmt();
            }
        );
        $('#cashTypeCode2RecvAmt').change(
            function () {
                sumCashRecvAmt();
            }
        );
        $('#cashTypeCode3RecvAmt').change(
            function () {
                sumCashRecvAmt();
            }
        );
        $('#cashTypeCode4RecvAmt').change(
            function () {
                sumCashRecvAmt();
            }
        );
        $('#cashTypeCode5RecvAmt').change(
            function () {
                sumCashRecvAmt();
            }
        );

        $('#cashType').val(2);
        cashTypeChange("2", "27", "21", "22", "23", "28");

        var parentData = fnObj.searchView.getData();
        parentData.reqDate = fnObj.formView01.get("reqDate");
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/cash/sh03001190",
            data: $.extend({nextSendingAmt: true}, parentData),
            callback: function (res) {
                console.log("data: " + res);
                nextDayCashSendingAmt = res;
            },
            options: {
                onError: viewError
            }
        });

        $('#reqDateForm').change(
            function () {
                var parentData = fnObj.searchView.getData();
                parentData.reqDate = fnObj.formView01.get("reqDate");
                axboot.ajax({
                    type: "GET",
                    url: "/api/v1//mng/cash/sh03001190",
                    data: $.extend({nextSendingAmt: true}, parentData),
                    callback: function (res) {
                        console.log("data: " + res);
                        nextDayCashSendingAmt = res;
                    },
                    options: {
                        onError: viewError
                    }
                });
            }
        );

    },
    initEvent: function () {
        var _this = this;
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        return $.extend({}, data);
    },
    get: function (dataPath) {
        return this.model.get(dataPath);
    },
    setData: function (data) {

        if (typeof data === "undefined") data = this.getDefaultData();
        data = $.extend({}, data);

        if (typeof data.chargeEmpPhotoUrlFile != "undefined") {
            UPLOAD.uploadedFiles.push(data.chargeEmpPhotoUrlFile);
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
    setSingleData: function (dataPath, value) {
        this.model.set(dataPath, value);
    },
    validate: function () {
        var rs = this.model.validate();
        var title;
        var message;

        if (rs.error) {
            rs.error[0].jquery.focus();
            formError(rs.error[0].jquery.attr("title") + '을(를) 입력해주세요.');
            return false;
        } else if ($("#chargeEmpRegno").val().length < 6) {
            message = '\n' + '생년월일 6자리는 필수 입력조건입니다.';
            $("#chargeEmpRegno").focus();
            formError(message);
            return false;
        } else if (UPLOAD.uploadedFiles.length == 0) {
            message = '\n' + '인수책임자 사진파일은 필수 입력조건입니다.';
            $("#cornerNameForm").focus();
            formError(message);
            return false;
        } else if (UPLOAD2.uploadedFiles.length == 0) {
            message = '\n' + '인수책임자 전자인감은 필수 입력조건입니다.';
            $("#cornerNameForm").focus();
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

var pageSearchAndviewError = function (err) {
    fnObj.gridView01.setPageData({pageNumber: 0});
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    viewError(err);
}

function getFormattedDate(date) {
    var day;
    var tempDate;

    date.setDate(date.getDate() + 1);
    tempDate = date.getDate();
    day = tempDate.toString();

    var year = date.getFullYear();
    var month = (1 + date.getMonth()).toString();
    month = month.length > 1 ? month : '0' + month;
    day = day.length > 1 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}

function cashTypeChange(data, value1, value2, value3, value4, value5) {

    $("#cashTypeCode1 option").remove();
    $("#cashTypeCode2 option").remove();
    $("#cashTypeCode3 option").remove();
    $("#cashTypeCode4 option").remove();
    $("#cashTypeCode5 option").remove();

    $("#cashTypeCode1").attr('disabled', true);
    $("#cashTypeCode2").attr('disabled', true);
    $("#cashTypeCode3").attr('disabled', true);
    $("#cashTypeCode4").attr('disabled', true);
    $("#cashTypeCode5").attr('disabled', true);

    if (data == 1) {
        $("#cashTypeCode1").append("<option value=''>선택</option>", "<option value='13'>10만원권</option>", "<option value='14'>30만원권</option>", "<option value='15'>50만원권</option>", "<option value='16'>100만원권</option>");
        $("#cashTypeCode2").append("<option value=''>선택</option>", "<option value='13'>10만원권</option>", "<option value='14'>30만원권</option>", "<option value='15'>50만원권</option>", "<option value='16'>100만원권</option>");
        $("#cashTypeCode3").append("<option value=''>선택</option>", "<option value='13'>10만원권</option>", "<option value='14'>30만원권</option>", "<option value='15'>50만원권</option>", "<option value='16'>100만원권</option>");
        $("#cashTypeCode4").append("<option value=''>선택</option>", "<option value='13'>10만원권</option>", "<option value='14'>30만원권</option>", "<option value='15'>50만원권</option>", "<option value='16'>100만원권</option>");
        $("#cashTypeCode5").append("<option value=''>선택</option>", "<option value='13'>10만원권</option>", "<option value='14'>30만원권</option>", "<option value='15'>50만원권</option>", "<option value='16'>100만원권</option>");
    } else if (data == 2) {
        $("#cashTypeCode1").append("<option value=''>선택</option>", "<option value='24'>십원권</option>", "<option value='25'>오십원권</option>", "<option value='26'>백원권</option>", "<option value='27'>오백원권</option>", "<option value='21'>천원권</option>", "<option value='22'>오천원권</option>", "<option value='23'>만원권</option>", "<option value='28'>오만원권</option>");
        $("#cashTypeCode2").append("<option value=''>선택</option>", "<option value='24'>십원권</option>", "<option value='25'>오십원권</option>", "<option value='26'>백원권</option>", "<option value='27'>오백원권</option>", "<option value='21'>천원권</option>", "<option value='22'>오천원권</option>", "<option value='23'>만원권</option>", "<option value='28'>오만원권</option>");
        $("#cashTypeCode3").append("<option value=''>선택</option>", "<option value='24'>십원권</option>", "<option value='25'>오십원권</option>", "<option value='26'>백원권</option>", "<option value='27'>오백원권</option>", "<option value='21'>천원권</option>", "<option value='22'>오천원권</option>", "<option value='23'>만원권</option>", "<option value='28'>오만원권</option>");
        $("#cashTypeCode4").append("<option value=''>선택</option>", "<option value='24'>십원권</option>", "<option value='25'>오십원권</option>", "<option value='26'>백원권</option>", "<option value='27'>오백원권</option>", "<option value='21'>천원권</option>", "<option value='22'>오천원권</option>", "<option value='23'>만원권</option>", "<option value='28'>오만원권</option>");
        $("#cashTypeCode5").append("<option value=''>선택</option>", "<option value='24'>십원권</option>", "<option value='25'>오십원권</option>", "<option value='26'>백원권</option>", "<option value='27'>오백원권</option>", "<option value='21'>천원권</option>", "<option value='22'>오천원권</option>", "<option value='23'>만원권</option>", "<option value='28'>오만원권</option>");
    } else if (data == 3) {
        $("#cashTypeCode1").append("<option value=''>선택</option>", "<option value='34'>10달러</option>", "<option value='35'>20달러</option>", "<option value='36'>50달러</option>", "<option value='37'>100달러</option>");
        $("#cashTypeCode2").append("<option value=''>선택</option>", "<option value='34'>10달러</option>", "<option value='35'>20달러</option>", "<option value='36'>50달러</option>", "<option value='37'>100달러</option>");
        $("#cashTypeCode3").append("<option value=''>선택</option>", "<option value='34'>10달러</option>", "<option value='35'>20달러</option>", "<option value='36'>50달러</option>", "<option value='37'>100달러</option>");
        $("#cashTypeCode4").append("<option value=''>선택</option>", "<option value='34'>10달러</option>", "<option value='35'>20달러</option>", "<option value='36'>50달러</option>", "<option value='37'>100달러</option>");
        $("#cashTypeCode5").append("<option value=''>선택</option>", "<option value='34'>10달러</option>", "<option value='35'>20달러</option>", "<option value='36'>50달러</option>", "<option value='37'>100달러</option>");
    } else if (data == 4) {
        $("#cashTypeCode1").append("<option value=''>선택</option>", "<option value='41'>1000엔</option>", "<option value='42'>2000엔</option>", "<option value='43'>5000엔</option>", "<option value='44'>10000엔</option>");
        $("#cashTypeCode2").append("<option value=''>선택</option>", "<option value='41'>1000엔</option>", "<option value='42'>2000엔</option>", "<option value='43'>5000엔</option>", "<option value='44'>10000엔</option>");
        $("#cashTypeCode3").append("<option value=''>선택</option>", "<option value='41'>1000엔</option>", "<option value='42'>2000엔</option>", "<option value='43'>5000엔</option>", "<option value='44'>10000엔</option>");
        $("#cashTypeCode4").append("<option value=''>선택</option>", "<option value='41'>1000엔</option>", "<option value='42'>2000엔</option>", "<option value='43'>5000엔</option>", "<option value='44'>10000엔</option>");
        $("#cashTypeCode5").append("<option value=''>선택</option>", "<option value='41'>1000엔</option>", "<option value='42'>2000엔</option>", "<option value='43'>5000엔</option>", "<option value='44'>10000엔</option>");
    } else {
        $("#cashTypeCode1").attr('disabled', false);
        $("#cashTypeCode2").attr('disabled', false);
        $("#cashTypeCode3").attr('disabled', false);
        $("#cashTypeCode4").attr('disabled', false);
        $("#cashTypeCode5").attr('disabled', false);
    }
    fnObj.formView01.setSingleData("cashTypeCode1", value1);
    fnObj.formView01.setSingleData("cashTypeCode2", value2);
    fnObj.formView01.setSingleData("cashTypeCode3", value3);
    fnObj.formView01.setSingleData("cashTypeCode4", value4);
    fnObj.formView01.setSingleData("cashTypeCode5", value5);

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

var sumCashRecvAmt = function () {
    var sum = 0;
    if ($('#cashTypeCode1RecvAmt').val() != undefined && $('#cashTypeCode1RecvAmt').val() != "") {
        sum += Number($('#cashTypeCode1RecvAmt').val().replace(/,/g, ''));
    }

    if ($('#cashTypeCode2RecvAmt').val() != undefined && $('#cashTypeCode2RecvAmt').val() != "") {
        sum += Number($('#cashTypeCode2RecvAmt').val().replace(/,/g, ''));
    }
    if ($('#cashTypeCode3RecvAmt').val() != undefined && $('#cashTypeCode3RecvAmt').val() != "") {
        sum += Number($('#cashTypeCode3RecvAmt').val().replace(/,/g, ''));
    }
    if ($('#cashTypeCode4RecvAmt').val() != undefined && $('#cashTypeCode4RecvAmt').val() != "") {
        sum += Number($('#cashTypeCode4RecvAmt').val().replace(/,/g, ''));
    }
    if ($('#cashTypeCode5RecvAmt').val() != undefined && $('#cashTypeCode5RecvAmt').val() != "") {
        sum += Number($('#cashTypeCode5RecvAmt').val().replace(/,/g, ''));
    }

    $('#cashTypeCodeRecvAmtSumValue').val(numberWithCommas(sum));
    console.log(sum);
    console.log(nextDayCashSendingAmt);
    if (sum == nextDayCashSendingAmt) {
        $('#cashTypeCodeRecvAmtSumValueIsEqual').val("일치");
    } else {
        $('#cashTypeCodeRecvAmtSumValueIsEqual').val("불일치");
    }
//    fnObj.formView01.setSingleData("cashTypeCodeRecvAmtSumValue", sum);

}
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
var UPLOAD;

var UPLOAD2;

var nextDayCashSendingAmt = 0;