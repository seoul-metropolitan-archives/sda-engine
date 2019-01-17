package rmsoft.ams.seoul.st.st020.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class St020ViewRouter extends BaseController {

    @GetMapping("/st/st020/st020")
    public String view(ModelMap model) {
        model.addAttribute("publishStatusUuid", CommonCodeUtils.get("CD220"));

        model.addAttribute("aggregationTypeUuid", CommonCodeUtils.get("CD127"));
        //model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st020/st020";
    }
}

