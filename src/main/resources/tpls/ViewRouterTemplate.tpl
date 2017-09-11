package ${controllerPackageName};

import io.onsemiro.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ${controllerClassName} extends BaseController {

    @GetMapping("${url}")
    public String view() {
        return "${viewName}";
    }
}

