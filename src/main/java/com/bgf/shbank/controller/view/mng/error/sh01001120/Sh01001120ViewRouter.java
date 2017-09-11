package com.bgf.shbank.controller.view.mng.error.sh01001120;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh01001120ViewRouter extends BaseController {

    @GetMapping("/mng/error/sh01001120")
    public String view(ModelMap model) {
        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));

        return "/mng/error/sh01001120";
    }
}

