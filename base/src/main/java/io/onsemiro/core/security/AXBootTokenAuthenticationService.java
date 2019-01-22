package io.onsemiro.core.security;

import io.onsemiro.core.code.AXBootTypes;
import io.onsemiro.core.code.Constants;
import io.onsemiro.core.domain.program.Program;
import io.onsemiro.core.domain.program.ProgramService;
import io.onsemiro.core.domain.program.menu.Menu;
import io.onsemiro.core.domain.program.menu.MenuService;
import io.onsemiro.core.domain.user.SessionUser;
import io.onsemiro.core.domain.user.UserGroupUser;
import io.onsemiro.core.domain.user.UserGroupUserService;
import io.onsemiro.core.domain.user.UserService;
import io.onsemiro.core.domain.user.auth.AccessControl;
import io.onsemiro.core.domain.user.auth.AccessControlService;
import io.onsemiro.core.domain.user.auth.Permission;
import io.onsemiro.core.domain.user.role.Role;
import io.onsemiro.core.domain.user.role.RoleService;
import io.onsemiro.core.vo.ScriptSessionVO;
import io.onsemiro.utils.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AXBootTokenAuthenticationService {

    @Value("${onsemiro.app-name}")
    private String appName;

    private final JWTSessionHandler jwtSessionHandler;

    @Inject
    private ProgramService programService;

   /* @Inject
    private AuthGroupMenuService authGroupMenuService;*/

    @Inject
    private MenuService menuService;

    @Inject
    private UserService userService;

    @Inject
    private UserGroupUserService userGroupUserService;

    @Inject
    private AccessControlService accessControlService;

    @Inject
    private RoleService roleService;

    public AXBootTokenAuthenticationService() {
        jwtSessionHandler = new JWTSessionHandler(DatatypeConverter.parseBase64Binary("YXhib290"));
    }

    public int tokenExpiry() {
        if (PhaseUtils.isProduction()) {
            return 60 * 50;
        } else {
            return 60 * 10 * 10 * 10 * 10;
        }
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
        final String menuId = requestUtils.getString("menuId");
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

        // 마지막 로그인 셋팅 시간
            /*if (StringUtils.equals(programCode, appName)) {
                setLastLoginTimeToSessionUser(user);
                updateLastLoginDate(user);
            }*/

        if (!requestUri.startsWith(ContextUtils.getBaseApiPath())) {
            if (StringUtils.isNotEmpty(menuId)) {
                // 해당 메뉴 정보를 조회한다.
                Menu menu = menuService.findMenu(menuId);

                if (menu != null) {
                    Program program = menu.getProgram();

                    if (program != null) {
                        requestUtils.setAttribute("program", program);
                        requestUtils.setAttribute("programUuid", program.getProgramUuid());
                        requestUtils.setAttribute("pageName", menu.getMenuNm());
                    }
                }

            }

            ScriptSessionVO scriptSessionVO = ModelMapperUtils.map(user, ScriptSessionVO.class);
            scriptSessionVO.setDateFormat(scriptSessionVO.getDateFormat().toUpperCase());
            requestUtils.setAttribute("loginUser", user);
            requestUtils.setAttribute("scriptSession", JsonUtils.toJson(scriptSessionVO));

            if (programCode.equals("main") || programCode.equals(appName)) {
                // 사용자 정보에 사용자그룹사용자 정보 셋팅
                List<UserGroupUser> userGroupUserList = userGroupUserService.findUserGroupUser(user.getUserUuid());
                List<AccessControl> accessControlList = new ArrayList<>(); // 접근제어

                // 사용자 접근권한정보 셋팅
                if (StringUtils.isNotEmpty(user.getUserUuid())) {
                    accessControlList.addAll(accessControlService.findRoleByUserUuid(user.getUserUuid()));
                }

                // 사용자 그룹 접근권한정보 셋팅
                if (userGroupUserList.size() > 0) {
                    userGroupUserList.forEach(userGroupUser -> {
                        accessControlList.addAll(accessControlService.findRoleByUserGroupUuid(userGroupUser.getUserGroupUuid()));
                    });
                }

                // 사용자/그룹을 포함한 모든 Role목록
                List<Role> allRoleList = new ArrayList<>();

                accessControlList.forEach(accessControl -> {
                    allRoleList.addAll(roleService.findByRoleUuid(accessControl.getRoleUuid()));
                });

                // 중복 Role 제거
                List<Role> userRoleList = allRoleList.stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Role::getRoleUuid)))).stream().collect(Collectors.toList());


                List<Menu> allMenuList = new ArrayList<>();
                List<Permission> allPermissionList = new ArrayList<>();
                // Role 에 포함된 메뉴,퍼미션 추출
                userRoleList.forEach(role -> {
                    role.getRoleMenuList().stream().forEach(roleMenu -> {

                        if (roleMenu.getMenu() != null && roleMenu.getMenu().getUseYn().equals(AXBootTypes.Used.YES)) {
                            Menu menu = roleMenu.getMenu();
                            menu.setSaveYn(roleMenu.getSaveYn());
                            menu.setInquiryYn(roleMenu.getInquiryYn());
                            allMenuList.add(menu);
                        }
                    });
                    role.getRolePermissionList().stream().forEach(rolePermission -> {
                        if (rolePermission.getUseYn().equals(AXBootTypes.Used.YES)) {
                            allPermissionList.add(rolePermission.getPermission());
                        }
                    });
                });

                // Menu 중복제거
                List<Menu> menuList = allMenuList.stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Menu::getMenuUuid)))).stream().collect(Collectors.toList());
                Collections.sort(menuList, new Comparator<Menu>() {
                    @Override
                    public int compare(Menu o1, Menu o2) {
                        if (o1.getMenuCode().compareTo(o2.getMenuCode()) == 0) {
                            if (o1.getUseYn().equals(AXBootTypes.Used.NO) && o2.getUseYn().equals(AXBootTypes.Used.YES)) {
                                o1.setUseYn(AXBootTypes.Used.YES);
                            }
                            if (o1.getInquiryYn().equals(AXBootTypes.Used.NO) && o2.getInquiryYn().equals(AXBootTypes.Used.YES)) {
                                o1.setInquiryYn(AXBootTypes.Used.YES);
                            }
                            if (o1.getSaveYn().equals(AXBootTypes.Used.NO) && o2.getSaveYn().equals(AXBootTypes.Used.YES)) {
                                o1.setSaveYn(AXBootTypes.Used.YES);
                            }
                        }

                        return o1.getMenuCode().compareTo(o2.getMenuCode());
                    }
                });

                // 퍼미션 중복제거거
                List<Permission> permissionList = allPermissionList.stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Permission::getPermissionUuid)))).stream().collect(Collectors.toList());

                List<Menu> menuList1 = menuService.getAuthorizedMenuList(menuList);

                requestUtils.setAttribute("menuJson", JsonUtils.toJson(menuList1));
                requestUtils.setAttribute("permissionJson", JsonUtils.toJson(permissionList));
            }
        }

        setUserEnvironments(user, response);

        return new AXBootUserAuthentication(user);
    }

