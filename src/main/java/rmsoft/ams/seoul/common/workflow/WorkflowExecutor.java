/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.workflow;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00302VO;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class WorkflowExecutor extends ClassLoader {
    @Value("${native.library.path}")
    private String nativeLibrary;

    private Map<String, Object> parameterMap = null;

    public WorkflowResponse invokeJobProcess(Wf00302VO wf00302VO) {
        WorkflowResponse callResult = new WorkflowResponse();

        try {

            File[] jarFiles = new File(nativeLibrary).listFiles(
                    (dir, name) -> {
                        return name.toLowerCase().endsWith(".jar");
                    }
            );

            // Create a new JavaClassLoader
            URL[] classLoaderUrls = new URL[jarFiles.length];

            for (int i = 0; i < jarFiles.length; i++) {
                classLoaderUrls[i] = new URL("file:///" + jarFiles[i].getPath());
            }

            URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
            Class loadedMyClass = urlClassLoader.loadClass(wf00302VO.getApi());

            System.out.println("Loaded class name: " + loadedMyClass.getName());

            Constructor constructor = loadedMyClass.getConstructor();
            Object myClassObject = constructor.newInstance();

            // 파라미터 추출
            parameterMap = getParameterMap(wf00302VO);

            // 키 추출
            List<String> keys = new ArrayList(parameterMap.keySet());
            Method method = null;

            for (int j = 0; j < keys.size(); j++) {
                Object parameterObject = parameterMap.get(keys.get(j));

                if (parameterObject instanceof String) {
                    if (parameterObject.equals("true") || parameterObject.equals("false")) {
                        parameterObject = Boolean.parseBoolean(parameterObject.toString());
                        method = loadedMyClass.getMethod("set" + makeMethodName(keys.get(j)), new Class[]{Boolean.class});
                    } else if (isNumber(parameterObject.toString())) {
                        parameterObject = Integer.parseInt(parameterObject.toString());
                        method = loadedMyClass.getMethod("set" + makeMethodName(keys.get(j)), new Class[]{Integer.class});
                    } else {
                        method = loadedMyClass.getMethod("set" + makeMethodName(keys.get(j)), new Class[]{String.class});
                    }
                } else if (parameterObject instanceof Integer) {
                    method = loadedMyClass.getMethod("set" + makeMethodName(keys.get(j)), new Class[]{Integer.class});
                } else if (parameterObject instanceof Boolean) {
                    method = loadedMyClass.getMethod("set" + makeMethodName(keys.get(j)), new Class[]{Boolean.class});
                } else if (parameterObject instanceof List) {
                    method = loadedMyClass.getMethod("set" + makeMethodName(keys.get(j)), new Class[]{List.class});
                }

                method.invoke(myClassObject, parameterObject);
                System.out.println("Invoked method name: " + method.getName());
            }

            Method runMethod = loadedMyClass.getMethod("runProcess");
            System.out.println("Invoked Run Process method");

            Object resultObject = runMethod.invoke(myClassObject);
            callResult = (WorkflowResponse) resultObject;

        } catch (ClassNotFoundException e) {
            errorLogging(e);

            log.error("Workflow Process Executor Error", e);
            callResult.setSuccess(false);
            callResult.setMessage(e.getMessage());
        } catch (Exception e) {
            errorLogging(e);

            log.error("Workflow Process Service Error", e);
            callResult.setMessage(e.getMessage());
        }

        return callResult;
    }

    private Map<String, Object> getParameterMap(Wf00302VO wf00302VO) {
        Map<String, Object> parameterMap = new HashMap<>();

        // Parameter 결과 등록
        if (wf00302VO.getParameterList() != null && wf00302VO.getParameterList().size() > 0) {
            wf00302VO.getParameterList().forEach(wfParameter -> {
                // Parameter Value 셋팅
                parameterMap.put(wfParameter.getParameterName(), wfParameter.getDefaultValue());
            });
        }

        return parameterMap;
    }

    public String makeMethodName(String methodName) {
        String tmpName = "";

        tmpName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1, methodName.length());

        return tmpName;
    }

    public boolean isNumber(String str) {
        boolean result = false;
        try {
            Double.parseDouble(str);
            result = true;
        } catch (Exception e) {

        }
        return result;
    }

    protected void errorLogging(Throwable throwable) {

        if (log.isErrorEnabled()) {
            Throwable rootCause = ExceptionUtils.getRootCause(throwable);

            if (rootCause != null) {
                throwable = rootCause;
            }

            if (throwable.getMessage() != null) {
                log.error(throwable.getMessage(), throwable);
            } else {
                log.error("ERROR", throwable);
            }
        }
    }
}
