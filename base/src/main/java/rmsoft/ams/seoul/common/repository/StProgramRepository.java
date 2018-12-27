package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.StProgram;

@Repository
public interface StProgramRepository  extends AXBootJPAQueryDSLRepository<StProgram, StProgram.StProgramId> {
}
