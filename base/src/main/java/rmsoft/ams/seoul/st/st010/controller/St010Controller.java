package rmsoft.ams.seoul.st.st010.controller;


import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.st.st010.service.St010Service;
import rmsoft.ams.seoul.st.st010.vo.St01004VO;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/st/st010")
public class St010Controller extends BaseController {

    @Inject
    private St010Service st010Service;


    @GetMapping("/01/list04")
    public Responses.PageResponse getAggregation(Pageable pageable, RequestParams<St01004VO> requestParams) {
        Page<St01004VO> pages = st010Service.getAggregation(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

}
