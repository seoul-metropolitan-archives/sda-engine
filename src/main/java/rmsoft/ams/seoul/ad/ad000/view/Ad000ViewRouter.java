package rmsoft.ams.seoul.ad.ad000.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Ad 000 view router.
 */
@Controller
public class Ad000ViewRouter extends BaseController {

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/ad/ad000/ad000")
    public String view(ModelMap model) {
        /*
            model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
            model.addAttribute("calleeReqReasonCode", CommonCodeUtils.get("CALLEE_REQ_REASON_CODE"));
            model.addAttribute("stextGubun", CommonCodeUtils.get("ERROR_STEXT_GUBUN"));
            model.addAttribute("errorType", CommonCodeUtils.get("ERROR_TYPE"));
            modurityCorp", CommonCodeUtils.get("SECURITY_CORP"));
            model.addAttribute("cael.addAttribute("totalClassifyCode", CommonCodeUtils.get("TOTAL_CLASSIFY_CODE"));
            model.addAttribute("seclleeGubun", CommonCodeUtils.get("CALLEE_GUBUN"));
            model.addAttribute("modelCode", CommonCodeUtils.get("MODEL_CODE"));
            model.addAttribute("errorClassifyCode", CommonCodeUtils.get("ERROR_CLASSIFY_CODE"));
        */
        return "/ad/ad000/ad000";
    }
}

