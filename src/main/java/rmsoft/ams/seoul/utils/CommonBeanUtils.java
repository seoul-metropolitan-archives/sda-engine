package rmsoft.ams.seoul.utils;

import io.onsemiro.core.context.AppContextManager;
import org.springframework.context.ApplicationContext;

public class CommonBeanUtils {

    public static Object getBean(String beanId) {

        ApplicationContext applicationContext = AppContextManager.getAppContext();

        if (applicationContext == null) {
            throw new NullPointerException("Spring의 ApplicationContext초기화 안됨");
        }


        String[] names = applicationContext.getBeanDefinitionNames();
        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i]);
        }


        return applicationContext.getBean(beanId);
    }
}