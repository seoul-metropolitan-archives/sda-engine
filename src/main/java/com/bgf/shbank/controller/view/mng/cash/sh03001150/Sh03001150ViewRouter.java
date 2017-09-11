package com.bgf.shbank.controller.view.mng.cash.sh03001150;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh03001150ViewRouter extends BaseController {

    @GetMapping("/mng/cash/sh03001150")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        model.addAttribute("bankCode", CommonCodeUtils.get("BANK_CODE"));
        model.addAttribute("dealType", CommonCodeUtils.get("DEAL_TYPE"));

        return "/mng/cash/sh03001150";
    }
}

