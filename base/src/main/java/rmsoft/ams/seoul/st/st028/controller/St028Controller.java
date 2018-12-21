package rmsoft.ams.seoul.st.st028.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st027.service.St027Service;
import rmsoft.ams.seoul.st.st027.vo.St02701VO;
import rmsoft.ams.seoul.st.st028.service.St028Service;
import rmsoft.ams.seoul.st.st028.vo.St02801VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st028")
public class St028Controller extends BaseController {

    @Inject
    private St028Service st028Service;


    @GetMapping("/01/list01")
    public Responses.PageResponse getStZone(Pageable pageable, RequestParams<St02801VO> requestParams){
        Page<St02801VO> pages = st028Service.getStGate(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(),pages);
    }

    @PutMapping(value = "/01/save01")
    @PostMapping
    public void saveZone(@RequestBody List<St02801VO> requestParams){
        st028Service.saveGate(requestParams);
    }

}

