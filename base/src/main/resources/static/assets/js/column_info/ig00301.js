var ig00301 =
    {
        column_info :
            [
                {
                    sortNo: 0,
                    name: "accessionRecordUuid",
                    text: "ACCESSION RECORD UUID",
                    editable: false,
                    dataType: "code",
                    required: false,
                    visible: false
                },
                {
                    sortNo: 1,
                    name: "accessionNo",
                    text: "Accession No",
                    width: 130,
                    editable: false,
                    styles: {
                        color:"#ff0000",
                        textAlignment: "near",
                        fontSize: 12,
                        fontUnderline:true,
                        fontFamily: "nanum",
                        fontBold: false
                    }
                },
                {
                    sortNo: 2,
                    name: "acquisitionDate",
                    text: "Acquisition Date",
                    width: 130,
                    editable: false,
                    dataType: "date",
                    format:'yyyy-mm-dd'
                },
                {
                    sortNo: 3,
                    name: "acquisitionSource",
                    text: "Source of Acquisition ",
                    width: 550,
                    editable: false,
                    dataType: "text"
                },
                {
                    sortNo: 4,
                    name: "title",
                    text: "Title",
                    width: 550,
                    editable: false,
                    dataType: "text"
                },
                {
                    sortNo: 5,
                    name: "acquisitionTypeUuid",
                    text: "Acquisition Type",
                    width: 110,
                    editable: false,
                    dataType: "text",
                    visible: false
                },
                {
                    sortNo: 9,
                    name: "insertUuid",
                    text: "Created By",
                    width: 80,
                    dataType: "text",
                    editable : false,
                    readonly : true,
                    visible: true,
                    required: false,
                    disable: true
                },
                {
                    sortNo: 10,
                    name: "insertDate",
                    text: "Date/Time Created",
                    width: 140,
                    dataType: "timestamp",
                    required: false,
                    disable: true
                }
            ]
    }