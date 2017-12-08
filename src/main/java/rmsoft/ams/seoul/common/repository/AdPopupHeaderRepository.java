package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;

/**
 * The interface Ad popup header repository.
 */
@Repository
public interface AdPopupHeaderRepository extends AXBootJPAQueryDSLRepository<AdPopupHeader, AdPopupHeader.AdPopupHeaderId>
{

}
