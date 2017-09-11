package com.bgf.shbank.controller.api.mng.common;

import com.bgf.shbank.domain.mng.common.SearchTerminalService;
import com.bgf.shbank.domain.mng.common.SearchTerminalVO;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/mng/common")
public class SearchTerminalController extends BaseController {

    @Inject
    private SearchTerminalService searchTerminalService;

    @GetMapping(value = "/search_terminal")
    public Responses.PageResponse list(Pageable pageable, RequestParams<SearchTerminalVO> requestParams) {
        Page<SearchTerminalVO> pages = searchTerminalService.findAllTerminal(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping(value = "/search_branch")
    public Responses.PageResponse listBranch(Pageable pageable, RequestParams<SearchTerminalVO> requestParams) {
        Page<SearchTerminalVO> pages = searchTerminalService.findAllBranch(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping(value = "/search_corner")
    public Responses.PageResponse listCorner(Pageable pageable, RequestParams<SearchTerminalVO> requestParams) {
        Page<SearchTerminalVO> pages = searchTerminalService.findAllCorner(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
}