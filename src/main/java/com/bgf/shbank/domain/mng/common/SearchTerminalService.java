package com.bgf.shbank.domain.mng.common;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SearchTerminalService extends BaseService {

    @Inject
    public SearchTerminalMapper searchTerminalMapper;

    public List<SearchTerminalVO> findAllTerminal(SearchTerminalVO searchTerminalVO) {
        return searchTerminalMapper.findAllTerminal(searchTerminalVO);
    }

    public List<SearchTerminalVO> findAllBranch(SearchTerminalVO searchTerminalVO) {
        return searchTerminalMapper.findAllBranch(searchTerminalVO);
    }

    public List<SearchTerminalVO> findAllCorner(SearchTerminalVO searchTerminalVO) {
        return searchTerminalMapper.findAllCorner(searchTerminalVO);
    }

    public Page<SearchTerminalVO> findAllTerminal(Pageable pageable, RequestParams<SearchTerminalVO> requestParams) {
        String filter = requestParams.getString("filter");

        SearchTerminalVO searchTerminalVO = new SearchTerminalVO();
        searchTerminalVO.setJisaCode(requestParams.getString("jisa"));

        return filter(findAllTerminal(searchTerminalVO), pageable, filter, SearchTerminalVO.class);
    }

    public Page<SearchTerminalVO> findAllBranch(Pageable pageable, RequestParams<SearchTerminalVO> requestParams) {
        String filter = requestParams.getString("filter");

        SearchTerminalVO searchTerminalVO = new SearchTerminalVO();
        searchTerminalVO.setJisaCode(requestParams.getString("jisa"));

        return filter(findAllBranch(searchTerminalVO), pageable, filter, SearchTerminalVO.class);
    }

    public Page<SearchTerminalVO> findAllCorner(Pageable pageable, RequestParams<SearchTerminalVO> requestParams) {
        String filter = requestParams.getString("filter");

        SearchTerminalVO searchTerminalVO = new SearchTerminalVO();
        searchTerminalVO.setJisaCode(requestParams.getString("jisa"));

        return filter(findAllCorner(searchTerminalVO), pageable, filter, SearchTerminalVO.class);
    }
}