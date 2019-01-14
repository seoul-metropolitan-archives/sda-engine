package rmsoft.ams.seoul.ad.ad010.controller;

import io.onsemiro.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad010.service.Ad010Service;
import rmsoft.ams.seoul.ad.ad010.vo.Ad01001VO;

@RestController
@RequestMapping(value = "/api/v1/ad/ad010")
public class Ad010Controller extends BaseController {
    @Autowired
    private Ad010Service ad010Service;

    @GetMapping("/01/getList01")
    public Ad01001VO getList01() {
        return ad010Service.getList01();
    }
//    @GetMapping("/01/getNoticeList")
//    public Responses.PageResponse getNoticeList(Pageable pageable, RequestParams<Ad01001VO> requestParams) {
//        Page<Ad01001VO> pages = ad010Service.getNoticeList(pageable, requestParams);
//        return Responses.PageResponse.of(pages.getContent(), pages);
//    }

//    @PutMapping(value = "/02/confirm")
//    @PostMapping
//    public ApiResponse updateStatus(@RequestBody List<Rs00101VO> requestParams) {
//        ApiResponse apiResponse = rs001Service.updateStatus(requestParams);
//        if(apiResponse.getStatus() == -1) {
//            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
//        }
//        return apiResponse;
//    }
//
//    @PutMapping("/03/save")
//    @PostMapping
//    public ApiResponse updateRsGeneralRecordScheduleList(@RequestBody List<Rs00101VO> requestParams) {
//        ApiResponse apiResponse = rs001Service.updateRsGeneralRecordScheduleList(requestParams);
//        if(apiResponse.getStatus() == -1) {
//            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
//        }
//        return apiResponse;
//    }
}