/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf001.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.wf.wf001.service.Wf001Service;
import rmsoft.ams.seoul.wf.wf001.vo.Wf00101VO;
import rmsoft.ams.seoul.wf.wf001.vo.Wf00102VO;

import java.util.List;

/**
 * Wf001Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-12 오후 5:48
 */
@RestController
@RequestMapping(value = "/api/v1/wf001/")
public class Wf001Controller extends MessageBaseController {

    @Autowired
    private Wf001Service wf001Service;

    /**
     * Find all job responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/01/list")
    public Responses.PageResponse findAllJob(Pageable pageable, RequestParams<Wf00101VO> requestParams) {
        Page<Wf00101VO> pages = wf001Service.findAllJob(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Find parameter responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/02/list")
    public Responses.PageResponse findParameter(Pageable pageable, RequestParams<Wf00102VO> requestParams) {
        Page<Wf00102VO> pages = wf001Service.findParameter(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Save job api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/01/save")
    @PostMapping
    public ApiResponse saveJob(@RequestBody List<Wf00101VO> requestParams) {
        return wf001Service.saveJob(requestParams);
    }

    /**
     * Save parameter api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/02/save")
    @PostMapping
    public ApiResponse saveParameter(@RequestBody List<Wf00102VO> requestParams) {
        return wf001Service.saveParameter(requestParams);
    }
}