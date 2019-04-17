package rmsoft.ams.seoul.st.st029.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st028.service.St028Service;
import rmsoft.ams.seoul.st.st028.vo.St02801VO;
import rmsoft.ams.seoul.st.st029.service.St029Service;
import rmsoft.ams.seoul.st.st029.vo.St02901VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st029")
public class St029Controller extends BaseController {

    @Inject
    private St029Service st029Service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getStProgram(Pageable pageable, RequestParams<St02901VO> requestParams){
        Page<St02901VO> pages = st029Service.getStProgram(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(),pages);
    }

    @PutMapping(value = "/01/save01")
    @PostMapping
    public void saveProgram(@RequestBody List<St02901VO> requestParams){
        for(St02901VO vo : requestParams)
        {
            System.out.println(vo.getProgramUuid());
        }
        st029Service.saveProgram(requestParams);
    }

}

