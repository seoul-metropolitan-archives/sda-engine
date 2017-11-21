var ad00201 =
{
    column_info :
        [
            {
                sortNo: 1,
                name: "messageUuid",
                text: "MessageUUID",
                width: 120,
                editable: true,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 2,
                name: "messageCode",
                text: "Message Code",
                width: 120,
                editable: true,
                dataType: "code",
                required: true
            },
            {
                sortNo: 3,
                name: "messageName",
                text: "Message Name",
                width: 400,
                editable: true,
                dataType: "text",
                required: true
            },
            {
                sortNo: 4,
                name: "serviceUuid",
                text: "Service",
                width: 110,
                editable: true,
                dataType: "combo",
                required: true,
                visible: true,
                values: axboot.commonCodeFilter("CD006").codeArr,
                labels: axboot.commonCodeFilter("CD006").nameArr,
                lookupDisplay : true
            },
            {
                sortNo: 5,
                name: "dbErrorCode",
                text: "DB Error Code",
                width: 150,
                editable: true,
                dataType: "text",
                required: false,
                visible: true
            },
            {
                sortNo: 6,
                name: "description",
                text: "Description",
                width: 200,
                editable: true,
                dataType: "richtext",
                required: false,
            },
            {
                sortNo: 7,
                name: "notes",
                text: "Notes",
                width: 200,
                editable: true,
                dataType: "richtext",
                required: false
            },
            {
                sortNo: 8,
                name: "useYN",
                text: "Use",
                width: 50,
                editable: true,
                dataType: "check",
                textAlignment: "center",
                defaultValue:"Y",
                required: false
            },
            {
                sortNo: 9,
                name: "insertUuid",
                text: "Created By",
                width: 80,
                editable: false,
                dataType: "text",
                visible: true,
                disable : true,
                required: false
            },
            {
                sortNo: 11,
                name: "insertDate",
                text: "Date/Time Created",
                width: 140,
                editable: false,
                disable : true,
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
                disable : true,
                dataType: "text",
                required: false
            },
            {
                sortNo: 13,
                name: "updateDate",
                text: "Date/Time Modified",
                width: 140,
                editable: false,
                disable : true,
                dataType: "timestamp",
                required: false
            }
        ]
}