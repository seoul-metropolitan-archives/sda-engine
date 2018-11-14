package rmsoft.ams.seoul.st.st011.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class St011ViewRouter extends BaseController {

    @GetMapping("/st/st011/st011")
    public String view(ModelMap model) {
        //model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
        //model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st011/st011";
    }
}

