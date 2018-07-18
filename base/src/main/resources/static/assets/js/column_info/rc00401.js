var rc00401 =
    {
        column_info :
            [
                {
                    sortNo: 0,
                    width:100,
                    name: "areaUuid",
                    text: "Area",
                    values: axboot.commonCodeFilter("CD125").codeArr,
                    labels: axboot.commonCodeFilter("CD125").nameArr,
                    dataType : "combo",
                    disable : true
                },
                {
                    sortNo: 1,
                    width:120,
                    name: "typeUuid",
                    text: "Type",
                    values: axboot.commonCodeFilter("CD126").codeArr,
                    labels: axboot.commonCodeFilter("CD126").nameArr,
                    dataType : "combo",
                    disable : true,
                },
                {
                    sortNo: 2,
                    width:600,
                    name: "title",
                    text: "Title",
                    dataType: "code",
                    editable:true,
                    disable:false
                },
                {
                    sortNo: 3,
                    width:90,
                    name: "openStatusUuid",
                    text: "Open Status",
                    values: axboot.commonCodeFilter("CD123").codeArr,
                    labels: axboot.commonCodeFilter("CD123").nameArr,
                    dataType : "combo",
                    editable:true,
                    disable:false
                },
                {
                    sortNo: 4,
                    width:80,
                    name: "contentsSize",
                    text: "Size(kb)",
                    disable : true,
                    dataType:"commanumber",
                    textAlignment: "far",

                },
                {
                    sortNo: 4,
                    width:140,
                    name: "componentUuid",
                    dataType:"text",
                    visible:false,
                }
            ]
    }