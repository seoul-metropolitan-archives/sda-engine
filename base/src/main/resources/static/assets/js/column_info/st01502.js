var st01502 =
    {
        column_info:
            [
                {
                    sortNo: 0,
                    name: "inventoryContResultUuid",
                    text: "inventoryContResult UUID",
                    width: 120,
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "inventoryPlanUuid",
                    text: "inventoryPlan UUID",
                    width: 120,
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "containerUuid",
                    text: "container UUID",
                    width: 120,
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 3,
                    name: "containerName",
                    text: "containerName",
                    width: 120,
                    editable: false,
                    dataType: "text"
                },
                {
                    sortNo: 4,
                    name: "parentContainerName",
                    text: "parentContainerName",
                    width: 120,
                    editable: false,
                    dataType: "text"
                },
                {
                    sortNo: 5,
                    width: 80,
                    name: "containerTypeUuid",
                    text: "containerTypeUuid",
                    editable: false,
                    disable : true,
                    required : true,
                    values: axboot.commonCodeFilter("CD139").codeArr,
                    labels: axboot.commonCodeFilter("CD139").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: 6,
                    name: "controlNumber",
                    text: "controlNumber",
                    width: 120,
                    editable: false,
                    dataType: "text"
                },
                {
                    sortNo: 7,
                    width: 80,
                    name: "inventoryResultUuid",
                    text: "결과",
                    editable: false,
                    disable : true,
                    required : true,
                    values: axboot.commonCodeFilter("CD217").codeArr,
                    labels: axboot.commonCodeFilter("CD217").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: 8,
                    width: 80,
                    name: "containerStatusUuid",
                    text: "container 상태",
                    editable: false,
                    disable : true,
                    required : true,
                    values: axboot.commonCodeFilter("CD218").codeArr,
                    labels: axboot.commonCodeFilter("CD218").nameArr,
                    dataType : "combo"
                }
            ]
    }