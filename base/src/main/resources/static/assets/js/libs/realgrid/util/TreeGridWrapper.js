var TreeGridWrapper = function(p_id,p_rootContext)
{
    var _this = GridWrapper.call(this,p_id,p_rootContext);
    this.childrenProp = undefined;
    var _gridDefaultOption = this.option;
    init = function () {
        _this.defaultStyle = $.extend({}, _this.defaultStyle, _this.style);
        RealGridJS.setTrace(false);
        RealGridJS.setRootContext(_this.rootContext);
        _this.gridView = new RealGridJS.TreeView(_this.i_id);
        _this.dataProvider = new RealGridJS.LocalTreeDataProvider();

        // if(!_this.checkBox)  _this.checkBox.style = {background:"#ff00f2"};
    };

    this._initOption = function (gridView, provider) {
        var option = undefined;
        if (this.gridOption)
            option = this.gridOption;
        else
            option = _gridDefaultOption;

        gridView.setTreeOptions({
            "summaryMode": "aggregate",
            "stateBar": {
                "visible": false
            },
            "header": option.header === undefined ? {"visible": false} : option.header,
            "footer": option.footer === undefined ? {"visible": false} : option.footer,
            "showCheckBox" : option.checkBox === undefined ? false : option.checkBox,
            "lineVisible" : option.lineVisible === undefined ? true : option.lineVisible,
            renderer: {editable: true, falseValues: "N", startEditOnClick: true, trueValues: "Y", type: "check"},
            styles: {paddingLeft: 8, textAlignment: "center", figureBackground: '#ffff0000', figureSize: '130%'},
            // "collapseImage":"/resource/image/smallflag/icon_folder_exp.png",
            // "expandImage":"/resource/image/smallflag/icon_folder_col.png"

        });
        console.log(option.header === undefined ? true : option.header);


        gridView.setDisplayOptions({fitStyle: "evenFill", focusBorderWidth: 1});
        gridView.setStyles(_this.defaultStyle);
        gridView.setEditorOptions({
            applyCellFont: true
        });
        //visible: 인디케이터 영역의 화면 표시여부를 지정합니다.
        gridView.setIndicator(option.indicator === undefined ? {"visible": false} : option.indicator);

        gridView.setStateBar(option.stateBar === undefined ? {"visible": false} : option.stateBar);

        gridView.setCheckBar(option.checkBar === undefined ? {"visible": false} : option.checkBar);
        _this.registerStyle();
    };

    init();
}
TreeGridWrapper.prototype = new GridWrapper();
TreeGridWrapper.prototype.constructor = TreeGridWrapper;
TreeGridWrapper.prototype.option.summaryMode = "aggregate";
TreeGridWrapper.prototype.option.stateBar = { "visible": false };
TreeGridWrapper.prototype.option.header= {"visible": false};
TreeGridWrapper.prototype.option.footer= {"visible": false};

TreeGridWrapper.prototype.style.header = {
    background: "linear,#f2f2f2",
    fontSize: 12,
    fontFamily: GridWrapper.prototype.style.fontFamily,
    foreground: "#000000",
    borderRight: "#cccccc,1",
    fontBold: false,
};

TreeGridWrapper.prototype.style.body = {
    borderRight: "#ff000000,0px",
    borderBottom: "#f0000000,0px",
    line: "#ffaaaaaa,0px",
    fontSize: 12,
    fontFamily: GridWrapper.prototype.style.fontFamily,


};
TreeGridWrapper.prototype.style.grid = {
    border: "#ffffffff,1px"
};
TreeGridWrapper.prototype.style.grid = {
    border: "#ffffffff,1px"
};
TreeGridWrapper.prototype.defaultBind = function(){
    /*
    this.bind("onKeyDown",function(grid, key, ctrl, shift, alt){
        if(key == 13)
        {
            var index = grid.getCurrent();
            if(-1 == index.dataRow || -1 == index.itemIndex)
                return ;
            if(_event)
                _event(grid.getDataProvider().getJsonRow(index.dataRow),index);
        }
    });
    */
}
//======================================================================
//		Tree 영역
//======================================================================
/**
 * 트리 펼치는 함수
 */
