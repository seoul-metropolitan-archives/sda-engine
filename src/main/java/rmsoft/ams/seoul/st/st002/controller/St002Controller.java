package rmsoft.ams.seoul.st.st002.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st002.service.St002Service;
import rmsoft.ams.seoul.st.st002.vo.St002;
import rmsoft.ams.seoul.st.st002.vo.St00201VO;
import rmsoft.ams.seoul.st.st002.vo.St002VO;

import javax.inject.Inject;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st002")
public class St002Controller extends BaseController {

    @Inject
    private St002Service st002Service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getContainerHierarchyList(Pageable pageable) {
        Page<St00201VO> pages  = st002Service.getContainerHierarchyList(pageable);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/01/list02")
    public Responses.PageResponse getContainerList(Pageable pageable, RequestParams<St00201VO> requestParams) {
        Page<St00201VO> pages = st002Service.getContainerList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/01/list03")
    public Responses.PageResponse getSelectedContainerList(Pageable pageable, RequestParams<St00201VO> requestParams) {
        Page<St00201VO> pages = st002Service.getSelectedContainerList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping(value = "/02/save")
    @PostMapping
    public void updateClassList(@RequestBody List<St00201VO> requestParams) {
        st002Service.saveContainerList(requestParams);
    }
    @PutMapping("/02/confirm")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<St00201VO> requestParams) {
        ApiResponse apiResponse = st002Service.updateStatus(requestParams);
        if(apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }
}