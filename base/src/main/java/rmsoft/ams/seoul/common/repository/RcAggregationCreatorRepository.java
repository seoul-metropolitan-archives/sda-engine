package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RcAggregationCreator;

/**
 * The interface Rc aggregation creator repository.
 */
@Repository
public interface RcAggregationCreatorRepository extends AXBootJPAQueryDSLRepository<RcAggregationCreator, RcAggregationCreator.RcAggregationCreatorId>
{

}
