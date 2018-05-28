package rmsoft.ams.seoul.st.st001.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st001.service.St001Service;
import rmsoft.ams.seoul.st.st001.vo.St001;
import rmsoft.ams.seoul.st.st001.vo.St001VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st001")
public class St001Controller extends BaseController {

    @Inject
    private St001Service st001Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<St001> requestParams) {
        Page<St001> pages = st001Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(St001VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<St001> request) {
        st001Service.save(request);
        return ok();
    }
}