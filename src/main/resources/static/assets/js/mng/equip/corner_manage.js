var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1//mng/equip/corner_manage",
            data: $.extend({}, this.searchView.getData(), this.gridView01.getPageData()),
            callback: function (res) {
                caller.gridView01.setData(res);
                PARAMS = null;
            },
            options: {
                onError: viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        var saveList = [].concat(caller.gridView01.getData("modified"));
        axboot.ajax({
            type: "PUT",
            url: "/api/v1//mng/equip/corner_manage",
            data: JSON.stringify(saveList),
            callback: function (res) {
                fnObj.gridView01.setPageData({pageNumber: 0});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push("저장 작업이 완료되었습니다.");
            },
            options: {
                onError: viewError
            }
        });
    },
    PAGE_DELETE: function (caller, act, data) {
        caller.gridView01.delRow("selected");
        var saveList = [].concat(caller.gridView01.getData("deleted"));
        axboot.ajax({
            type: "PUT",
            url: "/api/v1//mng/equip/corner_manage",
            data: JSON.stringify(saveList),
            callback: function (res) {
                fnObj.gridView01.setPageData({pageNumber: 0});
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push("삭제 작업이 완료되었습니다.");
            },
            options: {
                onError: pageSearchAndViewError
            }
        });
    },
    ITEM_ADD: function (caller, act, data) {
        caller.gridView01.addRow();
    },
    FORM_SAVE: function (caller, act, data) {
        if (caller.formView01.validate()) {
            var parentData = caller.formView01.getData();
            console.log("form1save", parentData);
            axboot
                .call({
                    type: "PUT",
                    url: "/api/v1//mng/equip/sh05001110",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
                    }
                })
                .done(function () {
                    caller.formView01.clear();
                    axToast.push("저장 작업이 완료되었습니다.");
                    // setDisabledFormSaveBtn(true);
                    //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                });
        }
    },
    FORM_SAVE2: function (caller, act, data) {
        if (caller.formView02.validate()) {
            var formData = caller.formView02.getData();

            var parentData = {
                jisaCode : formData.jisaCode,
                branchCode : formData.branchCode,
                cornerCode : formData.cornerCode,
                cornerName : formData.cornerName,
                overhaulDate : formData.overhaulDate,

                outsideBillboardPhoto : UPLOAD.uploadedFiles.length > 0 ? UPLOAD.uploadedFiles[0].id : "",
                gatePhoto : UPLOAD2.uploadedFiles.length > 0 ? UPLOAD2.uploadedFiles[0].id : "",
                floarPhoto : UPLOAD5.uploadedFiles.length > 0 ? UPLOAD5.uploadedFiles[0].id : "",
                boothLeftPhoto : UPLOAD3.uploadedFiles.length > 0 ? UPLOAD3.uploadedFiles[0].id : "",
                boothRightPhoto : UPLOAD4.uploadedFiles.length > 0 ? UPLOAD4.uploadedFiles[0].id : "",
                ceilingPhoto : UPLOAD6.uploadedFiles.length > 0 ? UPLOAD6.uploadedFiles[0].id : "",
                terminalTopPhoto : UPLOAD7.uploadedFiles.length > 0 ? UPLOAD7.uploadedFiles[0].id : "",
                terminalBottomPhoto : UPLOAD8.uploadedFiles.length > 0 ? UPLOAD8.uploadedFiles[0].id : "",
            }
            axboot
                .call({
                    type: "PUT",
                    url: "/api/v1//mng/equip/sh05001120",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
                    }
                })
                .done(function () {
                    caller.formView02.clear();
                    UPLOAD.removeFile(0);
                    UPLOAD2.removeFile(0);
                    UPLOAD3.removeFile(0);
                    UPLOAD4.removeFile(0);
                    UPLOAD5.removeFile(0);
                    UPLOAD6.removeFile(0);
                    UPLOAD7.removeFile(0);
                    UPLOAD8.removeFile(0);
                    axToast.push("저장 작업이 완료되었습니다.");

                    //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    // setDisabledFormSaveBtn(true);
                });
        }
    },
    FORM_SAVE3: function (caller, act, data) {
        if (caller.formView03.validate()) {
            var parentData = caller.formView03.getData();
            console.log("form3save", parentData);
            axboot
                .call({
                    type: "PUT",
                    url: "/api/v1//mng/equip/sh05001130",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
                    }
                })
                .done(function () {
                    caller.formView03.clear();
                    axToast.push("저장 작업이 완료되었습니다.");

                    //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    // setDisabledFormSaveBtn(true);
                });
        }
    },
    FORM_SAVE4: function (caller, act, data) {
        if (caller.formView04.validate()) {
            var formData = caller.formView04.getData();

            var parentData = {
                jisaCode : formData.jisaCode,
                branchCode : formData.branchCode,
                cornerCode : formData.cornerCode,
                cornerName : formData.cornerName,
                overhaulDate : formData.overhaulDate,

                terminalFrontGlass : UPLOAD9.uploadedFiles.length > 0 ? UPLOAD9.uploadedFiles[0].id : "",
                terminalFront : UPLOAD10.uploadedFiles.length > 0 ? UPLOAD10.uploadedFiles[0].id : "",
                terminalCracks : UPLOAD11.uploadedFiles.length > 0 ? UPLOAD11.uploadedFiles[0].id : "",
                monitor : UPLOAD12.uploadedFiles.length > 0 ? UPLOAD12.uploadedFiles[0].id : "",
                bnkbPartEntranceCracks : UPLOAD13.uploadedFiles.length > 0 ? UPLOAD13.uploadedFiles[0].id : "",
                cardPartEntranceCracks : UPLOAD14.uploadedFiles.length > 0 ? UPLOAD14.uploadedFiles[0].id : "",
                intercom : UPLOAD15.uploadedFiles.length > 0 ? UPLOAD15.uploadedFiles[0].id : "",
                boardCradle : UPLOAD16.uploadedFiles.length > 0 ? UPLOAD16.uploadedFiles[0].id : "",
                branchFloor : UPLOAD17.uploadedFiles.length > 0 ? UPLOAD17.uploadedFiles[0].id : "",
                insideWall : UPLOAD18.uploadedFiles.length > 0 ? UPLOAD18.uploadedFiles[0].id : "",
                outsideWall : UPLOAD19.uploadedFiles.length > 0 ? UPLOAD19.uploadedFiles[0].id : "",
                branchGlass : UPLOAD20.uploadedFiles.length > 0 ? UPLOAD20.uploadedFiles[0].id : "",
                ceiling : UPLOAD21.uploadedFiles.length > 0 ? UPLOAD21.uploadedFiles[0].id : "",
                fluorescentLight : UPLOAD22.uploadedFiles.length > 0 ? UPLOAD22.uploadedFiles[0].id : "",
                stickingBillboard : UPLOAD23.uploadedFiles.length > 0 ? UPLOAD23.uploadedFiles[0].id : "",
                garbageCan : UPLOAD24.uploadedFiles.length > 0 ? UPLOAD24.uploadedFiles[0].id : "",
                coolerHeater : UPLOAD25.uploadedFiles.length > 0 ? UPLOAD25.uploadedFiles[0].id : "",
                branchPeriphery : UPLOAD26.uploadedFiles.length > 0 ? UPLOAD26.uploadedFiles[0].id : "",
            }
            axboot
                .call({
                    type: "PUT",
                    url: "/api/v1//mng/equip/sh05001140",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
                    }
                })
                .done(function () {
                    caller.formView04.clear();
                    UPLOAD9.removeFile(0);
                    UPLOAD10.removeFile(0);
                    UPLOAD11.removeFile(0);
                    UPLOAD12.removeFile(0);
                    UPLOAD13.removeFile(0);
                    UPLOAD14.removeFile(0);
                    UPLOAD15.removeFile(0);
                    UPLOAD16.removeFile(0);
                    UPLOAD17.removeFile(0);
                    UPLOAD18.removeFile(0);
                    UPLOAD19.removeFile(0);
                    UPLOAD20.removeFile(0);
                    UPLOAD21.removeFile(0);
                    UPLOAD22.removeFile(0);
                    UPLOAD23.removeFile(0);
                    UPLOAD24.removeFile(0);
                    UPLOAD25.removeFile(0);
                    UPLOAD26.removeFile(0);
                    axToast.push("저장 작업이 완료되었습니다.");
                    //setDisabledFormSaveBtn(true);
                    //ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
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
            }
        });
    },
    FORM_CLEAR2: function (caller, act, data) {
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                axDialog.close();
                UPLOAD.removeFile(0);
                UPLOAD2.removeFile(0);
                UPLOAD3.removeFile(0);
                UPLOAD4.removeFile(0);
                UPLOAD5.removeFile(0);
                UPLOAD6.removeFile(0);
                UPLOAD7.removeFile(0);
                UPLOAD8.removeFile(0);
                caller.formView02.clear();
            }
        });
    },
    FORM_CLEAR3: function (caller, act, data) {
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                axDialog.close();
                caller.formView03.clear();
            }
        });
    },
    FORM_CLEAR4: function (caller, act, data) {
        axDialog.confirm({
            msg: "정말 양식을 초기화 하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                axDialog.close();
                UPLOAD9.removeFile(0);
                UPLOAD10.removeFile(0);
                UPLOAD11.removeFile(0);
                UPLOAD12.removeFile(0);
                UPLOAD13.removeFile(0);
                UPLOAD14.removeFile(0);
                UPLOAD15.removeFile(0);
                UPLOAD16.removeFile(0);
                UPLOAD17.removeFile(0);
                UPLOAD18.removeFile(0);
                UPLOAD19.removeFile(0);
                UPLOAD20.removeFile(0);
                UPLOAD21.removeFile(0);
                UPLOAD22.removeFile(0);
                UPLOAD23.removeFile(0);
                UPLOAD24.removeFile(0);
                UPLOAD25.removeFile(0);
                UPLOAD26.removeFile(0);
                caller.formView04.clear();
            }
        });
    },
    FORM_MODAL_OPEN: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_TERMINAL_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisa": caller.formView01.get("jisaCode")
                };
            },
            callback: function (data) {
                caller.formView01.setSingleData("jisaCode", data.jisaCode);
                caller.formView01.setSingleData("branchName", data.branchName);
                caller.formView01.setSingleData("branchCode", data.branchCode);
                caller.formView01.setSingleData("cornerName", data.cornerName);
                caller.formView01.setSingleData("cornerCode", data.cornerCode);

                this.close();
            }
        });
    },
    FORM_MODAL_OPEN2: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_TERMINAL_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisa": caller.formView02.get("jisaCode")
                };
            },
            callback: function (data) {
                caller.formView02.setSingleData("jisaCode", data.jisaCode);
                caller.formView02.setSingleData("branchName", data.branchName);
                caller.formView02.setSingleData("branchCode", data.branchCode);
                caller.formView02.setSingleData("cornerName", data.cornerName);
                caller.formView02.setSingleData("cornerCode", data.cornerCode);

                this.close();
            }
        });
    },
    FORM_MODAL_OPEN3: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_TERMINAL_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisa": caller.formView03.get("jisaCode")
                };
            },
            callback: function (data) {
                caller.formView03.setSingleData("jisaCode", data.jisaCode);
                caller.formView03.setSingleData("branchName", data.branchName);
                caller.formView03.setSingleData("branchCode", data.branchCode);
                caller.formView03.setSingleData("cornerName", data.cornerName);
                caller.formView03.setSingleData("cornerCode", data.cornerCode);

                this.close();
            }
        });
    },
    FORM_MODAL_OPEN4: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_TERMINAL_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisa": caller.formView04.get("jisaCode")
                };
            },
            callback: function (data) {
                caller.formView04.setSingleData("jisaCode", data.jisaCode);
                caller.formView04.setSingleData("branchName", data.branchName);
                caller.formView04.setSingleData("branchCode", data.branchCode);
                caller.formView04.setSingleData("cornerName", data.cornerName);
                caller.formView04.setSingleData("cornerCode", data.cornerCode);

                this.close();
            }
        });
    },
    ITEM_CLICK: function (caller, act, data) {

        refreshCalendar(data);
        
        // PARAMS = data;
    },
    ADD_PLAN: function (caller, act, data) {
        console.log("params", PARAMS);
        axboot.modal.open({
            modalType: "CORNER_MANAGE_M01",
            param: PARAMS,
            callback: function (data) {
                console.log("callback", data);
                refreshCalendar(data);
            }
        });
    },
    MODAL_OPEN: function (caller, act, data) {
        axboot.modal.open({
            modalType: "SEARCH_CORNER_MODAL",
            param: "",
            sendData: function () {
                return {
                    "jisa":  $("#jisaCode").val()
                };
            },
            callback: function (data) {
                //console.log('MODAL_OPEN: ', data);
                $("#branchName").val(data.branchName);
                $("#branchCode").val(data.branchCode);
                $("#cornerName").val(data.cornerName);
                $("#jisaCode").val(data.jisaCode);
                this.close();
            }
        });
    },
    EXCEL_DOWNLOAD: function (caller, act, data) {
        var params = buildParams($.extend({}, this.searchView.getData()));
        window.location = CONTEXT_PATH + "/api/v1//mng/equip/corner_manage/download/excel?" + params;
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

// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    $('#calendar').fullCalendar({
        header: {
            left: 'prev, next, today',
            center: 'title',
            right: 'month, listMonth'
        },
        //defaultDate: '2017-02-12',
        contentHeight: 540,
        locale: 'ko',
        buttonIcons: true, // show the prev/next text
        weekNumbers: false,
        selectable: true,
        unselectAuto: true,
        unselectCancel: $('#paln-button-group'),
        navLinks: false, // can click day/week names to navigate views
        editable: false,
        eventLimit: true, // allow "more" link when too many events
        eventDrop: function(event, delta, revertFunc) {
            console.log('event: ', event);
            console.log('delta: ', delta);
            //alert("[드래그 이벤트]  TX ID :: " + event.txId + " 타이틀 :: " +  event.title + "  일자 :: " + event.start.format());
        },
        eventClick: function(event, element) {
            //alert("[클릭 이벤트]  TX ID :: " + event.txId + " 타이틀 :: " +  event.title + "  일자 :: " + event.start.format());
            var data = {
                jisaCode : PARAMS.jisaCode,
                branchCode : PARAMS.branchCode,
                branchName : PARAMS.branchName,
                cornerCode : PARAMS.cornerCode,
                cornerName : PARAMS.cornerName,
                overhaulDate : event.start.format()
            }

            _this.formView01.clear();
            _this.formView01.setData(data);
            _this.formView02.clear();
            _this.formView02.setData(data);
            _this.formView03.clear();
            _this.formView03.setData(data);
            _this.formView04.clear();
            _this.formView04.setData(data);

            loadEventData(_this, data);

            var now = new Date();
            var today = dateToString(now);
            setDisabledFormSaveBtn(false);
        },
        select: function(start, end) {
            // $('#calendar').fullCalendar('unselect');
            //$('#calendar').fullCalendar('select', start, end);
           // alert("test");
            startDate = start.format();
            var date1 = new Date(end.format());
            date1.setDate(date1.getDate() - 1);
            endDate = dateToString(date1);
        },
        unselect: function() {
            startDate = null;
            endDate = null;
          //  alert("test");

        },
        dayClick: function(date, jsEvent, view) {
         //   alert("test");
            // $('#calendar').fullCalendar('select', date, date);
        },
        navLinkWeekClick: function(weekStart, jsEvent) {
            alert("test");
        },
        viewRender: function (view, element) {
            var moment = $('#calendar').fullCalendar('getDate');

            var date1 = new Date(moment.format());
            var curMonthNew = dateToString(date1);
            if (curMonth == null) {
                curMonth = curMonthNew;
            }
            if (curMonthNew != curMonth) {
                $('#calendar').fullCalendar('removeEvents');
            }
            curMonth = curMonthNew;

        }

    });


    this.pageButtonView.initView();
    this.searchView.initView();
    this.gridView01.initView();
    this.formView01.initView();
    this.formView02.initView();
    this.formView03.initView();
    this.formView04.initView();

    var API_SERVER = CONTEXT_PATH;

    UPLOAD = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload1"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
            action: "/api/v1//mng/equip/corner_manage/upload",
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
    UPLOAD3 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload3"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload3"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload3"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD3.removeFile(fileIndex);
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
    UPLOAD4 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload4"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload4"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload4"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD4.removeFile(fileIndex);
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
    UPLOAD5 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload5"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload5"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload5"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD5.removeFile(fileIndex);
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
    UPLOAD6 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload6"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload6"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload6"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD6.removeFile(fileIndex);
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
    UPLOAD7 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload7"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload7"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload7"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD7.removeFile(fileIndex);
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
    UPLOAD8 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload8"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload8"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload8"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD8.removeFile(fileIndex);
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

    UPLOAD9 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload9"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload9"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload9"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD9.removeFile(fileIndex);
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
    UPLOAD10 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload10"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload10"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload10"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD10.removeFile(fileIndex);
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
    UPLOAD11 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload11"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload11"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload11"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD11.removeFile(fileIndex);
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
    UPLOAD12 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload12"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload12"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload12"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD12.removeFile(fileIndex);
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
    UPLOAD13 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload13"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload13"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload13"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD13.removeFile(fileIndex);
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
    UPLOAD14 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload14"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload14"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload14"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD14.removeFile(fileIndex);
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
    UPLOAD15 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload15"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload15"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload15"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD15.removeFile(fileIndex);
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
    UPLOAD16 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload16"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload16"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload16"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD16.removeFile(fileIndex);
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

    UPLOAD17 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload17"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload17"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload17"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD17.removeFile(fileIndex);
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
    UPLOAD18 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload18"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload18"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload18"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD18.removeFile(fileIndex);
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
    UPLOAD19 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload19"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload19"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload19"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD19.removeFile(fileIndex);
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
    UPLOAD20 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload20"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload20"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload20"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD20.removeFile(fileIndex);
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
    UPLOAD21 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload21"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload21"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload21"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD21.removeFile(fileIndex);
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
    UPLOAD22 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload22"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload22"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload22"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD22.removeFile(fileIndex);
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
    UPLOAD23 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload23"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload23"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload23"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD23.removeFile(fileIndex);
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
    UPLOAD24 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload24"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload24"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload24"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD24.removeFile(fileIndex);
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
    UPLOAD25 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload25"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload25"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload25"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD25.removeFile(fileIndex);
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
    UPLOAD26 = new ax5.ui.uploader({
        debug: false,
        target: $('[data-ax5uploader="upload26"]'),
        form: {
            action: "/api/v1//mng/equip/corner_manage/upload",
            fileName: "file"
        },
        manualUpload: false,
        progressBox: false,
        progressBoxDirection: "left",
        dropZone: {
            target: $('[data-uploaded-box="upload26"]')
        },
        uploadedBox: {
            target: $('[data-uploaded-box="upload26"]'),
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
                supportedHTML5_emptyListMsg: '<div class="text-center" style="padding-top: 30px;">이미지를 선택하세요.</div>',
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
                            title: "AX5UI",
                            msg: "선택된 이미지를 삭제하시겠습니까?"
                        }, function () {
                            if (this.key == "ok") {
                                UPLOAD26.removeFile(fileIndex);
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
            "save": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            },
            "delete": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_DELETE);
            },
            "search-view-clear": function () {
                $("#filter").val("");
                if(sessionJson.userId == "system" || sessionJson.jisaCode == "00") {
                    $("#jisaCode").val("");
                }

                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
                $("#terminalNo").val("");

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
        this.jisaCode = $("#jisaCode");
        this.branchCode = $("#branchCode");

        axboot.buttonClick(this, "data-searchview-btn", {
            "modal": function () {
                ACTIONS.dispatch(ACTIONS.MODAL_OPEN)
            }
        });

        if(sessionJson.userId != "system" && sessionJson.jisaCode != "00") {
            $("#jisaCode").attr('readonly', true);
            $("#jisaCode").attr('disabled', true);
            $("#jisaCode").val(sessionJson.jisaCode);
        }

        $('#jisaCode').change(
            function(){
                $("#branchName").val("");
                $("#branchCode").val("");
                $("#cornerName").val("");
            });

        $('#branchCode').change(
            function(){
                $("#branchName").val("");
            });

        $('#terminalNo').change(
            function(){
                $("#cornerName").val("");
            });
    },
    getData: function () {
        return {
            filter: this.filter.val(),
            jisaCode: $("#jisaCode").val(),
            branchName: $("#branchName").val(),
            branchCode: $("#branchCode").val(),
            cornerName: $("#cornerName").val(),
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
            multipleSelect: false,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {key: 'jisaCode', label: '지사코드', width: 70, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["JISA_CODE"].map[this.value];
                    }
                },
                {key: 'branchCode', label: '지점코드', width: 80, align: 'center'},
                {key: 'branchName', label: '지점명', width: 100, align: 'left'},
                {key: 'cornerCode', label: '코너코드', width: 70, align: 'center'},
                {key: 'cornerName', label: '코너명', width: 100, align: 'left'},
                {key: 'placeGubun', label: '장소구분', width: 70, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["PLACE_GUBUN"].map[this.value];
                    }
                },
                {key: 'branchGubun', label: '점포구분', width: 80, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["BRANCH_GUBUN"].map[this.value];
                    }
                },
                // {key: 'specialStyleGubunName', label: '특수형구분', width: 80, align: 'center'},
                // {key: 'operTimeGubunName', label: '운영시간구분', width: 100, align: 'center'},
                {key: 'operStartTime', label: '운영시작시간', width: 80, align: 'center'},
                {key: 'operEndTime', label: '운영종료시간', width: 80, align: 'center'},
                // {key: 'checkOperEnableName', label: '수표운영여부', width: 100, align: 'center'},
                // {key: 'seviceFee', label: '용역료', width: 130, align: 'right', formatter: 'money'},
                {key: 'securityCorpCode', label: '경비사코드', width: 80, align: 'center',
                    formatter: function formatter() {
                        var value1 = this.value.replace(/(^\s*)|(\s*$)/gi, "");
                        return parent.COMMON_CODE["SECURITY_CORP"].map[value1];
                    }
                },
                {key: 'facHireEnable', label: '시설물임차여부', width: 100, align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE["FAC_HIRE_ENABLE"].map[this.value];
                    }
                },
                // {key: 'addr', label: '주소', width: 200, align: 'left'},
                // {key: 'unusl', label: '특이사항', width: 200, align: 'left'},
                // {key: 'operDay', label: '운영요일', width: 90, align: 'center'}
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, {selectedClear: true});
                    ACTIONS.dispatch(ACTIONS.ITEM_CLICK, this.item);
                }
            },
            onPageChange: function (pageNumber) {
                _this.setPageData({
                    pageNumber: pageNumber
                });
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            }
        });

        axboot.buttonClick(this, "data-grid-view-01-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "excel": function () {
                _this.excel("코너목록-"+getFormattedDate(new Date())+".xls");
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                return this.jisaCode && this.branchCode && this.cornerCode;
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

        axboot.buttonClick(this, "data-form-view-01-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
            "form-save": function () {
                ACTIONS.dispatch(ACTIONS.FORM_SAVE);
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
    get: function(dataPath) {
        return this.model.get(dataPath);
    },
    setData: function (data) {

        if (typeof data === "undefined") data = this.getDefaultData();
        data = $.extend({}, data);

        this.target.find('[data-ax-path="key"]').attr("readonly", "readonly");

        this.model.setModel(data);
        this.modelFormatter.formatting(); // 입력된 값을 포메팅 된 값으로 변경
        this.setSingleData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[this.get("branchCode")]);
    },
    setSingleData: function (dataPath, value) {
        this.model.set(dataPath, value);
    },
    validate: function () {
        var rs = this.model.validate();

        if (this.get("branchCode") == "" || typeof this.get("branchCode") === "undefined") {
            //$("#branchCode").focus();
            formError('\n' + '지점은 필수 입력조건입니다.\n' + '지점을 선택하세요.');
            return false;
        }

        if (rs.error) {
            rs.error[0].jquery.focus();
            formError(rs.error[0].jquery.attr("title") + '을(를) 입력해주세요.');
            return false;
        }

        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");

    }
});


