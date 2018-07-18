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
                    width: 80,
                    name: "statusUuid",
                    text: "Status",
                    editable: false,
                    disable : true,
                    required : true,
                    values: axboot.commonCodeFilter("CD138").codeArr,
                    labels: axboot.commonCodeFilter("CD138").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: 2,
                    name: "repositoryCode",
                    text: "Repository Code",
                    width: 120,
                    editable: false,
                    dataType: "text"
                },
                {
                    sortNo: 3,
                    name: "repositoryName",
                    text: "Repository Name",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true
                },
                {
                    sortNo: 4,
                    name: "description",
                    text: "Description",
                    width: 70,
                    editable: true,
                    datType: "multiline"
                },
                {
                    sortNo: 5,
                    name: "notes",
                    text: "Notes",
                    width: 120,
                    editable: true,
                    dataType: "text"
                },
                {
                    sortNo: 6,
                    name: "useYn",
                    text: "Use",
                    width: 50,
                    editable: true,
                    dataType: "check",
                    defaultValue : "Y",
                    textAlignment: "center",
                    required: false
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