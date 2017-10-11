/**
 * real grid
 */
var Popup = function()
{
	var popupCd = "";
	var searchData = "";
	var popupPageUrl = "/adPopup/getPopupPage.do";
	var popupUrl = "/adPopup/getPopupInfo.do";
	var targetID = "";
	var popupGrid = null;
	var gridID = "";
	
	var callback = function(response){
		alert("선택 데이터=>"+JSON.stringify(response));
	};
	var init = function()
	{
		initInstance();
	};
	var getUUID = function() {
	    function s4() {
	      return ((1 + Math.random()) * 0x10000 | 0).toString(16).substring(1);
	    }
	    return s4() + s4() + s4() + s4() + s4() + s4() + s4() + s4();
	  };
	var initInstance = function()
	{
		gridID = "popupGrid"+getUUID();
		
	};
	var bindEvent = function()
	{
		$("#"+targetID+" ._popupOk").click(function(){
			callback(popupGrid.getSelectedData());
			close();
			
		});
		$("#"+targetID+" ._popupClose").click(function(){
			close();
		});
	};
	var close = function()
	{
		$("#"+targetID+" ._popupOk").unbind("click");
		$("#"+targetID).empty();
	};
	this.setCallback = function(_callback)
	{
		if(typeof callback != "function")
			throw "콜백이 아닙니다.";
		callback = _callback;
		return this;
	};
	this.setTargetID = function(_targetID)
	{
		targetID = _targetID;
		return this;
	};
	this.setPopupCd = function(_popupCd)
	{
		popupCd = _popupCd;
		return this;
	};
	this.setSearchData = function(searchData)
	{
		searchData = _searchData;
		return this;
	};
	var makeGrid = function(data)
	{
		setTimeout(function(){
			if(undefined === popupGrid)
			{
				$("#"+targetID+" ._popupTitle").text(data.popupNm);
				$("#"+targetID+" ._popupList").attr("id",gridID);
				$("#"+targetID+" ._popup").show();
				$("#"+targetID+" ._popup").css("visibility","hidden");
				var columnInfo = [];
				var width = 0;
				for(var i = 0; i < data.columnInfo.length; i++)
				{
					columnInfo = data.columnInfo[i];
					
					if(false !== columnInfo.visible)
					{
						width += columnInfo.width;

					
				}
				popupGrid = new GridWrapper();
				$("#"+targetID).css("width",width+50);
				$("#"+gridID).parents().eq(0).css("width",width+50).css("height","300px");
				popupGrid.setGridId(gridID,"100%","100%");
				popupGrid.setColumnInfo(data.columnInfo).makeGrid();
				$("#"+targetID+" ._popup").css("visibility","visible");
			}
			popupGrid.setData("set",data.list); 
		},50);
	};
	var _show = function()
	{
		var result = false;
		$.ajax({
			url : popupPageUrl,
			type : "POST",
			dataType : "html",
			async : false,
			success : function(html)
			{
				$("#"+targetID).empty();
				$("#"+targetID).append(html);
				bindEvent();

				result = true;
			},
			error : function(a,b,c)
			{
				console.log(a);
				console.log(b);
				console.log(c);
				alert("팝업을 생성하는 중 문제가 발생하였습니다.");
			}
		});
		return result;
	};
	var search = function(_data,_callback)
	{
		var data = {
				header : {
					"popupCd" : popupCd
			},
			"searchData" : undefined === _data ? searchData : _data
		};
		$.ajax({
			url : popupUrl,
			type : "POST",
			dataType :"JSON",
			contentType: 'application/json',
			data : JSON.stringify(data),
			async : false,
			success : function(response)
			{
				
				if(response.list.length == 1)
				{
					callback(response.list[0]);
				}
				else
				{
					if(undefined !== _callback)
					{
						if(_callback())
						{
							makeGrid(response);
						}
					}
				}
			},
			error : function(a,b,c)
			{
				console.log(a);
				console.log(b);
				console.log(c);
				alert("검색 중 문제가 발생하였습니다.");
			}
		});
	};
	
	this.show = function()
	{
		if(undefined === targetID)
			throw "팝업 생성할 태그의 ID를 설정해주세요.";
		if(undefined === popupCd)
			throw "팝업코드를 설정해주세요.";
		
		search(undefined,_show);
		
	};
	init();
};