/**
 * formView02
 */
fnObj.formView02 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {});
    },
    initView: function () {
        this.target = $("#formView02");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();

        axboot.buttonClick(this, "data-form-view-02-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR2);
            },
            "form-save": function () {
                ACTIONS.dispatch(ACTIONS.FORM_SAVE2);
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
    get: function(dataPath) {
        return this.model.get(dataPath);
    },
    setData: function (data) {

        if (typeof data === "undefined") data = this.getDefaultData();
        data = $.extend({}, data);

        if (typeof data.outsideBillboardPhotoFile != "undefined") {
            UPLOAD.uploadedFiles.push(data.outsideBillboardPhotoFile);
            UPLOAD.refreshImage();
        }
        if (typeof data.gatePhotoFile != "undefined") {
            UPLOAD2.uploadedFiles.push(data.gatePhotoFile);
            UPLOAD2.refreshImage();
        }
        if (typeof data.floarPhotoFile != "undefined") {
            UPLOAD5.uploadedFiles.push(data.floarPhotoFile);
            UPLOAD5.refreshImage();
        }
        if (typeof data.boothLeftPhotoFile != "undefined") {
            UPLOAD3.uploadedFiles.push(data.boothLeftPhotoFile);
            UPLOAD3.refreshImage();
        }
        if (typeof data.boothRightPhotoFile != "undefined") {
            UPLOAD4.uploadedFiles.push(data.boothRightPhotoFile);
            UPLOAD4.refreshImage();
        }
        if (typeof data.ceilingPhotoFile != "undefined") {
            UPLOAD6.uploadedFiles.push(data.ceilingPhotoFile);
            UPLOAD6.refreshImage();
        }
        if (typeof data.terminalTopPhotoFile != "undefined") {
            UPLOAD7.uploadedFiles.push(data.terminalTopPhotoFile);
            UPLOAD7.refreshImage();
        }
        if (typeof data.terminalBottomPhotoFile != "undefined") {
            UPLOAD8.uploadedFiles.push(data.terminalBottomPhotoFile);
            UPLOAD8.refreshImage();
        }

        this.target.find('[data-ax-path="key"]').attr("readonly", "readonly");

        this.model.setModel(data);
        this.modelFormatter.formatting(); // 입력된 값을 포메팅 된 값으로 변경
        this.setSingleData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[this.get("branchCode")]);
    },
    setSingleData: function (dataPath, value) {
        this.model.set(dataPath, value);
    },
    validate: function () {
        var rs = this.model.validate();

        if (this.get("branchCode") == "" || typeof this.get("branchCode") === "undefined") {
            //$("#branchCode").focus();
            formError('\n' + '지점은 필수 입력조건입니다.\n' + '지점을 선택하세요.');
            return false;
        }

        if (rs.error) {
            rs.error[0].jquery.focus();
            formError(rs.error[0].jquery.attr("title") + '을(를) 입력해주세요.');
            return false;
        }

        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");

        UPLOAD.clear();
        UPLOAD.refreshImage();
        UPLOAD2.clear();
        UPLOAD2.refreshImage();
        UPLOAD3.clear();
        UPLOAD3.refreshImage();
        UPLOAD4.clear();
        UPLOAD4.refreshImage();
        UPLOAD5.clear();
        UPLOAD5.refreshImage();
        UPLOAD6.clear();
        UPLOAD6.refreshImage();
        UPLOAD7.clear();
        UPLOAD7.refreshImage();
        UPLOAD8.clear();
        UPLOAD8.refreshImage();
    }
});

