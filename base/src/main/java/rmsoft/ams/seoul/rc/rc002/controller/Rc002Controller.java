package rmsoft.ams.seoul.rc.rc002.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.rc.rc002.service.Rc002Service;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00204VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc002VO;

import java.util.Map;

/**
 * The type Rc 002 controller.
 */
@RestController
@RequestMapping("/api/v1/rc/rc002")
public class Rc002Controller extends MessageBaseController
{
    @Autowired
    private Rc002Service rc002Service;

    /**
     * Save api response.
     *
     * @param data the data
     * @return the api response
     */
    @RequestMapping("/save")
    public Map save(@RequestBody Rc002VO data)
    {
        return rc002Service.save(data);
    }

    @RequestMapping("/getTreeData")
    public Responses.ListResponse getTreeData(Rc00204VO param)
    {
        return Responses.ListResponse.of(rc002Service.getTreeData(param));
    }

}
