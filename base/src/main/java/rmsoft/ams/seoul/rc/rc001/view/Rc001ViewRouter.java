package rmsoft.ams.seoul.rc.rc001.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Rc001ViewRouter extends BaseController {

    @GetMapping("/rc/rc001/rc001")
    public String view(ModelMap model) {
        model.addAttribute("typeUuid", CommonCodeUtils.get("CD136"));
        model.addAttribute("publishedStatusUuid", CommonCodeUtils.get("CD121"));
        model.addAttribute("openStatusUuid", CommonCodeUtils.get("CD123"));
        //model.addAttribute("serviceList", CommonCodeUtils.get("CD006"));
        return "/rc/rc001/rc001";
    }
    @PostMapping("/rc/rc001/p_rc00101")
    public String movePopupView(ModelMap model) {
        //model.addAttribute("serviceList", CommonCodeUtils.get("CD006"));
        return "/rc/rc001/p_rc00101";
    }
    @PostMapping("/rc/rc001/p_rc00102")
    public String updateStatePopupView(ModelMap model) {
        model.addAttribute("updateStateList", CommonCodeUtils.get("CD121"));
        return "/rc/rc001/p_rc00102";
    }
}

