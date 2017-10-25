package rmsoft.ams.seoul.ad.ad003.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Ad003ViewRouter extends BaseController {

    @GetMapping("/ad/ad003/ad003")
    public String view(ModelMap model) {
        model.addAttribute("codeList", CommonCodeUtils.get("CD001"));
        model.addAttribute("serviceList", CommonCodeUtils.get("CD006"));
        return "/ad/ad003/ad003";
    }
}

