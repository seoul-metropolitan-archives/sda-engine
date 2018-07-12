package rmsoft.ams.seoul.df.df003.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Df003ViewRouter extends BaseController {

    @GetMapping("/df/df003/df003")
    public String view(ModelMap model) {
        model.addAttribute("statusList", CommonCodeUtils.get("CD115"));

        return "/df/df003/df003";
    }

    @PostMapping("/df/df003/df003-p01")
    public String viewPopup(ModelMap model) {

        return "/df/df003/df003-p01";
    }
}

