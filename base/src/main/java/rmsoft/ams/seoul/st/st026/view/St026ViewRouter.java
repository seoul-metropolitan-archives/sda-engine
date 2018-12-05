package rmsoft.ams.seoul.st.st026.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class St026ViewRouter extends BaseController {

    @GetMapping("/st/st026/st026")
    public String view(ModelMap model) {
        model.addAttribute("machineTypeUuid", CommonCodeUtils.get("CD200"));
        return "/st/st026/st026";
    }
}

