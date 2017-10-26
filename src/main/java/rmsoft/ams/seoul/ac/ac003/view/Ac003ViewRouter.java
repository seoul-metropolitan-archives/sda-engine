package rmsoft.ams.seoul.ac.ac003.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Ac003ViewRouter extends BaseController {

    @GetMapping("/ac/ac003/ac003")
    public String view(ModelMap model) {
        model.addAttribute("userTypeUuid", CommonCodeUtils.get("CD107"));

        return "/ac/ac003/ac003";
    }
}

