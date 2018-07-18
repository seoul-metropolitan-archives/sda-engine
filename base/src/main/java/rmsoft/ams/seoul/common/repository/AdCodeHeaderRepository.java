package rmsoft.ams.seoul.common.repository;

import org.springframework.data.repository.CrudRepository;
import rmsoft.ams.seoul.common.domain.AdCodeHeader;

/**
 * The interface Ad code header repository.
 */
public interface AdCodeHeaderRepository extends CrudRepository<AdCodeHeader, AdCodeHeader.AdCodeHeaderId>
{
}
