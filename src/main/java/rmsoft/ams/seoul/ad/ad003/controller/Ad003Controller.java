package rmsoft.ams.seoul.ad.ad003.controller;

import io.onsemiro.core.api.response.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad003.service.Ad003Service;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00303VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ad/ad003/")
public class Ad003Controller {

    @Autowired
    private Ad003Service service;

    @RequestMapping("/getCode")
    public Responses.ListResponse getCode(@RequestBody Ad00303VO param) {
        return Responses.ListResponse.of(service.getCode(param));
    }

    @GetMapping("/getAllByMap")
    public Map<String, List<Ad00303VO>> getAllByMap() {
        return CommonCodeUtils.getAllByMap();
    }
}