/**
 * formView03
 */
fnObj.formView03 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {});
    },
    initView: function () {
        this.target = $("#formView03");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();

        axboot.buttonClick(this, "data-form-view-03-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR3);
            },
            "form-save": function () {
                ACTIONS.dispatch(ACTIONS.FORM_SAVE3);
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
    get: function(dataPath) {
        return this.model.get(dataPath);
    },
    setData: function (data) {

        if (typeof data === "undefined") data = this.getDefaultData();
        data = $.extend({}, data);

        this.target.find('[data-ax-path="key"]').attr("readonly", "readonly");

        this.model.setModel(data);
        this.modelFormatter.formatting(); // 입력된 값을 포메팅 된 값으로 변경
        this.setSingleData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[this.get("branchCode")]);
    },
    setSingleData: function (dataPath, value) {
        this.model.set(dataPath, value);
    },
    validate: function () {
        var rs = this.model.validate();

        if (this.get("branchCode") == "" || typeof this.get("branchCode") === "undefined") {
            //$("#branchCode").focus();
            formError('\n' + '지점은 필수 입력조건입니다.\n' + '지점을 선택하세요.');
            return false;
        }

        if (rs.error) {
            rs.error[0].jquery.focus();
            formError(rs.error[0].jquery.attr("title") + '을(를) 입력해주세요.');
            return false;
        }

        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");

    }
});

