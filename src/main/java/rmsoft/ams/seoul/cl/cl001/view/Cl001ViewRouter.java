package rmsoft.ams.seoul.cl.cl001.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

/**
 * The type Cl 001 view router.
 */
@Controller
public class Cl001ViewRouter extends MessageBaseController {

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/cl/cl001/cl001")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD111"));
        model.addAttribute("classificationTypeUuid", CommonCodeUtils.get("CD112"));
        return "/cl/cl001/cl001";
    }
}

