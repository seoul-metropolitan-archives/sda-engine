var at00101 =
    {
        column_info :
            [
                {
                    sortNo: 0,
                    name: "authorityUuid",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "authorityNo",
                    text: "Authority No",
                    editable: false,
                    width:170,
                    dataType: "text",
                    required: false
                },
                {
                    sortNo: 2,
                    name: "authorityName",
                    text: "Title",
                    dataType: "text",
                    width:200,
                    editable: false
                },
                // {
                //     sortNo: 3,
                //     name: "authorityTypeUuid",
                //     width:100,
                //     editable: false,
                //     text: "전거 유형",
                //     dataType: "combo",
                //     values: axboot.commonCodeFilter("CD161").codeArr,
                //     labels: axboot.commonCodeFilter("CD161").nameArr
                // },
                {
                    sortNo: 4,
                    width:100,
                    name: "orgTypeUuid",
                    text: "조직 유형",
                    editable: false,
                    dataType: "combo",
                    values: axboot.commonCodeFilter("CD163").codeArr,
                    labels: axboot.commonCodeFilter("CD163").nameArr
                }
            ]
    }