package rmsoft.ams.seoul.st.st009.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.common.repository.StTakeoutRecordResultRepository;
import rmsoft.ams.seoul.common.repository.StTakeoutRequestRepository;
import rmsoft.ams.seoul.st.st009.service.St009Service;
import rmsoft.ams.seoul.st.st009.vo.St00901VO;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/st/st009")
public class St009Controller extends BaseController {

    @Inject
    private St009Service st009Service;


    @GetMapping("/01/list01")
    public Responses.PageResponse getTakeoutRequest(Pageable pageable, RequestParams<St00901VO> requestParams){
        Page<St00901VO> pages = st009Service.getTakeoutRequest(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(),pages);
    }




}
