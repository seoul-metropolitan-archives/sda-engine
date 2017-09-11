package com.bgf.shbank.controller.view.mng.etc.agent_mng;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AgentMngViewRouter extends BaseController {

    @GetMapping("/mng/etc/agent_mng")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        model.addAttribute("empEnable", CommonCodeUtils.get("STAFF_ENABLE"));
        model.addAttribute("empGubun", CommonCodeUtils.get("EMP_GUBUN"));
        model.addAttribute("corpGubun", CommonCodeUtils.get("CORP_GUBUN"));
        return "/mng/etc/agent_mng";
    }
}

