package rmsoft.ams.seoul.rc.rc002.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Rc002ViewRouter extends BaseController {

    @GetMapping("/rc/rc002/rc002")
    public String view(ModelMap model) {
        model.addAttribute("typeUuid", CommonCodeUtils.get("CD127"));
        model.addAttribute("publishedStatusUuid", CommonCodeUtils.get("CD121"));
        return "/rc/rc002/rc002";
    }
}

