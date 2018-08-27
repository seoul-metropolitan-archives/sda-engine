package rmsoft.ams.seoul.ig.ig002.controller;

import io.onsemiro.controller.BaseController;

import io.onsemiro.core.api.response.ApiResponse;

import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ig.ig002.service.Ig002Service;
import rmsoft.ams.seoul.ig.ig002.vo.Ig00201VO;
import rmsoft.ams.seoul.ig.ig002.vo.Ig002VO;


@RestController
@RequestMapping(value = "/api/v1/ig/ig002")
public class Ig002Controller extends BaseController {

    @Autowired
    private Ig002Service ig002Service;

    @GetMapping("/01/list01")
    public Ig00201VO getIgAccessionNo() {
        return ig002Service.getIgAccessionNo();
    }
    @GetMapping("/01/list02")
    public Ig002VO getIgAccessionRecord(RequestParams<Ig00201VO> requestParams) {
        return ig002Service.getIgAccessionRecord(requestParams);
    }
    @RequestMapping("/02/save01")
    public ApiResponse save(@RequestBody Ig002VO data)
    {
        return ig002Service.save(data);
    }
}