/**
 * formView04
 */
fnObj.formView04 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {});
    },
    initView: function () {
        this.target = $("#formView04");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();

        axboot.buttonClick(this, "data-form-view-04-btn", {
            "form-clear": function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR4);
            },
            "form-save": function () {
                ACTIONS.dispatch(ACTIONS.FORM_SAVE4);
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
    get: function(dataPath) {
        return this.model.get(dataPath);
    },
    setData: function (data) {

        if (typeof data === "undefined") data = this.getDefaultData();
        data = $.extend({}, data);

        if (typeof data.terminalFrontGlassFile != "undefined") {
            UPLOAD9.uploadedFiles.push(data.terminalFrontGlassFile);
            UPLOAD9.refreshImage();
        }
        if (typeof data.terminalFrontFile != "undefined") {
            UPLOAD10.uploadedFiles.push(data.terminalFrontFile);
            UPLOAD10.refreshImage();
        }
        if (typeof data.terminalCracksFile != "undefined") {
            UPLOAD11.uploadedFiles.push(data.terminalCracksFile);
            UPLOAD11.refreshImage();
        }
        if (typeof data.monitorFile != "undefined") {
            UPLOAD12.uploadedFiles.push(data.monitorFile);
            UPLOAD12.refreshImage();
        }
        if (typeof data.bnkbPartEntranceCracksFile != "undefined") {
            UPLOAD13.uploadedFiles.push(data.bnkbPartEntranceCracksFile);
            UPLOAD13.refreshImage();
        }
        if (typeof data.cardPartEntranceCracksFile != "undefined") {
            UPLOAD14.uploadedFiles.push(data.cardPartEntranceCracksFile);
            UPLOAD14.refreshImage();
        }
        if (typeof data.intercomFile != "undefined") {
            UPLOAD15.uploadedFiles.push(data.intercomFile);
            UPLOAD15.refreshImage();
        }
        if (typeof data.boardCradleFile != "undefined") {
            UPLOAD16.uploadedFiles.push(data.boardCradleFile);
            UPLOAD16.refreshImage();
        }
        if (typeof data.branchFloorFile != "undefined") {
            UPLOAD17.uploadedFiles.push(data.branchFloorFile);
            UPLOAD17.refreshImage();
        }
        if (typeof data.insideWallFile != "undefined") {
            UPLOAD18.uploadedFiles.push(data.insideWallFile);
            UPLOAD18.refreshImage();
        }
        if (typeof data.outsideWallFile != "undefined") {
            UPLOAD19.uploadedFiles.push(data.outsideWallFile);
            UPLOAD19.refreshImage();
        }
        if (typeof data.branchGlassFile != "undefined") {
            UPLOAD20.uploadedFiles.push(data.branchGlassFile);
            UPLOAD20.refreshImage();
        }
        if (typeof data.ceilingFile != "undefined") {
            UPLOAD21.uploadedFiles.push(data.ceilingFile);
            UPLOAD21.refreshImage();
        }
        if (typeof data.fluorescentLightFile != "undefined") {
            UPLOAD22.uploadedFiles.push(data.fluorescentLightFile);
            UPLOAD22.refreshImage();
        }
        if (typeof data.stickingBillboardFile != "undefined") {
            UPLOAD23.uploadedFiles.push(data.stickingBillboardFile);
            UPLOAD23.refreshImage();
        }
        if (typeof data.garbagecanFile != "undefined") {
            UPLOAD24.uploadedFiles.push(data.garbagecanFile);
            UPLOAD24.refreshImage();
        }
        if (typeof data.coolerHeaterFile != "undefined") {
            UPLOAD25.uploadedFiles.push(data.coolerHeaterFile);
            UPLOAD25.refreshImage();
        }
        if (typeof data.branchPeripheryFile != "undefined") {
            UPLOAD26.uploadedFiles.push(data.branchPeripheryFile);
            UPLOAD26.refreshImage();
        }


        this.target.find('[data-ax-path="key"]').attr("readonly", "readonly");

        this.model.setModel(data);
        this.modelFormatter.formatting(); // 입력된 값을 포메팅 된 값으로 변경
        this.setSingleData("branchName", parent.COMMON_CODE["BRANCH_CODE"].map[this.get("branchCode")]);
    },
    setSingleData: function (dataPath, value) {
        this.model.set(dataPath, value);
    },
    validate: function () {
        var rs = this.model.validate();

        if (this.get("branchCode") == "" || typeof this.get("branchCode") === "undefined") {
            //$("#branchCode").focus();
            formError('\n' + '지점은 필수 입력조건입니다.\n' + '지점을 선택하세요.');
            return false;
        }

        if (rs.error) {
            rs.error[0].jquery.focus();
            formError(rs.error[0].jquery.attr("title") + '을(를) 입력해주세요.');
            return false;
        }

        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
        UPLOAD9.clear();
        UPLOAD9.refreshImage();
        UPLOAD10.clear();
        UPLOAD10.refreshImage();
        UPLOAD11.clear();
        UPLOAD11.refreshImage();
        UPLOAD12.clear();
        UPLOAD12.refreshImage();
        UPLOAD13.clear();
        UPLOAD13.refreshImage();
        UPLOAD14.clear();
        UPLOAD14.refreshImage();
        UPLOAD15.clear();
        UPLOAD15.refreshImage();
        UPLOAD16.clear();
        UPLOAD16.refreshImage();
        UPLOAD17.clear();
        UPLOAD17.refreshImage();
        UPLOAD18.clear();
        UPLOAD18.refreshImage();
        UPLOAD19.clear();
        UPLOAD19.refreshImage();
        UPLOAD20.clear();
        UPLOAD20.refreshImage();
        UPLOAD21.clear();
        UPLOAD21.refreshImage();
        UPLOAD22.clear();
        UPLOAD22.refreshImage();
        UPLOAD23.clear();
        UPLOAD23.refreshImage();
        UPLOAD24.clear();
        UPLOAD24.refreshImage();
        UPLOAD25.clear();
        UPLOAD25.refreshImage();
        UPLOAD26.clear();
        UPLOAD26.refreshImage();
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

var pageSearchAndViewError = function (err) {
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
    confirmToast(err);
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

var removeAllUploadFiles = function (upload) {
    console.log("files:", upload.uploadedFiles.length);
    if (upload.uploadedFiles.length <= 0) {
        return;
    }
    $.ajax({
        contentType: "application/json",
        method: "post",
        url: "/api/v1//mng/equip/corner_manage/delete",
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

var dateToString = function(date) {
    var year = 1900 + date.getYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }
    return year + "-" + month + "-" + day;
}

var loadEventData = function (caller, data) {
    axboot
        .call({
            type: "GET",
            url: "/api/v1//mng/equip/sh05001110",
            data: $.extend({}, {
                details: true,
                jisaCode: data.jisaCode,
                branchCode: data.branchCode,
                cornerCode: data.cornerCode,
                overhaulDate: data.overhaulDate
            }),
            callback: function (res) {
                if (typeof res.branchCode != "undefined") {
                    caller.formView01.clear();
                    caller.formView01.setData(res);
                }
            },
            options: {
                onError: viewError
            }
        })
        .call({
            type: "GET",
            url: "/api/v1//mng/equip/sh05001120",
            data: $.extend({}, {
                details: true,
                jisaCode: data.jisaCode,
                branchCode: data.branchCode,
                cornerCode: data.cornerCode,
                overhaulDate: data.overhaulDate
            }),
            callback: function (res) {
                if (typeof res.branchCode != "undefined") {
                    caller.formView02.clear();
                    caller.formView02.setData(res);
                }
            },
            options: {
                onError: viewError
            }
        })
        .call({
            type: "GET",
            url: "/api/v1//mng/equip/sh05001130",
            data: $.extend({}, {
                details: true,
                jisaCode: data.jisaCode,
                branchCode: data.branchCode,
                cornerCode: data.cornerCode,
                overhaulDate: data.overhaulDate
            }),
            callback: function (res) {
                if (typeof res.branchCode != "undefined") {
                    caller.formView03.clear();
                    caller.formView03.setData(res);
                }
            },
            options: {
                onError: viewError
            }
        })
        .call({
            type: "GET",
            url: "/api/v1//mng/equip/sh05001140",
            data: $.extend({}, {
                details: true,
                jisaCode: data.jisaCode,
                branchCode: data.branchCode,
                cornerCode: data.cornerCode,
                overhaulDate: data.overhaulDate
            }),
            callback: function (res) {
                if (typeof res.branchCode != "undefined") {
                    caller.formView04.clear();
                    caller.formView04.setData(res);
                }
            },
            options: {
                onError: viewError
            }
        })
        .done(function () {
        });

}

var setDisabledFormSaveBtn = function (disabled) {
    $('#form-save').attr('disabled', disabled);
    $('#form-save2').attr('disabled', disabled);
    $('#form-save3').attr('disabled', disabled);
    $('#form-save4').attr('disabled', disabled);
}

var refreshCalendar = function(data) {
    $('#calendar').fullCalendar('removeEventSources');
    PARAMS = {
        jisaCode : data.jisaCode,
        jisaName : parent.COMMON_CODE["JISA_CODE"].map[data.jisaCode],
        branchCode : data.branchCode,
        branchName : data.branchName,
        cornerCode : data.cornerCode,
        cornerName : data.cornerName,
        startDate : startDate,
        endDate : endDate,
        curMonth: curMonth
    }
    axboot.ajax({
        type: "GET",
        url: "/api/v1//mng/equip/overhaul_plan",
        data: $.extend({}, PARAMS),
        callback: function (res) {
            var events = [];

            for (var i = 0; i < res.list.length; ++i) {
                var title = parent.COMMON_CODE["OVERHAUL_GUBUN"].map[res.list[i].overhaulGubun];
                if (res.list[i].overhaulGubun == 2) {
                    events = events.concat({
                        title: title,
                        start: res.list[i].overhaulDate,
                        color: '#257e4a',
                        id : res.list[i].overhaulDate
                    })
                } else {
                    events = events.concat({
                        title: title,
                        start: res.list[i].overhaulDate,
                        id : res.list[i].overhaulDate
                    })
                }
            }

            $('#calendar').fullCalendar('addEventSource', events);
        },
        options: {
            onError: viewError
        }
    });
}

$('#add-plan').on('click', function () {
    if (PARAMS == null) {
        formError("코너를 선택해야 추가 가능합니다");
        return;
    }

    if (startDate == null || endDate == null) {
        formError("날짜를 선택해야 추가 가능합니다");
        return;
    }
    PARAMS.startDate = startDate;
    PARAMS.endDate = endDate;

    ACTIONS.dispatch(ACTIONS.ADD_PLAN);
   //alert("test");

});


$('#remove-plan').on('click', function () {
    if (PARAMS == null) {
        formError("코너를 선택해야 삭제 가능합니다");
        return;
    }

    if (startDate == null || endDate == null) {
        formError("날짜를 선택해야 삭제 가능합니다");
        return;
    }

    var parentData = {
        jisaCode : PARAMS.jisaCode,
        jisaName : parent.COMMON_CODE["JISA_CODE"].map[PARAMS.jisaCode],
        branchCode : PARAMS.branchCode,
        branchName : PARAMS.branchName,
        cornerCode : PARAMS.cornerCode,
        cornerName : PARAMS.cornerName,
        overhaulDateFrom : startDate,
        overhaulDateTo : endDate,
    }

    axDialog.confirm({
        msg: "정말 삭제하시겠습니까?"
    }, function () {
        if (this.key == "ok") {
            axDialog.close();

            axboot
                .call({
                    type: "PUT",
                    url: "/api/v1//mng/equip/overhaul_plan/delete",
                    data: JSON.stringify(parentData),
                    callback: function (res) {
                    }
                })
                .done(function () {
                    axToast.push("삭제작업이 완료되었습니다.");
                    refreshCalendar(parentData);
                });
        }
    });

});

var buildParams = function (json) {

    var params = JSON.stringify(json);

    params=params.replace(/{/g, "");
    params=params.replace(/}/g, "");
    params=params.replace(/:/g, "=")
    params=params.replace(/,/g, "&");
    params=params.replace(/"/g, "");

    return params;

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
var UPLOAD3;
var UPLOAD4;
var UPLOAD5;
var UPLOAD6;
var UPLOAD7;
var UPLOAD8;
var UPLOAD9;
var UPLOAD10;
var UPLOAD11;
var UPLOAD12;
var UPLOAD13;
var UPLOAD14;
var UPLOAD15;
var UPLOAD16;
var UPLOAD17;
var UPLOAD18;
var UPLOAD19;
var UPLOAD20;
var UPLOAD21;
var UPLOAD22;
var UPLOAD23;
var UPLOAD24;
var UPLOAD25;
var UPLOAD26;

var startDate = null;
var endDate = null;
var PARAMS = null;
var curMonth = null;
