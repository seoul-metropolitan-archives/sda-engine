/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var wf00402 =
    {
        column_info:
            [
                {
                    sortNo: 1,
                    name: "jobResultUuid",
                    text: "jobResultUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "workflowResultUuid",
                    text: "workflowResultUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 3,
                    name: "statusUuid",
                    text: "Status",
                    width: 90,
                    editable: false,
                    dataType: "combo",
                    required: false,
                    visible: true,
                    values: axboot.commonCodeFilter("CD130").codeArr,
                    labels: axboot.commonCodeFilter("CD130").nameArr
                },
                {
                    sortNo: 4,
                    name: "jobUuid",
                    text: "jobUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 5,
                    autoincrement: true,
                    name: "sequence",
                    text: "Sequence",
                    width: 70,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 7,
                    name: "jobName",
                    text: "Job Name",
                    width: 210,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 8,
                    name: "api",
                    text: "API",
                    width: 200,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 9,
                    name: "startDate",
                    text: "Start Date",
                    width: 140,
                    editable: false,
                    dataType: "timestamp",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 9,
                    name: "endDate",
                    text: "End Date",
                    width: 140,
                    editable: false,
                    dataType: "timestamp",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 10,
                    name: "message",
                    text: "Message",
                    width: 250,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: true
                }
            ]
    }