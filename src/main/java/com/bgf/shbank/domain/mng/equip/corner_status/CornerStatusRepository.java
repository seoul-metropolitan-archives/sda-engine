package com.bgf.shbank.domain.mng.equip.corner_status;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CornerStatusRepository extends AXBootJPAQueryDSLRepository<CornerStatus, CornerStatus.CornerStatusId> {
}
