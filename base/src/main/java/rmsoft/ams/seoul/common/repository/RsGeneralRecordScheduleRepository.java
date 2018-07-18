package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RsGeneralRecordSchedule;

@Repository
public interface RsGeneralRecordScheduleRepository extends AXBootJPAQueryDSLRepository<RsGeneralRecordSchedule, RsGeneralRecordSchedule.RsGeneralRecordScheduleId> {

}
