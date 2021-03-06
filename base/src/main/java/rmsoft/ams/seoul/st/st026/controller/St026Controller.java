package rmsoft.ams.seoul.st.st026.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import rmsoft.ams.seoul.st.st026.service.St026Service;
import rmsoft.ams.seoul.st.st026.vo.St026VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st026")
public class St026Controller extends BaseController {

    @Inject
    private St026Service service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getStRfidMachine(Pageable pageable, RequestParams<St026VO> requestParams) {
        Page<St026VO> pages = service.getStRfidMachine(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping(value = "/01/save01")
    @PostMapping
    public void saveStRfidMachine(@RequestBody List<St026VO> requestParams) {
        service.saveStRfidMachine(requestParams);
    }

   /* @GetMapping("/01/list02")
    public Responses.PageResponse getStExceptRecordResult(Pageable pageable, RequestParams<St01102VO> requestParams) {
        Page<St01102VO> pages = service.getStExceptRecordResult(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }


    @PutMapping(value = "/01/save01")
    @PostMapping
    public void saveInoutExcept(@RequestBody List<St026VO> requestParams) {
        service.saveInoutExcept(requestParams);
    }


    @PutMapping(value = "/02/save01")
    @PostMapping
    public void saveExceptRecordResult(@RequestBody List<St01102VO> requestParams) {

        service.saveExceptRecordResult(requestParams);
    }*/
}
