package rmsoft.ams.seoul.rs.rs005.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.rs.rs005.service.Rs005Service;
import rmsoft.ams.seoul.rs.rs005.vo.Rs005;
import rmsoft.ams.seoul.rs.rs005.vo.Rs005VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/rs/rs005")
public class Rs005Controller extends BaseController {

    @Inject
    private Rs005Service rs005Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Rs005> requestParams) {
        Page<Rs005> pages = rs005Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Rs005VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Rs005> request) {
        rs005Service.save(request);
        return ok();
    }
}