package com.bgf.shbank.controller;

import io.onsemiro.core.code.Types;
import io.onsemiro.core.domain.user.SessionUser;
import io.onsemiro.utils.PhaseUtils;
import io.onsemiro.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by james on 2016-12-28.
 */
@Slf4j
@Controller
public class MainController {

    @Value("${onsemiro.app-name}")
    private String appName;

    private String commonCodeJson;

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

        return SessionUtils.isLoggedIn() ? "redirect:/" + appName : "/login";
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
        //if (StringUtils.isEmpty(commonCodeJson)) {
        //    commonCodeJson = CommonCodeUtils.getAllByJson();
        //}

        model.addAttribute("commonCodeJson", commonCodeJson);
        model.addAttribute("isDevelopmentMode", PhaseUtils.isDevelopmentMode());
        model.addAttribute("axbody_class", "frame-set");

        return "/main";
    }

}
