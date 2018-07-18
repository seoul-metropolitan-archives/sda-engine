package rmsoft.ams.seoul.st.st004.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st004.service.St004Service;
import rmsoft.ams.seoul.st.st004.vo.St004;
import rmsoft.ams.seoul.st.st004.vo.St004VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st004")
public class St004Controller extends BaseController {

    @Inject
    private St004Service st004Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<St004> requestParams) {
        Page<St004> pages = st004Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(St004VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<St004> request) {
        st004Service.save(request);
        return ok();
    }
}