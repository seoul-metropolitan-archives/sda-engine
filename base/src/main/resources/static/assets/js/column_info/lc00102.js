var lc00102 =
    {
        column_info :
            [
                {
                    sortNo: 0,
                    name: "scheduleNo",
                    text: "일련번호",
                    editable: false,
                    disable : false,
                    dataType: "text",
                    required: false,
                    visible: true
                },
                {
                    sortNo: 1,
                    name: "ingestOrgUuid",
                    text: "입수처",
                    editable: true,
                    width : 130,
                    dataType: "text",
                    required: true,
                    visible: true
                },
                {
                    sortNo: 2,
                    name: "authorityName",
                    text: "생산자",
                    width: 120,
                    editable: true,
                    visible: true,
                    dataType: "popup",
                    popupCode : "AUTHORITY_POPUP",
                    popupCallback: fnObj.gridView01.popupCallback,
                    sqlColumn : {AUTHORITY_UUID : "creatorUuid",AUTHORITY_TYPE_UUID : "AUTHORITY_NO",AUTHORITY_NO : "authorityNo",AUTHORITY_NAME : "authorityName", ORG_TYPE_UUID: "orgTypeUuid"},
                    required: false
                },
                {
                    sortNo: 3,
                    name: "collectionTypeUuid",
                    text: "수집구분",
                    width: 120,
                    editable: true,
                    dataType: "combo",
                    required:true,
                    values: axboot.commonCodeFilter("CD214").codeArr,
                    labels: axboot.commonCodeFilter("CD214").nameArr
                },
                {
                    sortNo: 4,
                    name: "contactTypeUuid",
                    text: "접촉방법",
                    width: 120,
                    editable: true,
                    required:true,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD215").codeArr,
                    labels: axboot.commonCodeFilter("CD215").nameArr
                },
                {
                    sortNo: 5,
                    name: "contactMaker",
                    text: "접촉대상자",
                    width: 200,
                    editable: true,
                    dataType: "text"
                },
                {
                    sortNo: 6,
                    name: "collectMaker",
                    text: "수집대상자",
                    width: 185,
                    editable: true,
                    dataType: "text",
                },
                {
                    sortNo: 7,
                    name: "collectionDate",
                    text: "수집일자",
                    width: 185,
                    editable: true,
                    dataType: "date",
                    required: true
                },
                {
                    sortNo: 11,
                    name: "collectionContents",
                    visible : false
                },
                {
                    sortNo: 12,
                    name: "leadCaseUuid",
                    visible: false
                },
                {
                    sortNo: 13,
                    name : "leadCaseScheduleUuid",
                    visible:false
                },
                {
                    sortNo: 13,
                    name : "creatorUuid",
                    visible:false
                }

            ]
    }