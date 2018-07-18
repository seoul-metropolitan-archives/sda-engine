package rmsoft.ams.seoul.rs.rs004.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Rs004ViewRouter extends BaseController {

    @GetMapping("/rs/rs004/rs004")
    public String view(ModelMap model) {
        model.addAttribute("disposalStatus", CommonCodeUtils.get("CD137"));
        model.addAttribute("disposalTypeUuid", CommonCodeUtils.get("CD135"));
        model.addAttribute("itemTypeUuid", CommonCodeUtils.get("CD136"));
        model.addAttribute("retentionPeriodUuid", CommonCodeUtils.get("CD133"));
        return "/rs/rs004/rs004";
    }
    @PostMapping("/rs/rs004/rs004-p01")
    public String viewPopup(ModelMap model) {
        return "/rs/rs004/rs004-p01";
    }
}

