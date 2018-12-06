package rmsoft.ams.seoul.lc.lc001.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

/**
 * The type Ad 003 view router.
 */
@Controller
public class Lc001ViewRouter extends BaseController {

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/lc/lc001/lc001")
    public String view(ModelMap model) {
        model.addAttribute("majorClassificationUuid", CommonCodeUtils.get("CD211"));
        model.addAttribute("middleClassificationUuid", CommonCodeUtils.get("CD212"));
        model.addAttribute("collectStatusUuid", CommonCodeUtils.get("CD213"));
        return "/lc/lc001/lc001";
    }
}

