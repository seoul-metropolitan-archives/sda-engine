package rmsoft.ams.seoul.rs.rs001.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.rs.rs001.service.Rs001Service;
import rmsoft.ams.seoul.rs.rs001.vo.Rs001;
import rmsoft.ams.seoul.rs.rs001.vo.Rs001VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/rs/rs001")
public class Rs001Controller extends BaseController {

    @Inject
    private Rs001Service rs001Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Rs001> requestParams) {
        Page<Rs001> pages = rs001Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Rs001VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Rs001> request) {
        rs001Service.save(request);
        return ok();
    }
}