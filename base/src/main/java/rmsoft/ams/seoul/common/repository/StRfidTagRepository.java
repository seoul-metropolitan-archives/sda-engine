package rmsoft.ams.seoul.common.repository;


import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

import rmsoft.ams.seoul.common.domain.StRfidTag;

@Repository
public interface StRfidTagRepository extends AXBootJPAQueryDSLRepository<StRfidTag, StRfidTag.StRfidTagId> {

}
