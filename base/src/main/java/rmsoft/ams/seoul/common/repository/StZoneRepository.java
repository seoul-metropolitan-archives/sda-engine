package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.StZone;

@Repository
public interface StZoneRepository extends AXBootJPAQueryDSLRepository<StZone, StZone.StZoneId> {
}
