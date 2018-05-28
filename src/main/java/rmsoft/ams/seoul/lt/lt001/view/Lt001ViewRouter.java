package rmsoft.ams.seoul.lt.lt001.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Lt001ViewRouter extends BaseController {

    @GetMapping("/lt/lt001/lt001")
    public String view() {
        return "/lt/lt001/lt001";
    }
}

