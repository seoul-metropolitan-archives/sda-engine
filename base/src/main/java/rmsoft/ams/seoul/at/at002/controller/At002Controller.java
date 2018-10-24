package rmsoft.ams.seoul.at.at002.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.at.at001.vo.At00101VO;
import rmsoft.ams.seoul.at.at001.vo.At001VO;
import rmsoft.ams.seoul.at.at002.service.At002Service;

/**
 * The type Cl 003 controller.
 */
@RestController
@RequestMapping("/api/v1/at/at002")
public class At002Controller extends BaseController {

    @Autowired
    private At002Service at002Service;

}
