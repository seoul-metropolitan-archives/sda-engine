package com.bgf.shbank.controller.view.mng.etc.sh04001180;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh04001180ViewRouter extends BaseController {

    @GetMapping("/mng/etc/sh04001180")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        return "/mng/etc/sh04001180";
    }
}

