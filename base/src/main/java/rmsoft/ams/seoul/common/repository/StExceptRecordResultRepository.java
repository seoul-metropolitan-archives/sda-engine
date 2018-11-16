package rmsoft.ams.seoul.common.repository;


import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.StExceptRecordResult;

@Repository
public interface StExceptRecordResultRepository extends AXBootJPAQueryDSLRepository<StExceptRecordResult, StExceptRecordResult.StExceptRecordResultId> {
}
