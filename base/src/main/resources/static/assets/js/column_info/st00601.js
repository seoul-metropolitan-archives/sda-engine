var cl00302 =
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
                    name: "classUuid",
                    text: "Class UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    width:200,
                    name: "title",
                    text: "Title",
                    editable: false,
                    dataType: "code",
                    required: false,
                },
                {
                    sortNo: 2,
                    width: 120,
                    name: "statusUuid",
                    text: "Status",
                    editable: false,
                    values: axboot.commonCodeFilter("CD111").codeArr,
                    labels: axboot.commonCodeFilter("CD111").nameArr,
                    dataType : "combo",
                    disable:true
                },
                {
                    sortNo: 5,
                    name: "classifiedDate",
                    text: "Classified Date",
                    width: 80,
                    dataType: "text",
                    editable: false,
                    sortable: true,
                    disable:true
                },
                {
                    sortNo: 8,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 9,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 10,
                    name: "updateUuid",
                    text: "Modified By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 11,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 12,
                    width:30,
                    name: "classifyRecordsUuid",
                    text: "Classify Records UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 13,
                    width:30,
                    name: "aggregationUuid",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 14,
                    width:30,
                    name: "itemUuid",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 15,
                    width:30,
                    name: "changeStatus",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 16,
                    width:140,
                    name: "orderKey1",
                    text: "ORDER_KEY",
                    editable: false,
                    dataType: "code",
                    visible : false
                },
                {
                    sortNo: 17,
                    width:140,
                    name: "choiceYn",
                    text: "choiceYn",
                    editable: false,
                    dataType: "code",
                    visible : false
                }
            ]
    }