package rmsoft.ams.seoul.st.st004.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st001.vo.St00103VO;
import rmsoft.ams.seoul.st.st004.service.St004Service;
import rmsoft.ams.seoul.st.st004.vo.St00401VO;
import rmsoft.ams.seoul.st.st004.vo.St00402VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st004")
public class St004Controller extends BaseController {

    @Inject
    private St004Service st004Service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getStArrangeContainersResult(Pageable pageable, RequestParams<St00401VO> requestParam) {
        Page<St00401VO> pages  = st004Service.getStArrangeContainersResult(pageable, requestParam);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/01/list02")
    public Responses.PageResponse getLocationList(Pageable pageable, RequestParams<St00402VO> requestParams) {
        Page<St00402VO> pages = st004Service.getLocationList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping("/02/confirm")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<St00401VO> requestParams) {
        ApiResponse apiResponse = st004Service.updateStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @PutMapping(value = "/02/save")
    @PostMapping
    public void saveArrangeContainerList(@RequestBody List<St00401VO> requestParams) {
        st004Service.saveArrangeRecordList(requestParams);
    }
}