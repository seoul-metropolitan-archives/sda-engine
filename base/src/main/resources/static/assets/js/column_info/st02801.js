var st02801 =
    {
        column_info:
            [
                {
                    sortNo: 0,
                    name: "gateUuid",
                    text: "GATE UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
               /* {
                    sortNo: 1,
                    name: "no",
                    text: "NO",
                    disable: true,
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: true
                },*/
                {
                    sortNo: 2,
                    name: "gateId",
                    text: "게이트 ID",
                    width: 200,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 3,
                    name: "gateName",
                    text: "게이트 명",
                    width: 200,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 4,
                    width: 150,
                    name: "modeUuid",
                    text: "운영모드",
                    editable: true,
                    disable : false,
                    required: true,
                    visible: true,
                    values: axboot.commonCodeFilter("CD223").codeArr,
                    labels: axboot.commonCodeFilter("CD223").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: 6,
                    width: 80,
                    name: "sensorUseYn",
                    text: "센서사용여부",
                    editable: true,
                    disable : false,
                    required: true,
                    visible: true,
                    values: ['Y','N'],
                    labels: ['Y','N'],
                    dataType : "combo"
                },
                {
                    sortNo: 7,
                    name: "inZoneName",
                    text: "In Zone",
                    width: 250,
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU146",
                    sqlColumn : {ZONE_UUID : "inZoneUuid", ZONE_ID : "zoneId", ZONE_NAME : "inZoneName"},
                    required: true,
                    visible: true
                },
                {
                    sortNo: 7,
                    name: "outZoneName",
                    text: "Out Zone",
                    width: 250,
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU146",
                    sqlColumn : {ZONE_UUID : "outZoneUuid", ZONE_ID : "zoneId", ZONE_NAME : "outZoneName"},
                    required: true,
                    visible: true
                },
                {
                    sortNo: 8,
                    width: 80,
                    name: "lightBarStatusUuid",
                    text: "경광등",
                    editable: true,
                    disable : false,
                    required: false,
                    visible: true,
                    values: axboot.commonCodeFilter("CD207").codeArr,
                    labels: axboot.commonCodeFilter("CD207").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: 9,
                    name: "inZoneUuid",
                    text: "inZoneUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 10,
                    name: "outZoneUuid",
                    text: "outZoneUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },

            ]
    }