var ad00402 =
{
    column_info :
        [
            {
                sortNo: 0,
                name: "popupDetailUuid",
                text: "Popup Detail UUID",
                editable: false,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 0,
                name: "popupHeaderUuid",
                text: "Popup Header UUID",
                editable: false,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 1,
                name: "sqlColumn",
                text: "SQL Column",
                editable: true,
                width: 150,
                dataType: "code",
                required: true,
                visible: true,
                renderer : {showTooltip : true}
            },
            {
                sortNo: 2,
                name: "title",
                text: "Title",
                width: 150,
                editable: true,
                dataType: "text",
                required: true
            },
            {
                sortNo: 3,
                name: "width",
                text: "Width",
                width: 100,
                editable: true,
                dataType: "number",
                required: true,
                sortable : true,
                textAlignment: "far",
                editor: {
                    editFormat: "#,##0.##",
                    multipleChar: "+",
                    textAlignment : "far"
                }
            },
            {
                sortNo: 4,
                name: "inputMethodUuid",
                text: "Input Method",
                width: 100,
                editable: true,
                dataType: "combo",
                required: true,
                visible: true,
                values: axboot.commonCodeFilter("CD003").codeArr,
                labels: axboot.commonCodeFilter("CD003").nameArr
            },
            {
                sortNo: 5,
                name: "alignUuid",
                text: "Align",
                width: 100,
                editable: true,
                dataType: "combo",
                required: true,
                values: axboot.commonCodeFilter("CD004").codeArr,
                labels: axboot.commonCodeFilter("CD004").nameArr
            },
            {
                sortNo: 6,
                name: "treeColumnYN",
                text: "Tree Column",
                width: 90,
                dataType : "check",
                editable: true,
                sortable: true,
                required : false,
                defaultValue : "N"
            },
            {
                sortNo: 7,
                name: "treeRelationUuid",
                text: "Tree Relation",
                width: 100,
                editable: true,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD005").codeArr,
                labels: axboot.commonCodeFilter("CD005").nameArr,
                required: false
            },
            {
                sortNo: 8,
                name: "orderNO",
                text: "Order No",
                width: 100,
                editable: true,
                sortable: true,
                dataType: "number",
                textAlignment: "far",
                editor: {
                    type: "number",
                    textAlignment: "far",
                    editFormat: "#,##0.##",
                    multipleChar: "+",
                }
            },
            {
                sortNo: 9,
                name: "insertUuid",
                text: "Created By",
                width: 80,
                dataType: "text",
                editable: false,
                visible: true,
                required: false,
                disable: true
            },
            {
                sortNo: 11,
                name: "insertDate",
                text: "Date/Time Created",
                width: 140,
                editable: false,
                dataType: "timestamp",
                required: false,
                disable: true
            },
            {
                sortNo: 12,
                name: "updateUuid",
                text: "Modifed By",
                width: 80,
                editable: false,
                visible: true,
                dataType: "text",
                required: false,
                disable: true
            },
            {
                sortNo: 14,
                name: "updateDate",
                text: "Date/Time Modified",
                width: 140,
                editable: false,
                dataType: "timestamp",
                required: false,
                disable: true
            }
        ]
}