/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var wf00202 =
    {
        column_info:
            [
                {
                    sortNo: 1,
                    name: "workflowJobUuid",
                    text: "workflowJobUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
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
                    name: "jobUuid",
                    text: "jobUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 3,
                    autoincrement : true,
                    name: "sequence",
                    text: "Sequence",
                    width: 70,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 3,
                    name: "jobName",
                    text: "Job Name",
                    width: 210,
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU124",
                    sqlColumn : {JOB_UUID: "jobUuid",JOB_NAME: "jobName",API:"API"},
                    required: true,
                    visible: true
                },
                {
                    sortNo: 4,
                    name: "api",
                    text: "API",
                    width: 200,
                    disable: true,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 5,
                    name: "skipYn",
                    text: "Skip",
                    width: 70,
                    editable: true,
                    dataType: "check",
                    textAlignment: "center",
                    required: false
                },
                {
                    sortNo: 6,
                    name: "terminateYn",
                    text: "Terminate",
                    width: 70,
                    editable: true,
                    dataType: "check",
                    textAlignment: "center",
                    required: false
                },
                {
                    sortNo: 7,
                    name: "description",
                    text: "Description",
                    width: 250,
                    editable: true,
                    dataType: "richtext",
                    required: false,
                },
                {
                    sortNo: 8,
                    name: "notes",
                    text: "Notes",
                    width: 250,
                    editable: true,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 9,
                    name: "useYn",
                    text: "Use",
                    width: 50,
                    editable: true,
                    dataType: "check",
                    textAlignment: "center",
                    required: false
                },
                {
                    sortNo: 10,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 11,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 12,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 13,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                }
            ]
    }