/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac007.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac007.service.Ac007Service;
import rmsoft.ams.seoul.ac.ac007.vo.Ac00701VO;
import rmsoft.ams.seoul.ac.ac007.vo.Ac00702VO;
import rmsoft.ams.seoul.ac.ac007.vo.Ac00703VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.List;

/**
 * Ac007Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-12 오후 5:48
 */
@RestController
@RequestMapping(value = "/api/v1/ac007/")
public class Ac007Controller extends MessageBaseController {

    @Autowired
    private Ac007Service ac007Service;

    /**
     * Find all role responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/01/list")
    public Responses.PageResponse findAllRole(Pageable pageable, RequestParams<Ac00701VO> requestParams) {
        Page<Ac00701VO> pages = ac007Service.findAllRole(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Find role menu responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/02/list")
    public Responses.PageResponse findRoleMenu(Pageable pageable, RequestParams<Ac00702VO> requestParams) {
        Page<Ac00702VO> pages = ac007Service.findRoleMenu(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Find permission responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/03/list")
    public Responses.PageResponse findPermission(Pageable pageable, RequestParams<Ac00702VO> requestParams) {
        Page<Ac00703VO> pages = ac007Service.findPermission(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Save role menu api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/02/save")
    @PostMapping
    public ApiResponse saveRoleMenu(@RequestBody List<Ac00702VO> requestParams) {
        return ac007Service.saveRoleMenu(requestParams);
    }

        /**
         * Save permission api response.
         *
         * @param requestParams the request params
         * @return the api response
         */
        @PutMapping(value = "/03/save")
        @PostMapping
        public ApiResponse savePermission(@RequestBody List<Ac00703VO> requestParams) {
            return ac007Service.savePermission(requestParams);
    }
}