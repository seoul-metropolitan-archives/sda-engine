package rmsoft.ams.seoul.rc.rc003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.rc.rc003.service.Rc003Service;
import rmsoft.ams.seoul.rc.rc003.vo.Rc00301VO;

@RestController
@RequestMapping("/api/v1/rc003/")
public class Rc003Controller extends BaseController{

    @Autowired
    private Rc003Service rc003Service;

    @GetMapping("/01/list")
    public Responses.PageResponse getRecordAggregationList(Pageable pageable, RequestParams<Rc00301VO> requestParams){
        Page<Rc00301VO> pages = rc003Service.getRecordAggregationList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
}
