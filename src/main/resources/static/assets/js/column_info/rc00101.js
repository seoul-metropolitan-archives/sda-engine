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
                visible: true
            },
            {
                sortNo: 2,
                name: "code",
                text: "Code",
                width: 130,
                editable: false,
                dataType: "text"
            },
            {
                sortNo: 3,
                name: "title",
                text: "Title",
                width: 500,
                editable: false,
                dataType: "text"
            },
            {
                sortNo: 4,
                name: "level",
                text: "Level",
                width: 110,
                editable: false,
                dataType: "text"
            },
            {
                sortNo: 5,
                name: "type",
                text: "Type",
                width: 100,
                editable: false,
                dataType: "text"
            },
            {
                sortNo: 6,
                name: "publishedStatus",
                text: "Published Status",
                width: 110,
                editable: false,
                dataType: "text"
            },
            {
                sortNo: 7,
                name: "author",
                text: "Author",
                width: 70,
                editable: false,
                dataType: "text"
            },
            {
                sortNo: 8,
                name: "descStartDate",
                text: "Start Date",
                width: 140,
                editable: false,
                dataType: "date"
            },
            {
                sortNo: 9,
                name: "descEndDate",
                text: "End Date",
                width: 140,
                editable: false,
                dataType: "date"
            },
            {
                sortNo: 10,
                name: "description",
                text: "Description",
                width: 250,
                editable: false,
                dataType: "richtext"
            },
            {
                sortNo: 11,
                name: "notes",
                text: "Notes",
                width: 250,
                editable: false,
                dataType: "richtext"
            }
         ]
}