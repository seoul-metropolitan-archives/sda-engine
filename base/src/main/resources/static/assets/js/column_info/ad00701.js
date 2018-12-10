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
                    sortNo: 0,
                    width:30,
                    name: "addMetaTemplateSetUuid",
                    text: "Add Meta Template Set Uuid",
                    dataType: "code",
                    editable: false,
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "setCode",
                    text: "Code",
                    width: 120,
                    dataType: "text",
                    editable: true,
                    required: true
                },
                {
                    sortNo: 2,
                    name: "setName",
                    text: "Name",
                    width: 180,
                    dataType: "text",
                    editable: true,
                    required: true,
                    disable: false
                },
                {
                    sortNo: 3,
                    name: "description",
                    text: "Description",
                    width: 150,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 4,
                    name: "notes",
                    text: "Notes",
                    width: 150,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 5,
                    name: "useYN",
                    text: "Use",
                    width: 50,
                    editable: true,
                    dataType: "check",
                    defaultValue: "Y"
                },
                {
                    sortNo: 6,
                    name: "defaultYN",
                    text: "Default",
                    width: 70,
                    editable: true,
                    dataType: "check",
                    defaultValue : "N",
                    selectType : "single",
                    required: false
                },
                {
                    sortNo: 7,
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
                    sortNo: 8,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 9,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 80,
                    visible: true,
                    dataType: "text",
                    required: false,
                    disable: true
                },
                {
                    sortNo: 10,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                },
                {
                    sortNo:11 ,
                    name: "metaCnt",
                    text: "Meta Cnt",
                    dataType: "text",
                    visible: false
                }
            ]
    }