package com.bgf.shbank.controller.view.mng.equip.facility_status;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FacilityStatusViewRouter extends BaseController {

    @GetMapping("/mng/equip/facility_status")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));

        model.addAttribute("installArticleGubun", CommonCodeUtils.get("INSTALL_ARTICLE_GUBUN"));
        model.addAttribute("facGubunCode", CommonCodeUtils.get("FAC_GUBUN_CODE"));
        model.addAttribute("facCode", CommonCodeUtils.get("FAC_CODE"));

        model.addAttribute("operEnable", CommonCodeUtils.get("OPER_ENABLE"));
        model.addAttribute("hireFacEnable", CommonCodeUtils.get("HIRE_FAC_ENABLE"));

        model.addAttribute("stextGubun", CommonCodeUtils.get("EQUIP_STEXT_GUBUN"));

        model.addAttribute("workCompleteEnable", CommonCodeUtils.get("WORK_COMPLETE_ENABLE"));
        model.addAttribute("resultStextGubun", CommonCodeUtils.get("RESULT_STEXT_GUBUN"));
        return "/mng/equip/facility_status";
    }
}

