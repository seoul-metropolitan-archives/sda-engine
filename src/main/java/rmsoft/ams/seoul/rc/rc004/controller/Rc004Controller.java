package rmsoft.ams.seoul.rc.rc004.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.rc.rc004.service.Rc004Service;
import rmsoft.ams.seoul.rc.rc004.vo.Rc00401VO;

@RestController
@RequestMapping("/api/v1/rc004/")
public class Rc004Controller extends BaseController{

    @Autowired
    private Rc004Service rc004Service;

    @GetMapping("/01/list")
    public Responses.PageResponse getRecordItemList(Pageable pageable, RequestParams<Rc00401VO> requestParams){
        return null;
    }
}
