package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RcAggrRelatedAuthority;

/**
 * The interface Rc aggregation repository.
 */
@Repository
public interface RcAggrRelatedAuthorityRepository extends AXBootJPAQueryDSLRepository<RcAggrRelatedAuthority, RcAggrRelatedAuthority.RcAggrRelatedAuthorityId>
{

}
