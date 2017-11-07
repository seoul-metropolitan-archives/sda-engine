package rmsoft.ams.seoul.rc.rc004.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Rc004ViewRouter extends BaseController {

    @GetMapping("/rc/rc004/rc004")
    public String view(ModelMap model) {
        //model.addAttribute("serviceList", CommonCodeUtils.get("CD006"));
        return "/rc/rc004/rc004";
    }
}

