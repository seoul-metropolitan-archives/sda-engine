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



var GridWrapper = function(p_id,p_rootContext,_isTree) {
	var makeObj = undefined;
    var _this = this;
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
	
	var gridView;
	
	var dataProvider;
	//기초 데이터 ( 줄 추가시 사용됨. )
	var defaultData = [];

	//세이브 여부
	var _doSave = false;
	
	//첫번째 컬럼 이름
	var firstFocusColumnName = "";
	//마지막 컬럼 이름
	var lastFocusColumnName = "";
	//데이터 유형
	var dataType = "";
	
	var list = undefined;
	//이미지 관련 URL 
	var imageUrl = "/assets/images/ams/";

	var isTree = undefined == _isTree ? false: true;
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

    var requiredColumnList = new Array();
    var defaultEditColumnProperties = new Array();

	//그리드 옵션
	var gridOption = undefined;
	var rootContext = p_rootContext;
    var addRowBeforeEventCallback = function(){};
    var addRowAfterEventCallback = function(){};
    var removeRowEventCallback = function(){};
    this.addRowBeforeEvent = function(_event)
    {
        addRowBeforeEventCallback = _event
    }
    this.addRowAfterEvent = function(_event)
	{
        addRowAfterEventCallback = _event
	}
    this.removeRowEvent = function(_event)
    {
        removeRowEventCallback = _event
    }
    this.setMakeObj = function(_makeObj)
	{
        makeObj = _makeObj;
	}
	//기본 그리드 옵션
	var _gridDefaultOption = {
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
			appendable : false,
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
		/*
		sorting : {
			style : "inclusive",
			showSortOrder : true,
			sortOrderStyles : {
				foreground : "#88ececec",
				fontSize : 11,
				fontBold : true,
				textAlignment : "far",
				lineAlignment : "far"
			},
			toast : {
				visible : true,
				message : "정렬 중입니다..."
			}
		},
		*/
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
		hideDeletedRows : true
	};
	var groupingDefaultOptions = {
			enabled: true,
			prompt: "컬럼 헤더를 이 곳으로 끌어다 놓으면 그 컬럼으로 그룹핑합니다.",
			linear: false,
			expandWhenGrouping: false,
			toast: {
				visible: true
			}
		};
	var editorDefaultOptions = {
		useCssStyleDropDownList : true,
		useCssStyleDatePicker : true,
		useCssStylePopupMenu : true,
		useCssStyleMultiCheck : true,
		applyCellFont: true
	};
	var displayDefaultOption = {
			rowFocusVisible: false,
	        rowFocusBackground: "#32f2fbff"
	};
	//필터링 시 사용되는 옵션
	var filteringDefaultOptions = {
			selector: {
	            useCssStyle: true
	        }
	};
	
	var providerDefaultOption = {
		commitBeforeDataEdit : true, // 그리드가 편집중일때 grid.setValue,
									// dataProvider.setValue를 하는 경우 편집 중인 행을
									// commit시킨다.
		softDeleting : true
	// softDeleting이 true인 경우 실제로 삭제되지않고 rowState만 변경
	};
	// 날짜 기본 스타일
	var timestampEditorStyle = {
		type : "datetime",
		datetimeFormat : "yyyy-MM-dd HH:mm:ss.SSS",
		mask : {
				editMask:"9999-99-99 99:99:99"
				,placeHolder:"yyyy-MM-dd HH:mm:ss" //편집기에 표시될 형식
		        ,includedFormat:true //편집기에 표시된 내용이 그대로 셀값으로 전달
			},
		fontFamily : "nanum",
		fontSize : 12
	};
	var dateEditorStyle = {
			type : "date",
			datetimeFormat : "yyyy-MM-dd",
			mask : {
				editMask:"9999-99-99"
				,placeHolder:"yyyy-MM-dd" //편집기에 표시될 형식
			    ,includedFormat:true //편집기에 표시된 내용이 그대로 셀값으로 전달
			},
			fontFamily : "nanum",
			fontSize : 12
		};
	var defaultStyle = {};
	var _defaultStyle = {
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
				type : "date",
				datetimeFormat : "yyyy-MM-dd",
				fontFamily : "nanum",
				fontSize : 12
			},
			timestamp : timestampEditorStyle,
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
	this.onRowsPasted = function(_rowsPasted)
	{
        gridView.onRowsPasted = _rowsPasted;
	}

	this.onKeydown = function(_keydown)
	{
        gridView.onKeyDown = _keydown;
	}
	
	var popCallback = undefined;
	var popupCallback = function(_popCallback)
	{
		popCallback = _popCallback;
	}
	var getPopupData = function(position)
	{
		return popupIndex[position];
	}
	var validateColumn = {};
	var showPopup = function(grid, currentField, searchData, rows, popupData,preSearch)
	{
		var retData = undefined;
        axboot.modal.open({
            modalType: "COMMON_POPUP",
            preSearch : preSearch,
            sendData: function () {
                return {
                    popupCode : popupData["popupCode"],
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
                doRequiredValidation = true;
                if(this.close)
                    this.close();
            }
        });
        if(popCallback)
            popCallback(retData);
	}
    var doRequiredValidation = true;
	var bindEvent = function() {
		gridView.onKeyDown = keyDown;

        gridView.onValidateColumn = function(grid, column, inserting, value) {
            var error = {};
            for(var i = 0;i < requiredColumnList.length; i++)
            {
                if (column.fieldName === requiredColumnList[i] && doRequiredValidation) {
                    if (value == undefined ||value == "") {
                        error.level = RealGridJS.ValidationLevel.ERROR;
                        error.message = axboot.getCommonMessage("AA008");
                    }

                };
            }
            return error;
        }

        gridView.onValidationFail = function (grid, itemIndex, column, err) {
           axToast.push(axboot.getCommonMessage("AA008"));
        }
        gridView.onEditChange = function(grid, index, value){
            //index.fieldIndex
			/*
            if(validateColumn[index.fieldName] && !validateColumn[index.fieldName](value))
            		grid.cancel();
            */
		}
		gridView.onEditCommit = function (grid, index, oldValue, newValue)
		{
		    doRequiredValidation = false;
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

        gridView.onImageButtonClicked = function(grid, itemIndex, column, buttonIdex, name)
		{

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
		}

		gridView.onCurrentChanged = function(grid,newIndex)
		{

		}

		var _callback = callback;

		//사용자가 Insert 키를 눌러 새로운 행을 삽입하거나, 마지막 행에서 아래 화살표를 눌러 행을 추가하려고 할 때 호출된다. 이 콜백에서 행 추가 불가 메시지를 리턴하면 행 추가가 금지된다.
        gridView.onRowInserting = function (grid, itemIndex) {
            addRowBeforeEventCallback(_this,makeObj);
            addRowAfterEventCallback(_this,makeObj);
        };

		gridView.onContextMenuItemClicked = function (grid, label, index) {
			switch(label.label)
			{
				case "ExcelExport":
					exportExcel(grid,label);
					default :
						if(_callback)
                            _callback(label.label,grid,index);
						break;
			}
		  };
		  gridView.onDataCellClicked = function(grid, index)
		  {

		  };
		  gridView.onCellEdited  = function(grid, itemIndex, dataRow, field)
		  {
		  }


		 $("#"+i_id).parents().eq(1).delegate(".btn_a","click",function(){
             _addRow(function() {
                 checkRow(gridView);
                 console.log(_getRowCnt());
                 if (_getRowCnt() === 0) {
                     return true;
                 } else {
                     checkRow(gridView);
                     if (checkData(gridView)) {
                         return true;
                     } else {
                         return false;
                     }
                 }
             });
         });
		 $("#"+i_id).parents().eq(1).delegate(".btn_d","click",function(){
		 	gridView.getDataProvider().removeRows(gridView.getSelectedRows(),false);
             removeRowEventCallback();
		 });
	};
	this.removeRow = function()
	{
        gridView.getDataProvider().removeRow(gridView.getCurrent().dataRow);
	}
	this.setIsTree = function(_isTree)
	{
        isTree = _isTree;
        return this;
	};

	this.setContextCallback = function(i_callback)
	{
		callback = i_callback;
	};
	this.getContextCallback = function()
	{
		return callback;
	};
	this.setEntityName = function(entityName)
	{
		i_entity_name = entityName;
		return this;
	};
	this.setGridStyle = function(width, height) {
		i_width = width;
		i_height = height;
		return this;
	};
	this.setOption = function(_gridOption) {
		gridOption = $.extend({}, _gridDefaultOption, _gridOption);
	};
	
	this.setStyle = function(name,style)
	{
		if(name instanceof String && style instanceof Object)
		{
			
		}
	};
	this.setStyle = function(style)
	{
		if(style instanceof Object)
			defaultStyle.header = $.extend({},defaultStyle,style);
		else
			throw new Exception("Header Style은 Object로 정의해야됩니다.");
	};

	this.addRemoveProperty = function(property)
	{
        removeProperty.push(property);
	}
	this.deleteRemoveProperty = function(index)
	{
		removeProperty.splice(index,1);
	}
    this.clearRemoveProperty = function(property)
    {
        removeProperty = new Array();
    }

	this.getHeaderStyle = function() {
		return defaultStyle.parsing.header;
	};
	this.getHeaderStyle = function(styleName) {
		return defaultStyle.parsing.header[styleName];
	};
	this.getBodyStyle = function() {
		return defaultStyle.parsing.body;
	};
	this.getHeaderStyle = function(styleName) {
		return defaultStyle.parsing.body[styleName];
	};
	this.addContextMenu = function(menu)
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
            gridView.setStyles({
				header : {
                    background: "linear,#f2f2f2",
                    fontSize: 12,
                    fontFamily: "nanum",
                    foreground: "#000000",
                    borderRight: "#cccccc,1",
                    fontBold: false,
                },
				body : {
					borderRight : "#ff000000,0px",
                    borderBottom : "#f0000000,0px",
					line : "#ffaaaaaa,0px",
					fontSize : 12,
					fontFamily : "nanum",
					grid : {
						border : "#ffffffff,0"
					}

				}
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

	};
	this.setDisplayOptions = function(option)
	{
        gridView.setDisplayOptions(option);
	}
	var popupIndex = {};
	/**
	 * 그리드 생성
	 */
	this.makeGrid = function() {
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
	/**
	 * 컬럼 데이터 설정
	 * 
	 * @param Array
	 * @description { "sortNo" : number ,"name" : text ,"text" : text
	 *              ,"required" : boolean ,"editable" : boolean ,"dataType" :
	 *              text ,"width" : number ,"fontSize" : number ,"fontBold" :
	 *              boolean }
	 */
	this.setColumnInfo = function(list) {
        firstFocusColumnName = undefined;
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
				required : data.required === undefined ? false : data.required,
				visible : data.visible === undefined ? true : data.visible,
				readOnly : !(data.editable === undefined ? true : data.editable)
			};

            defaultEditColumnProperties[obj.name] = obj.editable;

			if (!firstFocusColumnName && obj.visible == true && obj.editable == true)
				firstFocusColumnName = data.name;
			// tab사용시 마지막 컬럼사용하기 때문에 세팅.
			if (data.editable)
				lastFocusColumnName = data.name;
			// 필수여부 (비활성 여부 빠져있음.)
			if (data.disable) {
				styles = defaultStyle.column.disable;
                obj.editable = false;
			} else if (data.required) {
				styles = defaultStyle.column.required;
				requiredColumnList.push(data.name);
				obj.requiredMessage = "["+data.text+"] "+axboot.getCommonMessage("AA008");
			} else {
				styles = defaultStyle._default;
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
	this.getGridView = function() {
		return gridView;
	};
	this.getRowCnt = function() {
		return _getRowCnt();
	};
	var _getRowCnt = function() {
		var dataProvider = gridView.getDataProvider();

		return dataProvider.getRows();
	};

	this.setDefaultData = function(_data)
	{
		return defaultData = _data;
	}
	this.getDefaultData = function()
	{
		return defaultData;
	}
	this.addRow = function(obj) {
		return _addRow(obj);
	};
	var _addRow = function(obj) {
		if(addRowBeforeEventCallback)
        	addRowBeforeEventCallback(_this,makeObj);
		var validate = false;
		if (undefined === obj && appendValidate()) {
			gridView.getDataProvider().addRow(defaultData);
			_doSave = true;
		} else if (typeof obj == "function") {
			if (obj()) {
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

	//셀렉트 된 행 데이터 가져오는 함수
	this.getSelectedData = function() {
		if (gridView.getCurrent().itemIndex == -1) {
			return undefined;
		} else {
			return gridView.getDataProvider().getJsonRow(
					gridView.getCurrent().itemIndex);
		}
	};
	this.getCurrent = function()
	{
		return gridView.getCurrent();
	}
	this.getFieldIndex = function(fieldName)
	{
		return dataProvider.getFieldIndex(fieldName);
	}
	this.setValue = function(index,fieldIdx, data)
	{
        dataProvider.setValue(index,fieldIdx,data);
	}
    this.setValues = function(itemIndex,values, strict)
    {
        dataProvider.setValues(itemIndex,values,strict);
    }
    this.setFocus = function()
	{
		gridView.setFocus();
	}
	this.getDataProvider = function()
	{
		return dataProvider;
	}
	this.getJsonRows = function()
	{
		var rows = undefined;
        if(isTree)
            rows = dataProvider.getJsonRows(-1,true,childrenProp,"icon");
        else
            rows = dataProvider.getRows();
        return rows;
	}
    //데이터  반환 함수
	this.getData = function() {
        gridView.commit();
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
	this.clearRows = function() {
		gridView.getDataProvider().clearRows();
	};
	this.getFirstColumnName = function() {
		return firstFocusColumnName;
	};
	this.getLastColumnName = function() {
		return lastFocusColumnName;
	};
	this.setDoSave = function(doSave) {
		_doSave = doSave;
	};
	this.getDoSave = function() {
		return _doSave;
	};
	
	this.setFixedOptions = function(p_fixedOptions)
	{
		gridView.setFixedOptions(p_fixedOptions);
	};
	this.setIndicator = function(p_indicator)
	{
		gridView.setIndicator(p_indicator);
	}
	
	// ==============================
	// 필수 값체크
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
    // ==============================
    this.setCustomCellStyleRows = function(type, conditionFunc, columnNames)
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
			}
			else
			{
                //setting에 영향을 안받을 경우에는 기본값으로 복원
				for(var column in defaultEditColumnProperties)
				{
                    grid.setColumnProperty(column, "editable", defaultEditColumnProperties[column]);
				}
			}
        }
        var sel = {startItem: 1, endItem: 1, style: "block"};
        gridView.setSelection(sel);
        gridView.resetCurrent();

    }
    this.setValidations = function(validations) {
		var column = undefined;
		for (var i = 0; i < validations.length; i++) {
			column = gridView.columnByName(validations[i].fieldName);
			delete (validations[i].fieldName);
			column.validations = validations[i];
			gridView.setColumn(column);
		}
	};
	var setJSONData = function(mode,list)
	{
		gridView.getDataProvider().fillJsonData(list, {
			fillMode : mode
		});
	};
	this.setJSONData = function(_list)
	{
		dataType = "JSON";
		list = _list;
	};
	this.setData = function(mode, list) {
		dataProvider.fillJsonData(list, {
			fillMode : mode
		});
		return this;
	};
	this.expandAll = function()
	{
        gridView.expandAll()
	};
    this.setTreeData = function(list, rowsProp, childrenProp, iconProp)
	{
        dataProvider.setJsonRows(list, rowsProp, childrenProp, iconProp);
	}
	this.setTreeDataForJSON = function(list, rowsProp, childrenProp, iconProp)
	{
        dataProvider.setJsonRows(list, rowsProp, childrenProp, iconProp);
	};
    this.setTreeDataForArray = function(list, _childrenProp)
    {
        childrenProp = _childrenProp;
        dataProvider.setRows(list, _childrenProp);
    };
	this.setListCount = function(_listCount)
	{
		maxCount = _listCount;
	};
	this.load = function(url,data,parsingCallback)
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
	this.onDataCellClicked = function(_event)
	{
        gridView.onDataCellClicked = _event;
	}
	this.itemClick  = function(_event)
	{
        gridView.onDataCellClicked  = function(grid,index){
            if(-1 == index.dataRow || -1 == index.itemIndex)
                return ;

            _event(grid.getDataProvider().getJsonRow(index.dataRow),index);
		};
        /*
        gridView.onCurrentRowChanged =  function (grid, oldRow, newRow) {
        	console.log(grid.getCurrent());

        	if(-1 == grid.getCurrent().dataRow || -1 == grid.getCurrent().itemIndex)
        		return ;

	         _event(grid.getDataProvider().getJsonRow(grid.getCurrent().dataRow),grid.getCurrent());
        }
        */
    };
	this.getCheckedList = function()
	{
        var rowIndexList = gridView.getCheckedRows(false);
        var checkList = new Array();
        for(var i = 0; i < rowIndexList.length; i++)
		{
            checkList.push(dataProvider.getJsonRow(rowIndexList[i]));
		}

        return checkList;
	}
	this.setAppendValiate = function(func) {
		if (typeof func == "function") {
			appendValidate = func;
		} else {
			throw "함수만 설정 가능합니다.";
		}
	};
	this.commit = function()
	{
        dataProvider.clearRowStates(true, false);
    }
	init();

};
/*
GridWrapper.prototype =
{
};
*/


