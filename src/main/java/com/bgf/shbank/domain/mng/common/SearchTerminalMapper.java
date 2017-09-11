package com.bgf.shbank.domain.mng.common;

import io.onsemiro.core.mybatis.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchTerminalMapper extends MyBatisMapper {
    List<SearchTerminalVO> findAllTerminal(SearchTerminalVO searchTerminalVO);

    List<SearchTerminalVO> findAllBranch(SearchTerminalVO searchTerminalVO);

    List<SearchTerminalVO> findAllCorner(SearchTerminalVO searchTerminalVO);
}