package rmsoft.ams.seoul.rc.rc006.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Rc 006 view router.
 */
@Controller
public class Rc006ViewRouter extends BaseController {

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/rc/rc006/rc006")
    public String view(ModelMap model) {

        return "/rc/rc006/rc006";
    }
}
