package rmsoft.ams.seoul.st.st008.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

/**
 * The type Cl 003 view router.
 */
@Controller
public class St008ViewRouter extends BaseController {

    @GetMapping("/st/st008/st008")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
        model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st008/st008";
    }
    @PostMapping("/st/st008/st008-p01")
    public String viewPopup_01(ModelMap model) {
        // 반출서 작성
//        model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
//        model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st008/st008-p01";
    }
    @PostMapping("/st/st008/st008-p02")
    public String viewPopup_02(ModelMap model) {
        // 대상 추가
//        model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));

        return "/st/st008/st008-p02";
    }
    @PostMapping("/st/st008/st008-p03")
    public String viewPopup_03(ModelMap model) {

//        model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
//        model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st008/st008-p03";
    }
    @PostMapping("/st/st008/st008-p04")
    public String viewPopup_04(ModelMap model) {
        // 반출서 출력
//        model.addAttribute("statusUuid", CommonCodeUtils.get("CD138"));
//        model.addAttribute("containerTypeUuid", CommonCodeUtils.get("CD139"));
        return "/st/st008/st008-p04";
    }
}
