package rmsoft.ams.seoul.st.st014.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import rmsoft.ams.seoul.st.st014.service.St014Service;
import rmsoft.ams.seoul.st.st014.vo.St01401VO;
import rmsoft.ams.seoul.st.st014.vo.St01402VO;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/st/st014")
public class St014Controller extends BaseController {

    @Inject
    private St014Service service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getStWithoutNoticeInoutRecord(Pageable pageable, RequestParams<St01401VO> requestParams) {
        Page<St01401VO> pages = service.getStWithoutNoticeInoutRecord(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/01/list02")
    public Responses.PageResponse getStWithoutNoticeInoutHist(Pageable pageable, RequestParams<St01402VO> requestParams) {
        Page<St01402VO> pages = service.getStWithoutNoticeInoutHist(pageable, requestParams);
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
