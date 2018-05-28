package rmsoft.ams.seoul.df.df001.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.df.df001.service.Df001Service;
import rmsoft.ams.seoul.df.df001.vo.Df001;
import rmsoft.ams.seoul.df.df001.vo.Df001VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/df/df001")
public class Df001Controller extends BaseController {

    @Inject
    private Df001Service df001Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Df001> requestParams) {
        Page<Df001> pages = df001Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Df001VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Df001> request) {
        df001Service.save(request);
        return ok();
    }
}