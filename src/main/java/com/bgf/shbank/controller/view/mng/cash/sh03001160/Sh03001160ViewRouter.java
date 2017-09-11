package com.bgf.shbank.controller.view.mng.cash.sh03001160;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh03001160ViewRouter extends BaseController {

    @GetMapping("/mng/cash/sh03001160")
    public String view(ModelMap model) {
        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        model.addAttribute("acceptEnable", CommonCodeUtils.get("ACCEPT_ENABLE"));
        model.addAttribute("closeGubun", CommonCodeUtils.get("CASH_SENDING_CLOSE_GUBUN"));
        return "/mng/cash/sh03001160";
    }
}

