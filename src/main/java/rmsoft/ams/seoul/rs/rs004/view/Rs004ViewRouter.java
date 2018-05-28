package rmsoft.ams.seoul.rs.rs004.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Rs004ViewRouter extends BaseController {

    @GetMapping("/rs/rs004/rs004")
    public String view() {
        return "/rs/rs004/rs004";
    }
}

