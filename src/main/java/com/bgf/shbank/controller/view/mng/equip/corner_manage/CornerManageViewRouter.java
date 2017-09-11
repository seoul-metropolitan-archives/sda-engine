package com.bgf.shbank.controller.view.mng.equip.corner_manage;

import com.bgf.shbank.domain.mng.equip.corner_manage.CornerManageVO;
import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CornerManageViewRouter extends BaseController {

    @GetMapping("/mng/equip/corner_manage")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        return "/mng/equip/corner_manage";
    }


    @PostMapping("/mng/equip/corner_manage/m01")
    public String viewModal(CornerManageVO vo, ModelMap model) {

        model.addAttribute("overhaulGubun", CommonCodeUtils.get("OVERHAUL_GUBUN"));
        return "/mng/equip/corner_manage_m01";
    }
}

