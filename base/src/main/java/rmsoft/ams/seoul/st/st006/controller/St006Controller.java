package rmsoft.ams.seoul.st.st006.controller;

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
import rmsoft.ams.seoul.cl.cl002.vo.Cl00201VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;
import rmsoft.ams.seoul.st.st006.service.St006Service;
import rmsoft.ams.seoul.st.st006.vo.St00601VO;
import rmsoft.ams.seoul.st.st006.vo.St00602VO;
import rmsoft.ams.seoul.st.st006.vo.St00603VO;
import rmsoft.ams.seoul.st.st006.vo.St00603VO;

import java.util.List;

/**
 * The type Cl 006 controller.
 */
@RestController
@RequestMapping("/api/v1/st/st006")
public class St008Controller extends BaseController {

    @Autowired
    private St006Service st006Service;

    /**
     * Gets classified record list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the classified record list
     */
    @GetMapping("/01/list01")
    public Responses.PageResponse getStContainer(Pageable pageable, RequestParams<St00601VO> requestParams) {
        Page<St00601VO> pages = st006Service.getStContainer(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/01/list02")
    public Responses.PageResponse getSelectedItem(Pageable pageable, RequestParams<St00603VO> requestParams) {
        Page<St00603VO> pages = st006Service.getSelectedItem(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    /*
    @GetMapping("/02/list01")
    public Responses.PageResponse getSelectedItem(Pageable pageable, RequestParams<St00603VO> requestParam) {
        Page<St00603VO> pages  = st006Service.getSelectedItem(pageable, requestParam);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @RequestMapping("/02/list03")
    public Cl00201VO getClassInfo(Pageable pageable, RequestParams<Cl00201VO> requestParams) {
        return st006Service.getClassInfo(pageable, requestParams);
    }
    @GetMapping("/02/list04")
    public Responses.PageResponse getSelectedItemSchedule(Pageable pageable, RequestParams<St00603VO> requestParam) {
        Page<St00603VO> pages  = st006Service.getSelectedItemSchedule(pageable, requestParam);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @PutMapping("/02/save")
    @PostMapping
    public ApiResponse saveClassifiedRecordList(@RequestBody St00602VO requestParams) {
        ApiResponse apiResponse = st006Service.saveClassifiedRecordList(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @PutMapping("/02/confirm")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<St00601VO> requestParams) {
        ApiResponse apiResponse = st006Service.updateStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
    @RequestMapping("/getAllNodes")
    public Responses.ListResponse getAllNode(Rc00101VO param)
    {
        return Responses.ListResponse.of(st006Service.getAllNode(param));
    }

    @RequestMapping("/getAllNodeSchedule")
    public Responses.ListResponse getAllNodeSchedule(Rc00101VO param)
    {
        return Responses.ListResponse.of(st006Service.getAllNodeSchedule(param));
    }

    @PutMapping("/02/save02")
    @PostMapping
    public ApiResponse saveClassDescription(@RequestBody Cl00201VO param)
    {
        ApiResponse apiResponse = st006Service.saveClassDescription(param);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }*/
}
