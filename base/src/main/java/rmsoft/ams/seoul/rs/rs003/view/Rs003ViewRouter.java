package rmsoft.ams.seoul.rs.rs003.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Rs003ViewRouter extends BaseController {

    @GetMapping("/rs/rs003/rs003")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD134"));
        model.addAttribute("retentionPeriodUuid", CommonCodeUtils.get("CD133"));
        model.addAttribute("disposalTypeUuid", CommonCodeUtils.get("CD135"));

        return "/rs/rs003/rs003";
    }
}

