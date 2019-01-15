package rmsoft.ams.seoul.ad.ad010.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * The type Ac 003 view router.
 */
@Controller
public class Ad010ViewRouter extends BaseController {

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @PostMapping("/ad/ad010/ad010-p01")
    public String viewPopup(ModelMap model) {
        return "/ad/ad010/ad010-p01";
    }
}

