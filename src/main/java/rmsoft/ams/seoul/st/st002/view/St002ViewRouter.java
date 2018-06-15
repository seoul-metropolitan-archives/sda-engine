package rmsoft.ams.seoul.st.st002.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class St002ViewRouter extends BaseController {

    @GetMapping("/st/st002/st002")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
        model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st002/st002";
    }
}

