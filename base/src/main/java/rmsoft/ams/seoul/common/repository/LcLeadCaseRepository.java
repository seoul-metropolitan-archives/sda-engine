package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.LcLeadCase;
import rmsoft.ams.seoul.common.domain.StShelf;

@Repository
public interface LcLeadCaseRepository extends AXBootJPAQueryDSLRepository<LcLeadCase, LcLeadCase.LcLeadCaseId> {

}
