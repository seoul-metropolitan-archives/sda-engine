/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac006.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac006.service.Ac006Service;
import rmsoft.ams.seoul.ac.ac006.vo.Ac00601VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.List;

/**
 * Ac006Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017-10-12 오후 5:48
 **/
@RestController
@RequestMapping(value = "/api/v1/ac006/")
public class Ac006Controller extends MessageBaseController {

    @Autowired
    private Ac006Service ac006Service;

    @GetMapping("/01/list")
    public Responses.PageResponse findAllPermission(Pageable pageable, RequestParams<Ac00601VO> requestParams) {
        Page<Ac00601VO> pages = ac006Service.findAllPermission(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping(value = "/01/save")
    @PostMapping
    public ApiResponse saveUserGroup(@RequestBody List<Ac00601VO> requestParams) {
        return ac006Service.savePermission(requestParams);
    }
}