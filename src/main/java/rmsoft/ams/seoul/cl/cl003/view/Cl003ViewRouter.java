package rmsoft.ams.seoul.cl.cl003.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Cl003ViewRouter extends BaseController {

    @GetMapping("/cl/cl003/cl003")
    public String view(ModelMap model) { return "/cl/cl003/cl003"; }
}

