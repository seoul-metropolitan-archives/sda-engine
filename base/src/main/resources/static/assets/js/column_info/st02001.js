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
                    visible: false
                },
                {
                    sortNo: 0,
                    name: "rfidTagUuid",
                    text: "rfidTagUuid",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
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
                    width: 200,
                    required: true,
                    editable: true,
                    dataType: "text"
                },
                {
                    sortNo: 3,
                    name: "level",
                    text: "Level",
                    width: 110,
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
                    sortNo: 5,
                    name: "publishedStatus",
                    text: "Published Status",
                    width: 100,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 6,
                    name: "author",
                    text: "Author",
                    width: 140,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 7,
                    name: "descStrDate",
                    text: "Start Date",
                    width: 140,
                    editable: false,
                    dataType: "date",
                    disable: true
                },
                {
                    sortNo: 8,
                    name: "descEdDate",
                    text: "End Date",
                    width: 140,
                    editable: false,
                    dataType: "date",
                    disable: true
                },
                {
                    sortNo: 9,
                    name: "repositoryName",
                    text: "서고",
                    width: 250,
                    editable: false,
                    dataType: "text"
                },
                {
                    sortNo: 10,
                    name: "shelfName",
                    text: "서가",
                    width: 250,
                    editable: false,
                    dataType: "text"
                },
                {
                    sortNo: 11,
                    name: "locationName",
                    text: "행렬단",
                    width: 250,
                    editable: false,
                    dataType: "text"
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
                    width: 70,
                    editable: false,
                    dataType: "number"
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
