package rmsoft.ams.seoul.rs.rs002.controller;

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
import rmsoft.ams.seoul.rs.rs002.service.Rs002Service;
import rmsoft.ams.seoul.rs.rs002.vo.Rs00201VO;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/rs/rs002")
public class Rs002Controller extends BaseController {

    @Autowired
    private Rs002Service rs002Service;

    @GetMapping("/01/list")
    public Responses.PageResponse getRsTriggerList(Pageable pageable, RequestParams<Rs00201VO> requestParams) {
        Page<Rs00201VO> pages = rs002Service.getRsTriggerList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping(value = "/02/confirm")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<Rs00201VO> requestParams) {
        ApiResponse apiResponse = rs002Service.updateStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

    @PutMapping("/03/save")
    @PostMapping
    public ApiResponse updateRsTriggerList(@RequestBody List<Rs00201VO> requestParams) {
        ApiResponse apiResponse = rs002Service.updateRsTriggerList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
}