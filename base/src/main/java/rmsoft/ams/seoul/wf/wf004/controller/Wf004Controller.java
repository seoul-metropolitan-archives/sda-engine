/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf004.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.wf.wf004.service.Wf004Service;
import rmsoft.ams.seoul.wf.wf004.vo.Wf00401VO;
import rmsoft.ams.seoul.wf.wf004.vo.Wf00402VO;
import rmsoft.ams.seoul.wf.wf004.vo.Wf00403VO;

import java.util.List;

/**
 * Wf004Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-12 오후 5:48
 */
@RestController
@RequestMapping(value = "/api/v1/wf004/")
public class Wf004Controller extends MessageBaseController {

    @Autowired
    private Wf004Service wf004Service;

    /**
     * Find all workflow result responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/01/list")
    public Responses.PageResponse findAllWorkflowResult(Pageable pageable, RequestParams<Wf00401VO> requestParams) {
        Page<Wf00401VO> pages = wf004Service.findAllWorkflowResult(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Find workflow job result responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/02/list")
    public Responses.PageResponse findWorkflowJobResult(Pageable pageable, RequestParams<Wf00402VO> requestParams) {
        Page<Wf00402VO> pages = wf004Service.findWorkflowJobResult(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Find parameter result responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/03/list")
    public Responses.PageResponse findParameterResult(Pageable pageable, RequestParams<Wf00403VO> requestParams) {
        Page<Wf00403VO> pages = wf004Service.findParameterResult(pageable, requestParams);

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
    public ApiResponse saveWorkflow(@RequestBody List<Wf00401VO> requestParams) {
        return wf004Service.saveWorkflow(requestParams);
    }

    /**
     * Save workfloe job api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/02/save")
    @PostMapping
    public ApiResponse saveWorkflowJob(@RequestBody List<Wf00402VO> requestParams) {
        return wf004Service.saveWorkflowJob(requestParams);
    }

    /**
     * Save parameter api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/03/save")
    @PostMapping
    public ApiResponse saveParameter(@RequestBody List<Wf00403VO> requestParams) {
        return wf004Service.saveParameter(requestParams);
    }
}