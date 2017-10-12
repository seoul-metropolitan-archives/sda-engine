package rmsoft.ams.seoul.common.repository;

import org.springframework.data.repository.CrudRepository;
import rmsoft.ams.seoul.common.domain.AdCodeDetail;

public interface AdCodeDetailRepository extends CrudRepository<AdCodeDetail, AdCodeDetail.AdCodeDetailId>
{
}
