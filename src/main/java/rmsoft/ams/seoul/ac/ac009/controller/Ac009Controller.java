/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac009.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac009.service.Ac009Service;
import rmsoft.ams.seoul.ac.ac009.vo.Ac00901VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.List;

/**
 * Ac009Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-12 오후 5:48
 */
@RestController
@RequestMapping(value = "/api/v1/ac009/")
public class Ac009Controller extends MessageBaseController {

    @Autowired
    private Ac009Service ac009Service;

    /**
     * Find all menu responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/01/list")
    public Responses.PageResponse findAllMenu(Pageable pageable, RequestParams<Ac00901VO> requestParams) {
        Page<Ac00901VO> pages = ac009Service.findAllMenu(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Save menu api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/01/save")
    @PostMapping
    public ApiResponse saveMenu(@RequestBody List<Ac00901VO> requestParams) {
        return ac009Service.saveMenu(requestParams);
    }
}