TreeGridWrapper.prototype.expandAll = function () {
    this.gridView.expandAll()
    this.gridView.resetSize();
};
/**
 * 트리 모두 닫는 함수
 */
TreeGridWrapper.prototype.collapseAll = function () {
    this.gridView.collapseAll();
    this.gridView.resetSize();
}
/**
 * 트리데이터 설정하는 함수
 * @param list
 * @param rowsProp
 * @param childrenProp
 * @param iconProp
 */
TreeGridWrapper.prototype.setTreeData = function (list, rowsProp, childrenProp, iconProp) {
    this.dataProvider.setJsonRows(list, rowsProp, childrenProp, iconProp);
}
/**
 * 트리데이터 JSON형태로 설정하는 함수
 * @param list
 * @param rowsProp
 * @param childrenProp
 * @param iconProp
 */
TreeGridWrapper.prototype.setTreeDataForJSON = function (list, rowsProp, childrenProp, iconProp) {
    this.dataProvider.setJsonRows(list, rowsProp, childrenProp, iconProp);
};
/**
 * 트리데이터 어레이로 설정하는 함수
 * @param list
 * @param _childrenProp
 */
TreeGridWrapper.prototype.setTreeDataForArray = function (list, _childrenProp) {
    this.childrenProp = _childrenProp;
    this.dataProvider.setRows(list, _childrenProp);
};
//==========================================================================================
//					 duplicate 영역
//==========================================================================================
/**
 * 그리드내 검색 후 선택되는 함수
 * @param fieldList
 * @param value
 */
TreeGridWrapper.prototype.search = function (fieldList, value) {
    var ret = this.dataProvider.searchData({
        fields: fieldList,
        value: value,
        startIndex: this.searchStartIndex + 1,
        partialMatch: true,
        wrap: true,
        select: true
    });
    if (ret) {
        var rowId = ret.dataRow;
        var parents = this.dataProvider.getAncestors(rowId);
        if (parents) {
            for (var i = parents.length - 1; i >= 0; i--) {
                this.gridView.expand(this.gridView.getItemIndex(parents[i]));
            }
            this.searchStartIndex = rowId;
            this.gridView.setCurrent({itemIndex: this.gridView.getItemIndex(rowId), fieldIndex: ret.fieldIndex})
        }
    }
}
//데이터  반환 함수
TreeGridWrapper.prototype.getData = function() {
    this.gridView.commit(true);

    var dataProvider = this.gridView.getDataProvider();
    var rows = undefined;

    if(!this.childrenProp)
        return [];

    rows = dataProvider.getJsonRows(-1,true,this.childrenProp,"icon");

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
    return retList;
};
/**
 * @returns {undefined}
 */
TreeGridWrapper.prototype.getChildren = function(rowId){
    // return this.gridView.getTreeChildren(this._dp, rowId);
    return this.gridView.getChildren(rowId)
}

TreeGridWrapper.prototype.expand =  function (itemIndex) {
    this.gridView.expand(itemIndex, true, true)
}
TreeGridWrapper.prototype.collapse =  function (itemIndex) {
    this.gridView.collapse(itemIndex, true, true)
}
TreeGridWrapper.prototype.getJsonRows = function () {
    return this.dataProvider.getJsonRows(-1, true, this.childrenProp, "icon");
};

TreeGridWrapper.prototype.checkChildren = function(itemIndex, checked, recursive, visibleOnly, checkableOnly, checkEvent) {
    this.gridView.checkChildren(itemIndex, checked, recursive, visibleOnly, checkableOnly, checkEvent);
};

TreeGridWrapper.prototype.getParent = function(index) {
    return this.gridView.getParent(index);
};

TreeGridWrapper.prototype.isCheckedRow = function(index) {
    return this.gridView.isCheckedRow(index);
};

TreeGridWrapper.prototype.getChildCount = function(index) {
    return this.gridView.getChildCount(index);
};
