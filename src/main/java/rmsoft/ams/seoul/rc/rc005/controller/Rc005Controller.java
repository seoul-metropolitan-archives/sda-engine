package rmsoft.ams.seoul.rc.rc005.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.rc.rc005.service.Rc005Service;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;

@RestController
@RequestMapping("/api/v1/rc005/")
public class Rc005Controller extends BaseController{

    @Autowired
    private Rc005Service rc005Service;

    @GetMapping("/01/list")
    public Responses.PageResponse getRecordItemList(Pageable pageable, RequestParams<Rc00501VO> requestParams){
        Page<Rc00501VO> pages = rc005Service.getRecordItemList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
}
