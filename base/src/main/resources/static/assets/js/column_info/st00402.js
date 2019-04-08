var st00402 =
    {
        column_info:
            [
                {
                    sortNo: 0,
                    name: "arrangeContainersResultUuid",
                    text: "Arrange Containers Result Uuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "statusUuid",
                    text: "Status",
                    width: 120,
                    editable: false,
                    required: false,
                    disable : true,
                    values: axboot.commonCodeFilter("CD138").codeArr,
                    labels: axboot.commonCodeFilter("CD138").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: 2,
                    name: "containerName",
                    text: "Container Name",
                    width: 170,
                    editable: false,
                    dataType: "text",
                    required: true,
                    disable: true
                },
                {
                    sortNo: 3,
                    width: 160,
                    name: "containerTypeUuid",
                    text: "Container Type",
                    values: axboot.commonCodeFilter("CD139").codeArr,
                    labels: axboot.commonCodeFilter("CD139").nameArr,
                    dataType : "combo",
                    required : true
                },
                {
                    sortNo: 4,
                    name: "controlNumber",
                    text: "Control Number",
                    width: 160,
                    editable: false,
                    dataType: "text",
                    required: true,
                    disable: true
                },
                {
                    sortNo: 5,
                    name: "description",
                    text: "Description",
                    width: 250,
                    dataType: "multiline"
                },
                {
                    sortNo: 6,
                    name: "notes",
                    text: "Notes",
                    width: 150,
                    dataType: "text",
                },
                {
                    sortNo: 7,
                    name: "containerTree",
                    text: "Container Tree",
                    width: 160,
                    editable: false,
                    dataType: "text",
                    required: true,
                    disable: true
                },
                {
                    sortNo: 8,
                    name: "arrangementDate",
                    text: "Arrangement Date",
                    width: 200,
                    editable: false,
                    dataType: "text",
                    required: true,
                    disable: true
                },
                {
                    sortNo: 9,
                    name: "insertUuid",
                    text: "Created By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 10,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 220,
                    editable: false,
                    disable: true,
                    dataType: "timestamp"
                },
                {
                    sortNo: 11,
                    name: "updateUuid",
                    text: "Modified By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 12,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 220,
                    editable: false,
                    disable: true,
                    dataType: "timestamp"
                },
                {
                    sortNo: 13,
                    width:140,
                    name: "orderKey1",
                    text: "ORDER_KEY",
                    editable: false,
                    dataType: "code",
                    visible : false
                },
                {
                    sortNo: 14,
                    width:140,
                    name: "choiceYn",
                    text: "choiceYn",
                    editable: false,
                    dataType: "code",
                    visible : false
                },
                {
                    sortNo: 15,
                    name: "parentContainerUuid",
                    text: "parentContainerUuid",
                    width: 120,
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
            ]
    }