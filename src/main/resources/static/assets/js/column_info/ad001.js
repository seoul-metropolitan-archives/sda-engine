var ad00101 =
{
    column_info :
        [
            {
                sortNo: 1,
                name: "configurationUuid",
                text: "ConfigurationUuid",
                width: 120,
                editable: true,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 2,
                name: "configurationCode",
                text: "Environment Code",
                width: 120,
                editable: true,
                dataType: "text",
                required: true
            },
            {
                sortNo: 3,
                name: "configurationValue",
                text: "Environment Value",
                width: 120,
                editable: true,
                dataType: "text",
                required: true
            },
            {
                sortNo: 4,
                name: "serviceUuid",
                text: "Service",
                width: 120,
                editable: true,
                dataType: "combo",
                required: false,
                visible: true,
                values: axboot.commonCodeFilter("CD006").codeArr,
                labels: axboot.commonCodeFilter("CD006").nameArr,
                lookupDisplay : true,
                editor: {
                    type: "dropDown",
                    dropDownCount: 10,
                    domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                    textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
                }
            },
            /*
            {
                sortNo: 5,
                name: "serviceName",
                text: "Service",
                width: 120,
                editable: true,
                dataType: "text",
                required: false,
                visible: true
                values: ["Service1", "Service2", "Service3", "Service4"],
                labels: ["Service1", "Service2", "Service3", "Service4"],
                editor: {
                    type: "dropDown",
                    dropDownCount: 10,
                    domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                    textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
                }
            },*/
            {
                sortNo: 7,
                name: "notes",
                text: "NOTES",
                width: 120,
                editable: true,
                dataType: "richtext",
                required: false
            },
            {
                sortNo: 8,
                name: "useYN",
                text: "USE",
                width: 120,
                editable: true,
                dataType: "check",
                textAlignment: "center",
                required: true
            },
            {
                sortNo: 6,
                name: "description",
                text: "DESCRIPTION",
                width: 120,
                editable: true,
                dataType: "richtext",
                required: false,
            },
            {
                sortNo: 9,
                name: "insertUuid",
                text: "Created By",
                width: 120,
                editable: false,
                dataType: "text",
                visible: false,
                disable : true,
                required: false
            },
            {
                sortNo: 10,
                name: "insertUserName",
                text: "Created By",
                width: 120,
                editable: false,
                dataType: "text",
                disable : true,
                required: false
            },
            {
                sortNo: 11,
                name: "insertDate",
                text: "Date/Time Created",
                width: 120,
                editable: false,
                disable : true,
                dataType: "timestamp",
                required: false
            },
            {
                sortNo: 12,
                name: "updateUuid",
                text: "Modifed By",
                width: 120,
                editable: false,
                visible: false,
                disable : true,
                dataType: "text",
                required: false
            },
            {
                sortNo: 13,
                name: "updateUserName",
                text: "Modifed By",
                width: 120,
                editable: false,
                disable : true,
                dataType: "text",
                required: false
            },
            {
                sortNo: 14,
                name: "updateDate",
                text: "Date/Time Modified",
                width: 120,
                editable: false,
                disable : true,
                dataType: "timestamp",
                required: false
            }
        ]
}