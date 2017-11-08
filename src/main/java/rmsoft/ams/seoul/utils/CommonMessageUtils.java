/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.utils;

import io.onsemiro.core.context.AppContextManager;
import io.onsemiro.utils.JsonUtils;
import rmsoft.ams.seoul.common.domain.AdMessage;
import rmsoft.ams.seoul.common.repository.AdMessageRepository;

import java.util.List;

public class CommonMessageUtils {
    private static List<AdMessage> commonMessageList;

    public static String getMessage(String messageCode) {
        return getMessage(messageCode, false);
    }

    public static String getMessage(String messageCode, boolean isDatabaseError) {
        if (commonMessageList == null) {
            commonMessageList = getAllMessageByList();
        }

        AdMessage message = null;

        try {
            if (isDatabaseError) {
                message = commonMessageList.stream()
                        .filter(errorCode -> errorCode.getDbErrorCode() != null && errorCode.getDbErrorCode().equals(messageCode.trim()))
                        .findFirst().get();
            } else {
                message = commonMessageList.stream()
                        .filter(errorCode -> errorCode.getMessageCode() != null && errorCode.getMessageCode().equals(messageCode.trim()))
                        .findFirst().get();

            }
        } catch (Exception e) {
            if (isDatabaseError) {
                message = commonMessageList.stream()
                        .filter(errorCode -> errorCode.getMessageCode().equals("AA004"))
                        .findFirst().get();
            } else {
                message = commonMessageList.stream()
                        .filter(errorCode -> errorCode.getMessageCode().equals("AA009"))
                        .findFirst().get();
            }
        }

        return message.getMessageName();
    }

    public static List<AdMessage> getAllMessageByList() {
        commonMessageList = null;
        commonMessageList = getRepository().findAll();

        return commonMessageList;
    }

    public static String getAllMessageByJson() {
        return JsonUtils.toJson(getAllMessageByList());
    }


    public static AdMessageRepository getRepository() {
        return AppContextManager.getBean(AdMessageRepository.class);
    }
}
