package rmsoft.ams.seoul.rc.rc005.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.rc.rc005.service.Rc005Service;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;

import java.util.List;

/**
 * The type Rc 005 controller.
 */
@RestController
@RequestMapping("/api/v1/rc005/")
public class Rc005Controller extends MessageBaseController{

    @Autowired
    private Rc005Service rc005Service;


    /**
     * Get record item list responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/01/list")
    public Responses.PageResponse getRecordItemList(Pageable pageable, RequestParams<Rc00501VO> requestParams){
        Page<Rc00501VO> pages = rc005Service.getRecordItemList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping(value = "/01/merge")
    @PostMapping
    public Object mergeComponent(@RequestBody List<Rc00502VO> mergeList) {
        return rc005Service.mergeComponent(mergeList);
    }
}
