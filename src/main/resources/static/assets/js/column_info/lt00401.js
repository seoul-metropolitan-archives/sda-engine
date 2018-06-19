var lt00401 =
    {
        column_info:
            [
                {
                    sortNo: 1,
                    name: "pathwayUuid",
                    text: "",
                    width: 150,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "pathwayName",
                    text: "Pathway Name",
                    width: 150,
                    dataType: "text",
                    editable: true,
                    required: true,
                },
                {
                    sortNo: 4,
                    name: "sourceFileFormat",
                    text: "Source File Format",
                    width: 150,
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU133",
                    sqlColumn : {FILE_FORMAT_UUID: "sourceFileFormatUuid", PUID: "puid",FORMAT_NAME: "sourceFileFormat",FORMAT_VERSION:"formatVersion"},
                    required: true,
                    visible: true
                },
                {
                    sortNo: 4,
                    name: "targetFileFormat",
                    text: "Source File Format",
                    width: 150,
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU133",
                    sqlColumn : {FILE_FORMAT_UUID: "targetFileFormatUuid", PUID: "puid",FORMAT_NAME: "targetFileFormat",FORMAT_VERSION:"formatVersion"},
                    required: true,
                    visible: true
                },
                {
                    sortNo: 4,
                    name: "toolName",
                    text: "Tool",
                    width: 150,
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU134",
                    sqlColumn : {TOOL_UUID: "toolUuid",TOOL_NAME: "toolName"},
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
                    defaultValue : "Y",
                    renderer: {
                        type: "check",
                        shape: "",
                        falseValues: "N",
                        trueValues: "Y",
                        startEditOnClick: true,
                    },
                    required: false,
                    editable: true
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
                    name: "sourceFileFormatUuid",
                    text: "",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: true,
                    visible: false
                },
                {
                    sortNo: 12,
                    name: "targetFileFormatUuid",
                    text: "",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: true,
                    visible: false
                },
                {
                    sortNo: 12,
                    name: "toolUuid",
                    text: "",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: true,
                    visible: false
                }
            ]
    }