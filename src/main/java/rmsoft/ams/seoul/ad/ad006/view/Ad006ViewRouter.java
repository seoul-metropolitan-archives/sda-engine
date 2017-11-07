package rmsoft.ams.seoul.ad.ad006.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Ad006ViewRouter extends BaseController {

    @GetMapping("/ad/ad006/ad006")
    public String view(ModelMap model) {

        return "/ad/ad006/ad006";
    }
}
