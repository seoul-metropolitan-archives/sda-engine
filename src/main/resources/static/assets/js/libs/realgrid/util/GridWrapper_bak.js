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


/**
 * 
 * real grid
 */


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
            commitWhenExitLast : false,	//tab/enter 키로 마지막 셀을 벗어날 때 행 commit 한다.
            crossWhenExitLast : true,	//tab/enter 키로 마지막 셀을 벗어날 때 다음 행으로 이동한다.
            useTabKey : true,			//true면 Tab 키로 셀 이동할 수 있다.
            enterToNextRow : false,		//enter 입력시 다음 row로 이동
            enterToEdit: true,			//enter 시 텍스트 편집
            skipReadOnly : false,		//true이면 컬럼간 이동시 readOnly 셀은 건너뛰고 다음 컬럼 셀로 이동한다.
            skipReadOnlyCell : false, 	//true이면 한 컬럼에서 행간(Vertical 컬럼 그룹 행을 포함) 이동시 readOnly 셀은 건너뛰고 다음 행의 컬럼 셀로 이동한다.
            appendWhenExitLast : true, 	//commitWhenExitLast 가 true 일 경우 enterb/tab 키로 마지막셀을 벗어날 경우 행이 추가된다.
            appendWhenInsertKey : false, //Insert 키 입력시 해당 위치에 행이 삽입되는 것이 아니라 가장 마지막행에 추가된다.
            editWhenFocused : false, 	//셀이 선택될때마다 에디터가 표시된다.
            editWhenClickFocused : true, //한번 선택된 셀을 다시한번 선택하면 에디터가 표시된다.(더블 클릭이 아님)
            revertable : false,			//dataProvider.softDeleting = true 인 경우 삭제 상태인 행들을 ctrl+shift+del 키 입력시 원래 상태로 되돌리겠는지의 여부를 설정한다.
            maxLengthToNextCell : false, //column.editor.maxLength에 지정한 자리수 만큼 입력되면 다음 셀로 이동된다.editFormat이 있는 경우 보여지는 글자를 기준으로 maxLength가 체크된다. (numberEditor, dateEditor)multiLine의 경우 \n과 같이 제어문자도 글자수에 포함된다.
            innerDraggable : true,		//Inner Drag & Drop기능 사용 여부를 지정한다
            enterToTab : false
        },
        paste : {
            singleMode: false,	//포커스를 갖는 셀 하나에만 붙여넣기
            enabled: true,	//Paste가능여부 설정
            startEdit: true,	//붙여넣게 될 값이 복수 행이 아니고, 붙여넣을 행이 아직 편집 중이 아니면 편집을 시작
            commitEdit: true,	//복수 행 붙여넣기일 때 기존 편집 상태를 commit
            enableAppend: true,	//붙여넣을 복수 행이 기존 행의 범위를 넘어설 때 행을 추가
            fillFieldDefaults: false,	//행 추가시 포함되지 않은 필드의 값을 data field의 기본값으로 채움
            fillColumnDefaults: true,	//행 추가시 포함되지 않은 필드의 값을 컬럼의 기본값으로 채움
            forceRowValidation: true,	//복수행 붙여 넣기 중 행별로 행 validation을 실행
            forceColumnValidation: true,	//복수행 붙여 넣기 중 행별로 컬럼 validation을 실행
            selectionBase: false,	// true면 focus 셀이 포함된 선택 영역의 처음 셀부터 붙여넣기 시작
            stopOnError: false,	//Validation이 실패하거나 형변환이 실패하면 나머지 붙여넣기를 중지하고 예외를 발생
            noEditEvent: false,	//단일행을 붙여넣기 할때 Cell마다 발생하는 onEditRowChanged가 발생하지 않는다.
            eventEachRow: true,	//복수행을 붙여넣기 할때 각 행마다 onEditRowPasted이벤트가 발생
            checkReadOnly: true,	//readOnly이거나 editable이 false인 Column은 paste대상에서 제외
            checkDomainOnly: true	// DropDown Editor의 domainOnly가 true인 컬럼에 붙여넣기 할때 values에 없는 값은 붙여넣기 되지 않음
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
        commitBeforeDataEdit : true, // 그리드가 편집중일때 grid.setValue,
        // dataProvider.setValue를 하는 경우 편집 중인 행을
        // commit시킨다.
        softDeleting : true
        // softDeleting이 true인 경우 실제로 삭제되지않고 rowState만 변경
    }
}
GridWrapper.prototype.style = {
    _default : {
        background : "#ffffffff",
        fontSize : 12,
        fontFamily : "nanum",
        fontBold : false
    },
    column : {
        editable : {
            fontFamily : "nanum",
            fontSize : 12
        },
        disable : {
            background : "#fff2f2f2",
            fontSize : 12,
            fontFamily : "nanum",
            fontBold : false
        },
        required : {
            "textAlignment" : "near",
            "background" : "#fffffdd6",
            "fontSize" : 12,
            "fontFamily" : "nanum",
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
            fontFamily : "nanum",
            fontSize : 12
        },
        timestamp : {
            type : "datetime",
            datetimeFormat : "yyyy-MM-dd HH:mm:ss.SSS",
            mask : {
                editMask:"9999-99-99 99:99:99"
                ,placeHolder:"yyyy-MM-dd HH:mm:ss" //편집기에 표시될 형식
                ,includedFormat:true //편집기에 표시된 내용이 그대로 셀값으로 전달
            },
            fontFamily : "nanum",
            fontSize : 12
        },
        combo : {
            type : "dropDown",
            fontFamily : "nanum",
            domainOnly : true,
            textReadOnly : true,
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
                    up: imageUrl+"search_normal.png",
                    hover: imageUrl+"search_hover.png",
                    down: imageUrl+"search_click.png"
                }]
        }
    },
    tree : {
        header: {
            background: "linear,#f2f2f2",
            fontSize: 12,
            fontFamily: "nanum",
            foreground: "#000000",
            borderRight: "#cccccc,1",
            fontBold: false,
        },
        body: {
            borderRight: "#ff000000,0px",
            borderBottom: "#f0000000,0px",
            line: "#ffaaaaaa,0px",
            fontSize: 12,
            fontFamily: "nanum",
            grid: {
                border: "#ffffffff,0"
            }
        }
    },
    parsing : // 데이터 파싱시 사용되는 스타일
        {
            header : {
                /*	과거
                 background : "linear,#f2f2f2",
                fontSize : 12,
                fontFamily : "nanum",
                foreground : "#000000",
                borderRight : "#cccccc, 1",
                fontBold : false
                */
                textAlignment: "near",
                background: "linear,#f2f2f2",
                fontSize: 12,
                fontFamily: "nanum",
                foreground: "#000000",
                borderRight: "#cccccc, 1",
                fontBold: false
            },
            body : {
                background : "#ffffffff",
                fontSize : 12,
                fontFamily : "nanum"
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

var GridWrapper = function(p_id,p_rootContext,_isTree) {
	//현재 객체
	var _this = this;
	//자신을 만든 객체
	var makeObj = undefined;
	//Grid id
	var i_id = p_id;
	//Excel Export시 사용되는 Entity 이름
	var i_entity_name = "";
	//Grid 넓이
	var i_width = "";
	//Grid 높이
	var i_height = "";
	//컬럼 리스트
	var i_columnList = [];
	//필드 리스트
	var i_fieldList = [];
	
	var callback = {
			context : undefined
	};
	//그리드 생성시 객체
	var gridView;
	//데이터 프로바이더
	var dataProvider;

	//기초 데이터 ( 줄 추가시 사용됨. )
	var defaultData = [];

	//이미지 관련 URL
	var imageUrl = "/assets/images/ams/";

	var isTree = undefined == _isTree ? false: true;

	//트리 생성시 사용되는 프로퍼티
    var childrenProp = undefined;
	var appendValidate = function() {
		return true;
	};
	
	var contextMenu = [{label : "ExcelExport"}];
	var removeProperty = new Array();
	//================================================
	//lazy load시 사용 파라메터
	var maxCount = 100;
	//================================================

    var requiredColumnList = {};
    var defaultEditColumnProperties = new Array();
	var defaultStyles = new Array();
	//그리드 옵션
	var gridOption = undefined;
	var rootContext = p_rootContext;
    var addRowBeforeEventCallback = function(){};
    var addRowAfterEventCallback = function(){};
    var removeRowEventCallback = function(){};
    var searchStartIndex = -1;

    var validateColumn = {};
    //event저장소
    var event = {};

	var init = function() {
		defaultStyle = $.extend({}, defaultStyle, _defaultStyle);
		RealGridJS.setTrace(false);
		RealGridJS.setRootContext(rootContext);
        if(isTree){
            gridView = new RealGridJS.TreeView(i_id);
            dataProvider = new RealGridJS.LocalTreeDataProvider();
		}
        else
		{
            gridView = new RealGridJS.GridView(i_id);
            dataProvider = new RealGridJS.LocalDataProvider();
		}

    };
	//엑셀 추출
	var exportExcel = function(grid,label)
	{
		grid.exportGrid({
            type: "excel",
            target: "local",
            fileName: i_entity_name+"_"+new Date().format("yyyyMMdd")+".xlsx",
            progressMessage: "Exporting Excel...",
            indicator : "default",
            header : "default",
            footer : "default",
            allItems : true,
            indenting: true,
            compatibility: true,
            showProgress : true,
            done: function () {  
                alert("done excel export")
            }
        });
	};
	var _bind = function(eventName,_func){
        if(!event[eventName])
            event[eventName] = new Array();
        event.push(func);
    }
	this.bind = function(eventName,_func){
		_bind(eventName,_func);
	}

	this.onRowsPasted = function(_event)
	{
        _bind("onRowPasted",_event);
	}
	this.onKeydown = function(_event)
	{
        _bind("onKeydown",_event);
        gridView.onKeyDown = _keydown;
	}
	
	var popCallback = undefined;

	var getPopupData = function(position)
	{
		return popupIndex[position];
	}

	var showPopup = function(grid, currentField, searchData, rows, popupData,preSearch)
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
                for(var key in popupData["sqlColumn"])
                {
                    if(popupData["sqlColumn"][key] == currentField)
                    {
                        dataProvider.setValue(rows,popupData["sqlColumn"][key],data[key]);
                        currentValue = data[key];
                    }
                }
                console.log(data);
                retData = data;
                grid.commit(true);
                for(var key in data)
                {
                    try {
                        if(popupData["sqlColumn"][key])
                            dataProvider.setValue(rows,popupData["sqlColumn"][key],data[key]);
                    }
                    catch(exception)
                    {

                    }
                }
                grid.commit(true);
                if(this.close)
                    this.close();
            }
        });

        if(popCallback)
            popCallback(grid, retData);
	}
	var _onEditChange = undefined;

	var dataCellDbClicked = new Array();
	var defaultBind = function()
	{
        if(isTree)
        {
            _bind("onKeyDown",function(grid, key, ctrl, shift, alt){
                if(key == 13)
                {
                    var index = grid.getCurrent();
                    if(-1 == index.dataRow || -1 == index.itemIndex)
                        return ;
                    if(_event)
                        _event(grid.getDataProvider().getJsonRow(index.dataRow),index);
                }
            });
        }

        _bind("onRowInserting",function (grid, itemIndex) {

            var msg = validateForMsg();
            if("" == msg)
            {
                if(addRowBeforeEventCallback)
                    addRowBeforeEventCallback(_this,makeObj);

                if(addRowAfterEventCallback)
                    addRowAfterEventCallback(_this,makeObj);
            }
            else {
                return msg;
            }

        });
        _bind("onImageButtonClicked",function(grid, itemIndex, column, buttonIndex, name){
            var popupData = getPopupData(column.dataIndex);
            if(!popupData)
                return ;
            try {
                grid.commit(true);
            }catch(exception)
            {
                grid.getDataProvider().rollback(true);
            }

            var data = grid.getDataProvider().getJsonRow(itemIndex);
            console.log(data[column.fieldName]);
            console.log(getPopupData(column.dataIndex));

            showPopup(grid, column.fieldName,data[column.fieldName],itemIndex,popupData,false);
        });
        _bind("onContextMenuItemClicked",function (grid, label, index) {
            switch(label.label)
            {
                case "ExcelExport":
                    exportExcel(grid,label);
                default :
                    if(_callback)
                        _callback(label.label,grid,index);
                    break;
            }
        });
	}
	var bindEvent = function() {
        defaultBind();

        gridView.onRowsPasted = function(){

        	if(!event["onRowsPasted"])
        		return ;

        	for(var i = 0; i < event["onRowsPasted"].length; i++)
			{
                event["onRowsPasted"][i]();
			}
		};

        gridView.onKeyDown = function(grid, key, ctrl, shift, alt){
            if(!event["onKeyDown"])
                return ;

            for(var i = 0; i < event["onKeyDown"].length; i++)
            {
                event["onKeyDown"][i](grid, key, ctrl, shift, alt);
            }
        };
		gridView.onDataCellDblClicked = function(grid,index){
            if(!event["onDataCellDblClicked"])
                return ;

            for(var i = 0; i < event["onDataCellDblClicked"].length; i++)
            {
                event["onDataCellDblClicked"][i](grid,index);
            }
		}

        gridView.onEditChange = function(grid, index, value){
            if(!event["onEditChange"])
                return ;

            for(var i = 0; i < event["onEditChange"].length; i++)
            {
                event["onEditChange"][i](grid, index, value);
            }
		}

		/**
		gridView.onEditCommit = function (grid, index, oldValue, newValue)
		{
            var popupData = getPopupData(index.fieldIndex);
            if(!popupData) {
                return;
            }
            if(!newValue || "" == newValue)
            {
                grid.cancel();
                return ;
            }
            if(-1 == index.itemIndex)
            	return ;

            showPopup(grid, index.fieldName,newValue,index.itemIndex,popupData);
			console.log(index.fieldName);
		}
		 */


        gridView.onImageButtonClicked = function(grid, itemIndex, column, buttonIdex, name)
		{
            if(!event["onImageButtonClicked"])
                return ;

            for(var i = 0; i < event["onImageButtonClicked"].length; i++)
            {
                event["onImageButtonClicked"][i](grid, itemIndex, column, buttonIdex, name);
            }

		}

		gridView.onCurrentChanged = function(grid,newIndex)
		{
            if(!event["onCurrentChanged"])
                return ;

            for(var i = 0; i < event["onCurrentChanged"].length; i++)
            {
                event["onCurrentChanged"][i](grid,newIndex);
            }
		}

		var _callback = callback;

		//사용자가 Insert 키를 눌러 새로운 행을 삽입하거나, 마지막 행에서 아래 화살표를 눌러 행을 추가하려고 할 때 호출된다. 이 콜백에서 행 추가 불가 메시지를 리턴하면 행 추가가 금지된다.
        gridView.onRowInserting = function (grid, itemIndex) {

            if(!event["onRowInserting"])
                return ;

            for(var i = 0; i < event["onRowInserting"].length; i++)
            {
                event["onRowInserting"][i](grid, itemIndex);
            }
        };

		gridView.onContextMenuItemClicked = function (grid, label, index) {
            if(!event["onContextMenuItemClicked"])
                return ;

            for(var i = 0; i < event["onContextMenuItemClicked"].length; i++)
            {
                event["onContextMenuItemClicked"][i](grid, label, index);
            }
		  };
		  gridView.onDataCellClicked = function(grid, index)
		  {
              if(!event["onContextMenuItemClicked"])
                  return ;

              for(var i = 0; i < event["onContextMenuItemClicked"].length; i++)
              {
                  event["onContextMenuItemClicked"][i](grid, label, index);
              }
		  };
		  gridView.onCellEdited  = function(grid, itemIndex, dataRow, field)
		  {
              if(!event["onCellEdited"])
                  return ;

              for(var i = 0; i < event["onCellEdited"].length; i++)
              {
                  event["onCellEdited"][i](grid, itemIndex, dataRow, field);
              }
		  }

		 $("#"+i_id).parents().eq(1).delegate(".btn_a","click",function(){
             gridView.beginAppendRow();
             gridView.setFocus();
         });
		 $("#"+i_id).parents().eq(1).delegate(".btn_d","click",function(){
		 	gridView.getDataProvider().removeRows(gridView.getSelectedRows(),false);
             if(!event["onRemoveRow"])
                 return ;

             for(var i = 0; i < event["onRemoveRow"].length; i++)
             {
                 event["onRemoveRow"][i]();
             }removeRowEventCallback
             ();
		 });
	};

	var _initOption = function(gridView, provider) {
        var option = undefined;
        if(gridOption)
            option = gridOption;
		else
            option = _gridDefaultOption;
		if(isTree)
		{
            gridView.setTreeOptions({
                "summaryMode": "aggregate",
                "stateBar": {
                    "visible": false
                },
				"header" : option.header === undefined ? {"visible": false} : option.header,
                "footer" : option.footer === undefined? {"visible": false} : option.footer
            });
            console.log(option.header === undefined ? true : option.header);


            gridView.setDisplayOptions({fitStyle : "evenFill",focusBorderWidth : 1});
            gridView.setStyles(defaultStyle.tree);
            gridView.setEditorOptions({
                applyCellFont: true
            });
            //visible: 인디케이터 영역의 화면 표시여부를 지정합니다.
            gridView.setIndicator(option.indicator === undefined? {"visible": false} : option.indicator);

            gridView.setStateBar(option.stateBar === undefined? {"visible": false} : option.stateBar);

            gridView.setCheckBar(option.checkBar === undefined? {"visible": false} : option.checkBar);
		}
		else
		{
            gridView.setOptions(option);
            gridView.setGroupingOptions(groupingDefaultOptions);
            //gridView.setEditOptions(editorDefaultOptions);
            gridView.setStyles(defaultStyle.parsing);
            gridView.setFilteringOptions(filteringDefaultOptions);
            gridView.setDisplayOptions(displayDefaultOption);
            gridView.setPasteOptions(_gridDefaultOption.paste);
            gridView.setSortingOptions({
                style: "inclusive"
            });
            gridView.setIndicator({
				visible : true,
				styles : {background : "linear,#f2f2f2", fontFamily : "nanum"}
			})
            gridView.setStateBar(option.stateBar === undefined? {"visible": false} : option.stateBar);
            dataProvider.setOptions(providerDefaultOption);
            // gridView.setStyles(defaultStyle.event.selection);
		}
        initStyle();
	};
	var initStyle = function()
	{
        gridView.addCellStyle("_default", this.style._default, true);
        gridView.addCellStyle("required", this.style.column.required, true);
        gridView.addCellStyle("editable", this.style.column.editable, true);

        var imgs = new RealGridJS.ImageList("iconList1","/assets/images/ams/icon/");
        imgs.addUrls([
            "fi.png"
            ,"fi_v.png"
            ,"fo.png"
            ,"fo_v.png"
            ,"fo_t.png"
            ,"im.png"
            ,"im_v.png"
            ,"fo_op.png"
            ,"fo.op_t.png"
            ,"fo_op_v.png"
		]);
        gridView.registerImageList(imgs);

	}

	var popupIndex = {};

	var autoIncrementList = new Array();


	var _getRowCnt = function() {
		return dataProvider.getRows();
	};

	/* 자동 증가 함수*/
	var setAutoIncrementData = function()
	{
		var list = _this.getJsonRows();

		var getNumber = function(list, key)
		{
			var intData = 0;

			for(var i = 0; i < list.length; i++)
			{
                intData = parseInt(list[i][key])

				if(!isNaN(intData))
				{
					break;
				}
			}

			if(isNaN(intData))
				intData = 0;

			return intData;

		}

		for(var i = 0; i < autoIncrementList.length; i++)
		{

			if(list.length < 1)
			{
                defaultData[autoIncrementList[i]["index"]] = 1;
			}
			else
			{
                list.sort(function(a,b){
                    return b[autoIncrementList[i]["index"]] - a[autoIncrementList[i]["index"]];
                });
                defaultData[autoIncrementList[i]["index"]] = getNumber(list,autoIncrementList[i]["index"])+1;
			}
		}
	}
	/*
	var _addRow = function(obj) {
		if(addRowBeforeEventCallback)
        	addRowBeforeEventCallback(_this,makeObj);
		var validate = false;

		setAutoIncrementData();
		if (undefined === obj && appendValidate()) {
            setAutoIncrementData();
			gridView.getDataProvider().addRow(defaultData);
			_doSave = true;

		} else if (typeof obj == "function") {
			if (obj()) {
                setAutoIncrementData();
				gridView.getDataProvider().addRow(defaultData);
                _doSave = true;
			}
		} else if (typeof obj == "object") {
			gridView.getDataProvider().addRow(obj);
			_doSave = true;
		}
		if(addRowAfterEventCallback)
			addRowAfterEventCallback(_this,makeObj);
	};
	*/
    var validateForMsg = function()
	{
        var list = _this.getJsonRows();
        var states = dataProvider.getAllStateRows();
        var data = undefined;
        var msg = "";
        var result = true;
        var skipList = states.createAndDeleted;
        var skip = false;
        for(var i = 0; i < list.length; i++)
        {
            data = list[i];

            for(var j = 0; j < skipList.length; j++)
            {
                if(skipList[j] == i)
                {
                    skip = true;
                    break;
                }
            }

            if(skip)
            {
                skip = false;
                continue;
            }

            //validation;
            //key = 위치값
            for(var key in requiredColumnList)
            {
                if(undefined == data[key] || "" == data[key])
                {
                    msg = "["+requiredColumnList[key]["title"]+"] "+axboot.getCommonMessage("AA008");

                    result = false;
                    break;
                }
            }
            if(!result)
                break;

            data = undefined;
        }
        return msg;
	}

    var validateData = function(){
        var list = _this.getJsonRows();
        var states = dataProvider.getAllStateRows();
        var data = undefined;
        var msg = "";
        var result = true;
        var skipList = states.createAndDeleted;
        var skip = false;
        for(var i = 0; i < list.length; i++)
        {
            data = list[i];

            for(var j = 0; j < skipList.length; j++)
			{
				if(skipList[j] == i)
				{
					skip = true;
					break;
				}
			}

            if(skip)
			{
				skip = false;
                continue;
            }

			//validation;
			//key = 위치값
            for(var key in requiredColumnList)
			{
				if(undefined == data[key] || "" == data[key])
				{
                    msg = "["+requiredColumnList[key]["title"]+"] "+axboot.getCommonMessage("AA008");

                    if(axToast)
					{
                        axboot.viewError({message : msg});

					}
                    else
                        alert(msg);

                    result = false;
                    break;
				}
			}
            if(!result)
                break;

            data = undefined;
        }
        return result;
    }


	// ==============================
	// 필수 값체크
    /*
    var checkData = function(gridView) {
        var result = true;
        var dataProvider = gridView.getDataProvider();
        var columnInfo = gridView.getColumns();
        var currentRow = gridView.getCurrent();
        var rows = dataProvider.getRows();
        var row = undefined;
        for (var i = 0; i < rows.length; i++) {
            row = rows[i];
            for (var j = 0; j < row.length; j++) {
                if (columnInfo[j].required && (row[j] === "" || row[j] === null)) {
                    result = false;
                    break;
                }
            }

            row = undefined;
        }
        return result;
    };
    // 상태보고 커밋
    var checkRow = function(gridView) {
        var dataProvider = gridView.getDataProvider();

        if (gridView.getDataProvider().getAllStateRows().created.length > 0) {
            // console.log(gridView.getDataProvider().getAllStateRows());
            try {
                gridView.commit(false);
            } catch (ex) {
                console.log(ex);
                alert(ex.message);
            }
        }

    };
    // keyDown event

    var keyDown = function(grid, key, ctrl, shift, alt) {
        var result = true;
        //return ;
        switch (key) {
            case 40:
                _addRow(function() {
                    checkRow(grid);
                    //console.log(_getRowCnt());
                    if (_getRowCnt() === 0) {
                        return true;
                    } else {
                        checkRow(grid);
                        if (checkData(grid) && grid.getDataProvider().getRowCount() == (grid.getCurrent().dataRow+1)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                result = true;
                break;
            case 9:

                var isNextRow = lastFocusColumnName == gridView.getCurrent().fieldName;
                var isAppending = false;
                _addRow(function() {
                    if (_getRowCnt() === 0 ||
                            lastFocusColumnName == gridView.getCurrent().fieldName) {
                        checkRow(grid);
                        if (checkData(grid))
                            return isAppending = true;
                        else
                            return false;
                    } else {
                        return false;
                    }
                });
                if (isAppending || isNextRow) {

                    var current = gridView.getCurrent();
                    current.dataRow++;
                    current.itemIndex = dataProvider.getFieldIndex(firstFocusColumnName);
                    current.fieldIndex = dataProvider.getFieldIndex(firstFocusColumnName);
                    current.column = firstFocusColumnName;
                    current.fieldName = firstFocusColumnName;
                    console.log(current);
                    console.log(firstFocusColumnName);
                    gridView.setCurrent(current);
                }
                break;
            default :
                break;
        }
        return result;
    };
    */
    // ==============================

	var setJSONData = function(mode,list)
	{
		gridView.getDataProvider().fillJsonData(list, {
			fillMode : mode
		});
	};

	this.onDataCellClicked = function(_event)
	{
        gridView.onDataCellClicked = _event;
	}
	this.itemClick = function(_event)
	{
        gridView.onDataCellClicked  = function(grid,index){
            if(-1 == index.dataRow || -1 == index.itemIndex)
                return ;

            _event(grid.getDataProvider().getJsonRow(index.dataRow),index);
		};
    };

    this.onEditChange = function(p_onEditChange)
    {
        _onEditChange =p_onEditChange;
        return true;
    }
    this.onDataCellDblClicked = function(_event)
    {
        dataCellDbClicked.push(_event);
    }

    this.getGridView = function() {
        return gridView;
    };

    this.addRow = function(obj) {
        return _addRow(obj);
    };
	//줄 추가 전에 호출되는 함수
    this.addRowBeforeEvent = function(_event)
    {
        addRowBeforeEventCallback = _event
    }
    //줄 추가 후에 호출되는 함수
    this.addRowAfterEvent = function(_event)
    {
        addRowAfterEventCallback = _event
    }
    //줄 지워지면 호출되는 함수
    this.removeRowEvent = function(_event)
    {
        removeRowEventCallback = _event
    }

	init();
};

