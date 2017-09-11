package io.onsemiro.utils;

import io.onsemiro.core.code.AXBootTypes;
import io.onsemiro.core.context.AppContextManager;
import io.onsemiro.core.domain.code.CommonCode;
import io.onsemiro.core.domain.code.CommonCodeService;
import io.onsemiro.core.parameter.RequestParams;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class CommonCodeUtils {

    private static Map<String, List<CommonCode>> commonCodeMap;
    private static List<CommonCode> commonCodes;

    public static List<CommonCode> get(String groupCd) {
        if(commonCodeMap == null){
            RequestParams<CommonCode> requestParams = new RequestParams<>(CommonCode.class);
            requestParams.put("groupCd", groupCd);
            requestParams.put("useYn", AXBootTypes.Used.YES.getLabel());

            return getService().get(requestParams);
        }else{
            return commonCodeMap.get(groupCd);
        }
    }

    public static String getCode(String groupCd, String name) {

        List<CommonCode> commonCodes = get(groupCd);

        CommonCode commonCode = commonCodes.stream()
                                           .filter(e -> e.getName().equals(name))
                                           .findFirst().get();

        if (StringUtils.isEmpty(commonCode.getCode())) {
            commonCodes = get(groupCd);

            commonCode = commonCodes.stream()
                                    .filter(e -> e.getName().equals(name))
                                    .findFirst().get();
        }

        return StringUtils.isEmpty(commonCode.getCode())?"미등록코드":commonCode.getCode();
    }

    public static String getName(String groupCd, String code) {

        List<CommonCode> commonCodes = get(groupCd);

        CommonCode commonCode = commonCodes.stream()
                                           .filter(e -> e.getCode().equals(code.trim()))
                                           .findFirst().get();

        if (StringUtils.isEmpty(commonCode.getName())) {
            commonCodes = get(groupCd);

            commonCode = commonCodes.stream()
                                    .filter(e -> e.getCode().equals(code.trim()))
                                    .findFirst().get();
        }

        return StringUtils.isEmpty(commonCode.getName())?"미등록코드명":commonCode.getName();
    }

    public static Map<String, List<CommonCode>> getAllByMap() {
        RequestParams<CommonCode> requestParams = new RequestParams<>(CommonCode.class);
        requestParams.put("useYn", AXBootTypes.Used.YES.getLabel());
        List<CommonCode> commonCodes = getService().get(requestParams);

        commonCodeMap = null;
        commonCodeMap = commonCodes.stream().collect(groupingBy(CommonCode::getGroupCd));

        return commonCodeMap;
    }

    public static String getAllByJson() {
        return JsonUtils.toJson(getAllByMap());
    }


    public static CommonCodeService getService() {
        return AppContextManager.getBean(CommonCodeService.class);
    }
}
