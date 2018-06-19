package rmsoft.ams.seoul.lt.lt002.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Lt002ViewRouter extends BaseController {

    @GetMapping("/lt/lt002/lt002")
    public String view(ModelMap model) {

        return "/lt/lt002/lt002";
    }
}

