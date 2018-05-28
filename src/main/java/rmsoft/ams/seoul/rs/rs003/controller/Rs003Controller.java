package rmsoft.ams.seoul.rs.rs003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.rs.rs003.service.Rs003Service;
import rmsoft.ams.seoul.rs.rs003.vo.Rs003;
import rmsoft.ams.seoul.rs.rs003.vo.Rs003VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/rs/rs003")
public class Rs003Controller extends BaseController {

    @Inject
    private Rs003Service rs003Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Rs003> requestParams) {
        Page<Rs003> pages = rs003Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Rs003VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Rs003> request) {
        rs003Service.save(request);
        return ok();
    }
}