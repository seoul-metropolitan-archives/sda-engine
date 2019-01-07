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
                    disable: true,
                    values: axboot.commonCodeFilter("CD134").codeArr,
                    labels: axboot.commonCodeFilter("CD134").nameArr
                },
                {
                    sortNo: 2,
                    width: 80,
                    name: "reCalculationYn",
                    text: "Recalculation",
                    editable: false,
                    dataType: "check",
                    defaultValue : "N",
                    textAlignment: "center",
                    required: false
                },
                {
                    sortNo: 3,
                    width: 80,
                    name: "rsCode",
                    text: "RS Code",
                    disable: true,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: true
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
                    popupCallback: fnObj.gridView01.popupCallback,
                    sqlColumn : {GENERAL_RECORD_SCHEDULE_UUID : "generalRecordScheduleUuid",GRS_CODE : "grsCode",GRS_NAME : "grsName",RETENTION_PERIOD_UUID : "retentionPeriodUuid", DISPOSAL_TYPE_UUID: "disposalTypeUuid" , BASED_ON:"basedOn"},
                    required: false
                },
                {
                    sortNo: 6,
                    name: "grsName",
                    text: "GRS Name",
                    width: 170,
                    disable: true,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 7,
                    name: "retentionPeriodUuid",
                    text: "Retention Period",
                    width: 100,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD133").codeArr,
                    labels: axboot.commonCodeFilter("CD133").nameArr
                },
                {
                    sortNo: 8,
                    name: "disposalTypeUuid",
                    text: "Disposal Type",
                    width: 80,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD135").codeArr,
                    labels: axboot.commonCodeFilter("CD135").nameArr
                },
                {
                    sortNo: 9,
                    name: "basedOn",
                    text: "Based On",
                    width: 200,
                    dataType: "multiline"
                },
                {
                    sortNo: 10,
                    name: "triggerName",
                    text: "Trigger Name",
                    width: 200,
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU131",
                    sqlColumn : {TRIGGER_UUID : "triggerUuid",TRIGGER_NAME : "triggerName",TRIGGER_DATE:"triggerDate"},
                    required: false,
                    visible: true
                },
                {
                    sortNo: 11,
                    name: "triggerDate",
                    text: "Trigger Date",
                    width: 120,
                    disable: true,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 12,
                    name: "description",
                    text: "Description",
                    width: 300,
                    editable: true,
                    dataType: "multiline"
                },
                {
                    sortNo: 13,
                    name: "notes",
                    text: "Notes",
                    width: 300,
                    editable: true,
                    dataType: "text"
                },
                {
                    sortNo: 14,
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
                    text: "Modified By",
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
                },
                {
                    sortNo: 19,
                    name : "generalRecordScheduleUuid",
                    text : "General Record Schedule Uuid",
                    visible : false
                },
                {
                    sortNo: 20,
                    name : "triggerUuid",
                    text : "Trigger Uuid",
                    visible : false
                },
                {
                    sortNo: 21,
                    name : "relatedItems",
                    visible : false
                }
            ]
    }