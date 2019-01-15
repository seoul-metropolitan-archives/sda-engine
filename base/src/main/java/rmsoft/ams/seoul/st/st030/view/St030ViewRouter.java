package rmsoft.ams.seoul.st.st030.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class St030ViewRouter extends BaseController {

    @GetMapping("/st/st030/st030")
    public String view(ModelMap model) {
        //model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
        model.addAttribute("sourceTypeUuid", CommonCodeUtils.get("CD221"));
        return "/st/st030/st030";
    }
}

