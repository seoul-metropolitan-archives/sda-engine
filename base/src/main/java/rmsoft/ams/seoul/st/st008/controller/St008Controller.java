package rmsoft.ams.seoul.st.st008.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st008.service.St008Service;
import rmsoft.ams.seoul.st.st008.vo.St00801VO;
import rmsoft.ams.seoul.st.st008.vo.St00802VO;
import rmsoft.ams.seoul.st.st008.vo.St00802pVO;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * The type Cl 008 controller.
 */
@RestController
@RequestMapping("/api/v1/st/st008")
public class St008Controller extends BaseController {

    @Autowired
    private St008Service st008Service;


    /**
     * Gets classified record list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the classified record list
     */
    @GetMapping("/01/list01")
    public Responses.PageResponse getStTakeoutRequest(Pageable pageable, RequestParams<St00801VO> requestParams) {
        Page<St00801VO> pages = st008Service.getStTakeoutRequest(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/01/list02")
    public Responses.PageResponse getStTakeoutRecordResult(Pageable pageable, RequestParams<St00802VO> requestParams) {
        Page<St00802VO> pages = st008Service.getStTakeoutRecordResult(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/01/excelDown")
    public ResponseEntity<InputStreamResource> getExcelDown(RequestParams<St00801VO>  vo) throws IOException {


        ByteArrayInputStream in = st008Service.getExcelDown(vo);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", "st008.xlsx");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(new InputStreamResource(in));
    }
    @PutMapping("/01/save")
    @PostMapping
    public ApiResponse saveStTakeoutRequest(@RequestBody St00801VO vo) {
        ApiResponse apiResponse = st008Service.saveStTakeoutRequest(vo);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;

    }

    @PutMapping("/01/save01")
    @PostMapping
    public ApiResponse saveStTakeoutRequestList(@RequestBody List<St00801VO> list) {
        ApiResponse apiResponse = st008Service.saveStTakeoutRequestList(list);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;

    }

    @PutMapping("/01/save02")
    @PostMapping
    public ApiResponse saveStTakeoutRecordResultList(@RequestBody List<St00802VO> list) {
        ApiResponse apiResponse = st008Service.saveStTakeoutRecordResultList(list);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;

    }

    @PutMapping("/02/save01")
    @PostMapping
    public ApiResponse saveStTakeoutAdd(@RequestBody St00802pVO vo) {
        ApiResponse apiResponse = st008Service.saveStTakeoutAdd(vo);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }


}
