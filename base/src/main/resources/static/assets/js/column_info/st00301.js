var st00301 =
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
                    width:30,
                    name: "arrangeRecordsResultUuid",
                    text: "Arrange Records Result Uuid",
                    editable: false,
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
                    values: axboot.commonCodeFilter("CD138").codeArr,
                    labels: axboot.commonCodeFilter("CD138").nameArr,
                    dataType : "combo",
                    disable: true,
                    required : false
                },
                {
                    sortNo: 2,
                    width: 150,
                    name: "aggregationCode",
                    text: "Aggregation Code",
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false,
                },
                {
                    sortNo: 3,
                    width: 200,
                    name: "title",
                    text: "Title",
                    disable: true,
                    editable: false,
                    dataType: "text",
                    required: false,

                },
                {
                    sortNo: 5,
                    width: 80,
                    name: "typeUuid",
                    text: "Type",
                    disable: true,
                    values: axboot.commonCodeFilter("CD127").codeArr,
                    labels: axboot.commonCodeFilter("CD127").nameArr,
                    dataType : "combo",
                    editable : false
                },
                {
                    sortNo: 6,
                    width: 200,
                    name: "containerName",
                    text: "Container Name",
                    disable: true,
                    editable: false,
                    dataType: "text",
                    required: false,
                },
                {
                    sortNo: 7,
                    name: "description",
                    text: "Description",
                    width: 250,
                    editable: true,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 8,
                    name: "descStrDate",
                    text: "Start Date",
                    width: 100,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 9,
                    name: "descEdDate",
                    text: "End Date",
                    width: 100,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 10,
                    name: "notes",
                    text: "Notes",
                    width: 250,
                    editable: true,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 11,
                    name: "aggregationTree",
                    text: "Aggregation Tree",
                    width: 500,
                    disable: true,
                    type: "text",
                    editable: false
                },
                {
                    sortNo: 12,
                    name: "arrangedDate",
                    text: "Arranged Date",
                    width: 100,
                    disable: true,
                    type: "text",
                    editable: false
                },
                {
                    sortNo: 13,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    disable: true,
                    editable: false,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 14,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    disable: true,
                    editable: false,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 15,
                    name: "updateUuid",
                    text: "Modified By",
                    width: 80,
                    disable: true,
                    editable: false,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 16,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    disable: true,
                    editable: false,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 17,
                    width:30,
                    name: "containerUuid",
                    text: "Container UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 18,
                    width:30,
                    name: "aggregationUuid",
                    text: "Aggregation UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 19,
                    width:30,
                    name: "itemUuid",
                    text: "Item UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                }
            ]
    }