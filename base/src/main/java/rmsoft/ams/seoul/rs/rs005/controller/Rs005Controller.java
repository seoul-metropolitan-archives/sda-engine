package rmsoft.ams.seoul.rs.rs005.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.rs.rs005.service.Rs005Service;
import rmsoft.ams.seoul.rs.rs005.vo.Rs00501VO;

import javax.inject.Inject;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/rs/rs005")
public class Rs005Controller extends BaseController {

    @Inject
    private Rs005Service rs005Service;

    @GetMapping("/01/list")
    public Responses.PageResponse getDisposalRecordList(Pageable pageable, RequestParams<Rs00501VO> requestParams) {
        Page<Rs00501VO> pages = rs005Service.getDisposalRecordList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping("/02/confirm")
    @PostMapping
    public ApiResponse updateRecordScheduleResultList(@RequestBody List<Rs00501VO> requestParams) {
        ApiResponse apiResponse = rs005Service.updateRecordScheduleResultList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
}