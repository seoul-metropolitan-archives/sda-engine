var ad00601 =
{
    column_info :
        [
            {
                sortNo: 0,
                name: "entityTypeUuid",
                text: "EntityType UUID",
                editable: false,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 1,
                name: "entityType",
                text: "Entity Type",
                width: 200,
                editable: true,
                dataType: "code",
                required: true,
                visible: true
            },
            {
                sortNo: 2,
                name: "entityName",
                text: "Entity Name",
                width: 150,
                editable: true,
                dataType: "text",
                required: true,
                visible: true
            },
            {
                sortNo: 3,
                name: "description",
                text: "Description",
                width: 250,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 4,
                name: "notes",
                text: "Notes",
                width: 250,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 5,
                name: "useYN",
                text: "Use",
                width: 50,
                editable: true,
                dataType: "check",
                textAlignment: "center",
                defaultValue : "Y",
                required: true
            },
            {
                sortNo: 6,
                name: "auditYN",
                text: "Audit",
                width: 70,
                editable: true,
                dataType: "check",
                textAlignment: "center",
                required: true,
                defaultValue : "N",
            },
            {
                sortNo: 7,
                name: "insertUuid",
                text: "Created By",
                width: 120,
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
                width: 120,
                dataType: "timestamp",
                required: false,
                disable: true
            },
            {
                sortNo: 9,
                name: "updateUuid",
                text: "Modifed By",
                width: 120,
                visible: true,
                dataType: "text",
                required: false,
                disable: true
            },
            {
                sortNo: 10,
                name: "updateDate",
                text: "Date/Time Modified",
                width: 120,
                dataType: "timestamp",
                required: false,
                disable: true
            }
        ]
}