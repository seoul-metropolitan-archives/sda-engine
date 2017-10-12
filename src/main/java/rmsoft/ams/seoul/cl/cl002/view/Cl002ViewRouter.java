package rmsoft.ams.seoul.cl.cl002.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Cl002ViewRouter extends BaseController {

    @GetMapping("/cl/cl002/cl002")
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
        return "/cl/cl002/cl002";
    }
}

