Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";

    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var d = this;

    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};

String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};



var GridWrapper = function(p_id,p_rootContext) {
    //현재 객체
    var _this = this;
    //자신을 만든 객체
    this.makeObj = undefined;
    //Grid id
    this.i_id = p_id;
    //Excel Export시 사용되는 Entity 이름
    this.i_entity_name = "";
    //Grid 넓이
    this.i_width = "";
    //Grid 높이
    this.i_height = "";
    //컬럼 리스트
    this.i_columnList = [];
    //필드 리스트
    this.i_fieldList = [];

    this.callback = {
        context : undefined
    };
    this.addBtnName = ".btn_a";
    this.delBtnName = ".btn_d";
    //그리드 생성시 객체
    this.gridView;
    //데이터 프로바이더
    this.dataProvider;

    //기초 데이터 ( 줄 추가시 사용됨. )
    this.defaultData = [];

    //트리 생성시 사용되는 프로퍼티
    this.childrenProp = undefined;
    this.appendValidate = function() { return true; };

    this.contextMenu = [{label : "Excel Export"}];
    this.removeProperty = new Array();
    //================================================
    //lazy load시 사용 파라메터
    this.maxCount = 100;
    //================================================
    this.requiredColumnList = {};

    this.defaultStyle = {};
    this.defaultStyles = {};
    //그리드 옵션
    this.gridOption = undefined;
    this.rootContext = p_rootContext;
    this.searchStartIndex = -1;
    this.validateColumn = {};
    this.runAdd = true;
    this.runDel = true;
    this.doAppendValidate = true;
    //event저장소
    this.event = {};

    this.columnInfo = new Array();

    this.setDoAppendValidate = function(_doAppendValidate) {this.doAppendValidate = _doAppendValidate;}
    this.setAddBtnName = function(_name){this.addBtnName = _name;}
    this.setDelBtnName = function(_name){this.delBtnName = _name;}
    this.setRunAdd = function(_runAdd)
    {
        _this.runAdd = _runAdd
    }
    this.setRunDel = function(_runDel)
    {
        _this.runDel = _runDel
    }

    this.setDefaultStyles = function(name, value)
    {
        defaultStyles[name] = value;
    }
    this.getDefaultStyles = function()
    {
        return defaultStyles;
    }



    //엑셀 추출
    this.exportExcel = function(grid,label)
    {
        grid.exportGrid({
            type: "excel",
            target: "local",
            fileName: _this.i_entity_name+"_"+new Date().format("yyyyMMdd")+".xlsx",
            progressMessage: "Exporting Excel...",
            indicator : "default",
            header : "default",
            footer : "default",
            allItems : true,
            lookupDisplay : true,
            indenting: true,
            compatibility: true,
            showProgress : true,
            done: function () {
            }
        });
    };
    //==========================================================================================
    //          Event 영역
    //==========================================================================================

    //이벤트 걸기
    bindEvent = function() {
        _this.defaultBind();
        _this.gridView.onRowsPasted = function(grid, item){ _this.dispatch("onRowsPasted",_this, _this.makeObj, grid, item); };

        _this.gridView.onKeyDown = function(grid, key, ctrl, shift, alt){
            _this.dispatch("onKeyDown",grid, key, ctrl, shift, alt,_this);
        };
        _this.gridView.onKeyUp = function(grid, key, ctrl, shift, alt){
            _this.dispatch("onKeyUp",grid, key, ctrl, shift, alt);
        };
        _this.gridView.onEditRowChanged= function(grid, itemIndex, dataRow, field, oldValue, newValue){ _this.dispatch("onEditRowChanged",_this,grid, itemIndex, dataRow, field, oldValue, newValue); }
        _this.gridView.onDataCellDblClicked = function(grid,index){ _this.dispatch("onDataCellDblClicked",grid,index); }
        _this.gridView.onDataCellDblClicked = function(grid,index){ _this.dispatch("onDataCellDblClicked",grid,index); }
        _this.gridView.onEditChange = function(grid, index, value){ _this.dispatch("onEditChange",_this, grid, index, value); }
        _this.gridView.onImageButtonClicked = function(grid, itemIndex, column, buttonIdex, name) { _this.dispatch("onImageButtonClicked",_this ,grid, itemIndex, column, buttonIdex, name); }
        _this.gridView.onCurrentChanged = function(grid,newIndex) { _this.dispatch("onCurrentChanged",grid, newIndex); }
        _this.gridView.onCurrentRowChanged = function(grid, oldRow, newRow) { _this.dispatch("onCurrentRowChanged",grid, oldRow, newRow); }


        _this.dataProvider.onRowsDeleted = function(grid, rows){
        }

        _this.gridView.onEditCommit = function(grid, index, oldValue, newValue){_this.dispatch("onEditCommit", _this, grid, index, oldValue, newValue);}
        //사용자가 Insert 키를 눌러 새로운 행을 삽입하거나, 마지막 행에서 아래 화살표를 눌러 행을 추가하려고 할 때 호출된다. 이 콜백에서 행 추가 불가 메시지를 리턴하면 행 추가가 금지된다.
        _this.gridView.onRowInserting = function (grid, itemIndex) {

            if(!_this.runAdd)
                return false;

            if(false == this.doAppendValidate)
            {
                _this.dispatch("onBeforeAddRow",_this,_this.makeObj,grid, itemIndex);
                _this.dispatch("onAfterAddRow",_this,_this.makeObj,grid, itemIndex);
                return true;
            }
            else {
                if(!_this.validate())
                    return false;
                _this.dispatch("onBeforeAddRow",_this,_this.makeObj,grid, itemIndex);
                _this.dispatch("onAfterAddRow",_this,_this.makeObj,grid, itemIndex);
            }
        };
        _this.gridView.onSelectionChanged = function (grid) { _this.dispatch("onSelectionChanged",grid); };
        _this.gridView.onContextMenuItemClicked = function (grid, label, index) { _this.dispatch("onContextMenuItemClicked",grid, label, index); };
        _this.gridView.onDataCellClicked = function(grid, index)  { _this.dispatch("onDataCellClicked",grid, index); };
        _this.gridView.onCellEdited  = function(grid, itemIndex, dataRow, field)  { _this.dispatch("onCellEdited",_this, grid, itemIndex, dataRow, field); }

        $("#"+_this.i_id).parents().eq(1).delegate(_this.addBtnName,"click",function(){
            if(_this.runAdd)
            {
                _this.gridView.beginAppendRow();
                _this.gridView.setFocus();
            }
        });
        $("#"+_this.i_id).parents().eq(1).delegate(_this.delBtnName,"click",function(){
            if(_this.runDel) {
                _this.gridView.commit(true);
                _this.gridView.getDataProvider().removeRows(_this.gridView.getSelectedRows(), false);
                _this.dispatch("onRemoveRow");
            }
        });

        _this.bind("onRowsPasted",function(gridWrapper, _this, grid, item){
            var index = grid.getCurrent();

            var columnInfoData = gridWrapper.columnInfo[index.fieldIndex];
            var rowData = undefined;
            var colData = undefined;
            grid.commit(true);
            var validateData = grid.getDataProvider().getJsonRows(item[0],item[item.length-1])
            for(var row = 0; row < validateData.length; row++)
            {
                rowData = validateData[row];
                for(var col = 0; col < gridWrapper.columnInfo.length; col++)
                {
                    colData = gridWrapper.columnInfo[col];
                    if(colData.dataType == "combo")
                    {
                        var selectedData = "";
                        for(var i = 0; i < colData.labels.length; i++)
                        {
                            if(rowData[colData.name].toLowerCase() == colData.labels[i].toLowerCase() || rowData[colData.name] == colData.values[i])
                            {
                                selectedData = colData.values[i];
                                break;
                            }
                        }
                        rowData[colData.name] = selectedData;
                    }
                    else {
                        var popupData = gridWrapper.getPopupData(colData.name);

                        if (!popupData) {
                            continue;
                        }
                        grid.commit(true);
                        var newValue = rowData[colData.name];

                        if (undefined == newValue || "" == newValue)
                            continue;

                        axboot.ajax({
                            url: "/api/v1/common/popup/search",
                            dataType: "JSON",
                            type: "POST",
                            async: false,
                            data: JSON.stringify({
                                popupCode: popupData["popupCode"],
                                searchField: newValue,
                                isTree: false
                            }),
                            callback: function (res) {
                                list = res.list;
                            }
                        });
                        if (list && list.length == 1) {
                            //컬럼 살리기
                            console.log(list[0]);
                            console.log(list[0][colData.name]);
                            var sqlColumnName = "";
                            for(var key in colData.sqlColumn)
                            {
                                if(colData.sqlColumn[key] == colData.pasteTarget)
                                {
                                    sqlColumnName = key;
                                    break;
                                }
                            }

                            gridWrapper.gridView.setValue(item[row], colData.pasteTarget, list[0][sqlColumnName]);
                        }
                        else
                        {
                            //지우기
                            gridWrapper.gridView.setValue(item[row], colData.name, "");
                        }

                        //gridWrapper.showPopup(grid, index.fieldName, newValue, index.itemIndex, popupData);
                        console.log(index.fieldName);
                    }
                }

            }
            gridWrapper.gridView.commit(true);
            //gridWrapper.dataProvider.updateRows(item[0], validateData, 0, -1);


        })

    };
    this.registerStyle = function()
    {
        _this.gridView.addCellStyle("_default", this.style._default, true);
        _this.gridView.addCellStyle("disable", this.style.column.disable, true);
        _this.gridView.addCellStyle("required", this.style.column.required, true);
        _this.gridView.addCellStyle("editable", this.style.column.editable, true);

        var imgs = new RealGridJS.ImageList("iconList1","/assets/images/ams/icon/");
        imgs.addUrls([
            "fi.png"
            ,"fi_v.png"
            ,"fo.png"
            ,"fo_t.png"
            ,"fo_v.png"
            ,"im.png"
            ,"im_v.png"
            ,"fo_op.png"
            ,"fo.op_t.png"
            ,"fo_op_v.png"
        ]);
        this.gridView.registerImageList(imgs);


    }
    //==========================================================================================
    //          Validate 영역
    //==========================================================================================
    var validate = function () {
        var list = _this.getJsonRows();
        var states = _this.dataProvider.getAllStateRows();
        var data = undefined;
        var msg = "";
        var result = true;
        var skipList = states.createAndDeleted;
        var skip = false;
        for (var i = 0; i < list.length; i++) {
            data = list[i];

            for (var j = 0; j < skipList.length; j++) {
                if (skipList[j] == i) {
                    skip = true;
                    break;
                }
            }

            if (skip) {
                skip = false;
                continue;
            }

            //validation;
            //key = 위치값
            for (var key in _this.requiredColumnList) {
                if (undefined == data[key] || "" === data[key]) {
                    msg = "[" + _this.requiredColumnList[key]["title"] + "] " + axboot.getCommonMessage("AA008");

                    result = false;
                    break;
                }
            }
            if (!result)
                break;

            data = undefined;
        }
        return msg;
    }

    this.validateData = function () {
        var msg = validate();
        if ("" != msg) {
            if (axToast && axboot && axboot.viewError) {
                axboot.viewError({message: msg});
            }
            else
                alert(msg);
            return false;
        }
        else {
            return true;
        }
    }
    //==========================================================================================
    //              Popup 관련 영역
    //==========================================================================================
    this.getPopupData = function(name)
    {
        return this.popupNames[name];
    }

    this.popupNames = {};
    this.popCallback = undefined;

    this.showPopup = function(grid, currentField, searchData, rows, popupData,preSearch)
    {
        var popupCode = "";
        if(typeof popupData["popupCode"] == "string")
        {
            popupCode = popupData["popupCode"];
        }else if(typeof popupData["popupCode"] == "object")
        {
            var fieldname = Object.keys(popupData["popupCode"])[0];
            var currentData = _this.getSelectedData();
            var checkData = currentData[fieldname];
            if(typeof popupData["popupCode"][fieldname] == "function")
                popupCode = popupData["popupCode"][fieldname] (checkData);
        }

        if("" == popupCode)
        {
            return ;
        }

        var retData = undefined;
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : preSearch,
            sendData: function () {
                return {
                    popupCode : popupCode,
                    searchData : searchData
                };
            },
            callback: function (data) {
                //$("#calleeEmpName").val(data.empName);
                //$("#calleeEmpTelno").val(data.empPhoneNo);
                var currentValue = "";
                grid.commit(true);
                console.log(data);
                retData = data;
                grid.commit(true);
                grid.setFocus();
                var retData = {};
                var index = grid.getCurrent();
                for(var key in data)
                {
                    try {
                        if(popupData["sqlColumn"][key])
                        {
                            //_this.dataProvider.setValue(rows,popupData["sqlColumn"][key],data[key]);
                            retData[popupData["sqlColumn"][key]] = data[key];
                            _this.gridView.setValue(index.itemIndex, popupData["sqlColumn"][key], data[key]);
                        }

                    }
                    catch(exception)
                    {

                    }
                }
                //_this.dataProvider.updateRows(rows, retData);

                //_this.gridView.setValues(index.itemIndex, retData, true);
                grid.commit(true);
                if(this.close)
                    this.close();
            },
            emptyClose : function()
            {
                for(var key in popupData["sqlColumn"])
                {
                    if(popupData["sqlColumn"][key] == currentField)
                    {
                        _this.dataProvider.setValue(rows,popupData["sqlColumn"][key],"");
                    }
                }
            }
        });

        if(this.popCallback)
            this.popCallback(grid, retData);
    }
    //==========================================================================================


    //==========================================================================================
    //          초기 컬럼 값 저장 영역
    //==========================================================================================
    this.defaultEditColumnProperties = new Array();
    this.addDefaultEditColumnProperties = function(name,editable)
    {
        _this.defaultEditColumnProperties[name] = editable;
    }

    //==========================================================================================
    //          AutoIncrements 영역
    //==========================================================================================
    autoIncrementList = new Array();

    addAutoIncreament = function(obj)
    {
        autoIncrementList.push(obj);
    }

    /* 자동 증가 함수*/
    this.setAutoIncrementData = function () {
        var list = _this.getJsonRows();

        var getNumber = function (list, key) {
            var intData = 0;

            for (var i = 0; i < list.length; i++) {
                intData = parseInt(list[i][key])

                if (!isNaN(intData)) {
                    break;
                }
            }

            if (isNaN(intData))
                intData = 0;

            return intData;

        }

        for (var i = 0; i < autoIncrementList.length; i++) {

            if (list.length < 1) {
                this.defaultData[autoIncrementList[i]["index"]] = 1;
            }
            else {
                list.sort(function (a, b) {
                    return b[autoIncrementList[i]["index"]] - a[autoIncrementList[i]["index"]];
                });
                this.defaultData[autoIncrementList[i]["index"]] = getNumber(list, autoIncrementList[i]["index"]) + 1;
            }
        }
    }
    return this;
};

