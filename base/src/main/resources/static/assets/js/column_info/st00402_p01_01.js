var st00402_p01_01 =
    {
        column_info:
            [
                {
                    sortNo: 0,
                    name: "arrangeContainersResultUuid",
                    text: "Arrange Containers Result Uuid",
                    width: 120,
                    editable: false,
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
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "containerName",
                    text: "Container Name",
                    width: 120,
                    editable: false,
                    dataType: "text",
                    required: true,
                    disable: false
                },
                {
                    sortNo: 3,
                    width: 200,
                    name: "parentContainerName",
                    text: "Parent Container Name",
                    editable: false,
                    dataType: "text",
                    disable : false,
                    required: false
                },
                {
                    sortNo: 4,
                    name: "containerTypeUuid",
                    text: "Container Type",
                    width: 120,
                    editable: false,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD139").codeArr,
                    labels: axboot.commonCodeFilter("CD139").nameArr,
                    disable: true
                },
                {
                    sortNo: 5,
                    name: "controlNumber",
                    text: "Control Number",
                    width: 120,
                    editable: false,
                    dataType: "text",
                    required: true,
                    disable: true
                },
                {
                    sortNo: 6,
                    name: "description",
                    text: "Description",
                    width: 250,
                    editable: false,
                    dataType: "multiline"
                },
                {
                    sortNo: 7,
                    name: "notes",
                    text: "Notes",
                    width: 150,
                    editable: false,
                    dataType: "text"
                },
                {
                    sortNo: 8,
                    name: "locationUuid",
                    text: "locationUuid",
                    width: 100,
                    editable: false,
                    visible: false,
                    dataType: "text"
                },
                {
                    sortNo: 9,
                    name: "containerUuid",
                    text: "containerUuid",
                    width: 100,
                    editable: false,
                    visible: false,
                    dataType: "text"
                }

            ]
    }