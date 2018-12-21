package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.JobConv;

/**
 * The interface Rc component repository.
 */
@Repository
public interface JobConvRepository extends AXBootJPAQueryDSLRepository<JobConv, JobConv.JobConvId>
{

}
