package rmsoft.ams.seoul.ad.ad004.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad004.service.Ad004Service;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00401VO;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00402VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.common.domain.AdPopupDetail;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ad/ad004")
public class Ad004Controller extends MessageBaseController {

    @Autowired
    private Ad004Service service;

    @RequestMapping("/getUUID")
    public Responses.MapResponse getUUID() {
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("uuid",UUIDUtils.getUUID());
        return Responses.MapResponse.of(response);
    }

    @RequestMapping("/searchPopupHeader")
    @ResponseBody
    public Responses.ListResponse searchPopupHeader(@RequestBody Ad00401VO param) {
        return Responses.ListResponse.of(service.searchPopupHeader(param));
    }

    @RequestMapping("/getPopupDetail")
    @ResponseBody
    public Responses.ListResponse getPopupDetail(@RequestBody Ad00402VO param) {
        return Responses.ListResponse.of(service.getPopupDetail(param));
    }

    @RequestMapping("/savePopupHeader")
    public ApiResponse savePopupHeader(@RequestBody List<Ad00401VO> list) {
        ApiResponse apiResponse = service.savePopupHeader(ModelMapperUtils.mapList(list, AdPopupHeader.class));
        return apiResponse;
    }

    @RequestMapping("/savePopupDetail")
    public ApiResponse savePopupDetail(@RequestBody List<Ad00402VO> list) {
        ApiResponse apiResponse = service.savePopupDetail(ModelMapperUtils.mapList(list, AdPopupDetail.class));
        return apiResponse;
    }
}
