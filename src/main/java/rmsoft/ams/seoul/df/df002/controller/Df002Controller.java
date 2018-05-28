package rmsoft.ams.seoul.df.df002.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.df.df002.service.Df002Service;
import rmsoft.ams.seoul.df.df002.vo.Df002;
import rmsoft.ams.seoul.df.df002.vo.Df002VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/df/df002")
public class Df002Controller extends BaseController {

    @Inject
    private Df002Service df002Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Df002> requestParams) {
        Page<Df002> pages = df002Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Df002VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Df002> request) {
        df002Service.save(request);
        return ok();
    }
}