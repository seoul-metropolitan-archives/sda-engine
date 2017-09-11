package io.onsemiro.core.domain.user;

import com.bgf.shbank.utils.ModelMapperUtils;
import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.code.Types;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.domain.user.auth.UserAuth;
import io.onsemiro.core.domain.user.auth.UserAuthService;
import io.onsemiro.core.domain.user.role.UserRole;
import io.onsemiro.core.domain.user.role.UserRoleService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;


@Service
public class UserService extends BaseService<User, String> {

    private UserRepository userRepository;

    @Inject
    private UserAuthService userAuthService;

    @Inject
    private UserRoleService userRoleService;

    @Inject
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final String INIT_PASSWORD = "1234";

    @Inject
    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public User findUser(String userCode) {
        return userRepository.findOne(userCode);
    }

    @Transactional
    public void saveUser(UserVO userVO) {

        User user = ModelMapperUtils.map(userVO, User.class);

        doSaveJob(user);
    }

    public void resetPassword(String userCode) {

        User user = findUser(userCode);
        user.setUserPs(bCryptPasswordEncoder.encode(INIT_PASSWORD));
        user.setUserStatus(Types.UserStatus.ACCOUNT_LOCK);

        userRepository.save(user);
    }

    public void updatePassword(String userCode, String userPs) {

        User user = findUser(userCode);
        user.setUserPs(bCryptPasswordEncoder.encode(userPs));
        user.setUserStatus(Types.UserStatus.NORMAL);

        userRepository.save(user);
    }

    private void doSaveJob(User user) {

        updateUserEntity(user);

        save(user);

        doSaveUserAuthAndRole(user);
    }

    private void updateUserEntity(User user) {

        User originalUser = findUser(user.getUserCd());

        if (originalUser == null) {

            user.setUserPs(bCryptPasswordEncoder.encode(INIT_PASSWORD));
            user.setUserStatus(Types.UserStatus.ACCOUNT_LOCK);

            UserRole userRole = new UserRole();
            userRole.setUserCd(user.getUserCd());
            userRole.setRoleCd("ASP_ACCESS");

            user.setRoleList(Arrays.asList(userRole));

        } else {
            delete(qUserRole).where(qUserRole.userCd.eq(user.getUserCd())).execute();
            delete(qUserAuth).where(qUserAuth.userCd.eq(user.getUserCd())).execute();

            user.setUserPs(originalUser.getUserPs());
            user.setCreatedAt(originalUser.getCreatedAt());
        }

    }

    private void doSaveUserAuthAndRole(User user) {

        for (UserAuth userAuth : user.getAuthList()) {
            userAuth.setUserCd(user.getUserCd());
        }

        for (UserRole userRole : user.getRoleList()) {
            userRole.setUserCd(user.getUserCd());
        }

        userAuthService.save(user.getAuthList());
        userRoleService.save(user.getRoleList());
    }

    public User getUser(RequestParams requestParams) {
        User user = get(requestParams).stream().findAny().orElse(null);

        if (user != null) {
            user.setAuthList(userAuthService.get(requestParams));
            user.setRoleList(userRoleService.get(requestParams));
        }

        return user;
    }

    public List<User> get(RequestParams requestParams) {
        String userCd = requestParams.getString("userCd");
        String jisaCode = requestParams.getString("jisaCode");
        String filter = requestParams.getString("filter");

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(userCd)) {
            builder.and(qUser.userCd.eq(userCd));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qUser.jisaCode.eq(jisaCode));
        }

        List<User> list = select().from(qUser).where(builder).orderBy(qUser.userNm.asc()).fetch();

        if (isNotEmpty(filter)) {
            list = filter(list, filter);
        }

        return list;
    }

    public Page<User> get(Pageable pageable, RequestParams requestParams) {
        return filter(get(requestParams), pageable);
    }

    /*@Transactional
    public void saveUser(List<User> users) {

        if (isNotEmpty(users)) {
            users.stream()
                    .map(user -> updateUserEntity(user))
                    .forEach(user -> {
                        try {
                            doSaveJob(user);
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    });
        }
    }*/

    /*@Transactional
    public void saveUser(List<User> users) throws Exception {
        if (isNotEmpty(users)) {
            for (User user : users) {
                delete(qUserRole).where(qUserRole.userCd.eq(user.getUserCd())).execute();
                delete(qUserAuth).where(qUserAuth.userCd.eq(user.getUserCd())).execute();

                String password = bCryptPasswordEncoder.encode(user.getUserPs());
                User originalUser = userRepository.findOne(user.getUserCd());

                if (originalUser != null) {
                    if (isNotEmpty(user.getUserPs())) {
                        user.setPasswordUpdateDate(Instant.now(Clock.systemUTC()));
                        user.setUserPs(password);
                    } else {
                        user.setUserPs(originalUser.getUserPs());
                    }
                } else {
                    user.setPasswordUpdateDate(Instant.now(Clock.systemUTC()));
                    user.setUserPs(password);
                }

                save(user);

                for (UserAuth userAuth : user.getAuthList()) {
                    userAuth.setUserCd(user.getUserCd());
                }

                for (UserRole userRole : user.getRoleList()) {
                    userRole.setUserCd(user.getUserCd());
                }

                userAuthService.save(user.getAuthList());
                userRoleService.save(user.getRoleList());
            }
        }
    }*/
}
