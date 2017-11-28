package rmsoft.ams.seoul.ac.ac002.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac002.service.Ac002Service;
import rmsoft.ams.seoul.ac.ac002.vo.Ac002VO;
import rmsoft.ams.seoul.ac.ac003.service.Ac003Service;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00301VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00302VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00303VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.HashMap;
import java.util.List;

/**
 * Ac003Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017-10-12 오후 5:48
 **/
@RestController
@RequestMapping(value = "/api/v1/ac002/")
public class Ac002Controller extends MessageBaseController {

    @Autowired
    private Ac002Service ac003Service;

    @GetMapping("/01/getStartupProgram")
    @ResponseBody
    public Ac002VO getStartupProgram(Ac002VO param) {
        return ac003Service.getStartupProgram(param);
    }

}