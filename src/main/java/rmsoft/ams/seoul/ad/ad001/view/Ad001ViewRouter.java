package rmsoft.ams.seoul.ad.ad001.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

/**
 * The type Ad 001 view router.
 */
@Controller
public class Ad001ViewRouter extends BaseController {

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/ad/ad001/ad001")
    public String view(ModelMap model) {
        model.addAttribute("serviceList", CommonCodeUtils.get("CD006"));
        return "/ad/ad001/ad001";
    }
}

