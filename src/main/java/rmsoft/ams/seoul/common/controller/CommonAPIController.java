package rmsoft.ams.seoul.common.controller;

import io.onsemiro.core.context.AppContextManager;
import io.onsemiro.core.parameter.RequestParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad001.service.Ad001Service;
import rmsoft.ams.seoul.common.vo.CommonServiceVO;
import rmsoft.ams.seoul.config.constant.PackageNames;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Iterator;
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
        String serviceId = serviceParams.getServiceId();
        String serviceMethodName = serviceParams.getMethodName();

        Class proxyClass = null;
        Object controller = null; // Service
        Method method = null; // Service Method
        Parameter[] parameters = null;
        Constructor constructor = null;
        Object voInstance = null;
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

            return method.invoke(controller, inputParams);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @param serviceId
     * @param serviceGbn
     * @return
     */
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

    /**
     *
     * @param classInstance
     * @param methodName
     * @return
     */
    private Method getMethodByName(Object classInstance, String methodName) {
        Method[] methods = classInstance.getClass().getMethods();

        for (int i = 0; i < methods.length; i++) {
            if (methodName.equals(methods[i].getName())) {
                return methods[i];
            }
        }

        return null;
    }

    /**
     *
     * @param map
     * @param obj
     * @return
     */
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

    /**
     *
     * @param stringValue
     * @return
     */
    private String getFirstUppercaseStr(String stringValue) {
        if (StringUtils.isNotEmpty(stringValue)) {
            return stringValue.substring(0, 1).toUpperCase() + stringValue.substring(1, stringValue.length());
        }

        return stringValue;
    }
}
