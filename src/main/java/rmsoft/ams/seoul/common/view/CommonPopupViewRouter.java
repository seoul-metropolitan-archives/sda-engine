/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommonPopupViewRouter extends BaseController {
    @PostMapping("/common/common-popup")
    public String viewModal() {
        return "/common/common-popup";
    }
}

