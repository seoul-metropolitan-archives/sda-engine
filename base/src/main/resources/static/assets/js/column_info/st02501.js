var st02501 =
    {
        column_info:
            [
                {
                    sortNo: 0,
                    name: "readerMachineUuid",
                    text: "READER MACHINE UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "no",
                    text: "NO",
                    disable: true,
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 2,
                    name: "machineId",
                    text: "MACHINE ID",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 3,
                    name: "machineName",
                    text: "MACHINE NAME",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 5,
                    name: "parentGateName",
                    text: "GATE NAME",
                    width: 120,
                    editable: true,
                    dataType: "popup",
                    popupCode : "PU144",
                    sqlColumn: {
                        GATE_UUID : "gateUuid",
                        GATE_ID: "gateId",
                        GATE_NAME: "parentGateName",
                        SENSOR_USE_YN: "popSensorUseYn",
                        NO: "popNo"
                    },
                    pasteTarget : "popGateUuid",
                    disable : false,
                    required: true,
                    visible: true
                },
                {
                    sortNo: 4,
                    name: "gateUuid",
                    text: "gateUuid",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: false
                },
                {
                    sortNo: 6,
                    name: "ip",
                    text: "IP",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 7,
                    name: "subnetmask",
                    text: "SUBNETMASK",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 8,
                    name: "gateway",
                    text: "GATEWAY",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 9,
                    name: "printDecrease",
                    text: "PRINT DECREASE",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 10,
                    name: "macAddr",
                    text: "MAC ADDR",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 11,
                    name: "antennaCnt",
                    text: "ANTENNA CNT",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 12,
                    width: 80,
                    name: "statusUuid",
                    text: "Status",
                    values: axboot.commonCodeFilter("CD201").codeArr,
                    labels: axboot.commonCodeFilter("CD201").nameArr,
                    dataType : "combo",
                    required : true
                },

            ]
    }