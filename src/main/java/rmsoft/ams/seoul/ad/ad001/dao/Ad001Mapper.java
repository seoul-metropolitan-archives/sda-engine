package rmsoft.ams.seoul.ad.ad001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad001.vo.Ad00101VO;

import java.util.List;

public interface Ad001Mapper extends MyBatisMapper {

    /*      +  "   AND"
            +"   ("
            +"   A.CONFIGURATION_CODE = #{configuratinoCode}"
            +"   OR"
            +"   A.CONFIGURATION_VALUE = #{configurationValue}"
            +"   OR"
            +"   A.SERVICE_UUID = #{serviceUUID}"
            +"   OR"
            +"   A.USE_YN = #{useYN})"
    */
    List<Ad00101VO> select(Ad00101VO param);

}
