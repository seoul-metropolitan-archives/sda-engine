var ad00701 =
    {
        column_info:
            [
                /****************************************************
                 * sortNo: grid column number
                 * name: DB column name
                 * text: grid header text
                 * dataType : 입력방법
                 * editable : 활성비활성 여유
                 * required : 필수 여부
                 * visible  : grid 포함여부
                 ****************************************************/
                {
                    sortNo: 1,
                    width:30,
                    name: "addContextualMetaUuid",
                    text: "",
                    dataType: "code",
                    editable: false,
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    width:60,
                    name: "statusUuid",
                    text: "Status",
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD152").codeArr,
                    labels: axboot.commonCodeFilter("CD152").nameArr,
                    editable: false,
                    required: false,
                    disable: true
                },
                {
                    sortNo: 3,
                    name: "entityType",
                    text: "Entity Type",
                    width: 100,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD145").codeArr,
                    labels: axboot.commonCodeFilter("CD145").nameArr,
                    attributes: axboot.commonCodeFilter("CD145").attrArr,
                    editable: true,
                    required: true,
                    disable: false
                },
                {
                    sortNo: 4,
                    name: "columnCode",
                    text: "Column Code",
                    width: 140,
                    dataType: "text",
                    editable: true,
                    required: false,
                    disable: false
                },
                {
                    sortNo: 5,
                    name: "columnValue",
                    text: "Column Value",
                    width: 120,
                    dataType: "text",
                    editable: true,
                    required: true,
                    disable: false
                },
                {
                    sortNo: 6,
                    name: "metadataEntityType",
                    text: "Metadata Entity Type",
                    width: 170,
                    dataType: "text",
                    editable: true,
                    required: true,
                    disable: false
                },
                {
                    sortNo: 7,
                    name: "additionalColumn",
                    text: "Additional Column",
                    width: 130,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD148").nameArr,
                    labels: axboot.commonCodeFilter("CD148").nameArr,
                    editable: true,
                    required: true,
                    disable: false
                },
                {
                    sortNo: 8,
                    width:90,
                    name: "inputMethodUuid",
                    text: "Input Method",
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD149").codeArr,
                    labels: axboot.commonCodeFilter("CD149").nameArr,
                    editable: true,
                    required: true,
                    disable: false
                },
                {
                    sortNo: 9,
                    name: "inputValue",
                    text: "Input Value",
                    width: 100,
                    dataType: "popup",
                    popupCode: {
                        inputMethodUuid: function (checkData) {
                            var conditionlist = axboot.commonCodeFilter("CD149").codeArr;
                            var retData = "";
                            for (var conditionIndex = 0; conditionIndex < conditionlist.length; conditionIndex++) {

                                if (checkData == conditionlist[conditionIndex]) {
                                    //Input Method의 값에 따라서 동적으로 변경된다.
                                    if ("Combo" == axboot.commonCodeFilter("CD149").nameArr[conditionIndex]) {
                                        retData = "PU128";
                                        break;
                                    }
                                }
                            }
                            return retData;
                        }
                    },
                    sqlColumn : {CODE : "inputValue"},
                    editable: true,
                    required: true,
                    disable: false
                },
                {
                    sortNo: 10,
                    name: "title",
                    text: "Title",
                    width: 120,
                    dataType: "text",
                    editable: true,
                    required: true,
                    disable: false
                },
                {
                    sortNo: 11,
                    name: "requiredYN",
                    text: "Required",
                    width: 60,
                    dataType: "check",
                    textAlignment: "center",
                    defaultValue : "N",
                    renderer: {
                        type: "check",
                        shape: "",
                        falseValues: "N",
                        trueValues: "Y",
                        startEditOnClick: true,
                    },
                    required: false,
                },
                {
                    sortNo: 12,
                    name: "displayYN",
                    text: "Display",
                    width: 50,
                    dataType: "check",
                    textAlignment: "center",
                    defaultValue : "Y",
                    renderer: {
                        type: "check",
                        shape: "",
                        falseValues: "N",
                        trueValues: "Y",
                        startEditOnClick: true,
                    },
                    required: false,
                },
                {
                    sortNo: 13,
                    name: "description",
                    text: "Description",
                    width: 150,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 14,
                    name: "notes",
                    text: "Notes",
                    width: 150,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 15,
                    name: "useYN",
                    text: "Use",
                    width: 50,
                    dataType: "check",
                    textAlignment: "center",
                    defaultValue : "Y",
                    renderer: {
                        type: "check",
                        shape: "",
                        falseValues: "N",
                        trueValues: "Y",
                        startEditOnClick: true,
                    },
                    required: false,
                },
                {
                    sortNo: 16,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    dataType: "text",
                    editable : false,
                    readonly : true,
                    visible: true,
                    required: false,
                    disable: true
                },
                {
                    sortNo: 17,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 18,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    visible: true,
                    dataType: "text",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 19,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 20,
                    name: "reason",
                    text: "REASON",
                    dataType: "text",
                    visible: false
                }
            ]
    }