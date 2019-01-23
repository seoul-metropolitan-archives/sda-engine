var st01301 =
    {
        column_info:
            [
                {
                    sortNo: 0,
                    name: "inoutExceptUuid",
                    text: "INOUTEXCEPT UUID",
                    width: 120,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    width: 280,
                    name: "requestName",
                    text: "의뢰서명",
                    editable: true,
                    disable : false,
                    required : true,
                    dataType : "text"
                },
                {
                    sortNo: 2,
                    name: "requestorUuid",
                    text: "작성자",
                    width: 120,
                    editable: false,
                    disable : true,
                    dataType: "text"
                },
                {
                    sortNo: 3,
                    name: "requestDate",
                    text: "등록일시",
                    width: 150,
                    editable: false,
                    disable: true,
                },

                {
                    sortNo: 4,
                    name: "exceptStartDate",
                    text: "제외 시작 일자",
                    width: 120,
                    editable: true,
                    dataType : "date",
                    required : true,
                    styles: {
                        "datetimeFormat": "yyyy.MM.dd"
                    }
                },
                {
                    sortNo: 5,
                    name: "exceptEndDate",
                    text: "제외 종료 일자",
                    width: 120,
                    editable: true,
                    dataType : "date",
                    required : true,
                    styles: {
                        "datetimeFormat": "yyyy.MM.dd"
                    }
                },
                {
                    sortNo: 8,
                    name: "exceptReason",
                    text: "exceptReason",
                    width: 100,
                    editable: false,
                    visible: false,
                    dataType: "text"
                },
            ]
    }
