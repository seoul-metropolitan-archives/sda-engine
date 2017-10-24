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


@RestController
@RequestMapping("/api/v1/cl001/")
public class Cl001Controller extends MessageBaseController {

    @Autowired
    private Cl001Service cl001Service;

    @GetMapping("/01/list")
    public Responses.PageResponse getClassificationSchemeList(Pageable pageable, RequestParams<Cl00101VO> requestParams) {
        Page<Cl00101VO> pages = cl001Service.getClassificationSchemeList(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/02/detail")
    public Cl00102VO getClassificationSchemeDetail(RequestParams<Cl00101VO> requestParams) {
        Cl00102VO cl00102VO = cl001Service.getClassificationSchemeDetail(requestParams);
        return cl001Service.getClassificationSchemeDetail(requestParams);
    }

    @PutMapping(value = "/03/saveList")
    @PostMapping
    public ApiResponse updateClassificationSchemeList(@RequestBody List<Cl00101VO> requestParams) {
        ApiResponse apiResponse = cl001Service.updateClassificationSchemeList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
}
