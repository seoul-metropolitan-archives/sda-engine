var st02601 =
    {
        column_info:
            [
                {
                    sortNo: 0,
                    name: "rfidMachineUuid",
                    text: "RFID_MACHINE_UUID UUID",
                    width: 120,
                    dataType: "code",
                    required: false,
                    visible: false
                },
               /* {
                    sortNo: 1,
                    name: "no",
                    text: "NO",
                    width: 100,
                    dataType: "text",
                    disable : true,
                    required: true,
                    visible: true
                },*/
                {
                    sortNo: 2,
                    width: 100,
                    name: "machineTypeUuid",
                    text: "장비종류",
                    editable: true,
                    disable : false,
                    required: true,
                    visible: true,
                    values: axboot.commonCodeFilter("CD200").codeArr,
                    labels: axboot.commonCodeFilter("CD200").nameArr,
                    dataType : "combo"
                },
                {
                    sortNo: 3,
                    name: "machineId",
                    text: "장비 ID",
                    width: 200,
                    editable: true,
                    required: true,
                    disable : false,
                    visible: true,
                    dataType: "text"
                },
                {
                    sortNo: 4,
                    name: "machineName",
                    text: "장비명",
                    width: 200,
                    editable: true,
                    required: true,
                    visible: true,
                    dataType: "text"
                },
                {
                    sortNo:5,
                    name: "macAddr",
                    text: "Mac Addr",
                    width: 200,
                    editable: true,
                    required: false,
                    visible: true,
                    dataType: "text"
                },
                {
                    sortNo:6,
                    name: "ip",
                    text: "IP",
                    width: 200,
                    editable: true,
                    dataType : "text",
                    visible: true,
                    required : true
                },
                {
                    sortNo: 7,
                    name: "subnetmask",
                    text: "SubnetMask",
                    width: 200,
                    editable: true,
                    dataType : "text",
                    visible: true,
                    required : true
                },
                {
                    sortNo: 8,
                    name: "gateway",
                    text: "Gateway",
                    width: 200,
                    editable: true,
                    visible: true,
                    required : true,
                    dataType: "text"
                },

            ]
    }
