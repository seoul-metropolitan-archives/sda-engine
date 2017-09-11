package io.onsemiro.controller.view;

import io.onsemiro.controller.BaseController;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserControllerViewRouter extends BaseController {
    @GetMapping("/user")
    public String view(ModelMap model) {
        model.addAttribute("jisaCode", CommonCodeUtils.get("JISA_CODE"));
        return "/user";
    }
}

