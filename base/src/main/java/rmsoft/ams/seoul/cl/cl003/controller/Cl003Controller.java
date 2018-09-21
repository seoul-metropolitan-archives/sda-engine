package rmsoft.ams.seoul.cl.cl003.controller;

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
import rmsoft.ams.seoul.cl.cl003.service.Cl003Service;
import rmsoft.ams.seoul.cl.cl003.vo.Cl00301VO;
import rmsoft.ams.seoul.cl.cl003.vo.Cl00302VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;
import rmsoft.ams.seoul.st.st003.vo.St00303VO;

import java.util.List;

/**
 * The type Cl 003 controller.
 */
@RestController
@RequestMapping("/api/v1/cl/cl003")
public class Cl003Controller extends BaseController {

    @Autowired
    private Cl003Service cl003Service;

    /**
     * Gets classified record list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the classified record list
     */
    @GetMapping("/01/list01")
    public Responses.PageResponse getClassAggregationList(Pageable pageable, RequestParams<Cl00301VO> requestParams) {
        Page<Cl00301VO> pages = cl003Service.getClassAggregationList(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/01/list02")
    public Responses.PageResponse getClassItemList(Pageable pageable, RequestParams<Cl00301VO> requestParams) {
        Page<Cl00301VO> pages = cl003Service.getClassItemList(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/02/list01")
    public Responses.PageResponse getSelectedItem(Pageable pageable, RequestParams<St00303VO> requestParam) {
        Page<St00303VO> pages  = cl003Service.getSelectedItem(pageable, requestParam);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @PutMapping("/02/save")
    @PostMapping
    public ApiResponse saveClassifiedRecordList(@RequestBody Cl00302VO requestParams) {
        ApiResponse apiResponse = cl003Service.saveClassifiedRecordList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @PutMapping("/02/confirm")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<Cl00301VO> requestParams) {
        ApiResponse apiResponse = cl003Service.updateStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @RequestMapping("/getAllNodes")
    public Responses.ListResponse getAllNode(Rc00101VO param)
    {
        return Responses.ListResponse.of(cl003Service.getAllNode(param));
    }
}
