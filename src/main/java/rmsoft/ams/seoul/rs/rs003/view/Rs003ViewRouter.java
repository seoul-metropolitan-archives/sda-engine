package rmsoft.ams.seoul.rs.rs003.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Rs003ViewRouter extends BaseController {

    @GetMapping("/rs/rs003/rs003")
    public String view() {
        return "/rs/rs003/rs003";
    }
}

