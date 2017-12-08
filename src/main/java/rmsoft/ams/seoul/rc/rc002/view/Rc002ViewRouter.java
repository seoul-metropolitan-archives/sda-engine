package rmsoft.ams.seoul.rc.rc002.view;

import io.onsemiro.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.rc.rc002.service.Rc002Service;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

/**
 * The type Rc 002 view router.
 */
@Controller
public class Rc002ViewRouter extends BaseController {

    @Autowired
    private Rc002Service rc002Service;

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/rc/rc002/rc002")
    public String view(ModelMap model) {
        model.addAttribute("levelList", rc002Service.getLevel());
        model.addAttribute("typeList", CommonCodeUtils.get("CD127"));
        model.addAttribute("publishedStatusList", CommonCodeUtils.get("CD121"));
        return "/rc/rc002/rc002";
    }
}

