var SimpleGridWrapper = function (p_id,p_rootContext) {
    var _this = GridWrapper.call(this,p_id,p_rootContext);
    var _gridDefaultOption = this.option;
    var init = function () {
        _this.defaultStyle = $.extend({}, _this.defaultStyle, _this.style,this.style);
        RealGridJS.setTrace(false);
        RealGridJS.setRootContext(_this.rootContext);
        _this.gridView = new RealGridJS.GridView(_this.i_id);
        _this.dataProvider = new RealGridJS.LocalDataProvider();
    };

    this._initOption = function (gridView, provider) {
        var option = undefined;
        if (this.gridOption)
            option = this.gridOption;
        else
            option = this.option;

        gridView.setOptions(option.gridOption);
        //gridView.setGroupingOptions(option.grouping);
        //gridView.setEditOptions(editorDefaultOptions);
        gridView.setStyles(this.style.parsing);
        gridView.setFilteringOptions(option.filtering);
        gridView.setDisplayOptions(option.display);
        gridView.setCopyOptions(option.copy);
        gridView.setPasteOptions(option.paste);
        gridView.setSortingOptions({
            style: "inclusive"
        });
        gridView.setEditorOptions({
            applyCellFont: true
        });
        gridView.setCheckBar({
            width: 50
        });
        gridView.setIndicator({
            visible: true,
            styles: {background: "linear,#f2f2f2", fontFamily: "nanum"}
        })
        gridView.setStateBar(option.stateBar === undefined ? {"visible": false} : option.stateBar);
        gridView.setCheckBar(option.checkBar === undefined ? {"visible": false} : option.checkBar);
        this.dataProvider.setOptions(option.dataProvider);
        // gridView.setStyles(defaultStyle.event.selection);
        _this.registerStyle();
    };

    init();
}
SimpleGridWrapper.prototype = new GridWrapper();
SimpleGridWrapper.prototype.constructor = SimpleGridWrapper;


SimpleGridWrapper.prototype.defaultBind = function()
{
    this.bind("onRowInserting",function (grid, itemIndex) {

        var msg = validateForMsg();
        if("" == msg)
        {
            if(addRowBeforeEventCallback)
                addRowBeforeEventCallback(_this,makeObj);

            if(addRowAfterEventCallback)
                addRowAfterEventCallback(_this,makeObj);
        }
        else {
            return false
        }

    });
    this.bind("onImageButtonClicked",function(gridWrapper, grid, itemIndex, column, buttonIndex, name){
        var popupData = gridWrapper.getPopupData(column.fieldName);
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
        console.log(gridWrapper.getPopupData(column.dataIndex));

        gridWrapper.showPopup(grid, column.fieldName,data[column.fieldName],itemIndex,popupData,false);
    });
    this.bind("onContextMenuItemClicked",function (grid, label, index) {
        switch(label.label)
        {
            case "ExcelExport":
                this.exportExcel(grid,label);
            default :
                if(_callback)
                    _callback(label.label,grid,index);
                break;
        }
    });
    //ctrl + alt + p 클릭 시 팝업
    this.bind("onKeyDown",function(grid, key, ctrl, shift, alt,gridWrapper) {
        if(key == 80 && ctrl && alt)
        {
            var index = grid.getCurrent();
            var popupData = gridWrapper.getPopupData(index.fieldName);

            if(!popupData) {
                return;
            }
            if(-1 == index.itemIndex)
                return ;
            grid.commit(true);
            var newValue = gridWrapper.getSelectedData()[index.fieldName];

            if(undefined == newValue||"" == newValue)
            {
                newValue = "";
            }

            gridWrapper.showPopup(grid, index.fieldName,newValue,index.itemIndex,popupData);
            console.log(index.fieldName);
        }


    });
    this.bind("onCellEdited", function (gridWrapper, grid, itemIndex, dataRow, field)
    {
        var index = grid.getCurrent();

        var columnInfo = grid.columnByName(index.fieldName);
        if(columnInfo.editor == "dropDown")
        {
            grid.commit(true);
            var newValue = gridWrapper.getSelectedData()[index.fieldName];
            var selectedData = "";
            for(var i = 0; i < columnInfo.labels.length; i++)
            {
                if(newValue == columnInfo.labels[i] || newValue == columnInfo.values[i])
                {
                    selectedData = columnInfo.values[i];
                    break;
                }
            }
            grid.setValue(index.dataRow,index.fieldName, selectedData);

        }else
        {
            var popupData = gridWrapper.getPopupData(index.fieldName);

            if(!popupData) {
                return;
            }
            if(-1 == index.itemIndex)
                return ;
            grid.commit(true);
            var newValue = gridWrapper.getSelectedData()[index.fieldName];

            if(undefined == newValue||"" == newValue)
                return ;

            gridWrapper.showPopup(grid, index.fieldName,newValue,index.itemIndex,popupData);
            console.log(index.fieldName);
        }




    });
}
/**
 * 그리드내 검색 후 선택되는 함수
 * @param fieldList
 * @param value
 */
SimpleGridWrapper.prototype.search = function (fieldList, value) {
    var dataRow = dataProvider.searchDataRow({
        startIndex: 0,
        fields: fieldList,
        value: value,
        startIndex: searchStartIndex + 1,
        partialMatch: true,
        select: true
    });
    var ret = dataProvider.getRows(dataRow, dataRow)
    if (ret) {
        searchStartIndex = gridView.getItemIndex(dataRow);
        gridView.setCurrent({itemIndex: treeView.getItemIndex(dataRow), fieldIndex: ret.fieldIndex})
    }
}

//데이터  반환 함수
GridWrapper.prototype.getData = function() {
    this.gridView.commit(true);

    var rows = undefined;
    rows = this.dataProvider.getRows();

    var rowState = this.dataProvider.getAllStateRows();

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
        retList.push($.extend(removeField(this.dataProvider.getJsonRow(createRow[i])),{"__created__" : true}));
    }
    for (var i = 0; i < updateRow.length; i++) {
        retList.push($.extend(removeField(this.dataProvider.getJsonRow(updateRow[i])),{"__modified__" : true}));
    }
    for (var i = 0; i < deletedRow.length; i++) {
        retList.push($.extend(removeField(this.dataProvider.getJsonRow(deletedRow[i])),{"__deleted__" : true}));
    }
    return retList;
};

/**
 * 그리드 전체 데이터를 JSON 형식의 데이터로 반환해주는 함수
 * @returns {undefined}
 */
SimpleGridWrapper.prototype.getJsonRows = function () {
    return this.dataProvider.getRows();
};