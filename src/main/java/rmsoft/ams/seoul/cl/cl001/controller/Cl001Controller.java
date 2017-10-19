package rmsoft.ams.seoul.cl.cl001.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl001.service.Cl001Service;


@RestController
@RequestMapping("/api/v1/cl001/")
public class Cl001Controller extends BaseController{

    @Autowired
    private Cl001Service cl001Service;

    @GetMapping("/01/list")
    public Responses.PageResponse getClassificationSchemeList(Pageable pageable, RequestParams<Cl00101VO> requestParams) {
        Page<Cl00101VO> pages = cl001Service.getClassificationSchemeList(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }
}
