var ad00702 =
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
                    name: "addMetaSegmentUuid",
                    text: "",
                    dataType: "code",
                    editable: false,
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    width:30,
                    name: "addMetaTemplateSetUuid",
                    text: "",
                    dataType: "code",
                    editable: false,
                    required: false,
                    visible: false
                },
                {
                    sortNo: 3,
                    name: "sequence",
                    text: "Sequence",
                    width: 70,
                    dataType: "commanumber",
                    textAlignment: "far",
                    editable: true,
                    required: false,
                    disable: false
                },
                {
                    sortNo: 5,
                    name: "title",
                    text: "Title",
                    width: 120,
                    dataType: "text",
                    editable: true,
                    required: true,
                    disable: false
                },
                {
                    sortNo: 5,
                    name: "name",
                    text: "Name",
                    width: 120,
                    dataType: "text",
                    editable: true,
                    required: true,
                    disable: false
                },
                {
                    sortNo: 7,
                    name: "additionalColumn",
                    text: "Column",
                    width: 130,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD148").nameArr,
                    labels: axboot.commonCodeFilter("CD148").nameArr,
                    editable: true,
                    required: true,
                    disable: false
                },
                {
                    sortNo: 9,
                    name: "popupUuid",
                    text: "Input Value",
                    width: 100,
                    dataType: "popup",
                    popupCode: "PU128",
                    sqlColumn : {CODE : "popupUuid"},
                    editable: true,
                    required: false,
                    disable: false
                },
                {
                    sortNo: 11,
                    name: "displayedYN",
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
                    required: false
                },
                {
                    sortNo: 12,
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
                    sortNo: 15,
                    name: "displaySize",
                    text: "Display Size",
                    width: 80,
                    dataType: "commanumber",
                    textAlignment: "far",
                    editable: true,
                    required: false,
                    disable: false
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
                }
            ]
    }