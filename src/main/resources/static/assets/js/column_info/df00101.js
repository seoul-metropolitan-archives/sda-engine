var df00101 =
    {
        column_info:
            [
                /****************************************************
                 * sortNo: grid column number
                 * name: DB column name
                 * text: grid header text
                 * dataType : 입력방법
                 * editable : 활성비활성 여유
                 * required : 필수 여부
                 * visible  : grid 포함여부
                 ****************************************************/
                {
                    sortNo: 0,
                    width:30,
                    name: "",
                    text: "Classification Scheme UUID",
                    dataType: "code",
                    editable: false,
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    width:80,
                    name: "",
                    text: "Status",
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD111").codeArr,
                    labels: axboot.commonCodeFilter("CD111").nameArr,
                    editable: false,
                    required: true,
                    disable: true
                },
                {
                    sortNo: 2,
                    width:140,
                    name: "",
                    text: "Event Code",
                    width: 140,
                    dataType: "text",
                    required: false,
                    disable : true,
                    editable : false
                },
                {
                    sortNo: 3,
                    name: "",
                    text: "Event Name",
                    width: 160,
                    dataType: "code",
                    required : true,
                },
                {
                    sortNo: 4,
                    name: "",
                    text: "Review Date",
                    width: 140,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD112").codeArr,
                    labels: axboot.commonCodeFilter("CD112").nameArr,
                    required : true,
                    disable: false,
                    editable : true
                },
                {
                    sortNo: 5,
                    name: "",
                    text: "Description",
                    width: 70,
                    textAlignment: "far",
                    dataType: "number",
                    required: false
                },
                {
                    sortNo: 6,
                    name: "notes",
                    text: "Notes",
                    width: 250,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 7,
                    name: "",
                    text: "End",
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
                    required: false
                },
                {
                    sortNo: 8,
                    name: "",
                    text: "Terminator",
                    width: 80,
                    dataType: "text",
                    editable: false,
                    disable : true,
                    required: false
                },
                {
                    sortNo: 10,
                    name: "",
                    text: "End date/time",
                    width: 140,
                    dataType: "timestamp",
                    editable: false,
                    disable : true,
                    required: false
                },
                {
                    sortNo: 11,
                    name: "",
                    text: "Created By",
                    width: 80,
                    dataType: "text",
                    editable: false,
                    disable : true,
                    required: false
                },
                {
                    sortNo: 12,
                    name: "",
                    text: "Date/Time Created",
                    width: 140,
                    dataType: "timestamp",
                    editable: false,
                    disable : true,
                    required: false
                },
                {
                    sortNo: 13,
                    name: "Modified By"
                },
                {
                    sortNo: 12,
                    name: "",
                    text: "Date/Time Modified",
                    width: 140,
                    dataType: "timestamp",
                    editable: false,
                    disable : true,
                    required: false
                }
            ]
    }