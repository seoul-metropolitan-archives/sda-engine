package rmsoft.ams.seoul.st.st028.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class St028ViewRouter extends BaseController {

    @GetMapping("/st/st028/st028")
    public String view(ModelMap model) {
        model.addAttribute("modeUuid", CommonCodeUtils.get("CD223"));
        //model.addAttribute("statusUuid02", CommonCodeUtils.get("CD207"));

        return "/st/st028/st028";
    }

}

