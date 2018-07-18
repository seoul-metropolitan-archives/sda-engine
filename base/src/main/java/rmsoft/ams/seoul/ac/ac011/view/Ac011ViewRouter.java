package rmsoft.ams.seoul.ac.ac011.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Ac011ViewRouter extends BaseController {

    @GetMapping("/ac/ac011/ac011")
    public String view() {
        return "/ac/ac011/ac011";
    }
}

