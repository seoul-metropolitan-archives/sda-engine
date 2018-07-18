package rmsoft.ams.seoul.st.st003.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class St003ViewRouter extends BaseController {

    @GetMapping("/st/st003/st003")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
        model.addAttribute("aggregationTypeUuid", CommonCodeUtils.get("CD127"));
        model.addAttribute("itemTypeUuid", CommonCodeUtils.get("CD136"));
        return "/st/st003/st003";
    }

    @PostMapping("/st/st003/st003-p01")
    public String viewPopup(ModelMap model) {
        return "/st/st003/st003-p01";
    }
}

