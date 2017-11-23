/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var ac00703 =
    {
        column_info:
            [
                {
                    sortNo: 1,
                    name: "rolePermissionUuid",
                    text: "rolePermissionUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "roleUuid",
                    text: "roleUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 3,
                    name: "permissionUuid",
                    text: "permissionUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 4,
                    name: "pmsProgramUuid",
                    text: "pmsProgramUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 5,
                    name: "pmsEntityTypeUuid",
                    text: "pmsEntityTypeUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 6,
                    name: "permissionName",
                    text: "Permission",
                    width: 150,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 7,
                    name: "programName",
                    text: "Program",
                    width: 150,
                    editable: false,
                    dataType: "text",
                    required: true,
                    visible: false
                },
                {
                    sortNo: 8,
                    name: "entityTypeName",
                    text: "Entity Type",
                    width: 150,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 9,
                    name: "pmsFunctionUuid",
                    text: "Function",
                    width: 100,
                    disable: true,
                    editable: false,
                    dataType: "combo",
                    required: true,
                    visible: true,
                    values: axboot.commonCodeFilter("CD108").codeArr,
                    labels: axboot.commonCodeFilter("CD108").nameArr
                },
                {
                    sortNo: 10,
                    name: "useYn",
                    text: "Use",
                    width: 50,
                    editable: true,
                    dataType: "check",
                    textAlignment: "center",
                    required: false
                },
                {
                    sortNo: 11,
                    name: "description",
                    text: "Description",
                    width: 250,
                    editable: true,
                    dataType: "richtext",
                    required: false,
                },
                {
                    sortNo: 12,
                    name: "notes",
                    text: "Notes",
                    width: 250,
                    editable: true,
                    dataType: "richtext",
                    required: false
                },

                {
                    sortNo: 13,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 14,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 15,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 16,
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