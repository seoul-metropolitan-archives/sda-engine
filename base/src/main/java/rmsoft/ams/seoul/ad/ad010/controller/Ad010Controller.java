package rmsoft.ams.seoul.ad.ad010.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ad.ad010.service.Ad010Service;
import rmsoft.ams.seoul.ad.ad010.vo.Ad01001VO;
import rmsoft.ams.seoul.ad.ad010.vo.Ad01002VO;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v1/ad/ad010")
public class Ad010Controller extends BaseController {
    @Autowired
    private Ad010Service ad010Service;

    @GetMapping("/01/getList01")
    public Ad01001VO getList01() {
        return ad010Service.getList01();
    }

    @GetMapping("/01/getList02")
    public Responses.PageResponse getNoticeList(Pageable pageable) {
        Page<Ad01002VO> pages = ad010Service.getNoticeList(pageable);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping(value = "/02/save")
    @PostMapping
    public ApiResponse saveNotice(@RequestBody Ad01002VO requestParams) throws IOException {
        return ad010Service.saveNotice(requestParams);
    }

    @PutMapping(value = "/02/delete")
    @PostMapping
    public ApiResponse deleteNotice(@RequestBody Ad01002VO requestParams) {
        return ad010Service.deleteNotice(requestParams);
    }
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