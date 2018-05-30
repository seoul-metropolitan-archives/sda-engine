package rmsoft.ams.seoul.ac.ac010.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Ac010ViewRouter extends BaseController {

    @GetMapping("/ac/ac010/ac010")
    public String view() {
        return "/ac/ac010/ac010";
    }
}

