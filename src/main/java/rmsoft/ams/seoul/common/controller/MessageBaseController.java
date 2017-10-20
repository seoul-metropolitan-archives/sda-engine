/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

import java.sql.SQLException;

/**
 * Created by james on 2016-12-28.
 */
@ControllerAdvice
public class MessageBaseController extends BaseController {

    @Override
    public ApiResponse handleApiException(ApiException e) {
        errorLogging(e);
        return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage(e.getCodeStr()));
    }

    @Override
    public ApiResponse handleException(Throwable throwable) {
        errorLogging(throwable);
        ApiResponse apiResponse = ApiResponse.error(ApiStatus.SYSTEM_ERROR, throwable.getMessage());

        Throwable rootCause = ExceptionUtils.getRootCause(throwable);

        if (rootCause != null) {
            if (rootCause instanceof SQLException) {
                String[] oraCode = rootCause.getLocalizedMessage().split(":");
                String message = CommonMessageUtils.getMessage(oraCode[0], true);
                apiResponse = ApiResponse.error(ApiStatus.SYSTEM_ERROR, message);
            }
        }
        return apiResponse;
    }
}
