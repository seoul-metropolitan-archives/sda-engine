var st00602 =
    {

        column_info:
            [
                {
                    sortNo: 0,
                    name: "itemUuid",
                    text: "itemUuid",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false,
                    disable:true
                },
                {
                    sortNo: 2,
                    name: "code",
                    text: "Code",
                    width: 130,
                    editable: false,
                    dataType: "text",
                    disable:true
                },
                {
                    sortNo: 3,
                    name: "title",
                    text: "Title",
                    width: 500,
                    editable: false,
                    dataType: "text",
                    disable:true
                },
                {
                    sortNo: 4,
                    name: "level",
                    text: "Level",
                    width: 110,
                    editable: false,
                    dataType: "text",
                    disable:true
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
                    name: "publishedStatus",
                    text: "Published Status",
                    width: 100,
                    editable: false,
                    dataType: "text",
                    disable:true
                },
                {
                    sortNo: 7,
                    name: "author",
                    text: "Author",
                    width: 100,
                    editable: false,
                    dataType: "text",
                    disable:true
                },
                {
                    sortNo: 8,
                    name: "startDate",
                    text: "Start Date",
                    width: 120,
                    dataType: "timestamp",
                    editable: false,
                    sortable: true,
                    disable:true
                },
                {
                    sortNo: 9,
                    name: "endDate",
                    text: "End Date",
                    width: 120,
                    dataType: "timestamp",
                    editable: false,
                    sortable: true,
                    disable:true
                },

            ]
    }

