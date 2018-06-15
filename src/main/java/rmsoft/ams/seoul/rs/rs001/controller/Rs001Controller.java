package rmsoft.ams.seoul.rs.rs001.controller;

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
import rmsoft.ams.seoul.rs.rs001.service.Rs001Service;
import rmsoft.ams.seoul.rs.rs001.vo.Rs00101VO;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/rs/rs001")
public class Rs001Controller extends BaseController {

    @Autowired
    private Rs001Service rs001Service;

    @GetMapping("/01/list")
    public Responses.PageResponse getRsGeneralRecordScheduleList(Pageable pageable, RequestParams<Rs00101VO> requestParams) {
        Page<Rs00101VO> pages = rs001Service.getRsGeneralRecordScheduleList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping(value = "/02/confirm")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<Rs00101VO> requestParams) {
        ApiResponse apiResponse = rs001Service.updateStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

    @PutMapping("/03/save")
    @PostMapping
    public ApiResponse updateRsGeneralRecordScheduleList(@RequestBody List<Rs00101VO> requestParams) {
        ApiResponse apiResponse = rs001Service.updateRsGeneralRecordScheduleList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
}