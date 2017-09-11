package com.bgf.shbank.controller.view.mng.etc.sh04001130;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh04001130ViewRouter extends BaseController {

    @GetMapping("/mng/etc/sh04001130")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        model.addAttribute("branchCode", CommonCodeUtils.get("BRANCH_CODE"));

        model.addAttribute("branchGubunCode", CommonCodeUtils.get("BRANCH_GUBUN"));
        model.addAttribute("operTimeGubunCode", CommonCodeUtils.get("OPER_TIME_GUBUN"));
        model.addAttribute("checkOperEnable", CommonCodeUtils.get("CHECK_OPER_ENABLE"));

        return "/mng/etc/sh04001130";
    }
}

