package rmsoft.ams.seoul.common.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.context.AppContextManager;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.core.vo.BaseVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad001.service.Ad001Service;
import rmsoft.ams.seoul.ad.ad001.vo.Ad00101VO;
import rmsoft.ams.seoul.common.vo.CommonServiceVO;
import rmsoft.ams.seoul.config.constant.PackageNames;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * The type Common api controller.
 */
@RestController
@RequestMapping("/api/v1/common")
public class CommonAPIController extends MessageBaseController {

    @Autowired
    @Qualifier("AD001ServiceImpl")
    private Ad001Service service;

    /**
     * Controller invoker object.
     *
     * @param pageable      the pageable
     * @param serviceParams the service params
     * @param requestParams the request params
     * @return the object
     */
    @GetMapping("/controller")
    public Object controllerInvoker(Pageable pageable, CommonServiceVO serviceParams, RequestParams<Object> requestParams) {

        //Ad001Controller controller = (Ad001Controller)CommonBeanUtils.getBean("Ad001Controller");

        String serviceId = serviceParams.getServiceId();
        String serviceMethodName = serviceParams.getMethodName();

        Class proxyClass = null;
        Object controller = null; // Service
        Method method = null; // Service Method
        Parameter[] parameters = null;
        Constructor constructor = null;
        Object voInstance = null;
        Object methodParam = null;

        Object[] inputParams = null;

        try {
            proxyClass = Class.forName(getCombinedPackagePath(serviceId, "controller"));

            controller = AppContextManager.getBean(proxyClass);
            method = getMethodByName(controller, serviceMethodName);
            parameters = method.getParameters();

            inputParams = new Object[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].getType() == Pageable.class) {
                    inputParams[i] = pageable;
                } else if (parameters[i].getType() == RequestParams.class) {
                    inputParams[i] = requestParams;
                } else {
                    constructor = parameters[i].getType().getConstructor();
                    voInstance = constructor.newInstance();
                    inputParams[i] = convertMapToObject(requestParams.getMap(), voInstance);
                }
            }


            //constructor = parameterTypes[0].getConstructor(); // 파라미터가 여러개일수 있는 부분 처리해야할듯././.
            //voInstance = constructor.newInstance();
            //methodParam = convertMapToObject(params, voInstance);

            //Object methodParam = ModelMapperUtils.map(convertMapToObject(params, new Object()), parameterTypes[0]);

            return method.invoke(controller, inputParams);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Service invoker responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @GetMapping("/service")
    public Responses.ListResponse serviceInvoker(@RequestBody Map param) {

        //Ad001Controller controller = (Ad001Controller)CommonBeanUtils.getBean("Ad001Controller");

        String serviceId = (String) param.get("service");
        String serviceMethodName = (String) param.get("method");
        Map params = (HashMap) param.get("params");

        Class proxyClass = null;
        Object controller = null; // Service
        Method method = null; // Service Method

        List<T> resultList = null;

        try {
            proxyClass = Class.forName(getCombinedPackagePath(serviceId, "service"));

            controller = AppContextManager.getBean(proxyClass);
            method = getMethodByName(controller, serviceMethodName);

            Class[] parameterTypes = method.getParameterTypes();

            Constructor constructor = parameterTypes[0].getConstructor(); // 파라미터가 여러개일수 있는 부분 처리해야할듯././.
            Object voInstance = constructor.newInstance();
            Object methodParam = convertMapToObject(params, voInstance);

            //Object methodParam = ModelMapperUtils.map(convertMapToObject(params, new Object()), parameterTypes[0]);

            // 메소드 시그니쳐(선언)을 가져와서 Mapping해줘야하남??? -_-;
            resultList = (List) method.invoke(controller, methodParam);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Responses.ListResponse.of(resultList);
        //return Responses.ListResponse.of(service.getEnviromentList(null));
    }

    private String getCombinedPackagePath(String serviceId, String serviceGbn) {
        String combinedStr = "";

        if (serviceId.length() == 5) {
            combinedStr += PackageNames.BASE + ".";
            combinedStr += serviceId.substring(0, 2) + ".";
            combinedStr += serviceId + ".";

            combinedStr += serviceGbn.toLowerCase() + ".";
            combinedStr += getFirstUppercaseStr(serviceId) + getFirstUppercaseStr(serviceGbn);
        }

        return combinedStr;
    }

    private String getCombinedPackageVo(String serviceId, String voName) {
        String combinedStr = "";

        if (serviceId.length() == 5) {
            combinedStr += PackageNames.BASE + ".";
            combinedStr += serviceId.substring(0, 2) + ".";
            combinedStr += serviceId + ".";

            combinedStr += "vo." + voName;
        }

        return combinedStr;
    }

    private Method getMethodByName(Object classInstance, String methodName) {
        Method[] methods = classInstance.getClass().getMethods();

        for (int i = 0; i < methods.length; i++) {
            if (methodName.equals(methods[i].getName())) {
                return methods[i];
            }
        }

        return null;
    }

    private Object convertMapToObject(Map map, Object obj) {
        String keyAttribute = null;
        String setMethodString = "set";
        String methodString = null;
        Iterator itr = map.keySet().iterator();

        while (itr.hasNext()) {
            keyAttribute = (String) itr.next();
            methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methodString.equals(methods[i].getName())) {
                    try {
                        methods[i].invoke(obj, map.get(keyAttribute));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return obj;
    }

    private String getFirstUppercaseStr(String stringValue) {
        if (StringUtils.isNotEmpty(stringValue)) {
            return stringValue.substring(0, 1).toUpperCase() + stringValue.substring(1, stringValue.length());
        }

        return stringValue;
    }

    /**
     * Save api response.
     *
     * @param list the list
     * @return the api response
     */
    @RequestMapping("/save")
    public ApiResponse save(@RequestBody List<Ad00101VO> list) {
        ApiResponse apiResponse = service.save(list);
        return apiResponse;
    }

}
