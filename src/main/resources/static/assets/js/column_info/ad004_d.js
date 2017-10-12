var ad004_d =
{
    column_info :
        [
            {
                sortNo: 0,
                name: "popupUUID",
                text: "Popup UUID",
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
                visible: true
            },
            {
                sortNo: 2,
                name: "Title",
                text: "title",
                width: 150,
                editable: true,
                dataType: "text",
                required: true
            },
            {
                sortNo: 3,
                name: "width",
                text: "Width",
                width: 130,
                editable: true,
                dataType: "number",
                required: true,
                sortable : true,
                editor: {
                    editFormat: "#,##0.##",
                    multipleChar: "+"
                }
            },
            {
                sortNo: 4,
                name: "inputMethod",
                text: "Input Method",
                width: 100,
                editable: true,
                dataType: "combo",
                required: false,
                visible: true,
                values: new Array(),
                labels: new Array(),
                editor: {
                    domainOnly: true
                }
            },
            {
                sortNo: 5,
                name: "align",
                text: "Align",
                width: 70,
                editable: true,
                dataType: "combo",
                required: true,
                lookupDisplay: true, //라벨로 표시
                values : new Array(),
                label : new Array()

            },
            {
                sortNo: 6,
                name: "Tree",
                text: "tree",
                width: 250,
                dataType : "check",
                editable: true,
                sortable: true,
                renderer: {
                    falseValues: "false",
                    trueValues: "true",
                    editable: "false",
                    startEditOnClick: true
                },
            },
            {
                sortNo: 7,
                name: "Tree Relation",
                text: "treeRelation",
                width: 100,
                editable: true,
                dataType: "combo",
                required: true
            },
            {
                sortNo: 8,
                name: "orderNo",
                text: "Order No",
                width: 100,
                editable: true,
                sortable: true,
                dataType: "number",
                editor: {
                type: "number",
                textAlignment: "far",
                editFormat: "#,##0.##",
                multipleChar: "+",
            }
            },
            {
                sortNo: 9,
                name: "insertUUID",
                text: "Created By",
                width: 120,
                editable: false,
                dataType: "text",
                visible: false,
                required: false
            },
            {
                sortNo: 10,
                name: "insertUserName",
                text: "Created By",
                width: 120,
                editable: false,
                dataType: "text",
                required: false
            },
            {
                sortNo: 11,
                name: "insertDate",
                text: "Date/Time Created",
                width: 120,
                editable: false,
                dataType: "timestamp",
                required: false
            },
            {
                sortNo: 12,
                name: "updateUUID",
                text: "Modifed By",
                width: 120,
                editable: false,
                visible: false,
                dataType: "text",
                required: false
            },
            {
                sortNo: 13,
                name: "updateUserName",
                text: "Modifed By",
                width: 120,
                editable: false,
                dataType: "text",
                required: false
            },
            {
                sortNo: 14,
                name: "updateDate",
                text: "Date/Time Modified",
                width: 120,
                editable: false,
                dataType: "timestamp",
                required: false
            }
        ]
}