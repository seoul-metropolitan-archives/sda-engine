package rmsoft.ams.seoul.rc.rc004.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

/**
 * The type Rc 004 view router.
 */
@Controller
public class Rc004ViewRouter extends BaseController {

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/rc/rc004/rc004")
    public String view(ModelMap model) {
        model.addAttribute("typeUuid", CommonCodeUtils.get("CD136"));
        model.addAttribute("publishedStatusUuid", CommonCodeUtils.get("CD121"));
        model.addAttribute("openStatusUuid", CommonCodeUtils.get("CD123"));
        return "/rc/rc004/rc004";
    }
}

