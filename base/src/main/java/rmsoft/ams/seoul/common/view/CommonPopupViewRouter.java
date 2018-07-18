/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.view;

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * The type Common popup view router.
 */
@Controller
public class CommonPopupViewRouter extends BaseController {
    /**
     * View modal string.
     *
     * @param model the model
     * @return the string
     */
    @PostMapping("/common/common-popup")
    public String viewModal(ModelMap model) {
        return "/common/common-popup";
    }
}

