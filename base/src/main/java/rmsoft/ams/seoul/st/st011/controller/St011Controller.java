package rmsoft.ams.seoul.st.st011.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.cl.cl003.service.Cl003Service;
import rmsoft.ams.seoul.cl.cl003.vo.Cl00302VO;
import rmsoft.ams.seoul.st.st001.vo.St00101VO;
import rmsoft.ams.seoul.st.st011.service.St011Service;
import rmsoft.ams.seoul.st.st011.vo.St01101VO;
import rmsoft.ams.seoul.st.st011.vo.St01102VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st011")
public class St011Controller extends BaseController {

    @Inject
    private St011Service st011Service;


    @GetMapping("/01/list01")
    public Responses.PageResponse getStInoutExceptList(Pageable pageable, RequestParams<St01101VO> requestParams) {
        Page<St01101VO> pages = st011Service.getStInoutExcept(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/01/list02")
    public Responses.PageResponse getStExceptRecordResult(Pageable pageable, RequestParams<St01102VO> requestParams) {
        Page<St01102VO> pages = st011Service.getStExceptRecordResult(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }


    @PutMapping(value = "/01/save01")
    @PostMapping
    public void saveInoutExcept(@RequestBody List<St01101VO> requestParams) {
        st011Service.saveInoutExcept(requestParams);
    }


    @PutMapping(value = "/02/save01")
    @PostMapping
    public void saveExceptRecordResult(@RequestBody List<St01102VO> requestParams) {

        st011Service.saveExceptRecordResult(requestParams);
    }


}