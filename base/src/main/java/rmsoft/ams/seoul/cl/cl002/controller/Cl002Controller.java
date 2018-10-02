package rmsoft.ams.seoul.cl.cl002.controller;

import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl002.service.Cl002Service;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00201VO;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00202VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Cl 002 controller.
 */
@RestController
@RequestMapping("/api/v1/cl002/")
public class Cl002Controller extends MessageBaseController{

    @Autowired
    private Cl002Service cl002Service;

    /**
     * Gets classification scheme.
     *
     * @return the classification scheme
     */
    @GetMapping("/01/getClassificationScheme")
    public Responses.MapResponse getClassificationScheme() {
        Map<String,Object> response = new HashMap<String,Object>();
        Cl00101VO cl00101VO = cl002Service.getClassificationScheme();
        if(cl00101VO == null) return null;
        response.put("classificationCode",cl00101VO.getClassificationCode());
        response.put("classificationName",cl00101VO.getClassificationName());
        response.put("classificationSchemeUuid",cl00101VO.getClassificationSchemeUuid());

        return Responses.MapResponse.of(response);
    }

    /**
     * Gets class list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the class list
     */
    @GetMapping("/02/getClassList")
    public Responses.PageResponse getClassList(Pageable pageable, RequestParams<Cl00201VO> requestParams) {
        Page<Cl00201VO> pages = cl002Service.getClassList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Gets class hierarchy list.
     *
     * @param pageable                 the pageable
     * @param classificationSchemeUuid the classification scheme uuid
     * @return the class hierarchy list
     */
    @GetMapping("/03/getClassHierarchyList")
    public Responses.PageResponse getClassHierarchyList(Pageable pageable, RequestParams<Cl00201VO> requestParams) {
        Page<Cl00201VO> pages = cl002Service.getClassHierarchyList(pageable,requestParams );
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Gets selected class list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the selected class list
     */
    @GetMapping("/04/getSelectedClassList")
    public Responses.PageResponse getSelectedClassList(Pageable pageable, RequestParams<Cl00201VO> requestParams) {
        Page<Cl00201VO> pages = cl002Service.getSelectedClassList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Gets selected class detail.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the selected class detail
     */
    @GetMapping("/05/getSelectedClassDetail")
    public Cl00202VO getSelectedClassDetail(Pageable pageable, RequestParams<Cl00201VO> requestParams) {
        return cl002Service.getSelectedClassDetail(pageable, requestParams);
    }

    /**
     * Update status confirm.
     *
     * @param requestParams the request params
     */
    @PutMapping(value = "/06/updateStatusConfirm")
    @PostMapping
    public void updateStatusConfirm(@RequestBody List<Cl00201VO> requestParams) {
        cl002Service.updateStatusConfirm(requestParams);
    }

    /**
     * Update status can cel api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/07/updateStatusCancel")
    @PostMapping
    public ApiResponse updateStatusCanCel(@RequestBody List<Cl00201VO> requestParams) {
        ApiResponse apiResponse = cl002Service.updateStatusCancel(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

    /**
     * Update class list.
     *
     * @param requestParams the request params
     */
    @PutMapping(value = "/08/updateClassList")
    @PostMapping
    public void updateClassList(@RequestBody List<Cl00201VO> requestParams) {
        cl002Service.updateClassList(requestParams);
    }

    /**
     * Update class con.
     *
     * @param requestParams the request params
     */
    @GetMapping("/09/updateClassCon")
    public void updateClassCon(RequestParams<Cl00202VO> requestParams) {
        cl002Service.updateClassCon(requestParams);

    }
}
