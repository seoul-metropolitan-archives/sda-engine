var fnObj = {};
var repositoryUuid;
var shelfUuid;
var locationUuid;
var containerUuid;
var ACTIONS = axboot.actionExtend(fnObj, {
    // JOB 조회
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st022/01/list01",
            data: $.extend({}, {pageSize: 1000}, this.formView.getData(),{repositoryUuid : repositoryUuid},{shelfUuid : shelfUuid},{locationUuid : locationUuid}, {containerUuid : containerUuid}),
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
    },
    SEARCH_REPOSITORY_SCH: function (caller, act, data) {
        axboot.modal.open2({
            modalType: "COMMON_POPUP2",
            preSearch: data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='repositoryName']").val(data["REPOSITORY_NAME"])
                repositoryUuid = data['REPOSITORY_UUID'];
                shelfUuid = "";
                $("input[data-ax-path='shelfName']").val('');
                locationUuid = "";
                $("input[data-ax-path='locationName']").val('');
                containerUuid = "";
                $("input[data-ax-path='containerName']").val('');


                if (this.close) this.close();
            }
        });
    },
    SEARCH_SHELF_SCH: function (caller, act, data) {
        axboot.modal.open2({
            modalType: "COMMON_POPUP2",
            preSearch: data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='shelfName']").val(data["SHELF_NAME"])
                shelfUuid = data['SHELF_UUID'];
                statusUuid = data['STATUS_UUID'];

                locationUuid = "";
                $("input[data-ax-path='locationName']").val('');
                containerUuid = "";
                $("input[data-ax-path='containerName']").val('');


                if (this.close) this.close();
            }
        });
    }
    ,SEARCH_LOCATION_SCH : function(caller, act, data)
    {
        axboot.modal.open2({
            modalType: "COMMON_POPUP2",
            preSearch : data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {

                var text = `${data["ROWNO"]}행 ${data["COLUMNNO"]}열`;

                $("input[data-ax-path='locationName']").val(text)
                locationUuid = data['LOCATIONUUID'];
                console.log('locationUuid', locationUuid);
                containerUuid = "";
                $("input[data-ax-path='containerName']").val('');
                if(this.close) this.close();
            }
        });
    },
    SEARCH_CONTAINER_SCH : function (caller, act, data) {
        axboot.modal.open2({
            modalType: "COMMON_POPUP2",
            preSearch: data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {
                $("input[data-ax-path='containerName']").val(data["CONTAINER_NAME"])
                containerUuid = data['CONTAINER_UUID'];
                if (this.close) this.close();
            }
        });
    }
});


// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    //TODO 추후에 삭제될 내용으로 /실제 Grid의 컬럼 정보는 DB에서 가져올 예정
    $.ajax({
        url: "/assets/js/column_info/st02201.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();

    // Data 조회
    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
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

        $("input[data-ax-path='repositoryName']").parents().eq(1).find("a").click(function () {
            var data = {
                popupCode: "PU137",
                searchData: $("input[data-ax-path='repositoryName']").val().trim(),
                preSearch: false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_REPOSITORY_SCH, data);
        });

        $("input[data-ax-path='shelfName']").parents().eq(1).find("a").click(function () {
            if ("" != repositoryUuid) {
                var data = {
                    popupCode: "PU138",
                    searchData: repositoryUuid,
                    preSearch: false
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_SHELF_SCH, data);
            }
        });

        $("input[data-ax-path='locationName']").parents().eq(1).find("a").click(function () {
            if ("" != shelfUuid) {
                var data = {
                    popupCode: "PU147",
                    searchData: shelfUuid,
                    preSearch: false
                };
                ACTIONS.dispatch(ACTIONS.SEARCH_LOCATION_SCH, data);
            }
        });
        $("input[data-ax-path='containerName']").parents().eq(1).find("a").click(function () {

            var data = {
                popupCode: "PU148",
                searchData: locationUuid,
                preSearch: false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_CONTAINER_SCH, data);

        });

        $("input[data-ax-path='repositoryName']").keyup(function(e){
            if($(this).val() == ""){
                repositoryUuid = "";
                shelfUuid = "";
                $("input[data-ax-path='shelfName']").val('');
                locationUuid = "";
                $("input[data-ax-path='locationName']").val('');
                containerUuid = "";
                $("input[data-ax-path='containerName']").val('');
            }
        });

        $("input[data-ax-path='shelfName']").keyup(function(e){
            if($(this).val() == ""){
                shelfUuid = "";
                locationUuid = "";
                $("input[data-ax-path='locationName']").val('');
                containerUuid = "";
                $("input[data-ax-path='containerName']").val('');
            }
        });

        $("input[data-ax-path='locationName']").keyup(function(e){
            if($(this).val() == ""){
                locationUuid = "";
                containerUuid = "";
                $("input[data-ax-path='containerName']").val('');
            }
        });
        $("input[data-ax-path='containerName']").keyup(function(e){
            if($(this).val() == ""){
                containerUuid = "";
            }
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
    primaryKey: "gateUuid",
    entityName: "ST_GATE",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st02201.column_info);
        this.makeGrid();
        this.gridObj.itemClick(this.itemClick);
    },
    itemClick: function (data, index) {

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
