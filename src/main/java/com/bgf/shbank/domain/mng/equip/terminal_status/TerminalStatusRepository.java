package com.bgf.shbank.domain.mng.equip.terminal_status;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalStatusRepository extends AXBootJPAQueryDSLRepository<TerminalStatus, TerminalStatus.TerminalStatusId> {
}
