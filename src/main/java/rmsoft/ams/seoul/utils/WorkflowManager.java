/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.utils;

import io.onsemiro.core.context.AppContextManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WorkflowManager extends ClassLoader {
    @Value("${native.library.path}")
    private String nativeLibrary;

    private final static Map<String, Runnable> threadStopMap = new HashMap<>();

    public void invokeProcess(String batchId, String classBinName, Map<String, Object> parameterMap) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = AppContextManager.getBean(ThreadPoolTaskExecutor.class);

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
            Class loadedMyClass = urlClassLoader.loadClass(classBinName);

            System.out.println("Loaded class name: " + loadedMyClass.getName());

            Constructor constructor = loadedMyClass.getConstructor();
            Runnable myClassObject = (Runnable) constructor.newInstance();

            // 키 추출
            List<String> keys = new ArrayList(parameterMap.keySet());
            Method method = null;

            for (int j = 0; j < keys.size(); j++) {
                Object parameterObject = parameterMap.get(keys.get(j));

                if (parameterObject instanceof String) {
                    method = loadedMyClass.getMethod("set" + makeMethodName(keys.get(j)), new Class[]{String.class});
                } else if (parameterObject instanceof Integer) {
                    method = loadedMyClass.getMethod("set" + makeMethodName(keys.get(j)), new Class[]{Integer.class});
                }

                method.invoke(myClassObject, parameterObject);
                System.out.println("Invoked method name: " + method.getName());
            }

            // BatchID 셋팅
            Method setBatchIdMethod = loadedMyClass.getMethod("setBatchId", new Class[]{String.class});
            setBatchIdMethod.invoke(myClassObject, batchId);

            // 쓰레드 저장
            threadStopMap.put(batchId, myClassObject);

            // 쓰레드 생성
            Thread thread = new Thread(myClassObject);
            threadPoolTaskExecutor.execute(thread);



            /*Method runMethod = loadedMyClass.getMethod("runProcess");
            System.out.println("Invoked Run Process method");

            runMethod.invoke(myClassObject);*/

          /*  Method threadNameMethod = loadedMyClass.getMethod("getThreadName");
            String threadName = (String) threadNameMethod.invoke(myClassObject);
            System.out.println("Invoked Run Thread Name :  " + threadName);*/

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopProcess(String batchId) {
        Runnable runningThread = (Runnable) threadStopMap.get(batchId);

        if (runningThread != null) {
            Class threadClass = runningThread.getClass();

            try {
                Method method = threadClass.getMethod("stopProcess", new Class[]{String.class});
                method.invoke(runningThread, batchId);

                // 성공시에만 삭제
                threadStopMap.remove(batchId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String makeMethodName(String methodName) {
        String tmpName = "";

        tmpName = methodName.toLowerCase();
        tmpName = tmpName.substring(0, 1).toUpperCase() + tmpName.substring(1, tmpName.length());

        return tmpName;
    }

    @PreDestroy
    public void shutdownAllThread() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = AppContextManager.getBean(ThreadPoolTaskExecutor.class);
        threadPoolTaskExecutor.shutdown();

        System.out.println("All Thread are shutdown before System terminated");
    }
}
