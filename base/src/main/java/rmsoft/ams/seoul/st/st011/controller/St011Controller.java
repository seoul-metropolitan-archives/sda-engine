package rmsoft.ams.seoul.st.st011.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.st.st011.service.St011Service;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/st/st011")
public class St011Controller extends BaseController {

    @Inject
    private St011Service st004Service;

}