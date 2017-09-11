package com.bgf.shbank.controller.view.mng.error.error_handle_mng;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorHandleMngViewRouter extends BaseController {

    @GetMapping("/mng/error/error_handle_mng")
    public String view() {
        return "/mng/error/error_handle_mng";
    }
}

