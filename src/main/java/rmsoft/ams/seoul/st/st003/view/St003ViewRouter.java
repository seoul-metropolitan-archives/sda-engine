package rmsoft.ams.seoul.st.st003.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class St003ViewRouter extends BaseController {

    @GetMapping("/st/st003/st003")
    public String view() {
        return "/st/st003/st003";
    }
}

