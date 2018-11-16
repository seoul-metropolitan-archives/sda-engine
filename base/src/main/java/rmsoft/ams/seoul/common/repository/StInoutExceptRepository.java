package rmsoft.ams.seoul.common.repository;


import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.StInoutExcept;

@Repository
public interface StInoutExceptRepository extends AXBootJPAQueryDSLRepository<StInoutExcept, StInoutExcept.StInoutExceptId> {
}
