package rmsoft.ams.seoul.rc.rc003.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Rc003ViewRouter extends BaseController {

    @GetMapping("/rc/rc003/rc003")
    public String view(ModelMap model) {
        //model.addAttribute("serviceList", CommonCodeUtils.get("CD006"));
        return "/rc/rc003/rc003";
    }
}

