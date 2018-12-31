package rmsoft.ams.seoul.rs.rs003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.rs.rs003.service.Rs003Service;
import rmsoft.ams.seoul.rs.rs003.vo.Rs00301VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/rs/rs003")
public class Rs003Controller extends BaseController {

    @Inject
    private Rs003Service rs003Service;

    @GetMapping("/01/list")
    public Responses.PageResponse getRsRecordScheduleList(Pageable pageable, RequestParams<Rs00301VO> requestParams) {
        Page<Rs00301VO> pages = rs003Service.getRsRecordScheduleList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping("/02/confirm")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<Rs00301VO> requestParams) {
        ApiResponse apiResponse = rs003Service.updateStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

    @PutMapping("/03/save")
    @PostMapping
    public ApiResponse updateRsRecordScheduleList(@RequestBody List<Rs00301VO> requestParams) {
        ApiResponse apiResponse = rs003Service.updateRsRecordScheduleList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @PutMapping("/04/update")
    @PostMapping
    public ApiResponse dueToUpdateSchedule(@RequestBody List<Rs00301VO> requestParams) throws Exception {
        ApiResponse apiResponse = rs003Service.dueToUpdateSchedule(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
}