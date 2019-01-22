var st00601 =
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
                    name: "containerUuid",
                    text: "containerUuid",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "containerName",
                    text: "Container Name",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 2,
                    name: "parentContainerName",
                    text: "Parent Container Name",
                    width: 150,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 3,
                    name: "containerTypeUuid",
                    text: "Container Type",
                    width: 120,
                    editable: false,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD139").codeArr,
                    labels: axboot.commonCodeFilter("CD139").nameArr,
                    disable: true
                },
                {
                    sortNo: 3,
                    name: "containerType",
                    text: "Container Type",
                    width: 120,
                    editable: false,
                    dataType: "text",
                    visible: false,
                    values: axboot.commonCodeFilter("CD139").codeArr,
                    labels: axboot.commonCodeFilter("CD139").nameArr,
                    disable: true
                },
                {
                    sortNo: 3,
                    name: "controlNumber",
                    text: "Control Number",
                    width: 120,
                    dataType: "text",
                    editable: false,
                    sortable: true,
                    disable:true
                },
                {
                    sortNo: 4,
                    name: "provenance",
                    text: "Provenance",
                    width: 120,
                    dataType: "text",
                    editable: false,
                    sortable: true,
                    disable:true
                },
                {
                    sortNo: 5,
                    name: "creationStartDate",
                    text: "Creation Start Date",
                    width: 140,
                    dataType: "date",
                    editable: false,
                    sortable: true,
                    disable:true
                },
                {
                    sortNo: 6,
                    name: "creationEndDate",
                    text: "Creation End Date",
                    width: 140,
                    dataType: "date",
                    editable: false,
                    sortable: true,
                    disable:true
                },
                {
                    sortNo: 7,
                    name: "orderNo",
                    text: "Order No",
                    width: 120,
                    dataType: "text",
                    editable: false,
                    sortable: true,
                    disable:true
                },
                {
                    sortNo: 8,
                    name: "description",
                    text: "Description",
                    width: 250,
                    dataType: "richtext",
                    editable: false,
                    sortable: true,
                    disable:true
                },
                {
                    sortNo: 9,
                    name: "notes",
                    text: "Notes",
                    width: 250,
                    dataType: "richtext",
                    editable: false,
                    sortable: true,
                    disable:true
                },
                {
                    sortNo: 10,
                    width:140,
                    name: "orderKey1",
                    text: "ORDER_KEY",
                    editable: false,
                    dataType: "code",
                    visible : false
                },
                {
                    sortNo: 11,
                    width:140,
                    name: "choiceYn",
                    text: "choiceYn",
                    editable: false,
                    dataType: "code",
                    visible : false
                }
            ]
    }
