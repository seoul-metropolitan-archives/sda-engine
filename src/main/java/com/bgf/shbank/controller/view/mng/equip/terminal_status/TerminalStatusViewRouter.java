package com.bgf.shbank.controller.view.mng.equip.terminal_status;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TerminalStatusViewRouter extends BaseController {

    @GetMapping("/mng/equip/terminal_status")
    public String view(ModelMap model) {

        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));

        model.addAttribute("checkOperEnable", CommonCodeUtils.get("CHECK_OPER_ENABLE"));
        model.addAttribute("cash50kOperEnable", CommonCodeUtils.get("CASH_50K_TERMINAL_OPER_ENABLE"));
        model.addAttribute("securityCorp", CommonCodeUtils.get("SECURITY_CORP"));
        model.addAttribute("installPlaceGubun", CommonCodeUtils.get("INSTALL_PLACE_GUBUN"));
        model.addAttribute("modelCode", CommonCodeUtils.get("MODEL_CODE"));
        model.addAttribute("branchGubun", CommonCodeUtils.get("BRANCH_GUBUN"));
        model.addAttribute("photoEnable", CommonCodeUtils.get("TAKE_PHOTO_ENABLE"));
        model.addAttribute("placeGubun", CommonCodeUtils.get("PLACE_GUBUN"));
        model.addAttribute("boothCorp", CommonCodeUtils.get("BOOTH_CORP"));
        model.addAttribute("boothType", CommonCodeUtils.get("BOOTH_TYPE"));
        model.addAttribute("operTimeGubun", CommonCodeUtils.get("OPER_TIME_GUBUN"));
        model.addAttribute("weekendOperGubun", CommonCodeUtils.get("WEEKEND_OPER_GUBUN"));
        model.addAttribute("terminalCorpCode", CommonCodeUtils.get("TERMINAL_CORP_CODE"));

        model.addAttribute("operEnable", CommonCodeUtils.get("OPER_ENABLE"));
        model.addAttribute("terminalType", CommonCodeUtils.get("TERMINAL_TYPE"));
        model.addAttribute("intercomEnable", CommonCodeUtils.get("INTERCOM_ENABLE"));
        model.addAttribute("envelopeEnable", CommonCodeUtils.get("ENVELOPE_ENABLE"));
        model.addAttribute("garbagecanEnable", CommonCodeUtils.get("GARBAGECAN_ENABLE"));
        model.addAttribute("shredderEnable", CommonCodeUtils.get("SHREDDER_ENABLE"));
        model.addAttribute("extinguisherEnable", CommonCodeUtils.get("EXTINGUISHER_ENABLE"));
        model.addAttribute("coolerHeaterEnable", CommonCodeUtils.get("COOLER_HEATER_ENABLE"));
        model.addAttribute("slopeEnable", CommonCodeUtils.get("SLOPE_ENABLE"));
        model.addAttribute("hireTerminalEnable", CommonCodeUtils.get("HIRE_TERMINAL_ENABLE"));
        model.addAttribute("installTerminalGubun", CommonCodeUtils.get("INSTALL_TERMINAL_GUBUN"));

        model.addAttribute("cashPartStatus", CommonCodeUtils.get("CASH_PART_STATUS"));
        model.addAttribute("enableIcCard", CommonCodeUtils.get("ENABLE_IC_CARD"));
        model.addAttribute("idScannerStatus", CommonCodeUtils.get("ID_SCANNER_STATUS"));
        model.addAttribute("checkPartStatus", CommonCodeUtils.get("CHECK_PART_STATUS"));
        model.addAttribute("enableEmv", CommonCodeUtils.get("ENABLE_EMV"));
        model.addAttribute("bioScannerStatus", CommonCodeUtils.get("BIO_SCANNER_STATUS"));
        model.addAttribute("portfolioPartStatus", CommonCodeUtils.get("PORTFOLIO_PART_STATUS"));
        model.addAttribute("enableIr", CommonCodeUtils.get("ENABLE_IR"));
        model.addAttribute("scrtyCardHighendAtmOnly", CommonCodeUtils.get("SCRTY_CARD_HIGHEND_ATM_ONLY"));
        model.addAttribute("jrnlPartStatus", CommonCodeUtils.get("JRNL_PART_STATUS"));
        model.addAttribute("enableRf", CommonCodeUtils.get("ENABLE_RF"));
        model.addAttribute("cardStyleOtp", CommonCodeUtils.get("CARD_STYLE_OTP"));
        model.addAttribute("rtrvlBoxStatus", CommonCodeUtils.get("RTRVL_BOX_STATUS"));
        model.addAttribute("enableFingerprint", CommonCodeUtils.get("ENABLE_FINGERPRINT"));
        model.addAttribute("s20General", CommonCodeUtils.get("S20_GENERAL"));
        model.addAttribute("s20Frpy", CommonCodeUtils.get("S20_FRPY"));
        model.addAttribute("cardPartStatus", CommonCodeUtils.get("CARD_PART_STATUS"));
        model.addAttribute("encryptionStatus", CommonCodeUtils.get("ENCRYPTION_STATUS"));
        model.addAttribute("bnkbPartStatus", CommonCodeUtils.get("BNKB_PART_STATUS"));
        model.addAttribute("slineGeneral", CommonCodeUtils.get("SLINE_GENERAL"));
        model.addAttribute("slineFrpy", CommonCodeUtils.get("INSTALL_TERMINAL_GUBUN"));
        model.addAttribute("slineFrpy", CommonCodeUtils.get("SLINE_FRPY"));
        model.addAttribute("giroPartStatus", CommonCodeUtils.get("GIRO_PART_STATUS"));
        model.addAttribute("suspendStatus", CommonCodeUtils.get("SUSPEND_STATUS"));
        model.addAttribute("cashPartStatus50kWon", CommonCodeUtils.get("CASH_PART_STATUS_50K_WON"));
        model.addAttribute("fourtuneGeneral", CommonCodeUtils.get("FOURTUNE_GENERAL"));
        model.addAttribute("fourtuneFrpy", CommonCodeUtils.get("FOURTUNE_FRPY"));
        model.addAttribute("hwErrorStatus", CommonCodeUtils.get("HW_ERROR_STATUS"));
        model.addAttribute("atmcExcclcTerminalError", CommonCodeUtils.get("ATMC_EXCCLC_TERMINAL_ERROR"));
        model.addAttribute("maintenanceStatus", CommonCodeUtils.get("MAINTENANCE_STATUS"));
        model.addAttribute("atmcExcclcExecResult", CommonCodeUtils.get("ATMC_EXCCLC_EXEC_RESULT"));
        model.addAttribute("rcppayBnkb", CommonCodeUtils.get("RCPPAY_BNKB"));
        model.addAttribute("enableDesBoard", CommonCodeUtils.get("ENABLE_DES_BOARD"));
        model.addAttribute("cardIssuedTerminalStatus", CommonCodeUtils.get("CARD_ISSUED_TERMINAL_STATUS"));

        model.addAttribute("stextGubun", CommonCodeUtils.get("EQUIP_STEXT_GUBUN"));

        model.addAttribute("workCompleteEnable", CommonCodeUtils.get("WORK_COMPLETE_ENABLE"));
        model.addAttribute("resultStextGubun", CommonCodeUtils.get("RESULT_STEXT_GUBUN"));
        return "/mng/equip/terminal_status";
    }
}