/**
 * 그리드에 데이터 설정하는 함수
 * @param _list
 */
GridWrapper.prototype.setJSONData = function(_list)
{
    dataType = "JSON";
    list = _list;
};
/**
 * 데이터 설정하는 함수
 * @param mode
 * @param list
 * @returns {*}
 */
GridWrapper.prototype.setData = function(mode, list) {
    dataProvider.fillJsonData(list, {
        fillMode : mode
    });
    return this;
};
/**
 * 트리 펼치는 함수
 */
GridWrapper.prototype.expandAll = function()
{
    gridView.expandAll()
};
/**
 * 트리 모두 닫는 함수
 */
GridWrapper.prototype.collapseAll = function()
{
    gridView.collapseAll();
}
/**
 * 트리데이터 설정하는 함수
 * @param list
 * @param rowsProp
 * @param childrenProp
 * @param iconProp
 */
GridWrapper.prototype.setTreeData = function(list, rowsProp, childrenProp, iconProp)
{
    dataProvider.setJsonRows(list, rowsProp, childrenProp, iconProp);
}
/**
 * 트리데이터 JSON형태로 설정하는 함수
 * @param list
 * @param rowsProp
 * @param childrenProp
 * @param iconProp
 */
GridWrapper.prototype.setTreeDataForJSON = function(list, rowsProp, childrenProp, iconProp)
{
    dataProvider.setJsonRows(list, rowsProp, childrenProp, iconProp);
};
/**
 * 트리데이터 어레이로 설정하는 함수
 * @param list
 * @param _childrenProp
 */
