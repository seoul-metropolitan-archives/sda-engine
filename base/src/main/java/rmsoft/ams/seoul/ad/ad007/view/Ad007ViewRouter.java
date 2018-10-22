package rmsoft.ams.seoul.ad.ad007.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Ad007ViewRouter extends BaseController {

    @GetMapping("/ad/ad007/ad007")
    public String view(ModelMap model) {
        model.addAttribute("entityTypeList", CommonCodeUtils.get("CD147"));

        return "/ad/ad007/ad007";
    }
}


