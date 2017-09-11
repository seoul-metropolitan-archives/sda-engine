package com.bgf.shbank.domain.mng.equip.overhaul_plan;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverhaulPlanRepository extends AXBootJPAQueryDSLRepository<OverhaulPlan, OverhaulPlan.OverhaulPlanId> {
}
