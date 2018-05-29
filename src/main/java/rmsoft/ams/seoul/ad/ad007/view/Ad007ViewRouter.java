package rmsoft.ams.seoul.ad.ad007.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Ad007ViewRouter extends BaseController {

    @GetMapping("/ad/ad007/ad007")
    public String view() {
        return "/ad/ad007/ad007";
    }
}

