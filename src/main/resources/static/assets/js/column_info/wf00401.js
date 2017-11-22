/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var wf00401 =
    {
        column_info:
            [
                {
                    sortNo: 1,
                    name: "workflowResultUuid",
                    text: "workflowResultUuid",
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
                    sortNo: 3,
                    name: "statusUuid",
                    text: "Status",
                    width: 100,
                    editable: false,
                    dataType: "combo",
                    required: false,
                    visible: true,
                    values: axboot.commonCodeFilter("CD131").codeArr,
                    labels: axboot.commonCodeFilter("CD131").nameArr
                },
                {
                    sortNo: 4,
                    name: "batchId",
                    text: "Batch ID",
                    width: 150,
                    editable: false,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 5,
                    name: "workflowName",
                    text: "Workflow Name",
                    width: 150,
                    editable: false,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 6,
                    name: "serviceUuid",
                    text: "Service",
                    width: 100,
                    editable: false,
                    dataType: "combo",
                    required: false,
                    visible: true,
                    values: axboot.commonCodeFilter("CD006").codeArr,
                    labels: axboot.commonCodeFilter("CD006").nameArr
                },
                {
                    sortNo: 7,
                    name: "executerUuid",
                    text: "Excuter",
                    width: 150,
                    editable: false,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 8,
                    name: "munuName",
                    text: "Menu",
                    width: 150,
                    editable: false,
                    dataType: "text",
                    required: false
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
                    sortNo: 10,
                    name: "endDate",
                    text: "End Date",
                    width: 140,
                    editable: false,
                    dataType: "timestamp",
                    required: false,
                    visible: true
                }
            ]
    }