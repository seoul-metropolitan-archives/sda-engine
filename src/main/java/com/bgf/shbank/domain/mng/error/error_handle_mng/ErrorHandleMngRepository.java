package com.bgf.shbank.domain.mng.error.error_handle_mng;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorHandleMngRepository extends AXBootJPAQueryDSLRepository<ErrorHandleMng, ErrorHandleMng.ErrorHandleMngId> {
}
