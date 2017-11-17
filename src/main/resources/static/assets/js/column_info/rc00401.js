var rc00401 =
    {
        column_info :
            [
                {
                    sortNo: 0,
                    width:140,
                    name: "areaUuid",
                    text: "Area",
                    values: axboot.commonCodeFilter("CD125").codeArr,
                    labels: axboot.commonCodeFilter("CD125").nameArr,
                    dataType : "combo",
                    disable : true
                },
                {
                    sortNo: 1,
                    width:140,
                    name: "typeUuid",
                    text: "Type",
                    values: axboot.commonCodeFilter("CD126").codeArr,
                    labels: axboot.commonCodeFilter("CD126").nameArr,
                    dataType : "combo",
                    disable : true,
                },
                {
                    sortNo: 2,
                    width:140,
                    name: "title",
                    text: "Title",
                    dataType: "code",
                    editable:true,
                    disable:false
                },
                {
                    sortNo: 3,
                    width:140,
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
                    width:140,
                    name: "contentsSize",
                    text: "Size(kb)",
                    disable : true,
                    dataType:"commanumber"
                },
                {
                    sortNo: 4,
                    width:140,
                    name: "componentUuid",
                    dataType:"text",
                    visible:false
                }
            ]
    }