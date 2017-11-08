package rmsoft.ams.seoul.rc.rc001.controller;

import io.onsemiro.core.api.response.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.rc.rc001.service.Rc001Service;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;

@RestController
@RequestMapping("/rc/rc001")
public class Rc001Controller extends MessageBaseController
{
    @Autowired
    private Rc001Service rc001Service;

    @RequestMapping("/getAllNodes")
    public Responses.ListResponse getAllNode(Rc00101VO param)
    {
        return Responses.ListResponse.of(rc001Service.getAllNode(param));
    }

    @RequestMapping("/getAggregationInfo")
    @ResponseBody
    public Object getAggregationInfo(Rc00101VO param)
    {
        return rc001Service.getAggregationInfo(param);
    }
    @RequestMapping("/getItemInfo")
    @ResponseBody
    public Object getItemInfo(Rc00101VO param)
    {
        return rc001Service.getItemInfo(param);
    }

    @RequestMapping("/getGridData")
    public Responses.ListResponse getGridData(Rc00101VO param)
    {
        return Responses.ListResponse.of(rc001Service.getGridData(param));
    }


}
