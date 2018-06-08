package rmsoft.ams.seoul.df.df001.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Df001ViewRouter extends BaseController {


    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/df/df001/df001")
    public String view(ModelMap model) {
        model.addAttribute("statusList", CommonCodeUtils.get("CD115"));
        model.addAttribute("eventTypeList", CommonCodeUtils.get("CD116"));

        return "/df/df001/df001";
    }
}

