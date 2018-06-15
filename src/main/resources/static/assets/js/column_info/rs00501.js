var rs00501 =
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
                    name: "recordScheduleResultUuid",
                    text: "Record Schedule Result UUID",
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "recordScheduleUuid",
                    text: "Record Schedule UUID",
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    width:50,
                    name: "statusUuid",
                    text: "Status",
                    editable: false,
                    dataType: "code",
                    values: axboot.commonCodeFilter("CD137").codeArr,
                    labels: axboot.commonCodeFilter("CD137").nameArr,
                    required: true
                },
                {
                    sortNo: 3,
                    width: 150,
                    name: "aggregationTree",
                    text: "Aggregation Tree",
                    editable: false,
                    dataType:"text",
                    required: false
                },
                {
                    sortNo: 4,
                    width: 150,
                    name: "itemTitle",
                    text: "Item Title",
                    editable: false,
                    dataType:"text",
                    required: false
                },
                {
                    sortNo: 5,
                    name: "type",
                    text: "Type",
                    width: 150,
                    dataType: "text",
                    editable: true,
                    required: true
                },
                {
                    sortNo: 6,
                    name: "disposalType",
                    text: "Disposal Type",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    values: axboot.commonCodeFilter("CD134").codeArr,
                    labels: axboot.commonCodeFilter("CD134").nameArr,
                    required: true
                },
                {
                    sortNo: 7,
                    name: "initialDate",
                    text: "Initial Date",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "date"
                },
                {
                    sortNo: 8,
                    name: "disposalDueDate",
                    text: "Disposal Due Date",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "date"
                },
                {
                    sortNo: 9,
                    name: "disposalConfirmDate",
                    text: "Disposal Confirm Date",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "date"
                },
                {
                    sortNo: 10,
                    name: "disposalConfirmReason",
                    text: "Disposal Confirm Reason",
                    width: 150,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 11,
                    name: "disposalCompleteDateTime",
                    text: "Disposal Complete Date/Time",
                    width: 200,
                    editable: false,
                    disable: true,
                    dataType: "timestamp"
                },
                {
                    sortNo: 12,
                    name: "description",
                    text: "Description",
                    width: 200,
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
                }
            ]
    }