package rmsoft.ams.seoul;

import io.onsemiro.core.context.AppContextManager;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import rmsoft.ams.seoul.utils.RuntimeJarLoader;

import javax.annotation.PostConstruct;

/**
 * The type App runner.
 */
@SpringBootApplication(scanBasePackages = {"io.onsemiro", "rmsoft.ams.seoul"})
@EnableScheduling
@Log4j
public class AppRunner extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        log.info("SpringApplicationBuilder Start...");
        RuntimeJarLoader.loadJarIndDir("/app/www/WEB-INF/classes/service-modules/");

        return application.sources(AppRunner.class);
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        //
        // /Users/jspark226/IdeaProjects/seoul-ams/base/src/main/resources/service-modules/
        //RuntimeJarLoader.loadJarIndDir(args[0]);

        log.info("AppRunnerStart...");

        new SpringApplicationBuilder(AppRunner.class)
                .listeners(new SysEnvEventListener())
                .run(args);

        log.info("AppRunnerEnd...");
    }

    public static void restart(RestartEndpoint restartEndpoint) {
        Thread restartThread = new Thread(() -> restartEndpoint.restart());
        restartThread.setDaemon(false);
        restartThread.start();
    }
}
