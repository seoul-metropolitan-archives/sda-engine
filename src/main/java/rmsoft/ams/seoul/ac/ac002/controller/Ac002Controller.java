package rmsoft.ams.seoul.ac.ac002.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ac.ac002.service.Ac002Service;
import rmsoft.ams.seoul.ac.ac002.vo.Ac002VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

/**
 * Ac003Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-12 오후 5:48
 */
@RestController
@RequestMapping(value = "/api/v1/ac002/")
public class Ac002Controller extends MessageBaseController {

    @Autowired
    private Ac002Service ac003Service;

    /**
     * Gets startup program.
     *
     * @param param the param
     * @return the startup program
     */
    @GetMapping("/01/getStartupProgram")
    @ResponseBody
    public Ac002VO getStartupProgram(Ac002VO param) {
        return ac003Service.getStartupProgram(param);
    }

}