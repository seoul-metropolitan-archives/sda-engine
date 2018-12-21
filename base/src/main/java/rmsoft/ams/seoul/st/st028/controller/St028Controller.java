package rmsoft.ams.seoul.st.st027.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st027.service.St027Service;
import rmsoft.ams.seoul.st.st027.vo.St02701VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st027")
public class St027Controller extends BaseController {

    @Inject
    private St027Service st027Service;


    @GetMapping("/01/list01")
    public Responses.PageResponse getStZone(Pageable pageable, RequestParams<St02701VO> requestParams){
        Page<St02701VO> pages = st027Service.getStZone(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(),pages);
    }

    @PutMapping(value = "/01/save01")
    @PostMapping
    public void saveZone(@RequestBody List<St02701VO> requestParams){
        st027Service.saveZone(requestParams);
    }

}

