package rmsoft.ams.seoul.st.st018.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import rmsoft.ams.seoul.st.st018.service.St018Service;
import rmsoft.ams.seoul.st.st018.vo.St01801VO;
import rmsoft.ams.seoul.st.st018.vo.St01802VO;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/st/st018")
public class St018Controller extends BaseController {

    @Inject
    private St018Service service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getRcAggregation(Pageable pageable, RequestParams<St01801VO> requestParams) {
        Page<St01801VO> pages = service.getRcAggregation(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/01/list02")
    public Responses.PageResponse getStRfidTag(Pageable pageable, RequestParams<St01802VO> requestParams) {
        Page<St01802VO> pages = service.getStRfidTag(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping(value = "/01/saveTagPublish")
    @PostMapping
    public void saveTagPublish(@RequestBody St01801VO requestParams) {
        service.saveTagPublish(requestParams);
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
