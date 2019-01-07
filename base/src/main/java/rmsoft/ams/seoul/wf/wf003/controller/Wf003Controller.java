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
import rmsoft.ams.seoul.wf.wf003.vo.*;

import java.util.List;

/**
 * Wf003Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-12 오후 5:48
 */
@RestController
@RequestMapping(value = "/api/v1/wf003/")
public class Wf003Controller extends MessageBaseController {

    @Autowired
    private Wf003Service wf003Service;

    /**
     * Find all workflow responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/01/list")
    public Responses.PageResponse findAllWorkflow(Pageable pageable, RequestParams<Wf00301VO> requestParams) {
        Page<Wf00301VO> pages = wf003Service.findAllWorkflow(pageable, requestParams);

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
    public Responses.PageResponse findWorkflowJob(Pageable pageable, RequestParams<Wf00302VO> requestParams) {
        Page<Wf00302VO> pages = wf003Service.findWorkflowJob(pageable, requestParams);

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
    public ApiResponse saveWorkflow(@RequestBody List<Wf00301VO> requestParams) {
        return wf003Service.saveWorkflow(requestParams);
    }

    /**
     * Save workfloe job api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/02/save")
    @PostMapping
    public ApiResponse saveWorkflowJob(@RequestBody List<Wf00302VO> requestParams) {
        return wf003Service.saveWorkflowJob(requestParams);
    }


    /**********************************************************************************
     *  POPUP
     * @param pageable the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/p/01/list")
    public Responses.PageResponse findAllJob(Pageable pageable, RequestParams<Wf00301_P0101VO> requestParams) {
        Page<Wf00301_P0101VO> pages = wf003Service.findAllJob(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Find parameter responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/p/02/list")
    public Responses.PageResponse findParameter(Pageable pageable, RequestParams<Wf00301_P0102VO> requestParams) {
        Page<Wf00301_P0102VO> pages = wf003Service.findParameter(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Save parameter api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/p/02/save")
    @PostMapping
    public ApiResponse saveParameter(@RequestBody List<Wf00301_P0102VO> requestParams) {
        return wf003Service.saveParameter(requestParams);
    }

    /**
     * Gets popup info.
     *
     * @param requestParams the request params
     * @return the popup info
     */
    @RequestMapping("/p/02/getPopupInfo")
    public Responses.MapResponse getPopupInfo(RequestParams<Wf00301_P0102VO> requestParams) {

        return Responses.MapResponse.of(wf003Service.getPopupInfo(requestParams));
    }

    /**********************************************************************************
     *  Run Process
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping("/01/run")
    @PostMapping
    public ApiResponse runProcess(@RequestBody Wf00303VO requestParams) {
        return wf003Service.runProcess(requestParams);
    }

    /**
     * Stop process api response.
     *
     * @param batchId the batch id
     * @return the api response
     */
    @GetMapping("/01/stop")
    public ApiResponse stopProcess(String batchId) {
        return wf003Service.stopProcess(batchId);
    }
}