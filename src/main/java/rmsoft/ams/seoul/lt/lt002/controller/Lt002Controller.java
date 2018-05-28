package rmsoft.ams.seoul.lt.lt002.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.lt.lt002.service.Lt002Service;
import rmsoft.ams.seoul.lt.lt002.vo.Lt002;
import rmsoft.ams.seoul.lt.lt002.vo.Lt002VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/lt/lt002")
public class Lt002Controller extends BaseController {

    @Inject
    private Lt002Service lt002Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Lt002> requestParams) {
        Page<Lt002> pages = lt002Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Lt002VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Lt002> request) {
        lt002Service.save(request);
        return ok();
    }
}