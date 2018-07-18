package rmsoft.ams.seoul.rs.rs005.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Rs005ViewRouter extends BaseController {

    @GetMapping("/rs/rs005/rs005")
    public String view() {
        return "/rs/rs005/rs005";
    }
}

