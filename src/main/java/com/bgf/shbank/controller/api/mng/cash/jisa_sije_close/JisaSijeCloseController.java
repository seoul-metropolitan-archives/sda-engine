package com.bgf.shbank.controller.api.mng.cash.jisa_sije_close;

import com.bgf.shbank.domain.mng.cash.jisa_sije_close.JisaSijeClose;
import com.bgf.shbank.domain.mng.cash.jisa_sije_close.JisaSijeCloseService;
import com.bgf.shbank.domain.mng.cash.jisa_sije_close.JisaSijeCloseVO;
import com.bgf.shbank.utils.DateUtils;
import com.bgf.shbank.utils.ModelMapperUtils;
import com.google.common.collect.Lists;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/mng/cash/jisa_sije_close")
public class JisaSijeCloseController extends BaseController {

    @Inject
    private JisaSijeCloseService jisaSijeCloseService;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<JisaSijeClose> requestParams) {
        Page<JisaSijeClose> pages = jisaSijeCloseService.find(pageable, requestParams);
        return Responses.PageResponse.of(JisaSijeCloseVO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<JisaSijeCloseVO> requestParams) {

        List<JisaSijeClose> request = Lists.newArrayList();
        JisaSijeClose jisaSijeClose = null;

        for (JisaSijeCloseVO jisaSijeCloseVO : requestParams) {
            jisaSijeClose = ModelMapperUtils.map(jisaSijeCloseVO, JisaSijeClose.class);
            jisaSijeClose.setTxId(Timestamp.valueOf(LocalDateTime.now()));
            jisaSijeClose.setCloseDate(DateUtils.convertToTimestamp(jisaSijeCloseVO.getCloseDate(),"yyyy-MM-dd"));
            request.add(jisaSijeClose);
        }
        jisaSijeCloseService.save(request);
        return ok();
    }
}