//============================================================================================
//			Option 관련
//============================================================================================
GridWrapper.prototype.option = {
    //그리드 옵션
    gridOption : {
        indicator : {
            visible : false
        },
        checkBar : {
            visible : false
        },
        stateBar : {
            visible : false
        },
        footer : {
            visible : false
        },
        edit : {
            insertable : true,
            appendable : true,
            updatable : true,
            editable : true,
            deletable : true,
            deleteRowsConfirm : false,
            commitWhenExitLast : false,	//tab/enter 키로 마지막 셀을 벗어날 때 행 commit 한다.
            crossWhenExitLast : true,	//tab/enter 키로 마지막 셀을 벗어날 때 다음 행으로 이동한다.
            useTabKey : true,			//true면 Tab 키로 셀 이동할 수 있다.
            enterToNextRow : false,		//enter 입력시 다음 row로 이동
            enterToEdit: true,			//enter 시 텍스트 편집
            skipReadOnly : false,		//true이면 컬럼간 이동시 readOnly 셀은 건너뛰고 다음 컬럼 셀로 이동한다.
            skipReadOnlyCell : true, 	//true이면 한 컬럼에서 행간(Vertical 컬럼 그룹 행을 포함) 이동시 readOnly 셀은 건너뛰고 다음 행의 컬럼 셀로 이동한다.
            appendWhenExitLast : true, 	//commitWhenExitLast 가 true 일 경우 enterb/tab 키로 마지막셀을 벗어날 경우 행이 추가된다.
            appendWhenInsertKey : false, //Insert 키 입력시 해당 위치에 행이 삽입되는 것이 아니라 가장 마지막행에 추가된다.
            editWhenFocused : false, 	//셀이 선택될때마다 에디터가 표시된다.
            editWhenClickFocused : true, //한번 선택된 셀을 다시한번 선택하면 에디터가 표시된다.(더블 클릭이 아님)
            revertable : false,			//dataProvider.softDeleting = true 인 경우 삭제 상태인 행들을 ctrl+shift+del 키 입력시 원래 상태로 되돌리겠는지의 여부를 설정한다.
            maxLengthToNextCell : false, //column.editor.maxLength에 지정한 자리수 만큼 입력되면 다음 셀로 이동된다.editFormat이 있는 경우 보여지는 글자를 기준으로 maxLength가 체크된다. (numberEditor, dateEditor)multiLine의 경우 \n과 같이 제어문자도 글자수에 포함된다.
            innerDraggable : true,		//Inner Drag & Drop기능 사용 여부를 지정한다
            enterToTab : true,
            forceAppend : false,
            exceptDataClickWhenButton : false //true인 경우 셀 버튼 클릭 후 발생하는 이벤트인 onCellButtonClicked, onImageButtonClicked 발생후에 onDataCellClicked이벤트가 발생하지 않는다. JS ver 1.1.26부터 지원된다.
        },
        sort : {
            enabled : true
        },
        panel : {
            visible : false
        },
        sorting: {
            toast: {
                visible: true,
                message: "정렬 중입니다..."
            }
        },
        filtering: {
            toast: {
                visible: true,
                message: "필터링 중입니다..."
            }
        },
        grouping: {
            toast: {
                visible: true,
                message: "그룹핑 중입니다..."
            }
        },
        select : {
            style : RealGridJS.SelectionStyle.BLOCK
        },
        hideDeletedRows : true,
    },
    paste : {
        singleMode: false,	//포커스를 갖는 셀 하나에만 붙여넣기
        enabled: true,	//Paste가능여부 설정
        startEdit: true,	//붙여넣게 될 값이 복수 행이 아니고, 붙여넣을 행이 아직 편집 중이 아니면 편집을 시작
        commitEdit: false,	//복수 행 붙여넣기일 때 기존 편집 상태를 commit
        enableAppend: true,	//붙여넣을 복수 행이 기존 행의 범위를 넘어설 때 행을 추가
        fillFieldDefaults: false,	//행 추가시 포함되지 않은 필드의 값을 data field의 기본값으로 채움
        fillColumnDefaults: true,	//행 추가시 포함되지 않은 필드의 값을 컬럼의 기본값으로 채움
        forceRowValidation: false,	//복수행 붙여 넣기 중 행별로 행 validation을 실행
        forceColumnValidation: false,	//복수행 붙여 넣기 중 행별로 컬럼 validation을 실행
        selectionBase: false,	// true면 focus 셀이 포함된 선택 영역의 처음 셀부터 붙여넣기 시작
        stopOnError: false,	//Validation이 실패하거나 형변환이 실패하면 나머지 붙여넣기를 중지하고 예외를 발생
        noEditEvent: false,	//단일행을 붙여넣기 할때 Cell마다 발생하는 onEditRowChanged가 발생하지 않는다.
        eventEachRow: true,	//복수행을 붙여넣기 할때 각 행마다 onEditRowPasted이벤트가 발생
        checkReadOnly: true,	//readOnly이거나 editable이 false인 Column은 paste대상에서 제외
        checkDomainOnly: true	// DropDown Editor의 domainOnly가 true인 컬럼에 붙여넣기 할때 values에 없는 값은 붙여넣기 되지 않음
    },
    copy : {
        enabled : true, //false이면 복사가 되지 않는다.
        singleMode : false, //true면 선택 영역과 상관없이 포커스를 갖는 셀 하나의 값만 클립보드로 복사한다.
        datetimeFormat : "yyyy-MM-dd", //이 속성에 “yyyy-MM-dd” 같은 형식을 지정하면 지정한 형태로 복사가 된다.
        lookupDisplay : true, //true인 경우 label 값을 복사하고, false인 경우 value값을 복사한다.
        copyDisplayText : true //number, date, dropdown, multicheck 의 경우 보여지는 Text로 복사한다.
    },
    //그룹핑 옵션
    grouping : {
        enabled: true,
        prompt: "컬럼 헤더를 이 곳으로 끌어다 놓으면 그 컬럼으로 그룹핑합니다.",
        linear: false,
        expandWhenGrouping: false,
        toast: {
            visible: true
        }
    },
    //에디터 옵션
    editor : {
        useCssStyleDropDownList : true,
        useCssStyleDatePicker : true,
        useCssStylePopupMenu : true,
        useCssStyleMultiCheck : true,
        applyCellFont: true
    },
    filtering : {
        selector: {
            useCssStyle: true
        }
    },
    display : {
        rowFocusVisible: false,
        rowFocusBackground: "#32f2fbff"
    },
    dataProvider : {
        commitBeforeDataEdit : false, // 그리드가 편집중일때 grid.setValue,
        // dataProvider.setValue를 하는 경우 편집 중인 행을
        // commit시킨다.
        softDeleting : true
        // softDeleting이 true인 경우 실제로 삭제되지않고 rowState만 변경
    }
}
//============================================================================================
//			Style 관련
//============================================================================================
//이미지 관련 URL
GridWrapper.prototype.imageUrl = "/assets/images/ams/";
GridWrapper.prototype.style = {};
var gridFontFamily = "";
for(var i = 0; i < parent.COMMON_CONFIG.length; i++)
{
    if(
        "SYS_KO_FONT" == parent.COMMON_CONFIG[i]["configurationCode"]
        ||"SYS_EN_FONT" == parent.COMMON_CONFIG[i]["configurationCode"]
    )
        gridFontFamily += parent.COMMON_CONFIG[i]["configurationValue"]+",";

}
if(gridFontFamily != "")
    GridWrapper.prototype.style.fontFamily = gridFontFamily.substring(0,gridFontFamily.length-1);
