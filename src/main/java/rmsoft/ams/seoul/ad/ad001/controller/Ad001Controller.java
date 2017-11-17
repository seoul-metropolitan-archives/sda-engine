package rmsoft.ams.seoul.ad.ad001.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad001.service.Ad001Service;
import rmsoft.ams.seoul.ad.ad001.vo.Ad00101VO;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00402VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.common.domain.AdConfiguration;
import rmsoft.ams.seoul.common.domain.AdPopupDetail;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ad/ad001")
public class Ad001Controller extends MessageBaseController {

    @Autowired
    @Qualifier("AD001ServiceImpl")
    private Ad001Service service;

    @RequestMapping("/getEnviromentList.do")
    public Responses.ListResponse getEnviromentList(@RequestBody Ad00101VO param) {
        return Responses.ListResponse.of(service.getEnviromentList(param));
    }

    @RequestMapping("/save")
    public ApiResponse save(@RequestBody List<Ad00101VO> list) {
        ApiResponse apiResponse = service.save(list);
        return apiResponse;
    }

}
