package rmsoft.ams.seoul.common.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/common")
public class CommonController extends BaseController {
    @RequestMapping("/getUUID")
    public Responses.MapResponse getUUID() {
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("uuid", UUIDUtils.getUUID());
        return Responses.MapResponse.of(response);
    }
}
