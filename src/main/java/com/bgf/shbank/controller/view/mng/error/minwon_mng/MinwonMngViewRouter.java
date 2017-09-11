package com.bgf.shbank.controller.view.mng.error.minwon_mng;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MinwonMngViewRouter extends BaseController {

    @GetMapping("/mng/error/minwon_mng")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        model.addAttribute("minwonType", CommonCodeUtils.get("MINWON_TYPE"));
        model.addAttribute("minwonStatus", CommonCodeUtils.get("MINWON_STATUS"));
        model.addAttribute("handleDept", CommonCodeUtils.get("HANDLE_DEPT"));
        return "/mng/error/minwon_mng";
    }
}

