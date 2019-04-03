var st01501 =
    {
        column_info:
            [
                {
                    sortNo: 0,
                    name: "inventoryPlanUuid",
                    text: "InventoryPlan UUID",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "planName",
                    text: "계획서명",
                    width: 250,
                    editable: true,
                    dataType: "text",
                    required: true
                },
                {
                    sortNo: 2,
                    name: "plannerName",
                    text: "담당자",
                    width: 80,
                    editable: true,
                    dataType: "popup",
                    //popupCode : "PU107",
                    //sqlColumn : {USER_UUID : "plannerUuid", USER_NAME : "plannerName"},
                    required: true,
                    visible: true
                },
                {
                    sortNo: 3,
                    name: "plannerUuid",
                    text: "plannerUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: true,
                    visible: false
                },
                {
                    sortNo: 4,
                    name: "exceptStartDate",
                    text: "시작 예정일",
                    width: 100,
                    editable: true,
                    dataType : "date",
                    required : true,
                    styles: {
                        "datetimeFormat": "yyyy.MM.dd"
                    }
                },
                {
                    sortNo: 5,
                    name: "exceptEndDate",
                    text: "종료 예정일",
                    width: 100,
                    editable: true,
                    dataType : "date",
                    required : true,
                    styles: {
                        "datetimeFormat": "yyyy.MM.dd"
                    }
                },
                {
                    sortNo: 6,
                    name: "repositoryName",
                    text: "서고",
                    width: 120,
                    editable: true,
                    dataType: "popup",
                    /*popupCode : "PU137",
                    popupCallback : fnObj.gridView01.popupCallback,
                    sqlColumn : {REPOSITORY_UUID : "repositoryUuid", REPOSITORY_NAME : "repositoryName"},*/
                    required: true,
                    visible: true
                },
                {
                    sortNo: 7,
                    name: "repositoryUuid",
                    text: "repositoryUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: true,
                    visible: false
                },
                {
                    sortNo: 8,
                    name: "shelfName",
                    text: "서가",
                    width: 120,
                    editable: true,
                    dataType: "popup",
                    /*popupCode : "PU138",
                    sqlColumn : {SHELF_UUID : "shelfUuid", SHELF_NAME : "shelfName"},*/
                    required: true,
                    visible: true,
                    preSearch  :true
                },
                {
                    sortNo: 9,
                    name: "shelfUuid",
                    text: "shelfUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: true,
                    visible: false
                },
                {
                    sortNo: 8,
                    name: "locationName",
                    text: "행렬단",
                    width: 120,
                    editable: true,
                    dataType: "popup",
                    /*popupCode : "PU147",
                    sqlColumn : {LOCATION_UUID : "locationUuid", LOCATION_NAME : "locationName"},*/
                    required: false,
                    visible: true,
                    preSearch  :true
                },
                {
                    sortNo: 9,
                    name: "locationUuid",
                    text: "locationUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 10,
                    width: 80,
                    name: "statusUuid",
                    text: "상태",
                    editable: false,
                    disable : true,
                    required : true,
                    values: axboot.commonCodeFilter("CD216").codeArr,
                    labels: axboot.commonCodeFilter("CD216").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: 11,
                    width: 80,
                    name: "planResultUuid",
                    text: "결과",
                    editable: true,
                    disable : false,
                    required: true,
                    visible: true,
                    values: axboot.commonCodeFilter("CD217").codeArr,
                    labels: axboot.commonCodeFilter("CD217").nameArr,
                    dataType : "combo",
                    defaultValue : '904EC536-A5BA-47B8-A3C4-765BCE980AFB'
                },
                {
                    sortNo: 12,
                    name: "notes",
                    text: "비고",
                    width: 150,
                    editable: true,
                    dataType: "text",
                    required: false
                },
            ]
    }