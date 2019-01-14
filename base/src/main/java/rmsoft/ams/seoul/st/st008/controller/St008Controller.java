package rmsoft.ams.seoul.st.st008.controller;

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
import rmsoft.ams.seoul.st.st008.service.St008Service;
import rmsoft.ams.seoul.st.st008.vo.St00801VO;
import rmsoft.ams.seoul.st.st008.vo.St00802VO;
import rmsoft.ams.seoul.st.st008.vo.St00802pVO;

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
    /*
    @GetMapping("/02/list01")
    public Responses.PageResponse getStTakeoutRecordResult(Pageable pageable, RequestParams<St00803VO> requestParam) {
        Page<St00803VO> pages  = st008Service.getStTakeoutRecordResult(pageable, requestParam);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @RequestMapping("/02/list03")
    public Cl00201VO getClassInfo(Pageable pageable, RequestParams<Cl00201VO> requestParams) {
        return st008Service.getClassInfo(pageable, requestParams);
    }
    @GetMapping("/02/list04")
    public Responses.PageResponse getStTakeoutRecordResultSchedule(Pageable pageable, RequestParams<St00803VO> requestParam) {
        Page<St00803VO> pages  = st008Service.getStTakeoutRecordResultSchedule(pageable, requestParam);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @PutMapping("/02/save")
    @PostMapping
    public ApiResponse saveClassifiedRecordList(@RequestBody St00802VO requestParams) {
        ApiResponse apiResponse = st008Service.saveClassifiedRecordList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @PutMapping("/02/confirm")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<St00801VO> requestParams) {
        ApiResponse apiResponse = st008Service.updateStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @RequestMapping("/getAllNodes")
    public Responses.ListResponse getAllNode(Rc00101VO param)
    {
        return Responses.ListResponse.of(st008Service.getAllNode(param));
    }

    @RequestMapping("/getAllNodeSchedule")
    public Responses.ListResponse getAllNodeSchedule(Rc00101VO param)
    {
        return Responses.ListResponse.of(st008Service.getAllNodeSchedule(param));
    }

    @PutMapping("/02/save02")
    @PostMapping
    public ApiResponse saveClassDescription(@RequestBody Cl00201VO param)
    {
        ApiResponse apiResponse = st008Service.saveClassDescription(param);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }*/
}
