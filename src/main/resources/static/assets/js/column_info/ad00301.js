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
                width: 150,
                editable: true,
                dataType: "text",
                required: true
            },
            {
                sortNo: 3,
                name: "serviceUuid",
                text: "Service",
                width: 130,
                editable: true,
                dataType: "combo",
                required: true,
                visible: true,
                defaultValue : axboot.commonCodeFilter("CD006").codeArr[0],
                values: axboot.commonCodeFilter("CD006").codeArr,
                labels: axboot.commonCodeFilter("CD006").nameArr,
                editor: {
                    type: "dropDown",
                    dropDownCount: 10,
                    domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                    textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
                }

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
                defaultValue : axboot.commonCodeFilter("CD001").codeArr[0],
                values: axboot.commonCodeFilter("CD001").codeArr,
                labels: axboot.commonCodeFilter("CD001").nameArr,
                editor: {
                    type: "dropDown",
                    dropDownCount: 10,
                    domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                    textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
                }

            },
            {
                sortNo: 5,
                name: "orderMethodUuid",
                text: "Order Method",
                width: 100,
                editable: true,
                dataType: "combo",
                required: true,
                defaultValue : axboot.commonCodeFilter("CD002").codeArr[0],
                values: axboot.commonCodeFilter("CD002").codeArr,
                labels: axboot.commonCodeFilter("CD002").nameArr,
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
                text: "USE",
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
                required: true
            },
            {
            sortNo: 11,
            name: "attribute01",
            text: "Attribute1",
            width: 110,
            editable: true,
            dataType: "popup",
            required: false
        },
            {
                sortNo: 12,
                name: "attribute02",
                text: "Attribute2",
                width: 110,
                editable: true,
                dataType: "popup",
                required: false
            },
            {
                sortNo: 13,
                name: "attribute03",
                text: "Attribute3",
                width: 110,
                editable: true,
                dataType: "popup",
                required: false
            },
            {
                sortNo: 14,
                name: "attribute04",
                text: "Attribute4",
                width: 110,
                editable: true,
                dataType: "popup",
                required: false
            },
            {
                sortNo: 15,
                name: "attribute05",
                text: "Attribute5",
                width: 110,
                editable: true,
                dataType: "popup",
                required: false
            },
            {
                sortNo: 16,
                name: "attribute06",
                text: "Attribute6",
                width: 110,
                editable: true,
                dataType: "popup",
                required: false
            },
            {
                sortNo: 10,
                name: "attribute07",
                text: "Attribute7",
                width: 110,
                editable: true,
                dataType: "popup",
                required: false
            },
            {
                sortNo: 18,
                name: "attribute08",
                text: "Attribute8",
                width: 110,
                editable: true,
                dataType: "popup",
                required: false
            },
            {
                sortNo: 19,
                name: "attribute09",
                text: "Attribute9",
                width: 110,
                editable: true,
                dataType: "popup",
                required: false
            },
            {
                sortNo: 20,
                name: "attribute10",
                text: "Attribute10",
                width: 110,
                editable: true,
                dataType: "popup",
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
                disable: true
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