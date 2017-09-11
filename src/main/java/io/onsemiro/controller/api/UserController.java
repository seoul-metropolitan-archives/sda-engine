package io.onsemiro.controller.api;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.domain.user.User;
import io.onsemiro.core.domain.user.UserService;
import io.onsemiro.core.domain.user.UserVO;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends BaseController {

    @Inject
    private UserService userService;

    @Inject
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<User> requestParams) {
        Page<User> page = userService.get(pageable, requestParams);
        return Responses.PageResponse.of(UserVO.of(page.getContent()), page);
    }

    /*@GetMapping
    public Responses.ListResponse list(RequestParams<User> requestParams) {
        List<User> users = userService.get(requestParams);
        return Responses.ListResponse.of(users);
    }*/

    @GetMapping(params = "userCd")
    public User get(RequestParams requestParams) {
        return userService.getUser(requestParams);
    }

    @GetMapping(params = "resetPassword")
    public ApiResponse resetPassword(String userCode) {
        userService.resetPassword(userCode);
        return ok();
    }

    @GetMapping(params = "updatePassword")
    public ApiResponse updatePassword(String userCode, String userPs) {
        userService.updatePassword(userCode, userPs);
        return ok();
    }

    @PutMapping
    public ApiResponse save(@Valid @RequestBody UserVO user) throws Exception {
        userService.saveUser(user);
        return ok();
    }

    /*@PutMapping
    public ApiResponse save(@Valid @RequestBody List<User> users) throws Exception {
        userService.saveUser(users);
        return ok();
    }*/
}
