var st00101 =
    {
        column_info:
            [
                {
                    sortNo: 0,
                    name: "repositoryUuid",
                    text: "Repository UUID",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    width:50,
                    name: "statusUuid",
                    text: "Status",
                    editable: false,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD134").codeArr,
                    labels: axboot.commonCodeFilter("CD134").nameArr,
                    required: false
                },
                {
                    sortNo: 2,
                    name: "repositoryCode",
                    text: "Repository Code",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true
                },
                {
                    sortNo: 3,
                    name: "repositoryName",
                    text: "Repository Name",
                    width: 120,
                    editable: true,
                    datatype: "text",
                    required: true
                },
                {
                    sortNo: 4,
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
                    sortNo: 5,
                    name: "insertUuid",
                    text: "Created By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 6,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp"
                },
                {
                    sortNo: 7,
                    name: "updateUuid",
                    text: "Modified By",
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