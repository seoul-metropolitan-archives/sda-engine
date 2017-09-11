package com.bgf.shbank.controller.view.mng.equip.sh02001210;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh02001210ViewRouter extends BaseController {

    @GetMapping("/mng/equip/sh02001210")
    public String view() {
        return "/mng/equip/sh02001210";
    }
}

