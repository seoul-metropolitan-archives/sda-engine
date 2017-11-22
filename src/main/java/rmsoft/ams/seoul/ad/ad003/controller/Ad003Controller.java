package rmsoft.ams.seoul.ad.ad003.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad003.service.Ad003Service;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00301VO;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00302VO;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00303VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ad/ad003/")
public class Ad003Controller extends MessageBaseController {

    @Autowired
    private Ad003Service ad003Service;

    @RequestMapping("/getUUID")
    public Responses.MapResponse getUUID() {
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("uuid", UUIDUtils.getUUID());
        return Responses.MapResponse.of(response);
    }

    @RequestMapping("/searchCodeHeader")
    public Responses.ListResponse searchCodeHeader(@RequestBody Ad00301VO param) {
        return Responses.ListResponse.of(ad003Service.searchCodeHeader(param));
    }
    @RequestMapping("/getCodeDetailList")
    public Responses.ListResponse getCodeDetailList(@RequestBody Ad00302VO param) {
        return Responses.ListResponse.of(ad003Service.getCodeDetailList(param));
    }
    @RequestMapping("/saveCodeHeader")
    public ApiResponse saveCodeHeader(@RequestBody List<Ad00301VO> ad00301VOList)
    {
        return ad003Service.saveCodeHeader(ad00301VOList);
    }
    @RequestMapping("/saveCodeDetail")
    public ApiResponse saveCodeDetail(@RequestBody List<Ad00302VO> ad00302VOList)
    {
        return ad003Service.saveCodeDetail(ad00302VOList);
    }


    @GetMapping("/getAllByMap")
    public Map<String, List<Ad00303VO>> getAllByMap() {
        return CommonCodeUtils.getAllByMap();
    }
}
