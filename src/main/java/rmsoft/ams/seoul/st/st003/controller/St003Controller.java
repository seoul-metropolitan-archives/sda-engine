package rmsoft.ams.seoul.st.st003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st003.service.St003Service;
import rmsoft.ams.seoul.st.st003.vo.St003;
import rmsoft.ams.seoul.st.st003.vo.St003VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st003")
public class St003Controller extends BaseController {

    @Inject
    private St003Service st003Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<St003> requestParams) {
        Page<St003> pages = st003Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(St003VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<St003> request) {
        st003Service.save(request);
        return ok();
    }

}