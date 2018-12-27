package rmsoft.ams.seoul.st.st018.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class St018ViewRouter extends BaseController {

    @GetMapping("/st/st018/st018")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD220"));
        model.addAttribute("aggregationTypeUuid", CommonCodeUtils.get("CD127"));
        //model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st018/st018";
    }
}

