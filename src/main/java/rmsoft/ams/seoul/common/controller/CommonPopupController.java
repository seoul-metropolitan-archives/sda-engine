/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.controller;

import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ac.ac003.service.Ac003Service;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00301VO;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;
import rmsoft.ams.seoul.common.service.CommonPopupService;

import java.util.ArrayList;
import java.util.Map;

/**
 * Ac003Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017-10-12 오후 5:48
 **/
@RestController
@RequestMapping(value = "/api/v1/common/popup")
public class CommonPopupController extends MessageBaseController {

    @Autowired
    private CommonPopupService commonPopupService;

    @RequestMapping("/getPopupInfo")
    public Responses.MapResponse getPopupInfo(@RequestBody AdPopupHeader adPopupHeader ) {

        return Responses.MapResponse.of(commonPopupService.getPopupInfo(adPopupHeader));
    }
    @RequestMapping("/search")
    public Responses.ListResponse search(@RequestBody Map<String,Object> param ) {

        return Responses.ListResponse.of(commonPopupService.search(param));
    }

}