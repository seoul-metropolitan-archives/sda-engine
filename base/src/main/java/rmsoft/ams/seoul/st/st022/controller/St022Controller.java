package rmsoft.ams.seoul.st.st022.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.st.st022.service.St022Service;
import rmsoft.ams.seoul.st.st022.vo.St02201VO;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/st/st022")
public class St022Controller extends BaseController {

    @Inject
    private St022Service st022Service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getInventoryPlanList(Pageable pageable, RequestParams<St02201VO> requestParams){
        Page<St02201VO> pages = st022Service.getInventoryPlanList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(),pages);
    }

}
