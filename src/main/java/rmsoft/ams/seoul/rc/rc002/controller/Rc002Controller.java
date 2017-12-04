package rmsoft.ams.seoul.rc.rc002.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.rc.rc001.service.Rc001Service;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;
import rmsoft.ams.seoul.rc.rc002.service.Rc002Service;
import rmsoft.ams.seoul.rc.rc002.vo.Rc002VO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rc/rc002")
public class Rc002Controller extends MessageBaseController
{
    @Autowired
    private Rc002Service rc002Service;

    @RequestMapping("/save")
    public ApiResponse save(@RequestBody Rc002VO data)
    {
        return rc002Service.save(data);
    }
}
