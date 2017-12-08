package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.AcUser;

/**
 * AcUserRepository
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-12 오후 1:59
 */
@Repository
public interface AcUserRepository extends AXBootJPAQueryDSLRepository<AcUser, AcUser.AcUserId> {
}
