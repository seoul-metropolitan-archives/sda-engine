var st00102 =
    {
        column_info:
            [
                {
                    sortNo: 0,
                    name: "shelfUuid",
                    text: "Shelf UUID",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "repositoryUuid",
                    text: "Repository UUID",
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
                    name: "shelfCode",
                    text: "Shelf Code",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true
                },
                {
                    sortNo: 4,
                    name: "shelfName",
                    text: "Shelf Name",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true
                },
                {
                    sortNo: 5,
                    name: "maxContainer",
                    text: "Max Container",
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