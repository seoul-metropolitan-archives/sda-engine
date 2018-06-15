package rmsoft.ams.seoul.rs.rs002.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Rs002ViewRouter extends BaseController {

    @GetMapping("/rs/rs002/rs002")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD134"));
        return "/rs/rs002/rs002";
    }
}

