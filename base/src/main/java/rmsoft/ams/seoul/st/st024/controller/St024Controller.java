package rmsoft.ams.seoul.st.st024.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.st.st023.vo.St02301VO;
import rmsoft.ams.seoul.st.st024.service.St024Service;
import rmsoft.ams.seoul.st.st024.vo.St02401VO;
import rmsoft.ams.seoul.st.st024.vo.St02402VO;
import rmsoft.ams.seoul.st.st024.vo.St02403VO;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/st/st024")
public class St024Controller  extends BaseController {

    @Inject
    private St024Service st024Service;


    @GetMapping("/01/list01")
    public Responses.PageResponse getTagList(Pageable pageable, RequestParams<St02401VO> requestParams){
        Page<St02401VO> pages = st024Service.getTagList(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(),pages);
    }

    @GetMapping("/01/list02")
    public Responses.PageResponse getTagList02(Pageable pageable, RequestParams<St02402VO> requestParams){
        Page<St02402VO> pages = st024Service.getTagList02(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(),pages);
    }
    @GetMapping("/01/list03")
    public Responses.PageResponse getTagList03(Pageable pageable, RequestParams<St02403VO> requestParams){
        Page<St02403VO> pages = st024Service.getTagList03(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(),pages);
    }
}
