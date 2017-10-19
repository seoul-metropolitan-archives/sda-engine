/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ac.ac003.service.Ac003Service;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00301VO;

/**
 * Ac003Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017-10-12 오후 5:48
 **/
@RestController
@RequestMapping(value = "/api/v1/common")
public class CommonPopupController extends BaseController {

    @Autowired
    private Ac003Service ac003Service;

    @GetMapping("/01/list")
    public Responses.PageResponse findAllUser(Pageable pageable, RequestParams<Ac00301VO> requestParams) {
        Page<Ac00301VO> pages = ac003Service.findAllUser(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }
}