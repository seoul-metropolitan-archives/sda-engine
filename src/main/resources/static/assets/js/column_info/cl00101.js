var cl00101 =
    {
        column_info :
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
                    sortNo: 0,
                    width:30,
                    name: "classificationSchemeUuid",
                    text: "Classification Scheme UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    width:120,
                    name: "statusUuid",
                    text: "Status",
                    editable: false,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD111").codeArr,
                    labels: axboot.commonCodeFilter("CD111").nameArr,
                    required: true,
                },
                {
                    sortNo: 2,
                    width:140,
                    name: "classificationCode",
                    text: "Classification Code",
                    width: 140,
                    editable: false,
                    dataType: "text",
                    required: true
                },
                {
                    sortNo: 3,
                    name: "classificationName",
                    text: "Classification Name",
                    width: 160,
                    dataType: "code",
                    required: true,
                },
                {
                    sortNo: 4,
                    name: "classificationTypeUuid",
                    text: "Classification Type",
                    width: 140,
                    dataType: "combo",
                    required: true,
                    values: axboot.commonCodeFilter("CD112").codeArr,
                    labels: axboot.commonCodeFilter("CD112").nameArr
                },
                {
                    sortNo: 5,
                    name: "orderNo",
                    text: "Sort No",
                    width: 70,
                    editable: true,
                    dataType: "richtext",
                    required: true
                },
                {
                    sortNo: 6,
                    name: "description",
                    text: "Description",
                    width: 250,
                    dataType: "richtext"
                },
                {
                    sortNo: 8,
                    name: "notes",
                    text: "NOTES",
                    width: 250,
                    dataType: "richtext",
                    required: true
                },
                {
                    sortNo: 9,
                    name: "useYn",
                    text: "Use",
                    width: 120,
                    dataType: "check",
                    textAlignment: "right",
                    defaultValue : "Y",
                    renderer: {
                        type: "check",
                        shape: "",
                        falseValues: "N",
                        trueValues: "Y",
                        startEditOnClick: true,
                    },
                    required: true
                },
                {
                    sortNo: 10,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    editable: false,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 11,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    editable: false,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 12,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    editable: false,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 13,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    editable: false,
                    dataType: "timestamp",
                    required: false
                }
            ]
    }