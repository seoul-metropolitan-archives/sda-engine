package rmsoft.ams.seoul.ad.ad008.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Ad008ViewRouter extends BaseController {

    @GetMapping("/ad/ad008/ad008")
    public String view() {
        return "/ad/ad008/ad008";
    }
}

