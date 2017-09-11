package com.bgf.shbank.controller.api.mng.error.sh01001160;

import com.bgf.shbank.domain.mng.error.sh01001160.Sh01001160;
import com.bgf.shbank.domain.mng.error.sh01001160.Sh01001160Service;
import com.bgf.shbank.domain.mng.error.sh01001160.Sh01001160VO;
import com.bgf.shbank.utils.DateUtils;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/v1/mng/error/sh01001160")
public class Sh01001160Controller extends BaseController {

    @Inject
    private Sh01001160Service sh01001160Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh01001160VO> requestParams) {
        Page<Sh01001160> pages = sh01001160Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh01001160VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse requestSelfCall(@RequestBody Sh01001160VO vo) {
        if (vo.getSelfCalleeGubun().equals("1")) {
            vo.setCancleReqDatetime(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN));

            // 자체출동취소 전문 전송
            ApiResponse apiResponse = sh01001160Service.sendAndReceive(vo);

            if (apiResponse.getStatus() == -1) {
                throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
            }

            return apiResponse;
        } else {
            // 차체출동취소를 하지 않고 모니터링만 강제로 완료시킴
            // 조치완료 프로세스 추가
            sh01001160Service.cancelCurrentErrorStatus(vo);
            return ok();
        }
    }
}