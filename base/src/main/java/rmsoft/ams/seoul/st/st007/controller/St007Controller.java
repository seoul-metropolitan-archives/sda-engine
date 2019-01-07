package rmsoft.ams.seoul.st.st007.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import rmsoft.ams.seoul.st.st007.service.St007Service;
import rmsoft.ams.seoul.st.st007.vo.St00701VO;
import rmsoft.ams.seoul.st.st007.vo.St00702VO;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/st/st007")
public class St007Controller extends BaseController {

    @Inject
    private St007Service service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getDisposalList(Pageable pageable, RequestParams<St00701VO> requestParams) {
        Page<St00701VO> pages = service.getDisposalList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/01/list02")
    public Responses.PageResponse getDisposalItem(Pageable pageable, RequestParams<St00702VO> requestParams) {
        Page<St00702VO> pages = service.getDisposalItem(pageable, requestParams);
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
