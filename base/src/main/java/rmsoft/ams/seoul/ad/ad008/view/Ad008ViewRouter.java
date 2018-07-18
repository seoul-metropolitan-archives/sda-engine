package rmsoft.ams.seoul.ad.ad008.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Ad008ViewRouter extends BaseController {

    @GetMapping("/ad/ad008/ad008")
    public String view(ModelMap model) {
        model.addAttribute("functionList", CommonCodeUtils.get("CD108"));

        return "/ad/ad008/ad008";
    }
}

