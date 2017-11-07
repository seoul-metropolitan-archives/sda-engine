package rmsoft.ams.seoul.ad.ad006.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad006.vo.Ad00601VO;
import rmsoft.ams.seoul.ad.ad006.vo.Ad00602VO;
import rmsoft.ams.seoul.ad.ad006.service.Ad006Service;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ad/ad006/ad006")
public class Ad006Controller extends MessageBaseController {

    @Autowired
    private Ad006Service service;

    @RequestMapping("/getUUID")
    public Responses.MapResponse getUUID() {
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("uuid", UUIDUtils.getUUID());
        return Responses.MapResponse.of(response);
    }

    @RequestMapping("/searchEntityType")
    public Responses.ListResponse searchEntityType(@RequestBody Ad00601VO param) {
        return Responses.ListResponse.of(service.searchEntityType(param));
    }

    @RequestMapping("/getEntityColumnList")
    public Responses.ListResponse getEntityColumnList(@RequestBody Ad00602VO param) {
        return Responses.ListResponse.of(service.getEntityColumnList(param));
    }

    @RequestMapping("/saveEntityType")
    public ApiResponse saveEntityType(@RequestBody List<Ad00601VO> ad00601VOList)
    {
        return service.saveEntityType(ad00601VOList);
    }

    @RequestMapping("/saveEntityColumn")
    public ApiResponse saveEntityColumn(@RequestBody List<Ad00602VO> ad00602VOList)
    {
        return service.saveEntityColumn(ad00602VOList);
    }

}
