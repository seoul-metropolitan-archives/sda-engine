package rmsoft.ams.seoul.st.st002.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st002.service.St002Service;
import rmsoft.ams.seoul.st.st002.vo.St002;
import rmsoft.ams.seoul.st.st002.vo.St002VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st002")
public class St002Controller extends BaseController {

    @Inject
    private St002Service st002Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<St002> requestParams) {
        Page<St002> pages = st002Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(St002VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<St002> request) {
        st002Service.save(request);
        return ok();
    }
}