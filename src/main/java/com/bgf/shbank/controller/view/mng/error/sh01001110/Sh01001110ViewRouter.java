package com.bgf.shbank.controller.view.mng.error.sh01001110;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh01001110ViewRouter extends BaseController {

    @GetMapping("/mng/error/sh01001110")
    public String view(ModelMap model) {
        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        model.addAttribute("calleeReqReasonCode", CommonCodeUtils.get("CALLEE_REQ_REASON_CODE"));
        model.addAttribute("stextGubun", CommonCodeUtils.get("ERROR_STEXT_GUBUN"));
        model.addAttribute("errorType", CommonCodeUtils.get("ERROR_TYPE"));
        model.addAttribute("totalClassifyCode", CommonCodeUtils.get("TOTAL_CLASSIFY_CODE"));
        model.addAttribute("securityCorp", CommonCodeUtils.get("SECURITY_CORP"));
        model.addAttribute("calleeGubun", CommonCodeUtils.get("CALLEE_GUBUN"));
        model.addAttribute("modelCode", CommonCodeUtils.get("MODEL_CODE"));
        model.addAttribute("errorClassifyCode", CommonCodeUtils.get("ERROR_CLASSIFY_CODE"));

        return "/mng/error/sh01001110";
    }
}

