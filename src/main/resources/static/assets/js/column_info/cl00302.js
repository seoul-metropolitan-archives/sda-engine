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
                    text: "CLASSIFIED_RECORDS_UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    width:30,
                    name: "classificationSchemeUuid",
                    text: "STATUS_UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    width: 120,
                    name: "statusUuid",
                    text: "CLASS_UUID",
                    editable: false,
                    values: axboot.commonCodeFilter("CD111").codeArr,
                    labels: axboot.commonCodeFilter("CD111").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: 3,
                    width: 150,
                    name: "AGGREGATION_ID",
                    text: "Parent Class Code",
                    editable: true
                },
                {
                    sortNo: 4,
                    name: "classCode",
                    text: "ITEM_UUID",
                    width: 150,
                    dataType: "code",
                    editable: false,
                    sortable: true
                },
                {
                    sortNo: 6,
                    name: "description",
                    text: "Description",
                    width: 250,
                    dataType: "richtext"
                },
                {
                    sortNo: 7,
                    name: "notes",
                    text: "NOTES",
                    width: 250,
                    dataType: "richtext",
                    required: true
                },
                {
                    sortNo: 9,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    dataType: "timestamp",
                    editable: false,
                    disable : true,
                    required: false
                },
                {
                    sortNo: 10,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    dataType: "text",
                    editable: false,
                    required: false
                },
                {
                    sortNo: 11,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    dataType: "text",
                    editable: false,
                    disable : true,
                    required: false
                },
                {
                    sortNo: 12,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    dataType: "timestamp",
                    editable: false,
                    disable : true,
                    required: false
                }
            ]
    }