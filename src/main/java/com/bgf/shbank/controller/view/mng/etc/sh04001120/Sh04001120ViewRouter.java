package com.bgf.shbank.controller.view.mng.etc.sh04001120;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh04001120ViewRouter extends BaseController {

    @GetMapping("/mng/etc/sh04001120")
    public String view() {
        return "/mng/etc/sh04001120";
    }
}

