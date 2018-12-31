package rmsoft.ams.seoul.rs.rs004.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.rs.rs003.vo.Rs00301VO;
import rmsoft.ams.seoul.rs.rs004.service.Rs004Service;
import rmsoft.ams.seoul.rs.rs004.vo.Rs00401VO;
import rmsoft.ams.seoul.rs.rs004.vo.Rs00402VO;
import rmsoft.ams.seoul.rs.rs005.vo.Rs00501VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/rs/rs004")
public class Rs004Controller extends BaseController {

    @Inject
    private Rs004Service rs004Service;

    @GetMapping("/01/list")
    public Responses.PageResponse getRecordScheduleResultList(Pageable pageable, RequestParams<Rs00401VO> requestParams) {
        Page<Rs00401VO> pages = rs004Service.getRecordScheduleResultList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/02/list")
    public Responses.PageResponse getRecordScheduleList(Pageable pageable, RequestParams<Rs00301VO> requestParams) {
        Page<Rs00301VO> pages = rs004Service.getRecordScheduleList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/03/list")
    public Responses.PageResponse getRecordScheduleAggregationList(Pageable pageable, RequestParams<Rs00402VO> requestParams) {
        Page<Rs00402VO> pages = rs004Service.getRecordScheduleAggregationList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @PutMapping("/02/confirm")
    @PostMapping
    public ApiResponse updateRecordScheduleResultList(@RequestBody List<Rs00401VO> requestParams) {
        ApiResponse apiResponse = rs004Service.updateRecordScheduleResultList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @PutMapping("/03/save")
    @PostMapping
    public ApiResponse saveRecordScheduleResultList(@RequestBody List<Rs00402VO> requestParams) throws Exception {
        ApiResponse apiResponse = rs004Service.saveRecordScheduleResultList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
}