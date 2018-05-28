package rmsoft.ams.seoul.lt.lt004.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Lt004ViewRouter extends BaseController {

    @GetMapping("/lt/lt004/lt004")
    public String view() {
        return "/lt/lt004/lt004";
    }
}

