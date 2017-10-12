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
	//Grid id
	var i_id = p_id;
	//Excel Export시 사용되는 Entity 이름
	var i_entity_name = "";
	//Grid 넓이
	var i_width = "";
	//Grid 높이
	var i_height = "";
	//컬럼 리스트
	var i_columnList = new Array();
	//필드 리스트
	var i_fieldList = new Array();
	
	var callback = {
			context : undefined
	}
	
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
	var imageUrl = "/images/"

	var isTree = undefined == _isTree ? false: true;

	var appendValidate = function() {
		return true;
	};
	
	var contextMenu = [{label : "ExcelExport"}]

	//================================================
	//lazy load시 사용 파라메터
	var maxCount = 100;
	//================================================
	//그리드 옵션
	var gridOption = undefined;
	var rootContext = p_rootContext;
	//기본 그리드 옵션
	var _gridDefaultOption = {
		indicator : {
			visible : false
		},
		checkBar : {
			visible : false
		},
		stateBar : {
			visible : true
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
			commitWhenExitLast : false,
			crossWhenExitLast : false,
			enterToTab : true
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
		}
	var editorDefaultOption = {
		useCssStyleDropDownList : true,
		useCssStyleDatePicker : true,
		useCssStylePopupMenu : true,
		useCssStyleMultiCheck : true,
		skipReadOnly : true,
		applyCellFont: true
	};
	var displayDefaultOption = {
			rowFocusVisible: false,
	        rowFocusBackground: "#32f2fbff"
	}
	//필터링 시 사용되는 옵션
	var filteringDefaultOptions = {
			selector: {
	            useCssStyle: true
	        }
	}
	
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
		datetimeFormat : "yyyy-MM-dd hh:mm:ss",
		mask : {
				editMask:"9999-99-99 99:99:99"
				,placeHolder:"yyyy-MM-dd hh:mm:ss" //편집기에 표시될 형식
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
				trueValues : "true",
				falseValues : "false",
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
	}

	var bindEvent = function() {
		gridView.onKeyDown = keyDown;
		var _callback = callback;
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
		  }
		  gridView.onCellEdited  = function(grid, itemIndex, dataRow, field)
		  {
		  }
	};

	this.setIsTree = function(_isTree)
	{
        isTree = _isTree;
        return this;
	}

	this.setContextCallback = function(i_callback)
	{
		callback = i_callback;
	}
	this.getContextCallback = function()
	{
		return callback;
	}
	this.setEntityName = function(entityName)
	{
		i_entity_name = entityName;
		return this;
	}
	this.setGridStyle = function(width, height) {
		i_width = width;
		i_height = height;
		return this;
	};
	this.setOption = function() {
		gridOption = $.extend({}, _gridDefaultOption, gridOption);
	};
	
	this.setStyle = function(name,style)
	{
		if(name instanceof String && style instanceof Object)
		{
			
		}
	}
	this.setStyle = function(style)
	{
		if(style instanceof Object)
			defaultStyle.header = $.extend({},defaultStyle,style);
		else
			throw new Exception("Header Style은 Object로 정의해야됩니다.");
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
		var menus = new Array();
		menus.push({"label" : menu});
		for(var i = 0; i < contextMenu.length; i++)
		{
			menus.push(contextMenu[i]);	
		}
		contextMenu = menus;
		if(gridView)
			gridView.setContextMenu(contextMenu);
		
		return this;
	}
	

	var _initOption = function(gridView, provider) {
		var option = gridOption === undefined ? _gridDefaultOption : gridOption;
		if(isTree)
		{
            gridView.setTreeOptions({
                "summaryMode": "aggregate",
                "stateBar": {
                    "visible": false
                }
            });
            //visible: 인디케이터 영역의 화면 표시여부를 지정합니다.
            gridView.setIndicator({
                visible: false
            });

            gridView.setStateBar({
                visible: false
            });

            gridView.setCheckBar({
                visible: false
            });
		}
		else
		{
            gridView.setOptions(option);
            gridView.setGroupingOptions(groupingDefaultOptions);
            gridView.setEditOptions(editorDefaultOption);
            gridView.setStyles(defaultStyle.parsing);
            gridView.setFilteringOptions(filteringDefaultOptions);
            gridView.setDisplayOptions(displayDefaultOption);
            dataProvider.setOptions(providerDefaultOption);
            // gridView.setStyles(defaultStyle.event.selection);
		}

	};

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
		var columnList = new Array();
		var fieldList = new Array();
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
		for (var i = 0; i < list.length; i++) {
			data = list[i];

			// 필수여부 없으면 일반 모양에 수정 x
			// 필수여부 Y 노란색 수정 가능
			// 필수여부 N 일반 모양
			// 활성여부 Y N 회색
			var obj = {
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
			if (i === 0)
				firstFocusColumnName = data.name;
			// tab사용시 마지막 컬럼사용하기 때문에 세팅.
			if (data.editable)
				lastFocusColumnName = data.name;

			// 필수여부 (비활성 여부 빠져있음.)
			if (data.disable) {
				styles = defaultStyle.column.disable;
			} else if (data.required) {
				styles = defaultStyle.column.required;
				obj.requiredMessage = obj.header.text + "는 반드시 입력해야 됩니다.";
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
				case "code":
					//생각좀 해봐야겠음.
					obj.type = "text";
					obj.editor = {
						type: "multiline",
						textCase: "upper"
					}
                    defaultData.push("");
					break;
				case "number":
					obj.displayRegExp = /\B(?=(\d{3})+(?!\d))/g;
					obj.displayReplace = ",";
					obj.style = {textAlignment: "far"}
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
					obj.displayRegExp = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z|0-9]/gi;
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
					defaultData.push("");
					break;
				case "text":
					defaultData.push("");
					break;
				case "combo":
					obj.editor = $.extend({},defaultStyle.data.combo,data.editor)
					obj.values = data.values;
					obj.labels = data.labels;
					obj.sortable = data.sortable === undefined ? false
							: data.sortable;
					obj.lookupDisplay = data.lookupDisplay === undefined ? true
							: data.lookupDisplay;
					defaultData.push(data.values[0]);
					break;
				case "check":
					obj.renderer = defaultStyle.data.check;
					obj.editable = false;
                    defaultData.push("");
					break;
				case "button":
					obj.button = "image";
					obj.imageButtons = $.extend({},defaultStyle.data.imageButtons,data.imageButtons)
                    defaultData.push("");
					break;
				default:
					defaultData.push("");
					break;
			}
			columnList.push(obj);
			fieldList.push({
				fieldName : data.name,
				dataType : data.dataType
			});
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
	this.addRow = function(obj) {
		return _addRow(obj);
	};
	var _addRow = function(obj) {
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
	
	
	//데이터  반환 함수
	this.getData = function() {
		var dataProvider = gridView.getDataProvider();
		var rows = dataProvider.getRows();
		var rowState = dataProvider.getAllStateRows();

		var createRow = rowState.created;
		var updateRow = rowState.updated;
		var deletedRow = rowState.deleted;

		var createdList = new Array();
		var updatedList = new Array();
		var deletedList = new Array();

		var row = undefined;
		for (var i = 0; i < createRow.length; i++) {
			row = rows[createRow[i]];
			try {
				createdList.push(gridView.getDataProvider().getJsonRow(row));
			} catch (ex) {
				console.log(ex);
			}
		}
		for (var i = 0; i < updateRow.length; i++) {
			row = rows[updateRow[i]];
			updatedList.push(gridView.getDataProvider().getJsonRow(row));
		}
		for (var i = 0; i < deletedRow.length; i++) {
			row = rows[deletedRow[i]];
			deletedList.push(gridView.getDataProvider().getJsonRow(row));
		}
		return {
			createdList : createdList,
			updatedList : updatedList,
			deletedList : deletedList
		};
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
	// ==============================
	// keyDown event
	var keyDown = function(grid, key, ctrl, shift, alt) {
		var result = true;
		switch (key) {
			case 40:
				_addRow(function() {
					checkRow(grid);
					console.log(_getRowCnt());
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
				if (isAppending) {
	
					var current = gridView.getCurrent();
					current.dataRow++;
					current.itemIndex++;
					current.fieldIndex = 1;
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
	}
	this.setJSONData = function(_list)
	{
		dataType = "JSON";
		list = _list;
	}
	this.setData = function(mode, list) {
		dataProvider.fillJsonData(list, {
			fillMode : mode
		});
		return this;
	};
	this.expandAll = function()
	{
        gridView.expandAll()
	}
	this.setTreeData = function(list, rowsProp, childrenProp, iconProp)
	{
        dataProvider.setJsonRows(list, rowsProp, childrenProp, iconProp);
	}
	this.setListCount = function(_listCount)
	{
		maxCount = _listCount;
	}
	this.load = function(url,data,parsingCallback)
	{

		newStart = dataProvider.getRowCount();
		$.ajax({
			url : url
			, type : "POST"
			, data : JSON.stringify(data)
			, contentType : "application/json"
			, dataType : "JSON"
			, success: function(response) {
				if(response.header.result)
				{
					var fillMode = "set";
					if(0 != newStart)
					{
                        fillMode = "insert";
					}

					if(undefined === response.body.list || response.body.list.length < 1)
					{
                        _addRow();
					}
					else {
						if(parsingCallback)
                            response.body.list = parsingCallback(response.body.list);

                        dataProvider.fillJsonData(response.body.list,
                            {fillMode: fillMode, start: newStart, count: maxCount});
                    }

                }
			}
		});
	}
	this.onDataCellClicked  = function(_event)
	{
        gridView.onDataCellClicked  = _event;
    }
	this.setAppendValiate = function(func) {
		if (typeof func == "function") {
			appendValidate = func;
		} else {
			throw "함수만 설정 가능합니다.";
		}

	};

	init();

}

var gridEvent = 
{
};


