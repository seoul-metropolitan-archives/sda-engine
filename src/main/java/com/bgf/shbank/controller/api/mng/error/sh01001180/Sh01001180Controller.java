package com.bgf.shbank.controller.api.mng.error.sh01001180;

import com.bgf.shbank.domain.mng.error.error_status.StextSendGubun;
import com.bgf.shbank.domain.mng.error.sh01001180.Sh01001180;
import com.bgf.shbank.domain.mng.error.sh01001180.Sh01001180Service;
import com.bgf.shbank.domain.mng.error.sh01001180.Sh01001180VO;
import com.bgf.shbank.utils.ModelMapperUtils;
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

@RestController
@RequestMapping(value = "/api/v1/mng/error/sh01001180")
public class Sh01001180Controller extends BaseController {

    @Inject
    private Sh01001180Service sh01001180Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh01001180VO> requestParams) {
        Page<Sh01001180> pages = sh01001180Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh01001180VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh01001180VO vo) {
        ApiResponse apiResponse = sh01001180Service.sendAndReceive(vo);
        vo.setStextSendGubun(StextSendGubun.전문전송.getCode());

        if (apiResponse.getStatus() == -1) {
            vo.setStextSendGubun(StextSendGubun.전송실패.getCode());
            saveSh01001180(vo);
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }

        saveSh01001180(vo);
        return apiResponse;
    }

    public void saveSh01001180(Sh01001180VO vo) {
        Sh01001180 sh01001180 = ModelMapperUtils.map(vo, Sh01001180.class);
        // 동지다발장애목록관리에 조치결과정보 Update
        sh01001180Service.save(sh01001180);
    }
}