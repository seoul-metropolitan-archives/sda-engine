package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.ClClassificationScheme;

/**
 * The interface Cl classification scheme repository.
 */
@Repository
public interface ClClassificationSchemeRepository extends AXBootJPAQueryDSLRepository<ClClassificationScheme, ClClassificationScheme.ClClassificationSchemeId>
{

}
