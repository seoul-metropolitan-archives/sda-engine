package ${domainPackageName}.${targetPackageName};

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ${repositoryClassName} extends AXBootJPAQueryDSLRepository<${entityClassName}, ${keyClassRefName}> {
}
