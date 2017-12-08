package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.ClClassCon;

/**
 * The interface Cl class con repository.
 */
@Repository
public interface ClClassConRepository extends AXBootJPAQueryDSLRepository<ClClassCon, ClClassCon.ClClassConId>
{

}
