package ${domainPackageName}.${targetPackageName};

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.inject.Inject;

@Service
public class ${serviceClassName} extends BaseService<${entityClassName}, ${keyClassRefName}> {

    @Inject
    public ${serviceClassName}(${repositoryClassName} ${repositoryClassFieldName}) {
        super(${repositoryClassFieldName});
    }

    public Page<${entityClassName}> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, ${entityClassName}.class);
    }
}