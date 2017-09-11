package com.bgf.shbank.controller.view.mng.sla.sh_sla_a0;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShSlaA0ViewRouter extends BaseController {

    @GetMapping("/mng/sla/sh_sla_a0")
    public String view(ModelMap model) {
        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));

        return "/mng/sla/sh_sla_a0";
    }
}

