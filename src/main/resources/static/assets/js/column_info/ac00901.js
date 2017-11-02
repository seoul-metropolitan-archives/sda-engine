/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var ac00901 =
    {
        column_info:
            [
                {
                    sortNo: 1,
                    name: "menuUuid",
                    text: "menuUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "programUuid",
                    text: "programUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "parentMenuCode",
                    text: "Parent Menu Code",
                    width: 150,
                    editable: true,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 3,
                    name: "menuCode",
                    text: "Menu Code",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 4,
                    name: "menuName",
                    text: "Menu Name",
                    width: 180,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 5,
                    name: "programId",
                    text: "Program ID",
                    width: 120,
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU116",
                    sqlColumn : {PROGRAM_ID : "programId",PROGRAM_NAME : "programName"},
                    required: false,
                    visible: true
                },
                {
                    sortNo: 6,
                    name: "programName",
                    text: "Program Name",
                    width: 140,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 7,
                    name: "parameter",
                    text: "Parameter",
                    width: 130,
                    editable: true,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 8,
                    name: "description",
                    text: "Description",
                    width: 250,
                    editable: true,
                    dataType: "richtext",
                    required: false,
                },
                {
                    sortNo: 9,
                    name: "notes",
                    text: "Notes",
                    width: 250,
                    editable: true,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 10,
                    name: "useYn",
                    text: "Use",
                    width: 50,
                    editable: true,
                    dataType: "check",
                    textAlignment: "center",
                    required: true
                },
                {
                    sortNo: 11,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 12,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 13,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 14,
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