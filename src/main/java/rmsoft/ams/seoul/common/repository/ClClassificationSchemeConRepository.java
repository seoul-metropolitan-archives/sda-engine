package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.ClClassificationSchemeCon;

/**
 * The interface Cl classification scheme con repository.
 */
@Repository
public interface ClClassificationSchemeConRepository extends AXBootJPAQueryDSLRepository<ClClassificationSchemeCon, ClClassificationSchemeCon.ClClassificationSchemeId>
{

}
