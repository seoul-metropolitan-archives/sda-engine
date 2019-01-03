package rmsoft.ams.seoul.st.st030.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class St030ViewRouter extends BaseController {

    @GetMapping("/st/st030/st030")
    public String view(ModelMap model) {
        //model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
        //model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st030/st030";
    }
}

