package rmsoft.ams.seoul.ig.ig003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ig.ig002.vo.Ig00201VO;
import rmsoft.ams.seoul.ig.ig003.service.Ig003Service;

@RestController
@RequestMapping(value = "/api/v1/ig/ig003")
public class Ig003Controller extends BaseController {

    @Autowired
    private Ig003Service ig003Service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getStRepositoryList(Pageable pageable, RequestParams<Ig00201VO> requestParams) {
        Page<Ig00201VO> pages = ig003Service.getIgAccessionRecordList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
}