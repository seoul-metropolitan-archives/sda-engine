package rmsoft.ams.seoul.st.st025.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st025.service.St025Service;
import rmsoft.ams.seoul.st.st025.vo.St02501VO;
import rmsoft.ams.seoul.st.st027.vo.St02701VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st025")
public class St025Controller extends BaseController {

    @Inject
    private St025Service st025Service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getReaderMachine(Pageable pageable, RequestParams<St02501VO> requestParams){
        Page<St02501VO> pages = st025Service.getReaderMachine(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(),pages);
    }

    @PutMapping(value = "/01/save01")
    @PostMapping
    public void saveReaderMachine(@RequestBody List<St02501VO> requestParams){
        st025Service.saveReaderMachine(requestParams);
    }

}
