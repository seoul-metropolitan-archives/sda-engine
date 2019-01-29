package rmsoft.ams.seoul.st.st015.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st001.vo.St00101VO;
import rmsoft.ams.seoul.st.st015.service.St015Service;
import rmsoft.ams.seoul.st.st015.vo.St01501VO;
import rmsoft.ams.seoul.st.st015.vo.St01502VO;
import rmsoft.ams.seoul.st.st015.vo.St01503VO;
import rmsoft.ams.seoul.st.st029.service.St029Service;
import rmsoft.ams.seoul.st.st029.vo.St02901VO;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st015")
public class St015Controller extends BaseController {

    @Inject
    private St015Service st015Service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getStInventoryPlan(Pageable pageable, RequestParams<St01501VO> requestParams){
        Page<St01501VO> pages = st015Service.getStInventoryPlan(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(),pages);
    }

    @GetMapping("/01/list02")
    public Responses.PageResponse getStInventoryContainerResult(Pageable pageable, RequestParams<St01502VO> requestParams){
        Page<St01502VO> pages = st015Service.getStInventoryContainerResult(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(),pages);
    }

    @GetMapping("/01/list03")
    public Responses.PageResponse getStInventoryRecordResult(Pageable pageable, RequestParams<St01503VO> requestParams){
        Page<St01503VO> pages = st015Service.getStInventoryRecordResult(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(),pages);
    }

    @PutMapping(value = "/01/save01")
    @PostMapping
    public void saveGetStInventoryPlan(@RequestBody List<St01501VO> requestParams){
        st015Service.saveGetStInventoryPlan(requestParams);
    }

    @PutMapping("/02/confirm01")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<St01501VO> requestParams) {
        ApiResponse apiResponse = st015Service.updateStIventoryPlanStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

    @GetMapping("/01/excelDown")
    public ResponseEntity<InputStreamResource> getExcelDown(HttpServletResponse response) throws IOException {


        ByteArrayInputStream in = st015Service.getExcelDown();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", "st015.xlsx");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(new InputStreamResource(in));
    }


}

