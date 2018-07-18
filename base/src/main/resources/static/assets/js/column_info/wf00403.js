/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

var wf00403 =
    {
        column_info:
            [
                {
                    sortNo: 1,
                    name: "parameterResultUuid",
                    text: "parameterResultUuid",
                    width: 120,
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "inputCodeUuid",
                    text: "inputCodeUuid",
                    width: 120,
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "jobResultUuid",
                    text: "jobResultUuid",
                    width: 120,
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 3,
                    name: "parameterName",
                    text: "Parameter Name",
                    width: 140,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 4,
                    name: "inOutUuid",
                    text: "In/Out",
                    width: 100,
                    editable: false,
                    dataType: "combo",
                    required: false,
                    visible: true,
                    values: axboot.commonCodeFilter("CD129").codeArr,
                    labels: axboot.commonCodeFilter("CD129").nameArr
                },
                {
                    sortNo: 5,
                    name: "inputMethodUuid",
                    text: "Input Method",
                    width: 100,
                    editable: false,
                    dataType: "combo",
                    required: false,
                    visible: true,
                    values: axboot.commonCodeFilter("CD128").codeArr,
                    labels: axboot.commonCodeFilter("CD128").nameArr
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
                                    if ("콤보" == axboot.commonCodeFilter("CD128").nameArr[conditionIndex]) {
                                        retData = "PU118";
                                        break;
                                    } else if ("팝업" == axboot.commonCodeFilter("CD128").nameArr[conditionIndex]) {
                                        retData = "PU119";
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
                    width: 100,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 8,
                    name: "value",
                    text: "Value",
                    width: 100,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 9,
                    name: "displayYn",
                    text: "Display",
                    width: 70,
                    editable: false,
                    dataType: "check",
                    textAlignment: "center",
                    required: false
                },
                {
                    sortNo: 10,
                    name: "requiredYn",
                    text: "Required",
                    width: 70,
                    editable: false,
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
                    dataType: "text",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 15,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    editable: false,
                    dataType: "timestamp",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 16,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    editable: false,
                    dataType: "text",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 17,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    editable: false,
                    dataType: "timestamp",
                    required: false,
                    visible: false
                }
            ]
    }