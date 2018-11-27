(function () {

    if (axboot && axboot.def) {

        axboot.def["DEFAULT_TAB_LIST"] = [
            /*{
                menuId: "f529cda4-04ac-4b26-82ab-6b441a3d0015",
                id: "User",
                progNm: 'User',
                menuNm: 'User',
                progPh: '/ac/ac003/ac003',
                url: '/ac/ac003/ac003',
                status: "on",
                fixed: true
            }*/
        ];

        axboot.def["API"] = {
            "users": "/api/v1/user",
            "commonCodes": "/api/v1/common-code",
            "programs": "/api/v1/program",
            "menu": "/api/v1/menu",
            "manual": "/api/v1/manual",
            "errorLogs": "/api/v1/error-log",
            "files": "/api/v1/file",
            "samples": "/api/v1/sample",
            "modelExtractor": "/api/v1/model-extractor",
            "pageGenerator": "/api/v1/page-gen",
        };

        axboot.def["MODAL"] = {
            "ZIPCODE": {
                width: 600,
                height: 560,
                iframe: {
                    url: "/common/zipcode"
                },
                header: {
                    title: "우편번호 찾기"
                }
            },
            "PAGE_MODEL_GEN": {
                width: 420,
                height: 220,
                iframe: {
                    url: "/devtools/page-model-gen"
                },
                header: {
                    title: "Page & Model :: One Click Generator"
                }
            },
            "Sh03001110_M01": {
                width: 580,
                height: 300,
                iframe: {
                    url: "/mng/cash/sh03001110/m01"
                },
                header: {
                    title: ":: 단건 시재조회 ::"
                }
            },
            "CORNER_MANAGE_M01": {
                width: 380,
                height: 300,
                iframe: {
                    url: "/mng/equip/corner_manage/m01"
                },
                header: {
                    title: ":: 스케쥴 등록 ::"
                }
            },
            "SAMPLE_MODAL": {
                width: 500,
                height: 500,
                iframe: {
                    url: "/samples/ax5ui-sample-modal"
                },
                header: {
                    title: "우편번호 찾기"
                }
            },
            "SEARCH_TERMINAL_MODAL": {
                width: 575,
                height: 500,
                iframe: {
                    url: "/mng/common/search-terminal-modal"
                },
                header: {
                    title: "단말번호 검색"
                }
            },
            "SEARCH_BRANCH_MODAL": {
                width: 350,
                height: 500,
                iframe: {
                    url: "/mng/common/search-branch-modal"
                },
                header: {
                    title: "지점 검색"
                }
            },
            "SEARCH_CORNER_MODAL": {
                width: 500,
                height: 500,
                iframe: {
                    url: "/mng/common/search-corner-modal"
                },
                header: {
                    title: "코너 검색"
                }
            },
            "SEARCH_AGENT_MODAL": {
                width: 700,
                height: 500,
                iframe: {
                    url: "/mng/common/search-agent-modal"
                },
                header: {
                    title: "요원정보 검색"
                }
            },
            "COMMON_CODE_MODAL": {
                width: 600,
                height: 400,
                iframe: {
                    url: "/jsp/system/system-config-common-code-modal.jsp"
                },
                header: false
            },
            "COMMON_POPUP": {
                width: 700,
                height: 510,
                iframe: {
                    url: "/common/common-popup"
                },
                header: {
                    title: "팝업"
                }
            },
            "WORKFLOW_POPUP_01": {
                width: 1340,
                height: 430,
                iframe: {
                    url: "/wf/wf003/wf003-p01"
                },
                header: {
                    title: "Job Parameter"
                }
            },
            "INGEST_POPUP" :{
                width: 700,
                height: 650,
                iframe: {
                    url: "/ig/ig002/ig002-p01"
                }

            },
            "INGEST_LIST_POPUP" :{
                width: 1150,
                height: 650,
                iframe: {
                    url: "/ig/ig003/ig003-p01"
                }

            },
            "ARRANGE_POPUP" :{
                width: 1600,
                height: 700,
                iframe: {
                    url: "/st/st003/st003-p01"
                }

            },
            "FREEZE_POPUP" :{
                width: 1600,
                height: 700,
                iframe: {
                    url: "/df/df003/df003-p01"
                }

            },
            "ARRANGE_CONTAINER_POPUP" :{
                width: 1600,
                height: 700,
                iframe: {
                    url: "/st/st004/st004-p01"
                }

            },
            "SCHEDULING_RECORD_POPUP" :{
                width: 1200,
                height: 700,
                iframe: {
                    url: "/rs/rs004/rs004-p01"
                }

            },
            "AUTHORITY_REGISTER_POPUP" :{
                width: 900,
                height: 600,
                iframe: {
                    url: "/at/at001/at001-p01"
                }

            },
            "CLASSIFY_POPUP" :{
                width: 1400,
                height: 700,
                iframe: {
                    url: "/cl/cl003/cl003-p01"
                }

            },
            "MOVE_AGGREGATION": {
                width: 600,
                height: 515,
                iframe: {
                    url: "/rc/rc001/p_rc00101"
                },
                header: {
                    title: "Move"
                }
            },
            "UPDATE_STATE_AGGREGATION_N_ITEM": {
                width: 360,
                height: 120,
                iframe: {
                    url: "/rc/rc001/p_rc00102"
                },
                header: {
                    title: "Update State"
                }
            },
            "ADD_RECORD_ITEM_GRID": {
                width: 1300,
                height: 700,
                iframe: {
                    url: "/rc/rc001/p_rc00103"
                }
            },
            "AUTHORITY_POPUP": {
                width: 450,
                iframe: {
                    url: "/at/at002/at002-p01"
                }
            },
            "RECORD_EXPLORER_CLASSIFY_RECORDS": {
                width: 600,
                height: 700,
                iframe: {
                    url: "/rc/rc001/p_rc00104"
                }
            }
        };
    }

    var preDefineUrls = {
        "manual_downloadForm": "/api/v1/manual/excel/downloadForm",
        "manual_viewer": "/jsp/system/system-help-manual-view.jsp"
    };
    axboot.getURL = function (url) {
        if (ax5.util.isArray(url)) {
            if (url[0] in preDefineUrls) {
                url[0] = preDefineUrls[url[0]];
            }
            return url.join('/');

        } else {
            return url;
        }
    }

    var convertJsonObj = function (jsonStr) {
        return jsonStr == null || jsonStr == '' ? null : JSON.parse(jsonStr.replace(/&quot;/gi, '"'));
    }

})();
