package rmsoft.ams.seoul.cl.cl001.controller;

import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.cl.cl001.service.Cl001Service;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00102VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.List;


/**
 * The type Cl 001 controller.
 */
@RestController
@RequestMapping("/api/v1/cl001/")
public class Cl001Controller extends MessageBaseController {

    @Autowired
    private Cl001Service cl001Service;

    /**
     * Gets classification scheme list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the classification scheme list
     */
    @GetMapping("/01/list")
    public Responses.PageResponse getClassificationSchemeList(Pageable pageable, RequestParams<Cl00101VO> requestParams) {
        Page<Cl00101VO> pages = cl001Service.getClassificationSchemeList(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Gets classification scheme detail.
     *
     * @param requestParams the request params
     * @return the classification scheme detail
     */
    @GetMapping("/02/detail")
    public Cl00102VO getClassificationSchemeDetail(RequestParams<Cl00101VO> requestParams) {
        return cl001Service.getClassificationSchemeDetail(requestParams);
    }

    /**
     * Update classification scheme list api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/03/updateClassificationSchemeList")
    @PostMapping
    public ApiResponse updateClassificationSchemeList(@RequestBody List<Cl00101VO> requestParams) {
        ApiResponse apiResponse = cl001Service.updateClassificationSchemeList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

    /**
     * Update status api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/04/updateStatus")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<Cl00101VO> requestParams) {
        ApiResponse apiResponse = cl001Service.updateStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

    /**
     * Update classification scheme con detail.
     *
     * @param requestParams the request params
     */
    @PutMapping(value = "/05/updateClassificationSchemeConDetail")
    @PostMapping
    public void updateClassificationSchemeConDetail(@RequestBody Cl00102VO requestParams) {
        cl001Service.updateClassificationSchemeConDetail(requestParams);
    }
}
