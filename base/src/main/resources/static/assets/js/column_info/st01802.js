var st01802 =
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
                    sortNo: 1,
                    name: "seq",
                    text: "순번",
                    width: 120,
                    editable: false,
                    dataType: "number",
                    visible: false,
                    disable: true
                },
                {
                    sortNo: 2,
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
                    sortNo: 3,
                    width: 180,
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
                    sortNo: 4,
                    name: "publishDate",
                    text: "Publish Date",
                    width: 180,
                    editable: false,
                    dataType: "date",
                    disable: true
                },
                {
                    sortNo: 5,
                    name: "tag",
                    text: "Tag",
                    width: 250,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
            ]
    }
