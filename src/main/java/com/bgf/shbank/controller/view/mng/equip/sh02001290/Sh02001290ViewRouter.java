package com.bgf.shbank.controller.view.mng.equip.sh02001290;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh02001290ViewRouter extends BaseController {

    @GetMapping("/mng/equip/sh02001290")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        model.addAttribute("costGubun", CommonCodeUtils.get("COST_GUBUN"));
        model.addAttribute("detailItemGubun", CommonCodeUtils.get("DETAIL_ITEM_GUBUN"));
        return "/mng/equip/sh02001290";
    }
}

