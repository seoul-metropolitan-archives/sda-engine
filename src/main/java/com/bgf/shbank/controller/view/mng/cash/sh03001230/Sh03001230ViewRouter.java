package com.bgf.shbank.controller.view.mng.cash.sh03001230;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh03001230ViewRouter extends BaseController {

    @GetMapping("/mng/cash/sh03001230")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));

        return "/mng/cash/sh03001230";
    }
}

