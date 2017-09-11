package com.bgf.shbank.controller.view.mng.equip.sh05001140;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sh05001140ViewRouter extends BaseController {

    @GetMapping("/mng/equip/sh05001140")
    public String view() {
        return "/mng/equip/sh05001140";
    }
}

