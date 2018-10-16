var fnObj = {};
var option = '';
var authorityTypeUuid = '';
var isChanged = false;
var selectedRadio = "";
var selectedLabel = "";
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({ //트리 리스트
            type: "GET",
            url: "/api/v1/at/at001/01/list",
            data: $.extend({}, {pageSize: 1000}, {authorityTypeUuid:authorityTypeUuid,authorityName:fnObj.formView.getData().sAuthNm}),
            callback: function (res) {
                fnObj.formView.clear();
                fnObj.gridView01.setData(res.list);
                if(res.list.length == 0){
                    $("#contentsArea").hide();
                }else{
                    $("#contentsArea").show();
                    fnObj.formView.setData(res.list[fnObj.gridView01.gridObj.gridView.getSelectedItems()]);
                    isChanged = false;
                }
            },
            options: {
                onError: axboot.viewError
            }
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        // var authorityName = fnObj.formView.getData().authorityName;
        // var orgTypeUuid = fnObj.formView.getData().orgTypeUuid;
        //
        // if(authorityName == undefined || authorityName.trim() == ""){
        //     return;
        // }
        //
        // if(authorityTypeNm == ORG_TYPE_NM){
        //     if(orgTypeUuid == undefined || orgTypeUuid.trim() == ""){
        //         return;
        //     }
        //     if(fnObj.formView.getData().mainJob01 != undefined){
        //         fnObj.formView.setFormData("mainJob",fnObj.formView.getData().mainJob01);
        //     }
        // }else{
        //     if(fnObj.formView.getData().mainJob02 != undefined){
        //         fnObj.formView.setFormData("mainJob",fnObj.formView.getData().mainJob02);
        //     }
        // }
        axboot.ajax({
            type: "PUT",
            url: "/api/v1/at/at001/01/save",
            data: JSON.stringify($.extend({}, fnObj.formView.getData(), {authorityTypeUuid:authorityTypeUuid})),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH)
            },
            options: {
                onError: axboot.viewError
            }
        });
    },
    ADD_AUTHORITY: function(caller, act, data){
        fnObj.gridView01.registerAuth();
    },
    DEL_AUTHORITY: function(caller, act, data){
        axDialog.confirm({
            msg: fnObj.formView.getData().authorityName + "을(를) 삭제하시겠습니까?"
        }, function () {
            if (this.key == "ok") {
                fnObj.formView.setFormData('__deleted__',true);
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            }
        });
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
        url: "/assets/js/controller/simple_controller.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    $.ajax({
        url: "/assets/js/column_info/at00101.js",
        dataType: "script",
        async: false,
        success: function () {
        }
    });
    _this.formView.initView();
    _this.gridView01.initView();
    _this.childrenDrnInfo.initView();
};
fnObj.formView = axboot.viewExtend(axboot.formView, {
    initView: function () {
        this.target = $("#formView01");
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시
        this.model.onChange("*", function (n) {
            isChanged = true;
         });
        this.makeRadio();
        this.initEvent();
        this.bindEvent();
        this.target.find('[data-ax5picker="date"]').ax5picker({
            direction: "auto",
            content: {
                type: 'date'
            }
        });
    },
    initEvent: function(){
        $('input:radio[name="radio"]').change(function(){
            if(isChanged){
                axDialog.confirm({
                    msg: "데이터가 변경되었습니다. 저장하시겠습니까?"
                }, function () {
                    if (this.key == "ok") {
                        $('#' + selectedRadio).prop('checked',true);
                        ACTIONS.dispatch(ACTIONS.PAGE_SAVE)
                    }else{
                        authorityTypeUuid = event.currentTarget.value;
                        fnObj.formView.changeView();
                    }
                    isChanged = false;
                });
            }else{
                authorityTypeUuid = event.currentTarget.value;
                fnObj.formView.changeView();
            }
        });
        $("input[data-ax-path='startDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='startDate']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='endDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='endDate']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });
        $("input[data-ax-path='descriptionDate']").keyup(function () {
            var date = this.value;
            if (date.match(/^\d{4}$/) !== null) {
                this.value = date + '-';
            } else if (date.match(/^\d{4}\-\d{2}$/) !== null) {
                this.value = date + '-';
            }
        });
        $("input[data-ax-path='descriptionDate']").keypress(function () {
            if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
        });

        $("input[data-ax-path='startDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });
        $("input[data-ax-path='endDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });
        $("input[data-ax-path='descriptionDate']").focusout(function () {
            if (!checkDate(this.value)) {
                this.value = "";
                this.focus = true;
            }
        });
    },
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData);
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
    makeRadio: function() {
        var codes = axboot.commonCodeFilter("CD161").codeArr;
        var names = axboot.commonCodeFilter("CD161").nameArr;
        var defaultYNs = axboot.commonCodeFilter("CD161").defaultYNArr;
        var radioTag = "";
        for(var i = 0; i < codes.length; i++)
        {
            if(defaultYNs[i] == 'Y'){
                radioTag += '<label for="authType' + i + '"><input type="radio" id="authType' + i + '"  name="radio" class="no_border" value="' + codes[i] + '" checked="checked">' + names[i] + '</label>'
                authorityTypeUuid = codes[i];
            }else{
                radioTag += '<label for="authType' + i + '"><input type="radio" id="authType' + i + '" name="radio" class="no_border" value="' + codes[i] + '">' + names[i] + '</label>'
            }


        }
        $(".rdo_box").append(radioTag);
        fnObj.formView.changeView();
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr("readonly");
    },
    bindEvent : function()
    {
        var _this = this;
        $(".btn_adf").click(function() {
            ACTIONS.dispatch(ACTIONS.ADD_AUTHORITY);
        }),
        $(".btn_def").click(function() {
            ACTIONS.dispatch(ACTIONS.DEL_AUTHORITY);
        })
    },
    changeView: function () {
        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
        selectedRadio = $('input:radio[name="radio"]:checked').attr("id");
        selectedLabel = $("label[for='"+selectedRadio+"']").text();
        if(selectedLabel == "Organization"){
            $("#organizationArea").show();
            $("#etcArea").hide();
        }else{
            $("#organizationArea").hide();
            $("#etcArea").show();
        }
    }
});
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    tagId:"realgrid01",
    primaryKey : "authorityUuid",
    entityName : "AT_AUTHORITY",
    initView : function() {
        var _this = this;
        _this.initInstance();
        _this.setColumnInfo(at00101.column_info);
        _this.makeGrid();
        _this.gridObj.itemClick(_this.itemClick);
    },
    itemClick: function (data) {
        if(isChanged){
            axDialog.confirm({
                msg: "데이터가 변경되었습니다. 저장하시겠습니까?"
            }, function () {
                if (this.key == "ok") {
                    ACTIONS.dispatch(ACTIONS.PAGE_SAVE)
                }else{
                    fnObj.formView.setData(data);
                }
                isChanged = false;
            });
        }else{
            fnObj.formView.setData(data);
        }
    },
    getSelectedData : function(){
        return this.gridObj.getSelectedData()
    },
    registerAuth : function(){
        axboot.modal.open({
            modalType: "AUTHORITY_REGISTER_POPUP",
            header: {
                title: "AUTHORITY REGISTER POPUP"
            },
            sendData: function () {
                return {
                    authorityTypeUuid : authorityTypeUuid,
                    // description : fnObj.gridView01.gridObj.getSelectedData().description
                };
            },
            callback: function (data) {
                if(this) this.close();
                if(data){
                   ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                }else{
                    // _this.gridView.commit(true);
                    // _this.gridView.getDataProvider().removeRows(_this.gridView.getSelectedRows(), false);
                    fnObj.gridView01.gridObj._gridView.dispatch("onRemoveRow");
                }
            }
        });
    }
});

