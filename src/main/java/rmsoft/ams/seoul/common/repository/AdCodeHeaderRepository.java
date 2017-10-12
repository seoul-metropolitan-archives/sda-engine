package rmsoft.ams.seoul.common.repository;

import org.springframework.data.repository.CrudRepository;
import rmsoft.ams.seoul.common.domain.AdConfiguration;

public interface AdConfigurationRepository extends CrudRepository<AdConfiguration, AdConfiguration.AdConfigurationId>
{
}
