var rc00101 =
{
    column_info :
        [
            {
                sortNo: 0,
                name: "uuid",
                text: "UUID",
                editable: false,
                dataType: "code",
                required: false,
                visible: false
            },
            {
                sortNo: 0,
                name: "nodeType",
                dataType: "text",
                visible: false
            },
            {
                sortNo: 1,
                name: "iconType",
                text: " ",
                width: 30,
                imageList : "iconList1",
                dynamicStyles : [
                    {criteria : "value='file'",styles : "iconIndex=0"}
                    ,{criteria : "value='file_v'",styles : "iconIndex=1"}
                    ,{criteria : "value='folder'",styles : "iconIndex=2"}
                    ,{criteria : "value='folder_t'",styles : "iconIndex=3"}
                    ,{criteria : "value='folder_v'",styles : "iconIndex=4"}
                    ,{criteria : "value='image'",styles : "iconIndex=5"}
                    ,{criteria : "value='image_v'",styles : "iconIndex=6"}
                    ,{criteria : "value='folder_open'",styles : "iconIndex=7"}
                    ,{criteria : "value='folder_open_t'",styles : "iconIndex=8"}
                    ,{criteria : "value='folder_open_v'",styles : "iconIndex=9"}
                ],
                values : ["file","file_v","folder","folder_t","folder_v","image","image_v","folder_open","folder_open_t","folder_open_v"],
                labels : ["file","file_v","folder","folder_t","folder_v","image","image_v","folder_open","folder_open_t","folder_open_v"],
                dataType: "icon",
                visible: true,
                disable: true,
                editable : false
            },
            {
                sortNo: 2,
                name: "code",
                text: "Code",
                width: 120,
                editable: false,
                dataType: "text",
                disable: true
            },
            {
                sortNo: 3,
                name: "title",
                text: "Title",
                width: 400,
                required: true,
                editable: true,
                dataType: "text"
            },
            {
                sortNo: 4,
                name: "type",
                text: "Type",
                width: 80,
                editable: false,
                dataType: "text",
                disable: true
            },
            {
                sortNo: 5,
                name: "publishedStatusUuid",
                text: "Published Status",
                width: 100,
                editable: true,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD121").codeArr,
                labels: axboot.commonCodeFilter("CD121").nameArr,
            },
            {
                sortNo: 0,
                name: "recordCreatorUuid",
                dataType: "code",
                visible: false
            },
            {
                sortNo: 0,
                name: "creatorUuid",
                dataType: "code",
                visible: false
            },
            {
                sortNo: 6,
                name: "creatorName",
                text: "Creator",
                width: 150,
                editable: true,
                dataType: "popup",
                popupCode: "AUTHORITY_POPUP",
                sqlColumn: {AUTHORITY_UUID: "creatorUuid", AUTHORITY_NAME: "creatorName"},
            },
            {
                sortNo: 7,
                name: "levelUuid",
                text: "Level",
                width: 110,
                editable: true,
                dataType: "combo",
            },
            {
                sortNo: 8,
                name: "legalStatusUuid",
                text: "Legal Status",
                width: 100,
                editable: true,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD203").codeArr,
                labels: axboot.commonCodeFilter("CD203").nameArr,
            },
            {
                sortNo: 0,
                name: "repositoriesUuid",
                dataType: "code",
                visible: false
            },
            {
                sortNo: 9,
                name: "repositoriesName",
                text: "Repositories",
                width: 150,
                editable: true,
                dataType: "popup",
                popupCode: "AUTHORITY_POPUP",
                sqlColumn: {AUTHORITY_UUID: "repositoriesUuid", AUTHORITY_NAME: "repositoriesName"},
            },
            {
                sortNo: 10,
                name: "electronicRecordStatusUuid",
                text: "Format of Archival Materials",
                width: 150,
                editable: true,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD204").codeArr,
                labels: axboot.commonCodeFilter("CD204").nameArr,
            },
            {
                sortNo: 11,
                text: "Date of Creation",
                editable: false,
                width : 200,
                dataType: "text",
                header :{styles:{
                        background: "linear,#f2f2f2",
                        fontSize: 7,
                        fontFamily: "nanum",
                        foreground: "#000000",
                        borderRight: "#cccccc, 1",
                        borderBottom: "#cccccc, 1",
                        fontBold: false
                    }},
                columnList : [
                    {
                        sortNo: 1,
                        name: "creationStartDate",
                        text: "Start",
                        width: 100,
                        editable: true,
                        dataType: "text",
                        length: 8,
                    },
                    {
                        sortNo: 2,
                        name: "creationEndDate",
                        text: "End",
                        width: 100,
                        editable: true,
                        dataType: "text",
                        length: 8,
                    }
                ]
            },
            {
                sortNo: 12,
                text: "Date of Accumulation",
                editable: false,
                width : 200,
                dataType: "text",
                header :{styles:{
                        background: "linear,#f2f2f2",
                        fontSize: 7,
                        fontFamily: "nanum",
                        foreground: "#000000",
                        borderRight: "#cccccc, 1",
                        borderBottom: "#cccccc, 1",
                        fontBold: false
                    }},
                columnList : [
                    {
                        sortNo: 1,
                        name: "accumulationStartDate",
                        text: "Start",
                        width: 100,
                        editable: true,
                        dataType: "text",
                        length: 8,
                    },
                    {
                        sortNo: 2,
                        name: "accumulationEndDate",
                        text: "End",
                        width: 100,
                        editable: true,
                        dataType: "text",
                        length: 8,
                    }
                ]
            },
            {
                sortNo: 13,
                name: "scopeContent",
                text: "Scope & Content",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 0,
                name: "recordRelatedAuthorityUuid",
                dataType: "code",
                visible: false
            },
            {
                sortNo: 0,
                name: "authorityUuid",
                dataType: "code",
                visible: false
            },
            {
                sortNo: 14,
                name: "authorityName",
                text: "Access Points",
                width: 150,
                editable: true,
                dataType: "popup",
                popupCode: "AUTHORITY_POPUP",
                sqlColumn: {AUTHORITY_UUID: "authorityUuid", AUTHORITY_NAME: "authorityName"},
            },
            {
                sortNo: 0,
                name: "sourceAcquisitionUuid",
                dataType: "code",
                visible: false
            },
            {
                sortNo: 15,
                name: "sourceAcquisitionName",
                text: "Immediate Source of Acquisition",
                width: 150,
                editable: true,
                dataType: "popup",
                popupCode: "AUTHORITY_POPUP",
                sqlColumn: {AUTHORITY_UUID: "sourceAcquisitionUuid", AUTHORITY_NAME: "sourceAcquisitionName"},
            },
            {
                sortNo: 16,
                name: "languageCode",
                text: "Language",
                width: 100,
                editable: true,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD153").codeArr,
                labels: axboot.commonCodeFilter("CD153").nameArr,
            },
            {
                sortNo: 17,
                name: "custodialHistory",
                text: "Custodial History",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 18,
                name: "accessCondition",
                text: "Access Condition",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 19,
                name: "useCondition",
                text: "Use Condition",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 20,
                name: "findingAids",
                text: "Finding Aids",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 21,
                name: "author",
                text: "Actors of Description",
                width: 70,
                editable: true,
                dataType: "text",
            },
            {
                sortNo: 22,
                text: "Date of Description",
                editable: false,
                width : 200,
                dataType: "text",
                header :{styles:{
                        background: "linear,#f2f2f2",
                        fontSize: 7,
                        fontFamily: "nanum",
                        foreground: "#000000",
                        borderRight: "#cccccc, 1",
                        borderBottom: "#cccccc, 1",
                        fontBold: false
                    }},
                columnList : [
                    {
                        sortNo: 1,
                        name: "descriptionStartDate",
                        text: "Start",
                        width: 100,
                        editable: true,
                        dataType: "text",
                        length: 8,
                    },
                    {
                        sortNo: 2,
                        name: "descriptionEndDate",
                        text: "End",
                        width: 100,
                        editable: true,
                        dataType: "text",
                        length: 8,
                    }
                ]
            },
            {
                sortNo: 23,
                name: "rulesConversionUuid",
                text: "Rules or Conventions",
                width: 150,
                editable: true,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD206").codeArr,
                labels: axboot.commonCodeFilter("CD206").nameArr,
            },
            {
                sortNo: 24,
                name: "statusDescription",
                text: "Status of Description",
                width: 100,
                editable: true,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD205").codeArr,
                labels: axboot.commonCodeFilter("CD205").nameArr,
            },
            {
                sortNo: 25,
                name: "levelOfDetailUuid",
                text: "Level of Detail",
                width: 100,
                editable: true,
                dataType: "combo",
                values: axboot.commonCodeFilter("CD164").codeArr,
                labels: axboot.commonCodeFilter("CD164").nameArr,
            },
            {
                sortNo: 26,
                name: "description",
                text: "Archivist's Notes",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 27,
                name: "notes",
                text: "Notes",
                width: 250,
                editable: true,
                dataType: "richtext"
            },
            {
                sortNo: 100,
                name: "arrangement",
                dataType: "richtext",
                visible : false
            },
            {
                sortNo: 100,
                name: "accruals",
                dataType: "richtext",
                visible : false
            },
            {
                sortNo: 100,
                name: "recordScheduleUuid",
                dataType: "code",
                visible : false
            },
            {
                sortNo: 100,
                name: "recordScheduleUuid",
                dataType: "code",
                visible : false
            }
         ]
}