var st00202 =
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
                    text: "Container UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    width: 80,
                    name: "statusUuid",
                    text: "Status",
                    editable: false,
                    values: axboot.commonCodeFilter("CD138").codeArr,
                    labels: axboot.commonCodeFilter("CD138").nameArr,
                    dataType : "combo",
                    disable : true,
                    required : true
                },
                {
                    sortNo: 2,
                    width: 200,
                    name: "containerName",
                    text: "Container Name",
                    editable: true,
                    dataType: "text",
                    required: true,
                },
                {
                    sortNo: 3,
                    width: 200,
                    name: "parentContainerName",
                    text: "Parent Container Name",
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU135",
                    sqlColumn: {
                        CONTAINER_NAME: "parentContainerName",
                        PARENT_CONTAINER: "popContainerName",
                        CONTAINER_UUID: "parentContainerUuid",
                        CONTAINER_TYPE_UUID: "popContainerType"
                    },
                    pasteTarget : "popContainerUuid",
                    disable : false,
                    required:false,

                },
                {
                    sortNo: 4,
                    width: 200,
                    name: "parentContainerUuid",
                    text: "parentContainerUuid",
                    visible : false
                },
                {
                    sortNo: 5,
                    width: 120,
                    name: "containerTypeUuid",
                    text: "Container Type",
                    values: axboot.commonCodeFilter("CD139").codeArr,
                    labels: axboot.commonCodeFilter("CD139").nameArr,
                    dataType : "combo",
                    required : true
                },
                {
                    sortNo: 6,
                    width: 120,
                    name: "controlNumber",
                    text: "Control Number",
                    dataType: "text",
                    visible: true,
                    required : true

                },
                {
                    sortNo: 7,
                    width: 120,
                    name: "repositoryName",
                    text: "서고",
                    dataType: "text",
                    visible: true,
                    required : false,
                    editable: false,
                    disable: true

                },
                {
                    sortNo: 8,
                    width: 120,
                    name: "shelfName",
                    text: "서가",
                    dataType: "text",
                    visible: true,
                    required : false,
                    editable: false,
                    disable: true

                },
                {
                    sortNo: 9,
                    width: 120,
                    name: "locationName",
                    text: "행렬단",
                    dataType: "text",
                    visible: true,
                    required : false,
                    editable: false,
                    disable: true

                },
                {
                    sortNo: 10,
                    width: 120,
                    name: "provenance",
                    text: "Provenance",
                    dataType: "text",
                    visible: true,
                    required : false

                },
                {
                    sortNo: 11,
                    name: "creationStartDate",
                    text: "Creation Start Date",
                    width: 120,
                    editable: true,
                    dataType : "date",
                    required : false
                },
                {
                    sortNo: 12,
                    name: "creationEndDate",
                    text: "Creation End Date",
                    width: 120,
                    editable: true,
                    dataType : "date",
                    required : false,
                },
                {
                    sortNo: 13,
                    name: "orderNo",
                    text: "Order No",
                    width: 100,
                    editable: true,
                    dataType : "number"
                },
                {
                    sortNo: 14,
                    name: "description",
                    text: "Description",
                    width: 250,
                    editable: true,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 15,
                    name: "notes",
                    text: "Notes",
                    width: 250,
                    editable: true,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 16,
                    name: "useYn",
                    text: "Use",
                    width: 50,
                    editable: true,
                    dataType: "check",
                    defaultValue : "Y",
                    textAlignment: "center",
                    required: false
                },
                {
                    sortNo: 17,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 18,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 19,
                    name: "updateUuid",
                    text: "Modified By",
                    width: 80,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 20,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 17,
                    name: "orderKey",
                    text: "OrderKey",
                    width: 140,
                    visible: false
                },
                {
                    sortNo: 18,
                    width:30,
                    name: "recordCount",
                    text: "recordCount",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
            ]
    }