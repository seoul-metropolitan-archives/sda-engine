package rmsoft.ams.seoul.cl.cl001.view;

import io.onsemiro.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.ad.ad007.dao.Ad007Mapper;
import rmsoft.ams.seoul.ad.ad007.vo.Ad00701VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

/**
 * The type Cl 001 view router.
 */
@Controller
public class Cl001ViewRouter extends MessageBaseController {

    @Autowired
    private Ad007Mapper mapper;

    /**
     * View string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/cl/cl001/cl001")
    public String view(ModelMap model) {
        model.addAttribute("statusUuid", CommonCodeUtils.get("CD111"));
        model.addAttribute("classificationTypeUuid", CommonCodeUtils.get("CD112"));

        Ad00701VO ad00701VO = new Ad00701VO();

        ad00701VO.setEntityType("CL_CLASSIFICATION_SCHEME_CON");
        model.addAttribute("templateList", mapper.searchSetup(ad00701VO));

        return "/cl/cl001/cl001";
    }
}