package rmsoft.ams.seoul.st.st009.controller;

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
import rmsoft.ams.seoul.common.repository.StTakeoutRecordResultRepository;
import rmsoft.ams.seoul.common.repository.StTakeoutRequestRepository;
import rmsoft.ams.seoul.st.st008.vo.St00801VO;
import rmsoft.ams.seoul.st.st009.service.St009Service;
import rmsoft.ams.seoul.st.st009.vo.St00901VO;
import rmsoft.ams.seoul.st.st009.vo.St00902VO;

import javax.inject.Inject;
import java.util.List;

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

    @GetMapping("/01/list02")
    public Responses.PageResponse getTakeoutRecordResult(Pageable pageable, RequestParams<St00902VO> requestParams) {
        Page<St00902VO> pages = st009Service.getTakeoutRecordResult(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping("/01/confirm01")
    @PostMapping
    public ApiResponse updateStatus01(@RequestBody List<St00901VO> requestParams) {
        ApiResponse apiResponse = st009Service.updateTakeoutRequest(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

    @PutMapping("/01/confirm02")
    @PostMapping
    public ApiResponse updateStatus02(@RequestBody List<St00901VO> requestParams) {
        ApiResponse apiResponse = st009Service.updateTakeoutRequest2(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

    @PutMapping("/01/save")
    @PostMapping
    public ApiResponse saveStTakeoutRequest(@RequestBody St00901VO vo) {
        ApiResponse apiResponse = st009Service.saveStTakeoutRequest(vo);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;

    }
}