else
    GridWrapper.prototype.style.fontFamily = "nanum";

GridWrapper.prototype.style = {
    _default : {
        background : "#ffffffff",
        fontSize : 12,
        fontFamily : GridWrapper.prototype.style.fontFamily,
        fontBold : false
    },
    column : {
        editable : {
            fontFamily : GridWrapper.prototype.style.fontFamily,
            fontSize : 12
        },
        disable : {
            background : "#fff2f2f2",
            fontSize : 12,
            fontFamily : GridWrapper.prototype.style.fontFamily,
            fontBold : false
        },
        required : {
            "textAlignment" : "near",
            "background" : "#fffffdd6",
            "fontSize" : 12,
            fontFamily : GridWrapper.prototype.style.fontFamily,
            "fontBold" : false
        }
    },
    // dataType에 사용되는 스타일
    data : {
        date : {
            /*{
                type : "date",
                    datetimeFormat : "yyyy-MM-dd",
                mask : {
                editMask:"9999-99-99"
                    ,placeHolder:"yyyy-MM-dd" //편집기에 표시될 형식
                    ,includedFormat:true //편집기에 표시된 내용이 그대로 셀값으로 전달
            },
                fontFamily : "nanum",
                    fontSize : 12
            }*/
            type : "date",
            datetimeFormat : "yyyy-MM-dd",
            fontFamily : GridWrapper.prototype.style.fontFamily,
            fontSize : 12
        },
        timestamp : {
            type : "datetime",
            datetimeFormat : "yyyy-MM-dd HH:mm:ss",
            mask : {
                editMask:"9999-99-99 99:99:99"
                ,placeHolder:"yyyy-MM-dd HH:mm:ss" //편집기에 표시될 형식
                ,includedFormat:true //편집기에 표시된 내용이 그대로 셀값으로 전달
            },
            fontFamily : GridWrapper.prototype.style.fontFamily,
            fontSize : 12
        },
        combo : {
            type : "dropDown",
            fontFamily : GridWrapper.prototype.style.fontFamily,
            domainOnly : false,
            textReadOnly : false,
            fontSize : 12
        },
        check : {
            type : "check",
            editable : true,
            startEditOnClick : true,
            trueValues : "Y",
            falseValues : "N",
            labelPosition : "center"
        },
        imageButtons : {
            width: 16,
            height: 14,
            images: [
                {
                    name: "팝업버튼",
                    up: GridWrapper.prototype.imageUrl+"search_normal.png",
                    hover: GridWrapper.prototype.imageUrl+"search_hover.png",
                    down: GridWrapper.prototype.imageUrl+"search_click.png"
                }]
        }
    },
    parsing : // 데이터 파싱시 사용되는 스타일
        {
            header : {
                textAlignment: "near",
                background: "linear,#f2f2f2",
                fontSize: 12,
                fontFamily : GridWrapper.prototype.style.fontFamily,
                foreground: "#000000",
                borderRight: "#cccccc, 1",
                fontBold: false
            },
            body : {
                background : "#ffffffff",
                fontSize : 12,
                fontFamily : GridWrapper.prototype.style.fontFamily,
            },
            grid : {
                border: "#ffffffff,0"
            }
        },
    event : {
        selection : {
            background : "#00f2fbff",
            border : "#88000000,1"
        }
    }
};