GridWrapper.prototype.setTreeDataForArray = function(list, _childrenProp)
{
    childrenProp = _childrenProp;
    dataProvider.setRows(list, _childrenProp);
};
/**
 * 리스트 표현개수 설정하는 함수
 * @param _listCount
 */
GridWrapper.prototype.setListCount = function(_listCount)
{
    maxCount = _listCount;
};

/**
 * 그리드내 검색 후 선택되는 함수
 * @param fieldList
 * @param value
 */
GridWrapper.prototype.search = function(fieldList,value){
    if(isTree)
    {
        var ret = dataProvider.searchData({fields:fieldList, value:value,  startIndex:searchStartIndex+1,partialMatch:true ,wrap:true,select : true});
        if (ret) {
            var rowId = ret.dataRow;
            var parents = dataProvider.getAncestors(rowId);
            if (parents) {
                for (var i = parents.length - 1; i >= 0 ; i--) {
                    gridView.expand(gridView.getItemIndex(parents[i]));
                }
                searchStartIndex = rowId;
                gridView.setCurrent({itemIndex:gridView.getItemIndex(rowId), fieldIndex:ret.fieldIndex})
            }
        }
    }
    else {
        var dataRow = dataProvider.searchDataRow({startIndex: 0, fields: fieldList, value: value, startIndex:searchStartIndex+1,partialMatch:true,select : true});
        var ret = dataProvider.getRows(dataRow,dataRow)
        if(ret)
        {
            searchStartIndex = gridView.getItemIndex(dataRow);
            gridView.setCurrent({itemIndex:treeView.getItemIndex(dataRow), fieldIndex:ret.fieldIndex})
        }

    }
}

