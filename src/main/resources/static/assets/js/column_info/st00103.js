var st00103 =
    {
        column_info:
            [
                {
                    sortNo: 0,
                    name: "locationUuid",
                    text: "Location UUID",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "shelfUuid",
                    text: "Shelf UUID",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    width:50,
                    name: "statusUuid",
                    text: "Status",
                    editable: false,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD138").codeArr,
                    labels: axboot.commonCodeFilter("CD138").nameArr,
                    required: false
                },
                {
                    sortNo: 3,
                    name: "rowNo",
                    text: "Row",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true
                },
                {
                    sortNo: 4,
                    name: "columnNo",
                    text: "Column",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true
                },
                {
                    sortNo: 5,
                    name: "totalContainer",
                    text: "Total Container",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true
                },
                {
                    sortNo: 6,
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
                    sortNo: 7,
                    name: "insertUuid",
                    text: "Created By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 8,
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
                    text: "Modified By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 10,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp"
                }
            ]
    }