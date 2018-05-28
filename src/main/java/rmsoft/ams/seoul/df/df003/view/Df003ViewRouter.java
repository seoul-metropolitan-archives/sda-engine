package rmsoft.ams.seoul.df.df003.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Df003ViewRouter extends BaseController {

    @GetMapping("/df/df003/df003")
    public String view() {
        return "/df/df003/df003";
    }
}

