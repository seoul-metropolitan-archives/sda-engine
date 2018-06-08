var df00201 =
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
                    name: "disposalFreezeDegreeUuid",
                    text: "",
                    dataType: "code",
                    editable: false,
                    required: false,
                    visible: false
                },
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
                    name: "freezeYN",
                    text: "Freeze",
                    width: 50,
                    dataType: "check",
                    textAlignment: "center",
                    defaultValue : "N",
                    renderer: {
                        type: "check",
                        shape: "",
                        falseValues: "N",
                        trueValues: "Y",
                        startEditOnClick: true,
                    },
                    editable: false,
                    required: false,
                    disable: true
                },
                {
                    sortNo: 3,
                    width:140,
                    name: "eventCode",
                    text: "Event Code",
                    width: 140,
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU129",
                    sqlColumn : {DISPOSAL_FREEZE_EVENT_UUID: "disposalFreezeEventUuid",EVENT_NAME: "eventName",EVENT_CODE:"eventCode",EVENT_TYPE:"eventType",MAX_DEGREE:"degree"},
                    required: true,
                    visible: true
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
                    name: "degree",
                    text: "Degree",
                    width: 70,
                    textAlignment: "far",
                    dataType: "number",
                    required: false
                },
                {
                    sortNo: 6,
                    name: "description",
                    text: "Description",
                    width: 150,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 7,
                    name: "notes",
                    text: "Notes",
                    width: 150,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 8,
                    name: "endYN",
                    text: "End",
                    width: 50,
                    dataType: "check",
                    textAlignment: "center",
                    defaultValue : "N",
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
                    sortNo: 9,
                    name: "terminatorUuid",
                    text: "Terminator",
                    width: 80,
                    dataType: "text",
                    editable: false,
                    disable : true,
                    required: false
                },
                {
                    sortNo: 10,
                    name: "endDate",
                    text: "End date/time",
                    width: 140,
                    dataType: "timestamp",
                    editable: false,
                    disable : true,
                    required: false
                },
                {
                    sortNo: 11,
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
                    sortNo: 12,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 13,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    visible: true,
                    dataType: "text",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 14,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                }
            ]
    }