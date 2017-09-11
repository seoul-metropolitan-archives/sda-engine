package com.bgf.shbank.controller.view.mng.etc.sh04001190;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh04001190ViewRouter extends BaseController {

    @GetMapping("/mng/etc/sh04001190")
    public String view() {
        return "/mng/etc/sh04001190";
    }
}