fnObj.childrenDrnInfo = axboot.viewExtend({
    targetTag  : $("#childrenDnrInfoArea"),
    parentUuid : "",
    nodeType : "",
    popupCode : "",
    template :
    "                                                            <li style='width: 20%; padding: 0 0.5%;'>" +
    "                                                                <b>관할 기관</b>" +
    "                                                               <select data-ax-path='lvDtlUuid' style='width:135px;' class='form-control W120'>" +
    "                                                                   <option value=''></option>" +
    "                                                                </select>" +
    "                                                            </li>" +
    "                                                            <li style='width: 20%;'>" +
    "                                                                <b>전거 팝업</b>" +
    "                                                            <div class='src_box2'>" +
    "                                                                <input type=text data-ax-path='name' class='form-control'>" +
    "                                                                <a href='#' class='searchAggregation' ><img src='/assets/images/ams/search_normal.png' alt='find'></a>" +
    "                                                            </div>" +
    "                                                            </li>" +
    "                                                            <li style='width: 60%; text-align: center'>" +
    "                                                                <b>&nbsp; </b>" +
    "                                                                <div><a href='#' class='btn_del_left' style=''>X</a></div>" +
    "                                                            </li>",
    initView: function () {
        this.initEvent();

        var codes = axboot.commonCodeFilter("CD162").codeArr;
        var names = axboot.commonCodeFilter("CD162").nameArr;
        for(var i = 0; i < codes.length; i++)
        {
            option += '<option value="'+ codes[i] + '">' + names[i] + '</option>';

        }

        this.addChild($("#addDnrInfo"));
    },
    initEvent: function () {
        var _this = this;
        $("#addDnrInfo").click(function(){
            _this.addChild(this);
        });

        $("#childrenDnrInfoArea").on("change",".cntPrsn",function(){
            $(this).parents().eq(2).find("input[data-ax-path='__modified__']").val(true);
        });

        $("#childrenDnrInfoArea").delegate(".searchAggregation","click",function(){
            var parentsTag  = $(this).parents().eq(2);
            var data = {
                popupCode : "PU001",
                preSearch : false,
                searchData : data,
                callback : function(data){
                    parentsTag.find("input[data-ax-path='aggregationUuid']").attr("aggregationUuid",data["AGGREGATION_UUID"])
                    parentsTag.find("input[data-ax-path='aggregationUuid']").val(data["AGGREGATION_CODE"])
                    parentsTag.find("input[data-ax-path='title']").val(data["TITLE"])
                    console.log(data);
                }
            };
            ACTIONS.dispatch(ACTIONS.SEARCH_DNRINFO,data);
        });

        $(".childDnrInfo").delegate(".btn_del_left","click",function(){
            if("create" == $(this).parents().eq(2).attr("saveType"))
            {
                $(this).parents().eq(2).remove();
            }
            else
            {
                $(this).parents().eq(2).hide();
                $(this).parents().eq(2).find("input[data-ax-path='__deleted__']").val(false)
            }
        });
    },
    addChild : function(_this,data){
        var cloneTag = $("<ul>").addClass("pdb_10").attr("data-ax-path","saveType").attr("saveType","saved").html(this.template).clone();
        cloneTag.find("select").append($(option))
        $(_this).before(cloneTag);
        cloneTag.show();
    },
    getData : function(){
        // var retData = new Array();
        // var data = {};
        // if(this.targetTag.css("display") != "none")
        // {
        //     $(this.targetTag).find(".childDnrInfo>ul:not(#addDnrInfo)").each(function(){
        //         data = {};
        //         data["saveType"] = $(this).attr("saveType");
        //         $(this).children("li").find("input,select,textarea").each(function(){
        //
        //             if($(this).attr("data-ax-path")) {
        //                 if ($(this).attr($(this).attr("data-ax-path")))
        //                     data[$(this).attr("data-ax-path")] = $(this).attr($(this).attr("data-ax-path"));
        //                 else
        //                     data[$(this).attr("data-ax-path")] = $(this).val();
        //             }
        //
        //         });
        //         if(data["name"] && data["name"] != "")
        //             retData.push(data);
        //     });
        // }
        // return retData;
    },
    setData : function(data){
        if(data != null && data != "undefined"){
            for (var i=0;i<data.length;i++){
                this.addChild($("#addDnrInfo"),data[i]);
            }
        }
    }
});
