var rs00101 =
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
                    name: "generalRecordScheduleUuid",
                    text: "General Record Schedule UUID",
                    editable: false,
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
                    dataType: "code",
                    values: axboot.commonCodeFilter("CD134").codeArr,
                    labels: axboot.commonCodeFilter("CD134").nameArr,
                    required: false
                },
                {
                    sortNo: 2,
                    width: 120,
                    name: "grsCode",
                    text: "GRS Code",
                    editable: false,
                    dataType : "combo",
                    required: true
                },
                {
                    sortNo: 3,
                    width: 150,
                    name: "grsName",
                    text: "GRS Name",
                    editable: true,
                    dataType:"text",
                    required: true
                },
                {
                    sortNo: 4,
                    name: "retentionPeriodUuid",
                    text: "Retention Period",
                    width: 150,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD133").codeArr,
                    labels: axboot.commonCodeFilter("CD133").nameArr,
                    editable: true,
                    required: true
                },
                {
                    sortNo: 5,
                    name: "disposalTypeUuid",
                    text: "Disposal Type",
                    width: 150,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD135").codeArr,
                    labels: axboot.commonCodeFilter("CD135").nameArr,
                    editable: true,
                    required: true
                },
                {
                    sortNo: 6,
                    name: "basedOn",
                    text: "Based On",
                    width: 150,
                    dataType: "multiline",
                    editable: true,
                    required: true
                },
                {
                    sortNo: 7,
                    name: "triggerYN",
                    text: "Trigger",
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
                    sortNo: 8,
                    name: "description",
                    text: "Description",
                    width: 70,
                    editable: true,
                    datType: "multiline"
                },
                {
                    sortNo: 9,
                    name: "notes",
                    text: "Notes",
                    width: 120,
                    editable: true,
                    dataType: "text"
                },
                {
                    sortNo: 10,
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
                    sortNo: 11,
                    name: "insertUuid",
                    text: "Created By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 12,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp"
                },
                {
                    sortNo: 13,
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