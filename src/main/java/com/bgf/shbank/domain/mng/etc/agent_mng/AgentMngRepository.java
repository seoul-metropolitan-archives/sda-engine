package com.bgf.shbank.domain.mng.etc.agent_mng;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentMngRepository extends AXBootJPAQueryDSLRepository<AgentMng, AgentMng.AgentMngId> {
}