//============================================================================================
//			Event 관련
//============================================================================================
/*이벤트 실행함수*/
GridWrapper.prototype.dispatch = function()
{
    var eventName = arguments[0];

    if(!this.event[eventName])
        return ;

    var args = new Array();
    for (var i = 1; i < arguments.length; i++)
        args.push(arguments[i]);

    var events = this.event[eventName];

    for(var i = 0; i < events.length; i++)
    {
        if(events[i])
            events[i].apply(this,args);
    }
}
GridWrapper.prototype.defaultBind = function(){}
GridWrapper.prototype.bind = function (eventName, func) {
    if (!this.event[eventName])
        this.event[eventName] = new Array();
    this.event[eventName].push(func);
}
GridWrapper.prototype.unbind = function (eventName) {
    if (this.event[eventName])
        this.event[eventName] = new Array();
}

GridWrapper.prototype.onRowsPasted = function(_event) { this.bind("onRowsPasted",_event);}
GridWrapper.prototype.onKeydown = function(_event) { this.bind("onKeyDown",_event);}
GridWrapper.prototype.onKeyUp = function(_event) { this.bind("onKeyUp",_event);}
GridWrapper.prototype.onEditCommit = function(_event) { this.bind("onEditCommit",_event);}
GridWrapper.prototype.onEditRowChanged = function(_event) { this.bind("onEditRowChanged",_event);}
/*GridWrapper.prototype.itemClick = function(_event)
{
    this.bind("onDataCellClicked",function(grid,index){
        if(-1 == index.dataRow || -1 == index.itemIndex)
            return ;

        _event(grid.getDataProvider().getJsonRow(index.dataRow),index);
    })
};
*/
GridWrapper.prototype.itemClick = function(_event)
{
    this.bind("onDataCellClicked",function(grid){
        var index = grid.getCurrent();
        if(-1 == index.dataRow)
            return ;

        _event(grid.getDataProvider().getJsonRow(index.dataRow),index);
    })
    this.bind("onKeyDown",function(grid, key, ctrl, shift, alt) {
        console.log(key);
        if(key == 46 && ctrl)
            this.dispatch("onRemoveRow");
    });



    this.bind("onKeyUp",function(grid, key, ctrl, shift, alt){

        var index = grid.getCurrent();
        if(-1 == index.dataRow)
            return ;

        //키를 위로하거나 아래로 할때 조회를 itemClick에 대한 이벤트를 진행한다
        if(key == 38 || key == 40)
        {
            _event(grid.getDataProvider().getJsonRow(index.dataRow),index);
        }
    })

};
GridWrapper.prototype.onDataCellClicked = function(_event) { this.bind("onDataCellClicked",_event); }
GridWrapper.prototype.onEditChange = function(_event) { this.bind("onEditChange",_event); }
GridWrapper.prototype.onDataCellDblClicked = function(_event) { this.bind("onDataCellDblClicked",_event); }
//줄 추가 전에 호출되는 함수
GridWrapper.prototype.addRowBeforeEvent = function(_event) { this.bind("onBeforeAddRow",_event); }
//줄 추가 후에 호출되는 함수
GridWrapper.prototype.addRowAfterEvent = function(_event) { this.bind("onAfterAddRow",_event); }
//줄 지워지면 호출되는 함수
GridWrapper.prototype.removeRowEvent = function(_event) { this.bind("onRemoveRow",_event); }

