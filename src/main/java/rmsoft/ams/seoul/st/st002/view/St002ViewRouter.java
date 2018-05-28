package rmsoft.ams.seoul.st.st002.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class St002ViewRouter extends BaseController {

    @GetMapping("/st/st002/st002")
    public String view() {
        return "/st/st002/st002";
    }
}

