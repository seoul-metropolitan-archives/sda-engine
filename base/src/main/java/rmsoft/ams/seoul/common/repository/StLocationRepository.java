package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.StLocation;

@Repository
public interface StLocationRepository extends AXBootJPAQueryDSLRepository<StLocation, StLocation.StLocationId> {

}