//============================================================================================
//			Grid 공통영역
//============================================================================================

GridWrapper.prototype.addRow = function () {
    if(this.runAdd)
    {
        this.gridView.beginAppendRow();
        this.gridView.setFocus();
    }
}
/**
 * gridView 객체 반환함수
 */
GridWrapper.prototype.getGridView = function () {
    return this.gridView;
};

/**
 * 현재위치 초기화 함수
 */
GridWrapper.prototype.resetCurrent = function () {
    this.gridView.resetCurrent();
}
/**
 * 그리드뷰 커밋 함수
 */
GridWrapper.prototype.commit = function () {
    this.gridView.commit(true);
}
GridWrapper.prototype.clearRowStates = function () {
    this.dataProvider.clearRowStates(true, false);
}
/**
 * 그리드 생성한 오브젝트 설정하는 함수
 * @param _makeObj
 */
GridWrapper.prototype.setMakeObj = function (_makeObj) {
    this.makeObj = _makeObj;
}
/**
 * 팝업 콜백 설정하는 함수
 * @param _popCallback
 */
GridWrapper.prototype.popupCallback = function (_popCallback) {
    popCallback = _popCallback;
}
/**
 * 컨텍스트 콜백 설정하는 함수 ( 컨텍스트 메뉴 )
 * @param i_callback
 */
GridWrapper.prototype.setContextCallback = function (i_callback) {
    callback = i_callback;
};
/**
 * 컨텍스트 콜백 가져오는 함수
 * @returns {*}
 */
GridWrapper.prototype.getContextCallback = function () {
    return callback;
};
/**
 * 엔티티 ( 엑셀 명 ) 설정하는 함수
 * @param entityName
 * @returns {GridWrapper}
 */
GridWrapper.prototype.setEntityName = function (entityName) {
    this.i_entity_name = entityName;
    return this;
};
/**
 * 그리드 스타일 설정하는 함수 ( 넓이, 높이 )
 * @param width
 * @param height
 * @returns {GridWrapper}
 */
GridWrapper.prototype.setGridStyle = function (width, height) {
    this.i_width = width;
    this.i_height = height;
    return this;
};
/**
 * 그리드 옵션 설정하는 함수
 * @param _gridOption
 */
GridWrapper.prototype.setOption = function (_gridOption) {
    this.gridOption = $.extend({}, this.option,this._gridDefaultOption, _gridOption);
};


/**
 * 데이터 설정하는 함수
 * @param mode
 * @param list
 * @returns {*}
 */
GridWrapper.prototype.setData = function (mode, list) {
    this.dataProvider.fillJsonData(list, {
        fillMode: mode
    });
    return this;
};

/**
 * 현재의 위치를 반환해주는 함수
 * @returns {*|{uuid}}
 */
GridWrapper.prototype.getCurrent = function () {
    return this.gridView.getCurrent();
}
/**
 * 필드의 위치를 반환하는 함수
 * @param fieldName
 * @returns {*}
 */
GridWrapper.prototype.getFieldIndex = function (fieldName) {
    return this.dataProvider.getFieldIndex(fieldName);
}

/**
 * 특정 위치에 변수를 설정하는 함수
 * @param index
 * @param fieldIdx
 * @param data
 */
GridWrapper.prototype.setValue = function (rowIndex, fieldIdx, data) {
    this.dataProvider.setValue(rowIndex, fieldIdx, data);
}
/**
 * 다수의 값을 변경하는 함수
 * @param itemIndex
 * @param values
 * @param strict
 */
GridWrapper.prototype.setValues = function (itemIndex, values, strict) {
    this.dataProvider.setValues(itemIndex, values, strict);
}
/**
 * 그리드 포커스 주는 함수
 */
