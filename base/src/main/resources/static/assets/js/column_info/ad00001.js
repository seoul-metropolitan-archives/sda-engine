var ad00001 =
{
    column_info :
        [
            {
                sortNo: 1,
                name: "serviceUuid",
                text: "ServiceUuid",
                width: 120,
                editable: true,
                dataType: "text",
                required: false,
                visible: false
            },
            {
                sortNo: 2,
                name: "serviceCode",
                text: "Service Code",
                width: 210,
                editable: true,
                dataType: "code",
                required: true
            },
            {
                sortNo: 3,
                name: "serviceName",
                text: "Service Name",
                width: 150,
                editable: true,
                dataType: "text",
                required: true
            },
            {
                sortNo: 4,
                name: "installYN",
                text: "Install",
                width: 50,
                editable: true,
                dataType: "check",
                textAlignment: "center",
                required: false
            },
            /*{
                sortNo: 4,
                name: "serviceUuid",
                text: "Service",
                width: 110,
                editable: true,
                dataType: "combo",
                required: true,
                visible: true,
                values: axboot.commonCodeFilter("CD006").codeArr,
                labels: axboot.commonCodeFilter("CD006").nameArr
            },*/
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
                sortNo: 6,
                name: "description",
                text: "Description",
                width: 250,
                editable: true,
                dataType: "richtext",
                required: false,
                fontSize: 12,
            },
            {
                sortNo: 7,
                name: "notes",
                text: "Notes",
                width: 250,
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
                required: false,
            },
            {
                sortNo: 14,
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