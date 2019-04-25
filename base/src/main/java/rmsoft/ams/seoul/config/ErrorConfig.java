package rmsoft.ams.seoul.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * The type Web mvc config.
 */
@Configuration
public class ErrorConfig extends ServerProperties {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        super.customize(container);

        //container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
        //container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error/500"));
        //container.addErrorPages(new ErrorPage("/error/default"));*/
    }
}
