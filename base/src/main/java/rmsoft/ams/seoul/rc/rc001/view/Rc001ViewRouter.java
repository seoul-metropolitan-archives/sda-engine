package rmsoft.ams.seoul.rc.rc001.view;

import io.onsemiro.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rmsoft.ams.seoul.rc.rc002.service.Rc002Service;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

/**
 * The type Rc 001 view router.
 */
@Controller
public class Rc001ViewRouter extends BaseController {

    @Autowired
    private Rc002Service rc002Service;

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/rc/rc001/rc001")
    public String view(ModelMap model) {
        model.addAttribute("itemTypeUuid", CommonCodeUtils.get("CD136"));
        model.addAttribute("aggregationTypeUuid", CommonCodeUtils.get("CD127"));
        model.addAttribute("publishedStatusUuid", CommonCodeUtils.get("CD121"));
        model.addAttribute("openStatusUuid", CommonCodeUtils.get("CD123"));
        model.addAttribute("levelList", rc002Service.getLevel());
        //model.addAttribute("serviceList", CommonCodeUtils.get("CD006"));
        return "/rc/rc001/rc001";
    }

    /**
     * Move popup view string.
     *
     * @param model the model
     * @return the string
     */
    @PostMapping("/rc/rc001/p_rc00101")
    public String movePopupView(ModelMap model) {
        //model.addAttribute("serviceList", CommonCodeUtils.get("CD006"));
        return "/rc/rc001/p_rc00101";
    }

    /**
     * Update state popup view string.
     *
     * @param model the model
     * @return the string
     */
    @PostMapping("/rc/rc001/p_rc00102")
    public String updateStatePopupView(ModelMap model) {
        model.addAttribute("updateStateList", CommonCodeUtils.get("CD121"));
        return "/rc/rc001/p_rc00102";
    }

    /**
     * Add record item aggregation string.
     *
     * @param model the model
     * @return the string
     */
    @PostMapping("/rc/rc001/p_rc00103")
    public String addRecordItemAggregation(ModelMap model) {
        model.addAttribute("levelList", rc002Service.getLevel());

        return "/rc/rc001/p_rc00103";
    }

    /**
     * Classify records string.
     *
     * @param model the model
     * @return the string
     */
    @PostMapping("/rc/rc001/p_rc00104")
    public String classifyRecords(ModelMap model) {
        return "/rc/rc001/p_rc00104";
    }
}