GridWrapper.prototype.setFocus = function () {
    this.gridView.setFocus();
}
/**
 * 그리드 화면 옵션 셋팅하는 함수
 * @param option
 */
GridWrapper.prototype.setDisplayOptions = function (option) {
    this.gridView.setDisplayOptions(option);
};
/**
 * DataProvider 돌려주는 함수
 */
GridWrapper.prototype.getDataProvider = function () {
    return this.dataProvider;
};
/**
 * 리스트 표현개수 설정하는 함수
 * @param _listCount
 */
GridWrapper.prototype.setListCount = function (_listCount) {
    maxCount = _listCount;
};

/**
 * 그리드 페이지 동적 로딩하는 함수
 * @param url
 * @param data
 * @param parsingCallback
 */
GridWrapper.prototype.load = function(url,data,parsingCallback)
{

    newStart = this.dataProvider.getRowCount();
    $.ajax({
        url : url
        , type : "POST"
        , data : JSON.stringify(data)
        , contentType : "application/json"
        , dataType : "JSON"
        , success: function(list) {


            var fillMode = "set";
            if (0 != newStart) {
                fillMode = "insert";
            }

            if (undefined === list || list.length < 1) {
                _addRow();
            }
            else {
                if (parsingCallback)
                    list = parsingCallback(list);

                this.dataProvider.fillJsonData(list,
                    {fillMode: fillMode, start: newStart, count: maxCount});
            }

        }
    });
};
/**
 * 컬럼 데이터 설정
 *
 * @param Array
 * @description { "sortNo" : number ,"name" : text ,"text" : text
	 *              ,"required" : boolean ,"editable" : boolean ,"dataType" :
	 *              text ,"width" : number ,"fontSize" : number ,"fontBold" :
	 *              boolean }
 */
GridWrapper.prototype.setColumnInfo = function(list) {
    var columnList = [];
    var fieldList = [];
    var data = undefined;
    // 정렬순서로 데이터 정렬
    list.sort(function(a, b) {
        if (undefined === a.sortNo) {
            return 0;
        } else {
            return a.sortNo - b.sortNo;
        }
    });

    var generateColumnInfo = function(_this, data, dataType)
    {
        var obj = {
            name : data.name,
            fieldName : data.name,
            width : data.width,
            header : data.header === undefined ? {
                text : data.text
            } : $.extend({
                text : data.text
            },data.header),
            styles : data.styles === undefined ? {} : data.styles,
            renderer : data.renderer === undefined ? { type : data.dataType } : $.extend({},data.renderer,{type : data.dataType}),
            editable : data.editable === undefined ? true : data.editable,
            //required : data.required === undefined ? false : data.required,
            visible : data.visible === undefined ? true : data.visible,
            readOnly : !(data.editable === undefined ? true : data.editable)
        };

        _this.addDefaultEditColumnProperties(obj.name,obj.editable);
        if (data.autoincrement)
            addAutoIncreament({index: i, name: data.name});

        // tab사용시 마지막 컬럼사용하기 때문에 세팅.
        if (data.editable) {
            _this.defaultStyles[data.name] = "editable";
        }

        // 필수여부 (비활성 여부 빠져있음.)
        if (data.disable) {
            styles = _this.defaultStyle.column.disable;
            _this.defaultStyles[data.name] = "disable";
            obj.editable = false;
        } else if (data.required) {
            styles = _this.defaultStyle.column.required;
            _this.requiredColumnList[i] ={name : data.name, title : data.text};
            _this.defaultStyles[data.name] = "required";
        } else {
            styles = _this.defaultStyle._default;
            _this.defaultStyles[data.name] = "_default";
        }

        obj.styles = $.extend({},styles, {
            textAlignment : data.textAlignment === undefined ? "near"
                : data.textAlignment,
            fontSize : data.fontSize === undefined ? 12 : data.fontSize,
            fontBold : data.fontBold === undefined ? false : data.fontBold
        },obj.styles);


        obj.defaultValue = data.defaultValue === undefined ? "" : data.defaultValue ;


        // 데이터 타입
        switch (dataType) {
            case "popup":
                obj.button = "image";
                obj.imageButtons = $.extend({},_this.defaultStyle.data.imageButtons,data.imageButtons);
                _this.popupNames[data.name] = { popupCode : data.popupCode, sqlColumn : {}};
                for(var key in data.sqlColumn)
                {
                    _this.popupNames[data.name]["sqlColumn"][key] = data.sqlColumn[key];
                }

                break;
            case "code":
                //생각좀 해봐야겠음.
                obj.type = "text";
                obj.editor = {
                    //type: "multiline",
                    textCase: "upper"
                };
                obj.displayRegExp = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/gi;
                obj.displayReplace = "";
                _this.validateColumn[data.name] = function(value)
                {
                    var regexp = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/gi;
                    var result = regexp.test(value);
                    if(value)
                        value = value.replace(regexp,"");
                    return !result;
                }
                break;
            case "number":
                _this.validateColumn[data.name] = function(value)
                {
                    var regexp = /[^0-9]/gi;
                    var result = regexp.test(value);
                    if(value)
                        value = value.replace(regexp,"");
                    return !result;
                }
                break;
            case "commanumber":
                obj.displayRegExp = /\B(?=(\d{3})+(?!\d))/g;
                obj.displayReplace = ",";
                obj.style = {textAlignment: "far"};
                break;
            case "date":
                obj.editor = _this.style.data.date;
                obj.styles = $.extend({}, _this.defaultStyle.data.date, obj.styles );
                obj.displayRegExp = "([0-9]{4})([0-9]{4})([0-9]{4})([0-9]{4})";
                obj.displayReplace = "$1-$2-$3-$4";
                break;
            case "password":
                obj.type = "text";
                obj.renderer = {type : "text"};
                obj.displayRegExp = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z|0-9|\$|\/|\.|!|@|#|%|^|&|*|(|)|\-|=|+|_|{|}|[|\]|;|'|"|:|\?|>|<]/gi;
                obj.displayReplace = "*";
                break;
            case "timestamp":
                data.dataType = "datetime";
                obj.type = "datetime";
                obj.renderer = {type : "datetime"};
                obj.editor = _this.defaultStyle.data.timestamp;
                obj.styles = $.extend({}, _this.defaultStyle.data.timestamp, obj.styles);
                obj.displayRegExp = /\B(?=(\d{3})+(?!\d))/g;
                obj.displayReplace = ",";
                break;
            case "richtext":
                obj.type = "text";
                obj.editor = {
                    type: "multiline",
                    textWrap : "normal"
                };
                obj.styles = {
                    textAlignment : "near"
                    , background : "#ffffffff"
                    , fontSize : 12
                    , fontFamily : GridWrapper.prototype.style.fontFamily
                    , fontBold : false
                }
                obj.renderer = {showTooltip : true};
                break;
            case "text":
                break;
            case "combo":
                obj.editor = $.extend({},_this.defaultStyle.data.combo,data.editor);
                obj.values = data.values;
                obj.labels = data.labels;
                obj.sortable = data.sortable === undefined ? false
                    : data.sortable;

                if(!data.lookupDisplay)
                    data.lookupDisplay = true;

                obj.lookupDisplay = data.lookupDisplay;
                break;
            case "check":
                obj.renderer = $.extend({},_this.defaultStyle.data.check,data.renderer);
                obj.editable = false;

                if(!data.defaultValue)
                    data.defaultValue = "Y";

                obj.defaultValue = data.defaultValue;

                break;
            case "button":
                obj.button = "image";
                obj.imageButtons = $.extend({},_this.defaultStyle.data.imageButtons,data.imageButtons);
                break;
            case "icon":
                obj.renderer = {type : "icon",textVisible : false}
                obj.dynamicStyles = data.dynamicStyles;
                obj.imageList = data.imageList;
                obj.values = data.values;
                obj.labels = data.labels;
                obj.lookupDisplay = true;
                obj.editable  = false;
                obj.editor = {type : "dropDown",dropDownCount:data.values.length,domainOnly : true,textReadOnly : false}
                obj.styles= {background:"#ffffffff",iconIndex: 0,iconLocation:"center",iconAlignment:"center",iconOffset:4,iconPadding:4}
                break;
            default:
                break;
        }
        return obj;
    }
    var generateFieldInfo = function(_this,data,dataType)
    {
        var fieldObj = {};
        // 데이터 타입
        switch (dataType) {
            case "popup":
                break;
            case "code":
                break;
            case "number":
                break;
            case "commanumber":
                break;
            case "date":
                break;
            case "password":
                break;
            case "timestamp":
                fieldObj.datetimeFormat = "iso";
                break;
            case "richtext":
                break;
            case "text":
                break;
            case "combo":
                break;
            case "check":
                break;
            case "button":
                break;
            case "icon":
                fieldObj.dynamicStyles = data.dynamicStyles;
                fieldObj.imageList = data.imageList;
                break;
            default:
                break;
        }
        return fieldObj;
    }

    var styles = {};
    var obj = undefined;
    var fieldObj = undefined;
    var columnNFieldInfo = undefined;
    var innerColumnInfo = undefined;
    var dataType = undefined;
    var childrenDatType = undefined;
    for (var i = 0; i < list.length; i++) {
        data = list[i];

        // 필수여부 없으면 일반 모양에 수정 x
        // 필수여부 Y 노란색 수정 가능
        // 필수여부 N 일반 모양
        // 활성여부 Y N 회색
        fieldObj = {};

        dataType = data.dataType;

        fieldObj = generateFieldInfo(this, data, dataType);
        obj = generateColumnInfo(this, data, dataType);

        if(data.columnList && data.columnList.length > 0)
        {
            innerColumnInfo = new Array();
            for(var j = 0; j < data.columnList.length; j++)
            {
                childrenDatType = data.columnList[j].dataType;
                innerColumnInfo.push(generateColumnInfo(this, data.columnList[j],childrenDatType));
                fieldList.push($.extend(generateFieldInfo(this, data.columnList[j],childrenDatType),{
                    fieldName : data.name,
                    dataType : childrenDatType
                }));
            }

            obj.columns = innerColumnInfo;
            obj["type"] = "group";
            obj["renderer"]["type"] = "group";
            dataType = "group";
        }

        this.columnInfo.push(data);

        columnList.push(obj);
        fieldList.push($.extend(fieldObj,{
            fieldName : data.name,
            dataType : dataType
        }));
        data = undefined;
        styles = {};
    }
    this.dataProvider.setFields(fieldList);
    this.gridView.setColumns(columnList);
    return this;
};

