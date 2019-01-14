package rmsoft.ams.seoul.st.st007.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class St007ViewRouter extends BaseController {

    @GetMapping("/st/st007/st007")
    public String view(ModelMap model) {
        // model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
        model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st007/st007";
    }
}

