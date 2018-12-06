package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.LcLeadCaseSchedule;

@Repository
public interface LcLeadCaseScheduleRepository extends AXBootJPAQueryDSLRepository<LcLeadCaseSchedule, LcLeadCaseSchedule.LcLeadCaseScheduleId> {

}
