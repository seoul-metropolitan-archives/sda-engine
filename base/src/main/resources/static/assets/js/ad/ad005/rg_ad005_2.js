var SeGridView;
var SeDataProvider;

$(document).ready(function () {

    RealGridJS.setTrace(false);
    RealGridJS.setRootContext("js/realgrid");

    SeDataProvider = new RealGridJS.LocalDataProvider();
    SeGridView = new RealGridJS.GridView("realgrid2");
    SeGridView.setDataSource(SeDataProvider);


    //컬럼너비 자동조정 none, even, evenfill, fill
    SeGridView.setDisplayOptions({
        fitStyle: "none"
    });

    //체크바 영역의 화면 표시여부를 지정합니다. true / false
    SeGridView.setCheckBar({
        visible: false
    });


    //하단 합계부분 영역의 화면 표시여부를 지정합니다. true / false
    SeGridView.setFooter({
        resizable: false,
        visible: false,
        mergeCells: ["Col1", "Col2", "Col3"]
            //mergeCells: [["Col1", "Col2", "Col3"], ["Col6", "Col7"]] //이런 형태도 가능하다.
    });
    //visible: 인디케이터 영역의 화면 표시여부를 지정합니다.
    SeGridView.setIndicator({
        visible: true,
        styles: {
            background: "linear,#f2f2f2",
            fontFamily: "nanum",
        },
    });


    //visible: 상태바 영역의 화면 표시여부를 지정합니다.
    SeGridView.setStateBar({
        visible: false
    });

    //컬럼 헤더를 이 곳으로 끌어다 놓으면 그 컬럼으로 그룹핑합니다 안보여 지기
    SeGridView.setPanel({
        visible: false
    });

    //행 삽입과 행 추가, 행 삭제가 가능하도록 옵션 조정
    SeGridView.setEditOptions({
        insertable: true,
        appendable: true,
        deletable: true
    })

    //데이터를 바로 삭제하지 않고 상태만 변경
    SeDataProvider.setOptions({
        softDeleting: true
    })

    //선택된 셀의 행 표시-투명하게 덮어씌우던 행표시
    SeGridView.setDisplayOptions({
        rowFocusVisible: false,
        rowFocusBackground: "#32f2fbff"
    });

    //사용하여 컨텍스트 메뉴를 설정할 수 있습니다.
    SeGridView.setContextMenu([{
        label: "엑셀 내보내기"
            /*}, {
            	label: "Menu2 입니다"
            }, {
            	label: "-" // menu separator를 삽입합니다.
            }, {
            	label: "Menu3 입니다"*/
			}]);

    //토스트 메시지창 설정
    SeGridView.setOptions({
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
        }
    });

    //컬럼 헤더를 이 곳으로 끌어다 놓으면 그 컬럼으로 그룹핑합니다 판넬 컨트롤
    SeGridView.setGroupingOptions({
        enabled: true,
        prompt: "컬럼 헤더를 이 곳으로 끌어다 놓으면 그 컬럼으로 그룹핑합니다.",
        linear: false,
        expandWhenGrouping: false,
        toast: {
            visible: true
        }
    });

    //각 Editor에 CSS 스타일 적용 여부 설정
    SeGridView.setEditorOptions({
        //useCssStyle: true,  //모든 에디터에 CSS를 적용할 경우 사용  
        useCssStyleDropDownList: true, //dropDown
        useCssStyleDatePicker: true, //달력
        useCssStylePopupMenu: true, //popupMenu
        useCssStyleMultiCheck: true //multiCheck
    });

    // 필터에 CSS 스타일 적용 여부 설정
    SeGridView.setFilteringOptions({
        selector: {
            useCssStyle: true
        }
    });

    //에디터에의 폰트패밀리와 사이즈를 컬럼과 같게
    SeGridView.setEditorOptions({
        applyCellFont: true
    });

    // 이미지 버튼 클릭 이벤트 -- 추가
    SeGridView.onImageButtonClicked = function (grid, itemIndex, column, buttonIndex, name) {
        alert("onImageButtonClicked: " + itemIndex + ", " + column.name + ", " + buttonIndex + ", " + name);
    };



    //헤더 스타일 공통부분
    SeGridView.setStyles({
        header: {
            textAlignment: "near",
            background: "linear,#f2f2f2",
            fontSize: 12,
            fontFamily: "nanum",
            foreground: "#000000",
            borderRight: "#cccccc, 1",
            fontBold: false,
        },
        grid: {
            border: "#ffffffff,0"
        }
    });

    /* var headerHeight = SeGridView.getHeader().height  // 헤더 높이
            SeGridView.setHeader(
              {height : headerHeight + 30} // 헤더 높이 +10
            );
*/

    //열고정
    /*SeGridView.setFixedOptions({
        colCount: 2
    });*/

    //두 개의 필드를 가진 배열 객체를 생성합니다.
    var fields = [
        {
            fieldName: "field1"
        },
        {
            fieldName: "field2"
        },
        {
            fieldName: "field3"
        },
        {
            fieldName: "field4"
        },
        {
            fieldName: "field5"
        },
        {
            fieldName: "field6"
        }
			];
    //SeDataProvider의 setFields함수로 필드를 입력합니다.
    SeDataProvider.setFields(fields);




    //field1필드와 연결된 컬럼을 가진 배열 객체를 생성합니다.
    var columns = [
        {
            name: "col1",
            fieldName: "field1",
            header: {
                text: "Table Name",
            },
            width: 170,
            editable: false,
            styles: {
                textAlignment: "near",
                background: "#fff2f2f2",
                fontFamily: "nanum",
                fontBold: false
            }
        },

        {
            name: "col2",
            fieldName: "field2",
            header: {
                text: "Table Desc",
            },
            width: 170,
            editable: false,
            styles: {
                textAlignment: "near",
                background: "#fff2f2f2",
                fontFamily: "nanum",
                fontBold: false
            }
        },
        {
            name: "col3",
            fieldName: "field3",
            header: {
                text: "Column Name",
            },
            width: 170,
            editable: false,
            styles: {
                background: "#fff2f2f2",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
        },
        {
            name: "col4",
            fieldName: "field4",
            header: {
                text: "Column Desc",
            },
            width: 170,
            editable: false,
            sortable: true,
            styles: {
                textAlignment: "near",
                background: "#fff2f2f2",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false,
            }
        },

        {
            name: "col5",
            fieldName: "field5",
            header: {
                text: "Data Type",
            },
            width: 130,
            editable: false,
            styles: {
                background: "#fff2f2f2",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false,
            }
        },
        {
            name: "col6",
            fieldName: "field6",
            header: {
                text: "Nullable",
            },
            width: 60,
            editable: false,
             renderer: {
                type: "check",
                shape: "",
                falseValues: "false",
                trueValues: "true",
                /*editable: "false",*/
                startEditOnClick: true,
            },
            styles: {
                background: "#fff2f2f2",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
        }

    ];
    //컬럼을 GridView에 입력 합니다.
    SeGridView.setColumns(columns);

    //컬럼 수정 못하도록
    var column = SeGridView.columnByName("col");
    SeGridView.setColumnProperty(column, "editable", false);


    var data = [
					["AD001", "freeze", "column", "column desc",  "varchar2", "nullable"],
					["AD001", "freeze", "column", "column desc",  "varchar2", "nullable"],
					["AD001", "freeze", "column", "column desc",  "varchar2", "nullable"],
				
			];
    SeDataProvider.setRows(data);





    /*
    			// SortStyle을 NONE으로 설정
    			$("#btnChangeSortStyleNone").on("click", function(){
    				setSortStyles(RealGridJS.SortStyle.NONE);
    			})    

    			// SortStyle을 EXCLUSIVE로 설정
    			$("#btnChangeSortStyleExclusive").on("click", function(){
    				setSortStyles(RealGridJS.SortStyle.EXCLUSIVE);
    			})    
    */
    //셀버튼 클릭 이벤트
    SeGridView.onCellButtonClicked = function (grid, itemIndex, column) {
        alert("CellButton Clicked: itemIndex=" + itemIndex + ", fieldName=" + column.fieldName);
    };

    //Tooltip 설정
    SeGridView.onShowTooltip = function (grid, index, value) {
        var column = index.column;
        var itemIndex = index.itemIndex;

        var tooltip = value;
        if (column == "col1") {
            tooltip = "내부키: " + value +
                "\r\n아이디:" + grid.getValue(itemIndex, "field2") +
                "\r\n사용자명:" + grid.getValue(itemIndex, "field3");
        }
        return tooltip;
    }
});

function goMainPage() {
    location.href = "/";
}