package rmsoft.ams.seoul.ad.ad004.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Ad004ViewRouter extends BaseController {

    @GetMapping("/ad/ad004/ad004")
    public String view(ModelMap model) {
        model.addAttribute("serviceList", CommonCodeUtils.get("CD006"));
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
        return "/ad/ad004/ad004";
    }
    @PostMapping("/ad/ad004/ad0041")
    public String view3(ModelMap model) {
        model.addAttribute("serviceList", CommonCodeUtils.get("CD006"));
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
        return "/ad/ad004/ad004";
    }
    @GetMapping("/ad/ad004/ad00401")
    @PostMapping("/ad/ad004/ad00401")
    public String view2(ModelMap model) {
        return "/ad/ad004/ad00401";
    }
}

