package rmsoft.ams.seoul.ad.ad009.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Ad009ViewRouter extends BaseController {

    @GetMapping("/ad/ad009/ad009")
    public String view() {
        return "/ad/ad009/ad009";
    }
}

