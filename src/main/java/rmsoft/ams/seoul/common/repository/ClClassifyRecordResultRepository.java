package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.ClClassifyRecordsResult;

/**
 * The interface Cl classification scheme repository.
 */
@Repository
public interface ClClassifyRecordResultRepository extends AXBootJPAQueryDSLRepository<ClClassifyRecordsResult, ClClassifyRecordsResult.ClClassifyRecordsId>
{

}
