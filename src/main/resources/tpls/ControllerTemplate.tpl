package ${controllerPackageName};

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ${domainPackageName}.${targetPackageName}.${entityClassName};
import ${domainPackageName}.${targetPackageName}.${voClassName};
import ${domainPackageName}.${targetPackageName}.${serviceClassName};

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/${apiPath}")
public class ${controllerClassName} extends BaseController {

    @Inject
    private ${serviceClassName} ${serviceClassFieldName};

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<${entityClassName}> requestParams) {
        Page<${entityClassName}> pages = ${serviceClassFieldName}.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(${voClassName}.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<${entityClassName}> request) {
        ${serviceClassFieldName}.save(request);
        return ok();
    }
}