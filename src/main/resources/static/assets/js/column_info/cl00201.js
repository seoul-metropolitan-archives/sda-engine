var cl00201 =
    {
        column_info :
            [
                /****************************************************
                * sortNo: grid column number
                * name: 바인딩명 - VO 변수명과 일치
                * text: 그리드 헤더 텍스트
                * dataType : 입력방법
                * editable : 활성비활성 여유
                * required : 필수 여부
                * visible  : grid 포함여부
                ****************************************************/
                {
                    sortNo: 0,
                    width:30,
                    name: "classUuid",
                    text: "Class UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    width:30,
                    name: "classificationSchemeUuid",
                    text: "Classification Scheme UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    width: 120,
                    name: "statusUuid",
                    text: "Status",
                    editable: false,
                    values: axboot.commonCodeFilter("CD111").codeArr,
                    labels: axboot.commonCodeFilter("CD111").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: 3,
                    width: 150,
                    name: "parentClassUuid",
                    text: "Parent Class Code",
                    editable: true
                },
                {
                    sortNo: 4,
                    name: "classCode",
                    text: "Class Code",
                    width: 150,
                    dataType: "code",
                    editable: false,
                    sortable: true
                },
                {
                    sortNo: 5,
                    name: "classCode",
                    text: "Class Code",
                    width: 150,
                    dataType: "code",
                    editable: true,
                    sortable: true
                },
                {
                    sortNo: "6",
                    name: "classLevelUuid",
                    text: "Class Level Uuid",
                    width: 100,
                    editable: true,
                    values: axboot.commonCodeFilter("CD114").codeArr,
                    labels: axboot.commonCodeFilter("CD114").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: "7",
                    name: "orderNo",
                    text: "Order No",
                    width: 70,
                    editable: true,
                    sortable: true,
                    datType: "number"
                },
                {
                    sortNo: "8",
                    name: "description",
                    text: "Description",
                    width: 250,
                    editable: true,
                    datType: "multiline",
                    showTooltip: true
                },
                {
                    name: "col9",
                    fieldName: "field9",
                    header: {
                        text: "Notes",

                    },
                    width: 250,
                    editable: true,
                    editor: {
                        type: "multiline",
                        textCase: "upper"
                    },
                    styles: {

                        background: "#ffffffff",
                        fontSize: 12,
                        fontFamily: "nanum",
                        fontBold: false
                    },
                    renderer: {
                        type: "text",
                        showTooltip: true
                    }
                },
                {
                    name: "col10",
                    fieldName: "field10",
                    header: {
                        text: "Use",

                    },
                    width: 50,
                    editable: false,
                    renderer: {
                        type: "check",
                        shape: "",
                        falseValues: "false",
                        trueValues: "true",
                        editable: "false",
                        startEditOnClick: true,
                    },
                    styles: {
                        textAlignment: "center",
                        background: "#ffffffff",
                        fontSize: 12,
                        fontFamily: "nanum",
                        fontBold: false
                    }
                },
                {
                    name: "col11",
                    fieldName: "field11",
                    header: {
                        text: "Created By",

                    },
                    width: 80,
                    editable: false,
                    styles: {
                        textAlignment: "near",
                        background: "#f2f2f2",
                        fontSize: 12,
                        fontFamily: "nanum",
                        fontBold: false,
                    },
                    renderer: {
                        type: "text",
                        showTooltip: true
                    }
                },
                {
                    name: "col12",
                    fieldName: "field12",
                    header: {
                        text: "Date/Time Created",

                    },
                    width: 140,
                    editable: false,

                    editor: {
                        type: "date",
                        datetimeFormat: "yyyy-MM-dd hh:mm:ss",
                    },
                    styles: {
                        textAlignment: "near",
                        background: "#f2f2f2",
                        fontSize: 12,
                        fontFamily: "nanum",
                        datetimeFormat: "yyyy-MM-dd hh:mm:ss",
                        fontBold: false
                    }
                },
                {
                    name: "col13",
                    fieldName: "field13",
                    header: {
                        text: "Modifed By",

                    },
                    width: 80,
                    editable: false,
                    styles: {
                        textAlignment: "near",
                        background: "#f2f2f2",
                        fontSize: 12,
                        fontFamily: "nanum",
                        fontBold: false
                    },
                    renderer: {
                        type: "text",
                        showTooltip: true
                    }
                },
                {
                    name: "col14",
                    fieldName: "field14",
                    header: {
                        text: "Date/Time Modified",

                    },
                    width: 140,
                    editable: false,
                    editor: {
                        type: "date",
                        datetimeFormat: "yyyy-MM-dd hh:mm:ss",
                    },
                    styles: {
                        textAlignment: "near",
                        background: "#f2f2f2",
                        fontSize: 12,
                        fontFamily: "nanum",
                        datetimeFormat: "yyyy-MM-dd hh:mm:ss",
                        fontBold: false
                    }
                }
            ]
    }