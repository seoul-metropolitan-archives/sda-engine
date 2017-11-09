/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var wf00401 =
    {
        column_info:
            [
                {
                    sortNo: 1,
                    name: "workflowUuid",
                    text: "workflowUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "workflowName",
                    text: "Workflow Name",
                    width: 150,
                    editable: false,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 3,
                    name: "serviceUuid",
                    text: "Service",
                    width: 100,
                    editable: true,
                    dataType: "combo",
                    required: true,
                    visible: true,
                    values: axboot.commonCodeFilter("CD006").codeArr,
                    labels: axboot.commonCodeFilter("CD006").nameArr,
                    lookupDisplay: true,
                    editor: {
                        type: "dropDown",
                        dropDownCount: 10,
                        domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                        textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
                    },
                    visible: false
                },
                {
                    sortNo: 4,
                    name: "description",
                    text: "Description",
                    width: 250,
                    editable: false,
                    dataType: "richtext",
                    required: false,
                },
                {
                    sortNo: 5,
                    name: "notes",
                    text: "Notes",
                    width: 250,
                    editable: false,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 6,
                    name: "",
                    text: "",
                    width: 100,
                    editable: true,
                    renderer: {
                        type: "imageButton",
                        text: "Run",
                        imageUrl: "/assets/images/ams/btn_run_normal.png",
                        hoverUrl: "/assets/images/ams/btn_run_hover.png",
                        activeUrl: "/assets/images/ams/btn_run_hover.png"
                    }
                },
                {
                    sortNo: 7,
                    name: "useYn",
                    text: "Use",
                    width: 50,
                    editable: true,
                    dataType: "check",
                    textAlignment: "center",
                    required: true,
                    visible: false
                },
                {
                    sortNo: 8,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 9,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 10,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 11,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false,
                    visible: false
                }
            ]
    }