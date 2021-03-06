package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.StArrangeRecordsResult;

@Repository
public interface StArrangeRecordsResultRepository extends AXBootJPAQueryDSLRepository<StArrangeRecordsResult, StArrangeRecordsResult.StArrangeRecordsResultId> {

}
