package rmsoft.ams.seoul.st.st009.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

@Controller
public class St009ViewRouter extends BaseController {

    @GetMapping("/st/st009/st009")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD208"));
        return "/st/st009/st009";
    }

    @PostMapping("/st/st009/st009-p01")
    public String viewPopup_01(ModelMap model) {
        // 반출서 작성
//        model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
//        model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st009/st009-p01";
    }
}
