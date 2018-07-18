package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RsRecordScheduleResult;

@Repository
public interface RsRecordScheduleResultRepository extends AXBootJPAQueryDSLRepository<RsRecordScheduleResult, RsRecordScheduleResult.RsRecordScheduleResultId> {

}
