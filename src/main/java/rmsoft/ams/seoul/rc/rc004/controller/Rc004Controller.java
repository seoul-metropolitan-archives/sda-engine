package rmsoft.ams.seoul.rc.rc004.controller;

import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.rc.rc004.service.Rc004Service;
import rmsoft.ams.seoul.rc.rc004.vo.Rc00402VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;

import java.util.List;

/**
 * The type Rc 004 controller.
 */
@RestController
@RequestMapping("/api/v1/rc004/")
public class Rc004Controller extends MessageBaseController{

    @Autowired
    private Rc004Service rc004Service;

    /**
     * Save item details.
     *
     * @param requestParams the request params
     */
    @GetMapping("/01/saveItemDetails")
    public void saveItemDetails(RequestParams<Rc00501VO> requestParams){
       rc004Service.saveItemDetails(requestParams);
    }

    /**
     * Save component list.
     *
     * @param list the list
     */
    @PutMapping("/02/saveComponentList")
    @PostMapping
    public void saveComponentList(@RequestBody List<Rc00402VO> list){
        rc004Service.saveComponentList(list);
    }
}
