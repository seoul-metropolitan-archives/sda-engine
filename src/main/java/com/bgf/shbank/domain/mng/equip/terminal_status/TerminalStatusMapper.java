package com.bgf.shbank.domain.mng.equip.terminal_status;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface TerminalStatusMapper extends MyBatisMapper {

    List<TerminalStatus> findAll();

    TerminalStatus findOne(TerminalStatus terminalStatus);

    int update(TerminalStatus terminalStatus);

    int delete(TerminalStatus terminalStatus);

    int insert(TerminalStatus terminalStatus);
}