//==========================================================================================
//					 duplicate 영역
//==========================================================================================

/**
 * 체크된 리스트 반환하는 함수
 * @returns {Array}
 */
GridWrapper.prototype.getCheckedList = function()
{
    var rowIndexList = this.gridView.getCheckedRows(false);
    var checkList = new Array();
    for(var i = 0; i < rowIndexList.length; i++)
    {
        checkList.push(this.dataProvider.getJsonRow(rowIndexList[i]));
    }

    return checkList;
}
/**
 * 줄 추가시 검증로직 세팅하는 함수
 * @param func
 */
GridWrapper.prototype.setAppendValiate = function(func) {
    if (typeof func == "function") {
        appendValidate = func;
    } else {
        throw "함수만 설정 가능합니다.";
    }
};

/**
 * 현재행 지우는 함수
 */
GridWrapper.prototype.removeRow = function()
{
    if(runDel)
        thisÎ.gridView.getDataProvider().removeRow(this.gridView.getCurrent().dataRow);
}

/**
 * 스타일 셋업하는 함수
 * @param style
 * @param value
 */
GridWrapper.prototype.setStyle = function(style,value)
{
    this.defaultStyle[style] = value;
};

/**
 * 프로퍼티 추가하는 함수
 * @param property
 */
GridWrapper.prototype.addRemoveProperty = function(property)
{
    removeProperty.push(property);
}
/**
 * 프로퍼티 지우는 함수
 * @param index
 */
GridWrapper.prototype.deleteRemoveProperty = function(index)
{
    removeProperty.splice(index,1);
}
/**
 * 프로퍼티 클리어하는 함수
 * @param property
 */
GridWrapper.prototype.clearRemoveProperty = function(property)
{
    removeProperty = new Array();
}
/**
 * 해더 스타일 가져오는 함수
 * @param styleName
 * @returns {*}
 */
GridWrapper.prototype.getHeaderStyle = function(styleName) {
    return this.defaultStyle.parsing.header[styleName];
};
/**
 * 그리드 바디 스타일 가져오는 함수
 * @returns {body|{background, fontSize, fontFamily}}
 */
GridWrapper.prototype.getBodyStyle = function() {
    return this.defaultStyle.parsing.body;
};
/**
 * 컨택스트 메뉴 추가 메서드
 * @param menu
 * @returns {*}
 */
GridWrapper.prototype.addContextMenu = function(menu)
{
    var menus = [];
    menus.push({"label" : menu});
    for(var i = 0; i < contextMenu.length; i++)
    {
        menus.push(contextMenu[i]);
    }
    contextMenu = menus;
    if(this.gridView)
        this.gridView.setContextMenu(contextMenu);

    return this;
};

