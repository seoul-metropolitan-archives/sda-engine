package com.bgf.shbank.controller.view.mng.cash.sh03001220;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh03001220ViewRouter extends BaseController {

    @GetMapping("/mng/cash/sh03001220")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        model.addAttribute("reqGubun", CommonCodeUtils.get("REQ_GUBUN"));
        model.addAttribute("receiveBranchCode", CommonCodeUtils.get("RECEIVE_BRANCH_CODE"));
        model.addAttribute("manlessBranchCode", CommonCodeUtils.get("MANLESS_BRANCH_CODE"));
        model.addAttribute("atmCode", CommonCodeUtils.get("AMT_CODE"));
        return "/mng/cash/sh03001220";
    }
}

