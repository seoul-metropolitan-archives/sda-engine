package rmsoft.ams.seoul.ad.ad002.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad002.service.Ad002Service;
import rmsoft.ams.seoul.ad.ad002.vo.Ad00201VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.List;

/**
 * The type Ad 002 controller.
 */
@RestController
@RequestMapping("/api/v1/ad/ad002")
public class Ad002Controller extends MessageBaseController
{

    @Autowired
    @Qualifier("Ad002Service")
    private Ad002Service service;

    /**
     * Gets message list.
     *
     * @param param the param
     * @return the message list
     */
    @RequestMapping("/getMessageList.do")
    public Responses.ListResponse getMessageList(@RequestBody Ad00201VO param)
    {
        return Responses.ListResponse.of(service.getMessageList(param));
    }

    /**
     * Save api response.
     *
     * @param ad00201VOList the ad 00201 vo list
     * @return the api response
     */
    @RequestMapping("/save")
    public ApiResponse save(@RequestBody List<Ad00201VO> ad00201VOList) {
        ApiResponse apiResponse = service.save(ad00201VOList);
        return apiResponse;
    }

}
