package rmsoft.ams.seoul.st.st001.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class St001ViewRouter extends BaseController {

    @GetMapping("/st/st001/st001")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
        return "/st/st001/st001";
    }
}

