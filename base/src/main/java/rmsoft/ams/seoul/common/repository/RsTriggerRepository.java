package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RsTrigger;

@Repository
public interface RsTriggerRepository extends AXBootJPAQueryDSLRepository<RsTrigger, RsTrigger.RsTriggerId> {

}
