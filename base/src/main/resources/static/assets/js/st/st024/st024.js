var fnObj = {};

$( function() {
    $( "#itemTabs" ).tabs();
} );


var repositoryUuid;
var shelfUuid;
var locationUuid;
var requestorUuid;
var currentTab;
var publishStatusUuid;
var ACTIONS = axboot.actionExtend(fnObj, {
    // JOB 조회
    PAGE_SEARCH: function (caller, act, data) {
        if(currentTab == "tab1"){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
        }else if(currentTab == "tab2"){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
        }else if(currentTab == "tab3"){
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
        }

    },
    PAGE_SEARCH01: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st024/01/list01",
            data: $.extend({}, {pageSize: 1000}, {code : $("input[data-ax-path='code']").val()},{title : $("input[data-ax-path='title']").val()},{repositoryUuid : repositoryUuid},{shelfUuid : shelfUuid},{locationUuid : locationUuid}),
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
    PAGE_SEARCH02: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st024/01/list02",
            data: $.extend({}, {pageSize: 1000}, {code : $("input[data-ax-path='code1']").val()},{title : $("input[data-ax-path='title1']").val()},{userUuid : requestorUuid},{createDateFrom : $("input[data-ax-path='createDateFrom']").val()},{createDateTo : $("input[data-ax-path='createDateTo']").val()}),
            callback: function (res) {
                fnObj.gridView02.setData(res.list);
                fnObj.gridView02.resetCurrent();
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SEARCH03: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: "/api/v1/st/st024/01/list03",
            data: $.extend({}, {pageSize: 1000}, {publishStatusUuid : publishStatusUuid},{code : $("input[data-ax-path='code2']").val()},{title : $("input[data-ax-path='title2']").val()}),
            callback: function (res) {
                fnObj.gridView03.setData(res.list);
                fnObj.gridView03.resetCurrent();
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


                if(this.close) this.close();
            }
        });
    },
    SEARCH_USER_SCH: function (caller, act, data) {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch: data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {

                $("input[data-ax-path='requestorName']").val(data['USER_NAME'])
                requestorUuid = data['USER_UUID'];
                if (this.close) this.close();
            }
        });
    },
    SEARCH_PUBLISH_SCH: function (caller, act, data) {
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch: data["preSearch"],
            sendData: function () {
                return data;
            },
            callback: function (data) {

                $("input[data-ax-path='publishStatusName']").val(data['CODE_NAME'])
                publishStatusUuid = data['CODE_DETAIL_UUID'];
                if (this.close) this.close();
            }
        });
    },
});


// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    var _this = this;

    //TODO 추후에 삭제될 내용으로 /실제 Grid의 컬럼 정보는 DB에서 가져올 예정
    $.ajax({
        url: "/assets/js/column_info/st02401.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/st02402.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    $.ajax({
        url: "/assets/js/column_info/st02403.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });

    _this.formView.initView();
    _this.gridView01.initView();
    //_this.gridView02.initView();

    // Data 조회
    currentTab = "tab1";
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


        $('#tab1').click(function(){
            currentTab = "tab1";
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH01);
        });
        $('#tab2').click(function(){
            currentTab = "tab2";
            fnObj.gridView02.initView();
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH02);
        });
        $('#tab3').click(function(){
            currentTab = "tab3";
            fnObj.gridView03.initView();
            ACTIONS.dispatch(ACTIONS.PAGE_SEARCH03);
        });

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

        $("input[data-ax-path='repositoryName']").keyup(function(e){
            if($(this).val() == ""){
                repositoryUuid = "";
                shelfUuid = "";
                $("input[data-ax-path='shelfName']").val('');
                locationUuid = "";
                $("input[data-ax-path='locationName']").val('');
            }
        });

        $("input[data-ax-path='shelfName']").keyup(function(e){
            if($(this).val() == ""){
                shelfUuid = "";
                locationUuid = "";
                $("input[data-ax-path='locationName']").val('');
            }
        });

        $("input[data-ax-path='locationName']").keyup(function(e) {
            if ($(this).val() == "") {
                locationUuid = "";
            }
        });

        $("input[data-ax-path='requestorName']").parents().eq(1).find("a").click(function () {

            var data = {
                popupCode: "PU107",
                searchData: null,
                preSearch: false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_USER_SCH, data);

        });

        $("input[data-ax-path='requestorName']").keyup(function(e){
            if($(this).val() == ""){
                requestorUuid = "";
            }
        });

        $("input[data-ax-path='createDateFrom']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='createDateFrom']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='createDateTo']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='createDateTo']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });


        $("input[data-ax-path='publishStatusName']").parents().eq(1).find("a").click(function () {
            var data = {
                popupCode: "PU149",
                searchData: $("input[data-ax-path='publishStatusName']").val().trim(),
                preSearch: false
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_PUBLISH_SCH, data);
        });

        $("input[data-ax-path='publishStatusName']").keyup(function(e){
            if($(this).val() == ""){
                publishStatusUuid = "";
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
        this.setColumnInfo(st02401.column_info);
        this.makeGrid();
        //this.gridObj.itemClick(this.itemClick);
    },
    itemClick: function (data, index) {

    }

});

fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid02",
    primaryKey: "gateUuid",
    entityName: "ST_GATE",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st02402.column_info);
        this.makeGrid();
        //this.gridObj.itemClick(this.itemClick);
    },
    itemClick: function (data, index) {

    }

});

fnObj.gridView03 = axboot.viewExtend(axboot.gridView, {
    tagId: "realgrid03",
    primaryKey: "gateUuid",
    entityName: "ST_GATE",
    initView: function () {
        this.initInstance();
        this.setColumnInfo(st02403.column_info);
        this.makeGrid();
        //this.gridObj.itemClick(this.itemClick);
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
