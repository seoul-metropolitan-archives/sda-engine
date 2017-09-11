package com.bgf.shbank.controller.view.mng.equip.sh02001100;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh02001100ViewRouter extends BaseController {

    @GetMapping("/mng/equip/sh02001100")
    public String view(ModelMap model) {

        model.addAttribute("corner", CommonCodeUtils.get("CORNER_TERMINAL_CODE"));
        model.addAttribute("branch", CommonCodeUtils.get("BRANCH_CODE"));
        return "/mng/equip/sh02001100";
    }
}

