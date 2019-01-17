package rmsoft.ams.seoul.st.st019.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class St019ViewRouter extends BaseController {

    @GetMapping("/st/st019/st019")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD220"));
        model.addAttribute("publishSourceTypeUuid", CommonCodeUtils.get("CD221"));
        model.addAttribute("aggregationTypeUuid", CommonCodeUtils.get("CD127"));
        //model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st019/st019";
    }
}

