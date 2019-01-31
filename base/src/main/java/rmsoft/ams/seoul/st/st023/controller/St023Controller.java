package rmsoft.ams.seoul.st.st023.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.st.st023.service.St023Service;
import rmsoft.ams.seoul.st.st023.vo.St02301VO;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/st/st023")
public class St023Controller extends BaseController {

    @Inject
    private St023Service st023Service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getInoutList(Pageable pageable, RequestParams<St02301VO> requestParams){
        Page<St02301VO> pages = st023Service.getInoutList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(),pages);
    }
}
