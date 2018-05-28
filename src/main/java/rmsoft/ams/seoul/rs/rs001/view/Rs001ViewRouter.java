package rmsoft.ams.seoul.rs.rs001.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Rs001ViewRouter extends BaseController {

    @GetMapping("/rs/rs001/rs001")
    public String view() {
        return "/rs/rs001/rs001";
    }
}

