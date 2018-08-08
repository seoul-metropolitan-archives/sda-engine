package rmsoft.ams.seoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The type App runner.
 */
@SpringBootApplication(scanBasePackages = {"io.onsemiro", "rmsoft.ams.seoul"})
@EnableScheduling
public class AppRunner extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppRunner.class);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }

    public static void restart(RestartEndpoint restartEndpoint) {
        Thread restartThread = new Thread(() -> restartEndpoint.restart());
        restartThread.setDaemon(false);
        restartThread.start();
    }
}
