package com.bgf.shbank.domain.mng.equip.facility_status;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityStatusRepository extends AXBootJPAQueryDSLRepository<FacilityStatus, FacilityStatus.FacilityStatusId> {
}
