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
                    name: "statusUuid",
                    text: "Status",
                    editable: false,
                    dataType: "code",
                    values: axboot.commonCodeFilter("CD134").codeArr,
                    labels: axboot.commonCodeFilter("CD134").nameArr,
                    required: true
                },
                {
                    sortNo: 3,
                    name: "recordSchedule",
                    text: "Record Schedule",
                    editable: false,
                    width : 400,
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
                        name: "recordScheduleCode",
                        text: "RS Code",
                        width : 150,
                        editable: false,
                        dataType: "text",
                        visible: true,
                        disable: true
                    },
                        {
                            sortNo: 1,
                            name: "recordScheduleName",
                            text: "RS Name",
                            width : 150,
                            editable: false,
                            dataType: "text",
                            visible: true,
                            disable: true
                        },
                        {
                            sortNo: 2,
                            name: "retentionPeriod",
                            text: "Retention Period",
                            width : 200,
                            editable: false,
                            dataType: "combo",
                            visible: true,
                            disable: true
                        },
                        {
                            sortNo: 3,
                            name: "disposalType",
                            text: "Disposal Type",
                            width : 180,
                            editable: false,
                            dataType: "combo",
                            visible: true,
                            disable: true
                        },
                        {
                            sortNo: 4,
                            name: "disposalFreezeYn",
                            text: "Disposal Freeze",
                            width : 220,
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
                    width : 310,
                    header :{styles:{
                            background: "linear,#f2f2f2",
                            fontSize: 12,
                            fontFamily: "nanum",
                            foreground: "#000000",
                            borderRight: "#cccccc, 1",
                            borderBottom: "#cccccc, 1",
                            fontBold: false
                        }},
                    columnList: [
                        {
                            sortNo: 6,
                            name: "aggregationTree",
                            text: "Aggregation Tree",
                            width : 220,
                            editable: false,
                            dataType: "text",
                            visible: true,
                            disable: true
                        },
                        {
                            sortNo: 7,
                            name: "itemCode",
                            text: "Item Code",
                            width : 150,
                            editable: false,
                            dataType: "text",
                            visible: true,
                            disable: true
                        },
                        {
                            sortNo: 8,
                            name: "title",
                            text: "Title",
                            width : 100,
                            editable: false,
                            dataType: "text",
                            visible: true,
                            disable: true
                        },
                        {
                            sortNo: 9,
                            name: "initialDate",
                            text: "Initial Date",
                            width : 100,
                            editable: false,
                            dataType: "text",
                            visible: true,
                            disable: true
                        },
                        {
                            sortNo: 10,
                            name: "disposalDueDate",
                            text: "Disposal Due Date",
                            width: 220,
                            editable: false,
                            dataType: "text",
                            visible: true,
                            disable: true
                        }
                    ]
                },
                {
                    sortNo: 10,
                    name: "type",
                    text: "Type",
                    width: 50,
                    editable: false,
                    dataType: "text",
                    visible: true,
                    disable: true
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
                    sortNo: 13,
                    name: "useYn",
                    text: "Use",
                    width: 50,
                    editable: true,
                    dataType: "check",
                    textAlignment: "center",
                    defaultValue : "Y",
                    required: false
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
                }
            ]
    }