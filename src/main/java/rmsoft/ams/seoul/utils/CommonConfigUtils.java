/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.utils;

import io.onsemiro.core.context.AppContextManager;
import io.onsemiro.utils.JsonUtils;
import rmsoft.ams.seoul.common.domain.AdConfiguration;
import rmsoft.ams.seoul.common.repository.AdConfigurationRepository;

import java.util.List;

public class CommonConfigUtils {
    private static List<AdConfiguration> commonConfigList;

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

    public static List<AdConfiguration> getAllConfigByList() {
        commonConfigList = null;
        commonConfigList = getRepository().findAll();

        return commonConfigList;
    }

    public static String getAllConfigByJson() {
        return JsonUtils.toJson(getAllConfigByList());
    }


    public static AdConfigurationRepository getRepository() {
        return AppContextManager.getBean(AdConfigurationRepository.class);
    }
}
