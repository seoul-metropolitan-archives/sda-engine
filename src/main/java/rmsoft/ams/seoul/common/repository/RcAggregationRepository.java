package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RcAggregation;
import rmsoft.ams.seoul.common.domain.RcAggregationCon;

@Repository
public interface RcAggregationRepository extends AXBootJPAQueryDSLRepository<RcAggregation, RcAggregation.RcAggregationId>
{

}
