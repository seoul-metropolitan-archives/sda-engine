var ac00301 =
    {
        column_info:
            [
                {
                    sortNo: 1,
                    name: "userUuid",
                    text: "userUuid",
                    width: 120,
                    editable: true,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 2,
                    name: "userId",
                    text: "User ID",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true
                },
                {
                    sortNo: 3,
                    name: "userNm",
                    text: "User Name",
                    width: 120,
                    editable: true,
                    dataType: "text",
                    required: true
                },
                {
                    sortNo: 4,
                    name: "userPassword",
                    text: "Password",
                    width: 120,
                    editable: true,
                    dataType: "password",
                    required: true
                },
                {
                    sortNo: 5,
                    name: "passwordUpdateDate",
                    text: "Date/Time Changed PW",
                    width: 150,
                    editable: false,
                    dataType: "timestamp",
                    disable: true
                },
                {
                    sortNo: 6,
                    name: "userTypeUuid",
                    text: "User Type",
                    width: 120,
                    editable: true,
                    dataType: "combo",
                    required: false,
                    visible: true,
                    values: new Array(),
                    labels: new Array(),
                    lookupDisplay: true,
                    editor: {
                        type: "dropDown",
                        dropDownCount: 10,
                        domainOnly: true, //domainOnly가 true이면 목록에 있는 값들만 선택할 수 있습니다.
                        textReadOnly: false, // true이면 키 입력이 안되며 선택만 할 수 있습니다.
                    }
                },
                {
                    sortNo: 7,
                    name: "startProgramUuid",
                    text: "Startup Program",
                    width: 120,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 8,
                    name: "organizationUuid",
                    text: "Organization",
                    width: 120,
                    editable: false,
                    dataType: "text",
                    disable: true
                },
                {
                    sortNo: 9,
                    name: "description",
                    text: "description",
                    width: 120,
                    editable: true,
                    dataType: "richtext",
                    required: false,
                },
                {
                    sortNo: 10,
                    name: "notes",
                    text: "NOTES",
                    width: 120,
                    editable: true,
                    dataType: "richtext",
                    required: false
                },
                {
                    sortNo: 11,
                    name: "useYn",
                    text: "USE",
                    width: 120,
                    editable: true,
                    dataType: "check",
                    textAlignment: "center",
                    renderer: {
                        type: "check",
                        shape: "",
                        falseValues: "false",
                        trueValues: "true",
                        editable: "false",
                        startEditOnClick: true,
                    },
                    required: true
                },
                {
                    sortNo: 12,
                    name: "insertUuid",
                    text: "Created By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 13,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                },
                {
                    sortNo: 14,
                    name: "updateUuid",
                    text: "Modifed By",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 15,
                    name: "updateDate",
                    text: "Date/Time Modified",
                    width: 120,
                    editable: false,
                    disable: true,
                    dataType: "timestamp",
                    required: false
                }
            ]
    }