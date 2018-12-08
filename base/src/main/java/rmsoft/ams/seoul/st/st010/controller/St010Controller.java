package rmsoft.ams.seoul.st.st010.controller;


import io.onsemiro.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.st.st010.service.St010Service;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/st/st009")
public class St010Controller extends BaseController {

    @Inject
    private St010Service st010Service;




}
