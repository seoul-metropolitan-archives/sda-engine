/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var wf00102 =
    {
        column_info:
            [
                {
                    sortNo: 1,
                    name: "parameterUuid",
                    text: "parameterUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "inputCodeUuid",
                    text: "inputCodeUuid",
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
                    name: "parameterName",
                    text: "Parameter Name",
                    width: 200,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 4,
                    name: "inOutUuid",
                    text: "In/Out",
                    width: 70,
                    editable: true,
                    dataType: "combo",
                    required: true,
                    visible: true,
                    values: axboot.commonCodeFilter("CD129").codeArr,
                    labels: axboot.commonCodeFilter("CD129").nameArr,
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
                    name: "inputMethodUuid",
                    text: "Input Method",
                    width: 100,
                    editable: true,
                    dataType: "combo",
                    required: true,
                    visible: true,
                    values: axboot.commonCodeFilter("CD128").codeArr,
                    labels: axboot.commonCodeFilter("CD128").nameArr,
                    lookupDisplay: true,
                    editor: {
                        type: "dropDown",
                        dropDownCount: 10,
                        domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                        textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
                    }
                },
                {
                    sortNo: 6,
                    name: "inputCode",
                    text: "Input Code",
                    width: 100,
                    editable: false,
                    dataType: "popup",
                    popupCode: {
                        inputMethodUuid: function (checkData) {
                            var conditionlist = axboot.commonCodeFilter("CD128").codeArr;
                            var retData = "";
                            for (var conditionIndex = 0; conditionIndex < conditionlist.length; conditionIndex++) {

                                if (checkData == conditionlist[conditionIndex]) {
                                    //Input Method의 값에 따라서 동적으로 변경된다.
                                    if ("Combo" == axboot.commonCodeFilter("CD128").nameArr[conditionIndex]) {
                                        retData = "PU002";
                                        break;
                                    } else if ("Popup" == axboot.commonCodeFilter("CD128").nameArr[conditionIndex]) {
                                        retData = "PU125";
                                        break;
                                    }
                                }
                            }
                            return retData;
                        }
                    },
                    sqlColumn: {UUID: "inputCodeUuid", NAME: "inputCodeName", CODE: "inputCode"},
                    required: false,
                    visible: true
                },
                {
                    sortNo: 7,
                    name: "inputCodeName",
                    text: "Input Name",
                    width: 170,
                    disable: true,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 8,
                    name: "defaultValue",
                    text: "Defalut Value",
                    width: 150,
                    editable: true,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 9,
                    name: "displayYn",
                    text: "Display",
                    width: 70,
                    editable: true,
                    dataType: "check",
                    textAlignment: "center",
                    required: false
                },
                {
                    sortNo: 10,
                    name: "requiredYn",
                    text: "Required",
                    width: 70,
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
                    name: "useYn",
                    text: "Use",
                    width: 50,
                    editable: true,
                    dataType: "check",
                    textAlignment: "center",
                    required: false
                },
                {
                    sortNo: 14,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 15,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 16,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 17,
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