var rs00403 =
    {
        column_info :
            [
                {
                    sortNo: 0,
                    width:300,
                    name: "title",
                    text: "Title",
                    editable: false,
                    dataType: "text"
                },
                {
                    sortNo: 1,
                    width:400,
                    name: "catPath",
                    text: "PATH",
                    editable: false,
                    dataType: "text"
                },
                {
                    sortNo: 2,
                    width:140,
                    name: "rsCode",
                    text: "RS_CODE",
                    editable: false,
                    dataType: "text",
                    visible : true
                },
                {
                    sortNo: 3,
                    width:140,
                    name: "rsName",
                    text: "RS_NAME",
                    editable: false,
                    dataType: "text",
                    visible : true
                },
                {
                    sortNo: 4,
                    width:140,
                    name: "recordScheduleUuid",
                    text: "RECORD_SCHEDULE_UUID",
                    editable: false,
                    dataType: "text",
                    visible : false
                },
                {
                    sortNo: 5,
                    width:100,
                    name: "retentionPeriodUuid",
                    text: "Retention Period",
                    editable: false,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD133").codeArr,
                    labels: axboot.commonCodeFilter("CD133").nameArr,
                    disable: true
                },
                {
                    sortNo: 6,
                    width:100,
                    name: "disposalTypeUuid",
                    text: "Disposal Type",
                    editable: false,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD135").codeArr,
                    labels: axboot.commonCodeFilter("CD135").nameArr,
                    disable: true
                }
            ]
    }


