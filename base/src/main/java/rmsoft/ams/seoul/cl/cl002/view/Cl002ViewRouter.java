package rmsoft.ams.seoul.cl.cl002.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

/**
 * The type Cl 002 view router.
 */
@Controller
public class Cl002ViewRouter extends MessageBaseController {

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/cl/cl002/cl002")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD113"));
        model.addAttribute("classLevel", CommonCodeUtils.get("CD114"));
        model.addAttribute("statusDescription", CommonCodeUtils.get("CD205"));
        model.addAttribute("levelOfDetail", CommonCodeUtils.get("CD164"));
//        model.addAttribute("levelOfDetail", CommonCodeUtils.get("CD164"));
        return "/cl/cl002/cl002";
    }
}

