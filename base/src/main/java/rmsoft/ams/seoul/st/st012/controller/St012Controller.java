package rmsoft.ams.seoul.st.st012.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import rmsoft.ams.seoul.st.st012.service.St012Service;
import rmsoft.ams.seoul.st.st012.vo.St01201VO;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/st/st012")
public class St012Controller extends BaseController {

    @Inject
    private St012Service service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getStInoutExceptList(Pageable pageable, RequestParams<St01201VO> requestParams) {
        Page<St01201VO> pages = service.getStInoutExcept(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

   /* @GetMapping("/01/list02")
    public Responses.PageResponse getStExceptRecordResult(Pageable pageable, RequestParams<St01102VO> requestParams) {
        Page<St01102VO> pages = service.getStExceptRecordResult(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }


    @PutMapping(value = "/01/save01")
    @PostMapping
    public void saveInoutExcept(@RequestBody List<St01201VO> requestParams) {
        service.saveInoutExcept(requestParams);
    }


    @PutMapping(value = "/02/save01")
    @PostMapping
    public void saveExceptRecordResult(@RequestBody List<St01102VO> requestParams) {

        service.saveExceptRecordResult(requestParams);
    }*/
}
