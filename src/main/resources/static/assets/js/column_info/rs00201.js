var rs00201 =
    {
        column_info :
            [

                /****************************************************
                 * sortNo: grid column number
                 * name: 바인딩명 - VO 변수명과 일치
                 * text: 그리드 헤더 텍스트
                 * dataType : 입력방법
                 * editable : 활성비활성 여유
                 * required : 필수 여부
                 * visible  : grid 포함여부
                 ****************************************************/
                {
                    sortNo: 0,
                    name: "triggerUuid",
                    text: "Trigger UUID",
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    width:50,
                    name: "statusUuid",
                    text: "Status",
                    editable: false,
                    dataType: "code",
                    values: axboot.commonCodeFilter("CD134").codeArr,
                    labels: axboot.commonCodeFilter("CD134").nameArr,
                    required: false
                },
                {
                    sortNo: 2,
                    width: 120,
                    name: "triggerCode",
                    text: "Trigger Code",
                    editable: false,
                    dataType : "combo",
                    required: true
                },
                {
                    sortNo: 3,
                    width: 150,
                    name: "triggerName",
                    text: "Trigger Name",
                    editable: true,
                    dataType:"text",
                    required: true
                },
                {
                    sortNo: 4,
                    name: "triggerDate",
                    text: "Trigger Date",
                    width: 150,
                    dataType: "date",
                    editable: true,
                    required: false
                },
                {
                    sortNo: 5,
                    name: "description",
                    text: "Description",
                    width: 70,
                    editable: true,
                    datType: "multiline"
                },
                {
                    sortNo: 6,
                    name: "notes",
                    text: "Notes",
                    width: 120,
                    editable: true,
                    dataType: "text"
                },
                {
                    sortNo: 7,
                    name: "useYN",
                    text: "use",
                    width: 120,
                    width: 50,
                    dataType: "check",
                    textAlignment: "center",
                    defaultValue : "Y",
                    renderer: {
                        type: "check",
                        shape: "",
                        falseValues: "N",
                        trueValues: "Y",
                        startEditOnClick: true,
                    },
                    required: true
                },
                {
                    sortNo: 8,
                    name: "insertUuid",
                    text: "Created By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 12,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp"
                },
                {
                    sortNo: 9,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 14,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp"
                }
            ]
    }