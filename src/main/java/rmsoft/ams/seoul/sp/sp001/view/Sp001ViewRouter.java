/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.sp.sp001.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Sp001ViewRouter
 *
 * @author james
 * @version 1.0.0
 * @since 2017-10-23 오후 2:06
 **/
@Controller
public class Sp001ViewRouter extends BaseController {

    @GetMapping("/sp/sp001/sp001")
    public String view(ModelMap model) {
        return "/sp/sp001/sp001";
    }
}