/**
 * 그리드 페이지 동적 로딩하는 함수
  * @param url
 * @param data
 * @param parsingCallback
 */
GridWrapper.prototype.load = function(url,data,parsingCallback)
{

    newStart = dataProvider.getRowCount();
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

                dataProvider.fillJsonData(list,
                    {fillMode: fillMode, start: newStart, count: maxCount});
            }

        }
    });
};

/**
 * 체크된 리스트 반환하는 함수
 * @returns {Array}
 */
GridWrapper.prototype.getCheckedList = function()
{
    var rowIndexList = gridView.getCheckedRows(false);
    var checkList = new Array();
    for(var i = 0; i < rowIndexList.length; i++)
    {
        checkList.push(dataProvider.getJsonRow(rowIndexList[i]));
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
    gridView.getDataProvider().removeRow(gridView.getCurrent().dataRow);
}
/**
 * 그리드 타입이 트리인지 설정하는 함수
 * @param _isTree
 * @returns {*}
 */
GridWrapper.prototype.setIsTree = function(_isTree)
{
    isTree = _isTree;
    return this;
};
/**
 * 현재위치 초기화 함수
 */
GridWrapper.prototype.resetCurrent = function()
{
    gridView.resetCurrent();
}
/**
 * 그리드뷰 커밋 함수
 */
GridWrapper.prototype.commit = function()
{
    dataProvider.clearRowStates(true, false);
}

/**
 * 그리드 생성한 오브젝트 설정하는 함수
 * @param _makeObj
 */
GridWrapper.prototype.setMakeObj = function(_makeObj)
{
    makeObj = _makeObj;
}
/**
 * 팝업 콜백 설정하는 함수
 * @param _popCallback
 */
GridWrapper.prototype.popupCallback = function(_popCallback)
{
    popCallback = _popCallback;
}
/**
 * 컨텍스트 콜백 설정하는 함수 ( 컨텍스트 메뉴 )
 * @param i_callback
 */
GridWrapper.prototype.setContextCallback = function(i_callback)
{
    callback = i_callback;
};
/**
 * 컨텍스트 콜백 가져오는 함수
 * @returns {*}
 */
GridWrapper.prototype.getContextCallback = function()
{
    return callback;
};
/**
 * 엔티티 ( 엑셀 명 ) 설정하는 함수
 * @param entityName
 * @returns {GridWrapper}
 */
GridWrapper.prototype.setEntityName = function(entityName)
{
    i_entity_name = entityName;
    return this;
};
/**
 * 그리드 스타일 설정하는 함수 ( 넓이, 높이 )
 * @param width
 * @param height
 * @returns {GridWrapper}
 */
GridWrapper.prototype.setGridStyle = function(width, height) {
    i_width = width;
    i_height = height;
    return this;
};
/**
 * 그리드 옵션 설정하는 함수
 * @param _gridOption
 */
GridWrapper.prototype.setOption = function(_gridOption) {
    gridOption = $.extend({}, _gridDefaultOption, _gridOption);
};

/**
 * 스타일 셋업하는 함수
 * @param style
 * @param value
 */
GridWrapper.prototype.setStyle = function(style,value)
{
    if(style instanceof Object)
        defaultStyle.header = $.extend({},defaultStyle,style);
    else
        defaultStyle[style] = value;
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
    return defaultStyle.parsing.header[styleName];
};
/**
 * 그리드 바디 스타일 가져오는 함수
 * @returns {body|{background, fontSize, fontFamily}}
 */
GridWrapper.prototype.getBodyStyle = function() {
    return defaultStyle.parsing.body;
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
    if(gridView)
        gridView.setContextMenu(contextMenu);

    return this;
};

/**
 * 그리드 생성
 */
GridWrapper.prototype.makeGrid = function() {
    $("#" + i_id).css({
        width : i_width,
        height : i_height
    });

    _initOption(gridView, dataProvider);
    bindEvent();

    gridView.setContextMenu(contextMenu);
    gridView.setDataSource(dataProvider);
    gridView.onDataCellClicked()

};

GridWrapper.prototype.setCustomCellStyleRows = function(type, conditionFunc, columnNames,doCommit)
{
    var styles = undefined;
    var editable  =false;
    if(type == "disable")
    {
        styles =  _defaultStyle.column.disable;
        editable  = false;
    }
    else if(type == "required")
    {
        styles =  _defaultStyle.column.required;
        editable  = true;
    }

    if(!styles)
        return ;
    if(undefined === doCommit || doCommit)
        gridView.commit();

    var rows = dataProvider.getJsonRows(0,-1);

    var columnIndexList = new Array();

    for(var i = 0; i < columnNames.length; i++)
    {
        columnIndexList.push(dataProvider.getFieldIndex(columnNames[i]));
    }


    var applyData = {rows : new Array(), columns : columnNames}
    gridView.addCellStyle("customStyle01", styles, true);
    for(var i = 0; i < rows.length; i++)
    {
        if(conditionFunc(rows[i]))
        {
            applyData.rows.push(i);

            for(var j = 0; j < columnIndexList.length; j++)
            {
                gridView.setCellStyle(i, columnIndexList[j], "customStyle01",true);
            }
        }else {
            for(var j = 0; j < columnIndexList.length; j++)
            {
                gridView.setCellStyle(i, columnIndexList[j], defaultStyles[columnIndexList[j]],true);
            }
        }
    }



    /*
    gridView.setCellStyleRows(
        applyData.rows,
        applyData.columns,
        "customStyle01"
    );
    */
    /*해당 그리드가 수정관련한 부분에 대해서 못하거나 할 수 있게 만들어주는 함수*/
    gridView.onCurrentRowChanged = function (grid, oldRow, newRow) {
        var curr = grid.getCurrent();
        var doSetting  = false;
        for(var i = 0; i < applyData.rows.length; i++)
        {
            if(applyData.rows[i] == curr.dataRow)
            {
                doSetting = true;
            }
        }
        //setting을 해야되는 경우 진행
        if(doSetting)
        {
            for(var i = 0; i < applyData.columns.length; i++)
            {
                grid.setColumnProperty(applyData.columns[i], "editable", editable);
            }
            var sel = {startItem: 1, endItem: 1, style: "block"};
            gridView.setSelection(sel);
            gridView.resetCurrent();
        }
        else
        {
            //setting에 영향을 안받을 경우에는 기본값으로 복원
            for(var column in defaultEditColumnProperties)
            {
                grid.setColumnProperty(column, "editable", defaultEditColumnProperties[column]);
                grid.setCellStyle(curr.dataRow, column, defaultStyles[column],true);
            }
        }
    }

}

/**
 * 검증 로직 세팅함수
 * @param validations
 */
GridWrapper.prototype.setValidations = function(validations) {
    var column = undefined;
    for (var i = 0; i < validations.length; i++) {
        column = gridView.columnByName(validations[i].fieldName);
        delete (validations[i].fieldName);
        column.validations = validations[i];
        gridView.setColumn(column);
    }
};
/**
 * 현재 데이터 검증
 * @returns {*}
 */
GridWrapper.prototype.validate = function()
{
    gridView.commit();
    return validateData();
}
//데이터  반환 함수
GridWrapper.prototype.getData = function() {
    gridView.commit(true);

    var dataProvider = gridView.getDataProvider();
    var rows = undefined;
    if(isTree)
        rows = dataProvider.getJsonRows(-1,true,childrenProp,"icon");
    else
        rows = dataProvider.getRows();

    var rowState = dataProvider.getAllStateRows();

    var createRow = rowState.created;
    var updateRow = rowState.updated;
    var deletedRow = rowState.deleted;

    var createdList = [];
    var updatedList = [];
    var deletedList = [];
    var retList = [];
    var row = undefined;

    var removeField = function(data)
    {
        delete(data["insertUUID"]);
        delete(data["insertUuid"]);
        delete(data["insertDate"]);
        delete(data["updateUUID"]);
        delete(data["updateUuid"]);
        delete(data["updateDate"]);
        console.log(data);
        return data;
    }

    for (var i = 0; i < createRow.length; i++) {
        retList.push($.extend(removeField(dataProvider.getJsonRow(createRow[i])),{"__created__" : true}));
    }
    for (var i = 0; i < updateRow.length; i++) {
        retList.push($.extend(removeField(dataProvider.getJsonRow(updateRow[i])),{"__modified__" : true}));
    }
    for (var i = 0; i < deletedRow.length; i++) {
        retList.push($.extend(removeField(dataProvider.getJsonRow(deletedRow[i])),{"__deleted__" : true}));
    }
    /*return {
        createdList : createdList,
        updatedList : updatedList,
        deletedList : deletedList
    };*/
    return retList;
};



/**
 * 줄 모두 없애는 함수
 */
GridWrapper.prototype.clearRows = function() {
    gridView.getDataProvider().clearRows();
};
/**
 * 열 고정하는 함수
 * @param p_fixedOptions
 */
GridWrapper.prototype.setFixedOptions = function(p_fixedOptions)
{
    gridView.setFixedOptions(p_fixedOptions);
};
/**
 * 인디케이터 설정함수
 * @param p_indicator
 */
GridWrapper.prototype.setIndicator = function(p_indicator)
{
    gridView.setIndicator(p_indicator);
}
/**
 * 상태 변경 데이터 있는지 확인하는 함수
 * @returns {boolean}
 */
GridWrapper.prototype.isDataChanged = function()
{
    return this.getData().length > 0;
}

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

    var styles = {};
    var obj = undefined;
    var fieldObj = undefined;
    for (var i = 0; i < list.length; i++) {
        data = list[i];

        // 필수여부 없으면 일반 모양에 수정 x
        // 필수여부 Y 노란색 수정 가능
        // 필수여부 N 일반 모양
        // 활성여부 Y N 회색
        fieldObj = {};
        obj = {
            name : data.name,
            fieldName : data.name,
            width : data.width,
            header : {
                text : data.text
            },
            styles : data.styles === undefined ? {} : data.styles,
            renderer : data.renderer === undefined ? { type : data.dataType } : $.extend({},data.renderer,{type : data.dataType}),
            editable : data.editable === undefined ? true : data.editable,
            //required : data.required === undefined ? false : data.required,
            visible : data.visible === undefined ? true : data.visible,
            readOnly : !(data.editable === undefined ? true : data.editable)
        };

        defaultEditColumnProperties[obj.name] = obj.editable;

        if(data.autoincrement)
            autoIncrementList.push({index: i, name
                : data.name});

        if (!firstFocusColumnName && obj.visible == true && obj.editable == true)
            firstFocusColumnName = data.name;
        // tab사용시 마지막 컬럼사용하기 때문에 세팅.
        if (data.editable)
        {
            defaultStyles[data.name] = "editable";
            lastFocusColumnName = data.name;
        }
        // 필수여부 (비활성 여부 빠져있음.)
        if (data.disable) {
            styles = defaultStyle.column.disable;
            defaultStyles[data.name] = "disable";
            obj.editable = false;
        } else if (data.required) {
            styles = defaultStyle.column.required;
            requiredColumnList[i] ={name : data.name, title : data.text};
            //obj.requiredMessage = "["+data.text+"] "+axboot.getCommonMessage("AA008");
            defaultStyles[data.name] = "required";
        } else {
            styles = defaultStyle._default;
            defaultStyles[data.name] = "_default";
        }

        obj.styles = $.extend({},styles, {
            textAlignment : data.textAlignment === undefined ? "near"
                : data.textAlignment,
            fontSize : data.fontSize === undefined ? 12 : data.fontSize,
            fontBold : data.fontBold === undefined ? false : data.fontBold
        },obj.styles);


        // 데이터 타입
        switch (data.dataType) {
            case "popup":
                obj.button = "image";
                obj.imageButtons = $.extend({},defaultStyle.data.imageButtons,data.imageButtons);
                defaultData.push("");
                popupIndex[i] = { popupCode : data.popupCode, sqlColumn : {}};
                for(var key in data.sqlColumn)
                {
                    popupIndex[i]["sqlColumn"][key] = data.sqlColumn[key];
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
                validateColumn[data.name] = function(value)
                {
                    var regexp = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/gi;
                    var result = regexp.test(value);
                    if(value)
                        value = value.replace(regexp,"");
                    return !result;
                }
                defaultData.push("");
                break;
            case "number":
                defaultData.push("");
                validateColumn[data.name] = function(value)
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
                defaultData.push("");
                break;
            case "date":
                obj.editor = dateEditorStyle;
                obj.styles = $.extend({}, defaultStyle.data.date, obj.styles );
                obj.displayRegExp = "([0-9]{4})([0-9]{4})([0-9]{4})([0-9]{4})";
                obj.displayReplace = "$1-$2-$3-$4";
                defaultData.push("");
                break;
            case "password":
                obj.type = "text";
                obj.renderer = {type : "text"};
                obj.displayRegExp = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z|0-9|\$|\/]/gi;
                obj.displayReplace = "*";
                defaultData.push("");
                break;
            case "timestamp":
                data.dataType = "datetime";
                obj.type = "datetime";
                obj.renderer = {type : "datetime"};
                obj.editor = defaultStyle.data.timestamp;
                obj.styles = $.extend({}, defaultStyle.data.timestamp, obj.styles);
                obj.displayRegExp = /\B(?=(\d{3})+(?!\d))/g;
                obj.displayReplace = ",";

                fieldObj.datetimeFormat = "iso"
                defaultData.push("");
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
                    , fontFamily : "nanum"
                    , fontBold : false
                }
                obj.renderer = {showTooltip : true};
                defaultData.push("");
                break;
            case "text":
                defaultData.push("");
                break;
            case "combo":
                obj.editor = $.extend({},defaultStyle.data.combo,data.editor);
                obj.values = data.values;
                obj.labels = data.labels;
                obj.sortable = data.sortable === undefined ? false
                    : data.sortable;
                if(!data.lookupDisplay)
                    data.lookupDisplay = true;

                obj.lookupDisplay = data.lookupDisplay;
                if(!data.values)
                    defaultData.push("");
                else
                    defaultData.push(data.values[0]);
                break;
            case "check":
                obj.renderer = $.extend({},defaultStyle.data.check,data.renderer);
                obj.editable = false;

                if(!data.defaultValue)
                    data.defaultValue = "Y";

                obj.defaultValue = data.defaultValue;
                fieldObj.defaultValue = data.defaultValue;
                defaultData.push(data.defaultValue);
                break;
            case "button":
                obj.button = "image";
                obj.imageButtons = $.extend({},defaultStyle.data.imageButtons,data.imageButtons);
                defaultData.push("");
                break;
            case "icon":
                obj.renderer = {type : "icon",textVisible : false}
                obj.dynamicStyles = data.dynamicStyles;
                fieldObj.dynamicStyles = data.dynamicStyles;
                obj.imageList = data.imageList;
                fieldObj.imageList = data.imageList;
                obj.values = data.values;
                obj.labels = data.labels;
                obj.lookupDisplay = true;
                obj.editable  = false;
                obj.editor = {type : "dropDown",dropDownCount:data.values.length,domainOnly : true,textReadOnly : false}
                obj.styles= {background:"#ffffffff",iconIndex: 0,iconLocation:"center",iconAlignment:"center",iconOffset:4,iconPadding:4}
                break;
            default:
                defaultData.push("");
                break;
        }
        columnList.push(obj);
        fieldList.push($.extend(fieldObj,{
            fieldName : data.name,
            dataType : data.dataType
        }));
        data = undefined;
        styles = {};
    }
    dataProvider.setFields(fieldList);
    gridView.setColumns(columnList);
    return this;
};

