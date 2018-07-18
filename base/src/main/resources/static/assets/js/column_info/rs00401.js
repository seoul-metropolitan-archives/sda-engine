var rs00401 =
    {
        column_info :
            [
                {
                    sortNo: 0,
                    name: "recordScheduleResultUuid",
                    text: "Record Schedule Result UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "recordScheduleUuid",
                    text: "Record Schedule UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    width:50,
                    name: "statusUuid",
                    text: "Status",
                    editable: false,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD137").codeArr,
                    labels: axboot.commonCodeFilter("CD137").nameArr,
                    disable: true
                },
                {
                    sortNo: 3,
                    name: "recordSchedule",
                    text: "Record Schedule",
                    editable: false,
                    width : 700,
                    dataType: "text",
                    header :{styles:{
                            background: "linear,#f2f2f2",
                            fontSize: 12,
                            fontFamily: "nanum",
                            foreground: "#000000",
                            borderRight: "#cccccc, 1",
                            borderBottom: "#cccccc, 1",
                            fontBold: false
                        }},
                        columnList : [{
                            sortNo: 0,
                            name: "rsCode",
                            text: "RS Code",
                            width : 80,
                            dataType: "popup",
                            popupCode : "PU140",
                            sqlColumn : {RS_NAME :"rsName", RS_CODE:"rsCode" ,RETENTION_PERIOD_NM: "retentionPeriodNm", DISPOSAL_TYPE_NM: "disposalTypeNm", RETENTION_PERIOD_NM: "retentionPeriodUuid", DISPOSAL_TYPE_NM: "disposalTypeUuid"},
                            disable : false,
                            required:true
                        },
                        {
                            sortNo: 1,
                            name: "rsName",
                            text: "RS Name",
                            width : 250,
                            editable: false,
                            dataType: "text",
                            visible: true,
                            disable: true
                        },
                        {
                            sortNo: 2,
                            name: "retentionPeriodUuid",
                            text: "Retention Period",
                            width : 90,
                            editable: false,
                            dataType: "combo",
                            textAlignment:"center",
                            values: axboot.commonCodeFilter("CD133").codeArr,
                            labels: axboot.commonCodeFilter("CD133").nameArr,
                            disable: true
                        },
                        {
                            sortNo: 3,
                            name: "disposalTypeUuid",
                            text: "Disposal Type",
                            width : 90,
                            editable: false,
                            dataType: "combo",
                            textAlignment:"center",
                            values: axboot.commonCodeFilter("CD135").codeArr,
                            labels: axboot.commonCodeFilter("CD135").nameArr,
                            disable: true
                        },
                        {
                            sortNo: 4,
                            name: "disposalDueDate",
                            text: "Disposal Due Date",
                            width: 100,
                            editable: false,
                            dataType: "text",
                            visible: true,
                            disable: true
                        },
                        {
                            sortNo: 5,
                            name: "disposalFreeze",
                            text: "Disposal Freeze",
                            width : 90,
                            editable: false,
                            dataType: "check",
                            visible: true,
                            disable: true
                        }]
                },
                {
                    sortNo: 4,
                    name: "item",
                    text: "Item",
                    editable: false,
                    dataType: "text",
                    width : 620,
                    header :{styles:{
                            background: "linear,#f2f2f2",
                            fontSize: 12,
                            fontFamily: "nanum",
                            foreground: "#000000",
                            borderRight: "#cccccc, 1",
                            borderBottom: "#cccccc, 1",
                        }},
                    columnList: [
                        {
                            sortNo: 6,
                            name: "aggregationTree",
                            text: "Aggregation Tree",
                            width : 250,
                            editable: false,
                            dataType: "text",
                            visible: true,
                            disable: true
                        },
                        {
                            sortNo: 7,
                            name: "itemCode",
                            text: "Item Code",
                            width : 120,
                            editable: false,
                            dataType: "text",
                            visible: true,
                            disable: true
                        },
                        {
                            sortNo: 8,
                            name: "itemTitle",
                            text: "Title",
                            width : 200,
                            editable: false,
                            dataType: "text",
                            visible: true,
                            disable: true
                        },
                        {
                            sortNo: 9,
                            name: "itemTypeUuid",
                            text: "Type",
                            width: 50,
                            editable: false,
                            dataType: "combo",
                            values: axboot.commonCodeFilter("CD136").codeArr,
                            labels: axboot.commonCodeFilter("CD136").nameArr,
                            disable: true
                        }
                        // {
                        //     sortNo: 9,
                        //     name: "initialDate",
                        //     text: "Initial Date",
                        //     width : 100,
                        //     editable: false,
                        //     dataType: "text",
                        //     visible: true,
                        //     disable: true
                        // },
                        // {
                        //     sortNo: 10,
                        //     name: "disposalDueDate",
                        //     text: "Disposal Due Date",
                        //     width: 100,
                        //     editable: false,
                        //     dataType: "text",
                        //     visible: true,
                        //     disable: true
                        // }
                    ]
                },
                {
                    sortNo: 11,
                    name: "description",
                    text: "Description",
                    width: 250,
                    editable: true,
                    dataType: "text"
                },
                {
                    sortNo: 12,
                    name: "notes",
                    text: "Notes",
                    width: 250,
                    editable: true,
                    dataType: "text"
                },
                {
                    sortNo: 14,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    dataType: "text",
                    editable : false,
                    readonly : true,
                    visible: true,
                    required: false,
                    disable: true
                },
                {
                    sortNo: 15,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 16,
                    name: "updateUuid",
                    text: "Modified By",
                    width: 80,
                    visible: true,
                    dataType: "text",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 17,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 19,
                    name: "itemUuid",
                    text: "Item Uuid",
                    visible: false
                },
                {
                    sortNo: 20,
                    name: "disposalStatus",
                    text: "Disposal Status",
                    visible: false
                }
            ]
    }