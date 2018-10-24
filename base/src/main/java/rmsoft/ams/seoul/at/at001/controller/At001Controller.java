package rmsoft.ams.seoul.at.at001.controller;

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
import rmsoft.ams.seoul.at.at001.service.At001Service;
import rmsoft.ams.seoul.at.at001.vo.At00101VO;
import rmsoft.ams.seoul.at.at001.vo.At00103VO;
import rmsoft.ams.seoul.at.at001.vo.At001VO;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00201VO;
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
@RequestMapping("/api/v1/at/at001")
public class At001Controller extends BaseController {

    @Autowired
    private At001Service at001Service;

    /**
     * Gets classified record list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the classified record list
     */
    @GetMapping("/01/list")
    public Responses.PageResponse getAuthorityList(Pageable pageable, RequestParams<At00101VO> requestParams) {
        Page<At001VO> pages = at001Service.getAuthorityList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/02/list")
    public Responses.PageResponse getAuthorityListForMetaInfo(Pageable pageable, RequestParams<At00101VO> requestParams) {
        Page<At001VO> pages = at001Service.getAuthorityListForMetaInfo(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/03/list")
    public Responses.PageResponse getAuthorityMetaInfoList(Pageable pageable, RequestParams<At00103VO> requestParams) {
        Page<At001VO> pages = at001Service.getAuthorityMetaInfoList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping("/01/save")
    @PostMapping
    public ApiResponse saveAuthority(@RequestBody At001VO requestParams) {
        ApiResponse apiResponse = at001Service.saveAuthority(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

}
