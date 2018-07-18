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
                    name: "disposalStatus",
                    text: "Status",
                    editable: false,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD137").codeArr,
                    labels: axboot.commonCodeFilter("CD137").nameArr,
                    disable: true
                },
                {
                    sortNo: 3,
                    width: 250,
                    name: "aggregationTree",
                    text: "Aggregation Tree",
                    editable: false,
                    dataType:"text",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 4,
                    width: 250,
                    name: "itemTitle",
                    text: "Item Title",
                    editable: false,
                    dataType:"text",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 5,
                    name: "itemTypeUuid",
                    text: "Type",
                    width: 50,
                    editable: false,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD136").codeArr,
                    labels: axboot.commonCodeFilter("CD136").nameArr,
                    disable: true
                },
                {
                    sortNo: 6,
                    name: "disposalTypeUuid",
                    text: "Disposal Type",
                    width: 80,
                    editable: false,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD135").codeArr,
                    labels: axboot.commonCodeFilter("CD135").nameArr,
                    required: false,
                    disable: true
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
                    dataType: "date",
                    format:'yyyy-mm-dd',
                    required:true
                },
                {
                    sortNo: 10,
                    name: "disposalConfirmReason",
                    text: "Disposal Confirm Reason",
                    width: 150,
                    editable: true,
                    dataType: "multiline",
                    required:true
                },
                {
                    sortNo: 11,
                    name: "disposalCompleteDate",
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
                    editable: false,
                    disable: true,
                    datType: "multiline"
                },
                {
                    sortNo: 13,
                    name: "notes",
                    text: "Notes",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 14,
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
                    sortNo: 15,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 16,
                    name: "updateUuid",
                    text: "Modified By",
                    width: 80,
                    visible: true,
                    dataType: "text",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 17,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 18,
                    name: "itemUuid",
                    text: "Item UUID",
                    width: 140,
                    required: false,
                    visible: false
                },
                {
                    sortNo: 18,
                    name: "statusUuid",
                    text: "statusUuid",
                    width: 140,
                    required: false,
                    visible: false
                }
            ]
    }