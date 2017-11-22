package rmsoft.ams.seoul.rc.rc004.controller;

import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.rc.rc004.service.Rc004Service;
import rmsoft.ams.seoul.rc.rc004.vo.Rc00402VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rc004/")
public class Rc004Controller extends MessageBaseController{

    @Autowired
    private Rc004Service rc004Service;

    @GetMapping("/01/saveItemDetails")
    public void saveItemDetails(RequestParams<Rc00501VO> requestParams){
       rc004Service.saveItemDetails(requestParams);
    }

    @PutMapping("/02/saveComponentList")
    @PostMapping
    public void saveComponentList(@RequestBody List<Rc00402VO> list){
        rc004Service.saveComponentList(list);
    }
}
