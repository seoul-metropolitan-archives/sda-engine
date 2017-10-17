package rmsoft.ams.seoul.ac.ac003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.AXBootTypes;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac003.service.Ac003Service;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00301VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00302VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00303VO;
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
    public Responses.PageResponse findAllUser(Pageable pageable, RequestParams<Ac00301VO> requestParams) {
        Page<Ac00301VO> pages = ac003Service.findAllUser(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/01/details")
    public Ac00301VO details(RequestParams<Ac00301VO> requestParams) {
        return ac003Service.findOne(requestParams);
    }

    @GetMapping("/02/list")
    public Responses.PageResponse findUserGroupUser(Pageable pageable, RequestParams<Ac00302VO> requestParams) {
        Page<Ac00302VO> pages = ac003Service.findUserGroupUser(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @GetMapping("/03/list")
    public Responses.PageResponse findUserRole(Pageable pageable, RequestParams<Ac00303VO> requestParams) {
        Page<Ac00303VO> pages = ac003Service.findUserRole(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    @PutMapping(value = "/01/save")
    @PostMapping
    public ApiResponse saveUser(@RequestBody List<Ac00301VO> requestParams) {

        for (Ac00301VO ac00301VO : requestParams) {
            AcUser acUser = ModelMapperUtils.map(ac00301VO, AcUser.class);

            //TODO 임시 코드
            if(ac00301VO.getUseYn().equals("false")){
                acUser.setUseYn(AXBootTypes.Used.NO);
            }else{
                acUser.setUseYn(AXBootTypes.Used.YES);
            }

            if (acUser.getPasswordUpdateDate() == null) {
                acUser.setPasswordUpdateDate(DateUtils.getTimestampNow());
            }

            if (acUser.getInsertDate() == null) {
                acUser.setInsertDate(DateUtils.getTimestampNow());
            }


            if (ac00301VO.isDeleted()) {
                ac003Service.deleteUser(acUser);
            } else if (ac00301VO.isModified()) {
                ac003Service.saveUser(acUser);
            } else {
                // Created
                // TODO Insert Validation 필요
                acUser.setUserUuid(UUIDUtils.getUUID());
                ac003Service.saveUser(acUser);

            }
        }
        return ok();
    }
}