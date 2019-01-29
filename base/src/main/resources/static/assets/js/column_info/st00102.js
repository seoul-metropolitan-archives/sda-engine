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
                    width:80,
                    name: "statusUuid",
                    text: "Status",
                    editable: false,
                    disable : true,
                    required : true,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD138").codeArr,
                    labels: axboot.commonCodeFilter("CD138").nameArr
                },
                {
                    sortNo: 3,
                    name: "shelfCode",
                    text: "Shelf Code",
                    width: 120,
                    editable: false,
                    dataType: "text",
                    disable : true,
                },
                {
                    sortNo: 4,
                    name: "shelfName",
                    text: "Shelf Name",
                    width: 180,
                    editable: true,
                    dataType: "text",
                    required: true
                },
                {
                    sortNo: 5,
                    name: "maxContainer",
                    text: "Max Container",
                    width: 100,
                    editable: false,
                    dataType: "text",
                    disable : true
                },
                {
                    sortNo: 6,
                    name: "description",
                    text: "Description",
                    width: 200,
                    editable: true,
                    dataType: "multiline"
                },
                {
                    sortNo: 7,
                    name: "notes",
                    text: "Notes",
                    width: 200,
                    editable: true,
                    dataType: "text"
                },
                {
                    sortNo: 8,
                    name: "useYn",
                    text: "Use",
                    width: 50,
                    editable: true,
                    dataType: "check",
                    defaultValue : "Y",
                    textAlignment: "center"
                },
                {
                    sortNo: 9,
                    name: "insertUuid",
                    text: "Created By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 10,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp"
                },
                {
                    sortNo: 11,
                    name: "updateUuid",
                    text: "Modified By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 12,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp"
                }
            ]
    }