/*    private void setLastLoginTimeToSessionUser(SessionUser sessionUser) {

        User user = userService.findUser(sessionUser.getUserId());

        if (user.getLastLoginDate() != null) {
            LocalDateTime dateTimeInstant = LocalDateTime.ofInstant(user.getLastLoginDate(), ZoneId.systemDefault());
            String lastLoginDate = DateUtils.convertToString(dateTimeInstant, "yyyy-MM-dd HH:mm");

            sessionUser.setLastLoginDate(lastLoginDate);
        }

    }*/

  /*  private void updateLastLoginDate(SessionUser sessionUser) {

        User user = new User();
        user.setUserUuid(sessionUser.getUserUuid());
        user.setUserId(sessionUser.getUserId());
        user.setUserName(sessionUser.getUsername());
        user.setUserPassword(sessionUser.getPassword());
        //user.set(sessionUser.getMenuGrpCd());
        user.setLastLoginDate(Instant.now(Clock.systemUTC()));

        userService.save(user);
    }*/

    private Authentication deleteCookieAndReturnNullAuthentication(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, Constants.ADMIN_AUTH_TOKEN_KEY);
        ScriptSessionVO scriptSessionVO = ScriptSessionVO.noLoginSession();
        request.setAttribute("scriptSession", JsonUtils.toJson(scriptSessionVO));
        return null;
    }
}
