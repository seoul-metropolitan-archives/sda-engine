package io.onsemiro.core.security;

import io.onsemiro.core.code.AXBootTypes;
import io.onsemiro.core.code.Constants;
import io.onsemiro.core.domain.program.Program;
import io.onsemiro.core.domain.program.ProgramService;
import io.onsemiro.core.domain.program.menu.Menu;
import io.onsemiro.core.domain.program.menu.MenuService;
import io.onsemiro.core.domain.user.SessionUser;
import io.onsemiro.core.domain.user.User;
import io.onsemiro.core.domain.user.UserService;
import io.onsemiro.core.domain.user.auth.menu.AuthGroupMenu;
import io.onsemiro.core.domain.user.auth.menu.AuthGroupMenuService;
import io.onsemiro.core.vo.ScriptSessionVO;
import io.onsemiro.utils.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class AXBootTokenAuthenticationService {

    @Value("${onsemiro.app-name}")
    private String appName;

    private final JWTSessionHandler jwtSessionHandler;

    @Inject
    private ProgramService programService;

    @Inject
    private AuthGroupMenuService authGroupMenuService;

    @Inject
    private MenuService menuService;

    @Inject
    private UserService userService;

    public AXBootTokenAuthenticationService() {
        jwtSessionHandler = new JWTSessionHandler(DatatypeConverter.parseBase64Binary("YXhib290"));
    }

    public int tokenExpiry() {
        return 60 * 60 * 8; // 60초(1분) * 60분(1시간) * 8시간 => 8시간
    }

    public void addAuthentication(HttpServletResponse response, AXBootUserAuthentication authentication) throws IOException {
        final SessionUser user = authentication.getDetails();
        setUserEnvironments(user, response);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void setUserEnvironments(SessionUser user, HttpServletResponse response) throws IOException {
        String token = jwtSessionHandler.createTokenForUser(user);
        CookieUtils.addCookie(response, Constants.ADMIN_AUTH_TOKEN_KEY, token, tokenExpiry());
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestUtils requestUtils = RequestUtils.of(request);
        final String token = CookieUtils.getCookieValue(request, Constants.ADMIN_AUTH_TOKEN_KEY);
        final String programCode = FilenameUtils.getBaseName(request.getServletPath());
        final Long menuId = requestUtils.getLong("menuId");
        final String requestUri = request.getRequestURI();
        final String language = requestUtils.getString(Constants.LANGUAGE_PARAMETER_KEY, "");

        if (StringUtils.isNotEmpty(language)) {
            CookieUtils.addCookie(response, Constants.LANGUAGE_COOKIE_KEY, language);
        }

        if (token == null) {
            return deleteCookieAndReturnNullAuthentication(request, response);
        }

        SessionUser user = jwtSessionHandler.parseUserFromToken(token);

        if (user == null) {
            return deleteCookieAndReturnNullAuthentication(request, response);
        }

        if (!requestUri.startsWith(ContextUtils.getBaseApiPath())) {
            if(menuId > 0) {
                Menu menu = menuService.findOne(menuId);

                if (menu != null) {
                    Program program = menu.getProgram();

                    if (program != null) {
                        requestUtils.setAttribute("program", program);
                        requestUtils.setAttribute("pageName", menu.getMenuNm());
                        requestUtils.setAttribute("pageRemark", program.getRemark());

                        if (program.getAuthCheck().equals(AXBootTypes.Used.YES.getLabel())) {
                            AuthGroupMenu authGroupMenu = authGroupMenuService.getCurrentAuthGroupMenu(menuId, user);
                            if (authGroupMenu == null) {
                                throw new AccessDeniedException("Access is denied");
                            }
                            requestUtils.setAttribute("authGroupMenu", authGroupMenu);
                        }
                    }
                }
            }

            if (StringUtils.equals(programCode, appName)) {
                setLastLoginTimeToSessionUser(user);
                updateLastLoginDate(user);
            }

            ScriptSessionVO scriptSessionVO = ModelMapperUtils.map(user, ScriptSessionVO.class);
            scriptSessionVO.setDateFormat(scriptSessionVO.getDateFormat().toUpperCase());
            requestUtils.setAttribute("loginUser", user);
            requestUtils.setAttribute("scriptSession", JsonUtils.toJson(scriptSessionVO));

            if (programCode.equals("main") || programCode.equals(appName)) {
                List<Menu> menuList = menuService.getAuthorizedMenuList(user.getMenuGrpCd(), user.getAuthGroupList());
                requestUtils.setAttribute("menuJson", JsonUtils.toJson(menuList));
            }
        }

        setUserEnvironments(user, response);

        return new AXBootUserAuthentication(user);
    }

    private void setLastLoginTimeToSessionUser(SessionUser sessionUser) {

        User user = userService.findUser(sessionUser.getUserCd());

        LocalDateTime dateTimeInstant = LocalDateTime.ofInstant(user.getLastLoginDate(), ZoneId.systemDefault());
        String lastLoginDate = DateUtils.convertToString(dateTimeInstant, "yyyy-MM-dd HH:mm");

        sessionUser.setLastLoginDate(lastLoginDate);
    }

    private void updateLastLoginDate(SessionUser sessionUser) {

        User user = userService.findUser(sessionUser.getUserCd());
        user.setLastLoginDate(Instant.now(Clock.systemUTC()));

        userService.save(user);
    }

    private Authentication deleteCookieAndReturnNullAuthentication(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, Constants.ADMIN_AUTH_TOKEN_KEY);
        ScriptSessionVO scriptSessionVO = ScriptSessionVO.noLoginSession();
        request.setAttribute("scriptSession", JsonUtils.toJson(scriptSessionVO));
        return null;
    }
}
