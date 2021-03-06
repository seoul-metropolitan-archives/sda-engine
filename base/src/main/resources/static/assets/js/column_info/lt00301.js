var lt00301 =
    {
        column_info:
            [
                {
                    sortNo: 1,
                    name: "toolUuid",
                    text: "",
                    width: 150,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "toolName",
                    text: "Tool Name",
                    width: 150,
                    dataType: "text",
                    editable: true,
                    required: true,
                },
                {
                    sortNo: 3,
                    name: "api",
                    text: "API",
                    width: 200,
                    dataType: "text",
                    editable: true,
                    required: true,
                },
                {
                    sortNo: 4,
                    name: "softwareName",
                    text: "Software Name",
                    width: 150,
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU132",
                    sqlColumn : {SOFTWARE_UUID: "softwareUuid",SOFTWARE_NAME: "softwareName",SOFTWARE_VERSION:"softwareVersion"},
                    required: true,
                    visible: true
                },
                {
                    sortNo: 5,
                    name: "description",
                    text: "Description",
                    width: 150,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 6,
                    name: "notes",
                    text: "Notes",
                    width: 150,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 7,
                    name: "useYN",
                    text: "Use",
                    width: 50,
                    dataType: "check",
                    textAlignment: "center",
                    defaultValue : "N",
                    required: false
                },
                {
                    sortNo: 8,
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
                    sortNo: 9,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 10,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    visible: true,
                    dataType: "text",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 11,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 12,
                    name: "softwareUuid",
                    text: "",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: true,
                    visible: false
                }
            ]
    }