/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac007.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Ac007ViewRouter extends BaseController {

    @GetMapping("/ac/ac007/ac007")
    public String view(ModelMap model) {
        return "/ac/ac007/ac007";
    }
}

