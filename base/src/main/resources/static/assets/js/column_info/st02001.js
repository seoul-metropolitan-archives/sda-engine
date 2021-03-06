var st02001 =
    {
        column_info :
            [
                {
                    sortNo: 0,
                    name: "aggregationUuid",
                    text: "aggregationUuid",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false,
                    disable: true
                },
                {
                    sortNo: 0,
                    name: "rfidTagUuid",
                    text: "rfidTagUuid",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false,
                    disable: true
                },
                {
                    sortNo: 1,
                    name: "code",
                    text: "Code",
                    width: 120,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 2,
                    name: "title",
                    text: "Title",
                    width: 350,
                    required: false,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 3,
                    name: "level",
                    text: "Level",
                    width: 80,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 4,
                    name: "type",
                    text: "Type",
                    width: 80,
                    editable: false,
                    dataType: "text",
                    disable: true
                },

                {
                    sortNo: 9,
                    name: "repositoryName",
                    text: "서고",
                    width: 200,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 10,
                    name: "shelfName",
                    text: "서가",
                    width: 200,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 11,
                    name: "locationName",
                    text: "행렬단",
                    width: 200,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 12,
                    width: 80,
                    name: "publishStatusUuid",
                    text: "Publish Status",
                    editable: false,
                    values: axboot.commonCodeFilter("CD220").codeArr,
                    labels: axboot.commonCodeFilter("CD220").nameArr,
                    dataType : "combo",
                    disable : true,
                    required : true
                },
                {
                    sortNo: 13,
                    name: "publishCount",
                    text: "Publish Count",
                    width: 100,
                    editable: false,
                    dataType: "number",
                    disable: true
                },
                {
                    sortNo: 14,
                    name: "publishDate",
                    text: "Publish Date",
                    width: 140,
                    editable: false,
                    dataType: "date",
                    disable: true
                },
            ]
    }
