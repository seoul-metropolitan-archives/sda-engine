package rmsoft.ams.seoul.ac.ac011.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac011.service.Ac011Service;
import rmsoft.ams.seoul.ac.ac011.vo.Ac011;
import rmsoft.ams.seoul.ac.ac011.vo.Ac011VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/ac/ac011")
public class Ac011Controller extends BaseController {

    @Inject
    private Ac011Service ac011Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Ac011> requestParams) {
        Page<Ac011> pages = ac011Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Ac011VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Ac011> request) {
        ac011Service.save(request);
        return ok();
    }
}