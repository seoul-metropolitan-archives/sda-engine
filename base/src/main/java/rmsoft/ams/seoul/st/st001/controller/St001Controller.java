package rmsoft.ams.seoul.st.st001.controller;

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
import rmsoft.ams.seoul.st.st001.service.St001Service;
import rmsoft.ams.seoul.st.st001.vo.St00101VO;
import rmsoft.ams.seoul.st.st001.vo.St00102VO;
import rmsoft.ams.seoul.st.st001.vo.St00103VO;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st001")
public class St001Controller extends BaseController {

    @Autowired
    private St001Service st001Service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getStRepositoryList(Pageable pageable, RequestParams<St00101VO> requestParams) {
        Page<St00101VO> pages = st001Service.getStRepositoryList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/01/list02")
    public Responses.PageResponse getStShelfList(Pageable pageable, RequestParams<St00102VO> requestParams) {
        Page<St00102VO> pages = st001Service.getStShelfList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/01/list03")
    public Responses.PageResponse getLocationList(Pageable pageable, RequestParams<St00103VO> requestParams) {
        Page<St00103VO> pages = st001Service.getLocationList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping("/02/confirm01")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<St00101VO> requestParams) {
        ApiResponse apiResponse = st001Service.updateRepositoryStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @PutMapping("/02/confirm02")
    @PostMapping
    public ApiResponse updateShelfStatus(@RequestBody List<St00102VO> requestParams) {
        ApiResponse apiResponse = st001Service.updateShelfStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @PutMapping("/02/confirm03")
    @PostMapping
    public ApiResponse updateLocationStatus(@RequestBody List<St00103VO> requestParams) {
        ApiResponse apiResponse = st001Service.updateLocationStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @PutMapping(value = "/02/save01")
    @PostMapping
    public void saveRepositoryList(@RequestBody List<St00101VO> requestParams) {
        st001Service.saveRepositoryList(requestParams);
    }
    @PutMapping(value = "/02/save02")
    @PostMapping
    public void saveShelfList(@RequestBody List<St00102VO> requestParams) {
        st001Service.saveShelfList(requestParams);
    }
    @PutMapping(value = "/02/save03")
    @PostMapping
    public void saveLocationList(@RequestBody List<St00103VO> requestParams) {
        st001Service.saveLocationList(requestParams);
    }
}