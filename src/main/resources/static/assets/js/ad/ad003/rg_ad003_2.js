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
    SeGridView.setFixedOptions({
        colCount: 2
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
            fieldName: "field10"
        },
        {
            fieldName: "field11"
        },
        {
            fieldName: "field12"
        },
        {
            fieldName: "field13"
        },
        {
            fieldName: "field14"
        },
        {
            fieldName: "field15"
        },
        {
            fieldName: "field16"
        },
        {
            fieldName: "field17"
        },
        {
            fieldName: "field18"
        },
        {
            fieldName: "field19",
            dataType: "datetime"
				},
        {
            fieldName: "field20"
        },
        {
            fieldName: "field21",
            dataType: "datetime"
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
                text: "Code",
            },
            width: 130,
            styles: {
                textAlignment: "near",
                background: "#fffffdd6",
                fontFamily: "nanum",
                fontBold: false
            }
        },

        {
            name: "col2",
            fieldName: "field2",
            header: {
                text: "Code Name",
            },
            width: 170,
            styles: {
                textAlignment: "near",
                background: "#fffffdd6",
                fontFamily: "nanum",
                fontBold: false
            }
        },
        {
            name: "col3",
            fieldName: "field3",
            header: {
                text: "Default",
            },
            width: 70,
            editable: true,
            renderer: {
                type: "check",
                shape: "",
                falseValues: "false",
                trueValues: "true",
                editable: "false",
                startEditOnClick: true,
            },
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
                text: "Order No",
            },
            width: 80,
            editable: true,
            sortable: true,
            editor: {
                type: "number",
                textAlignment: "far",
                editFormat: "#,##0.##",
                multipleChar: "+",
            },

            styles: {
                textAlignment: "far",
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false,
                "numberFormat": "#,##0.##"
            }
        },

        {
            name: "col5",
            fieldName: "field5",
            header: {
                text: "Description",
                styles: {
                    background: "linear,#f2f2f2",
                    fontSize: 12,
                    fontFamily: "nanum",
                    borderRight: "#cccccc, 1",
                    fontBold: false
                }
            },
            width: 260,
            editable: true,
            editor: {
                type: "multiline",
                textCase: "upper"
            },
            styles: {
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false,
            },
            renderer: {
                showTooltip: true
            }
        },
        {
            name: "col6",
            fieldName: "field6",
            header: {
                text: "Notes",
                styles: {
                    background: "linear,#f2f2f2",
                    fontSize: 12,
                    fontFamily: "nanum",
                    borderRight: "#cccccc, 1",
                    fontBold: false
                }
            },
            width: 260,
            editable: true,
            editor: {
                type: "multiline",
                textCase: "upper"
            },
            styles: {

                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            },
            renderer: {
                showTooltip: true
            }
        },
        {
            name: "col7",
            fieldName: "field7",
            header: {
                text: "Use",
            },
            width: 50,
            editable: true,
            renderer: {
                type: "check",
                shape: "",
                falseValues: "false",
                trueValues: "true",
                editable: "false",
                startEditOnClick: true,
            },
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
            header: {
                text: "Value1",
            },
            width: 110,
            editable: true,
            sortable: true,
            lookupDisplay: true, //라벨로 표시
            values: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            labels: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            editor: {
                type: "dropDown",
                dropDownCount: 10,
                domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
            },
            styles: {
                textAlignment: "near",
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
                text: "Value2",
            },
            width: 110,
            editable: true,
            sortable: true,
            lookupDisplay: true, //라벨로 표시
            values: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            labels: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            editor: {
                type: "dropDown",
                dropDownCount: 10,
                domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
            },
            styles: {
                textAlignment: "near",
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
                text: "Value3",
            },
            width: 110,
            editable: true,
            sortable: true,
            lookupDisplay: true, //라벨로 표시
            values: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            labels: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            editor: {
                type: "dropDown",
                dropDownCount: 10,
                domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
            },
            styles: {
                textAlignment: "near",
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
                text: "Value4",
            },
            width: 110,
            editable: true,
            sortable: true,
            lookupDisplay: true, //라벨로 표시
            values: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            labels: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            editor: {
                type: "dropDown",
                dropDownCount: 10,
                domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
            },
            styles: {
                textAlignment: "near",
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
                text: "Value5",
            },
            width: 110,
            editable: true,
            sortable: true,
            lookupDisplay: true, //라벨로 표시
            values: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            labels: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            editor: {
                type: "dropDown",
                dropDownCount: 10,
                domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
            },
            styles: {
                textAlignment: "near",
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
        },
        {
            name: "col13",
            fieldName: "field13",
            header: {
                text: "Value6",
            },
            width: 110,
            editable: true,
            sortable: true,
            lookupDisplay: true, //라벨로 표시
            values: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            labels: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            editor: {
                type: "dropDown",
                dropDownCount: 10,
                domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
            },
            styles: {
                textAlignment: "near",
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
        },
        {
            name: "col14",
            fieldName: "field14",
            header: {
                text: "Value7",
            },
            width: 110,
            editable: true,
            sortable: true,
            lookupDisplay: true, //라벨로 표시
            values: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            labels: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            editor: {
                type: "dropDown",
                dropDownCount: 10,
                domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
            },
            styles: {
                textAlignment: "near",
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
        },
        {
            name: "col15",
            fieldName: "field15",
            header: {
                text: "Value8",
            },
            width: 110,
            editable: true,
            sortable: true,
            lookupDisplay: true, //라벨로 표시
            values: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            labels: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            editor: {
                type: "dropDown",
                dropDownCount: 10,
                domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
            },
            styles: {
                textAlignment: "near",
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
        },
        {
            name: "col16",
            fieldName: "field16",
            header: {
                text: "Value9",
            },
            width: 110,
            editable: true,
            sortable: true,
            lookupDisplay: true, //라벨로 표시
            values: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            labels: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            editor: {
                type: "dropDown",
                dropDownCount: 10,
                domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
            },
            styles: {
                textAlignment: "near",
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
        },
        {
            name: "col17",
            fieldName: "field17",
            header: {
                text: "Value10",
            },
            width: 110,
            editable: true,
            sortable: true,
            lookupDisplay: true, //라벨로 표시
            values: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            labels: ["일반사용자", "시스템관리자", "DB관리자", "관리자"],
            editor: {
                type: "dropDown",
                dropDownCount: 10,
                domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
            },
            styles: {
                textAlignment: "near",
                background: "#ffffffff",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
        },
        {
            name: "col18",
            fieldName: "field18",
            header: {
                text: "Created By",
            },
            width: 80,
            editable: false,
            styles: {
                textAlignment: "near",
                background: "#f2f2f2",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false,
            }
        },
        {
            name: "col19",
            fieldName: "field19",
            header: {
                text: "Date/Time Created",
            },
            width: 140,
            editable: false,

            editor: {
                type: "date",
                datetimeFormat: "yyyy-MM-dd hh:mm:ss",
            },
            styles: {
                textAlignment: "near",
                background: "#f2f2f2",
                fontSize: 12,
                fontFamily: "nanum",
                datetimeFormat: "yyyy-MM-dd hh:mm:ss",
                fontBold: false
            }
        },
        {
            name: "col20",
            fieldName: "field20",
            header: {
                text: "Modifed By",
            },
            width: 80,
            editable: false,
            styles: {
                textAlignment: "near",
                background: "#f2f2f2",
                fontSize: 12,
                fontFamily: "nanum",
                fontBold: false
            }
        },
        {
            name: "col21",
            fieldName: "field21",
            header: {
                text: "Date/Time Modified",
            },
            width: 140,
            editable: false,

            editor: {
                type: "date",
                datetimeFormat: "yyyy-MM-dd hh:mm:ss",
            },
            styles: {
                textAlignment: "near",
                background: "#f2f2f2",
                fontSize: 12,
                fontFamily: "nanum",
                datetimeFormat: "yyyy-MM-dd hh:mm:ss",
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
					["AA001", "코드유형", "false", "정렬방법",  "설명", "노트",  "false","", "", "", "", "", "", "", "", "", "", "등록자", "2017-04-17 01:02:03", "수정자", "2017-04-17 01:02:03"],
				["AA002", "정렬방법", "false", "정렬방법", "설명", "노트",  "false","", "", "", "", "", "", "", "", "", "", "등록자", "2017-04-17 01:02:03", "수정자", "2017-04-17 01:02:03"],
                ["AA003", "정렬방법", "false", "정렬방법", "설명", "노트", "false", "", "", "", "", "", "", "", "", "", "", "등록자", "2017-04-17 01:02:03", "수정자", "2017-04-17 01:02:03"],
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