/**
 * 이름으로 컬럼 가져오는 함수
 * @param _columnName
 * @returns {*}
 */
GridWrapper.prototype.columnByName = function(_columnName)
{
    return gridView.columnByName(_columnName);
};
/**
 * 컬럼 설정하는 함수
 * @param _column
 */
GridWrapper.prototype.setColumn = function(_column)
{
    gridView.setColumn(_column);
};
/**
 * 총 갯수 반환하는 함수
 * @returns {*}
 */
GridWrapper.prototype.getRowCnt = function() {
    return _getRowCnt();
};

/**
 * 기본값 세팅하는 함수
 * @param _data
 */
GridWrapper.prototype.setDefaultData = function(_data)
{
    defaultData = _data;
}
/**
 * 기본값 가져오는 함수
 * @returns {*}
 */
GridWrapper.prototype.getDefaultData = function()
{
    return defaultData;
}


//셀렉트 된 행 데이터 가져오는 함수
GridWrapper.prototype.getSelectedData = function() {
    if (gridView.getCurrent().itemIndex == -1) {
        return undefined;
    } else {

        if(gridView.getCurrent().dataRow == -1)
            gridView.commit(true);

        if(isTree)
        {
            return gridView.getDataProvider().getJsonRow(
                gridView.getCurrent().dataRow);
        }
        else {
            return gridView.getDataProvider().getJsonRow(
                gridView.getCurrent().dataRow);
        }

    }
};
/**
 * 선택된 로우의 데이터를 반환하는 함수
 * @returns {*}
 */
