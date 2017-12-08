/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac005.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac005.service.Ac005Service;
import rmsoft.ams.seoul.ac.ac005.vo.Ac00501VO;
import rmsoft.ams.seoul.ac.ac005.vo.Ac00502VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.List;

/**
 * Ac005Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-12 오후 5:48
 */
@RestController
@RequestMapping(value = "/api/v1/ac005/")
public class Ac005Controller extends MessageBaseController {

    @Autowired
    private Ac005Service ac005Service;

    /**
     * Find all role responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/01/list")
    public Responses.PageResponse findAllRole(Pageable pageable, RequestParams<Ac00501VO> requestParams) {
        Page<Ac00501VO> pages = ac005Service.findAllRole(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Find role permission responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/02/list")
    public Responses.PageResponse findRolePermission(Pageable pageable, RequestParams<Ac00502VO> requestParams) {
        Page<Ac00502VO> pages = ac005Service.findRolePermission(pageable, requestParams);

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
    public ApiResponse saveGroup(@RequestBody List<Ac00501VO> requestParams) {
        return ac005Service.saveRole(requestParams);
    }

    /**
     * Save user group api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/02/save")
    @PostMapping
    public ApiResponse saveUserGroup(@RequestBody List<Ac00502VO> requestParams) {
        return ac005Service.saveRolePermission(requestParams);
    }
}