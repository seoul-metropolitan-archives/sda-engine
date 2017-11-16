package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.data.repository.CrudRepository;
import rmsoft.ams.seoul.common.domain.AdCodeDetail;

public interface AdCodeDetailRepository extends AXBootJPAQueryDSLRepository<AdCodeDetail, AdCodeDetail.AdCodeDetailId>
{
}
