package com.bgf.shbank.controller.view.mng.common;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CornerCodeViewRouter extends BaseController {
    @PostMapping("/mng/common/search-corner-modal")
    public String viewModal() {
        return "/mng/common/search-corner-modal";
    }
}

