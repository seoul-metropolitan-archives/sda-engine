package rmsoft.ams.seoul.common.repository;

import org.springframework.data.repository.CrudRepository;
import rmsoft.ams.seoul.common.domain.AdCodeHeader;
import rmsoft.ams.seoul.common.domain.AdConfiguration;

public interface AdCodeHeaderRepository extends CrudRepository<AdCodeHeader, AdCodeHeader.AdCodeHeaderId>
{
}