GridWrapper.prototype.getSelectedData = function() {
	var index = gridView.getCurrent();
    if (index.itemIndex == -1) {
        return undefined;
    } else {

        if(index.dataRow == -1)
            gridView.commit(true);

        if(isTree)
        {
            return gridView.getDataProvider().getJsonRow(
                gridView.getCurrent().dataRow);
        }
        else {
            return gridView.getDataProvider().getJsonRow(
                gridView.getCurrent().dataRow);
        }

    }
};

/**
 * 현재의 위치를 반환해주는 함수
 * @returns {*|{uuid}}
 */
GridWrapper.prototype.getCurrent = function()
{
    return gridView.getCurrent();
}
/**
 * 필드의 위치를 반환하는 함수
 * @param fieldName
 * @returns {*}
 */
GridWrapper.prototype.getFieldIndex = function(fieldName)
{
    return dataProvider.getFieldIndex(fieldName);
}

/**
 * 특정 위치에 변수를 설정하는 함수
 * @param index
 * @param fieldIdx
 * @param data
 */
GridWrapper.prototype.setValue = function(rowIndex,fieldIdx, data)
{
    dataProvider.setValue(rowIndex,fieldIdx,data);
}
/**
 * 다수의 값을 변경하는 함수
 * @param itemIndex
 * @param values
 * @param strict
 */
GridWrapper.prototype.setValues = function(itemIndex,values, strict)
{
    dataProvider.setValues(itemIndex,values,strict);
}
/**
 * 그리드 포커스 주는 함수
 */
GridWrapper.prototype.setFocus = function()
{
    gridView.setFocus();
}
/**
 * DataProvider 돌려주는 함수
 */
GridWrapper.prototype.getDataProvider = function()
{
    return dataProvider;
};
/**
 * 그리드 전체 데이터를 JSON 형식의 데이터로 반환해주는 함수
 * @returns {undefined}
 */
GridWrapper.prototype.getJsonRows = function()
{
    var rows = undefined;
    if(isTree)
        rows = dataProvider.getJsonRows(-1,true,childrenProp,"icon");
    else
        rows = dataProvider.getRows();
    return rows;
};
/**
 * 그리드 화면 옵션 셋팅하는 함수
 * @param option
 */
GridWrapper.prototype.setDisplayOptions = function(option)
{
    gridView.setDisplayOptions(option);
};


