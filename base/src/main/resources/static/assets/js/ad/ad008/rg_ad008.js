var gridView;
var dataProvider;

$(document).ready(function () {

    RealGridJS.setTrace(false);
    RealGridJS.setRootContext("js/realgrid");

    dataProvider = new RealGridJS.LocalDataProvider();
    gridView = new RealGridJS.GridView("realgrid");
    gridView.setDataSource(dataProvider);

    /*//그리드 높이 변경하는 방법
    document.getElementById("realgrid").style.height =80 + "%"
    gridView.resetSize();
    */

    //컬럼너비 자동조정 none, even, evenfill, fill
    gridView.setDisplayOptions({
        fitStyle: "none"
    });

    //체크바 영역의 화면 표시여부를 지정합니다. true / false
    gridView.setCheckBar({
        visible: false,
        width: 50
    });


    //하단 합계부분 영역의 화면 표시여부를 지정합니다. true / false
    gridView.setFooter({
        resizable: false,
        visible: false,
        mergeCells: ["Col1", "Col2", "Col3"]
            //mergeCells: [["Col1", "Col2", "Col3"], ["Col6", "Col7"]] //이런 형태도 가능하다.
    });
    //visible: 인디케이터 영역의 화면 표시여부를 지정합니다.
    gridView.setIndicator({
        visible: true,
        styles: {
            background: "linear,#f2f2f2",
            fontFamily: "nanum",
        },
    });

    //visible: 상태바 영역의 화면 표시여부를 지정합니다.
    gridView.setStateBar({
        visible: false
    });

    //컬럼 헤더를 이 곳으로 끌어다 놓으면 그 컬럼으로 그룹핑합니다 안보여 지기
    gridView.setPanel({
        visible: false
    });

    //행 삽입과 행 추가, 행 삭제가 가능하도록 옵션 조정
    gridView.setEditOptions({
        insertable: false,
        appendable: false,
        deletable: false
    })

    //데이터를 바로 삭제하지 않고 상태만 변경
    dataProvider.setOptions({
        softDeleting: true
    })

    //선택된 셀의 행 표시-투명하게 덮어씌우던 행표시
    gridView.setDisplayOptions({
        rowFocusVisible: false,
        rowFocusBackground: "#32f2fbff"
    });

    //사용하여 컨텍스트 메뉴를 설정할 수 있습니다.
    gridView.setContextMenu([{
        label: "엑셀 내보내기"
            /*}, {
            	label: "Menu2 입니다"
            }, {
            	label: "-" // menu separator를 삽입합니다.
            }, {
            	label: "Menu3 입니다"*/
			}]);

    //토스트 메시지창 설정
    gridView.setOptions({
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
    gridView.setGroupingOptions({
        enabled: true,
        prompt: "컬럼 헤더를 이 곳으로 끌어다 놓으면 그 컬럼으로 그룹핑합니다.",
        linear: false,
        expandWhenGrouping: false,
        toast: {
            visible: true
        }
    });

    //각 Editor에 CSS 스타일 적용 여부 설정
    gridView.setEditorOptions({
        //useCssStyle: true,  //모든 에디터에 CSS를 적용할 경우 사용  
        useCssStyleDropDownList: true, //dropDown
        useCssStyleDatePicker: true, //달력
        useCssStylePopupMenu: true, //popupMenu
        useCssStyleMultiCheck: true //multiCheck
    });

    // 필터에 CSS 스타일 적용 여부 설정
    gridView.setFilteringOptions({
        selector: {
            useCssStyle: true
        }
    });


    // 이미지 버튼 클릭 이벤트 -- 추가
    gridView.onImageButtonClicked = function (grid, itemIndex, column, buttonIndex, name) {
        alert("onImageButtonClicked: " + itemIndex + ", " + column.name + ", " + buttonIndex + ", " + name);
    };

    //그리드 행 선택시 표시
    /*gridView.setDisplayOptions({
      rowFocusVisible:true, 
      rowFocusBackground:"#11000000",
      rowFocusMask: "row",
      focusBorderWidth:2,
    });*/

    //그리드 행 선택시 표시
    /* gridView.setOptions({
       select: {style: RealGridJS.SelectionStyle.ROWS}
      }); 

      gridView.setStyles({
             selection: {
                 background: "#00f2fbff",
                 border: "#88000000,1"
             }
      }); 
     */

    //에디터에의 폰트패밀리와 사이즈를 컬럼과 같게
    gridView.setEditorOptions({
        applyCellFont: true
    });

    //열고정
    gridView.setFixedOptions({
      colCount: 3
    });

    //헤더 스타일 공통부분
    gridView.setStyles({
        header: {
            textAlignment: "near",
            background: "linear,#f2f2f2",
            fontSize: 12,
            fontFamily: "nanum",
            foreground: "#000000",
            borderRight: "#cccccc, 1",
            fontBold: false
        },
        grid: {
            border: "#ffffffff,0"
        }

    });


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
				},
        {
            fieldName: "field7"
				},
        {
            fieldName: "field8"
				},
        {
            fieldName: "field9"
				},
        {
            fieldName: "field10",
            dataType: "datetime"
		},
                {
            fieldName: "field11"
				},
        {
            fieldName: "field12",
            dataType: "datetime"
		}

			];
    //DataProvider의 setFields함수로 필드를 입력합니다.
    dataProvider.setFields(fields);

    //field1필드와 연결된 컬럼을 가진 배열 객체를 생성합니다.
    var columns = [
        {
            name: "col1",
            fieldName: "field1",
            width: 180,
            header: {
                text: "Entity Type",

            },
            editable: false,
            styles: {
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
				},
        {
            name: "col2",
            fieldName: "field2",
            header: {
                text: "Entity Name",

            },
            width: 180,
            editable: false,
            styles: {
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
				},
        {
            name: "col3",
            fieldName: "field3",
            header: {
                text: "Column",

            },
            width: 120,
            editable: false,
            sortable: true,
            styles: {
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
				},
        {
            name: "col4",
            fieldName: "field4",
            header: {
                text: "Primary Key",

            },
            width: 250,
             editable: false,
            styles: {
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
				},

        
        {
            name: "col5",
            fieldName: "field5",
            header: {
                text: "Program",

            },
            width: 180,
            editable: false,
            sortable: true,
            styles: {
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
				},
        {
            name: "col6",
            fieldName: "field6",
            header: {
                text: "Function",

            },
            width: 150,
            editable: false,
            lookupDisplay: true, //라벨로 표시
            values: ["수정", "삭제"],
            labels: ["수정", "삭제"],
            editor: {
                type: "dropDown",
                dropDownCount: 10,
                domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
            },
            styles: {
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
				},
         {
            name: "col7",
            fieldName: "field7",
            header: {
                text: "Previous Value",

            },
            width: 150,
            editable: false,
            sortable: true,
            styles: {
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
				},
              {
            name: "col8",
            fieldName: "field8",
            width: 150,
            header: {
                text: "New Value",

            },
             editable: false,
            sortable: true,
            styles: {
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
				},
         {
            name: "col9",
            fieldName: "field9",
            header: {
                text: "Created By",

            },
            width: 70,
            editable: false,
            sortable: true,
            styles: {
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
				},
         {
            name: "col10",
            fieldName: "field10",
                    header: {
                text: "Date/Time Created",

            },
            width: 150,
            editable: false,
             editor: {
                type: "date",
                 "datetimeFormat": "yyyy-MM-dd hh:mm:ss"

            },
            styles: {

                "datetimeFormat": "yyyy-MM-dd hh:mm:ss",
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
				},
         {
            name: "col11",
            fieldName: "field11",
            header: {
                text: "Modifed By",

            },
            width: 70,
            editable: false,
            sortable: true,
            styles: {
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
				},
         {
            name: "col12",
            fieldName: "field12",
                    header: {
                text: "Date/Time Modified",

            },
            width: 150,
            editable: false,
             editor: {
                type: "date",
                 "datetimeFormat": "yyyy-MM-dd hh:mm:ss"

            },
            styles: {

                "datetimeFormat": "yyyy-MM-dd hh:mm:ss",
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
				}

			];
    //컬럼을 GridView에 입력 합니다.
    gridView.setColumns(columns);

    //컬럼 수정 못하도록
    /*var column = gridView.columnByName("col1");
            gridView.setColumnProperty(column, "editable", false);*/


    var data = [
        ["AD001", "사용자", "USER_ID", "a730d605-8f4a-471c-b7b4-1f58ed300007", "사용자","수정","AAA","AAB","등록자", "2017-04-17 21:02:00","수정자", "2017-04-17 21:02:00"],
        ["AD001", "사용자그룹", "NAME", "", "사용자","삭제","KIM KIN","KIM KIN2","등록자", "2017-04-17 09:02:08","수정자", "2017-04-17 21:02:00"]
			];
    dataProvider.setRows(data);

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
    gridView.onCellButtonClicked = function (grid, itemIndex, column) {
        alert("CellButton Clicked: itemIndex=" + itemIndex + ", fieldName=" + column.fieldName);
    };



    //Tooltip 설정
    gridView.onShowTooltip = function (grid, index, value) {
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