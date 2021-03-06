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
                    sortNo: 1,
                    width:30,
                    name: "disposalFreezeEventUuid",
                    text: "",
                    dataType: "code",
                    editable: false,
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    width:80,
                    name: "statusUuid",
                    text: "Status",
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD115").codeArr,
                    labels: axboot.commonCodeFilter("CD115").nameArr,
                    editable: false,
                    required: false,
                    disable: true
                },
                {
                    sortNo: 3,
                    width:140,
                    name: "eventCode",
                    text: "Event Code",
                    dataType: "text",
                    required: false,
                    editable : false,
                    disable : true
                },
                {
                    sortNo: 4,
                    name: "eventName",
                    text: "Event Name",
                    width: 160,
                    dataType: "code",
                    required: true,
                    editable : true,
                    disable : false
                },
                {
                    sortNo: 5,
                    name: "eventTypeUuid",
                    text: "Event Type",
                    width: 80,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD116").codeArr,
                    labels: axboot.commonCodeFilter("CD116").nameArr,
                    editable: true,
                    required: true,
                    disable: false
                },
                {
                    sortNo: 6,
                    name: "reviewDate",
                    text: "Review Date",
                    width: 100,
                    dataType: "date",
                    length: 8,
                    required: false,
                    editable : true,
                    disable : false
                },
                {
                    sortNo: 7,
                    name: "description",
                    text: "Description",
                    width: 150,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 8,
                    name: "notes",
                    text: "Notes",
                    width: 150,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 9,
                    name: "endYN",
                    text: "End",
                    width: 50,
                    dataType: "check",
                    textAlignment: "center",
                    defaultValue : "N",
                    required: false,
                },
                {
                    sortNo: 10,
                    name: "terminatorUuid",
                    text: "Terminator",
                    width: 80,
                    dataType: "text",
                    editable: false,
                    disable : true,
                    required: false
                },
                {
                    sortNo: 11,
                    name: "endDate",
                    text: "End date/time",
                    width: 140,
                    dataType: "timestamp",
                    editable: false,
                    disable : true,
                    required: false
                },
                {
                    sortNo: 12,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    dataType: "text",
                    editable : false,
                    readonly : true,
                    visible: true,
                    required: false,
                    disable: true
                },
                {
                    sortNo: 13,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 14,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    visible: true,
                    dataType: "text",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 15,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 16,
                    name: "reason",
                    text: "REASON",
                    dataType: "text",
                    visible: false
                },
                {
                    sortNo: 17,
                    name: "freezeCnt",
                    dataType: "number",
                    visible: false
                },
                {
                    sortNo: 18,
                    name: "aggregationCnt",
                    dataType: "number",
                    visible: false
                },
                {
                    sortNo: 19,
                    name: "itemCnt",
                    dataType: "number",
                    visible: false
                }
            ]
    }