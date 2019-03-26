var st01503 =
    {
        column_info :
            [
                {
                    sortNo: 0,
                    name: "inventoryRecordResultUuid",
                    text: "UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "nodeType",
                    dataType: "text",
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "code",
                    text: "Code",
                    width: 120,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 3,
                    name: "title",
                    text: "Title",
                    width: 400,
                    required: false,
                    editable: true,
                    dataType: "text",
                    disable : true
                },
                {
                    sortNo: 4,
                    name: "level",
                    text: "Level",
                    width: 110,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 5,
                    name: "type",
                    text: "Type",
                    width: 80,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 6,
                    name: "workerUuid",
                    text: "workerUuid",
                    width: 80,
                    editable: false,
                    dataType: "text",
                    disable: true,
                    visible: false
                },
                {
                    sortNo: 6,
                    name: "workerName",
                    text: "작업자",
                    width: 80,
                    editable: false,
                    dataType: "text",
                    disable: true,
                    visible: true
                },
                {
                    sortNo: 7,
                    name: "inventoryDate",
                    text: "작업일",
                    width: 80,
                    editable: false,
                    dataType: "text",
                    disable: true,
                    visible: true
                },
                {
                    sortNo: 8,
                    width: 150,
                    name: "inventoryResultUuid",
                    text: "결과",
                    editable: false,
                    disable : true,
                    required : true,
                    values: axboot.commonCodeFilter("CD217").codeArr,
                    labels: axboot.commonCodeFilter("CD217").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: 9,
                    width: 150,
                    name: "tagStatusUuid",
                    text: "태그상태",
                    editable: false,
                    disable : true,
                    required : true,
                    values: axboot.commonCodeFilter("CD224").codeArr,
                    labels: axboot.commonCodeFilter("CD224").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: 9,
                    width: 150,
                    name: "recordStatusUuid",
                    text: "기록물 상태",
                    editable: false,
                    disable : true,
                    required : true,
                    values: axboot.commonCodeFilter("CD219").codeArr,
                    labels: axboot.commonCodeFilter("CD219").nameArr,
                    dataType : "combo"
                }

            ]
    }