package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RcComponent;

/**
 * The interface Rc component repository.
 */
@Repository
public interface RcComponentRepository extends AXBootJPAQueryDSLRepository<RcComponent, RcComponent.RcComponentId>
{

}
