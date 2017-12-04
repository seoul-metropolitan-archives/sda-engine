package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RcAggregationCon;
import rmsoft.ams.seoul.common.domain.RcItemCon;

@Repository
public interface RcAggregationConRepository extends AXBootJPAQueryDSLRepository<RcAggregationCon, RcAggregationCon.RcAggregationConId>
{

}
