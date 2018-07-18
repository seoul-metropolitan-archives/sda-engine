/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac004.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac004.service.Ac004Service;
import rmsoft.ams.seoul.ac.ac004.vo.Ac00401VO;
import rmsoft.ams.seoul.ac.ac004.vo.Ac00402VO;
import rmsoft.ams.seoul.ac.ac004.vo.Ac00403VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.List;

/**
 * Ac004Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-12 오후 5:48
 */
@RestController
@RequestMapping(value = "/api/v1/ac004/")
public class Ac004Controller extends MessageBaseController {

    @Autowired
    private Ac004Service ac004Service;

    /**
     * Find all group responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/01/list")
    public Responses.PageResponse findAllGroup(Pageable pageable, RequestParams<Ac00401VO> requestParams) {
        Page<Ac00401VO> pages = ac004Service.findAllGroup(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Find user group user responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/02/list")
    public Responses.PageResponse findUserGroupUser(Pageable pageable, RequestParams<Ac00402VO> requestParams) {
        Page<Ac00402VO> pages = ac004Service.findUserGroupUser(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Find user role responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/03/list")
    public Responses.PageResponse findUserRole(Pageable pageable, RequestParams<Ac00403VO> requestParams) {
        Page<Ac00403VO> pages = ac004Service.findUserRole(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Save group api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/01/save")
    @PostMapping
    public ApiResponse saveGroup(@RequestBody List<Ac00401VO> requestParams) {
        return ac004Service.saveGroup(requestParams);
    }

    /**
     * Save user group api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/02/save")
    @PostMapping
    public ApiResponse saveUserGroup(@RequestBody List<Ac00402VO> requestParams) {
        return ac004Service.saveUserGroup(requestParams);
    }

    /**
     * Save user role api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/03/save")
    @PostMapping
    public ApiResponse saveUserRole(@RequestBody List<Ac00403VO> requestParams) {
        return ac004Service.saveUserRole(requestParams);
    }
}