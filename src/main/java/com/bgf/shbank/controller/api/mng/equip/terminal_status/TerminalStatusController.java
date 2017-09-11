package com.bgf.shbank.controller.api.mng.equip.terminal_status;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.equip.terminal_status.TerminalStatus;
import com.bgf.shbank.domain.mng.equip.terminal_status.TerminalStatusService;
import com.bgf.shbank.domain.mng.equip.terminal_status.TerminalStatusVO;
import com.bgf.shbank.utils.DateUtils;
import com.bgf.shbank.utils.ModelMapperUtils;
import com.google.common.collect.Maps;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/mng/equip/terminal_status")
public class TerminalStatusController extends BaseController {

    @Inject
    private TerminalStatusService terminalStatusService;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<TerminalStatus> requestParams) {
        Page<TerminalStatus> pages = terminalStatusService.find(pageable, requestParams);
        return Responses.PageResponse.of(TerminalStatusVO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "details")
    public TerminalStatusVO details(RequestParams<TerminalStatusVO> requestParams) {
        TerminalStatus terminalStatus = new TerminalStatus();
        terminalStatus.setJisaCode(requestParams.getString("jisaCode"));
        terminalStatus.setBranchCode(requestParams.getString("branchCode"));
        terminalStatus.setCornerCode(requestParams.getString("cornerCode"));
        terminalStatus.setTerminalNo(requestParams.getString("terminalNo"));

        return ModelMapperUtils.map(terminalStatusService.findOne(terminalStatus), TerminalStatusVO.class);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody TerminalStatusVO terminalStatusVO) {

        TerminalStatus terminalStatus = ModelMapperUtils.map(terminalStatusVO, TerminalStatus.class);
        String modelCode = terminalStatusVO.getModelCode();
        terminalStatus.setTerminalCorpCode(modelCode.substring(0, 1));
        terminalStatus.setModelCode(modelCode.substring(1, 4));

        terminalStatusService.save(terminalStatus);
        return ok();
    }

    @GetMapping("/download")
    public View download(RequestParams<TerminalStatusVO> requestParams, Model model) {

        List<TerminalStatus> resultList = terminalStatusService.find(requestParams);

        List<TerminalStatusVO> voList = TerminalStatusVO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "terminal_status");
        model.addAttribute("fileName", "기기목록-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}