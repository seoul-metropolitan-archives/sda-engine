package rmsoft.ams.seoul.utils;

import io.onsemiro.core.context.AppContextManager;
import io.onsemiro.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import rmsoft.ams.seoul.ad.ad003.service.Ad003Service;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00303VO;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * The type Common code utils.
 */
public class CommonCodeUtils {
    private static Map<String, List<Ad00303VO>> commonCodeMap;
    private static List<Ad00303VO> commonCodes;

    /**
     * Get list.
     *
     * @param groupCd the group cd
     * @return the list
     */
    public static List<Ad00303VO> get(String groupCd) {
        if (commonCodeMap == null) {
            Ad00303VO ad00303VO = new Ad00303VO();
            List<Ad00303VO> commonCodes = getService().getCode(ad00303VO);

            return commonCodes;
        } else {
            return commonCodeMap.get(groupCd);
        }
    }

    /**
     * Gets code.
     *
     * @param groupCd the group cd
     * @param name    the name
     * @return the code
     */
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

    /**
     * Gets name.
     *
     * @param groupCd the group cd
     * @param code    the code
     * @return the name
     */
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

    /**
     * Gets code detail uuid.
     *
     * @param groupCd the group cd
     * @param name    the name
     * @return the code detail uuid
     */
    public static String getCodeDetailUuid(String groupCd, String name) {

        if(commonCodeMap == null){
            commonCodeMap =  getAllByMap();
        }

        List<Ad00303VO> commonCodes = commonCodeMap.get(groupCd);
        Ad00303VO commonCode = commonCodes.stream()
                .filter(e -> e.getCodeName().equals(name.trim()))
                .findFirst().get();

        return StringUtils.isEmpty(commonCode.getCodeDetailUUID()) ? "미등록코드" : commonCode.getCodeDetailUUID();
    }

    /**
     * Gets code detail uuid by code.
     *
     * @param groupCd the group cd
     * @param code    the code
     * @return the code detail uuid by code
     */
    public static String getCodeDetailUuidByCode(String groupCd, String code) {

        if(commonCodeMap == null){
            commonCodeMap =  getAllByMap();
        }

        List<Ad00303VO> commonCodes = commonCodeMap.get(groupCd);
        Ad00303VO commonCode = commonCodes.stream()
                .filter(e -> e.getCode().equals(code.trim()))
                .findFirst().get();

        return StringUtils.isEmpty(commonCode.getCodeDetailUUID()) ? "미등록코드" : commonCode.getCodeDetailUUID();
    }

    /**
     * Gets detail code.
     *
     * @param groupCd the group cd
     * @param cdUuid  the cd uuid
     * @return the detail code
     */
    public static String getDetailCode(String groupCd, String cdUuid) {

        if(commonCodeMap == null){
            commonCodeMap =  getAllByMap();
        }

        List<Ad00303VO> commonCodes = commonCodeMap.get(groupCd);
        Ad00303VO commonCode = commonCodes.stream()
                .filter(e -> e.getCodeDetailUUID().equals(cdUuid.trim()))
                .findFirst().get();

        return StringUtils.isEmpty(commonCode.getCode()) ? "미등록코드" : commonCode.getCode();
    }
    /**
     * Gets detail code.
     *
     * @param groupCd the group cd
     * @param cdUuid  the cd uuid
     * @return the detail code
     */
    public static String getAttr01Code(String groupCd, String cdUuid) {

        if(commonCodeMap == null){
            commonCodeMap =  getAllByMap();
        }

        List<Ad00303VO> commonCodes = commonCodeMap.get(groupCd);
        Ad00303VO commonCode = commonCodes.stream()
                .filter(e -> e.getCodeDetailUUID().equals(cdUuid.trim()))
                .findFirst().get();

        return StringUtils.isEmpty(commonCode.getAttribute01()) ? "미등록코드" : commonCode.getAttribute01();
    }
    /**
     * Gets all by map.
     *
     * @return the all by map
     */
    public static Map<String, List<Ad00303VO>> getAllByMap() {
        Ad00303VO ad00303VO = new Ad00303VO();
        List<Ad00303VO> commonCodes = getService().getCode(ad00303VO);

        commonCodeMap = null;
        commonCodeMap = commonCodes.stream().collect(groupingBy(Ad00303VO::getCategoryCode));

        return commonCodeMap;
    }

    /**
     * Gets all by json.
     *
     * @return the all by json
     */
    public static String getAllByJson() {
        return JsonUtils.toJson(getAllByMap());
    }


    /**
     * Gets service.
     *
     * @return the service
     */
    public static Ad003Service getService() {
        return AppContextManager.getBean(Ad003Service.class);
    }
}
