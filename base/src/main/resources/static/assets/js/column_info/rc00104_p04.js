var rc00104_p04 =
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
                dataType: "code",
                editable: false,
                required: false,
                visible: false
            },
            /*{
                sortNo: 2,
                width:140,
                name: "classificationCode",
                text: "Classification Code",
                width: 140,
                dataType: "text",
                required: false,
                disable : true,
                editable : false
            },*/
            {
                sortNo: 3,
                name: "classificationName",
                text: "Classification Name",
                width: 140,
                dataType: "text",
                editable : false
            },
            {
                sortNo: 4,
                name: "classificationTypeUuid",
                text: "Classification Type",
                width: 110,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD112").codeArr,
                labels: axboot.commonCodeFilter("CD112").nameArr,
                required : false,
                disable: false,
                editable : false
            },
        ]
}