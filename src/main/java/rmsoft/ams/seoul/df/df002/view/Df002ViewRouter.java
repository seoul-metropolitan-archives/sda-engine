package rmsoft.ams.seoul.df.df002.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class Df002ViewRouter extends BaseController {


    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/df/df002/df002")
    public String view(ModelMap model) {

        return "/df/df002/df002";
    }
}

