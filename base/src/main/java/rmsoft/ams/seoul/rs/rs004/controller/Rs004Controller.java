package rmsoft.ams.seoul.rs.rs004.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.rs.rs004.service.Rs004Service;
import rmsoft.ams.seoul.rs.rs004.vo.Rs004;
import rmsoft.ams.seoul.rs.rs004.vo.Rs004VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/rs/rs004")
public class Rs004Controller extends BaseController {

    @Inject
    private Rs004Service rs004Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Rs004> requestParams) {
        Page<Rs004> pages = rs004Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Rs004VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Rs004> request) {
        rs004Service.save(request);
        return ok();
    }
}