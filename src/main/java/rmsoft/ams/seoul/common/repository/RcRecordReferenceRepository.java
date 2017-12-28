package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RcRecordReference;

/**
 * The interface Rc record reference repository.
 */
@Repository
public interface RcRecordReferenceRepository extends AXBootJPAQueryDSLRepository<RcRecordReference, RcRecordReference.RcRecordReferenceId>
{

}
