package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.StGate;

@Repository
public interface StGateRepository extends AXBootJPAQueryDSLRepository<StGate, StGate.StGateId> {
}
