package rmsoft.ams.seoul.ad.ad001.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad001.service.Ad001Service;
import rmsoft.ams.seoul.ad.ad001.vo.Ad00101VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.List;

/**
 * The type Ad 001 controller.
 */
@RestController
@RequestMapping("/api/v1/ad/ad001")
public class Ad001Controller extends MessageBaseController {

    @Autowired
    @Qualifier("AD001ServiceImpl")
    private Ad001Service service;

    /**
     * Gets enviroment list.
     *
     * @param param the param
     * @return the enviroment list
     */
    @RequestMapping("/getEnviromentList.do")
    public Responses.ListResponse getEnviromentList(@RequestBody Ad00101VO param) {
        return Responses.ListResponse.of(service.getEnviromentList(param));
    }

    /**
     * Save api response.
     *
     * @param list the list
     * @return the api response
     */
    @RequestMapping("/save")
    public ApiResponse save(@RequestBody List<Ad00101VO> list) {
        ApiResponse apiResponse = service.save(list);
        return apiResponse;
    }

}
