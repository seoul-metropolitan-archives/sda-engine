var rs00301 =
    {
        column_info :
            [

                /****************************************************
                 * sortNo: grid column number
                 * name: 바인딩명 - VO 변수명과 일치
                 * text: 그리드 헤더 텍스트
                 * dataType : 입력방법
                 * editable : 활성비활성 여유
                 * required : 필수 여부
                 * visible  : grid 포함여부
                 ****************************************************/
                {
                    sortNo: 0,
                    name: "recordScheduleUuid",
                    text: "Record Schedule UUID",
                    editable: false,
                    dataType: "text",
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
                    required: true
                },
                {
                    sortNo: 2,
                    width: 120,
                    name: "recalculationYn",
                    text: "Recalculation",
                    dataType: "check",
                    textAlignment: "center",
                    defaultValue : "N",
                    editable: false
                },
                {
                    sortNo: 3,
                    width: 150,
                    name: "rsCode",
                    text: "RS Code",
                    editable: false,
                    dataType:"text",
                    required: false
                },
                {
                    sortNo: 4,
                    name: "rsName",
                    text: "RS Name",
                    width: 150,
                    dataType: "text",
                    editable: true,
                    required: true
                },
                {
                    sortNo: 5,
                    name: "grsCode",
                    text: "GRS Code",
                    width: 120,
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU130",
                    sqlColumn : {GENERAL_RECORD_SCHEDULE_UUID : "generalRecordScheduleUuid",GRS_CODE : "grsCode",GRS_NAME : "grsName"},
                    pasteTarget:"grsName",
                    required: false
                },
                {
                    sortNo: 6,
                    name: "grsName",
                    text: "GRS Name",
                    width: 120,
                    editable: false,
                    required: true,
                    dataType: "text"
                },
                {
                    sortNo: 7,
                    name: "retentionPeriodUuid",
                    text: "Retention Period",
                    width: 120,
                    editable: true,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD133").codeArr,
                    labels: axboot.commonCodeFilter("CD133").nameArr,
                    required: true
                },
                {
                    sortNo: 8,
                    name: "disposalTypeUuid",
                    text: "Disposal Type",
                    width: 120,
                    editable: true,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD135").codeArr,
                    labels: axboot.commonCodeFilter("CD135").nameArr,
                    required: true
                },
                {
                    sortNo: 9,
                    name: "basedOn",
                    text: "Based On",
                    width: 120,
                    editable: true,
                    required: true,
                    dataType: "multiline"
                },
                {
                    sortNo: 10,
                    name: "triggerName",
                    text: "Trigger Name",
                    width: 120,
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU131",
                    sqlColumn : {TRIGGER_UUID : "triggerUuid",TRIGGER_CODE : "triggerCode",TRIGGER_NAME : "triggerName",TRIGGER_DATE:"triggerDate"},
                    required: false,
                    pasteTarget:"triggerDate",
                    visible: true
                },
                {
                    sortNo: 11,
                    name: "triggerDate",
                    text: "Trigger Date",
                    width: 120,
                    dataType: "date"
                },
                {
                    sortNo: 12,
                    name: "description",
                    text: "Description",
                    width: 70,
                    editable: true,
                    datType: "multiline"
                },
                {
                    sortNo: 13,
                    name: "notes",
                    text: "Notes",
                    width: 120,
                    editable: true,
                    dataType: "text"
                },
                {
                    sortNo: 14,
                    name: "useYn",
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
                    sortNo: 15,
                    name: "insertUuid",
                    text: "Created By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 16,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp"
                },
                {
                    sortNo: 17,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 18,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp"
                }
            ]
    }