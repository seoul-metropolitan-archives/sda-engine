var cl00301 =
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
                    name: "aggreation",
                    text: "Aggreation",
                    editable: false
                },
                {
                    sortNo: 4,
                    name: "classCode",
                    text: "Aggregation Tree",
                    width: 150,
                    dataType: "",
                    editable: false,
                    sortable: true
                },
                {
                    sortNo: 5,
                    name: "classfiedDate",
                    text: "Classfied Date",
                    width: 150,
                    dataType: "text",
                    editable: false,
                    sortable: true
                },
                {
                    sortNo: 6,
                    name: "description",
                    text: "Description",
                    width: 100,
                    editable: false,
                    dataType : "text"
                },
                {
                    sortNo: 7,
                    name: "notes",
                    text: "Notes",
                    width: 70,
                    editable: true,
                    sortable: true,
                    datType: "number"
                },
                {
                    sortNo: 8,
                    name: "insertUuid",
                    text: "Created By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 9,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 10,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 11,
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