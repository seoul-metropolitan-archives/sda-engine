var rc00103_p03 =
{
    column_info_item :
        [
            {
                sortNo: 0,
                name: "uuid",
                text: "UUID",
                editable: false,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 0,
                name: "nodeType",
                dataType: "text",
                visible: false
            },
            {
                sortNo: 1,
                name: "title",
                text: "Title",
                width: 300,
                required: true,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 2,
                name: "typeUuid",
                text: "Type",
                width: 100,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD136").codeArr,
                labels: axboot.commonCodeFilter("CD136").nameArr,
                editable: true,
                required: false,
                disable: false
            },
            {
                sortNo: 4,
                name: "description",
                text: "Description",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 5,
                name: "notes",
                text: "Notes",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 6,
                name: "author",
                text: "Author",
                width: 100,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 7,
                text: "Date of Description",
                editable: false,
                width : 200,
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
                columnList : [
                    {
                        sortNo: 8,
                        name: "descriptionStartDate",
                        text: "Start",
                        width: 100,
                        dataType: "date",
                        includedFormat: true,
                        length: 8,
                        required: false,
                        editable: true,
                        disable: false
                    },
                    {
                        sortNo: 9,
                        name: "descriptionEndDate",
                        text: "End",
                        width: 100,
                        dataType: "date",
                        includedFormat: true,
                        length: 8,
                        required: false,
                        editable: true,
                        disable: false
                    }
                ]
            },
            {
                sortNo: 10,
                name: "provenance",
                text: "Provenance",
                width: 100,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 11,
                name: "referenceCode",
                text: "Reference Code",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 12,
                name: "creator",
                text: "Creator",
                width: 80,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 13,
                text: "Date of Creation",
                editable: false,
                width : 200,
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
                columnList : [
                    {
                        sortNo: 14,
                        name: "creationStartDate",
                        text: "Start",
                        width: 100,
                        dataType: "date",
                        length: 8,
                        required: false,
                        editable: true,
                        disable: false
                    },
                    {
                        sortNo: 15,
                        name: "creationEndDate",
                        text: "End",
                        width: 100,
                        dataType: "date",
                        length: 8,
                        required: false,
                        editable: true,
                        disable: false
                    }
                ]
            },
            {
                sortNo: 16,
                name: "keyword",
                text: "Keyword",
                width: 100,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 17,
                name: "openStatusUuid",
                text: "Open Status",
                width: 100,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD123").codeArr,
                labels: axboot.commonCodeFilter("CD123").nameArr,
                editable: true,
                required: false
            },
         ],
    column_info_aggregation :
        [
            {
                sortNo: 0,
                name: "uuid",
                text: "UUID",
                editable: false,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 0,
                name: "nodeType",
                dataType: "text",
                visible: false
            },
            {
                sortNo: 1,
                name: "title",
                text: "Title",
                width: 300,
                required: true,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 2,
                name: "description",
                text: "Description",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 3,
                name: "notes",
                text: "Notes",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 4,
                name: "author",
                text: "Author",
                width: 100,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 5,
                text: "Date of Description",
                editable: false,
                width : 200,
                dataType: "text",
                header :{styles:{
                        background: "linear,#f2f2f2",
                        fontSize: 7,
                        fontFamily: "nanum",
                        foreground: "#000000",
                        borderRight: "#cccccc, 1",
                        borderBottom: "#cccccc, 1",
                        fontBold: false
                    }},
                columnList : [
                    {
                        sortNo: 6,
                        name: "descriptionStartDate",
                        text: "Start",
                        width: 100,
                        dataType: "date",
                        includedFormat: true,
                        length: 8,
                        required: false,
                        editable: true,
                        disable: false
                    },
                    {
                        sortNo: 7,
                        name: "descriptionEndDate",
                        text: "End",
                        width: 100,
                        dataType: "date",
                        includedFormat: true,
                        length: 8,
                        required: false,
                        editable: true,
                        disable: false
                    }
                ]
            },
            {
                sortNo: 8,
                name: "provenance",
                text: "Provenance",
                width: 100,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 9,
                name: "referenceCode",
                text: "Reference Code",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 10,
                name: "creator",
                text: "Creator",
                width: 80,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 11,
                text: "Date of Creation",
                editable: false,
                width : 200,
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
                columnList : [
                    {
                        sortNo: 12,
                        name: "creationStartDate",
                        text: "Start",
                        width: 100,
                        dataType: "date",
                        length: 8,
                        required: false,
                        editable: true,
                        disable: false
                    },
                    {
                        sortNo: 13,
                        name: "creationEndDate",
                        text: "End",
                        width: 100,
                        dataType: "date",
                        length: 8,
                        required: false,
                        editable: true,
                        disable: false
                    }
                ]
            }
        ]
}