package com.bgf.shbank.controller.view.mng.common;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SearchAgentViewRouter extends BaseController {
    @PostMapping("/mng/common/search-agent-modal")
    public String viewModal(ModelMap model) {

        //model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));

        return "/mng/common/search-agent-modal";
    }

}

