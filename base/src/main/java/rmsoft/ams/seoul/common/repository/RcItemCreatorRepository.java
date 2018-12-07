package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RcItemCreator;

/**
 * The interface Rc aggregation creator repository.
 */
@Repository
public interface RcItemCreatorRepository extends AXBootJPAQueryDSLRepository<RcItemCreator, RcItemCreator.RcItemCreatorId>
{

}
