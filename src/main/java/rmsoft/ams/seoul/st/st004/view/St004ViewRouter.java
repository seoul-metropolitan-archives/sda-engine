package rmsoft.ams.seoul.st.st004.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class St004ViewRouter extends BaseController {

    @GetMapping("/st/st004/st004")
    public String view() {
        return "/st/st004/st004";
    }
}

