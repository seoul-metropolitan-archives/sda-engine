/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac008.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac008.service.Ac008Service;
import rmsoft.ams.seoul.ac.ac008.vo.Ac00801VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.List;

/**
 * Ac008Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-12 오후 5:48
 */
@RestController
@RequestMapping(value = "/api/v1/ac008/")
public class Ac008Controller extends MessageBaseController {

    @Autowired
    private Ac008Service ac008Service;

    /**
     * Find all program responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/01/list")
    public Responses.PageResponse findAllProgram(Pageable pageable, RequestParams<Ac00801VO> requestParams) {
        Page<Ac00801VO> pages = ac008Service.findAllProgram(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Save program api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/01/save")
    @PostMapping
    public ApiResponse saveProgram(@RequestBody List<Ac00801VO> requestParams) {
        return ac008Service.saveProgram(requestParams);
    }
}