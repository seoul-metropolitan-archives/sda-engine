package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.StContainer;

/**
 * The interface Cl class repository.
 */
@Repository
public interface StContainerRepository extends AXBootJPAQueryDSLRepository<StContainer, StContainer.StContainerId>
{

}
