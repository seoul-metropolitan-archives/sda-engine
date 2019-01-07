package rmsoft.ams.seoul;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import rmsoft.ams.seoul.utils.RuntimeJarLoader;

import java.util.Map;

/**
 * SystemEnvironment Property Resource add
 * Created by jjh on 2018-10-22
 */
@Slf4j
public class SysEnvEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Value("${service.module.path}")
    public static String serviceModulePath;

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        log.info("SystemEnvrionment Add Properties Start");

        try {

            String serviceModulePath = readServicePathPropsFromYaml();

            RuntimeJarLoader.loadJarIndDir(serviceModulePath);

            /*List<Map<String, Object>> dbPropsList = DBPropResourceHandler.getPropertySources();

            dbPropsList.forEach(propsMap -> {
                PropertySource propertySource =
                        DBPropResource.of(String.valueOf(propsMap.get("SYSENV_KEY")), propsMap.get("SYSENV_VALUE"));

                event.getEnvironment().getPropertySources().addFirst(propertySource);
            });*/

        } catch (Exception e) {
            log.error("시스템 프로퍼티를 가져오는 작업 중에 오류가 발생하였습니다.", e);
        }

        log.info("SystemEnvrionment Add Properties End");
    }

    private static String readServicePathPropsFromYaml() throws Exception {

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Resource yamlFile = new ClassPathResource("application.yml");

        Yaml yaml = new Yaml(options);
        Iterable<Object> yamlProps = yaml.loadAll(yamlFile.getInputStream());

        String resultServicePath = "";

        for (Object obj: yamlProps) {
            Map<String, String> propsMap = (Map<String, String>) obj;
            if (propsMap.containsKey("service.module.path")) {
                resultServicePath = (String)propsMap.get("service.module.path");
                break;
            }
        }

        return resultServicePath;
    }
}
