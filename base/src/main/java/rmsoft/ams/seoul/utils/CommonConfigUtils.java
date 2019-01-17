/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.utils;

import io.onsemiro.core.context.AppContextManager;
import io.onsemiro.utils.JsonUtils;
import rmsoft.ams.seoul.common.domain.AdConfiguration;
import rmsoft.ams.seoul.common.repository.AdConfigurationRepository;

import java.util.List;

/**
 * The type Common config utils.
 */
public class CommonConfigUtils {
    private static List<AdConfiguration> commonConfigList;

    /**
     * Gets config name.
     *
     * @param configCode the config code
     * @return the config name
     */
    public static String getConfigName(String configCode) {
        if (commonConfigList == null) {
            commonConfigList = getAllConfigByList();
        }

        AdConfiguration config = null;

        config = commonConfigList.stream()
                .filter(adConfiguration -> adConfiguration.getConfigurationCode().equals(configCode))
                .findFirst().get();

        return config.getConfigurationValue();
    }

    /**
     * Gets all config by list.
     *
     * @return the all config by list
     */
    public static List<AdConfiguration> getAllConfigByList() {
        commonConfigList = null;
        commonConfigList = getRepository().findAll();

        List<AdConfiguration> configurationList = commonConfigList;

        return configurationList;
    }

    /**
     * Gets all config by json.
     *
     * @return the all config by json
     */
    public static String getAllConfigByJson() {
        return JsonUtils.toJson(getAllConfigByList());
    }


    /**
     * Gets repository.
     *
     * @return the repository
     */
    public static AdConfigurationRepository getRepository() {
        return AppContextManager.getBean(AdConfigurationRepository.class);
    }
}
