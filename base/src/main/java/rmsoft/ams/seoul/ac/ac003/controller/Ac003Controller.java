package rmsoft.ams.seoul.ac.ac003.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac003.service.Ac003Service;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00301VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00302VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00303VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.List;

/**
 * Ac003Controller
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-12 오후 5:48
 */
@RestController
@RequestMapping(value = "/api/v1/ac003/")
public class Ac003Controller extends MessageBaseController {

    @Autowired
    private Ac003Service ac003Service;

    /**
     * Find all user responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/01/list")
    public Responses.PageResponse findAllUser(Pageable pageable, RequestParams<Ac00301VO> requestParams) {
        Page<Ac00301VO> pages = ac003Service.findAllUser(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Find user group user responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/02/list")
    public Responses.PageResponse findUserGroupUser(Pageable pageable, RequestParams<Ac00302VO> requestParams) {
        Page<Ac00302VO> pages = ac003Service.findUserGroupUser(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Find user role responses . page response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the responses . page response
     */
    @GetMapping("/03/list")
    public Responses.PageResponse findUserRole(Pageable pageable, RequestParams<Ac00303VO> requestParams) {
        Page<Ac00303VO> pages = ac003Service.findUserRole(pageable, requestParams);

        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Save user api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/01/save")
    @PostMapping
    public ApiResponse saveUser(@RequestBody List<Ac00301VO> requestParams) {
        return ac003Service.saveUser(requestParams);
    }

    /**
     * Save user group api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/02/save")
    @PostMapping
    public ApiResponse saveUserGroup(@RequestBody List<Ac00302VO> requestParams) {
        return ac003Service.saveUserGroup(requestParams);
    }

    /**
     * Save user role api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/03/save")
    @PostMapping
    public ApiResponse saveUserRole(@RequestBody List<Ac00303VO> requestParams) {
        return ac003Service.saveUserRole(requestParams);
    }

    /**
     * Change password api response.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the api response
     */
    @GetMapping(value = "/04/save")
    @PostMapping
    public ApiResponse changePassword(Pageable pageable, RequestParams<Ac00301VO> requestParams) {
        return ac003Service.savePassword(requestParams);
    }
}