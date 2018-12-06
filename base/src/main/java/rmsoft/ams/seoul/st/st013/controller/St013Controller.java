package rmsoft.ams.seoul.st.st013.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st013.service.St013Service;
import rmsoft.ams.seoul.st.st013.vo.St01301VO;
import rmsoft.ams.seoul.st.st013.vo.St01302VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st013")
public class St013Controller extends BaseController {

    @Inject
    private St013Service st013Service;


    @GetMapping("/01/list01")
    public Responses.PageResponse getStInoutExceptList(Pageable pageable, RequestParams<St01301VO> requestParams) {
        Page<St01301VO> pages = st013Service.getStInoutExcept(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/01/list02")
    public Responses.PageResponse getStExceptRecordResult(Pageable pageable, RequestParams<St01302VO> requestParams) {
        Page<St01302VO> pages = st013Service.getStExceptRecordResult(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }


    @PutMapping(value = "/01/save01")
    @PostMapping
    public void saveInoutExcept(@RequestBody List<St01301VO> requestParams) {
        st013Service.saveInoutExcept(requestParams);
    }


    @PutMapping(value = "/02/save01")
    @PostMapping
    public void saveExceptRecordResult(@RequestBody List<St01302VO> requestParams) {

        st013Service.saveExceptRecordResult(requestParams);
    }


}