package rmsoft.ams.seoul.rs.rs005.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Rs005ViewRouter extends BaseController {

    @GetMapping("/rs/rs005/rs005")
    public String view(ModelMap model) {
        model.addAttribute("disposalStatus", CommonCodeUtils.get("CD137"));
        model.addAttribute("disposalTypeUuid", CommonCodeUtils.get("CD135"));
        return "/rs/rs005/rs005";
    }
}

