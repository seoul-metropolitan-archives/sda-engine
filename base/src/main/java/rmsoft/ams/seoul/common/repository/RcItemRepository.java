package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RcItem;

/**
 * The interface Rc item repository.
 */
@Repository
public interface RcItemRepository extends AXBootJPAQueryDSLRepository<RcItem, RcItem.RcItemId>
{

}
