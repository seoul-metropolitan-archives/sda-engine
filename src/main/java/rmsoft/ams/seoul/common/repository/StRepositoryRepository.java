package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.StRepository;

@Repository
public interface StRepositoryRepository extends AXBootJPAQueryDSLRepository<StRepository, StRepository.StRepositoryId> {

}
