package rmsoft.ams.seoul.st.st001.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class St001ViewRouter extends BaseController {

    @GetMapping("/st/st001/st001")
    public String view() {
        return "/st/st001/st001";
    }
}

