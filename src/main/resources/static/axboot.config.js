(function () {

    if (axboot && axboot.def) {

        if (sessionJson.menuGrpCd == 'SHINHAN_USER') {
            axboot.def["DEFAULT_TAB_LIST"] = [
                {
                    menuId: "corner_manage",
                    id: "error-monitoring",
                    progNm: '환경/시설물 관리',
                    menuNm: '홈',
                    progPh: '/mng/equip/corner_manage',
                    url: '/mng/equip/corner_manage',
                    status: "on",
                    fixed: true
                }
            ];
        } else {
            axboot.def["DEFAULT_TAB_LIST"] = [
                {
                    menuId: "error-monitoring",
                    id: "error-monitoring",
                    progNm: '장애 모니터링',
                    menuNm: '홈',
                    progPh: '/mng/error/error_status',
                    url: '/mng/error/error_status',
                    status: "on",
                    fixed: true
                }
            ];
        }

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
