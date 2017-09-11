package com.bgf.shbank.controller.view.mng.cash.sh03001180;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh03001180ViewRouter extends BaseController {

    @GetMapping("/mng/cash/sh03001180")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        model.addAttribute("acceptEnable", CommonCodeUtils.get("ACCEPT_ENABLE"));
        model.addAttribute("closeGubun", CommonCodeUtils.get("CASH_SENDING_CLOSE_GUBUN"));

        model.addAttribute("monCashSendingEnable", CommonCodeUtils.get("MON_CASH_SENDING_ENABLE"));
        model.addAttribute("tueCashSendingEnable", CommonCodeUtils.get("TUE_CASH_SENDING_ENABLE"));
        model.addAttribute("wedCashSendingEnable", CommonCodeUtils.get("WED_CASH_SENDING_ENABLE"));
        model.addAttribute("thuCashSendingEnable", CommonCodeUtils.get("THU_CASH_SENDING_ENABLE"));
        model.addAttribute("friCashSendingEnable", CommonCodeUtils.get("FRI_CASH_SENDING_ENABLE"));

        return "/mng/cash/sh03001180";
    }
}

