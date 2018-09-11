/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf999.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;
import rmsoft.ams.seoul.wf.wf999.service.Wf999Service;
import rmsoft.ams.seoul.wf.wf999.vo.Wf99901VO;
import rmsoft.ams.seoul.wf.wf999.vo.Wf99902VO;
import rmsoft.ams.seoul.wf.wf999.vo.Wf99903VO;

import java.util.List;

/**
 * Wf999Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-12 오후 5:48
 */
@RestController
@RequestMapping(value = "/api/v1/wf999/")
public class Wf999Controller extends MessageBaseController {

    @Autowired
    private Wf999Service wf999Service;

    /**
     * Save workflow api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/01/save")
    @PostMapping
    public ApiResponse extractArchive(@RequestBody List<Rc00502VO> requestParams) {
        return wf999Service.extractArchive(requestParams);
    }

}