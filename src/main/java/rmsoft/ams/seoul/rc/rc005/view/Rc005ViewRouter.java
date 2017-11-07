package rmsoft.ams.seoul.rc.rc005.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Rc005ViewRouter extends BaseController {

    @GetMapping("/rc/rc005/rc005")
    public String view(ModelMap model) {
        //model.addAttribute("serviceList", CommonCodeUtils.get("CD006"));
        return "/rc/rc005/rc005";
    }
}

