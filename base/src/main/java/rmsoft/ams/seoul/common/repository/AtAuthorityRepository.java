package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.AtAuthority;
import rmsoft.ams.seoul.common.domain.ClClass;

/**
 * The interface Cl class repository.
 */
@Repository
public interface AtAuthorityRepository extends AXBootJPAQueryDSLRepository<AtAuthority, AtAuthority.AtAuthorityId>
{

}
