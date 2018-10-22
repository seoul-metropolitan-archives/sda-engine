/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.at.at002.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

/**
 * Wf001ViewRouter
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-23 오후 2:06
 */
@Controller
public class At002ViewRouter extends BaseController {

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/at/at002/at002")
    public String view(ModelMap model) {
        model.addAttribute("orgTypeUuid", CommonCodeUtils.get("CD163"));
        model.addAttribute("lvDtlUuid", CommonCodeUtils.get("CD164"));
        return "/at/at002/at002";
    }
}

