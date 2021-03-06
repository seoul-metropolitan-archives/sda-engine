var ad00401 =
{
    column_info :
        [
            {
                sortNo: 0,
                name: "popupHeaderUuid",
                text: "Popup UUID",
                editable: false,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 1,
                name: "popupCode",
                text: "Popup Code",
                editable: true,
                width: 130,
                dataType: "code",
                required: true,
                visible: true
            },
            {
                sortNo: 2,
                name: "popupName",
                text: "Popup Name",
                width: 200,
                editable: true,
                dataType: "text",
                required: true
            },
            {
                sortNo: 3,
                name: "serviceUuid",
                text: "Service",
                width: 110,
                editable: true,
                dataType: "combo",
                required: false,
                visible: true,
                required: true,
                lookupDisplay : true,
                values: axboot.commonCodeFilter("CD006").codeArr,
                labels: axboot.commonCodeFilter("CD006").nameArr
            },
            {
                sortNo: 5,
                name: "multiselectYN",
                text: "Multiselect",
                width: 70,
                editable: true,
                dataType: "check",
                required: false,
                defaultValue : "N",
            },
            {
                sortNo: 5,
                name: "treeYN",
                text: "Tree",
                width: 70,
                defaultValue : "N",
                editable: true,
                dataType: "check",
                required: false
            },
            {
                sortNo: 6,
                name: "description",
                text: "Description",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 7,
                name: "notes",
                text: "Notes",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 8,
                name: "useYN",
                text: "Use",
                width: 50,
                editable: true,
                dataType: "check",
                textAlignment: "center",
                defaultValue : "Y",
                required: false
            },
            {
                sortNo: 9,
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
                sortNo: 11,
                name: "insertDate",
                text: "Date/Time Created",
                width: 140,
                dataType: "timestamp",
                required: false,
                disable: true
            },
            {
                sortNo: 12,
                name: "updateUuid",
                text: "Modifed By",
                width: 80,
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
                dataType: "timestamp",
                required: false,
                disable: true
            }
            ,
            {
                sortNo: 15,
                name: "popupSQL",
                text: "Popup SQL",
                width: 120,
                visible : false,
                dataType: "text",
                required: false,
                disable: true
            }
        ]
}