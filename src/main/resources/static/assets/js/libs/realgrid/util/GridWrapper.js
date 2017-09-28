/**
 * real grid
 */
var GridWrapper = function() {
	var i_id = "";
	var i_width = "";
	var i_height = "";
	var i_columnList = new Array();
	var i_fieldList = new Array();
	var gridView;
	var dataProvider;
	var defaultData = [];

	var _doSave = false;
	var firstFocusColumnName = "";
	var lastFocusColumnName = "";

	var appendValidate = function() {
		return true;
	};

	var gridOption = undefined;
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
			enabled : false
		},
		panel : {
			visible : false
		},
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
		select : {
			style : RealGridJS.SelectionStyle.ROWS
		},
		hideDeletedRows : true
	};
	var editorDefaultOption = {
		useCssStyleDropDownList : true,
		useCssStyleDatePicker : true,
		useCssStylePopupMenu : true,
		useCssStyleMultiCheck : true,
		skipReadOnly : true
	};
	var providerDefaultOption = {
		commitBeforeDataEdit : true, // 그리드가 편집중일때 grid.setValue,
									// dataProvider.setValue를 하는 경우 편집 중인 행을
									// commit시킨다.
		softDeleting : true
	// softDeleting이 true인 경우 실제로 삭제되지않고 rowState만 변경
	};
	// 날짜 기본 스타일
	var dateEditorStyle = {
		type : "date",
		datetimeFormat : "yyyy-mm-dd hh:mm:ss",
		fontFamily : "나눔고딕",
		fontSize : 12
	};
	var defaultStyle = {};
	var _defaultStyle = {
		_default : {
			background : "#ffffffff",
			fontSize : 12,
			fontFamily : "나눔고딕",
			fontBold : false
		},
		column : {
			editable : {
				fontFamily : "나눔고딕",
				fontSize : 10
			},
			disable : {
				background : "#fff2f2f2",
				fontSize : 12,
				fontFamily : "나눔고딕",
				fontBold : false
			},
			required : {
				"textAlignment" : "near",
				"background" : "#fffffdd6",
				"fontSize" : 12,
				"fontFamily" : "나눔고딕",
				"fontBold" : false
			}
		},
		// dataType에 사용되는 스타일
		data : {
			date : {
				type : "date",
				datetimeFormat : "yyyy-mm-dd hh:mm:ss",
				fontFamily : "나눔고딕",
				fontSize : 12
			},
			combo : {
				type : "dropDown",
				fontFamily : "나눔고딕",
				domainOnly : true,
				textReadOnly : true,
				fontSize : 10
			},
			check : {
				type : "check",
				editable : true,
				startEditOnClick : true,
				trueValues : "true",
				falseValues : "false",
				labelPosition : "center"
			}
		},
		parsing : // 데이터 파싱시 사용되는 스타일
		{
			header : {
				background : "linear,#f2f2f2",
				fontSize : 12,
				fontFamily : "나눔고딕",
				foreground : "#000000",
				borderRight : "#cccccc, 1",
				fontBold : false
			},
			body : {
				background : "#ffffffff",
				fontSize : 12,
				fontFamily : "나눔고딕"
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
	};

	var bindEvent = function() {
		gridView.onKeyDown = keyDown;
	};

	this.setGridId = function(id, width, height) {
		i_id = id;
		i_width = width;
		i_height = height;
		return this;
	};
	this.setOption = function() {
		gridOption = $.extend({}, _gridDefaultOption, gridOption);
	};
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

	var _initOption = function(gridView, provider) {
		var option = gridOption === undefined ? _gridDefaultOption : gridOption;
		gridView.setOptions(option);
		gridView.setEditOptions(editorDefaultOption);
		dataProvider.setOptions(providerDefaultOption);
		gridView.setStyles(defaultStyle.parsing.header);
		// gridView.setStyles(defaultStyle.event.selection);
	};

	/**
	 * 그리드 생성
	 */
	this.makeGrid = function() {
		$("#" + i_id).css({
			width : i_width,
			height : i_height
		});
		RealGridJS.setTrace(false);
		RealGridJS.setRootContext("/assets/js");
		gridView = new RealGridJS.GridView(i_id);
		dataProvider = new RealGridJS.LocalDataProvider();
		_initOption(gridView, dataProvider);
		bindEvent();
		dataProvider.setFields(i_fieldList);
		gridView.setDataSource(dataProvider);
		gridView.setColumns(i_columnList);
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
				styles : {},
				renderer : {
					type : data.dataType
				},
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

			obj.styles = $.extend({}, styles, {
				textAlignment : data.textAlignment === undefined ? "near"
						: data.textAlignment,
				fontSize : data.fontSize === undefined ? 10 : data.fontSize,
				fontBold : data.fontBold === undefined ? false : data.fontBold
			});
			// 데이터 타입
			switch (data.dataType) {
			case "date":
				obj.editor = defaultStyle.data.date;
				obj.styles = $.extend({}, obj.styles, defaultStyle.data.date);
				defaultData.push("");
				break;
			case "text":
				defaultData.push("");
				break;
			case "combo":
				obj.editor = $.extend({
					dropDownCount : data.list.length
				}, defaultStyle.data.combo)
				obj.values = data.list;
				obj.labels = data.list;
				obj.sortable = data.sortable === undefined ? false
						: data.sortable;
				obj.lookupDisplay = data.lookupDisplay === undefined ? true
						: data.lookupDisplay;
				defaultData.push(data.list[0]);
				break;
			case "check":
				obj.renderer = defaultStyle.data.check;
				obj.editable = false;
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
		i_fieldList = fieldList;
		i_columnList = columnList;
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
	this.getSelectedData = function() {
		if (gridView.getCurrent().itemIndex == -1) {
			return undefined;
		} else {
			return gridView.getDataProvider().getJsonRow(
					gridView.getCurrent().itemIndex);
		}
	};
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
					if (checkData(grid)) {
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
	this.setData = function(mode, list) {
		gridView.getDataProvider().fillJsonData(list, {
			fillMode : mode
		});
	};
	this.setAppendValiate = function(func) {
		if (typeof func == "function") {
			appendValidate = func;
		} else {
			throw "함수만 설정 가능합니다.";
		}

	};

	init();

}