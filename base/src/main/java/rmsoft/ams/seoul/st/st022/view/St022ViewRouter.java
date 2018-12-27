package rmsoft.ams.seoul.st.st022.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class St022ViewRouter extends BaseController {

    @GetMapping("/st/st022/st022")
    public String view(ModelMap model) {
        //model.addAttribute("statusUuid01", CommonCodeUtils.get("CD202"));
        //model.addAttribute("statusUuid02", CommonCodeUtils.get("CD207"));

        return "/st/st022/st022";
    }

}

