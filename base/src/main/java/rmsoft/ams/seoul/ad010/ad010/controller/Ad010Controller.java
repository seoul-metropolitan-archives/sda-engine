package rmsoft.ams.seoul.ad010.ad010.controller;

import io.onsemiro.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad010.ad010.service.Ad010Service;

@RestController
@RequestMapping(value = "/api/v1/ad/ad010")
public class Ad010Controller extends BaseController {
    @Autowired
    private Ad010Service ad010Service;

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