/**
 * 그리드 생성
 */
GridWrapper.prototype.makeGrid = function() {
    $("#" + this.i_id).css({
        width : this.i_width,
        height : this.i_height
    });

    this._initOption(this.gridView, this.dataProvider);
    bindEvent();

    this.gridView.setContextMenu(this.contextMenu);
    this.gridView.setDataSource(this.dataProvider);
};

GridWrapper.prototype.setCustomCellStyleRow = function(gridWrapper, grid, row, type, conditionFunc, columnNames,doCommit) {
    var styles = undefined;
    var editable = false;
    if (type == "disable") {
        styles =  this.defaultStyle.column.disable;
        editable = false;
    }
    else if (type == "required") {
        styles =  this.defaultStyle.column.disable;
        editable = true;
    } else {
        return;
    }

    if (undefined === doCommit || doCommit)
        gridWrapper.gridView.commit();

    var rows = this.dataProvider.getJsonRows(0, -1);

    var columnIndexList = new Array();

    for (var i = 0; i < columnNames.length; i++) {
        columnIndexList.push(this.dataProvider.getFieldIndex(columnNames[i]));
    }
    grid.addCellStyle("customStyle01", styles, true);
    var applyData = {rows: new Array(), columns: columnNames}

    if (conditionFunc(this, rows[row])) {
        gridWrapper.gridView.setCellStyles(row, columnNames, type, true);
    } else {
        for (var j = 0; j < columnIndexList.length; j++) {
            gridWrapper.gridView.setCellStyle(row, columnNames[j], this.defaultStyles[columnNames[j]], true);
        }
    }

}

GridWrapper.prototype.setCustomCellStyleRows = function(type, conditionFunc, columnNames,doCommit)
{
    var styles = undefined;
    var editable = false;
    if (type == "disable") {
        editable = false;
    }
    else if (type == "required") {
        editable = true;
    }else {
        return ;
    }

    if (undefined === doCommit || doCommit)
        this.gridView.commit();

    var rows = this.dataProvider.getJsonRows(0, -1);

    var columnIndexList = new Array();

    for (var i = 0; i < columnNames.length; i++) {
        columnIndexList.push(this.dataProvider.getFieldIndex(columnNames[i]));
    }

    var applyData = {rows: new Array(), columns: columnNames}

    for (var i = 0; i < rows.length; i++) {
        if (conditionFunc(rows[i])) {
            applyData.rows.push(i);

            for (var j = 0; j < columnIndexList.length; j++) {
                //this.gridView.setCellStyle(i, columnIndexList[j], type, true);
                this.gridView.setCellStyles(i, columnNames, type, true);
            }
        } else {
            for (var j = 0; j < columnIndexList.length; j++) {
                this.gridView.setCellStyle(i, columnIndexList[j], this.defaultStyles[columnIndexList[j]], true);
            }
        }
    }

    /*해당 그리드가 수정관련한 부분에 대해서 못하거나 할 수 있게 만들어주는 함수*/
    this.unbind("onCurrentRowChanged");

    var changeColumnState= function(_this, grid, oldRow, newRow)
    {
        var curr = grid.getCurrent();
        var doSetting = false;
        for (var i = 0; i < applyData.rows.length; i++) {
            if (applyData.rows[i] == curr.dataRow) {
                doSetting = true;
            }
        }
        //setting을 해야되는 경우 진행
        if (doSetting) {
            for (var i = 0; i < applyData.columns.length; i++) {
                grid.setColumnProperty(applyData.columns[i], "editable", editable);
            }
            var sel = {startItem: 1, endItem: 1, style: "block"};
            _this.gridView.setSelection(sel);
            _this.gridView.resetCurrent();
        }
        else {
            //setting에 영향을 안받을 경우에는 기본값으로 복원
            for (var column in _this.defaultEditColumnProperties) {
                grid.setColumnProperty(column, "editable", _this.defaultEditColumnProperties[column]);
                grid.setCellStyle(curr.dataRow, column, _this.defaultStyles[column], true);
            }
        }
    }

    this.bind("onCurrentRowChanged", function (grid, oldRow, newRow) {
        changeColumnState(this, grid,oldRow,newRow);
    });
    changeColumnState(this, this.gridView);
}

/**
 * 검증 로직 세팅함수
 * @param validations
 */
GridWrapper.prototype.setValidations = function(validations) {
    var column = undefined;
    for (var i = 0; i < validations.length; i++) {
        column = this.gridView.columnByName(validations[i].fieldName);
        delete (validations[i].fieldName);
        column.validations = validations[i];
        this.gridView.setColumn(column);
    }
};
/**
 * 현재 데이터 검증
 * @returns {*}
 */
GridWrapper.prototype.validate = function()
{
    this.gridView.commit();
    return this.validateData();
}
//데이터  반환 함수
GridWrapper.prototype.getData = function() {};

/**
 * 줄 모두 없애는 함수
 */
GridWrapper.prototype.clearRows = function() { this.gridView.getDataProvider().clearRows(); };
/**
 * 열 고정하는 함수
 * @param p_fixedOptions
 */
GridWrapper.prototype.setFixedOptions = function(p_fixedOptions) { this.gridView.setFixedOptions(p_fixedOptions);};
/**
 * 인디케이터 설정함수
 * @param p_indicator
 */
GridWrapper.prototype.setIndicator = function(p_indicator) { this.gridView.setIndicator(p_indicator);};
/**
 * 상태 변경 데이터 있는지 확인하는 함수
 * @returns {boolean}
 */
GridWrapper.prototype.isDataChanged = function() { return this.getData().length > 0; };

/**
 * 이름으로 컬럼 가져오는 함수
 * @param _columnName
 * @returns {*}
 */
GridWrapper.prototype.columnByName = function(_columnName) { return this.gridView.columnByName(_columnName);};
/**
 * 컬럼 설정하는 함수
 * @param _column
 */
GridWrapper.prototype.setColumn = function(_column) { this.gridView.setColumn(_column);};
/**
 * 총 갯수 반환하는 함수
 * @returns {*}
 */
GridWrapper.prototype.getRowCnt = function() { return this.dataProvider.getRows(); };

//셀렉트 된 행 데이터 가져오는 함수
GridWrapper.prototype.getSelectedData = function () {
    if (this.gridView.getCurrent().itemIndex == -1) {
        return undefined;
    } else {

        if (this.gridView.getCurrent().dataRow == -1)
            this.gridView.commit(true);

        return this.gridView.getDataProvider().getJsonRow(
            this.gridView.getCurrent().dataRow);

    }
};