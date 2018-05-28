package rmsoft.ams.seoul.rs.rs002.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Rs002ViewRouter extends BaseController {

    @GetMapping("/rs/rs002/rs002")
    public String view() {
        return "/rs/rs002/rs002";
    }
}

