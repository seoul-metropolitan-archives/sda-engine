package com.bgf.shbank.controller.view.mng.cash.sh03001140;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh03001140ViewRouter extends BaseController {

    @GetMapping("/mng/cash/sh03001140")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        model.addAttribute("noneProcessAt", CommonCodeUtils.get("NONE_PROCESS_AT"));

        return "/mng/cash/sh03001140";
    }
}

