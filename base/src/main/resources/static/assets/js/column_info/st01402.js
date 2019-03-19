var st01402 =
    {
        column_info :
            [

                {
                    sortNo: 0,
                    name: "inoutDateTime",
                    text: "출입 일시",
                    width: 140,
                    editable: false,
                    dataType: "date",
                    disable: true
                },
                {
                    sortNo: 2,
                    name: "gateName",
                    text: "게이트",
                    width: 250,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 3,
                    name: "inoutTypeUuid",
                    text: "출입구분",
                    width: 250,
                    values: axboot.commonCodeFilter("CD222").codeArr,
                    labels: axboot.commonCodeFilter("CD222").nameArr,
                    editable: false,
                    dataType : "combo",
                    disable: true
                },
                {
                    sortNo: 4,
                    name: "withoutNoticeYn",
                    text: "무단여부",
                    width: 250,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 5,
                    name: "disposerName",
                    text: "조치자",
                    width: 250,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 6,
                    name: "reason",
                    text: "REASON",
                    width: 250,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
            ]
    }
