/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var ac00701 =
    {
        column_info:
            [
                {
                    sortNo: 1,
                    name: "roleUuid",
                    text: "roleUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "roleName",
                    text: "Role",
                    width: 150,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false,

                },
                {
                    sortNo: 3,
                    name: "description",
                    text: "Description",
                    width: 250,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false,
                    visible: true,
                    renderer: {showTooltip : true}
                },
                {
                    sortNo: 4,
                    name: "notes",
                    text: "Notes",
                    width: 250,
                    editable: true,
                    dataType: "richtext",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 5,
                    name: "useYn",
                    text: "Use",
                    width: 50,
                    editable: false,
                    disable: true,
                    dataType: "check",
                    textAlignment: "center",
                    required: true
                },
                {
                    sortNo: 6,
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
                    sortNo: 7,
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
                    sortNo: 8,
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
                    sortNo: 9,
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