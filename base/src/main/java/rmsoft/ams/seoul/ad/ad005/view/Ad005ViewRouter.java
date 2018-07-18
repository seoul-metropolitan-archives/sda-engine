package rmsoft.ams.seoul.ad.ad005.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Ad 005 view router.
 */
@Controller
public class Ad005ViewRouter extends BaseController {

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/ad/ad005/ad005")
    public String view(ModelMap model) {

        return "/ad/ad005/ad005";
    }
}
