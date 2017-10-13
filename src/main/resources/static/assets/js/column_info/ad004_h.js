var ad004_h =
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
                values: new Array(),
                labels: new Array(),
                editor: {
                    type: "dropDown",
                    dropDownCount: 10,
                    domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                    textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
                }
            },
            {
                sortNo: 5,
                name: "Multiselect",
                text: "Multiselect",
                width: 70,
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
                dataType: "richtext",
                required: true,
            },
            {
                sortNo: 7,
                name: "notes",
                text: "NOTES",
                width: 250,
                editable: true,
                dataType: "richtext",
                required: true
            },
            {
                sortNo: 8,
                name: "use_YN",
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