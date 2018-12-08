package rmsoft.ams.seoul.st.st006.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

/**
 * The type Cl 003 view router.
 */
@Controller
public class St006ViewRouter extends BaseController {

    @GetMapping("/st/st006/st006")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
        model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st006/st006";
    }
    @PostMapping("/st/st006/st006-p01")
    public String viewPopup(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
        model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st006/st006-p01";
    }
}
