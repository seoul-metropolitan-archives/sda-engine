package com.bgf.shbank.domain.mng.etc.agent_mng;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class AgentMngService extends BaseService<AgentMng, AgentMng.AgentMngId> {

    @Inject
    public AgentMngService(AgentMngRepository agentMngRepository) {
        super(agentMngRepository);
    }

    public Page<AgentMng> find(Pageable pageable, RequestParams<AgentMngVO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String empName = requestParams.getString("empName");

        QAgentMng qAgentMng = QAgentMng.agentMng;
        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qAgentMng.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(empName)) {
            builder.and(qAgentMng.empName.like('%' + empName + '%'));
        }

        List<AgentMng> resultList = select().from(qAgentMng).where(builder).fetch();
        return filter(resultList, pageable, filter, AgentMng.class);
    }

    public List<AgentMng> find(RequestParams<AgentMngVO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String empName = requestParams.getString("empName");

        QAgentMng qAgentMng = QAgentMng.agentMng;
        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qAgentMng.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(empName)) {
            builder.and(qAgentMng.empName.like('%' + empName + '%'));
        }

        List<AgentMng> resultList = select().from(qAgentMng).where(builder).fetch();
        return filter(resultList, filter);
    }
}