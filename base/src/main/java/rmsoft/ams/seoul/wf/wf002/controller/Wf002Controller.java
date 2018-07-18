/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf002.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.wf.wf002.service.Wf002Service;
import rmsoft.ams.seoul.wf.wf002.vo.Wf00201VO;
import rmsoft.ams.seoul.wf.wf002.vo.Wf00202VO;

import java.util.List;

/**
 * Wf002Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-12 오후 5:48
 */
@RestController
@RequestMapping(value = "/api/v1/wf002/")
public class Wf002Controller extends MessageBaseController {

    @Autowired
    private Wf002Service wf002Service;

    /**
     * Find all workflow responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/01/list")
    public Responses.PageResponse findAllWorkflow(Pageable pageable, RequestParams<Wf00201VO> requestParams) {
        Page<Wf00201VO> pages = wf002Service.findAllWorkflow(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Find workflow job responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/02/list")
    public Responses.PageResponse findWorkflowJob(Pageable pageable, RequestParams<Wf00202VO> requestParams) {
        Page<Wf00202VO> pages = wf002Service.findWorkflowJob(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Save workflow api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/01/save")
    @PostMapping
    public ApiResponse saveWorkflow(@RequestBody List<Wf00201VO> requestParams) {
        return wf002Service.saveWorkflow(requestParams);
    }

    /**
     * Save workfloe job api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/02/save")
    @PostMapping
    public ApiResponse saveWorkfloeJob(@RequestBody List<Wf00202VO> requestParams) {
        return wf002Service.saveWorkfloeJob(requestParams);
    }
}