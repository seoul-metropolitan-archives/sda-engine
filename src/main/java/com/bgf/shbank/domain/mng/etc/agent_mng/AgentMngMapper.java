package com.bgf.shbank.domain.mng.etc.agent_mng;

import io.onsemiro.core.mybatis.MyBatisMapper;

import java.util.List;


public interface AgentMngMapper extends MyBatisMapper {

    List<AgentMng> findAll();

    AgentMng findOne(AgentMng agentMng);

    int update(AgentMng agentMng);

    int delete(AgentMng agentMng);

    int insert(AgentMng agentMng);
}