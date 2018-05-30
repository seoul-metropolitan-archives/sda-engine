package rmsoft.ams.seoul.ac.ac012.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Ac012ViewRouter extends BaseController {

    @GetMapping("/ac/ac012/ac012")
    public String view() {
        return "/ac/ac012/ac012";
    }
}

