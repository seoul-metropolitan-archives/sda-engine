/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf003.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.wf.wf003.service.Wf003Service;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00301VO;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00301_P0101VO;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00301_P0102VO;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00302VO;

import java.util.List;

/**
 * Wf003Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017-10-12 오후 5:48
 **/
@RestController
@RequestMapping(value = "/api/v1/wf003/")
public class Wf003Controller extends MessageBaseController {

    @Autowired
    private Wf003Service wf003Service;

    @GetMapping("/01/list")
    public Responses.PageResponse findAllWorkflow(Pageable pageable, RequestParams<Wf00301VO> requestParams) {
        Page<Wf00301VO> pages = wf003Service.findAllWorkflow(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/02/list")
    public Responses.PageResponse findWorkflowJob(Pageable pageable, RequestParams<Wf00302VO> requestParams) {
        Page<Wf00302VO> pages = wf003Service.findWorkflowJob(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping(value = "/01/save")
    @PostMapping
    public ApiResponse saveWorkflow(@RequestBody List<Wf00301VO> requestParams) {
        return wf003Service.saveWorkflow(requestParams);
    }

    @PutMapping(value = "/02/save")
    @PostMapping
    public ApiResponse saveWorkfloeJob(@RequestBody List<Wf00302VO> requestParams) {
        return wf003Service.saveWorkfloeJob(requestParams);
    }


    /**********************************************************************************
     *  POPUP
     **********************************************************************************/

    @GetMapping("/p/01/list")
    public Responses.PageResponse findAllJob(Pageable pageable, RequestParams<Wf00301_P0101VO> requestParams) {
        Page<Wf00301_P0101VO> pages = wf003Service.findAllJob(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/p/02/list")
    public Responses.PageResponse findParameter(Pageable pageable, RequestParams<Wf00301_P0102VO> requestParams) {
        Page<Wf00301_P0102VO> pages = wf003Service.findParameter(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping(value = "/p/02/save")
    @PostMapping
    public ApiResponse saveParameter(@RequestBody List<Wf00301_P0102VO> requestParams) {
        return wf003Service.saveParameter(requestParams);
    }
}