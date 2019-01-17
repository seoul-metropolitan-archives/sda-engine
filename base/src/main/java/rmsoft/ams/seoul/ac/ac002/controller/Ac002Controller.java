package rmsoft.ams.seoul.ac.ac002.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac002.service.Ac002Service;
import rmsoft.ams.seoul.ac.ac002.vo.Ac00201VO;
import rmsoft.ams.seoul.ac.ac002.vo.Ac002VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.List;

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
    private Ac002Service ac002Service;

    /**
     * Gets startup program.
     *
     * @param param the param
     * @return the startup program
     */
    @GetMapping("/01/getStartupProgram")
    @ResponseBody
    public Ac002VO getStartupProgram(Ac002VO param) {
        return ac002Service.getStartupProgram(param);
    }

    /**
     * Save api response.
     *
     * @param data the data
     * @return the api response
     */
    @PutMapping("/01/addBookmark")
    public List<Ac00201VO> addBookmark(@RequestBody Ac00201VO data)
    {
        return ac002Service.addBookMark(data);
    }

    /**
     * Save api response.
     *
     * @return the api response
     */
    @GetMapping("/01/getBookmark")
    public List<Ac00201VO> getBookmark()
    {
        return ac002Service.getBookMark();
    }
}