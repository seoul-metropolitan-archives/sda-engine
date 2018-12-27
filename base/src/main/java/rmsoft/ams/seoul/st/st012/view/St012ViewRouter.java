package rmsoft.ams.seoul.st.st012.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class St012ViewRouter extends BaseController {

    @GetMapping("/st/st012/st012")
    public String view(ModelMap model) {
        //model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
        //model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st012/st012";
    }
    @PostMapping("/st/st012/st012-p01")
    public String view_p01(ModelMap model) {
        //model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
        //model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st012/st012-p01";
    }
}

