var st02901 =
    {
        column_info:
            [
                {
                    sortNo: 0,
                    name: "programUuid",
                    text: "PROGRAM_UUID",
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
                    width: 80,
                    name: "machineTypeUuid",
                    text: "장비유형",
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
                    name: "machineName",
                    text: "장비명",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 4,
                    name: "programName",
                    text: "프로그램 명",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 5,
                    name: "version",
                    text: "버전",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 6,
                    name: "description",
                    text: "비고",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 7,
                    dataType: "file",
                    defaultValue: "",
                    editable: true,
                    name: "uploadFilePath",
                    required: true,
                    text: "파일",
                    textAlignment: "left",
                    treeColumnYN: "N",
                    treeRelationType: "",
                    visible: true,
                    width: 210,
                },
                {
                    sortNo: 8,
                    name: "insertUuid",
                    text: "Created By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text"
                },
                {
                    sortNo: 9,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp"
                }
            ]
    }