package com.bgf.shbank.controller.view.mng.error.error_status;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorStatusViewRouter extends BaseController {

    @GetMapping("/mng/error/error_status")
    public String view(ModelMap model) {

        /*model.addAttribute("cashPartStatus", CommonCodeUtils.get("CASH_PART_STATUS"));
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
        model.addAttribute("cardIssuedTerminalStatus", CommonCodeUtils.get("CARD_ISSUED_TERMINAL_STATUS"));*/

        //model.addAttribute("calleeReqGubunCode", CommonCodeUtils.get("CALLEE_REQ_GUBUN_CODE"));
        model.addAttribute("calleeReqReasonCode", CommonCodeUtils.get("CALLEE_REQ_REASON_CODE"));
        model.addAttribute("stextSendEnable", CommonCodeUtils.get("STEXT_SEND_ENABLE"));
        model.addAttribute("calleeCancleReasonCode", CommonCodeUtils.get("CALLEE_CANCEL_REQ_REASON_CODE"));

        return "/mng/error/error_status";
    }
}

