package com.bgf.shbank.controller.view.mng.etc.sh04001140;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh04001140ViewRouter extends BaseController {

    @GetMapping("/mng/etc/sh04001140")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        model.addAttribute("mngBranchCode", CommonCodeUtils.get("BRANCH_CODE"));
        model.addAttribute("modelCode", CommonCodeUtils.get("MODEL_CODE"));

        model.addAttribute("overhaulGubun", CommonCodeUtils.get("OVERHAUL_GUBUN"));

        model.addAttribute("overhaulCorp", CommonCodeUtils.get("TERMINAL_CORP_CODE"));
        model.addAttribute("inspectionCorp", CommonCodeUtils.get("TERMINAL_CORP_CODE"));

        return "/mng/etc/sh04001140";
    }
}

