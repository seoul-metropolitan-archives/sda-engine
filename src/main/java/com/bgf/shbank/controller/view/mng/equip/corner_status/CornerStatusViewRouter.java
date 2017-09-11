package com.bgf.shbank.controller.view.mng.equip.corner_status;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CornerStatusViewRouter extends BaseController {

    @GetMapping("/mng/equip/corner_status")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        model.addAttribute("branchCode", CommonCodeUtils.get("BRANCH_CODE"));
        model.addAttribute("cornerCode", CommonCodeUtils.get("CORNER_TERMINAL_CODE"));

        model.addAttribute("securityCorpCode", CommonCodeUtils.get("SECURITY_CORP"));
        model.addAttribute("placeGubun", CommonCodeUtils.get("PLACE_GUBUN"));
        model.addAttribute("branchGubun", CommonCodeUtils.get("BRANCH_GUBUN"));
        model.addAttribute("operTimeGubun", CommonCodeUtils.get("OPER_TIME_GUBUN"));
        model.addAttribute("checkOperEnable", CommonCodeUtils.get("CHECK_OPER_ENABLE"));
        model.addAttribute("specialStyleGubun", CommonCodeUtils.get("SPECIAL_STYLE_GUBUN"));
        model.addAttribute("facHireEnable", CommonCodeUtils.get("FAC_HIRE_ENABLE"));
        model.addAttribute("stextGubun", CommonCodeUtils.get("EQUIP_STEXT_GUBUN"));


        return "/mng/equip/corner_status";
    }
}

