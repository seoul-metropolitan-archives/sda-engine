package rmsoft.ams.seoul.cl.cl003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.cl.cl003.service.Cl003Service;
import rmsoft.ams.seoul.cl.cl003.vo.Cl00301VO;

@RestController
@RequestMapping("/cl/cl003")
public class Cl003Controller extends BaseController {

    @Autowired
    private Cl003Service cl003Service;

    @GetMapping("/01/list")
    public Responses.PageResponse getClassifiedRecordList(Pageable pageable, RequestParams<Cl00301VO> requestParams) {
        Page<Cl00301VO> pages = cl003Service.getClassifiedRecordList(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

}
