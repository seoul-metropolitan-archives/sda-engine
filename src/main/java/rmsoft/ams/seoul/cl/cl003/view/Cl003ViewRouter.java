package rmsoft.ams.seoul.cl.cl003.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Cl 003 view router.
 */
@Controller
public class Cl003ViewRouter extends BaseController {

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/cl/cl003/cl003")
    public String view(ModelMap model) { return "/cl/cl003/cl003"; }
}

