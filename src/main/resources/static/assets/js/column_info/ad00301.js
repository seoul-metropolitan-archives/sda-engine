var ad00301 =
{
    column_info :
        [
            {
                sortNo: 0,
                name: "codeHeaderUuid",
                text: "Code UUID",
                editable: false,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 1,
                name: "categoryCode",
                text: "Category Code",
                editable: true,
                width : 130,
                dataType: "code",
                required: true,
                visible: true
            },
            {
                sortNo: 2,
                name: "categoryName",
                text: "Category Name",
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
                required: true,
                visible: true,
                values: axboot.commonCodeFilter("CD006").codeArr,
                labels: axboot.commonCodeFilter("CD006").nameArr

            },
            {
                sortNo: 4,
                name: "codeTypeUuid",
                text: "Code Type",
                width: 90,
                editable: true,
                dataType: "combo",
                required: true,
                visible: true,
                values: axboot.commonCodeFilter("CD001").codeArr,
                labels: axboot.commonCodeFilter("CD001").nameArr
            },
            {
                sortNo: 5,
                name: "orderMethodUuid",
                text: "Order Method",
                width: 100,
                editable: true,
                dataType: "combo",
                required: true,
                values: axboot.commonCodeFilter("CD002").codeArr,
                labels: axboot.commonCodeFilter("CD002").nameArr
            },
            {
                sortNo: 6,
                name: "description",
                text: "Description",
                width: 185,
                editable: true,
                dataType: "richtext",
                required: false,
            },
            {
                sortNo: 7,
                name: "notes",
                text: "Notes",
                width: 185,
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
                defaultValue : "Y",
                renderer: {
                    type: "check",
                    shape: "",
                    falseValues: "N",
                    trueValues: "Y",
                    startEditOnClick: true,
                },
                required: false
            },
            {
                sortNo: 11,
                name: "attribute01",
                text: "Attribute1",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 11,
                name: "attribute01Code",
                text: "Attribute01",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 11,
                name: "attribute01Str",
                text: "Attribute1",
                width: 110,
                editable: true,
                dataType: "popup",
                popupCode : "PU002",
                sqlColumn : {UUID : "attribute01",NAME: "attribute01Str",CODE:"attribute01Code"},
                required: false
            },
            {
                sortNo: 12,
                name: "attribute02",
                text: "Attribute2",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 12,
                name: "attribute02Code",
                text: "Attribute02",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 12,
                name: "attribute02Str",
                text: "Attribute2",
                width: 110,
                editable: true,
                dataType: "popup",
                popupCode : "PU002",
                sqlColumn : {UUID : "attribute02",NAME: "attribute02Str",CODE:"attribute02Code"},
                required: false
            },
            {
                sortNo: 13,
                name: "attribute03",
                text: "Attribute3",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 13,
                name: "attribute03Code",
                text: "Attribute03",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 13,
                name: "attribute03Str",
                text: "Attribute3",
                width: 110,
                editable: true,
                dataType: "popup",
                popupCode : "PU002",
                sqlColumn : {UUID : "attribute03",NAME: "attribute03Str",CODE:"attribute03Code"},
                required: false
            },
            {
                sortNo: 13,
                name: "attribute04",
                text: "Attribute4",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false,
            },
            {
                sortNo: 14,
                name: "attribute04Code",
                text: "Attribute04",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 14,
                name: "attribute04Str",
                text: "Attribute4",
                width: 110,
                editable: true,
                dataType: "popup",
                popupCode : "PU002",
                sqlColumn : {UUID : "attribute04",NAME: "attribute04Str",CODE:"attribute04Code"},
                required: false
            },
            {
                sortNo: 15,
                name: "attribute05",
                text: "Attribute5",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 15,
                name: "attribute05Code",
                text: "Attribute06",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 15,
                name: "attribute05Str",
                text: "Attribute5",
                width: 110,
                editable: true,
                dataType: "popup",
                popupCode : "PU002",
                sqlColumn : {UUID : "attribute05",NAME: "attribute05Str",CODE:"attribute05Code"},
                required: false
            },
            {
                sortNo: 16,
                name: "attribute06",
                text: "Attribute6",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 17,
                name: "attribute06Code",
                text: "Attribute06",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 16,
                name: "attribute06Str",
                text: "Attribute6",
                width: 110,
                editable: true,
                dataType: "popup",
                popupCode : "PU002",
                sqlColumn : {UUID : "attribute06",NAME: "attribute06Str",CODE:"attribute06Code"},
                required: false
            },
            {
                sortNo: 17,
                name: "attribute07",
                text: "Attribute7",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 17,
                name: "attribute07Code",
                text: "Attribute07",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 17,
                name: "attribute07Str",
                text: "Attribute7",
                width: 110,
                editable: true,
                dataType: "popup",
                popupCode : "PU002",
                sqlColumn : {UUID : "attribute07",NAME: "attribute07Str",CODE:"attribute07Code"},
                required: false
            },
            {
                sortNo: 18,
                name: "attribute08",
                text: "Attribute8",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 18,
                name: "attribute08Code",
                text: "Attribute08",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 18,
                name: "attribute08Str",
                text: "Attribute8",
                width: 110,
                editable: true,
                dataType: "popup",
                popupCode : "PU002",
                sqlColumn : {UUID : "attribute08",NAME: "attribute08Str",CODE:"attribute08Code"},
                required: false
            },
            {
                sortNo: 19,
                name: "attribute09",
                text: "Attribute9",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 19,
                name: "attribute09Code",
                text: "Attribute09",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 19,
                name: "attribute09Str",
                text: "Attribute9",
                width: 110,
                editable: true,
                dataType: "popup",
                popupCode : "PU002",
                sqlColumn : {UUID : "attribute09",NAME: "attribute09Str",CODE:"attribute09Code"},
                required: false
            },
            {
                sortNo: 19,
                name: "attribute10",
                text: "Attribute10",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 19,
                name: "attribute10Code",
                text: "Attribute10",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 20,
                name: "attribute10Str",
                text: "Attribute10",
                width: 110,
                editable: true,
                dataType: "popup",
                popupCode : "PU002",
                sqlColumn : {UUID : "attribute10",NAME: "attribute10Str",CODE:"attribute10Code"},
                required: false
            },
            {
                sortNo: 90,
                name: "insertUuid",
                text: "Created By",
                width: 80,
                dataType: "text",
                editable : false,
                readonly : true,
                visible: true,
                required: false,
                disable: true,
            },
            {
                sortNo: 91,
                name: "insertDate",
                text: "Date/Time Created",
                width: 140,
                dataType: "timestamp",
                required: false,
                disable: true,
            },
            {
                sortNo: 92,
                name: "updateUuid",
                text: "Modifed By",
                width: 80,
                visible: true,
                dataType: "text",
                required: false,
                disable: true
            },
            {
                sortNo: 93,
                name: "updateDate",
                text: "Date/Time Modified",
                width: 140,
                dataType: "timestamp",
                required: false,
                disable: true
            }
        ]
}