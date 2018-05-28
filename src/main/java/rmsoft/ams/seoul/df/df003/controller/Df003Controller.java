package rmsoft.ams.seoul.df.df003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.df.df003.service.Df003Service;
import rmsoft.ams.seoul.df.df003.vo.Df003;
import rmsoft.ams.seoul.df.df003.vo.Df003VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/df/df003")
public class Df003Controller extends BaseController {

    @Inject
    private Df003Service df003Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Df003> requestParams) {
        Page<Df003> pages = df003Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Df003VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Df003> request) {
        df003Service.save(request);
        return ok();
    }
}