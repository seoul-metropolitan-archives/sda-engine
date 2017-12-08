package rmsoft.ams.seoul.rc.rc001.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Rc 001 view router.
 */
@Controller
public class Rc001ViewRouter extends BaseController {

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/rc/rc001/rc001")
    public String view(ModelMap model) {
        //model.addAttribute("serviceList", CommonCodeUtils.get("CD006"));
        return "/rc/rc001/rc001";
    }
}

