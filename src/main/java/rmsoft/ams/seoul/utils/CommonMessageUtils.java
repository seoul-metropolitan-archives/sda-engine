/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.utils;

import io.onsemiro.core.context.AppContextManager;
import io.onsemiro.utils.JsonUtils;
import rmsoft.ams.seoul.common.domain.AdMessage;
import rmsoft.ams.seoul.common.repository.AdMessageRepository;

import java.util.List;

/**
 * The type Common message utils.
 */
public class CommonMessageUtils {
    private static List<AdMessage> commonMessageList;

    /**
     * Gets message.
     *
     * @param messageCode the message code
     * @return the message
     */
    public static String getMessage(String messageCode) {
        return getMessage(messageCode, false);
    }

    /**
     * Gets message.
     *
     * @param messageCode     the message code
     * @param isDatabaseError the is database error
     * @return the message
     */
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

    /**
     * Gets all message by list.
     *
     * @return the all message by list
     */
    public static List<AdMessage> getAllMessageByList() {
        commonMessageList = null;
        commonMessageList = getRepository().findAll();

        return commonMessageList;
    }

    /**
     * Gets all message by json.
     *
     * @return the all message by json
     */
    public static String getAllMessageByJson() {
        return JsonUtils.toJson(getAllMessageByList());
    }


    /**
     * Gets repository.
     *
     * @return the repository
     */
    public static AdMessageRepository getRepository() {
        return AppContextManager.getBean(AdMessageRepository.class);
    }
}
