package rmsoft.ams.seoul.cl.cl001.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Cl001ViewRouter extends BaseController {

    @GetMapping("/cl/cl001/cl001")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD111"));
        model.addAttribute("classificationUuid", CommonCodeUtils.get("CD112"));
        return "/cl/cl001/cl001";
    }
}

