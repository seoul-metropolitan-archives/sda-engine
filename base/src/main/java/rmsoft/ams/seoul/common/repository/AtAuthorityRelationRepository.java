package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.AtAuthorityRelation;

/**
 * The interface Cl class repository.
 */
@Repository
public interface AtAuthorityRelationRepository extends AXBootJPAQueryDSLRepository<AtAuthorityRelation, AtAuthorityRelation.AtAuthorityRelationId>
{

}
