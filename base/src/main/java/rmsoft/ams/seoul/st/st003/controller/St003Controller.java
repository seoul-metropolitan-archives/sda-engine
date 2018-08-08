package rmsoft.ams.seoul.st.st003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st003.service.St003Service;
import rmsoft.ams.seoul.st.st003.vo.St00301VO;
import rmsoft.ams.seoul.st.st003.vo.St00302VO;
import rmsoft.ams.seoul.st.st003.vo.St00303VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st003")
public class St003Controller extends BaseController {

    @Inject
    private St003Service st003Service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getContainerAggregationList(Pageable pageable, RequestParams<St00301VO> requestParam) {
        Page<St00301VO> pages  = st003Service.getContainerAggregationList(pageable, requestParam);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/01/list02")
    public Responses.PageResponse getContainerItemList(Pageable pageable, RequestParams<St00301VO> requestParam) {
        Page<St00301VO> pages  = st003Service.getContainerItemList(pageable, requestParam);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping("/02/confirm")
    @PostMapping
    public ApiResponse updateArrangeRecordStatus(@RequestBody List<St00301VO> requestParams) {
        ApiResponse apiResponse = st003Service.updateArrangeRecordStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

    @PutMapping("/03/save")
    @PostMapping
    public ApiResponse saveArrangeRecordList(@RequestBody List<St00301VO> requestParams) {
        ApiResponse apiResponse = st003Service.saveArrangeRecordList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @GetMapping("/04/list01")
    public Responses.PageResponse getAggregationHierarchyList(Pageable pageable) {
        Page<St00302VO> pages  = st003Service.getAggregationHierarchyList(pageable);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/04/list02")
    public Responses.PageResponse getSelectedItem(Pageable pageable, RequestParams<St00303VO> requestParam) {
        Page<St00303VO> pages  = st003Service.getSelectedItem(pageable, requestParam);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
}