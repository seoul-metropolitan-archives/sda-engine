var st01901 =
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
                    name: "rfidTagRepublishUuid",
                    text: "rfidTagRepublishUuid",
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
                    width: 400,
                    required: false,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 3,
                    name: "level",
                    text: "Level",
                    width: 70,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 4,
                    name: "type",
                    text: "Type",
                    width: 70,
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
                    width: 180,
                    name: "publishSourceTypeUuid",
                    text: "Publish Source Type",
                    editable: false,
                    values: axboot.commonCodeFilter("CD221").codeArr,
                    labels: axboot.commonCodeFilter("CD221").nameArr,
                    dataType : "combo",
                    disable : true,
                    required : true
                },
                {
                    sortNo: 13,
                    name: "requestDate",
                    text: "Request Date",
                    width: 140,
                    editable: false,
                    dataType: "date",
                    disable: true
                },
                {
                    sortNo: 14,
                    name: "republishYn",
                    text: "Republish YN",
                    width: 80,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 15,
                    name: "republishDate",
                    text: "Republish Date",
                    width: 140,
                    editable: false,
                    dataType: "date",
                    disable: true
                },

            ]
    }
