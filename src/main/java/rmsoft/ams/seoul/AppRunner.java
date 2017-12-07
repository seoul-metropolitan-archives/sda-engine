package rmsoft.ams.seoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"io.onsemiro", "rmsoft.ams.seoul"})
@EnableScheduling
public class AppRunner extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppRunner.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }
}
