package rmsoft.ams.seoul.cl.cl002.controller;

import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.cl.cl002.service.Cl002Service;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00201VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;


@RestController
@RequestMapping("/api/v1/cl002/")
public class Cl002Controller extends MessageBaseController{

    @Autowired
    private Cl002Service cl002Service;

    @GetMapping("/01/list")
    public Responses.PageResponse getClassList(Pageable pageable, RequestParams<Cl00201VO> requestParams) {
        Page<Cl00201VO> pages = cl002Service.getClassList(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }
}
