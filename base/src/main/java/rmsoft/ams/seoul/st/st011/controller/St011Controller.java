package rmsoft.ams.seoul.st.st011.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.st.st010.vo.St01004VO;
import rmsoft.ams.seoul.st.st011.service.St011Service;
import rmsoft.ams.seoul.st.st011.vo.St01101VO;
import rmsoft.ams.seoul.st.st011.vo.St01102VO;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/st/st011")
public class St011Controller extends BaseController {


    @Inject
    private St011Service st011Service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getAggregation(Pageable pageable, RequestParams<St01101VO> requestParams) {
        Page<St01101VO> pages = st011Service.getAggregation(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/01/list02")
    public Responses.PageResponse getTakeInOutList(Pageable pageable, RequestParams<St01102VO> requestParams) {
        Page<St01102VO> pages = st011Service.getTakeInOutList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

}
