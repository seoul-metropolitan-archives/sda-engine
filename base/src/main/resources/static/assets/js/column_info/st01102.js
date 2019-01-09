var st01102 =
    {
        column_info:
            [
                {
                    sortNo: 0,
                    name: "takeoutRequestUuid",
                    text: "takeoutRequestUuid",
                    width: 120,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    width: 80,
                    name: "requestName",
                    text: "반출의뢰서",
                    editable: false,
                    disable : false,
                    required : false,
                    dataType : "text"
                },
                {
                    sortNo: 2,
                    width: 80,
                    name: "requestName",
                    text: "반출자",
                    editable: false,
                    disable : false,
                    required : false,
                    dataType : "text"
                },
                {
                    sortNo: 3,
                    width: 80,
                    name: "requestName",
                    text: "소속",
                    editable: false,
                    disable : false,
                    required : false,
                    dataType : "text"
                },
                {
                    sortNo: 4,
                    name: "takeoutDate",
                    text: "반출일자",
                    width: 100,
                    editable: false,
                    dataType : "date",
                    required : true,
                    styles: {
                        "datetimeFormat": "yyyy.MM.dd"
                    }
                },

                {
                    sortNo: 5,
                    name: "returnDueDate",
                    text: "반입예정일",
                    width: 100,
                    editable: false,
                    dataType : "date",
                    required : false,
                    styles: {
                        "datetimeFormat": "yyyy.MM.dd"
                    }
                },
                {
                    sortNo: 6,
                    width: 80,
                    name: "statusUuid",
                    text: "Status",
                    editable: false,
                    disable : true,
                    required : false,
                    values: axboot.commonCodeFilter("CD208").codeArr,
                    labels: axboot.commonCodeFilter("CD208").nameArr,
                    dataType : "combo"
                },
            ]
    }