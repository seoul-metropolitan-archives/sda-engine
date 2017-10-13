package rmsoft.ams.seoul.ac.ac003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac003.service.Ac003Service;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00301VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00302VO;
import rmsoft.ams.seoul.common.domain.AcUser;

import java.util.List;

/**
 * Ac003Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017-10-12 오후 5:48
 **/
@RestController
@RequestMapping(value = "/api/v1/ac003/")
public class Ac003Controller extends BaseController {

    @Autowired
    private Ac003Service ac003Service;

    @GetMapping("/01/list")
    public Responses.PageResponse listUser(Pageable pageable, RequestParams<AcUser> requestParams) {
        Page<AcUser> pages = ac003Service.findUser(pageable, requestParams.getString("filter", ""));

        return Responses.PageResponse.of(Ac00301VO.of(pages.getContent()), pages);
    }

    @GetMapping("/02/list")
    public Responses.PageResponse listUserGroupUser(Pageable pageable, RequestParams<Ac00302VO> requestParams) {
        Page<Ac00302VO> pages = ac003Service.findUserGroupUser(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }


    @GetMapping("/01/details")
    public Ac00301VO details(RequestParams<Ac00301VO> requestParams) {
        return ac003Service.findOne(requestParams);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<AcUser> request) {
        ac003Service.save(request);
        return ok();
    }
}