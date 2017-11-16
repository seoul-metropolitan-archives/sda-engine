/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.controller;

import io.onsemiro.core.domain.user.SessionUser;
import io.onsemiro.utils.PhaseUtils;
import io.onsemiro.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import rmsoft.ams.seoul.utils.CommonCodeUtils;
import rmsoft.ams.seoul.utils.CommonConfigUtils;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

/**
 * Created by james on 2016-12-28.
 */
@Slf4j
@Controller
public class MainController {

    @Value("${onsemiro.app-name}")
    private String appName;

    private String commonCodeJson;
    private String commonMessageJson;
    private String commonConfigJson;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(ModelMap model) {

        SessionUser sessionUser = SessionUtils.getCurrentUser();

       /* if (sessionUser != null) {
            if (sessionUser.getUserStatus() == Types.UserStatus.ACCOUNT_LOCK) {
                model.addAttribute("userId", sessionUser.getUserCd());
                return "/change-pwd";
            }
        }*/

        return SessionUtils.isLoggedIn() ? "redirect:/" + appName : "/ac/ac001/ac001";
    }

    @GetMapping("/main")
    public String main() {
        return "redirect:/" + appName;
    }

    @GetMapping("/${onsemiro.app-name}")
    public String app(Model model) {

        SessionUser sessionUser = SessionUtils.getCurrentUser();

       /* if (sessionUser.getUserStatus() == Types.UserStatus.ACCOUNT_LOCK) {
            model.addAttribute("userId", sessionUser.getUserCd());
            return "/change-pwd";
        }*/

        // 무조건 공통코드를 재조회 한다.

        commonCodeJson = CommonCodeUtils.getAllByJson();
        commonMessageJson = CommonMessageUtils.getAllMessageByJson();
        commonConfigJson = CommonConfigUtils.getAllConfigByJson();

        model.addAttribute("commonCodeJson", commonCodeJson);
        model.addAttribute("commonMessageJson", commonMessageJson);
        model.addAttribute("commonConfigJson", commonConfigJson);
        model.addAttribute("isDevelopmentMode", PhaseUtils.isDevelopmentMode());
        model.addAttribute("axbody_class", "frame-set");

        return "/main";
    }

}
