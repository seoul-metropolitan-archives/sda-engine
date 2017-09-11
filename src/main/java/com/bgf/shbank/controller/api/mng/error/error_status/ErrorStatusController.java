package com.bgf.shbank.controller.api.mng.error.error_status;

import com.bgf.shbank.domain.mng.error.error_status.ErrorStatus;
import com.bgf.shbank.domain.mng.error.error_status.ErrorStatusService;
import com.bgf.shbank.domain.mng.error.error_status.ErrorStatusVO;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/mng/error/error_status")
public class ErrorStatusController extends BaseController {

    @Inject
    private ErrorStatusService errorStatusService;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ErrorStatus> requestParams) {
        Page<ErrorStatus> pages = errorStatusService.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(ErrorStatusVO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "details")
    public ErrorStatusVO details(RequestParams<ErrorStatusVO> requestParams) {
        return errorStatusService.findOne(requestParams);
    }

    @GetMapping("/history")
    public Responses.PageResponse historyList(Pageable pageable, RequestParams<ErrorStatus> requestParams) {
        Page<ErrorStatus> pages = errorStatusService.findHistory(pageable, requestParams);
        return Responses.PageResponse.of(ErrorStatusVO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<ErrorStatus> request) {
        errorStatusService.save(request);
        return ok();
    }
}