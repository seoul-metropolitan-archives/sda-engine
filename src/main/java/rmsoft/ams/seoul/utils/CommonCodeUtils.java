package rmsoft.ams.seoul.utils;

import io.onsemiro.core.context.AppContextManager;
import io.onsemiro.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import rmsoft.ams.seoul.ad.ad003.service.Ad003Service;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00303VO;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class CommonCodeUtils {
    private static Map<String, List<Ad00303VO>> commonCodeMap;
    private static List<Ad00303VO> commonCodes;

    public static List<Ad00303VO> get(String groupCd) {
        if (commonCodeMap == null) {
            Ad00303VO ad00303VO = new Ad00303VO();
            List<Ad00303VO> commonCodes = getService().getCode(ad00303VO);

            return commonCodes;
        } else {
            return commonCodeMap.get(groupCd);
        }
    }

    public static String getCode(String groupCd, String name) {

        List<Ad00303VO> commonCodes = get(groupCd);

        Ad00303VO commonCode = commonCodes.stream()
                .filter(e -> e.getCodeName().equals(name))
                .findFirst().get();

        if (StringUtils.isEmpty(commonCode.getCode())) {
            commonCodes = get(groupCd);

            commonCode = commonCodes.stream()
                    .filter(e -> e.getCodeName().equals(name))
                    .findFirst().get();
        }

        return StringUtils.isEmpty(commonCode.getCode()) ? "미등록코드" : commonCode.getCode();
    }

    public static String getName(String groupCd, String code) {

        List<Ad00303VO> commonCodes = get(groupCd);

        Ad00303VO commonCode = commonCodes.stream()
                .filter(e -> e.getCode().equals(code.trim()))
                .findFirst().get();

        if (StringUtils.isEmpty(commonCode.getCodeName())) {
            commonCodes = get(groupCd);

            commonCode = commonCodes.stream()
                    .filter(e -> e.getCode().equals(code.trim()))
                    .findFirst().get();
        }

        return StringUtils.isEmpty(commonCode.getCodeName()) ? "미등록코드명" : commonCode.getCodeName();
    }

    public static Map<String, List<Ad00303VO>> getAllByMap() {
        Ad00303VO ad00303VO = new Ad00303VO();
        List<Ad00303VO> commonCodes = getService().getCode(ad00303VO);

        commonCodeMap = null;
        commonCodeMap = commonCodes.stream().collect(groupingBy(Ad00303VO::getCategoryCode));

        return commonCodeMap;
    }

    public static String getAllByJson() {
        return JsonUtils.toJson(getAllByMap());
    }


    public static Ad003Service getService() {
        return AppContextManager.getBean(Ad003Service.class);
    }
}
