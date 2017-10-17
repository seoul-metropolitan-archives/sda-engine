/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var ac00303 =
    {
        column_info:
            [
                {
                    sortNo: 1,
                    name: "accessControlUuid",
                    text: "accessControlUuid",
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
                    name: "userGroupUuid",
                    text: "userGroupUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 4,
                    name: "roleName",
                    text: "Role",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 5,
                    name: "description",
                    text: "DESCRIPTION",
                    width: 120,
                    editable: true,
                    dataType: "richtext",
                    required: false,
                },
                {
                    sortNo: 6,
                    name: "notes",
                    text: "NOTES",
                    width: 120,
                    editable: true,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 7,
                    name: "useYn",
                    text: "USE",
                    width: 120,
                    editable: true,
                    dataType: "check",
                    textAlignment: "center",
                    renderer: {
                        type: "check",
                        shape: "",
                        falseValues: "false",
                        trueValues: "true",
                        editable: "false",
                        startEditOnClick: true,
                    },
                    required: true
                },
                {
                    sortNo: 8,
                    name: "insertUuid",
                    text: "Created By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 9,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 10,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 11,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                }
            ]
    }