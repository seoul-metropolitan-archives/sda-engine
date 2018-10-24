package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.AdConMetaSegment;
import rmsoft.ams.seoul.common.domain.AdConMetaSetup;

/**
 * AdGlossaryhRepository
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -11-01 오후 7:59
 */
@Repository
public interface AdConMetaSegmentRepository extends AXBootJPAQueryDSLRepository<AdConMetaSegment, AdConMetaSegment.AdConMetaSegmentId> {
}
