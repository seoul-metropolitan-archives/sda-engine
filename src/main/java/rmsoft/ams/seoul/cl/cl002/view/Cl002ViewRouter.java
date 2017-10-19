package rmsoft.ams.seoul.cl.cl002.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Cl002ViewRouter extends BaseController {

    @GetMapping("/cl/cl002/cl002")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD113"));
        model.addAttribute("classLevel", CommonCodeUtils.get("CD114"));
        return "/cl/cl002/cl002";
    }
}

