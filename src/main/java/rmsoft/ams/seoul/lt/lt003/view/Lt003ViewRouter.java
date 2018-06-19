package rmsoft.ams.seoul.lt.lt003.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Lt003ViewRouter extends BaseController {

    @GetMapping("/lt/lt003/lt003")
    public String view(ModelMap model) {

        return "/lt/lt003/lt003";
    }
}

