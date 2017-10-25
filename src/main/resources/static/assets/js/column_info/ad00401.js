var ad00401 =
{
    column_info :
        [
            {
                sortNo: 0,
                name: "popupHeaderUUID",
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
                dataType: "code",
                required: true,
                visible: true
            },
            {
                sortNo: 2,
                name: "popupName",
                text: "Popup Name",
                width: 150,
                editable: true,
                dataType: "text",
                required: true
            },
            {
                sortNo: 3,
                name: "serviceUUID",
                text: "Service UUID",
                width: 130,
                editable: true,
                dataType: "combo",
                required: false,
                visible: true,
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
                sortNo: 5,
                name: "multiselectYN",
                text: "Multiselect",
                width: 70,
                editable: true,
                dataType: "check",
                required: true,
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
                required: true
            },
            {
                sortNo: 6,
                name: "description",
                text: "DESCRIPTION",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 7,
                name: "notes",
                text: "NOTES",
                width: 250,
                editable: true,
                dataType: "richtext"
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
                required: true
            },
            {
                sortNo: 9,
                name: "insertUUID",
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
                sortNo: 11,
                name: "insertDate",
                text: "Date/Time Created",
                width: 120,
                dataType: "timestamp",
                required: false,
                disable: true
            },
            {
                sortNo: 12,
                name: "updateUUID",
                text: "Modifed By",
                width: 120,
                visible: true,
                dataType: "text",
                required: false,
                disable: true
            },
            {
                sortNo: 14,
                name: "updateDate",
                text: "Date/Time Modified",
                width: 120,
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