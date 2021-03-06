var st00801 =
    {
        column_info :
            [
                /****************************************************
                 * sortNo: grid column number
                 * name: 바인딩명 - VO 변수명과 일치
                 * text: 그리드 헤더 텍스트
                 * dataType : 입력방법
                 * editable : 활성비활성 여유
                 * required : 필수 여부
                 * visible  : grid 포함여부
                 ****************************************************/
                {
                    sortNo: 0,
                    width:30,
                    name: "takeoutRequestUuid",
                    text: "takeoutRequestUuid",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 0,
                    width:30,
                    name: "requestTypeUuid",
                    text: "requestTypeUuid",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 0,
                    width:30,
                    name: "requestorUuid",
                    text: "requestorUuid",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 0,
                    width:30,
                    name: "statusUuid",
                    text: "statusUuid",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },

                {
                    sortNo: 0,
                    width:30,
                    name: "outsourcingDepartment",
                    text: "outsourcingDepartment",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 0,
                    width:30,
                    name: "outsourcingPosition",
                    text: "outsourcingPosition",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 0,
                    width:30,
                    name: "outsourcingPersonName",
                    text: "outsourcingPersonName",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 0,
                    width:30,
                    name: "outsourcingPhone",
                    text: "outsourcingPhone",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },

                {
                    sortNo: 1,
                    name: "requestName",
                    text: "반출의뢰서",
                    width: 100,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 2,
                    name: "requestorName",
                    text: "반출자",
                    width: 100,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 3,
                    name: "userGroupName",
                    text: "소속",
                    width: 100,
                    editable: false,
                    disable: true,
                    dataType: "text",
                    required: false
                },

                {
                    sortNo: 4,
                    name: "takeoutDate",
                    text: "반출일자",
                    width: 120,
                    dataType: "date",
                    editable: false,
                    sortable: true,
                    disable:true
                },
                {
                    sortNo: 5,
                    name: "returnDueDate",
                    text: "반입예정일",
                    width: 100,
                    dataType: "date",
                    required: false,
                    editable: false,
                    disable:true
                },

                {
                    sortNo: 6,
                    name: "status",
                    text: "상태",
                    width: 120,
                    dataType: "text",
                    editable: false,
                    sortable: true,
                    disable:true
                },
                {
                    sortNo: 7,
                    name: "takeoutPropose",
                    text: "takeoutPropose",
                    width: 120,
                    dataType: "text",
                    editable: false,
                    sortable: true,
                    disable:true,
                    visible: false
                },


            ]
    }
