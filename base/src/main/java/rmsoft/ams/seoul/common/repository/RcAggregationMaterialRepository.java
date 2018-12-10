package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RcAggregationMaterial;

/**
 * The interface Rc aggregation material repository.
 */
@Repository
public interface RcAggregationMaterialRepository extends AXBootJPAQueryDSLRepository<RcAggregationMaterial, RcAggregationMaterial.RcAggregationMaterialId>
{

}
