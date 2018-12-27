package rmsoft.ams.seoul.st.st024.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class St024ViewRouter extends BaseController {

    @GetMapping("/st/st024/st024")
    public String view(ModelMap model) {
        //model.addAttribute("statusUuid01", CommonCodeUtils.get("CD202"));
        //model.addAttribute("statusUuid02", CommonCodeUtils.get("CD207"));

        return "/st/st024/st024";
    }

}

