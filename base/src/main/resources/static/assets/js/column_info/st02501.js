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
                    name: "machineId",
                    text: "장비 ID",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 2,
                    name: "machineName",
                    text: "장비명",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 3,
                    name: "parentGateName",
                    text: "설치 GATE",
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
                    sortNo: 5,
                    name: "ip",
                    text: "IP",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 6,
                    name: "subnetmask",
                    text: "SUBNETMASK",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 7,
                    name: "gateway",
                    text: "GATEWAY",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 8,
                    name: "printDecrease",
                    text: "출력감쇄",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 9,
                    name: "macAddr",
                    text: "MAC ADDR",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 10,
                    name: "antennaCnt",
                    text: "안테나수",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 11,
                    width: 80,
                    name: "statusUuid",
                    text: "장비상태",
                    values: axboot.commonCodeFilter("CD201").codeArr,
                    labels: axboot.commonCodeFilter("CD201").nameArr,
                    dataType : "combo",
                    required : true,
                    editable: false,
                    disable : true
                },

            ]
    }