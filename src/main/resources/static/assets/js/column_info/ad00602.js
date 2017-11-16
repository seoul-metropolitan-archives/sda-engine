var ad00602 =
{
    column_info :
        [
            {
                sortNo: 0,
                name: "entityColumnUuid",
                text: "EntityColumnUuid",
                editable: false,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 1,
                name: "entityTypeUuid",
                text: "EntityTypeUuid",
                editable: false,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 2,
                name: "glossaryUuid",
                text: "GlossaryUuid",
                editable: false,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 3,
                name: "glossary",
                text: "Glossary",
                editable: false,
                width : 400,
                dataType: "text",
                header :{styles:{
                    background: "linear,#f2f2f2",
                    fontSize: 12,
                    fontFamily: "nanum",
                    foreground: "#000000",
                    borderRight: "#cccccc, 1",
                    borderBottom: "#cccccc, 1",
                    fontBold: false
                }},
                columnList : [{
                    sortNo: 3,
                    name: "termCode",
                    text: "Term Code",
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU003",
                    sqlColumn : {GLOSSARY_UUID: "glossaryUuid",TERM_NAME: "termName",TERM_CODE:"termCode",DATATYPE:"dataType1"},
                    required: false,
                    visible: true
                },
                    {
                        sortNo: 4,
                        name: "termName",
                        text: "Term Name",
                        editable: false,
                        dataType: "text",
                        visible: true
                    },
                    {
                        sortNo: 5,
                        name: "dataType1",
                        text: "Data Type",
                        editable: false,
                        dataType: "text",
                        visible: true
                    }]
            },
            {
                sortNo: 4,
                name: "table",
                text: "Table",
                editable: false,
                dataType: "text",
                width : 400,
                header :{styles:{
                    background: "linear,#f2f2f2",
                    fontSize: 12,
                    fontFamily: "nanum",
                    foreground: "#000000",
                    borderRight: "#cccccc, 1",
                    borderBottom: "#cccccc, 1",
                    fontBold: false
                }},
                columnList: [
                    {
                        sortNo: 6,
                        name: "columnName",
                        text: "Columnname",
                        editable: false,
                        dataType: "text",
                        visible: true
                    },
                    {
                        sortNo: 7,
                        name: "comments",
                        text: "Comments",
                        editable: false,
                        dataType: "text",
                        visible: true
                    },
                    {
                        sortNo: 8,
                        name: "dataType2",
                        text: "Data Type",
                        editable: false,
                        dataType: "text",
                        visible: true
                    }
                ]
            },
            {
                sortNo: 9,
                name: "nullable",
                text: "Nullable",
                width: 70,
                editable: false,
                dataType: "check",
                textAlignment: "center",
                visible: true
            },
            {
                sortNo: 10,
                name: "auditYN",
                text: "Audit",
                width: 70,
                editable: false,
                dataType: "check",
                textAlignment: "center",
                defaultValue : "N",
                visible: true
            },
            {
                sortNo: 11,
                name: "description",
                text: "Description",
                width: 250,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 12,
                name: "notes",
                text: "Notes",
                width: 250,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 13,
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
                sortNo: 14,
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
                sortNo: 15,
                name: "insertDate",
                text: "Date/Time Created",
                width: 120,
                dataType: "timestamp",
                required: false,
                disable: true
            },
            {
                sortNo: 16,
                name: "updateUuid",
                text: "Modifed By",
                width: 120,
                visible: true,
                dataType: "text",
                required: false,
                disable: true
            },
            {
                sortNo: 17,
                name: "updateDate",
                text: "Date/Time Modified",
                width: 120,
                dataType: "timestamp",
                required: false,
                disable: true
            }
        ]
}