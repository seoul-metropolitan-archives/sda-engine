/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var ac00801 =
    {
        column_info:
            [
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
                    name: "programId",
                    text: "Program ID",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 3,
                    name: "programName",
                    text: "Program Name",
                    width: 200,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 4,
                    name: "serviceUuid",
                    text: "Service",
                    width: 110,
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
                    }
                },
                {
                    sortNo: 5,
                    name: "url",
                    text: "URL",
                    width: 250,
                    editable: true,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 6,
                    name: "description",
                    text: "Description",
                    width: 250,
                    editable: true,
                    dataType: "richtext",
                    required: false,
                },
                {
                    sortNo: 7,
                    name: "notes",
                    text: "Notes",
                    width: 250,
                    editable: true,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 8,
                    name: "useYn",
                    text: "Use",
                    width: 50,
                    editable: true,
                    dataType: "check",
                    textAlignment: "center",
                    required: false
                },
                {
                    sortNo: 9,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 10,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 11,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 12,
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