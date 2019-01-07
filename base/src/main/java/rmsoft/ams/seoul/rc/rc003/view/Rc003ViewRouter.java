package rmsoft.ams.seoul.rc.rc003.view;

import io.onsemiro.controller.BaseController;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rmsoft.ams.seoul.ad.ad007.dao.Ad007Mapper;
import rmsoft.ams.seoul.ad.ad007.vo.Ad00701VO;
import rmsoft.ams.seoul.rc.rc002.service.Rc002Service;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

/**
 * The type Rc 003 view router.
 */
@Controller
public class Rc003ViewRouter extends BaseController {

    @Autowired
    private Rc002Service rc002Service;
    @Autowired
    private Ad007Mapper mapper;

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/rc/rc003/rc003")
    public String view(ModelMap model) {
        model.addAttribute("levelList", rc002Service.getLevel());
        model.addAttribute("typeList", CommonCodeUtils.get("CD127"));
        model.addAttribute("publishedStatusList", CommonCodeUtils.get("CD121"));
        model.addAttribute("legalStatusList", CommonCodeUtils.get("CD203"));
        model.addAttribute("electronicRecordStatusList", CommonCodeUtils.get("CD204"));
        model.addAttribute("languageCodeList", CommonCodeUtils.get("CD153"));
        model.addAttribute("statusDescriptionList", CommonCodeUtils.get("CD205"));
        model.addAttribute("levelOfDetailList", CommonCodeUtils.get("CD164"));
        model.addAttribute("ruleConversionList", CommonCodeUtils.get("CD206"));

        Ad00701VO ad00701VO = new Ad00701VO();
        ad00701VO.setEntityType("RC_AGGREGATION_CON");
        model.addAttribute("templateList", mapper.searchSetup(ad00701VO));

        return "/rc/rc003/rc003";
    }

    /**
     * View aggregation string.
     *
     * @param model the model
     * @return the string
     */
    @PostMapping("/rc/rc003/p_rc003")
    public String viewAggregation(ModelMap model) {
        model.addAttribute("levelList", rc002Service.getLevel());
        model.addAttribute("typeList", CommonCodeUtils.get("CD127"));
        model.addAttribute("publishedStatusList", CommonCodeUtils.get("CD121"));
        model.addAttribute("legalStatusList", CommonCodeUtils.get("CD203"));
        model.addAttribute("electronicRecordStatusList", CommonCodeUtils.get("CD204"));
        model.addAttribute("languageCodeList", CommonCodeUtils.get("CD153"));
        model.addAttribute("statusDescriptionList", CommonCodeUtils.get("CD205"));
        model.addAttribute("levelOfDetailList", CommonCodeUtils.get("CD164"));
        model.addAttribute("ruleConversionList", CommonCodeUtils.get("CD206"));

        Ad00701VO ad00701VO = new Ad00701VO();
        ad00701VO.setEntityType("RC_AGGREGATION_CON");
        model.addAttribute("templateList", mapper.searchSetup(ad00701VO));

        return "/rc/rc003/p_rc003";
    }
}

