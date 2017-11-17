package rmsoft.ams.seoul.common.repository;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.RcItem;
import rmsoft.ams.seoul.common.domain.RcItemCon;

@Repository
public interface RcItemConRepository extends AXBootJPAQueryDSLRepository<RcItemCon, RcItemCon.RcItemConId>
{

}
