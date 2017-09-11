package com.bgf.shbank.controller.view.mng.equip.sh02001280;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh02001280ViewRouter extends BaseController {

    @GetMapping("/mng/equip/sh02001280")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        model.addAttribute("branchCode", CommonCodeUtils.get("BRANCH_CODE"));
        model.addAttribute("cornerCode", CommonCodeUtils.get("CORNER_TERMINAL_CODE"));
        return "/mng/equip/sh02001280";
    }
}

