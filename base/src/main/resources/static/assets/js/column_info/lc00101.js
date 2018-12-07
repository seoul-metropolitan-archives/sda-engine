var lc00101 =
{
    column_info :
        [
            {
                sortNo: 0,
                name: "leadCaseNo",
                text: "LC No.",
                editable: false,
                disable : false,
                dataType: "text",
                required: false,
                visible: true
            },
            {
                sortNo: 1,
                name: "leadCaseName",
                text: "LC Name",
                editable: true,
                width : 130,
                dataType: "code",
                required: true,
                visible: true
            },
            {
                sortNo: 2,
                name: "majorClassificationUuid",
                text: "구분(대)",
                width: 120,
                editable: true,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD211").codeArr,
                labels: axboot.commonCodeFilter("CD211").nameArr
            },
            {
                sortNo: 3,
                name: "middleClassificationUuid",
                text: "구분(중)",
                width: 120,
                editable: true,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD212").codeArr,
                labels: axboot.commonCodeFilter("CD212").nameArr

            },
            {
                sortNo: 4,
                name: "occupation",
                text: "소속/직업",
                width: 200,
                editable: true,
                dataType: "text",
                visible: true
            },
            {
                sortNo: 5,
                name: "region",
                text: "지역",
                width: 200,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 6,
                name: "address",
                text: "주소",
                width: 185,
                editable: true,
                dataType: "text",
            },
            {
                sortNo: 7,
                name: "contactTarget",
                text: "접촉대상",
                width: 185,
                editable: true,
                dataType: "richtext",
                required: false
            },
            {
                sortNo: 11,
                name: "phone",
                text: "연락처",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 11,
                name: "personInCharge",
                text: "담당자",
                width: 110,
                editable: true,
                dataType: "text",
                required: false,
                visible : false
            },
            {
                sortNo: 11,
                name: "collectStatusUuid",
                text: "수집현황",
                width: 110,
                editable: true,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD213").codeArr,
                labels: axboot.commonCodeFilter("CD213").nameArr
            },
            {
                sortNo: 12,
                name: "leadCaseUuid",
                editable: false,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 13,
                name : "ownMaterial",
                visible:false
            },
            {
                sortNo: 14,
                name : "childCnt",
                visible:false
            }
        ]
}