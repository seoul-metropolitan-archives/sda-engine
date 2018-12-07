package rmsoft.ams.seoul.lc.lc001.controller;

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
import rmsoft.ams.seoul.lc.lc001.service.Lc001Service;
import rmsoft.ams.seoul.lc.lc001.vo.Lc00101VO;
import rmsoft.ams.seoul.lc.lc001.vo.Lc00102VO;
import rmsoft.ams.seoul.rs.rs001.vo.Rs00101VO;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/lc/lc001")
public class Lc001Controller extends BaseController {

    @Autowired
    private Lc001Service lc001Service;

    @GetMapping("/01/list")
    public Responses.PageResponse getLeadCaseList(Pageable pageable, RequestParams<Lc00101VO> requestParams) {
        Page<Lc00101VO> pages = lc001Service.getLeadCaseList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/02/list")
    public Responses.PageResponse getLeadCaseScheduleList(Pageable pageable, RequestParams<Lc00102VO> requestParam) {
        Page<Lc00102VO> pages = lc001Service.getLeadCaseScheduleList(pageable, requestParam);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping("/01/save")
    @PostMapping
    public ApiResponse updateLeadCaseList(@RequestBody List<Lc00101VO> requestParams) {
        ApiResponse apiResponse = lc001Service.updateLeadCaseList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @PutMapping("/02/save")
    @PostMapping
    public ApiResponse updateLeadCaseScheduleList(@RequestBody List<Lc00102VO> requestParams) {
        ApiResponse apiResponse = lc001Service.updateLeadCaseScheduleList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

}