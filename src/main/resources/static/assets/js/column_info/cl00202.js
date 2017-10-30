var cl00202 =
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
                    sortNo: 6,
                    name: "classLevelUuid",
                    text: "Class Level Uuid",
                    width: 100,
                    editable: true,
                    values: axboot.commonCodeFilter("CD114").codeArr,
                    labels: axboot.commonCodeFilter("CD114").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: 7,
                    name: "orderNo",
                    text: "Order No",
                    width: 70,
                    editable: true,
                    sortable: true,
                    datType: "number"
                },
                {
                    sortNo: 8,
                    name: "description",
                    text: "description",
                    width: 120,
                    editable: true,
                    dataType: "richtext",
                    required: false,
                },
                {
                    sortNo: 9,
                    name: "notes",
                    text: "NOTES",
                    width: 120,
                    editable: true,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 10,
                    name: "useYn",
                    text: "USE",
                    width: 120,
                    editable: true,
                    dataType: "check",
                    textAlignment: "center",
                    required: true
                },
                {
                    sortNo: 11,
                    name: "insertUuid",
                    text: "Created By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 12,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 13,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 14,
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