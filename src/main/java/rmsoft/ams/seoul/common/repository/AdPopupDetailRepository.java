package rmsoft.ams.seoul.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.AdPopupDetail;

@Repository
public interface AdPopupDetailRepository extends CrudRepository<AdPopupDetail,AdPopupDetail.AdPopupDetailId> {
}
