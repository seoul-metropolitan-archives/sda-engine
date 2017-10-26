/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac009.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Ac009ViewRouter
 *
 * @author james
 * @version 1.0.0
 * @since 2017-10-23 오후 2:06
 **/
@Controller
public class Ac009ViewRouter extends BaseController {

    @GetMapping("/ac/ac009/ac009")
    public String view(ModelMap model) {
        return "/ac/ac009/ac009";
    }
}

