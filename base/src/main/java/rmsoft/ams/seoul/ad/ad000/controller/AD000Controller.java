package rmsoft.ams.seoul.ad.ad000.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad000.service.Ad000Service;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

/**
 * The type Ad 000 controller.
 */
@RestController
@RequestMapping("/ad/ad000")
public class AD000Controller extends MessageBaseController
{

    @Autowired
    private Ad000Service service;

    /**
     * Search popup header object.
     *
     * @return the object
     */
    @RequestMapping("/getServiceList.do")
    @ResponseBody
    public Object searchPopupHeader()
    {
        return service.getServiceList();
    }
}
