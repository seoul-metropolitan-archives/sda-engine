package rmsoft.ams.seoul.rs.rs002.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.rs.rs002.service.Rs002Service;
import rmsoft.ams.seoul.rs.rs002.vo.Rs002;
import rmsoft.ams.seoul.rs.rs002.vo.Rs002VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/rs/rs002")
public class Rs002Controller extends BaseController {

    @Inject
    private Rs002Service rs002Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Rs002> requestParams) {
        Page<Rs002> pages = rs002Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Rs002VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Rs002> request) {
        rs002Service.save(request);
        return ok();
    }
}