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
                required: true,
                renderer : {showTooltip : true}
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
                labels: axboot.commonCodeFilter("CD003").nameArr,
                editor: {
                    domainOnly: true
                }
            },
            {
                sortNo: 5,
                name: "alignUuid",
                text: "Align",
                width: 100,
                editable: true,
                dataType: "combo",
                required: true,
                lookupDisplay: true, //라벨로 표시
                values: axboot.commonCodeFilter("CD004").codeArr,
                labels: axboot.commonCodeFilter("CD004").nameArr,
                editor: {
                    domainOnly: true
                }

            },
            {
                sortNo: 6,
                name: "treeColumnYN",
                text: "Tree Column",
                width: 90,
                dataType : "check",
                editable: true,
                sortable: true,
                renderer: {
                    falseValues: "N",
                    trueValues: "Y",
                    editable: "false",
                    startEditOnClick: true
                },
            },
            {
                sortNo: 7,
                name: "treeRelationUuid",
                text: "Tree Relation",
                width: 100,
                editable: true,
                dataType: "combo",
                lookupDisplay: true, //라벨로 표시
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
                editable: false,
                dataType: "text",
                visible: true,
                required: false,
                renderer : {showTooltip : true}
            },
            {
                sortNo: 11,
                name: "insertDate",
                text: "Date/Time Created",
                width: 140,
                editable: false,
                dataType: "timestamp",
                required: false
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
                renderer : {showTooltip : true}
            },
            {
                sortNo: 14,
                name: "updateDate",
                text: "Date/Time Modified",
                width: 140,
                editable: false,
                dataType: "timestamp",
                required: false
            }
        ]
}