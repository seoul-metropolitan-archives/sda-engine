var ad00501 =
{
    column_info :
        [
            {
                sortNo: 0,
                name: "glossaryUuid",
                text: "Glossary UUID",
                editable: false,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 1,
                name: "termCode",
                text: "Term Code",
                width: 200,
                editable: true,
                dataType: "code",
                required: true,
                visible: true
            },
            {
                sortNo: 2,
                name: "termName",
                text: "Term Name",
                width: 200,
                editable: true,
                dataType: "text",
                required: true,
                visible: true
            },
            {
                sortNo: 3,
                name: "dataTypeUuid",
                text: "Data Type",
                width: 100,
                editable: true,
                dataType: "combo",
                required: true,
                visible: true,
                values: axboot.commonCodeFilter("CD140").codeArr,
                labels: axboot.commonCodeFilter("CD140").nameArr
            },
            {
                sortNo: 4,
                name: "description",
                text: "Description",
                width: 250,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 5,
                name: "notes",
                text: "Notes",
                width: 250,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 6,
                name: "useYN",
                text: "Use",
                width: 50,
                dataType: "check",
                textAlignment: "center",
                defaultValue : "Y",
                required: false,
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
            }
        ]
}