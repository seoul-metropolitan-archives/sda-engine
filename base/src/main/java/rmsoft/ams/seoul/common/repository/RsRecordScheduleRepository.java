package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RsRecordSchedule;

@Repository
public interface RsRecordScheduleRepository extends AXBootJPAQueryDSLRepository<RsRecordSchedule, RsRecordSchedule.RsRecordScheduleId> {

}
