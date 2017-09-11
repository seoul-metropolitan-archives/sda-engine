package com.bgf.shbank.domain.mng.error.error_status;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorStatusRepository extends AXBootJPAQueryDSLRepository<ErrorStatus, ErrorStatus.ErrorStatusId> {
}
