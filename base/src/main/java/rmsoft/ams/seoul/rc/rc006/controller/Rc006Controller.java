package rmsoft.ams.seoul.rc.rc006.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.rc.rc006.service.Rc006Service;
import rmsoft.ams.seoul.rc.rc006.vo.Rc00601VO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Rc 006 controller.
 */
@RestController
@RequestMapping("/rc/rc006/rc006")
public class Rc006Controller extends MessageBaseController {

    @Autowired
    private Rc006Service service;

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    @RequestMapping("/getUUID")
    public Responses.MapResponse getUUID() {
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("uuid", UUIDUtils.getUUID());
        return Responses.MapResponse.of(response);
    }

    /**
     * Search level of description responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @RequestMapping("/searchLevelOfDescription")
    public Responses.ListResponse searchLevelOfDescription(@RequestBody Rc00601VO param) {
        return Responses.ListResponse.of(service.searchLevelOfDescription(param));
    }

    /**
     * Save level of description api response.
     *
     * @param rc00601VOList the rc 00601 vo list
     * @return the api response
     */
    @RequestMapping("/saveLevelOfDescription")
    public ApiResponse saveLevelOfDescription(@RequestBody List<Rc00601VO> rc00601VOList)
    {
        return service.saveLevelOfDescription(rc00601VOList);
    }


}
